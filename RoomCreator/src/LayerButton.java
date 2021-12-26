import javax.swing.*;
import java.awt.*;

public class LayerButton extends JButton {

    Layer layer;
    Color color;

    public LayerButton(String name, int x, int y, Color c)
    {
        this.layer = new Layer();
        this.setText(name);
        this.setBounds(x, y, 50, 20);
        this.color = c;
        this.addActionListener(new LayerListeners(layer, name, color));
    }

    public int[][] getLayer()
    {
        return layer.getLayer();
    }

    public void setValue(int x, int y, int value)
    {
        this.layer.setValue(x, y, value);
    }

    public Color getColor()
    {
        return this.color;
    }

    public void showLayer()
    {
        this.layer.showLayer();
    }

    public void clear()
    {
        this.layer.clear();
    }
}