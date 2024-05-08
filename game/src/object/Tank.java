package game.object;

import object.Brick;
import object.Bullet;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;

public class Tank {
    public int screenWidth = 832;
    public int screenHeight = 832;


    public int RIGHT = 1;
    public int LEFT = 3;
    public int DOWN = 2;
    public int UP = 0;

    int x;
    int y;
    int dir;

    int height;
    int width;

    int heal;
    int damage;
    int speed;

    Image image;
    private static Image[] tankImages = null;

    static {
        tankImages = new Image[] {
                extractTankImage(0, 0),
                extractTankImage(32, 0),
                extractTankImage(64, 0),
                extractTankImage(96, 32),
        };
    }

    public Tank (int tankX, int tankY, int heal, int damage, int speed) {
        this.x = tankX;
        this.y = tankY;
        this.heal = heal;
        this.damage = damage;
        this.speed = speed;
        this.image = tankImages[dir];
        this.height = 48;
        this.width = 48;
    }

    public static void createTank(int tankX, int tankY, int currHeal, int direction, int speed) {
        new Tank(tankX, tankY, currHeal, direction, speed);
    }

    private static Image extractTankImage(int x, int y) {
        Image textureImage = new ImageIcon(Objects.requireNonNull(Tank.class.getResource("/texture.png"))).getImage();
        BufferedImage texture = new BufferedImage(textureImage.getWidth(null), textureImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = texture.createGraphics();
        g2d.drawImage(textureImage, 0, 0, null);
        g2d.dispose();
        return texture.getSubimage(x, y, 32, 32);
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public Image getImage(){
        return this.image;
    }

    public void moveUp() {
        this.y -= speed;
        dir = UP;
        this.image = tankImages[dir];
    }

    public void moveDown() {
        this.y += speed;
        dir = DOWN;
        this.image = tankImages[dir];
    }

    public void moveLeft() {
        this.x -= speed;
        dir = LEFT;
        this.image = tankImages[dir];
    }

    public void moveRight() {
        this.x += speed;
        dir = RIGHT;
        this.image = tankImages[dir];
    }

    public int getWidth(){return this.width;}

    public int getHeight(){return this.height;}

    public Bullet shot(){
        if(dir == UP) {
            return new Bullet (x + 19, y - 9, UP, damage);
        }
        else if(dir == DOWN) {
                return new Bullet (x + 19, y + 48, DOWN, damage);
        }
        else if(dir == LEFT) {
                return new Bullet (x - 12, y + 19, LEFT, damage);
        }
        else if(dir == RIGHT) {
                return new Bullet (x + 48, y + 19, RIGHT, damage);
        }
        return null;
    }

    public boolean canGoUp(ArrayList<Brick> bricks){
        this.dir = UP;
        this.image = tankImages[dir];
        if(this.getY () == 0) return false;

        if(bricks != null){
            for (Brick brick : bricks) {
                if(this.getY() == brick.getY() + brick.getHeight ()){
                    if(this.getX() - brick.getWidth() < brick.getX() && brick.getX () < this.getX() + this.getWidth())
                        return false;
                }
            }
        }

        return true;
    }

    public boolean canGoDown(ArrayList<Brick> bricks){
        this.dir = DOWN;
        this.image = tankImages[dir];
        if(this.getY() + this.getHeight () == screenHeight) return false;

        if(bricks != null){
            for (Brick brick : bricks) {
                if(this.getY() + this.getHeight () == brick.getY() && this.getX() - brick.getWidth() < brick.getX() && brick.getX () < this.getX() + this.getWidth())
                    return false;
            }
        }

        return true;
    }

    public boolean canGoLeft(ArrayList<Brick> bricks){
        this.dir = LEFT;
        this.image = tankImages[dir];

        if(this.getX() == 0) return false;

        if(bricks != null){
            for (Brick brick : bricks) {
                if(this.getX () == brick.getX () + brick.getWidth () && this.getY() - brick.getHeight () < brick.getY () && brick.getY () < this.getY () + this.getHeight ())
                    return false;
            }
        }
        return true;
    }


    public boolean canGoRight(ArrayList<Brick> bricks){
        this.dir = RIGHT;
        this.image = tankImages[dir];

        if(this.getX() + this.getWidth () == screenWidth)
            return false;
        if(bricks != null){
            for (Brick brick : bricks) {
                if(this.getX () + this.getWidth () == brick.getX () && this.getY() - brick.getHeight () < brick.getY () && brick.getY () < this.getY () + this.getHeight ())
                    return false;
            }
        }
        return true;
    }

}