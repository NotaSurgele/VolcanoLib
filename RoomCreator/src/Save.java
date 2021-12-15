import javax.swing.*;

public class Save extends JButton {

    public Save(int x, int y, String name)
    {
        this.setBounds(x, y, 70, 20);
        this.setText(name);
        this.addActionListener(new SaveListener());
    }

    public JPanel getContentPane()
    {
        return (JPanel) this.getRootPane().getContentPane();
    }

}
