package object;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static gui.GamePanel.bullets;

public class SecondPlayer extends object.Tank {

    public SecondPlayer (int tankX, int tankY, int heal, int damage, int speed) {
        super (tankX, tankY, heal, damage, speed);
        tankType = 1;
        this.image = tankImages[tankType * 4 + dir];
    }

    @Override
    public void shot() {
        if(this.currBullet == 0) return;
        this.currBullet--;
        Bullet bullet = this.addBullet ();
        if(bullet != null)
            bullets.add (bullet);

    }

    //1: up, 2: left, 3: down, 4: right
    public void changeMove(int key){
        switch (key) {
            case KeyEvent.VK_UP:
                this.currMove = 1;
                break;
            case KeyEvent.VK_DOWN:
                this.currMove = 3;
                break;
            case KeyEvent.VK_LEFT:
                this.currMove = 2;
                break;
            case KeyEvent.VK_RIGHT:
                this.currMove = 4;
                break;
            case KeyEvent.VK_NUMPAD1:
                this.shot ();
                break;
            default:
                break;
        }
    }

    public void stopMove(int key){
        if (key == KeyEvent.VK_UP && currMove == 1) currMove = 5;
        if (key == KeyEvent.VK_DOWN && currMove == 3) currMove = 5;
        if (key == KeyEvent.VK_LEFT && currMove == 2) currMove = 5;
        if (key == KeyEvent.VK_RIGHT && currMove == 4) currMove = 5;
    }
}
