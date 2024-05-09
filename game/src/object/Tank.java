package object;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

import static gui.GamePanel.botTanks;
import static gui.GamePanel.bricks;
import static gui.GamePanel.p1;
import static gui.GamePanel.p2;

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
    int currMove = 0;

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
                extractTankImage(0, 7 * 32),
                extractTankImage(32, 7 * 32),
                extractTankImage(64, 7 * 32),
                extractTankImage(96, 7 * 32),

                extractTankImage(256, 7 * 32),
                extractTankImage(288, 7 * 32),
                extractTankImage(320, 7 * 32),
                extractTankImage(352, 7 * 32),

                extractTankImage (128, 7 * 32),
                extractTankImage (160, 7 * 32),
                extractTankImage (192, 7 * 32),
                extractTankImage (224, 7 * 32),

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

    public void getDamaged(int damage){
        this.heal -= damage;
    }

    public int getHeal(){
        return this.heal;
    }

    public void move() {
        switch (currMove) {
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
                if(this.getY() - speed < brick.getY() + brick.getHeight () && this.getY() + speed > brick.getY() + brick.getHeight ()){
                    if(this.getX() - brick.getWidth() < brick.getX() && brick.getX () < this.getX() + this.getWidth())
                        return false;
                }
            }
        }

        if(botTanks != null){
            for (BotTank bot : botTanks) {
                if(this.getY() - speed < bot.getY() + bot.getHeight () && this.getY() + speed > bot.getY() + bot.getHeight ()){
                    if(this.getX() - bot.getWidth() < bot.getX() && bot.getX () < this.getX() + this.getWidth())
                        return false;
                }
            }
        }

        if(p1 != null){
            if(this.getY() - speed < p1.getY() + p1.getHeight () && this.getY() + speed > p1.getY() + p1.getHeight ()){
                if(this.getX() - p1.getWidth() < p1.getX() && p1.getX () < this.getX() + this.getWidth())
                    return false;
            }
        }

        if(p2 != null){
            if(this.getY() - speed < p2.getY() + p2.getHeight () && this.getY() + speed > p2.getY() + p2.getHeight ()){
                if(this.getX() - p2.getWidth() < p2.getX() && p2.getX () < this.getX() + this.getWidth())
                    return false;
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
                if(brick.getY() - speed < this.getY() + this.getHeight () && brick.getY () + speed > this.getY() + this.getHeight ()){
                    if(this.getX() - brick.getWidth() < brick.getX() && brick.getX () < this.getX() + this.getWidth())
                        return false;

                }
            }
        }

        if(botTanks != null){
            for (BotTank bot : botTanks) {
                if(bot.getY() - speed < this.getY() + this.getHeight () && bot.getY () + speed > this.getY() + this.getHeight ()){
                    if(this.getX() - bot.getWidth() < bot.getX() && bot.getX () < this.getX() + this.getWidth())
                        return false;
                }
            }
        }

        if(p1 != null){
            if(p1.getY() - speed < this.getY() + this.getHeight () && p1.getY () + speed > this.getY() + this.getHeight ()){
                if(this.getX() - p1.getWidth() < p1.getX() && p1.getX () < this.getX() + this.getWidth())
                    return false;
            }
        }

        if(p2 != null){
            if(p2.getY() - speed < this.getY() + this.getHeight () && p2.getY () + speed > this.getY() + this.getHeight ()){
                if(this.getX() - p2.getWidth() < p2.getX() && p2.getX () < this.getX() + this.getWidth())
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
                if(this.getX () - speed < brick.getX () + brick.getWidth () && this.getX () + speed > brick.getX () + brick.getWidth ()){
                        if(this.getY() - brick.getHeight () < brick.getY () && brick.getY () < this.getY () + this.getHeight ())
                            return false;
                }
            }
        }

        if(botTanks != null){
            for (BotTank bot : botTanks) {
                if(this.getX () - speed < bot.getX () + bot.getWidth () && this.getX () + speed > bot.getX () + bot.getWidth ()){
                    if(this.getY() - bot.getHeight () < bot.getY () && bot.getY () < this.getY () + this.getHeight ())
                        return false;

                }
            }
        }

        if(p1 != null){
            if(this.getX () - speed < p1.getX () + p1.getWidth () && this.getX () + speed > p1.getX () + p1.getWidth ()){
                if(this.getY() - p1.getHeight () < p1.getY () && p1.getY () < this.getY () + this.getHeight ())
                    return false;
            }
        }

        if(p2 != null){
            if(this.getX () - speed < p2.getX () + p2.getWidth () && this.getX () + speed > p2.getX () + p2.getWidth ()){
                if(this.getY() - p2.getHeight () < p2.getY () && p2.getY () < this.getY () + this.getHeight ())
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
                if(brick.getX () - speed < this.getX () + this.getWidth () && brick.getX () + speed > this.getX () + this.getWidth ()) {
                    if (this.getY () - brick.getHeight () < brick.getY () && brick.getY () < this.getY () + this.getHeight ())
                        return false;
                }
            }
        }

        if(botTanks != null){
            for (BotTank bot : botTanks) {
                if(bot.getX () - speed < this.getX () + this.getWidth () && bot.getX () + speed > this.getX () + this.getWidth ()) {
                    if (this.getY () - bot.getHeight () < bot.getY () && bot.getY () < this.getY () + this.getHeight ())
                        return false;
                }
            }
        }

        if(p1 != null) {
            if (p1.getX () - speed < this.getX () + this.getWidth () && p1.getX () + speed > this.getX () + this.getWidth ()) {
                if (this.getY () - p1.getHeight () < p1.getY () && p1.getY () < this.getY () + this.getHeight ())
                    return false;
            }
        }

        if(p2 != null){
            if (p2.getX () - speed < this.getX () + this.getWidth () && p2.getX () + speed > this.getX () + this.getWidth ()) {
                if (this.getY () - p2.getHeight () < p2.getY () && p2.getY () < this.getY () + this.getHeight ())
                    return false;
            }
        }

        return true;
    }

}