import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Test implements ActionListener {
    private ButtonFrame theButtonFrame;
    private JMenuItem moveItem;

    public Test() {
        theButtonFrame = new ButtonFrame("Test");
        theButtonFrame.setPreferredSize(new Dimension(800, 800));

        JMenuBar menuBar = new JMenuBar();
        theButtonFrame.setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("File");
        fileMenu.setFont(new Font(null, Font.BOLD, 20));

        moveItem = new JMenuItem("Move");
        moveItem.setFont(new Font(null, Font.BOLD, 15));
        fileMenu.add(moveItem);
        moveItem.addActionListener(this);

        menuBar.add(fileMenu);

        theButtonFrame.pack();
        theButtonFrame.setLocationRelativeTo(null);
        theButtonFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        theButtonFrame.setVisible(true);
        theButtonFrame.setResizable(false);
    }

    public void actionPerformed(ActionEvent event) {
        Object object = event.getSource();
        if(object instanceof JMenuItem) {
            JMenuItem menuitem = (JMenuItem) object;
            if(menuitem == moveItem) {
                // move(1, 4, 2, 4);
            }
        }
    }

    public static void main(String[] args) {
      javax.swing.SwingUtilities.invokeLater(new Runnable() {
        @Override public void run() {
          new Test();
        }
      });
    }
}
