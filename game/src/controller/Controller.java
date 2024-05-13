package controller;

import gui.ChangeState;
import gui.GamePanel;
import gui.MainFrame;
import gui.MainPanel;

import javax.swing.*;

public class Controller {
    static MainPanel mainPanel;
    static ChangeState changeState;
    static GamePanel gamePanel;
    private static MainFrame mainFrame = null;
    private static JFrame currentFrame;

    public Controller () {
        mainFrame = new MainFrame ();
        startMenu ();
    }

    public static void changeState () {
        currentFrame = (JFrame) SwingUtilities.getWindowAncestor (mainPanel);
        //        currentFrame.dispose ();
        changeState = new ChangeState ();
        mainFrame.setContentPane (changeState);
        changeState.requestFocus ();
        mainFrame.setVisible (true);
    }

    public static void gamePlay () {
        currentFrame = (JFrame) SwingUtilities.getWindowAncestor (changeState);
        //        currentFrame.dispose ();
        gamePanel = new GamePanel ();
        mainFrame.setContentPane (gamePanel);
        gamePanel.requestFocusInWindow ();
        mainFrame.setVisible (true);
    }

    public static void startMenu () {
        if (changeState != null)
            currentFrame = (JFrame) SwingUtilities.getWindowAncestor (changeState);
        mainPanel = new MainPanel ();
        mainFrame.setContentPane (mainPanel);
        mainPanel.requestFocus ();
        mainFrame.setVisible (true);
    }
}
