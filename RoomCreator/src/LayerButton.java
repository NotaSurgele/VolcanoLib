import javax.swing.*;

public class LayerButton extends JButton {

    Layer layer;

    public LayerButton(String name, int x, int y)
    {
        this.layer = new Layer();
        this.setText(name);
        this.setBounds(x, y, 50, 20);
        this.addActionListener(new LayerListeners(layer));
    }

    public int[][] getLayer()
    {
        return layer.getLayer();
    }

    public void setValue(int x, int y, int value)
    {
        this.layer.setValue(x, y, value);
    }
}