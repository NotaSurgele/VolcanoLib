import java.awt.*;
import java.io.IOException;
import javax.swing.*;

public class RoomCreator {

    static final int WIDTH = 900;
    static final int HEIGHT = 900;
    static final int TILESIZE = 16;

    static final int ROOMWIDTH = 784;
    static final int ROOMHEIGHT = 784;

    static Vector2i startRect;
    static Vector2i endRect;

    public static Brush brush;

    static MouseEvents mouse;

    JFrame frame = new JFrame();

    public RoomCreator() throws IOException {

        setJFrame();

        //Button
        setButton();

        //Main method
        update();
    }

    public void setJFrame()
    {
        startRect = new Vector2i(-404, -404);
        endRect = new Vector2i(0, 0);
        mouse = new MouseEvents();
        frame.setContentPane(new Grid());
        brush = new Brush((JPanel) frame.getContentPane());
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.addMouseListener(mouse);
    }

    public void setButton() throws IOException {

        LayerButton collision = new LayerButton("C", 832, 400);
        LayerButton noCollision = new LayerButton("NC", 832, 432);

        brush.setLayer(noCollision.layer);
        Clear clear = new Clear(832, 480, "CL");

        frame.getContentPane().add(collision);
        frame.getContentPane().add(noCollision);
        frame.getContentPane().add(clear);


        TexturedButton floor = new TexturedButton(32, 832, "assets/floor_1.png", 1);
        TexturedButton floor2 = new TexturedButton(64, 832, "assets/floor_2.png", 2);

        frame.getContentPane().add(floor);
        frame.getContentPane().add(floor2);
    }

    public void update()
    {
        while (true) {

            int marginX = frame.getLocation().x;
            int marginY = frame.getLocation().y;

            int x = MouseInfo.getPointerInfo().getLocation().x - marginX;
            int y = MouseInfo.getPointerInfo().getLocation().y - marginY;

            endRect.set(x - (x % TILESIZE), y - (y % TILESIZE) - 32);
            if (mouse.isPressed)  brush.drawTexture(startRect, endRect);

        }
    }

    public static void main(String[] args) throws IOException {
        new RoomCreator();
    }

}
