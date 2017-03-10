import java.awt.*;
import javax.swing.*;

public class Test {
    private ButtonFrame theButtonFrame;

    public Test() {
      theButtonFrame = new ButtonFrame("Test");
      theButtonFrame.pack();
      theButtonFrame.setLocationRelativeTo(null);
      theButtonFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      theButtonFrame.setVisible(true);
    }

    public static void main(String[] args) {
      javax.swing.SwingUtilities.invokeLater(new Runnable() {
        @Override public void run() {
          new Test();
        }
      });
    }
}
