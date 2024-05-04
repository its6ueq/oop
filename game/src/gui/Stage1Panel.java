package gui;

import game.object.Tank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;

public class Stage1Panel extends JPanel implements ActionListener, KeyListener {

    Image p1IMage;

    Tank p1 = Tank.getInstance (0, 0, 0, 0, 10, p1IMage);


    Stage1Panel () {
        setFocusable (true);
        addKeyListener (this);

        p1IMage = new ImageIcon(Objects.requireNonNull(getClass().getResource("0.gif"))).getImage();

    }

    @Override
    public void actionPerformed (ActionEvent e) {

    }

    @Override
    public void keyTyped (KeyEvent e) {

    }

    @Override
    public void keyPressed (KeyEvent e) {

    }

    @Override
    public void keyReleased (KeyEvent e) {

    }

    @Override
    public void paintComponent (Graphics g) {
        super.paintComponent (g);
        draw (g);
    }

    public void draw (Graphics g) {
        g.drawImage (p1IMage, 0, 0, this);

        g.setFont (new Font ("Arial", Font.PLAIN, 32));
        g.drawString ("Game Over: ", 10, 35);


    }
}

