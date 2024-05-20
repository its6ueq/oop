package gui;

import javax.swing.*;

public class MainFrame extends JFrame {
    public static int currState = 0;

    public MainFrame () {
        super ("TANK BATTLE");
        System.out.println("Created JFrame");
        setJMenuBar (createMenu ());

        int screenWidth = 832;
        int screenHeight = 892;

        setSize (screenWidth, screenHeight);
        setLayout (new BoxLayout (this.getContentPane (), BoxLayout.Y_AXIS));
        add (Box.createVerticalGlue ());
        setLocationRelativeTo (null);
        setResizable (false);
        setDefaultCloseOperation (EXIT_ON_CLOSE);
        setVisible (true);
    }

    private JMenuBar createMenu () {
        var menuBar = new JMenuBar ();
        var fileMenu = new JMenu ("File");
        var exitItem = new JMenuItem ("Exit");

        exitItem.addActionListener (_ -> System.exit (0));

        fileMenu.add (exitItem);
        menuBar.add (fileMenu);

        return menuBar;
    }
}
