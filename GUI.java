import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
  * A class to create a GUI.
  *
  * @author Ricky Dam
  * @version March 5, 2017
  *
  */
public class GUI extends JFrame implements ActionListener {
  private JFrame frame;

  /**
   * Constructs the GUI.
   */
  public GUI() {
    frame = new JFrame("SYSC3010 T2 - MeAndMyChess");
    frame.setPreferredSize(new Dimension(500, 500));
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setResizable(true);
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
