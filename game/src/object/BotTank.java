package object;

import java.util.Random;



import static gui.GamePanel.bestPath;
import static gui.GamePanel.cx;
import static gui.GamePanel.cy;
import static gui.GamePanel.enermyBullets;

import static gui.GamePanel.p1;
import static gui.GamePanel.p2;


public class BotTank extends object.Tank {


    int onGoingX, onGoingY;

    public BotTank (int tankX, int tankY, int heal, int damage, int speed) {
        super (tankX, tankY, heal, damage, speed);
        tankType = 2;
        this.onGoingX = tankX;
        this.onGoingY = tankY;
        this.image = tankImages[tankType * 4 + dir];
    }

    @Override
    public void shot () {
        if (this.currBullet == 0)
            return;
        this.currBullet--;
        Bullet bullet = this.addBullet ();
        if (bullet != null)
            enermyBullets.add (bullet);
    }



    public void botMove(){
        if(this.onGoingX == x && this.onGoingY == y){
            this.changeMove ();
        } else {
            this.currMove = this.dir + 1;
            this.move();
        }
    }

    @Override
    public void move () {
        if(checkCanHitPlayer()){
            this.shot ();
        } else {
            switch (currMove) {
                case 1:
                    if(this.canGoUp ()) this.moveUp ();
                    else this.shot ();
                    break;
                case 2:
                    if(this.canGoRight ()) this.moveRight ();
                    else this.shot ();
                    break;
                case 3:
                    if(this.canGoDown ()) this.moveDown ();
                    else this.shot ();
                    break;
                case 4:
                    if(this.canGoLeft ()) this.moveLeft ();
                    else this.shot ();
                    break;
                default:
                    break;
            }
        }
    }

    //o1x < o2x && o1x + o1w > o2x && o1y < o2y && o1y + o1h > o2y

    boolean checkCanHitPlayer(){
        if(p1 != null){
            if(p1.getX() + p1.getWidth () / 2 > this.x && p1.getX() < this.x + width / 2 ){
                if(p1.getY () > this.y && dir == DOWN){
                    return true;
                }
                if(p1.getY () < this.y && dir == UP){
                    return true;
                }
            }
            if(p1.getY () + p1.getHeight () / 2 > this.y && p1.getY () < this.y + height / 2){
                if(p1.getX() > this.x && dir == RIGHT){
                    return true;
                }
                if(p1.getX() < this.x && dir == LEFT){
                    return true;
                }
            }
        }
        if(p2 != null){
            if(p2.getX() + p2.getWidth () / 2 > this.x && p2.getX() < this.x + width / 2 ){
                if(p2.getY () < this.y && dir == DOWN){
                    return true;
                }
                if(p2.getY () > this.y && dir == UP){
                    return true;
                }
            }
            if(p2.getY () + p2.getHeight () / 2 > this.y && p2.getY () < this.y + height / 2){
                if(p2.getX() > this.x && dir == RIGHT){
                    return true;
                }
                if(p2.getX() < this.x && dir == LEFT){
                    return true;
                }
            }
        }
        return false;
    }

//    public static int RIGHT = 1;
//    public static int LEFT = 3;
//    public static int DOWN = 2;
//    public static int UP = 0;

    //0 : shot, 1: up, 2: right, 3: down, 4: left

//    public static int[] cx = {0, 1, 0, -1};
//    public static int[] cy = {1, 0, -1, 0};

    public void changeMove () {
        int x32 = (y - 8) / 32;
        int y32 = (x - 8) / 32;
        int maxNextMove = 999999;
//        System.out.println ("Current Move: " + bestPath[x32][y32] + " " + x32 + " " + y32);
        for(int i = 0; i < 4; i++){
            if(x32 + cx[i] < 26 && x32 + cx[i] >= 0 && y32 + cy[i] < 26 && y32 + cy[i] >= 0){
                if(bestPath[x32 + cx[i]][y32 + cy[i]] < maxNextMove){
//                    System.out.println ("Next Move: " + bestPath[x32 + cx[i]][y32 + cy[i]] + " " + (x32 + cx[i]) + " " + (y32 + cy[i]));
                    maxNextMove = bestPath[x32 + cx[i]][y32 + cy[i]];
                    this.onGoingX = (y32 + cy[i]) * 32 + 8;
                    this.onGoingY = (x32 + cx[i]) * 32 + 8;
                    this.dir = (i + 1) % 4;
                    this.setImage ();
                }
            }
        }
    }

    private static int findShortestPath (int x, int y) {
        int x32 = x / 32;
        int y32 = y / 32;

        return 0;
    }




}

