package object;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class TankExplore {
    int x;
    int y;
    int state;
    BufferedImage image;

    private static BufferedImage[] exploreImages = null;

    static {
        try {
            exploreImages = new BufferedImage[] {
                    extractExploreImages(1040, 0),
                    extractExploreImages(1040, 65),
                    extractExploreImages(1040, 130),
                    extractExploreImages(1040, 195),
                    extractExploreImages(1040, 260),
                    extractExploreImages(1040, 325),
                    extractExploreImages(1040, 390),
            };
        } catch (IOException e) {
            throw new RuntimeException (e);
        }
    }

    private static BufferedImage extractExploreImages(int x, int y) throws IOException {
        Image textureImage = new ImageIcon (Objects.requireNonNull(Brick.class.getResource("/texture.png"))).getImage();
        BufferedImage texture = new BufferedImage(textureImage.getWidth(null), textureImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = texture.createGraphics();
        g2d.drawImage(textureImage, 0, 0, null);
        g2d.dispose();
        return texture.getSubimage(x, y, 64, 64);
    }

    public TankExplore (int x, int y) {
        this.x = x;
        this.y = y;
        this.state = 0;
        this.image = exploreImages[3];
    }

    public int nextState(){
        state++;
        if(state < 7){
            this.image = exploreImages[state];
        }
        return state;
    }

    public BufferedImage getImage(){
        return image;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

}
