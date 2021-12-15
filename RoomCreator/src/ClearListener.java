import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClearListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        Clear c = (Clear) e.getSource();

        ((LayerButton) c.getContentPane().getComponentAt(832, 400)).clear();
        ((LayerButton) c.getContentPane().getComponentAt(832, 432)).clear();

        c.getContentPane().repaint();
    }
}
