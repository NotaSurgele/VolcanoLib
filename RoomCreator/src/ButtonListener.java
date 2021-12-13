import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class ButtonListener implements ActionListener {

    BufferedImage i;
    int value;

    public ButtonListener(BufferedImage img, int value) {
        this.i = img;
        this.value = value;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        RoomCreator.brush.setTexture(this.i);
        RoomCreator.brush.setValue(value);
    }
}
