package gui;

import game.object.Tank;
import object.Bullet;
import object.Explore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    int screenWidth = 676;
    int screenHeight = 676;

    boolean p1_moveup = false;
    boolean p1_movedown = false;
    boolean p1_moveright = false;
    boolean p1_moveleft = false;

    Tank p1;

    int TIMER_DELAY = 1000/60;
    Timer gameLoop;
    Timer nextStage;

    ArrayList<Bullet> bullets = new ArrayList<>();
    ArrayList<Explore> explorings = new ArrayList<> ();

    GamePanel () {
        setPreferredSize (new Dimension (screenWidth, screenHeight));
        setFocusable (true);
        addKeyListener (this);

        p1 = new Tank(10, 10 , 10, 1, 3);

        nextStage = new Timer(10, new ActionListener() {
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
            }
        });

        nextStage.start ();
        gameLoop = new Timer(TIMER_DELAY, this);
        gameLoop.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        p1move ();
        bulletMove();
        checkBulletHit();
        repaint();
    }

    void checkBulletHit(){
        if(bullets != null) {
            Iterator<Bullet> iterator = bullets.iterator();
            while(iterator.hasNext()) {
                Bullet bullet = iterator.next();
                System.out.println(bullet.getX() + " " + bullet.getWidth());
                if(bullet.getX() + bullet.getWidth() > screenWidth || bullet.getX() < 0){
                    Explore explore = new Explore(bullet.getX() - 12, bullet.getY() - 13);
                    iterator.remove();
                    explorings.add(explore);
                }
                if(bullet.getY() + bullet.getHeight () > screenHeight || bullet.getY() < 0){
                    Explore explore = new Explore(bullet.getX() - 13, bullet.getY() - 12);
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
        if (p1_moveup) {
            p1.moveUp();
        }
        if (p1_movedown) {
            p1.moveDown();
        }
        if (p1_moveleft) {
            p1.moveLeft();
        }
        if (p1_moveright) {
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
    public void keyTyped (KeyEvent e) {

    }



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
                g.drawImage (bullet.getImage (), bullet.getX(), bullet.getY(), null);
            }
        }
        
        g.drawImage (p1.getImage(), p1.getX (), p1.getY (), null);

        if(explorings != null) {
            for(Explore explore : explorings){
                g.drawImage (explore.getImage (), explore.getX(), explore.getY(), null);
            }
        }
    }


}

