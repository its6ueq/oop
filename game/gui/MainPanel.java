package game.gui;

import game.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import game.controller.Controller;

public class MainPanel extends JPanel {
    public MainPanel(){
        var contentPanel = new JPanel ();
        GroupLayout groupLayout = new GroupLayout (contentPanel);
        contentPanel.setLayout(groupLayout);
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);

        var name = new JLabel("TEN GAME");
        name.setFont(new Font(name.getFont().getName(), name.getFont().getStyle(), 40));

        var nameField = new JTextField ("Enter your name");
        nameField.setMinimumSize(new Dimension(150, 30));
        nameField.setMaximumSize(new Dimension(150, 30));

        var newGame = new JButton("New Game");
        newGame.setMinimumSize(new Dimension(150, 30));
        newGame.setMaximumSize(new Dimension(150, 30));
        newGame.addActionListener (new ActionListener () {
            public void actionPerformed (ActionEvent e) {
                Controller controller = new Controller ();
                controller.gamePlay();
            }
        });

        var scoreBoard = new JButton ("Score Board");
        scoreBoard.setMinimumSize(new Dimension(150, 30));
        scoreBoard.setMaximumSize(new Dimension(150, 30));

        var exit = new JButton ("Exit");
        exit.setMinimumSize(new Dimension(150, 30));
        exit.setMaximumSize(new Dimension(150, 30));

        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent (name)
                        .addGap (30)
                        .addComponent (nameField)
                        .addComponent (newGame)
                        .addComponent (scoreBoard)
                        .addComponent (exit)
        );
        groupLayout.setVerticalGroup(
                groupLayout.createSequentialGroup()
                        .addComponent (name)
                        .addGap (30)
                        .addComponent (nameField)
                        .addComponent (newGame)
                        .addComponent (scoreBoard)
                        .addComponent (exit)
        );

        add(contentPanel);
    }


}
