package controller;

import gui.MainFrame;
import gui.MainPanel;
import gui.Stage2Panel;

public class Controller {
    private final MainFrame mainFrame;


    public Controller () {
        mainFrame = new MainFrame ();
        startMenu ();
    }

    public void startMenu () {
        MainPanel mainPanel = new MainPanel ();
        mainFrame.setContentPane (mainPanel);
        mainFrame.setVisible (true);
    }


}