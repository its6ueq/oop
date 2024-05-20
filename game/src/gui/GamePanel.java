package gui;

import controller.Controller;
import object.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import static gui.MainFrame.currState;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    public static boolean is2P = true;

    public static FirstPlayer p1;
    public static SecondPlayer p2;
    public static Ally ally;
    public static ArrayList<StaticObject> bricks;
    public static ArrayList<StaticObject> stones;
    public static ArrayList<StaticObject> waters;
    public static ArrayList<StaticObject> bushes;
    public static ArrayList<BotTank> botTanks;
    public static ArrayList<Bullet> bullets;
    public static ArrayList<Bullet> enermyBullets;
    public int screenWidth;
    public int screenHeight;
    int TIMER_DELAY;
    Timer gameLoop;
    Timer nextState;
    Timer addBullet;
    public static ArrayList<ArrayList<Character>> map;
    public static int[][] bestPath = new int[30][30];
    static int[][] mapp = new int[30][30];
    public static int[] cx = {0, 1, 0, -1};
    public static int[] cy = {1, 0, -1, 0};

    ArrayList<Explore> explorings;
    ArrayList<TankExplore> tankexplorings;

    int enemyTanks;
    int allyTanks;

    Sound sound;
    public GamePanel () {
        sound = new Sound ();
        System.out.println("Start state: " + currState);
        screenWidth = 832;
        screenHeight = 832;
        TIMER_DELAY = 1000 / 60;
        bullets = new ArrayList<> ();
        enermyBullets = new ArrayList<> ();
        enemyTanks = 0;
        allyTanks = 1;

        p1 = new FirstPlayer (300, 784, 10, 1, 2);

        if (is2P) {
            p2 = new SecondPlayer (480, 784, 10, 1, 2);
            allyTanks++;
        }

        ally = new Ally (386, 775, 10);

        bricks = new ArrayList<> ();
        stones = new ArrayList<> ();
        waters = new ArrayList<> ();
        bushes = new ArrayList<> ();
        botTanks = new ArrayList<> ();
        explorings = new ArrayList<> ();
        tankexplorings = new ArrayList<> ();

        map = readMap ();
        buildMap ();

        setPreferredSize (new Dimension (screenWidth, screenHeight));
        setFocusable (true);
        addKeyListener (this);


        //exlore
        nextState = new Timer (35, _ -> {
            if (!explorings.isEmpty ()) {
                explorings.removeIf (explore -> explore.nextState () == 5);
            }
            if (!tankexplorings.isEmpty ()) {
                tankexplorings.removeIf (explore -> explore.nextState () == 7);
            }
        });
        nextState.start ();

        addBullet = new Timer (500, _ -> {
            if (p1 != null) {
                p1.loadBullet ();
            }
            if (p2 != null) {
                p2.loadBullet ();
            }

            if (botTanks != null) {
                for (BotTank bot : botTanks)
                    bot.loadBullet ();
            }
        });
        addBullet.start ();

        gameLoop = new Timer (TIMER_DELAY, this);
        gameLoop.start ();
    }

    void defeat () {
        System.out.println("Defeat");
        currState = -1;
        addBullet.stop ();
        gameLoop.stop ();
        Controller.changeState ();
    }

    void victory () {
        System.out.println("State complete");
        addBullet.stop ();
        gameLoop.stop ();
        Controller.changeState ();
    }

    ArrayList<ArrayList<Character>> readMap () {
        //import map
        ArrayList<ArrayList<Character>> map = new ArrayList<> ();
        try {
            BufferedReader in = new BufferedReader (new FileReader ("resource/levels/" + currState));
            String line;
            while ((line = in.readLine ()) != null) {
                ArrayList<Character> currLine = new ArrayList<> ();
                for (int i = 0; i < line.length (); i++) {
                    currLine.add (line.charAt (i));
                }
                map.add (currLine);
            }
            in.close ();
        } catch (IOException e) {
            e.printStackTrace ();
        }
        System.out.println ("Map imported");

        return map;
    }

    void buildMap () {
        //import map
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < 26; j++) {
                bestPath[i][j] = 999999;
                if (map.get (i).get (j) == '#') {
                    mapp[i][j] = 500;
                } else if (map.get (i).get (j) == '@' || map.get (i).get (j) == '~') {
                    mapp[i][j] = 9999;
                } else {
                    mapp[i][j] = 1;
                }
            }
            mapp[i][26] = 9999;
            mapp[26][i] = 9999;
        }

        bestPath[24][13] = 500;
        bestPath[24][12] = 0;
        bestPath[25][13] = 500;
        bestPath[25][12] = 0;

        recur (24, 13);
        recur (24, 12);
        recur (25, 13);
        recur (25, 12);
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < 26; j++) {
                char currChar = map.get (j).get (i);
                if (currChar == '#') {
                    StaticObject brick = new StaticObject (i * 32, j * 32, 0);
                    bricks.add (brick);
                }
                if (currChar == '@') {
                    StaticObject stone = new StaticObject (i * 32, j * 32, 1);
                    stones.add (stone);
                }
                if (currChar == '~') {
                    StaticObject water = new StaticObject (i * 32, j * 32, 2);
                    waters.add (water);
                }
                if (currChar == '%') {
                    StaticObject bush = new StaticObject (i * 32, j * 32, 3);
                    bushes.add (bush);
                }
                if (currChar == 'X') {
                    BotTank bot;
                    if(is2P) {
                        bot = new BotTank (i * 32 + 8, j * 32 + 8, 7, 1, 1);
                    } else {
                        bot = new BotTank (i * 32 + 8, j * 32 + 8, 3, 1, 1);
                    }
                    botTanks.add (bot);
                    enemyTanks++;
                }
            }
        }
        System.out.println ("Map built");
    }

    private static void recur (int currX, int currY) {
        for (int i = 0; i < 4; i++) {
            if (currX + cx[i] < 26 && currX + cx[i] >= 0 && currY + cy[i] < 26 && currY + cy[i] >= 0) {
                if(bestPath[currX + cx[i]][currY + cy[i]] > bestPath[currX][currY] + mapp[currX + cx[i]][currY + cy[i]] + mapp[currX + cx[i] + 1][currY + cy[i]] + mapp[currX + cx[i]][currY + cy[i] + 1] + mapp[currX + cx[i] + 1][currY + cy[i] + 1]){
                    bestPath[currX + cx[i]][currY + cy[i]] = bestPath[currX][currY] + mapp[currX + cx[i]][currY + cy[i]] + mapp[currX + cx[i] + 1][currY + cy[i]] + mapp[currX + cx[i]][currY + cy[i] + 1] + mapp[currX + cx[i] + 1][currY + cy[i] + 1];
                    recur (currX + cx[i], currY + cy[i]);
                }
            }
        }
    }

    @Override
    public void actionPerformed (ActionEvent e) {
        if (p1 != null)
            p1.move ();
        if (p2 != null)
            p2.move ();
        bottankmove ();
        bulletMove ();
        checkBulletHit ();
        checkRemoved ();
        repaint ();
        Toolkit.getDefaultToolkit ().sync ();
    }

    void bottankmove () {
        if (botTanks != null) {
            for (BotTank bot : botTanks)
                bot.botMove ();
        }
    }

    void checkRemoved () {
        if (bullets != null) {
            Iterator<Bullet> iterator = bullets.iterator ();
            while (iterator.hasNext ()) {
                Bullet bullet = iterator.next ();
                if (bullet.getDamage () <= 0) {
                    sound.playSE (2);
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
                    sound.playSE (2);
                    Explore explore = new Explore (bullet.getX () - 32 + bullet.getWidth (), bullet.getY () - 32 + bullet.getHeight ());
                    iterator.remove ();
                    explorings.add (explore);

                }
            }
        }

        if (botTanks != null) {
            Iterator<BotTank> iterator = botTanks.iterator ();
            while (iterator.hasNext ()) {
                BotTank bot = iterator.next ();
                if (bot.getHeal () <= 0) {
                    sound.playSE (4);
                    TankExplore explore = new TankExplore (bot.getX () - 24, bot.getY () - 24);
                    iterator.remove ();
                    tankexplorings.add (explore);
                    enemyTanks--;
                    System.out.println ("Bot Tank destroyed");
                    if (enemyTanks <= 0)
                        victory ();
                }
            }
        }

        if (p1 != null) {
            if (p1.getHeal () <= 0) {
                sound.playSE (5);
                TankExplore explore = new TankExplore (p1.getX () - 24, p1.getY () - 24);
                tankexplorings.add (explore);
                p1 = null;
                allyTanks--;
                System.out.println ("Player 1 destroyed");
                if (allyTanks == 0)
                    defeat ();
            }
        }

        if (p2 != null) {
            if (p2.getHeal () <= 0) {
                sound.playSE (5);
                TankExplore explore = new TankExplore (p2.getX () - 24, p2.getY () - 24);
                tankexplorings.add (explore);
                p2 = null;
                allyTanks--;
                System.out.println ("Player 2 destroyed");
                if (allyTanks == 0)
                    defeat ();
            }
        }

        if (bricks != null) {
            bricks.removeIf (brick -> brick.getHeal () <= 0);
        }
    }

    void checkBulletHit () {
        if (bullets != null) {
            for (Bullet bullet : bullets) {
                if ((bullet.getX () + bullet.getWidth () > screenWidth || bullet.getX () < 0) || (bullet.getY () + bullet.getHeight () > screenHeight || bullet.getY () < 0)) {
                    bullet.getDamaged (bullet.getDamage ());
                }

                for (StaticObject brick : bricks) {
                    if (isHit (bullet.getX (), bullet.getY (), bullet.getHeight (), bullet.getWidth (), brick.getX (), brick.getY (), brick.getHeight (), brick.getWidth ())) {
                        int dmg = Math.min (bullet.getDamage (), brick.getHeal ());
                        brick.getDamaged (dmg);
                        bullet.getDamaged (dmg);
                    }
                }

                for (StaticObject stone : stones) {
                    if (isHit (bullet.getX (), bullet.getY (), bullet.getHeight (), bullet.getWidth (), stone.getX (), stone.getY (), stone.getHeight (), stone.getWidth ())) {
                        bullet.getDamaged (stone.getHeal ());
                    }
                }

                for (BotTank bot : botTanks) {
                    if (isHit (bullet.getX (), bullet.getY (), bullet.getHeight (), bullet.getWidth (), bot.getX (), bot.getY (), bot.getHeight (), bot.getWidth ())) {
                        int dmg = Math.min (bullet.getDamage (), bot.getHeal ());
                        bot.getDamaged (dmg);
                        bullet.getDamaged (dmg);
                    }
                }

                if(p1 != null){
                    if (isHit (bullet.getX (), bullet.getY (), bullet.getHeight (), bullet.getWidth (), p1.getX (), p1.getY (), p1.getHeight (), p1.getWidth ())) {
                        bullet.getDamaged (bullet.getDamage ());
                    }
                }

                if(p2 != null){
                    if (isHit (bullet.getX (), bullet.getY (), bullet.getHeight (), bullet.getWidth (), p2.getX (), p2.getY (), p2.getHeight (), p2.getWidth ())) {
                        bullet.getDamaged (bullet.getDamage ());
                    }
                }

                if(ally != null){
                    if (isHit (bullet.getX (), bullet.getY (), bullet.getHeight (), bullet.getWidth (), ally.getX (), ally.getY (), ally.getHeight (), ally.getWidth ())) {
                        bullet.getDamaged (bullet.getDamage ());
                    }
                }
            }
        }
        if (enermyBullets != null) {
            for (Bullet bullet : enermyBullets) {
                if ((bullet.getX () + bullet.getWidth () > screenWidth || bullet.getX () < 0) || (bullet.getY () + bullet.getHeight () > screenHeight || bullet.getY () < 0)) {
                    bullet.getDamaged (bullet.getDamage ());
                }

                for (StaticObject brick : bricks) {
                    if (isHit (bullet.getX (), bullet.getY (), bullet.getHeight (), bullet.getWidth (), brick.getX (), brick.getY (), brick.getHeight (), brick.getWidth ())) {
                        int dmg = Math.min (bullet.getDamage (), brick.getHeal ());
                        brick.getDamaged (dmg);
                        bullet.getDamaged (dmg);
                    }
                }

                for (StaticObject stone : stones) {
                    if (isHit (bullet.getX (), bullet.getY (), bullet.getHeight (), bullet.getWidth (), stone.getX (), stone.getY (), stone.getHeight (), stone.getWidth ())) {
                        int dmg = Math.min (bullet.getDamage (), stone.getHeal ());
                        stone.getDamaged (dmg);
                        bullet.getDamaged (dmg);
                    }
                }

                if (p1 != null) {
                    if (isHit (bullet.getX (), bullet.getY (), bullet.getHeight (), bullet.getWidth (), p1.getX (), p1.getY (), p1.getHeight (), p1.getWidth ())) {
                        int dmg = Math.min (bullet.getDamage (), p1.getHeal ());
                        p1.getDamaged (dmg);
                        bullet.getDamaged (dmg);
                    }
                }
                if (p2 != null) {
                    if (isHit (bullet.getX (), bullet.getY (), bullet.getHeight (), bullet.getWidth (), p2.getX (), p2.getY (), p2.getHeight (), p2.getWidth ())) {
                        int dmg = Math.min (bullet.getDamage (), p2.getHeal ());
                        p2.getDamaged (dmg);
                        bullet.getDamaged (dmg);
                    }
                }

                if (ally != null) {
                    if (isHit (bullet.getX (), bullet.getY (), bullet.getHeight (), bullet.getWidth (), ally.getX (), ally.getY (), ally.getHeight (), ally.getWidth ())) {
                        int dmg = Math.min (bullet.getDamage (), ally.getHeal ());
                        ally.getDamaged (dmg);
                        bullet.getDamaged (dmg);
                        if(ally.getHeal () == 0) {
                            defeat ();
                        }
                    }
                }
            }
        }
    }

    public boolean isHit (int o1x, int o1y, int o1h, int o1w, int o2x, int o2y, int o2h, int o2w) {
        if (o1x < o2x && o1x + o1w > o2x && o1y < o2y && o1y + o1h > o2y)
            return true;
        if (o2x < o1x && o2x + o2w > o1x && o2y < o1y && o2y + o2h > o1y)
            return true;
        if (o1x < o2x && o1x + o1w > o2x && o2y < o1y && o2y + o2h > o1y)
            return true;
        return o2x < o1x && o2x + o2w > o1x && o1y < o2y && o1y + o1h > o2y;
    }

    void bulletMove () {
        if (bullets != null) {
            for (Bullet bullet : bullets) {
                if (bullet != null)
                    bullet.move ();
            }
        }

        if (enermyBullets != null) {
            for (Bullet bullet : enermyBullets) {
                bullet.move ();
            }
        }
    }

    @Override
    public void keyPressed (KeyEvent e) {
        int key = e.getKeyCode ();
        if (p1 != null) {
            p1.changeMove (key);
        }
        if (p2 != null) {
            p2.changeMove (key);
        }
    }

    @Override
    public void keyTyped (KeyEvent e) {
    }

    @Override
    public void keyReleased (KeyEvent e) {
        int key = e.getKeyCode ();
        if (p1 != null) {
            p1.stopMove (key);
        }
        if (p2 != null) {
            p2.stopMove (key);
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
        g.setColor (Color.WHITE);
//        g.drawString ("Nhìn source code bẩn mắt vãi cứt !!", 10, 35);


        if (waters != null) {
            for (StaticObject water : waters) {
                g.drawImage (water.getImage (), water.getX (), water.getY (), water.getWidth (), water.getHeight (), null);
            }
        }

        drawTank (g);
        drawMap (g);
        drawExplore (g);
        drawHP(g);
    }

    void drawHP(Graphics g){
        if (p1 != null) {
            g.setColor (Color.BLACK);
            g.fillRect (p1.getX(), p1.getY() - 12, p1.getWidth (), 5);
            g.setColor (Color.GREEN);
            double currHP = (double) p1.getWidth () * p1.getHeal () / p1.getMaxHeal ();
            g.fillRect (p1.getX(), p1.getY() - 12, (int)currHP, 5);

            g.setColor (Color.WHITE);
            g.drawRect (p1.getX(), p1.getY() - 12, p1.getWidth (), 5);
        }
        if (p2 != null) {
            g.setColor (Color.BLACK);
            g.fillRect (p2.getX(), p2.getY() - 12, p2.getWidth (), 5);
            g.setColor (Color.GREEN);
            double currHP = (double) p2.getWidth () * p2.getHeal () / p2.getMaxHeal ();
            g.fillRect (p2.getX(), p2.getY() - 12, (int)currHP, 5);
            g.setColor (Color.WHITE);
            g.drawRect (p2.getX(), p2.getY() - 12, p2.getWidth (), 5);
        }
        if (botTanks != null) {
            for (BotTank bot : botTanks) {
                g.setColor (Color.BLACK);
                g.fillRect (bot.getX(), bot.getY() - 12, bot.getWidth (), 5);
                g.setColor (Color.RED);
                double currHP = (double) bot.getWidth () * bot.getHeal () / bot.getMaxHeal ();
                g.fillRect (bot.getX(), bot.getY() - 12, (int)currHP, 5);
                g.setColor (Color.WHITE);
                g.drawRect (bot.getX(), bot.getY() - 12, bot.getWidth (), 5);
            }
        }
        if (ally != null) {
            g.setColor (Color.BLACK);
            g.fillRect (ally.getX(), ally.getY() - 8, ally.getWidth (), 5);
            g.setColor (Color.BLUE);
            double currHP = (double) ally.getWidth () * ally.getHeal () / ally.getMaxHeal ();
            g.fillRect (ally.getX(), ally.getY() - 8, (int)currHP, 5);
            g.setColor (Color.BLACK);
            g.drawRect (ally.getX(), ally.getY() - 8, ally.getWidth (), 5);
        }
    }

    void drawTank (Graphics g) {
        if (ally != null) {
            g.drawImage (ally.getImage (), ally.getX (), ally.getY (), ally.getWidth (), ally.getHeight (), null);
        }

        if (p1 != null) {
            g.drawImage (p1.getImage (), p1.getX (), p1.getY (), p1.getWidth (), p1.getHeight (), null);
        }

        if (p2 != null) {
            g.drawImage (p2.getImage (), p2.getX (), p2.getY (), p2.getWidth (), p2.getHeight (), null);
        }

        if (botTanks != null) {
            for (BotTank bot : botTanks) {
                g.drawImage (bot.getImage (), bot.getX (), bot.getY (), bot.getWidth (), bot.getHeight (), null);
            }
        }

        if (bullets != null) {
            for (Bullet bullet : bullets) {
                g.drawImage (bullet.getImage (), bullet.getX (), bullet.getY (), bullet.getWidth (), bullet.getHeight (), null);
            }
        }

        if (enermyBullets != null) {
            for (Bullet bullet : enermyBullets) {
                g.drawImage (bullet.getImage (), bullet.getX (), bullet.getY (), bullet.getWidth (), bullet.getHeight (), null);
            }
        }
    }

    void drawMap (Graphics g) {
        if (bricks != null) {
            for (StaticObject brick : bricks) {
                g.drawImage (brick.getImage (), brick.getX (), brick.getY (), brick.getWidth (), brick.getHeight (), null);
            }
        }
        if (bushes != null) {
            for (StaticObject bush : bushes) {
                g.drawImage (bush.getImage (), bush.getX (), bush.getY (), bush.getWidth (), bush.getHeight (), null);
            }
        }
        if (stones != null) {
            for (StaticObject stone : stones) {
                g.drawImage (stone.getImage (), stone.getX (), stone.getY (), stone.getWidth (), stone.getHeight (), null);
            }
        }


    }

    void drawExplore (Graphics g) {
        if (explorings != null) {
            for (Explore explore : explorings) {
                g.drawImage (explore.getImage (), explore.getX (), explore.getY (), explore.getWidth (), explore.getHeight (), null);
            }
        }

        if (tankexplorings != null) {
            for (TankExplore explore : tankexplorings) {
                g.drawImage (explore.getImage (), explore.getX (), explore.getY (), explore.getWidth (), explore.getHeight (), null);
            }
        }
    }
}


