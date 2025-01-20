package main;

import java.awt.Graphics;

public class Alien extends SpaceObj {
    boolean isVisible;

    public Alien(int x, int y, int w, int h, int s, String u)
    {
        super(x, y, w, h, s, u);
        isVisible =true;
        moveRight=true;
    }

    public  void move(int direction){
        if(moveLeft)
            setX(getX()-getSpeed());

        if(moveRight)
            setX(getX()+getSpeed());

    }
    public void draw(Graphics window){
        window.drawImage(getImage(),getX(),getY(),getWidth(),getHeight(),null);
    }

}

