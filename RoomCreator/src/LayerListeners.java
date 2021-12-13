import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LayerListeners implements ActionListener {

    Layer layer;

    public LayerListeners(Layer layer) {
        this.layer = layer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        RoomCreator.brush.setLayer(layer);
    }
}
