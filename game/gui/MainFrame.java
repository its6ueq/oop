package game.gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame () {
        super ("Swing Demo");


        setJMenuBar (createMenu ());

        setSize (800, 800);

        setLayout (new BoxLayout (this.getContentPane (), BoxLayout.Y_AXIS));
        add (Box.createVerticalGlue ());
        setLocationRelativeTo (null);
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
