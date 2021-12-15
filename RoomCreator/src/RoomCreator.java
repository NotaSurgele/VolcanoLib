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

    static JLabel selectedLayer = new JLabel("NC");

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
        frame.setLayout(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.addMouseListener(mouse);
        selectedLayer.setBounds(840, 300, 100, 20);
        selectedLayer.setForeground(Color.white);
        frame.add(selectedLayer);
    }

    public void setButton() throws IOException {

        LayerButton collision = new LayerButton("C", 832, 400);
        LayerButton noCollision = new LayerButton("NC", 832, 432);
        Clear clear = new Clear(832, 480, "CL");
        Save save = new Save(815, 200, "Save");

        brush.setLayer(noCollision.layer);
        frame.getContentPane().add(collision);
        frame.getContentPane().add(noCollision);
        frame.getContentPane().add(clear);
        frame.getContentPane().add(save);

        TexturedButton floor = new TexturedButton(32, 832, "assets/floor_1.png", 1);
        TexturedButton floor2 = new TexturedButton(64, 832, "assets/floor_2.png", 2);
        TexturedButton eraser = new TexturedButton(96, 832, "assets/eraser.png", -1, true);
        TexturedButton wall_bot = new TexturedButton(128, 832, "assets/wall_bottom_1.png", -2);
        TexturedButton wall_top1 = new TexturedButton(160, 832, "assets/wall_top_1.png", -4);
        TexturedButton wall_top2 = new TexturedButton(192, 832, "assets/wall_1.png", -10);
        TexturedButton wall_left = new TexturedButton(224, 832, "assets/wall_bottom_inner_left.png", -5);
        TexturedButton wall_right = new TexturedButton(256, 832, "assets/wall_bottom_inner_right.png", -3);

        frame.getContentPane().add(floor);
        frame.getContentPane().add(floor2);
        frame.getContentPane().add(eraser);
        frame.getContentPane().add(wall_bot);
        frame.getContentPane().add(wall_top1);
        frame.getContentPane().add(wall_top2);
        frame.getContentPane().add(wall_left);
        frame.getContentPane().add(wall_right);
        frame.setVisible(true);
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
