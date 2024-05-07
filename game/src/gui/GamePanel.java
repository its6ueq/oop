package gui;

import game.object.Tank;
import object.Brick;
import object.Bullet;
import object.Explore;
import object.TankExplore;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    int screenWidth = 832;
    int screenHeight = 832;

    int currStage = 1;

    boolean p1_moveup = false;
    boolean p1_movedown = false;
    boolean p1_moveright = false;
    boolean p1_moveleft = false;

    Tank p1;

    int TIMER_DELAY = 1000/60;
    Timer gameLoop;
    Timer nextState;


    ArrayList<Brick> bricks = new ArrayList<> ();
    ArrayList<Tank> botTanks = new ArrayList<> ();
    ArrayList<Bullet> bullets = new ArrayList<> ();
    ArrayList<Explore> explorings = new ArrayList<> ();
    ArrayList<TankExplore> tankexplorings = new ArrayList<> ();

    GamePanel () throws FileNotFoundException {


        ArrayList<ArrayList<Character>> map = readMap();
        setPreferredSize (new Dimension (screenWidth, screenHeight));
        setFocusable (true);
        addKeyListener (this);

        p1 = new Tank(10, 10 , 10, 1, 2);


        nextState = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!explorings.isEmpty()) {
                    Iterator<Explore> iterator = explorings.iterator();
                    while (iterator.hasNext()) {
                        Explore explore = iterator.next();
                        if (explore.nextState() == 5) {
                            iterator.remove();
                        }
                    }
                }
                if (!tankexplorings.isEmpty()) {
                    Iterator<TankExplore> iterator = tankexplorings.iterator();
                    while (iterator.hasNext()) {
                        TankExplore explore = iterator.next();
                        if (explore.nextState() == 7) {
                            iterator.remove();
                        }
                    }
                }
            }
        });


        nextState.start ();
        gameLoop = new Timer(TIMER_DELAY, this);
        gameLoop.start();
    }

    ArrayList<ArrayList<Character>> readMap () throws FileNotFoundException {
        ArrayList<ArrayList<Character>> map = new ArrayList<>();
        return map;
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        p1move ();
        bulletMove();
        checkBulletHit();
        repaint();
        Toolkit.getDefaultToolkit().sync();
    }

    void checkBulletHit(){
        if(bullets != null) {
            Iterator<Bullet> iterator = bullets.iterator();
            while(iterator.hasNext()) {
                Bullet bullet = iterator.next();
                if(bullet.getX() + bullet.getWidth() > screenWidth || bullet.getX() < 0){
                    Explore explore = new Explore(bullet.getX() - 24, bullet.getY() - 26);
                    iterator.remove();
                    explorings.add(explore);
                }
                if(bullet.getY() + bullet.getHeight () > screenHeight || bullet.getY() < 0){
                    Explore explore = new Explore(bullet.getX() - 26, bullet.getY() - 24);
                    iterator.remove();
                    explorings.add(explore);
                }
            }
        }
    }
    void bulletMove(){
        if(bullets != null) {
            for(Bullet bullet : bullets){
                bullet.move ();
            }
        }
    }

    void p1move(){
        if (p1_moveup && p1.getY() > 0) {
            p1.moveUp();
        }
        if (p1_movedown && p1.getY() + p1.getHeight () < screenHeight) {
            p1.moveDown();
        }
        if (p1_moveleft && p1.getX() > 0) {
            p1.moveLeft();
        }
        if (p1_moveright && p1.getX() + p1.getWidth () < screenWidth) {
            p1.moveRight();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_W:
                p1_moveup = true;
                p1_movedown = false;
                p1_moveleft = false;
                p1_moveright = false;
                break;
            case KeyEvent.VK_S:
                p1_moveup = false;
                p1_movedown = true;
                p1_moveleft = false;
                p1_moveright = false;
                break;
            case KeyEvent.VK_A:
                p1_moveup = false;
                p1_movedown = false;
                p1_moveleft = true;
                p1_moveright = false;
                break;
            case KeyEvent.VK_D:
                p1_moveup = false;
                p1_movedown = false;
                p1_moveleft = false;
                p1_moveright = true;
                break;
            case KeyEvent.VK_J:
                bullets.add(p1.shot ());
            default:
                break;
        }
    }

    @Override
    public void keyTyped (KeyEvent e) {}

    @Override
    public void keyReleased (KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_W) {
            p1_moveup = false;
        }
        if (key == KeyEvent.VK_S) {
            p1_movedown = false;
        }
        if (key == KeyEvent.VK_A) {
            p1_moveleft = false;
        }
        if (key == KeyEvent.VK_D) {
            p1_moveright = false;
        }
    }

    @Override
    public void paintComponent (Graphics g) {
        super.paintComponent (g);
        draw (g);
    }

    public void draw (Graphics g) {

        setBackground (Color.BLACK);

        g.setFont (new Font ("Arial", Font.PLAIN, 32));
        g.drawString ("Game dau uoi` ", 10, 35);

        if(bullets != null) {
            for(Bullet bullet : bullets){
                g.drawImage (bullet.getImage (), bullet.getX(), bullet.getY(), bullet.getWidth (), bullet.getHeight (), null);
            }
        }

        g.drawImage (p1.getImage(), p1.getX (), p1.getY (), 64, 64, null);


        if(explorings != null) {
            for(Explore explore : explorings){
                g.drawImage (explore.getImage (), explore.getX(), explore.getY(), 64, 64, null);
            }
        }

        if(tankexplorings != null) {
            for(TankExplore explore : tankexplorings){
                g.drawImage (explore.getImage (), explore.getX (), explore.getY (), 64, 64, null);
            }
        }
    }
}


