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
    int counter = 1;
    for(int col=0; col<8; col++) {
      for(int row=0; row<8; row++) {
        board[row][col] = new JButton();
        board[row][col].setEnabled(false);
        try {
          Image img = ImageIO.read(getClass().getResource("original/" + counter + ".jpg"));
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

    contentPane.add(mainPanel, BorderLayout.CENTER);

    JMenuBar menuBar = new JMenuBar();
    frame.setJMenuBar(menuBar);
    JMenu fileMenu = new JMenu("File");
    fileMenu.setFont(new Font(null, Font.BOLD, 15));
    menuBar.add(fileMenu);

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setResizable(false);
    frame.setVisible(true);
  }

  /**
   * Checks for action performed by the user.
   */
  public void actionPerformed(ActionEvent event) {

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
