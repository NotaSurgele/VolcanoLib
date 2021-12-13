import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Brush {

    JPanel content;
    BufferedImage currentTexture;

    int[][] drawingLayer;

    int value;

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

    public void setLayer(Layer layer)
    {
        this.drawingLayer = layer.getLayer();
    }

    public void setValue(int v)
    {
        this.value = v;
    }

    public void showLayer()
    {
        for (int i = 0; i != this.drawingLayer.length; i++) {
            for (int j = 0; j != this.drawingLayer[i].length; j++) {
                System.out.print(" " + this.drawingLayer[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("______________________________________________________\n");
    }
    public void drawTexture(Vector2i origin, Vector2i pos)
    {
        Graphics g = this.content.getGraphics();
        int originX = origin.getX();
        int originY = origin.getY();
        int posX = pos.getX();
        int posY = pos.getY();

        if ((originX <= RoomCreator.ROOMHEIGHT && originY <= RoomCreator.ROOMWIDTH)) {
            for (int i = originX; i <= posX; i += RoomCreator.TILESIZE) {
                for (int j = originY; j <= posY; j += RoomCreator.TILESIZE) {
                    if (i <= RoomCreator.ROOMHEIGHT && j <= RoomCreator.ROOMWIDTH) {
                        g.drawImage(this.currentTexture, i, j, null);
                        if ((i / RoomCreator.TILESIZE > 0 && i / RoomCreator.TILESIZE < 50) && (j / RoomCreator.TILESIZE > 0 && j / RoomCreator.TILESIZE < 50)) {
                            if (this.drawingLayer != null)
                                this.drawingLayer[j / RoomCreator.TILESIZE][i / RoomCreator.TILESIZE] = this.value;
                        }
                    }
                }
            }
        }
    }

}
