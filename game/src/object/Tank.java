package object;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;

import static gui.GamePanel.*;

public abstract class Tank {
    public static int RIGHT = 1;
    public static int LEFT = 3;
    public static int DOWN = 2;
    public static int UP = 0;
    public static Image[] tankImages = null;
    Sound sound;
    static {
        tankImages = new Image[] {
                extractTankImage (0, 7 * 32),
                extractTankImage (32, 7 * 32),
                extractTankImage (64, 7 * 32),
                extractTankImage (96, 7 * 32),

                extractTankImage (256, 7 * 32),
                extractTankImage (288, 7 * 32),
                extractTankImage (320, 7 * 32),
                extractTankImage (352, 7 * 32),

                extractTankImage (128, 7 * 32),
                extractTankImage (160, 7 * 32),
                extractTankImage (192, 7 * 32),
                extractTankImage (224, 7 * 32),

        };
    }

    public int screenWidth = 832;
    public int screenHeight = 832;
    int x;
    int y;

    //0 : shot, 1: up, 2: left, 3: down, 4: right
    int dir;
    int currMove = 0;
    int height;
    int width;
    int tankType = 0;
    int heal;
    int maxHeal;
    int damage;
    int speed;
    int speedReload;
    int currBullet;
    int maxBullet;
    Image image;

    public Tank (int tankX, int tankY, int heal, int damage, int speed) {
        sound = new Sound();
        this.x = tankX;
        this.y = tankY;
        this.maxHeal = heal;
        this.heal = this.maxHeal;
        this.damage = damage;
        this.speed = speed;
        this.height = 48;
        this.width = 48;
        this.maxBullet = 3;
        this.currBullet = this.maxBullet;
    }

    public void reset(){
        sound = null;
    }

