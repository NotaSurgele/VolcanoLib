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

    public void drawTexture(MouseEvent mouse)
    {
        Graphics g = this.content.getGraphics();
        int x = mouse.getX() - (mouse.getX() % RoomCreator.TILESIZE);
        int y = mouse.getY() - (mouse.getY() % RoomCreator.TILESIZE) - 32;

        if (x <= 800 && y <= 800)
            g.drawImage(this.currentTexture, x, y, null);
    }

}
