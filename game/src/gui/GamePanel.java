package gui;

import object.*;
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

    boolean is2P = true;
    int currState = 1;

    public static FirstPlayer p1;
    public static SecondPlayer p2;



    int TIMER_DELAY = 1000/60;
    Timer gameLoop;
    Timer nextState;
    Timer botMove;
    Timer addBullet;

    ArrayList<ArrayList<Character>> map;

    public static ArrayList<Brick> bricks = new ArrayList<> ();
    public static ArrayList<BotTank> botTanks = new ArrayList<> ();
    public static ArrayList<Bullet> bullets = new ArrayList<> ();
    public static ArrayList<Bullet> enermyBullets = new ArrayList<> ();
    ArrayList<Explore> explorings = new ArrayList<> ();
    ArrayList<TankExplore> tankexplorings = new ArrayList<> ();

    GamePanel () throws FileNotFoundException {
        map = readMap();
        buildMap();

        setPreferredSize (new Dimension (screenWidth, screenHeight));
        setFocusable (true);
        addKeyListener (this);

        p1 = new FirstPlayer (300, 784, 10, 1, 2);
        if(is2P){
            p2 = new SecondPlayer (480, 784, 10, 1, 2);
        }

        //exlore
        nextState = new Timer(10, e -> {
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
        });
        nextState.start ();

        botMove = new Timer (1000, e -> {
            if(botTanks != null){
                for (BotTank tank : botTanks){
                    tank.changeMove ();
                }
            }
        });
        botMove.start ();

        addBullet = new Timer (500, e-> {
            p1.loadBullet ();
            p2.loadBullet ();
            if(botTanks != null){
                for(BotTank bot : botTanks)
                    bot.loadBullet ();
            }
        });
        addBullet.start ();

        gameLoop = new Timer(TIMER_DELAY, this);
        gameLoop.start();
    }


    ArrayList<ArrayList<Character>> readMap () throws FileNotFoundException {
        //import map
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
                if(currChar == 'X'){
                    BotTank bot = new BotTank (i * 32, j * 32, 5, 1, 1);
                    botTanks.add(bot);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        p1.move();
        p2.move();
        bottankmove();

        bulletMove();

        checkBulletHit();

        checkRemoved();

        repaint();
        Toolkit.getDefaultToolkit().sync();
    }

    void bottankmove(){
        if(botTanks != null){
            for(BotTank tank : botTanks)
                tank.move ();
        }
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

        if (enermyBullets != null) {
            Iterator<Bullet> iterator = enermyBullets.iterator ();
            while (iterator.hasNext ()) {
                Bullet bullet = iterator.next ();
                if (bullet.getDamage () <= 0) {
                    Explore explore = new Explore (bullet.getX () - 32 + bullet.getWidth (), bullet.getY () - 32 + bullet.getHeight ());
                    iterator.remove ();
                    explorings.add (explore);
                }
            }
        }

        if (botTanks!= null) {
            Iterator<BotTank> iterator = botTanks.iterator ();
            while (iterator.hasNext ()) {
                BotTank bot = iterator.next ();
                if (bot.getHeal () <= 0) {
                    TankExplore explore = new TankExplore (bot.getX () - 24, bot.getY () - 24);
                    iterator.remove ();
                    tankexplorings.add (explore);
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
                for (BotTank bot : botTanks) {
                    if (isHit (bullet.getX (), bullet.getY (), bullet.getHeight (), bullet.getWidth (), bot.getX (), bot.getY (), bot.getHeight (), bot.getWidth ())) {
                        int dmg = Math.min(bullet.getDamage(), bot.getHeal());
                        bot.getDamaged(dmg);
                        bullet.getDamaged(dmg);
                    }
                }
            }
        }
        if (enermyBullets != null) {
            for (Bullet bullet : enermyBullets) {
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
                if(isHit(bullet.getX (), bullet.getY (), bullet.getHeight (), bullet.getWidth (), p1.getX (), p1.getY (), p1.getHeight (), p1.getWidth ())){
                    int dmg = Math.min(bullet.getDamage(), p1.getHeal());
                    p1.getDamaged(dmg);
                    bullet.getDamaged(dmg);
                }
                if(isHit(bullet.getX (), bullet.getY (), bullet.getHeight (), bullet.getWidth (), p2.getX (), p2.getY (), p2.getHeight (), p2.getWidth ())){
                    int dmg = Math.min(bullet.getDamage(), p2.getHeal());
                    p2.getDamaged(dmg);
                    bullet.getDamaged(dmg);
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
                if(bullet != null)
                bullet.move ();
            }
        }

        if(enermyBullets != null) {
            for(Bullet bullet : enermyBullets){
                bullet.move ();
            }
        }
    }



    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        p1.changeMove (key);
        p2.changeMove (key);
    }

    @Override
    public void keyTyped (KeyEvent e) {}

    @Override
    public void keyReleased (KeyEvent e) {
        int key = e.getKeyCode();
        p1.stopMove (key);
        p2.stopMove (key);
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

        if(botTanks != null){
            for(BotTank bot : botTanks){
                g.drawImage (bot.getImage (), bot.getX (), bot.getY (), bot.getWidth (), bot.getHeight (), null);
            }
        }

        if(bullets != null) {
            for(Bullet bullet : bullets){
                g.drawImage (bullet.getImage (), bullet.getX(), bullet.getY(), bullet.getWidth (), bullet.getHeight (), null);
            }
        }

        if(enermyBullets != null) {
            for(Bullet bullet : enermyBullets){
                g.drawImage (bullet.getImage (), bullet.getX(), bullet.getY(), bullet.getWidth (), bullet.getHeight (), null);
            }
        }

        g.drawImage (p1.getImage(), p1.getX (), p1.getY (), p1.getWidth (), p1.getHeight (), null);
        g.drawImage (p2.getImage (), p2.getX (), p2.getY (), p2.getWidth (), p2.getHeight (), null);


        if(explorings != null) {
            for(Explore explore : explorings){
                g.drawImage (explore.getImage (), explore.getX(), explore.getY(), explore.getWidth (), explore.getHeight (), null);
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


