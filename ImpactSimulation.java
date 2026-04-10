import javax.swing.JFrame;

public class ImpactSimulation
{
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Impact Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        MainPanel panel = new MainPanel();
        frame.getContentPane().add(panel);

        frame.pack();
        frame.setVisible(true);
    }
}