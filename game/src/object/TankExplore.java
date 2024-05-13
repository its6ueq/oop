package object;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class TankExplore {
    private static BufferedImage[] exploreImages = null;

    static {
        try {
            exploreImages = new BufferedImage[] {extractExploreImages (1040, 0), extractExploreImages (1040, 65), extractExploreImages (1040, 130), extractExploreImages (1040, 195), extractExploreImages (1040, 260), extractExploreImages (1040, 325), extractExploreImages (1040, 390),};
        } catch (IOException e) {
            throw new RuntimeException (e);
        }
    }

    int x;
    int y;
    int width;
    int height;
    int state;
    BufferedImage image;

    public TankExplore (int x, int y) {
        this.x = x;
        this.y = y;
        this.state = 0;
        this.image = exploreImages[3];
        this.width = 96;
        this.height = 96;
    }

    private static BufferedImage extractExploreImages (int x, int y) throws IOException {
        Image textureImage = new ImageIcon (Objects.requireNonNull (StaticObject.class.getResource ("/texture/texture.png"))).getImage ();
        BufferedImage texture = new BufferedImage (textureImage.getWidth (null), textureImage.getHeight (null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = texture.createGraphics ();
        g2d.drawImage (textureImage, 0, 0, null);
        g2d.dispose ();
        return texture.getSubimage (x, y, 64, 64);
    }

    public int nextState () {
        state++;
        if (state < 7) {
            this.image = exploreImages[state];
        }
        return state;
    }

    public BufferedImage getImage () {
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
