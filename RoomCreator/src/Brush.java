import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Brush {

    JPanel content;
    BufferedImage currentTexture;

    public Brush(JPanel p) {
        this.content = p;
    }

    public void setTexture(BufferedImage i)
    {
        this.currentTexture = i;
    }

    public BufferedImage getTexture()
    {
        return this.currentTexture;
    }

    public void drawTexture(Vector2i origin, Vector2i pos)
    {
        Graphics g = this.content.getGraphics();
        int originX = origin.getX();
        int originY = origin.getY();
        int posX = pos.getX();
        int posY = pos.getY();

        if ((originX <= 800 && originY <= 800)) {
            for (int i = originX; i <= posX; i += RoomCreator.TILESIZE) {
                for (int j = originY; j <= posY; j += RoomCreator.TILESIZE) {
                    if (i <= 800 && j <= 800)
                        g.drawImage(this.currentTexture, i, j, null);
                }
            }
        }
    }

}
