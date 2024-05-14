package object;

import gui.GamePanel;

import java.awt.*;
import java.util.Random;

import static gui.GamePanel.*;

public class BotTank extends object.Tank {

    public BotTank (int tankX, int tankY, int heal, int damage, int speed) {
        super (tankX, tankY, heal, damage, speed);
        tankType = 2;
        this.image = tankImages[tankType * 4 + dir];
    }

    @Override
    public void shot() {
        if(this.currBullet == 0) return;
        this.currBullet--;
        Bullet bullet = this.addBullet ();
        if(bullet != null)
            enermyBullets.add (bullet);
    }

    //0 : shot, 1: up, 2: left, 3: down, 4: right

    public void changeMove(){
        Random rand = new Random();
        if(rand.nextDouble () < 0.5)
            this.shot ();
        else this.currMove = rand.nextInt(5);
    }
}

