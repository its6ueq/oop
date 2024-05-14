package object;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Ally {
    int x;
    int y;
    int width;
    int height;
    int heal;
    int maxHeal;
    Image image;

    public Ally (int x, int y, int heal) {
        this.x = x;
        this.y = y;
        this.maxHeal = heal;
        this.heal = maxHeal;
        this.image = extractAllyImage (944, 0);

        this.width = 60;
        this.height = 60;
    }

    protected static Image extractAllyImage (int x, int y) {
        Image textureImage = new ImageIcon (Objects.requireNonNull (Tank.class.getResource ("/texture/texture.png"))).getImage ();
        BufferedImage texture = new BufferedImage (textureImage.getWidth (null), textureImage.getHeight (null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = texture.createGraphics ();
        g2d.drawImage (textureImage, 0, 0, null);
        g2d.dispose ();
        return texture.getSubimage (x, y, 32, 32);
    }

    public Image getImage () {
        return image;
    }

    public int getX () {
        return x;
    }

    public int getY () {
        return y;
    }

    public int getWidth () {
        return width;
    }

    public int getHeight () {
        return height;
    }

    public int getHeal () {
        return heal;
    }

    public void getDamaged (int damage) {
        this.heal -= damage;
    }

    public int getMaxHeal () {
        return maxHeal;
    }
}
