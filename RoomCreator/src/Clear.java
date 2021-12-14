import javax.swing.*;

public class Clear extends JButton {

    public Clear(int x, int y, String name) {
        this.setText(name);
        this.setBounds(x, y, 50, 20);
        this.addActionListener(new ClearListener());
    }

    public JPanel getContentPane()
    {
        return (JPanel) this.getRootPane().getContentPane();
    }

}
