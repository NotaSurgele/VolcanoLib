import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TexturedButton extends JButton {

    BufferedImage texture;

    public TexturedButton(int x, int y, String imgPath, int value) throws IOException {

        BufferedImage img = ImageIO.read(new File(imgPath));
        ImageIcon image = new ImageIcon(img);
        this.setBounds(x, y, 16, 16);
        this.setIcon(image);
        this.texture = img;
        this.addActionListener(new ButtonListener(img, value));
    }

    public TexturedButton(int x, int y, String imgPath, int value, boolean eraser) throws IOException {

        BufferedImage img = ImageIO.read(new File(imgPath));
        ImageIcon image = new ImageIcon(img);
        this.setBounds(x, y, 16, 16);
        this.setIcon(image);
        this.texture = null;
        this.addActionListener(new ButtonListener(null, value));
    }

    public BufferedImage getTexture()
    {
        return this.texture;
    }
}
