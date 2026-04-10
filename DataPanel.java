import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class DataPanel extends JPanel
{   
    private JLabel massLabel;
    private JLabel velocityLabel;
    private JLabel vxLabel;
    private JLabel vyLabel;
    private JLabel xLabel;
    private JLabel yLabel;
    private JLabel directionLabel;
    private JLabel densityLabel;
    private JLabel impulseLabel;
    private JLabel averageForceLabel;

    public DataPanel()
    {
        this.setBackground(new Color(20, 20, 20));

        massLabel = new JLabel();
        massLabel.setPreferredSize(new Dimension(200, 30));
        massLabel.setText("Mass: ");
        massLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        massLabel.setForeground(Color.WHITE);
        this.add(massLabel);

        velocityLabel = new JLabel();
        velocityLabel.setPreferredSize(new Dimension(200, 30));
        velocityLabel.setText("Velocity: ");
        velocityLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        velocityLabel.setForeground(Color.WHITE);
        this.add(velocityLabel);

        vxLabel = new JLabel();
        vxLabel.setPreferredSize(new Dimension(200, 30));
        vxLabel.setText("VX: ");
        vxLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        vxLabel.setForeground(Color.WHITE);
        this.add(vxLabel);

        vyLabel = new JLabel();
        vyLabel.setPreferredSize(new Dimension(200, 30));
        vyLabel.setText("VY: ");
        vyLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        vyLabel.setForeground(Color.WHITE);
        this.add(vyLabel);

        xLabel = new JLabel();
        xLabel.setPreferredSize(new Dimension(200, 30));
        xLabel.setText("X: ");
        xLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        xLabel.setForeground(Color.WHITE);
        this.add(xLabel);

        yLabel = new JLabel();
        yLabel.setPreferredSize(new Dimension(200, 30));
        yLabel.setText("Y: ");
        yLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        yLabel.setForeground(Color.WHITE);
        this.add(yLabel);

        directionLabel = new JLabel();
        directionLabel.setPreferredSize(new Dimension(200, 30));
        directionLabel.setText("Direction: ");
        directionLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        directionLabel.setForeground(Color.WHITE);
        this.add(directionLabel);

        densityLabel = new JLabel();
        densityLabel.setPreferredSize(new Dimension(200, 30));
        densityLabel.setText("Density: ");
        densityLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        densityLabel.setForeground(Color.WHITE);
        this.add(densityLabel);

        impulseLabel = new JLabel();
        impulseLabel.setPreferredSize(new Dimension(200, 30));
        impulseLabel.setText("Impulse: ");
        impulseLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        impulseLabel.setForeground(Color.WHITE);
        this.add(impulseLabel);

        averageForceLabel = new JLabel();
        averageForceLabel.setPreferredSize(new Dimension(200, 30));
        averageForceLabel.setText("Average Force: ");
        averageForceLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        averageForceLabel.setForeground(Color.WHITE);
        this.add(averageForceLabel);
    }

    public void updateData(MainPanel mainPanel, SimulationPanel simulationPanel)
    {
        massLabel.setText("Mass: " + mainPanel.getMass() + " g");
        velocityLabel.setText("Velocity: " + Math.round(mainPanel.getVelocity()) + " m/s");
        vxLabel.setText("VX: " + Math.round(mainPanel.getVX()) + " m/s");
        vyLabel.setText("VY: " + -Math.round(mainPanel.getVY()) + " m/s");
        xLabel.setText("X: " + Math.round(mainPanel.getObjectX()));
        yLabel.setText("Y: " + (simulationPanel.getHeight() - Math.round(mainPanel.getObjectY()) - mainPanel.size));
        directionLabel.setText("Direction: " + Math.round(Math.toDegrees(mainPanel.getDirection())) + "°");
        densityLabel.setText("Density: " + mainPanel.getDensity() + " g/cm" + "\u00B3");
        impulseLabel.setText("Impulse: " + mainPanel.getImpulse() + " N·s");
        averageForceLabel.setText("Average Force: " + mainPanel.getAverageForce() + " N");
    }
}
