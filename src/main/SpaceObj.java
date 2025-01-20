package main;

import java.net.URL;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;

abstract class SpaceObj
{
    int x;
    int y;
    int w;
    int h;
    private final int speed;
    private Image image;
    boolean moveLeft = false;
    boolean moveRight = false;

    public SpaceObj(int x, int y, int w, int h, int s, String u)
    {
        // initialise instance variables
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        speed=s;
        try
        {
            URL url = getClass().getResource(u);
            image = ImageIO.read(url);
        }
        catch(Exception e)
        {
            System.out.println("Image not found");
        }
    }

    public abstract void move(int direction);

    public abstract void draw(Graphics window);

    public String toString()
    {
        return getX() + " " + getY() + " " + getWidth() + " " + getHeight();
    }


    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }


    public int getWidth()
    {
        return w;
    }

    public int getHeight()
    {
        return h;
    }

    public int getSpeed(){
        return speed;
    }

    public Image getImage(){
        return image;
    }


}
