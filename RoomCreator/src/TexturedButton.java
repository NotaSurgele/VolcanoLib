import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TexturedButton extends JButton {

    BufferedImage texture;

    public TexturedButton(int x, int y, String imgPath) throws IOException {

        BufferedImage img = ImageIO.read(new File(imgPath));
        ImageIcon image = new ImageIcon(img);
        this.setBounds(x, y, 16, 16);
        this.setIcon(image);
        this.texture = img;
        this.addActionListener(new ButtonListener(img));
    }

    public BufferedImage getTexture()
    {
        return this.texture;
    }
}
