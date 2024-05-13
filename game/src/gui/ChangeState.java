package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;

import static gui.MainFrame.currState;

public class ChangeState extends JPanel implements ActionListener, KeyListener {
    Timer time;

    public ChangeState () {
        setFocusable (true);
        addKeyListener (this);

        currState++;
        if (currState > 0 && currState <= 35) {
            repaint ();
            time = new Timer (1000, _ -> {
                Controller.gamePlay ();
            });
            time.start ();
            time.setRepeats (false);
        } else {
            repaint ();

        }
    }

    @Override
    public void actionPerformed (ActionEvent e) {
        repaint ();
        Toolkit.getDefaultToolkit ().sync ();
    }

    @Override
    public void paintComponent (Graphics g) {
        super.paintComponent (g);
        if (currState != 36) {
            try {
                drawState (g);
            } catch (IOException | FontFormatException e) {
                throw new RuntimeException (e);
            }
        } else {
            try {
                drawVictory (g);
            } catch (IOException | FontFormatException e) {
                throw new RuntimeException (e);
            }

        }
    }

    void drawState (Graphics g) throws IOException, FontFormatException {
        setBackground (Color.BLACK);
        InputStream is1 = MainPanel.class.getResourceAsStream ("/Font/prstartk.ttf");
        assert is1 != null;
        Font gamefont = Font.createFont (Font.TRUETYPE_FONT, is1);
        Font st = gamefont.deriveFont (60f);
        String state = "State " + currState;
        g.setFont (st);
        g.setColor (Color.WHITE);
        g.drawString (state, getXforCenteredText (state, g), 832 / 2);
    }

    void drawVictory (Graphics g) throws IOException, FontFormatException {
        endScene ("VICTORY");
    }

    void drawDefeat (Graphics g) throws IOException, FontFormatException {
        endScene ("DEFEAT");
    }

    void endScene (String string) throws IOException, FontFormatException {
        setBackground (Color.BLACK);
        InputStream is1 = MainPanel.class.getResourceAsStream ("/Font/prstartk.ttf");
        assert is1 != null;
        Font gamefont = Font.createFont (Font.TRUETYPE_FONT, is1);
        Font st = gamefont.deriveFont (90f);
        g.setFont (st);
        g.setColor (Color.WHITE);
        g.drawString (string, getXforCenteredText (string, g), 832 / 2);

        st = gamefont.deriveFont (20f);
        String conti = "Press any key to continue";
        g.setFont (st);
        g.setColor (Color.WHITE);
        g.drawString (conti, getXforCenteredText (conti, g), 832 / 10 * 6);
    }

    private int getXforCenteredText (String text, Graphics g) {
        int length = (int) g.getFontMetrics ().getStringBounds (text, g).getWidth ();
        return 832 / 2 - length / 2;
    }

    @Override
    public void keyTyped (KeyEvent e) {

    }

    @Override
    public void keyPressed (KeyEvent e) {
        int key = e.getKeyCode ();
        if (key != KeyEvent.VK_ESCAPE) {
            System.out.println ("DONE");
            if (time != null)
                time.stop ();
            Controller.startMenu ();
        }

    }

    @Override
    public void keyReleased (KeyEvent e) {

    }
}
