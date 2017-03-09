import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.imageio.ImageIO;

/**
  * A class to create a GUI.
  *
  * @author Ricky Dam
  * @version March 5, 2017
  *
  */
public class GUI extends JFrame implements ActionListener {
  private JFrame frame;
  private JPanel mainPanel;
  private Color white;
  private Color black;
  private JButton[][] board;
  private JMenuItem moveItem;

  private Icon redblank;
  private Icon whiteblank;
  /**
   * Constructs the GUI.
   */
  public GUI() {
    frame = new JFrame("SYSC3010 T2 - MeAndMyChess");
    frame.setPreferredSize(new Dimension(800, 800));
    Container contentPane = frame.getContentPane();
    contentPane.setLayout(new BorderLayout());

    mainPanel = new JPanel();
    white = new Color(255, 255, 255);
    black = new Color(0, 0, 0);
    mainPanel.setBackground(white);
    mainPanel.setLayout(new GridLayout(8, 8));

    board = new JButton[8][8];
    int counter = 0;

    for(int row=0; row<8; row++) {
      for(int col=0; col<8; col++) {
        board[row][col] = new JButton();
        board[row][col].setEnabled(false);

        /////////////////// isRook ///////////////////
        if(counter==0 || counter==7 || counter==56 || counter==63) {

        }
        else {

        }

        /////////////////// isKnight ///////////////////
        if(counter==1 || counter==6 || counter==57 || counter==62) {

        }
        else {

        }

        /////////////////// isBishop ///////////////////
        if(counter==2 || counter==5 || counter==58 || counter==61) {

        }
        else {
          ;
        }

        /////////////////// isQueen ///////////////////
        if(counter==3 || counter==59) {

        }
        else {

        }

        /////////////////// isKing ///////////////////
        if(counter==4 || counter==60) {

        }
        else {

        }

        /////////////////// isPawn ///////////////////
        if((counter>8 && counter<16) || (counter>47 && counter<56)) {

        }
        else {

        }

        /////////////////// isBlack ///////////////////
        if(counter<16) {

        }
        else {

        }

        /////////////////// isWhite ///////////////////
        if(counter>47) {

        }
        else {

        }

        if(row%2==0) { // Row 1, Row 3, Row 5, Row 7
          if(col%2==0) {
            try {
              Image img = ImageIO.read(getClass().getResource("pieces/white.jpg"));
              Image resizedimg = img.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
              board[row][col].setIcon(new ImageIcon(resizedimg));
              board[row][col].setDisabledIcon(new ImageIcon(resizedimg));
            } catch (Exception e) {
              System.out.println(e);
            }
          }
          else {
            try {
              Image img = ImageIO.read(getClass().getResource("pieces/red.jpg"));
              Image resizedimg = img.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
              board[row][col].setIcon(new ImageIcon(resizedimg));
              board[row][col].setDisabledIcon(new ImageIcon(resizedimg));
            } catch (Exception e) {
              System.out.println(e);
            }
          }
        }
        else { // Row 0, Row 2, Row 4, Row 6
          if(col%2==0) {
            try {
              Image img = ImageIO.read(getClass().getResource("pieces/red.jpg"));
              Image resizedimg = img.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
              board[row][col].setIcon(new ImageIcon(resizedimg));
              board[row][col].setDisabledIcon(new ImageIcon(resizedimg));
            } catch (Exception e) {
              System.out.println(e);
            }
          }
          else {
            try {
              Image img = ImageIO.read(getClass().getResource("pieces/white.jpg"));
              Image resizedimg = img.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
              board[row][col].setIcon(new ImageIcon(resizedimg));
              board[row][col].setDisabledIcon(new ImageIcon(resizedimg));
            } catch (Exception e) {
              System.out.println(e);
            }
          }
        }
        mainPanel.add(board[row][col]);
      }
    }

    contentPane.add(mainPanel, BorderLayout.CENTER);

    JMenuBar menuBar = new JMenuBar();
    frame.setJMenuBar(menuBar);
    JMenu fileMenu = new JMenu("File");
    fileMenu.setFont(new Font(null, Font.BOLD, 30));

    moveItem = new JMenuItem("Move");
    moveItem.setFont(new Font(null, Font.BOLD, 25));
    fileMenu.add(moveItem);
    moveItem.addActionListener(this);

    menuBar.add(fileMenu);

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setResizable(false);
    frame.setVisible(true);
  }

  public Icon makeIcon(Image img) {
    Image newimg = img.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
    return new ImageIcon(newimg);
  }

  /**
   * Move a piece.
   */
  public void move(int oldrow, int oldcol, int newrow, int newcol) {
  }
  /*
  public void movePiece(int oldrow, int oldcol, int newrow, int newcol, Icon piece) {
    board[newrow][newcol].setIcon(piece);
    board[newrow][newcol].setDisabledIcon(piece);
    board[oldrow][oldcol].setIcon(blanksquare);
    board[oldrow][oldcol].setDisabledIcon(blanksquare);
  }
  */
  /**
   * Checks for action performed by the user.
   */
  public void actionPerformed(ActionEvent event) {
    Object object = event.getSource();
    if(object instanceof JMenuItem) {
      JMenuItem menuitem = (JMenuItem) object;
      if(menuitem == moveItem) {
        move(1, 4, 2, 4);
      }
    }
  }

  /**
   * The main function that runs the GUI.
   */
  public static void main(String[] args) {
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      @Override public void run() {
        new GUI();
      }
    });
  }
}
