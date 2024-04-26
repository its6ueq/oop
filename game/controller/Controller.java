package game.controller;

import game.gui.MainFrame;
import game.gui.MainPanel;
import game.gui.GamePanel;
public class Controller {
    private MainFrame mainFrame;
    private MainPanel mainPanel;
    private GamePanel gamePanel;
    public Controller () {
        mainFrame = new MainFrame ();
        startMenu ();
    }

    public void startMenu(){
        mainPanel = new MainPanel ();
        mainFrame.setContentPane (mainPanel);
        mainFrame.setVisible (true);
    }

    public void gamePlay(){
        gamePanel = new GamePanel ();
        mainFrame.setContentPane (gamePanel);
        mainFrame.setVisible (true);
    }

}
