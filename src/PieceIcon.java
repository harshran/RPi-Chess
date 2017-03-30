import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.imageio.ImageIO;
import java.awt.image.*;
import java.io.*;

public class PieceIcon extends JButton {

    public static final int EMPTY = 0;
    public static final int WHITE_PAWN = 1;
    public static final int WHITE_ROOK = 2;
    public static final int WHITE_KNIGHT = 3;
    public static final int WHITE_BISHOP = 4;
    public static final int WHITE_QUEEN = 5;
    public static final int WHITE_KING = 6;
    public static final int BLACK_PAWN = 7;
    public static final int BLACK_ROOK = 8;
    public static final int BLACK_KNIGHT = 9;
    public static final int BLACK_BISHOP = 10;
    public static final int BLACK_QUEEN = 11;
    public static final int BLACK_KING = 12;

    public static final int RED_BASE = 20;
    public static final int BEIGE_BASE = 21;

    private int piece_value;
    private int base_value;
    private Image base_color;

    public PieceIcon(int new_base_value) {
        super();
        this.setEnabled(false);

        base_value = new_base_value;
        piece_value = PieceIcon.EMPTY;

        // Load background base color
        try {
            if(base_value == PieceIcon.RED_BASE) {
                base_color = ImageIO.read(new FileInputStream(new File("pieces/red.jpg")));
            } else if(base_value == PieceIcon.BEIGE_BASE) {
                base_color = ImageIO.read(new FileInputStream(new File("pieces/white.jpg")));
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        if(base_color == null) {
            System.out.println("Exists");
        }
            // Set button icon to background color
            Image resized_img = base_color.getScaledInstance(100,100,java.awt.Image.SCALE_SMOOTH);
            this.setIcon(new ImageIcon(resized_img));
            this.setDisabledIcon(new ImageIcon(resized_img));
    }

    public void setPiece(int new_piece_value) {
        piece_value = new_piece_value;
        BufferedImage piece;

        // Load piece image
        try {
            switch(new_piece_value) {
                case EMPTY: piece = ImageIO.read(getClass().getResource("pieces_no_background/whitepawn.png")); break;
                case WHITE_PAWN: piece = ImageIO.read(getClass().getResource("pieces_no_background/whitepawn.png")); break;
                case WHITE_ROOK: piece = ImageIO.read(getClass().getResource("pieces_no_background/whiterook.png")); break;
                case WHITE_KNIGHT: piece = ImageIO.read(getClass().getResource("pieces_no_background/whiteknight.png")); break;
                case WHITE_BISHOP: piece = ImageIO.read(getClass().getResource("pieces_no_background/whitebishop.png")); break;
                case WHITE_QUEEN: piece = ImageIO.read(getClass().getResource("pieces_no_background/whitequeen.png")); break;
                case WHITE_KING: piece = ImageIO.read(getClass().getResource("pieces_no_background/whiteking.png")); break;
                case BLACK_PAWN: piece = ImageIO.read(getClass().getResource("pieces_no_background/blackpawn.png")); break;
                case BLACK_ROOK: piece = ImageIO.read(getClass().getResource("pieces_no_background/blackrook.png")); break;
                case BLACK_KNIGHT: piece = ImageIO.read(getClass().getResource("pieces_no_background/blackknight.png")); break;
                case BLACK_BISHOP: piece = ImageIO.read(getClass().getResource("pieces_no_background/blackbishop.png")); break;
                case BLACK_QUEEN: piece = ImageIO.read(getClass().getResource("pieces_no_background/blackqueen.png")); break;
                case BLACK_KING: piece = ImageIO.read(getClass().getResource("pieces_no_background/blackking.png")); break;
                default: piece = ImageIO.read(getClass().getResource("pieces_no_background/blackking.png"));
            }

            // Overlay images
            BufferedImage combined = new BufferedImage(piece.getWidth(), piece.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics g = combined.getGraphics();
            g.drawImage(base_color, 0, 0, null);
            if(new_piece_value != PieceIcon.EMPTY) {
                g.drawImage(piece, 0, 0, null);
            }

            // Set Icon
            ImageIcon icon = new ImageIcon(resize(combined, 100, 100));
            this.setIcon(icon);
            this.setDisabledIcon(icon);

        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public int getPieceValue() {
        return piece_value;
    }

    public int getBaseValue() {
        return base_value;
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return dimg;
    }
}
