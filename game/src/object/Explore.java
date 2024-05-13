package object;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Explore {
    private static Image[] exploreImages = null;

    static {
        exploreImages = new Image[] {extractExploreImages (1108, 0), extractExploreImages (1108, 31), extractExploreImages (1108, 64), extractExploreImages (1108, 97), extractExploreImages (1108, 130),};
    }

    int x;
    int y;
    int width;
    int height;
    int state;
    Image image;

    public Explore (int x, int y) {
        this.x = x;
        this.y = y;
        this.state = 0;
        this.image = exploreImages[state];
        this.width = 64;
        this.height = 64;
    }

    private static Image extractExploreImages (int x, int y) {
        Image textureImage = new ImageIcon (Objects.requireNonNull (Explore.class.getResource ("/texture/texture.png"))).getImage ();
        BufferedImage texture = new BufferedImage (textureImage.getWidth (null), textureImage.getHeight (null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = texture.createGraphics ();
        g2d.drawImage (textureImage, 0, 0, null);
        g2d.dispose ();
        return texture.getSubimage (x, y, 32, 32);
    }

    public int nextState () {
        state++;
        if (state < 5) {
            this.image = exploreImages[state];
        }
        return state;
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

    public int getHeight () {
        return height;
    }

    public int getWidth () {
        return width;
    }

}
