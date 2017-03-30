import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class GUI implements ActionListener {
    private ButtonFrame theButtonFrame;
    private JMenuItem moveItem;

    public GUI() {
        theButtonFrame = new ButtonFrame("SYSC3010 T2 - MeAndMyChess");
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
                theButtonFrame.movePiece(1, 16);
            }
        }
    }

    public ArrayList<Integer> getBoard() {
        return new ArrayList<Integer>();
    }

    public static void main(String[] args) {
      javax.swing.SwingUtilities.invokeLater(new Runnable() {
        @Override public void run() {
          new GUI();
        }
      });
    }
}
