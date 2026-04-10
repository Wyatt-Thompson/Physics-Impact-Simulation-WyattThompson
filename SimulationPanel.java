import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Window;

import javax.swing.Timer;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class SimulationPanel extends JPanel
{
    MainPanel mainPanel;
    DataPanel dataPanel;

    private int FPS = 60;

    private Timer timer;

    private double startTime;
    private double currentTime;
    
    public double timeElapsed;

    private final double GRAVITY = 9.81;

    private double[] bounceMultipliers =
    {
        -0.7,
        -0.6,
        -0.8,
        -0.3,
        -0.5
    };

    private double[] frictionMultipliers =
    {
        0.8,
        0.7,
        0.85,
        0.4,
        0.5
    };

    private Color[] materialColors =
    {
        new Color(247, 247, 247),
        new Color(115, 76, 38),
        new Color(128, 128, 128),
        new Color(255, 252, 156),
        new Color(244, 255, 38)
    };

    public SimulationPanel(MainPanel mainPanel, DataPanel dataPanel)
    {
        this.mainPanel = mainPanel;
        this.dataPanel = dataPanel;

        this.setBackground(new Color(120, 180, 230));

        this.timer = new Timer(1000 / FPS, e -> {
            currentTime = System.currentTimeMillis();

            timeElapsed = (currentTime - startTime) / 1000;

            update();
            repaint();

            dataPanel.updateData(mainPanel, this);
        });
    }

    public void startSim()
    {
        startTime = System.currentTimeMillis();

        timer.start();
    }

    private void update()
    {
        double tempVelocity = mainPanel.getVelocity();

        mainPanel.previousVelocity = tempVelocity;

        mainPanel.vy += (GRAVITY * 8 / FPS);

        if (mainPanel.y >= this.getHeight() - mainPanel.GROUND_LEVEL - mainPanel.size)
        {
            mainPanel.y = this.getHeight() - mainPanel.GROUND_LEVEL - mainPanel.size;
            
            mainPanel.vy *= bounceMultipliers[mainPanel.selectedMaterial];

            mainPanel.vx *= frictionMultipliers[mainPanel.selectedMaterial];

            if (Math.abs(mainPanel.getVY()) <= 10)
            {
                mainPanel.vy = 0;
            }

            if (Math.abs(mainPanel.getVX()) <= 5)
            {
                mainPanel.vx = 0;
            }
        }

        if (mainPanel.x >= this.getWidth() - mainPanel.WALL_WIDTH - mainPanel.size)
        {
            mainPanel.x = this.getWidth() - mainPanel.WALL_WIDTH - mainPanel.size;
            
            mainPanel.vx *= bounceMultipliers[mainPanel.selectedMaterial];
        }

        mainPanel.velocity = Math.round(Math.sqrt(Math.pow(mainPanel.getVX(), 2) + Math.pow(mainPanel.getVY(), 2)) * 100.0) / 100.0;

        mainPanel.direction = Math.round(Math.atan2(mainPanel.getVY(), mainPanel.getVX()) * 100.0) / 100.0;

        mainPanel.impulse = Math.round((mainPanel.getMass() * (mainPanel.getVelocity() - mainPanel.previousVelocity)) * 100.0) / 100.0;

        if (this.timeElapsed > 0)
        {
            mainPanel.averageForce = Math.abs(Math.round((mainPanel.getImpulse() / this.timeElapsed) * 100.0) / 100.0);
        }
        else
        {
            mainPanel.averageForce = 0;
        }

        if (mainPanel.getVX() == 0 && mainPanel.getVY() == 0)
        {
            endSim();
        }

        mainPanel.x += ((mainPanel.getVX() * 6) / FPS);
        mainPanel.y += ((mainPanel.getVY() * 6) / FPS);
    }

    private void endSim()
    {
        timer.stop();

        ImageIcon mrMathieImage = new ImageIcon("mr-mathie.jpg");

        int choice = JOptionPane.showConfirmDialog(
            null,
            "Impulse: " + mainPanel.getImpulse() + "\nAverage Force: " + mainPanel.getAverageForce() + "\nWould you like to continue?",
            "End of Simulation",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.YES_NO_OPTION,
            mrMathieImage);

        if (choice == JOptionPane.NO_OPTION)
        {
            Window window = SwingUtilities.getWindowAncestor(mainPanel);

            if (window != null)
            {
                window.dispose();
                System.exit(0);
            }
        }
    }


    @Override
    protected void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;

        super.paintComponent(g2);

        g2.setColor(new Color(95, 65, 40));
        g2.fillRect(this.getWidth() - mainPanel.WALL_WIDTH, 0 - mainPanel.GROUND_LEVEL, mainPanel.WALL_WIDTH, this.getHeight());

        g2.setColor(new Color(110, 165, 60));
        g2.fillRect(0, this.getHeight() - mainPanel.GROUND_LEVEL, this.getWidth(), mainPanel.GROUND_LEVEL);

        g2.setColor(materialColors[mainPanel.selectedMaterial]);
        g2.fillArc((int) mainPanel.x, (int) mainPanel.y, mainPanel.size, mainPanel.size, 0, 360);

        int centerX = (int) (mainPanel.x + (mainPanel.size / 2));
        int centerY = (int) (mainPanel.y + (mainPanel.size / 2));

        if (Math.abs(mainPanel.getVX()) > 0 || Math.abs(mainPanel.getVY()) > 0)
        {
            g2.setColor(Color.BLUE);
            g2.setStroke(new BasicStroke(3f));
            g2.drawLine(centerX, centerY, (int) (centerX + mainPanel.getVX()), (int) (centerY + mainPanel.getVY()));
        }
    }
}