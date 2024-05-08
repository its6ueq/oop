package object;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static gui.GamePanel.bullets;

public class FirstPlayer extends object.Tank {

    public FirstPlayer (int tankX, int tankY, int heal, int damage, int speed) {
        super (tankX, tankY, heal, damage, speed);
        tankType = 0;
        this.image = tankImages[tankType * 4 + dir];
    }

    @Override
    public void shot() {
        if(this.currMove == 0){
            Bullet bullet = this.addBullet ();
            if(bullet != null)
                bullets.add (bullet);
        }
    }



    //0 : shot, 1: up, 2: left, 3: down, 4: right
    public void changeMove(int key){
        switch (key) {
            case KeyEvent.VK_W:
                this.currMove = 1;
                break;
            case KeyEvent.VK_S:
                this.currMove = 3;
                break;
            case KeyEvent.VK_A:
                this.currMove = 2;
                break;
            case KeyEvent.VK_D:
                this.currMove = 4;
                break;
            case KeyEvent.VK_J:
                bullets.add(this.addBullet ());
            default:
                break;
        }
    }

    public void stopMove(int key){
        if (key == KeyEvent.VK_W && currMove == 1) currMove = 5;
        if (key == KeyEvent.VK_S && currMove == 3) currMove = 5;
        if (key == KeyEvent.VK_A && currMove == 2) currMove = 5;
        if (key == KeyEvent.VK_D && currMove == 4) currMove = 5;
    }
}