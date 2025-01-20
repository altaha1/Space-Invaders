package main;

import java.awt.Graphics;

public class Bullet extends SpaceObj
{

    boolean fired;

    public Bullet(int x, int y, int width, int height, int speed, String u)
    {
        super(x, y, width, height, speed, "/image/shot.png");
        setY(600);
        fired = false;
    }

    public  void move(int d){

        if(getY()<0){
            fired =false;
            setY(600);
        }

        if(fired)
            setY(getY()-getSpeed());
    }

    public void draw(Graphics window){
        window.drawImage(getImage(),getX(),getY(),getWidth(),getHeight(),null);
    }


}

