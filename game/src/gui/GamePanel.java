package gui;

import game.object.Tank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    int screenWidth = 676;
    int screenHeight = 676;

    boolean p1_moveup = false;
    boolean p1_movedown = false;
    boolean p1_moveright = false;
    boolean p1_moveleft = false;

    Tank p1;

    int TIMER_DELAY = 16;
    Timer gameLoop;

    GamePanel () {
        setPreferredSize (new Dimension (screenWidth, screenHeight));

        setFocusable (true);
        addKeyListener (this);
        p1 = new Tank(10, 10 , 20, 20, 10, 1, 3);

        gameLoop = new Timer(TIMER_DELAY, this);
        gameLoop.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
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
        repaint();
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

        g.drawImage (p1.image(), p1.getX (), p1.getY (), 32, 32, null);

    }
}

