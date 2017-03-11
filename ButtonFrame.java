import java.awt.*;
import javax.swing.*;

public class ButtonFrame extends JFrame {
    private PieceIcon bChange;
    private ButtonFrame frm;

    ButtonFrame(String title)
    {
        super(title);

        setLayout(new FlowLayout());

        bChange = new PieceIcon(PieceIcon.RED_BASE);
        bChange.setPiece(PieceIcon.BLACK_PAWN);
        add(bChange);
    }
}
