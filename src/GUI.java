import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.imageio.ImageIO;

public class GUI extends JFrame implements ActionListener {
  private JFrame frame;
  private JPanel mainPanel;
  private Color white;
  private Color black;
  private JButton[][] board;
  private JMenuItem moveItem;

  private Icon redblank;
  private Icon whiteblank;

  
  public ArrayList<Integer> getBoard(){
      return new ArrayList<Integer>();
  }


  public GUI(){
  }

  public void setup() {
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