    protected static Image extractTankImage (int x, int y) {
        Image textureImage = new ImageIcon (Objects.requireNonNull (Tank.class.getResource ("/texture/texture.png"))).getImage ();
        BufferedImage texture = new BufferedImage (textureImage.getWidth (null), textureImage.getHeight (null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = texture.createGraphics ();
        g2d.drawImage (textureImage, 0, 0, null);
        g2d.dispose ();
        return texture.getSubimage (x, y, 32, 32);
    }

    public int getX () {
        return x;
    }

    public int getY () {
        return y;
    }

    public Image getImage () {
        return this.image;
    }

    public void setImage () {
        this.image = tankImages[tankType * 4 + dir];
    }
    public int getWidth () {
        return this.width;
    }

    public int getHeight () {
        return this.height;
    }

    public void getDamaged (int damage) {
        this.heal -= damage;
    }

    public int getHeal () {
        return this.heal;
    }

    public int getMaxHeal () {
        return this.maxHeal;
    }

    public void move () {
        switch (currMove) {
            case 1:
                this.moveUp ();
                break;
            case 2:
                this.moveRight ();
                break;
            case 3:
                this.moveDown ();
                break;
            case 4:
                this.moveLeft ();
                break;
            default:
                break;
        }
    }

    public void shot () {}

    public Bullet addBullet () {
        sound.playSE(3);
        if (dir == UP) {
            return new Bullet (x + 19, y - 9, UP, damage);
        } else if (dir == DOWN) {
            return new Bullet (x + 19, y + 48, DOWN, damage);
        } else if (dir == LEFT) {
            return new Bullet (x - 12, y + 19, LEFT, damage);
        } else if (dir == RIGHT) {
            return new Bullet (x + 48, y + 19, RIGHT, damage);
        }
        return null;
    }

    public void loadBullet () {
        if (this.currBullet < this.maxBullet) {
            this.currBullet++;
        }
    }

    public void moveUp () {
        if (this.canGoUp ()) {
            this.y -= speed;
            dir = UP;
        }
    }

    public void moveDown () {
        if (this.canGoDown ()) {
            this.y += speed;
            dir = DOWN;
        }
    }

    public void moveLeft () {
        if (this.canGoLeft ()) {
            this.x -= speed;
            dir = LEFT;
        }
    }

    public void moveRight () {
        if (this.canGoRight ()) {
            this.x += speed;
            dir = RIGHT;
        }
    }

    public boolean canGoUp () {
        this.dir = UP;
        this.setImage ();
        if (this.getY () == 0)
            return false;

        if (isUpHitObj (bricks))
            return false;

        if (isUpHitObj (waters))
            return false;

        if (isUpHitObj (stones))
            return false;

        if (botTanks != null) {
            for (BotTank bot : botTanks) {
                if (this.getY () - speed < bot.getY () + bot.getHeight () && this.getY () + speed > bot.getY () + bot.getHeight ()) {
                    if (this.getX () - bot.getWidth () < bot.getX () && bot.getX () < this.getX () + this.getWidth ())
                        return false;
                }
            }
        }

        if (p1 != null) {
            if (this.getY () - speed < p1.getY () + p1.getHeight () && this.getY () + speed > p1.getY () + p1.getHeight ()) {
                if (this.getX () - p1.getWidth () < p1.getX () && p1.getX () < this.getX () + this.getWidth ())
                    return false;
            }
        }

        if (p2 != null) {
            if (this.getY () - speed < p2.getY () + p2.getHeight () && this.getY () + speed > p2.getY () + p2.getHeight ()) {
                if (this.getX () - p2.getWidth () < p2.getX () && p2.getX () < this.getX () + this.getWidth ())
                    return false;
            }
        }

        if (ally != null) {
            if (this.getY () - speed < ally.getY () + ally.getHeight () && this.getY () + speed > ally.getY () + ally.getHeight ()) {
                if (this.getX () - ally.getWidth () < ally.getX () && ally.getX () < this.getX () + this.getWidth ())
                    return false;
            }
        }

        return true;
    }

    private boolean isUpHitObj (ArrayList<StaticObject> objs) {
        if (objs != null) {
            for (StaticObject obj : objs) {
                if (this.getY () - speed < obj.getY () + obj.getHeight () && this.getY () + speed > obj.getY () + obj.getHeight ()) {
                    if (this.getX () - obj.getWidth () < obj.getX () && obj.getX () < this.getX () + this.getWidth ())
                        return true;
                }
            }
        }
        return false;
    }


    public boolean canGoDown () {
        this.dir = DOWN;
        this.setImage ();
        if (this.getY () + this.getHeight () == screenHeight)
            return false;

        if (isDownHitObj (bricks))
            return false;

        if (isDownHitObj (waters))
            return false;

        if (isDownHitObj (stones))
            return false;

        if (botTanks != null) {
            for (BotTank bot : botTanks) {
                if (bot.getY () - speed < this.getY () + this.getHeight () && bot.getY () + speed > this.getY () + this.getHeight ()) {
                    if (this.getX () - bot.getWidth () < bot.getX () && bot.getX () < this.getX () + this.getWidth ())
                        return false;
                }
            }
        }

        if (p1 != null) {
            if (p1.getY () - speed < this.getY () + this.getHeight () && p1.getY () + speed > this.getY () + this.getHeight ()) {
                if (this.getX () - p1.getWidth () < p1.getX () && p1.getX () < this.getX () + this.getWidth ())
                    return false;
            }
        }

        if (p2 != null) {
            if (p2.getY () - speed < this.getY () + this.getHeight () && p2.getY () + speed > this.getY () + this.getHeight ()) {
                if (this.getX () - p2.getWidth () < p2.getX () && p2.getX () < this.getX () + this.getWidth ())
                    return false;
            }
        }

        if (ally != null) {
            if (ally.getY () - speed < this.getY () + this.getHeight () && ally.getY () + speed > this.getY () + this.getHeight ()) {
                if (this.getX () - ally.getWidth () < ally.getX () && ally.getX () < this.getX () + this.getWidth ())
                    return false;
            }
        }

        return true;
    }

    private boolean isDownHitObj (ArrayList<StaticObject> objs) {
        if (objs != null) {
            for (StaticObject obj : objs) {
                if (obj.getY () - speed < this.getY () + this.getHeight () && obj.getY () + speed > this.getY () + this.getHeight ()) {
                    if (this.getX () - obj.getWidth () < obj.getX () && obj.getX () < this.getX () + this.getWidth ())
                        return true;
                }
            }
        }
        return false;
    }


    public boolean canGoLeft () {
        this.dir = LEFT;
        this.setImage ();

        if (this.getX () == 0)
            return false;

        if (isLeftHitObj (bricks))
            return false;

        if (isLeftHitObj (waters))
            return false;

        if (isLeftHitObj (stones))
            return false;

        if (botTanks != null) {
            for (BotTank bot : botTanks) {
                if (this.getX () - speed < bot.getX () + bot.getWidth () && this.getX () + speed > bot.getX () + bot.getWidth ()) {
                    if (this.getY () - bot.getHeight () < bot.getY () && bot.getY () < this.getY () + this.getHeight ())
                        return false;

                }
            }
        }

        if (p1 != null) {
            if (this.getX () - speed < p1.getX () + p1.getWidth () && this.getX () + speed > p1.getX () + p1.getWidth ()) {
                if (this.getY () - p1.getHeight () < p1.getY () && p1.getY () < this.getY () + this.getHeight ())
                    return false;
            }
        }

        if (p2 != null) {
            if (this.getX () - speed < p2.getX () + p2.getWidth () && this.getX () + speed > p2.getX () + p2.getWidth ()) {
                if (this.getY () - p2.getHeight () < p2.getY () && p2.getY () < this.getY () + this.getHeight ())
                    return false;
            }
        }

        if (ally != null) {
            if (this.getX () - speed < ally.getX () + ally.getWidth () && this.getX () + speed > ally.getX () + ally.getWidth ()) {
                if (this.getY () - ally.getHeight () < ally.getY () && ally.getY () < this.getY () + this.getHeight ())
                    return false;
            }
        }

        return true;
    }

    private boolean isLeftHitObj (ArrayList<StaticObject> objs) {
        if (objs != null) {
            for (StaticObject obj : objs) {
                if (this.getX () - speed < obj.getX () + obj.getWidth () && this.getX () + speed > obj.getX () + obj.getWidth ()) {
                    if (this.getY () - obj.getHeight () < obj.getY () && obj.getY () < this.getY () + this.getHeight ())
                        return true;
                }
            }
        }
        return false;
    }


    public boolean canGoRight () {
        this.dir = RIGHT;
        this.setImage ();

        if (this.getX () + this.getWidth () == screenWidth)
            return false;

        if (isRightHitObj (bricks))
            return false;

        if (isRightHitObj (waters))
            return false;

        if (isRightHitObj (stones))
            return false;


        if (botTanks != null) {
            for (BotTank bot : botTanks) {
                if (bot.getX () - speed < this.getX () + this.getWidth () && bot.getX () + speed > this.getX () + this.getWidth ()) {
                    if (this.getY () - bot.getHeight () < bot.getY () && bot.getY () < this.getY () + this.getHeight ())
                        return false;
                }
            }
        }

        if (p1 != null) {
            if (p1.getX () - speed < this.getX () + this.getWidth () && p1.getX () + speed > this.getX () + this.getWidth ()) {
                if (this.getY () - p1.getHeight () < p1.getY () && p1.getY () < this.getY () + this.getHeight ())
                    return false;
            }
        }

        if (p2 != null) {
            if (p2.getX () - speed < this.getX () + this.getWidth () && p2.getX () + speed > this.getX () + this.getWidth ()) {
                if (this.getY () - p2.getHeight () < p2.getY () && p2.getY () < this.getY () + this.getHeight ())
                    return false;
            }
        }

        if (ally != null) {
            if (ally.getX () - speed < this.getX () + this.getWidth () && ally.getX () + speed > this.getX () + this.getWidth ()) {
                if (this.getY () - ally.getHeight () < ally.getY () && ally.getY () < this.getY () + this.getHeight ())
                    return false;
            }
        }

        return true;
    }

    private boolean isRightHitObj (ArrayList<StaticObject> objs) {
        if (objs != null) {
            for (StaticObject obj : objs) {
                if (obj.getX () - speed < this.getX () + this.getWidth () && obj.getX () + speed > this.getX () + this.getWidth ()) {
                    if (this.getY () - obj.getHeight () < obj.getY () && obj.getY () < this.getY () + this.getHeight ())
                        return true;
                }
            }
        }
        return false;
    }
}