import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseEvents extends MouseAdapter {

    public MouseEvents() {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);

        Brush b = RoomCreator.brush;
        b.drawTexture(e);
    }
}
