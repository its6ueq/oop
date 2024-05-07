package object;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Brick {
    int x;
    int y;
    int height = 32;
    int width = 32;

    Image image = new ImageIcon (Objects.requireNonNull(Explore.class.getResource("/Brick.png"))).getImage();

    public Brick (int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Image getImage(){
        return image;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getHeight(){
        return height;
    }

    public int getWidth(){
        return width;
    }
}
