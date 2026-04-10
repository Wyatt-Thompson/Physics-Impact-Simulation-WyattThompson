import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class SliderLabel extends JLabel
{
    public SliderLabel(int key)
    {
        this.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        this.setForeground(Color.WHITE);
        this.setText(String.valueOf(key));
    }
}
