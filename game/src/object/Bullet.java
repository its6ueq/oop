package object;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Bullet {
    int UP = 0;
    int RIGHT = 1;
    int DOWN = 2;
    int LEFT = 3;

    int x;
    int y;
    int height;
    int width;
    int damage;
    int dir;
    Image image;
    int speed = 15;
    private static Image[] bulletImages = null;

    static {
        bulletImages = new Image[] {
                extractUDBulletImage(945, 128),
                extractBulletImage(952, 129),
                extractUDBulletImage(961, 128),
                extractBulletImage(968, 129),
        };
    }

    private static Image extractUDBulletImage(int x, int y) {
        Image textureImage = new ImageIcon(Objects.requireNonNull(Bullet.class.getResource("/texture.png"))).getImage();
        BufferedImage texture = new BufferedImage(textureImage.getWidth(null), textureImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = texture.createGraphics();
        g2d.drawImage(textureImage, 0, 0, null);
        g2d.dispose();
        return texture.getSubimage(x, y, 6, 8);
    }

    private static Image extractBulletImage(int x, int y) {
        Image textureImage = new ImageIcon(Objects.requireNonNull(Bullet.class.getResource("/texture.png"))).getImage();
        BufferedImage texture = new BufferedImage(textureImage.getWidth(null), textureImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = texture.createGraphics();
        g2d.drawImage(textureImage, 0, 0, null);
        g2d.dispose();
        return texture.getSubimage(x, y, 8, 6);
    }


    public Bullet (int x, int y, int dir, int damage){
        this.x = x;
        this.y = y;
        this.damage = damage;
        this.dir = dir;
        this.image = bulletImages[dir];
        if(dir == UP || dir == DOWN){
            height = 16;
            width = 12;
        }
        if(dir == LEFT || dir == RIGHT){
            height = 12;
            width = 16;
        }
    }

    public void move(){
        if(dir == UP) {
            y -= speed;

        }
        else if(dir == DOWN) {
            y += speed;
        }
        else if(dir == LEFT) {
            x -= speed;
        }
        else if(dir == RIGHT) {
            x += speed;
        }
    }

    public int getX () {
        return this.x;
    }

    public int getY () {
        return this.y;
    }

    public int getHeight(){
        return this.height;
    }

    public int getWidth(){
        return this.width;
    }

    public Image getImage(){
        return this.image;
    }
}
