package object;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class StaticObject {
    int x;
    int y;
    int height = 32;
    int width = 32;
    int heal;

    Image image;
    public static Image[] objectImages = null;

    static {
        objectImages = new Image[] {
                extractObjectImage (29 * 32, 0),
                extractObjectImage (29 * 32, 9 * 16),
                extractObjectImage (29 * 32, 10 * 16),
                extractObjectImage (29 * 32, 12 * 16),
        };
    }

    protected static Image extractObjectImage (int x, int y) {
        Image objectImage = new ImageIcon(Objects.requireNonNull(StaticObject.class.getResource("/texture.png"))).getImage();
        BufferedImage texture = new BufferedImage(objectImage.getWidth(null), objectImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = texture.createGraphics();
        g2d.drawImage(objectImage, 0, 0, null);
        g2d.dispose();
        return texture.getSubimage(x, y, 16, 16);
    }

    public StaticObject (int x, int y, int obj) {
        this.x = x;
        this.y = y;
        switch (obj){
            case 0:
                this.heal = 3;
                this.image = objectImages[0];
                break;
            case 1:
                this.heal = 999;
                this.image = objectImages[1];
                break;
            case 2:
                this.heal = 1;
                this.image = objectImages[2];
                break;
            case 3:
                this.heal = 1;
                this.image = objectImages[3];
                break;
            default:
                break;
        }
    }

    public Image getImage(){
        return image;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getHeight(){
        return height;
    }

    public int getWidth(){
        return width;
    }

    public int getHeal() {return heal;};

    public void getDamaged(int damage){this.heal -= damage;};
}
