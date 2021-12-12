import javax.swing.*;
import java.awt.*;

public class Grid extends JPanel {

    public Grid() {
        setBackground(Color.BLACK);
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        for (int x = 0; x <= RoomCreator.ROOMWIDTH; x += RoomCreator.TILESIZE) {

            for (int y = 0; y <= RoomCreator.ROOMHEIGHT; y += RoomCreator.TILESIZE) {

                g.setColor(Color.WHITE);
                g.drawRect(x, y, RoomCreator.TILESIZE, RoomCreator.TILESIZE);
            }
        }
    }
}
