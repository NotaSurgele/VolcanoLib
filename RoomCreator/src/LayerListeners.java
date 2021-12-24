import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LayerListeners implements ActionListener {

    Layer layer;
    String name;

    public LayerListeners(Layer layer, String name) {
        this.layer = layer;
        this.name = name;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        RoomCreator.selectedLayer.setText(name);
        RoomCreator.brush.setLayer(layer);
    }
}
