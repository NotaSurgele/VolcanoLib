import java.io.IOException;
import javax.swing.*;

public class RoomCreator {

    static final int WIDTH = 900;
    static final int HEIGHT = 900;
    static final int TILESIZE = 16;

    static final int ROOMWIDTH = 800;
    static final int ROOMHEIGHT = 800;

    static Brush brush;

    TexturedButton floor;
    MouseEvents mouse;

    public RoomCreator() throws IOException {
        JFrame frame = new JFrame();

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
    }

    public static void main(String[] args) throws IOException {
        RoomCreator r = new RoomCreator();
    }

}
