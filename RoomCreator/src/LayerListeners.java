import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LayerListeners implements ActionListener {

    Layer layer;
    String name;
    Color color;

    public LayerListeners(Layer layer, String name, Color c) {
        this.layer = layer;
        this.name = name;
        this.color = c;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        RoomCreator.selectedLayer.setText(name);
        RoomCreator.brush.setLayer(layer);
        RoomCreator.brush.setColor(this.color);
    }
}
