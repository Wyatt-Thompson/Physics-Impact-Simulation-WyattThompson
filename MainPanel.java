import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import java.awt.BorderLayout;

public class MainPanel extends JPanel
{
    private JSlider massSlider;
    private JSlider velocitySlider;
    private JSlider directionSlider;
    private DataPanel dataPanel;
    private SimulationPanel simulationPanel;
    private JComboBox<String> materialComboBox;

    public int size = 75;

    public final int GROUND_LEVEL = 150;

    public final int WALL_WIDTH = 75;

    public double x;
    public double y;

    public int mass;

    public double previousVelocity;
    public double velocity;

    public double vx;
    public double vy;

    public double direction;

    public int selectedMaterial = 0;

    public double[] densityList =
    {
        0.91,
        0.60,
        7.87,
        1.60,
        1.02
    };

    public double impulse;

    public double density;

    public double averageForce;

    public MainPanel()
    {
        this.setPreferredSize(new Dimension(1000, 600));
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(40, 40, 40));

        JPanel controlsPanel = new JPanel();
        controlsPanel.setPreferredSize(new Dimension(getWidth(), 90));
        controlsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 5));
        controlsPanel.setBackground(new Color(30, 30, 30));
        this.add(controlsPanel, BorderLayout.NORTH);

        JButton startButton = new JButton("START");
        startButton.setPreferredSize(new Dimension(100, 45));
        startButton.setBackground(new Color(255, 67, 54));
        startButton.setBorderPainted(false);
        startButton.setFocusPainted(false);
        startButton.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        startButton.setForeground(Color.WHITE);
        startButton.addActionListener(new StartButtonActionListener());
        controlsPanel.add(startButton);

        // MASS

        JPanel massPanel = new JPanel();
        massPanel.setPreferredSize(new Dimension(170, 70));
        massPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        massPanel.setOpaque(false);
        controlsPanel.add(massPanel);

        JLabel massLabel = new JLabel();
        massLabel.setPreferredSize(new Dimension(170, 20));
        massLabel.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        massLabel.setText("Mass (g)");
        massLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        massLabel.setForeground(Color.WHITE);
        massPanel.add(massLabel);

        massSlider = new JSlider(25, 100, 50);
        massSlider.setPreferredSize(new Dimension(170, 50));
        massSlider.setOpaque(false);
        massSlider.setPaintLabels(true);
        massSlider.setMajorTickSpacing(1);
        massSlider.setSnapToTicks(true);
        Hashtable<Integer, JLabel> massLT = new Hashtable<>();
        massLT.put(25, new SliderLabel(25));
        massLT.put(100, new SliderLabel(100));
        massSlider.setLabelTable(massLT);
        massPanel.add(massSlider);

        // VELOCITY

        JPanel velocityPanel = new JPanel();
        velocityPanel.setPreferredSize(new Dimension(170, 70));
        velocityPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        velocityPanel.setOpaque(false);
        controlsPanel.add(velocityPanel);

        JLabel velocityLabel = new JLabel();
        velocityLabel.setPreferredSize(new Dimension(170, 20));
        velocityLabel.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        velocityLabel.setText("Velocity (m/s)");
        velocityLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        velocityLabel.setForeground(Color.WHITE);
        velocityPanel.add(velocityLabel);

        velocitySlider = new JSlider(1, 200, 75);
        velocitySlider.setPreferredSize(new Dimension(170, 50));
        velocitySlider.setOpaque(false);
        velocitySlider.setPaintLabels(true);
        velocitySlider.setMajorTickSpacing(1);
        velocitySlider.setSnapToTicks(true);
        Hashtable<Integer, JLabel> velocityLT = new Hashtable<>();
        velocityLT.put(1, new SliderLabel(1));
        velocityLT.put(200, new SliderLabel(200));
        velocitySlider.setLabelTable(velocityLT);
        velocityPanel.add(velocitySlider);

        // DIRECTION

        JPanel directionPanel = new JPanel();
        directionPanel.setPreferredSize(new Dimension(170, 70));
        directionPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        directionPanel.setOpaque(false);
        controlsPanel.add(directionPanel);

        JLabel directionLabel = new JLabel();
        directionLabel.setPreferredSize(new Dimension(170, 20));
        directionLabel.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        directionLabel.setText("Direction (°)");
        directionLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        directionLabel.setForeground(Color.WHITE);
        directionPanel.add(directionLabel);

        directionSlider = new JSlider(0, 90, 30);
        directionSlider.setPreferredSize(new Dimension(170, 50));
        directionSlider.setOpaque(false);
        directionSlider.setPaintLabels(true);
        directionSlider.setMajorTickSpacing(1);
        directionSlider.setSnapToTicks(true);
        Hashtable<Integer, JLabel> directionLT = new Hashtable<>();
        directionLT.put(0, new SliderLabel(0));
        directionLT.put(90, new SliderLabel(90));
        directionSlider.setLabelTable(directionLT);
        directionPanel.add(directionSlider);

        // MATERIAL

        JPanel materialPanel = new JPanel();
        materialPanel.setPreferredSize(new Dimension(150, 80));
        materialPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        materialPanel.setOpaque(false);
        controlsPanel.add(materialPanel);

        JLabel materialLabel = new JLabel();
        materialLabel.setPreferredSize(new Dimension(150, 20));
        materialLabel.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        materialLabel.setText("Material");
        materialLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        materialLabel.setForeground(Color.WHITE);
        materialPanel.add(materialLabel);

        materialComboBox = new JComboBox<String>();
        materialComboBox.setPreferredSize(new Dimension(150, 35));
        materialComboBox.addItem("Plastic - 0.91g/cm" + "\u00B3");
        materialComboBox.addItem("Wood - 0.60g/cm" + "\u00B3");
        materialComboBox.addItem("Metal - 7.87/cm" + "\u00B3");
        materialComboBox.addItem("Sand - 1.60g/cm" + "\u00B3");
        materialComboBox.addItem("Lemon - 1.02g/cm" + "\u00B3");
        materialComboBox.setBackground(new Color(40, 40, 40));
        materialComboBox.setForeground(Color.WHITE);
        materialComboBox.setFocusable(false);
        materialComboBox.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        materialPanel.add(materialComboBox);

        // DATA PANEL

        dataPanel = new DataPanel();
        dataPanel.setPreferredSize(new Dimension(220, 510));
        this.add(dataPanel, BorderLayout.WEST);
        
        // SIMULATION PANEL

        simulationPanel = new SimulationPanel(this, dataPanel);
        simulationPanel.setPreferredSize(new Dimension(100, 510));
        this.add(simulationPanel, BorderLayout.CENTER);

        x = 100;
        y = 510 - GROUND_LEVEL - size - 20;
    }

    public int getMass()
    {
        return this.mass;
    }

    public double getVelocity()
    {
        return this.velocity;
    }

    public double getVX()
    {
        return this.vx;
    }

    public double getVY()
    {
        return this.vy;
    }

    public double getObjectX()
    {
        return this.x;
    }

    public double getObjectY()
    {
        return this.y;
    }

    public double getDirection()
    {
        return this.direction;
    }

    public double getDensity()
    {
        return this.density;
    }

    public double getImpulse()
    {
        return this.impulse;
    }

    public double getAverageForce()
    {
        return this.averageForce;
    }

    private class StartButtonActionListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            mass = massSlider.getValue();
            size = (int) (1.5 * mass);
            x = 100;
            y = simulationPanel.getHeight() - GROUND_LEVEL - size - 20;
            previousVelocity = velocitySlider.getValue();
            velocity = previousVelocity;
            direction = directionSlider.getValue();
            vx = velocity * Math.cos(Math.toRadians(direction));
            vy = -velocity * Math.sin(Math.toRadians(direction));
            selectedMaterial = materialComboBox.getSelectedIndex();
            density = densityList[selectedMaterial];

            dataPanel.updateData(MainPanel.this, simulationPanel);

            simulationPanel.startSim();
        }
    }
}