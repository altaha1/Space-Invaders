package main;

import java.awt.Graphics;

public class Spaceship extends SpaceObj
{

    public Spaceship(int x, int y, int width, int height, int speed, String u)
    {
        super(x, y, width, height, speed, "/image/spaceship.png");
    }

    public void draw(Graphics window){
        window.drawImage(getImage(),getX(),getY(),getWidth(),getHeight(),null);
    }

    public  void move(int d){
        if(moveLeft)
            setX(getX()-getSpeed());

        if(moveRight){
            setX(getX()+getSpeed());
        }

    }

    public void setLeftRight(int d){
        //left arrow
        if(d==37){
            moveLeft = true;
        }

        //right arrow
        if(d==39){
            moveRight = true;
        }

    }

    public void stopLeft(){
        moveLeft = false;
    }

    public void stopRight(){
        moveRight = false;
    }

}

