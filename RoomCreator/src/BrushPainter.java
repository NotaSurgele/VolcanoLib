import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BrushPainter {

    public BrushPainter() {
    }

    private JButton createButton(int x, int y, String imgPath) throws IOException {
        JButton button = new JButton();

        BufferedImage img = ImageIO.read(new File(imgPath));
        ImageIcon image = new ImageIcon(img);
        button.setBounds(x, y, 16, 16);
        button.setIcon(image);
        return button;
    }

    public void addBrushIcon(JPanel panel) throws IOException {
        panel.setLayout(null);
        panel.add(createButton(16, 832, "assets/floor_1.png"));
    }
}
