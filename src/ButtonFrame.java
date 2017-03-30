import java.awt.*;
import javax.swing.*;

public class ButtonFrame extends JFrame {
    private PieceIcon squareBase;
    private PieceIcon elements[] = new PieceIcon[64];
    private int counter = 0;

    public ButtonFrame(String title)
    {
        super(title);
        setLayout(new GridLayout(8, 8));

        for(int row=0; row<8; row++) {
            for(int col=0; col<8; col++) {
                if(row%2==0) { // Row 1, Row 3, Row 5, Row 7
                    if(col%2==0) {
                        squareBase = new PieceIcon(PieceIcon.BEIGE_BASE);
                        initialize();
                        add(squareBase);
                        elements[counter] = squareBase;
                    }
                    else {
                        squareBase = new PieceIcon(PieceIcon.RED_BASE);
                        initialize();
                        add(squareBase);
                        elements[counter] = squareBase;
                    }
                }
                else { // Row 0, Row 2, Row 4, Row 6
                    if(col%2==0) {
                        squareBase = new PieceIcon(PieceIcon.RED_BASE);
                        initialize();
                        add(squareBase);
                        elements[counter] = squareBase;
                    }
                    else {
                        squareBase = new PieceIcon(PieceIcon.BEIGE_BASE);
                        initialize();
                        add(squareBase);
                        elements[counter] = squareBase;
                    }
                }
                counter++;
            }
        }
    }

    public PieceIcon getPiece(int index) {
        return elements[index];
    }

    public void movePiece(int oldIndex, int newIndex) {
        PieceIcon oldPieceIcon = elements[oldIndex];
        int pieceToMove = oldPieceIcon.getPieceValue();
        oldPieceIcon.setPiece(PieceIcon.EMPTY);
        elements[oldIndex] = oldPieceIcon;
        PieceIcon newPieceIcon = elements[newIndex];
        newPieceIcon.setPiece(pieceToMove);
        elements[newIndex] = newPieceIcon;
    }

    public void initialize() {
        if(counter==0 || counter==7) { squareBase.setPiece(PieceIcon.BLACK_ROOK); }
        if(counter==56 || counter==63) { squareBase.setPiece(PieceIcon.WHITE_ROOK); }
        if(counter==1 || counter==6) { squareBase.setPiece(PieceIcon.BLACK_KNIGHT); }
        if(counter==57 || counter==62) { squareBase.setPiece(PieceIcon.WHITE_KNIGHT); }
        if(counter==2 || counter==5) { squareBase.setPiece(PieceIcon.BLACK_BISHOP); }
        if(counter==58 || counter==61) { squareBase.setPiece(PieceIcon.WHITE_BISHOP); }
        if(counter==3) { squareBase.setPiece(PieceIcon.BLACK_QUEEN); }
        if(counter==59) { squareBase.setPiece(PieceIcon.WHITE_QUEEN); }
        if(counter==4) { squareBase.setPiece(PieceIcon.BLACK_KING); }
        if(counter==60) { squareBase.setPiece(PieceIcon.WHITE_KING); }
        if((counter>7 && counter<16)) { squareBase.setPiece(PieceIcon.BLACK_PAWN); }
        if(counter>47 && counter<56) { squareBase.setPiece(PieceIcon.WHITE_PAWN); }
    }
}
