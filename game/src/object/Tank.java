package object;

import gui.GamePanel;
import object.Brick;
import object.Bullet;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import static gui.GamePanel.bricks;
import static gui.GamePanel.bullets;

public abstract class Tank {
    public int screenWidth = 832;
    public int screenHeight = 832;

    public static int RIGHT = 1;
    public static int LEFT = 3;
    public static int DOWN = 2;
    public static int UP = 0;

    int x;
    int y;
    int dir;
    int currMove = 5;
    //0 : shot, 1: up, 2: left, 3: down, 4: right

    int height;
    int width;
    int tankType = 0;

    int heal;
    int damage;
    int speed;

    int speedReload;
    int currBullet;
    int maxBullet;

    Image image;
    public static Image[] tankImages = null;

    static {
        tankImages = new Image[] {
                extractTankImage(0, 0),
                extractTankImage(32, 0),
                extractTankImage(64, 0),
                extractTankImage(96, 0),

                extractTankImage(256, 0),
                extractTankImage(288, 0),
                extractTankImage(320, 0),
                extractTankImage(352, 0),

                extractTankImage (128, 0),
                extractTankImage (160, 0),
                extractTankImage (192, 0),
                extractTankImage (224, 0),

        };
    }

    public Tank (int tankX, int tankY, int heal, int damage, int speed) {
        this.x = tankX;
        this.y = tankY;
        this.heal = heal;
        this.damage = damage;
        this.speed = speed;
        this.height = 48;
        this.width = 48;
        this.maxBullet = 3;
        this.currBullet = this.maxBullet;
    }

    protected static Image extractTankImage (int x, int y) {
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

    public int getWidth(){return this.width;}

    public int getHeight(){return this.height;}

    public void move() {
        switch (currMove) {
            case 0:
                this.shot();
                break;
            case 1:
                this.moveUp ();
                break;
            case 2:
                this.moveLeft ();
                break;
            case 3:
                this.moveDown ();
                break;
            case 4:
                this.moveRight ();
                break;
            default:
                break;
        }
    }

    public void shot(){}
    public Bullet addBullet(){
        if(currBullet > 0){
            currBullet--;
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
        }
        return null;
    }

    public void loadBullet (){
        if(this.currBullet < this.maxBullet){
            this.currBullet++;
        }
    }

    public void moveUp() {
        if(this.canGoUp ()){
            this.y -= speed;
            dir = UP;
        }
    }

    public void moveDown() {
        if(this.canGoDown ()){
            this.y += speed;
            dir = DOWN;
        }
    }

    public void moveLeft() {
        if(this.canGoLeft ()){
            this.x -= speed;
            dir = LEFT;
        }
    }

    public void moveRight() {
        if(this.canGoRight ()){
            this.x += speed;
            dir = RIGHT;
        }
    }

    public boolean canGoUp(){
        this.dir = UP;
        this.image = tankImages[tankType * 4 + dir];
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

    public boolean canGoDown(){
        this.dir = DOWN;
        this.image = tankImages[tankType * 4 + dir];
        if(this.getY() + this.getHeight () == screenHeight) return false;

        if(bricks != null){
            for (Brick brick : bricks) {
                if(this.getY() + this.getHeight () == brick.getY() && this.getX() - brick.getWidth() < brick.getX() && brick.getX () < this.getX() + this.getWidth())
                    return false;
            }
        }

        return true;
    }

    public boolean canGoLeft(){
        this.dir = LEFT;
        this.image = tankImages[tankType * 4 + dir];

        if(this.getX() == 0) return false;

        if(bricks != null){
            for (Brick brick : bricks) {
                if(this.getX () == brick.getX () + brick.getWidth () && this.getY() - brick.getHeight () < brick.getY () && brick.getY () < this.getY () + this.getHeight ())
                    return false;
            }
        }
        return true;
    }


    public boolean canGoRight(){
        this.dir = RIGHT;
        this.image = tankImages[tankType * 4 + dir];

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