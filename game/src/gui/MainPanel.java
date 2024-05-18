package gui;

import controller.Controller;
import object.Sound;
import object.Tank;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static gui.GamePanel.is2P;


public class MainPanel extends JPanel implements ActionListener, KeyListener {

    public final int TIMER_DELAY;
    int currPick;
    Timer gameLoop;
    Sound sound;

    public MainPanel () {
        sound = new Sound ();
        sound.playMusic (0);
        TIMER_DELAY = 60;
        currPick = 0;

        setFocusable (true);
        addKeyListener (this);

        gameLoop = new Timer (TIMER_DELAY, this);
        gameLoop.start ();

        var contentPanel = new JPanel ();
        GroupLayout groupLayout = new GroupLayout (contentPanel);
        contentPanel.setLayout (groupLayout);
        groupLayout.setAutoCreateGaps (true);
        groupLayout.setAutoCreateContainerGaps (true);
        add (contentPanel);
    }

    protected static Image extractTankImage (int x, int y) {
        Image textureImage = new ImageIcon (Objects.requireNonNull (Tank.class.getResource ("/texture/texture.png"))).getImage ();
        BufferedImage texture = new BufferedImage (textureImage.getWidth (null), textureImage.getHeight (null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = texture.createGraphics ();
        g2d.drawImage (textureImage, 0, 0, null);
        g2d.dispose ();
        return texture.getSubimage (x, y, 32, 32);
    }

    @Override
    public void paintComponent (Graphics g) {
        super.paintComponent (g);
        try {
            drawMenu (g);
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException (e);
        }
    }

    public void drawMenu (Graphics g) throws IOException, FontFormatException {
        setBackground (Color.BLACK);
        drawText (g);
    }

    void drawText (Graphics g) throws IOException, FontFormatException {

        InputStream is = MainPanel.class.getResourceAsStream ("/font/tank_font.ttf");
        assert is != null;
        Font tankfont = Font.createFont (Font.TRUETYPE_FONT, is);
        Font sizedFont = tankfont.deriveFont (96f);
        String gameName = "TANKS";
        g.setFont (sizedFont);
        g.setColor (Color.RED);
        g.drawString (gameName, getXforCenteredText (gameName, g), 832 / 3);

        InputStream is1 = MainPanel.class.getResourceAsStream ("/font/prstartk.ttf");
        assert is1 != null;
        Font gamefont = Font.createFont (Font.TRUETYPE_FONT, is1);
        Font sized1Font = gamefont.deriveFont (30f);

        String mode1 = "1 Player";
        g.setFont (sized1Font);
        g.setColor (Color.WHITE);
        g.drawString (mode1, 300, 832 / 3 * 2 - 100);

        String mode2 = "2 Players";
        g.setFont (sized1Font);
        g.setColor (Color.WHITE);
        g.drawString (mode2, 300, 832 / 3 * 2 - 25);

        String exit = "Exit";
        g.setFont (sized1Font);
        g.setColor (Color.WHITE);
        g.drawString (exit, 300, 832 / 3 * 2 + 50);

        Image tank = extractTankImage (13 * 32, 0);

        if (currPick == 0) {
            g.drawImage (tank, 245, 420, 40, 40, null);
        } else if (currPick == 1) {
            g.drawImage (tank, 245, 495, 40, 40, null);
        } else if (currPick == 2) {
            g.drawImage (tank, 245, 570, 40, 40, null);
        }
    }

    private int getXforCenteredText (String text, Graphics g) {
        int length = (int) g.getFontMetrics ().getStringBounds (text, g).getWidth ();
        int x = 832 / 2 - length / 2;
        return x;
    }

    @Override
    public void actionPerformed (ActionEvent e) {
        repaint ();
        Toolkit.getDefaultToolkit ().sync ();
    }

    @Override
    public void keyTyped (KeyEvent e) {

    }

    @Override
    public void keyPressed (KeyEvent e) {
        int key = e.getKeyCode ();
        if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
            sound.playSE (2);
            currPick--;
            if (currPick < 0)
                currPick = 2;
            repaint ();
        }
        if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
            sound.playSE (2);
            currPick++;
            if (currPick > 2)
                currPick = 0;
            repaint ();
        }
        if (key == KeyEvent.VK_ENTER) {
            sound.stopMusic (0);
            switch (currPick) {
                case 0:
                    System.out.println ("Playing alone:(");
                    is2P = false;
                    break;
                case 1:
                    System.out.println ("Playing with friend:)");
                    is2P = true;
                    break;
                case 2:
                    System.out.println ("Exiting the game but why :(?");
                    System.exit (0);
                    break;
            }
            gameLoop.stop ();
            Controller.changeState ();
        }
    }

    @Override
    public void keyReleased (KeyEvent e) {

    }
}
