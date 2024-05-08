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
import java.io.*;
import java.util.*;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    public int screenWidth = 832;
    public int screenHeight = 832;

    int currState = 3;

    boolean p1_moveup = false;
    boolean p1_movedown = false;
    boolean p1_moveright = false;
    boolean p1_moveleft = false;

    Tank p1;

    int TIMER_DELAY = 1000/60;
    Timer gameLoop;
    Timer nextState;

    ArrayList<ArrayList<Character>> map;

    ArrayList<Brick> bricks = new ArrayList<> ();
    ArrayList<Tank> botTanks = new ArrayList<> ();
    ArrayList<Bullet> bullets = new ArrayList<> ();
    ArrayList<Explore> explorings = new ArrayList<> ();
    ArrayList<TankExplore> tankexplorings = new ArrayList<> ();

    GamePanel () throws FileNotFoundException {
        map = readMap();
        buildMap();

        setPreferredSize (new Dimension (screenWidth, screenHeight));
        setFocusable (true);
        addKeyListener (this);

        p1 = new Tank(392, 784, 10, 1, 2);

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

    //import map
    ArrayList<ArrayList<Character>> readMap () throws FileNotFoundException {
        ArrayList<ArrayList<Character>> map = new ArrayList<>();
        try {
            BufferedReader in = new BufferedReader(new FileReader ("levels/" + currState));
            String line;
            while ((line = in.readLine ()) != null){
                ArrayList<Character> currLine = new ArrayList<>();
                for (int i = 0; i < line.length(); i++){
                    currLine.add(line.charAt(i));
                }
                map.add(currLine);
            }
            in.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    void buildMap(){
        for(int i = 0; i < 26; i++){
            for(int j = 0; j < 26; j++){
                char currChar = map.get(j).get(i);
                if(currChar == '#'){
                    Brick brick = new Brick (i * 32, j * 32);
                    bricks.add(brick);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        p1move ();
        bulletMove();
        checkBulletHit();
        checkRemoved();
        repaint();
        Toolkit.getDefaultToolkit().sync();
    }

    void checkRemoved() {
        if (bullets != null) {
            Iterator<Bullet> iterator = bullets.iterator ();
            while (iterator.hasNext ()) {
                Bullet bullet = iterator.next ();
                if (bullet.getDamage () <= 0) {
                    Explore explore = new Explore (bullet.getX () - 32 + bullet.getWidth (), bullet.getY () - 32 + bullet.getHeight ());
                    iterator.remove ();
                    explorings.add (explore);
                }
            }
        }
        if (bricks != null) {
            Iterator<Brick> iterator = bricks.iterator ();
            while (iterator.hasNext ()) {
                Brick brick = iterator.next ();
                if (brick.getHeal () <= 0) {
                    iterator.remove ();
                }
            }
        }
    }

    void checkBulletHit () {
        if (bullets != null) {
            for (Bullet bullet : bullets) {
                if ((bullet.getX () + bullet.getWidth () > screenWidth || bullet.getX () < 0) || (bullet.getY () + bullet.getHeight () > screenHeight || bullet.getY () < 0)) {
                    bullet.getDamaged (bullet.getDamage ());
                }
                for (Brick brick : bricks) {
                    if (isHit (bullet.getX (), bullet.getY (), bullet.getHeight (), bullet.getWidth (), brick.getX (), brick.getY (), brick.getHeight (), brick.getWidth ())) {
                        int dmg = Math.min(bullet.getDamage(), brick.getHeal());
                        brick.getDamaged(dmg);
                        bullet.getDamaged(dmg);
                    }
                }
            }
        }
    }

    public boolean isHit(int o1x, int o1y, int o1h, int o1w, int o2x, int o2y, int o2h, int o2w){
        if(o1x < o2x && o1x + o1w > o2x && o1y < o2y && o1y + o1h > o2y) return true;
        if(o2x < o1x && o2x + o2w > o1x && o2y < o1y && o2y + o2h > o1y) return true;
        if(o1x < o2x && o1x + o1w > o2x && o2y < o1y && o2y + o2h > o1y) return true;
        if(o2x < o1x && o2x + o2w > o1x && o1y < o2y && o1y + o1h > o2y) return true;
        return false;
    }
    void bulletMove(){
        if(bullets != null) {
            for(Bullet bullet : bullets){
                bullet.move ();
            }
        }
    }

    void p1move(){
        if (p1_moveup && p1.canGoUp(bricks)) {
            p1.moveUp();
        }
        if (p1_movedown && p1.canGoDown (bricks)) {
            p1.moveDown();
        }
        if (p1_moveleft && p1.canGoLeft (bricks)) {
            p1.moveLeft();
        }
        if (p1_moveright && p1.canGoRight (bricks)) {
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
        g.drawString ("Game", 10, 35);

        drawMap(g);

        if(bullets != null) {
            for(Bullet bullet : bullets){
                g.drawImage (bullet.getImage (), bullet.getX(), bullet.getY(), bullet.getWidth (), bullet.getHeight (), null);
            }
        }

        g.drawImage (p1.getImage(), p1.getX (), p1.getY (), p1.getWidth (), p1.getHeight (), null);


        if(explorings != null) {
            for(Explore explore : explorings){
                g.drawImage (explore.getImage (), explore.getX(), explore.getY(), 64, 64, null);
            }
        }

        if(tankexplorings != null) {
            for(TankExplore explore : tankexplorings){
                g.drawImage (explore.getImage (), explore.getX (), explore.getY (), explore.getWidth (), explore.getHeight (), null);
            }
        }
    }

    void drawMap(Graphics g){
        if(bricks != null){
            for(Brick brick : bricks){
                g.drawImage (brick.getImage (), brick.getX (), brick.getY (), brick.getWidth (), brick.getHeight (), null);
            }
        }
    }
}


