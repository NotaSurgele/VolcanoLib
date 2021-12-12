import java.awt.*;
import java.io.IOException;
import javax.swing.*;

public class RoomCreator {

    static final int WIDTH = 900;
    static final int HEIGHT = 900;
    static final int TILESIZE = 16;

    static final int ROOMWIDTH = 800;
    static final int ROOMHEIGHT = 800;

    public RoomCreator() throws IOException {
        JFrame frame = new JFrame();
        BrushPainter brush = new BrushPainter();

        frame.setContentPane(new Grid());
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        brush.addBrushIcon((JPanel) frame.getContentPane());
    }

    public static void main(String[] args) throws IOException {
        new RoomCreator();
    }

}
