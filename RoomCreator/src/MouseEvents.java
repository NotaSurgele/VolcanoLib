import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseEvents extends MouseAdapter {

    public boolean isPressed = false;

    public MouseEvents() {}

    @Override
    public void mousePressed(MouseEvent e) {
        super.mouseClicked(e);

        isPressed = true;
        if (RoomCreator.startRect.getX() == -404)
            RoomCreator.startRect.set(e.getX() - (e.getX() % RoomCreator.TILESIZE), e.getY() - (e.getY() % RoomCreator.TILESIZE) - 32);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseEntered(e);
        isPressed = false;
        RoomCreator.startRect.set(-404, -404);
        RoomCreator.endRect.set(0,0);
    }

}
