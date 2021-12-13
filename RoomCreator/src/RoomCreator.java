import java.awt.*;
import java.io.IOException;
import javax.swing.*;

public class RoomCreator {

    static final int WIDTH = 900;
    static final int HEIGHT = 900;
    static final int TILESIZE = 16;

    static final int ROOMWIDTH = 800;
    static final int ROOMHEIGHT = 800;

    static Vector2i startRect;
    static Vector2i endRect;

    public static Brush brush;

    TexturedButton floor;
    static MouseEvents mouse;
    JFrame frame = new JFrame();

    public RoomCreator() throws IOException {

        startRect = new Vector2i(-404, -404);
        endRect = new Vector2i(0, 0);
        mouse = new MouseEvents();
        frame.setContentPane(new Grid());
        brush = new Brush((JPanel) frame.getContentPane());
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.addMouseListener(mouse);

        //Button
        floor = new TexturedButton(32, 832, "assets/floor_1.png");
        frame.getContentPane().add(floor);

        while (true) {
            int x = MouseInfo.getPointerInfo().getLocation().x;
            int y = MouseInfo.getPointerInfo().getLocation().y;

            endRect.set(x - (x % TILESIZE), y - (y % TILESIZE) - 32);
            if (mouse.isPressed)
                brush.drawTexture(startRect, endRect);
        }
    }

    public static void main(String[] args) throws IOException {
        new RoomCreator();
    }

}
