package application;

import controller.Controller;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class App {
    public static void main (String[] args) {
        SwingUtilities.invokeLater (Controller::new);
    }

}
