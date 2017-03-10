import java.awt.*;
import javax.swing.*;

class ButtonFrame extends JFrame{
    JButton bChange;

    ButtonFrame(String title)
    {
        super (title);
        setLayout(new FlowLayout());

        bChange = new PieceIcon(PieceIcon.RED_BASE);
        add(bChange);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

public class Test
{
    public static void main (String[] args)
    {
        ButtonFrame frm = new ButtonFrame("Button Demo");

        frm.setSize(150, 150);
        frm.setVisible(true);
    }
}

