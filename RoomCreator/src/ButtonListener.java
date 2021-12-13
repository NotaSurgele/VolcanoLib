import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class ButtonListener implements ActionListener {

    BufferedImage i;

    public ButtonListener(BufferedImage img) {
        this.i = img;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        RoomCreator.brush.setTexture(this.i);
    }
}
