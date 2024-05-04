package game.object;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;

public class Tank {

    int x;
    int y;
    int width;
    int height;
    int heal;
    int currDir;
    Image image;
    int speed;

    private static Toolkit tk = Toolkit.getDefaultToolkit();
    private static Image[] tankImages = null;

    static {
        tankImages = new Image[] {
                extractTankImage(0, 0, 32, 32), // p1u
                extractTankImage(32, 0, 32, 32), // p1r
                extractTankImage(64, 0, 32, 32), //p1d
                extractTankImage(96, 32, 32, 32), //p1l
        };
    }

    public Tank (int tankX, int tankY, int tankWidth, int tankHeight, int currHeal, int direction, int speed) {
        this.x = tankX;
        this.y = tankY;
        this.width = tankWidth;
        this.height = tankHeight;
        this.heal = currHeal;
        this.currDir = direction;
        this.image = tankImages[currDir];
        this.speed = speed;

        if (tankImages != null  && currDir < tankImages.length) {
            this.image = tankImages[currDir];
        } else {
            // Handle the case when tankImages is null or currDir is out of bounds
            System.out.println("Failed to set image for Tank with direction: " + currDir);
        }

    }

    public static void createTank(int tankX, int tankY, int tankWidth, int tankHeight, int currHeal, int direction, int speed) {
        new Tank(tankX, tankY, tankWidth, tankHeight, currHeal, direction, speed);
    }

    private static Image extractTankImage(int x, int y, int width, int height) {
        System.out.println("Attempting to load texture image...");
        System.out.println("Resource path: " + Tank.class.getResource("/texture.png"));

        Image textureImage = new ImageIcon(Objects.requireNonNull(Tank.class.getResource("/texture.png"))).getImage();
        if (textureImage == null) {
            System.out.println("Failed to load the texture image.");
            return null;
        }

        BufferedImage texture = new BufferedImage(textureImage.getWidth(null), textureImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = texture.createGraphics();
        g2d.drawImage(textureImage, 0, 0, null);
        g2d.dispose();

        return texture.getSubimage(x, y, width, height);
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public Image image(){
        return this.image;
    }

    public void moveUp() {
        this.y -= speed;
        this.image = tankImages[0];
    }

    public void moveDown() {
        this.y += speed;
        this.image = tankImages[2];
    }

    public void moveLeft() {
        this.x -= speed;
        this.image = tankImages[3];
    }

    public void moveRight() {
        this.x += speed;
        this.image = tankImages[1];
    }
}