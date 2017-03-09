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
  private boolean[][] blockcolour;
  private boolean[][] occupied;
  private JMenuItem moveItem;

  private Icon blackkingred;
  private Icon blackkingwhite;
  private Icon blackqueenred;
  private Icon blackqueenwhite;
  private Icon blackrookred;
  private Icon blackrookwhite;
  private Icon blackbishopred;
  private Icon blackbishopwhite;
  private Icon blackknightred;
  private Icon blackknightwhite;
  private Icon blackpawnred;
  private Icon blackpawnwhite;

  private Icon whitekingred;
  private Icon whitekingwhite;
  private Icon whitequeenred;
  private Icon whitequeenwhite;
  private Icon whiterookred;
  private Icon whiterookwhite;
  private Icon whitebishopred;
  private Icon whitebishopwhite;
  private Icon whiteknightred;
  private Icon whiteknightwhite;
  private Icon whitepawnred;
  private Icon whitepawnwhite;

  private Icon redblank;
  private Icon whiteblank;

  private boolean[][] isRook;
  private boolean[][] isKnight;
  private boolean[][] isBishop;
  private boolean[][] isQueen;
  private boolean[][] isKing;
  private boolean[][] isPawn;
  private boolean[][] isBlack;
  private boolean[][] isWhite;
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
    blockcolour = new boolean[8][8];
    boolean white = true;
    boolean red = false;
    occupied = new boolean[8][8];
    isRook = new boolean[8][8];
    isKnight = new boolean[8][8];
    isBishop = new boolean[8][8];
    isQueen = new boolean[8][8];
    isKing = new boolean[8][8];
    isPawn = new boolean[8][8];
    isBlack = new boolean[8][8];
    isWhite = new boolean[8][8];

    for(int row=0; row<8; row++) {
      for(int col=0; col<8; col++) {
        board[row][col] = new JButton();
        board[row][col].setEnabled(false);

        /////////////////// isRook ///////////////////
        if(counter==0 || counter==7 || counter==56 || counter==63) {
          isRook[row][col] = true;
        }
        else {
          isRook[row][col] = false;
        }

        /////////////////// isKnight ///////////////////
        if(counter==1 || counter==6 || counter==57 || counter==62) {
          isKnight[row][col] = true;
        }
        else {
          isKnight[row][col] = false;
        }

        /////////////////// isBishop ///////////////////
        if(counter==2 || counter==5 || counter==58 || counter==61) {
          isBishop[row][col] = true;
        }
        else {
          isBishop[row][col] = false;
        }

        /////////////////// isQueen ///////////////////
        if(counter==3 || counter==59) {
          isQueen[row][col] = true;
        }
        else {
          isQueen[row][col] = false;
        }

        /////////////////// isKing ///////////////////
        if(counter==4 || counter==60) {
          isKing[row][col] = true;
        }
        else {
          isKing[row][col] = false;
        }

        /////////////////// isPawn ///////////////////
        if((counter>8 && counter<16) || (counter>47 && counter<56)) {
          isPawn[row][col] = true;
        }
        else {
          isPawn[row][col] = false;
        }

        /////////////////// isBlack ///////////////////
        if(counter<16) {
          isBlack[row][col] = true;
        }
        else {
          isBlack[row][col] = false;
        }

        /////////////////// isWhite ///////////////////
        if(counter>47) {
          isWhite[row][col] = true;
        }
        else {
          isWhite[row][col] = false;
        }

        /////////////////// OCCUPIED ///////////////////
        if(counter<16 || counter>48) {
          occupied[row][col] = true;
        }
        else {
          occupied[row][col] = false;
        }

        /////////////////// BLOCK COLOUR ///////////////////
        if(row%2==0) { // Row 2, Row 4, Row 6, Row 8
          if(col%2==0) {
            blockcolour[row][col] = white;
          }
          else {
            blockcolour[row][col] = red;
          }
        }
        else { // Row 1, Row 3, Row 5, Row 7
          if(col%2==0) {
            blockcolour[row][col] = red;
          }
          else {
            blockcolour[row][col] = white;
          }
        }
        // System.out.println("Block " + counter + ": " + blockcolour[row][col]);
        /////////////////// BLOCK COLOUR ///////////////////

        try {
          int temp = counter+1;
          Image img = ImageIO.read(getClass().getResource("original/" + temp + ".jpg"));
          Image newimg = img.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
          board[row][col].setIcon(new ImageIcon(newimg));
          board[row][col].setDisabledIcon(new ImageIcon(newimg));
          counter++;
        } catch (Exception e) {
          System.out.println(e);
        }
        mainPanel.add(board[row][col]);
      }
    }

    try {
      blackkingred = makeIcon(ImageIO.read(getClass().getResource("pieces/blackkingred.jpg")));
      blackkingwhite = makeIcon(ImageIO.read(getClass().getResource("pieces/blackkingwhite.jpg")));
      blackqueenred = makeIcon(ImageIO.read(getClass().getResource("pieces/blackqueenred.jpg")));
      blackqueenwhite = makeIcon(ImageIO.read(getClass().getResource("pieces/blackqueenwhite.jpg")));
      blackrookred = makeIcon(ImageIO.read(getClass().getResource("pieces/blackrookred.jpg")));
      blackrookwhite = makeIcon(ImageIO.read(getClass().getResource("pieces/blackrookwhite.jpg")));
      blackbishopred = makeIcon(ImageIO.read(getClass().getResource("pieces/blackbishopred.jpg")));
      blackbishopwhite = makeIcon(ImageIO.read(getClass().getResource("pieces/blackbishopwhite.jpg")));
      blackknightred = makeIcon(ImageIO.read(getClass().getResource("pieces/blackknightred.jpg")));
      blackknightwhite = makeIcon(ImageIO.read(getClass().getResource("pieces/blackknightwhite.jpg")));
      blackpawnred = makeIcon(ImageIO.read(getClass().getResource("pieces/blackpawnred.jpg")));
      blackpawnwhite = makeIcon(ImageIO.read(getClass().getResource("pieces/blackpawnwhite.jpg")));

      whitekingred = makeIcon(ImageIO.read(getClass().getResource("pieces/whitekingred.jpg")));
      whitekingwhite = makeIcon(ImageIO.read(getClass().getResource("pieces/whitekingwhite.jpg")));
      whitequeenred = makeIcon(ImageIO.read(getClass().getResource("pieces/whitequeenred.jpg")));
      whitequeenwhite = makeIcon(ImageIO.read(getClass().getResource("pieces/whitequeenwhite.jpg")));
      whiterookred = makeIcon(ImageIO.read(getClass().getResource("pieces/whiterookred.jpg")));
      whiterookwhite = makeIcon(ImageIO.read(getClass().getResource("pieces/whiterookwhite.jpg")));
      whitebishopred = makeIcon(ImageIO.read(getClass().getResource("pieces/whitebishopred.jpg")));;
      whitebishopwhite = makeIcon(ImageIO.read(getClass().getResource("pieces/whitebishopwhite.jpg")));
      whiteknightred = makeIcon(ImageIO.read(getClass().getResource("pieces/whiteknightred.jpg")));
      whiteknightwhite = makeIcon(ImageIO.read(getClass().getResource("pieces/whiteknightwhite.jpg")));
      whitepawnred = makeIcon(ImageIO.read(getClass().getResource("pieces/whitepawnred.jpg")));
      whitepawnwhite = makeIcon(ImageIO.read(getClass().getResource("pieces/whitepawnwhite.jpg")));

      redblank = makeIcon(ImageIO.read(getClass().getResource("pieces/red.jpg")));
      whiteblank = makeIcon(ImageIO.read(getClass().getResource("pieces/white.jpg")));
    } catch(Exception e) {
      System.out.println(e);
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
    if(isPawn[oldrow][oldcol]) { // A pawn
      if(isWhite[oldrow][oldcol]) { // A white pawn
        if(blockcolour[oldrow][oldcol]) { // A white pawn on white square
          if(blockcolour[oldrow][oldcol] == blockcolour[newrow][newcol]) { // Same square colour
            System.out.println("Case 1: A white pawn on white square going to white square.");
            Icon piece = board[oldrow][oldcol].getIcon(); // Keep the same piece
            movePiece(oldrow, oldcol, newrow, newcol, piece, whiteblank);
          }
          else {
            System.out.println("Case 2: A white pawn on white square going to red square.");
            Icon piece = whitepawnred;
            movePiece(oldrow, oldcol, newrow, newcol, piece, whiteblank);
          }
        }
        else { // A white pawn on red square.
          if(blockcolour[oldrow][oldcol] == blockcolour[newrow][newcol]) { // Same square colour
            System.out.println("Case 3: A white pawn on red square going to red square.");
            Icon piece = board[oldrow][oldcol].getIcon(); // Keep the same piece
            movePiece(oldrow, oldcol, newrow, newcol, piece, redblank);
          }
          else {
            System.out.println("Case 4: A white pawn on red square going to white square.");
            Icon piece = whitepawnwhite;
            movePiece(oldrow, oldcol, newrow, newcol, piece, redblank);
          }
        }
      }
      else if(isBlack[oldrow][oldcol]) { // A black pawn.
        if(blockcolour[oldrow][oldcol]) { // A black pawn on white square
          if(blockcolour[oldrow][oldcol] == blockcolour[newrow][newcol]) { // Same square colour
            System.out.println("Case 5: A black pawn on white square going to white square.");
            Icon piece = board[oldrow][oldcol].getIcon(); // Keep the same piece
            movePiece(oldrow, oldcol, newrow, newcol, piece, whiteblank);
          }
          else {
            System.out.println("Case 6: A black pawn on white square going to red square.");
            Icon piece = blackpawnred;
            movePiece(oldrow, oldcol, newrow, newcol, piece, whiteblank);
          }
        }
        else { // A black pawn on red square
          if(blockcolour[oldrow][oldcol] == blockcolour[newrow][newcol]) { // Same square colour
            System.out.println("Case 7: A black pawn on red square going to red square.");
            Icon piece = board[oldrow][oldcol].getIcon(); // Keep the same piece
            movePiece(oldrow, oldcol, newrow, newcol, piece, redblank);
          }
          else {
            System.out.println("Case 8: A black pawn on red square going to white square.");
            Icon piece = blackpawnwhite;
            movePiece(oldrow, oldcol, newrow, newcol, piece, redblank);
          }
        }
      }
      else {
        System.out.println("IT SHOULD NEVER GET HERE");
      }
    }
    else { // not a pawn

    }


  }

  public void movePiece(int oldrow, int oldcol, int newrow, int newcol, Icon piece, Icon blanksquare) {
    board[newrow][newcol].setIcon(piece);
    board[newrow][newcol].setDisabledIcon(piece);
    board[oldrow][oldcol].setIcon(blanksquare);
    board[oldrow][oldcol].setDisabledIcon(blanksquare);
  }

  public void test() {
    move(1, 4, 2, 4);
  }

  /**
   * Checks for action performed by the user.
   */
  public void actionPerformed(ActionEvent event) {
    Object object = event.getSource();
    if(object instanceof JMenuItem) {
      JMenuItem menuitem = (JMenuItem) object;
      if(menuitem == moveItem) {
        test();
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
