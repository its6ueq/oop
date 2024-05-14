package application;

import controller.Controller;

import javax.swing.*;

public class App {
    public static void main (String[] args) {
        System.out.println("Run application");
        SwingUtilities.invokeLater (Controller::new);
    }
}
