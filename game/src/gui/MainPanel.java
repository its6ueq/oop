package gui;

import controller.Controller;
import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class MainPanel extends JPanel {
    public MainPanel () {
        var contentPanel = new JPanel ();
        GroupLayout groupLayout = new GroupLayout (contentPanel);
        contentPanel.setLayout (groupLayout);
        groupLayout.setAutoCreateGaps (true);
        groupLayout.setAutoCreateContainerGaps (true);


        var name = new JLabel ("TEN GAME");
        name.setFont (new Font (name.getFont ().getName (), name.getFont ().getStyle (), 40));

        var nameField = new JTextField ("Enter your name");
        nameField.setMinimumSize (new Dimension (150, 30));
        nameField.setMaximumSize (new Dimension (150, 30));

        var newGame = new JButton ("New Game");

        newGame.setMinimumSize (new Dimension (150, 30));
        newGame.setMaximumSize (new Dimension (150, 30));

        newGame.addActionListener (new ActionListener () {
            @Override
            public void actionPerformed (ActionEvent e) {
                Stage1Panel stage1Panel = new Stage1Panel ();
                JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor (MainPanel.this);
                mainFrame.setContentPane (stage1Panel);
                mainFrame.setVisible (true);
            }
        });

        var scoreBoard = new JButton ("Score Board");
        scoreBoard.setMinimumSize (new Dimension (150, 30));
        scoreBoard.setMaximumSize (new Dimension (150, 30));

        var exit = new JButton ("Exit");
        exit.setMinimumSize (new Dimension (150, 30));
        exit.setMaximumSize (new Dimension (150, 30));

        //        setBackground (Color.BLACK);
        //        newGame.setBackground (Color.BLACK);
        //        scoreBoard.setBackground (Color.BLACK);
        //        exit.setBackground (Color.BLACK);

        groupLayout.setHorizontalGroup (groupLayout.createParallelGroup (GroupLayout.Alignment.CENTER).addGap (100).addComponent (name).addGap (30).addComponent (nameField).addComponent (newGame).addComponent (scoreBoard).addComponent (exit));
        groupLayout.setVerticalGroup (groupLayout.createSequentialGroup ().addGap (100).addComponent (name).addGap (30).addComponent (nameField).addComponent (newGame).addComponent (scoreBoard).addComponent (exit));
        add (contentPanel);
    }
}
