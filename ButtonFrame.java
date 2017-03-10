import java.awt.*;
import javax.swing.*;

public class ButtonFrame extends JFrame {
    private JButton bChange;
    private ButtonFrame frm;

    ButtonFrame(String title)
    {
        super(title);

        setLayout(new FlowLayout());

        bChange = new PieceIcon(PieceIcon.RED_BASE);
        add(bChange);
    }
}
