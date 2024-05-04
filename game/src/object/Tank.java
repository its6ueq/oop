package game.object;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Tank {
    private static Tank instance;

    int x;
    int y;
    int width;
    int height;
    int heal;
    int currDir;
    Image image;

    Tank (int tankX, int tankY, int tankWidth, int tankHeight, int currHeal, Image image) {
        this.image = image;
        this.x = tankX;
        this.y = tankY;
        this.width = tankWidth;
        this.height = tankHeight;
        this.heal = currHeal;
        this.currDir = 1;
    }

    public static Tank getInstance (int tankX, int tankY, int tankWidth, int tankHeight, int currHeal, Image image) {
        if (instance == null) {
            instance = new Tank (tankX, tankY, tankWidth, tankHeight, currHeal, image);
        }
        return instance;
    }

    private void move () {

    }
}


