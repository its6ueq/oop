package gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame () {

        super ("TANK BATTLE");

        setJMenuBar (createMenu ());

        int screenWidth = 676;
        int screenHeight = 676;

        setSize (screenWidth, screenHeight);
        setLayout (new BoxLayout (this.getContentPane (), BoxLayout.Y_AXIS));
        add (Box.createVerticalGlue ());
        setLocationRelativeTo (null);
        setResizable (false);
        setDefaultCloseOperation (DISPOSE_ON_CLOSE);
        setVisible (true);
    }

    private JMenuBar createMenu () {
        var menuBar = new JMenuBar ();
        var fileMenu = new JMenu ("File");
        var exitItem = new JMenuItem ("Exit");

        exitItem.addActionListener (e -> System.exit (0));

        fileMenu.add (exitItem);
        menuBar.add (fileMenu);

        return menuBar;
    }
}
