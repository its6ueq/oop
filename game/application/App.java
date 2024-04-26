package game.application;

import game.controller.Controller;

import javax.swing.*;

public class App {
    public static void main (String[] args) {
        SwingUtilities.invokeLater (Controller::new);
    }
}
