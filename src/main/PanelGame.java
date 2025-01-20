package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PanelGame extends JPanel implements Runnable, KeyListener {

    Spaceship spaceship;
    Alien[][] aliens;
    Bullet bullet;
    int xAxis;
    int yAxis;
    boolean gameOn;
    boolean gameLost;
    boolean gameWon;
    int score = 0;
    private Thread thread;

    public PanelGame(Dimension dimension) {

        resetGame();

        setSize(dimension);
        setPreferredSize(dimension);
        addKeyListener ( this ) ;
        setFocusable(true);

        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }

        setDoubleBuffered(true);

    }

    public void resetGame(){
        xAxis = 30;
        yAxis = 30;

        aliens = new Alien[3][10];

        gameOn = false;
        gameLost = false;
        gameWon = false;

        //spawns the spaceship and the bullet
        spaceship = new Spaceship(200, 500, 57, 35, 5, "/image/spaceship.png");
        bullet = new Bullet(200, 500, 5, 20, 10, "/image/shot.png");

        int y = 10;
        int x = 10;

        //spawns the aliens
        for(int i = 0; i< aliens.length; i++) {
            for (int j = 0; j < aliens[0].length; j++) {
                aliens[i][j] = new Alien(x, y, 30, 20, 3, "/image/alien.png");
                x += 35;
            }
            x = 10;
            y += 25;
        }
    }

    @Override
    public void paint(Graphics g) {

        //g2 is the graphics object needed to be used
        Graphics2D g2 = (Graphics2D) g;

        // to draw things to the screen
        Dimension d = getSize();

        // create a background
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(0, 0, d.width, d.height);

        // Executed if user Won the game
        if (gameWon){
            g2.setColor(Color.BLACK);
            g.drawString("Press Q to start", 10, 200);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
            g.drawString("You Won!", 190, d.height / 2);
            score = 0;
        }

        else if (gameOn){
            moveAlien();
            spaceship.move(0);
            bullet.move(0);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
            g2.setColor(Color.BLACK);
            g.drawString("Score: " + score, 10, 25);
        }

        else if (gameLost){
            g2.setColor(Color.BLACK);
            g.drawString("Press Q to start", 10, 200);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
            g.drawString("You Lost!", 190, d.height / 2);
            score = 0;
        }

        else{
            g2.setColor(Color.BLACK);
            g.drawString("Press Q to start", 10, 200);

        }


        bullet.draw(g2);
        spaceship.draw(g2);
        detectHit();

        for (int i = 0; i< aliens.length; i++){
            for (int j = 0; j< aliens[0].length; j++){
                if(aliens[i][j].isVisible)
                    aliens[i][j].draw(g2);
            }
        }

    }

    // check if bullet hit an alien
    public void detectHit(){

        for(int i = 0; i< aliens.length; i++){
            for (int j = 0; j< aliens[0].length; j++){

                if (aliens[i][j].isVisible && bullet.getX() + bullet.getWidth() >= aliens[i][j].getX() &&
                        bullet.getX() <= aliens[i][j].getX() + aliens[i][j].getWidth() &&
                        bullet.getY() + bullet.getHeight() >= (aliens[i][j].getY()) &&
                        bullet.getY() <= aliens[i][j].getY() + aliens[i][j].getHeight()) {

                    aliens[i][j].isVisible=false;
                    score += 100;
                    if (score == 3000){
                        gameWon = true;
                    }
                    bullet.x = -30;
                }

            }
        }

    }

    public void moveAlien(){
        for(int i = 0; i< aliens.length; i++){
            for (int j = 0; j< aliens[0].length; j++){
                if(aliens[i][j].moveLeft)
                    aliens[i][j].setX(aliens[i][j].getX()- aliens[i][j].getSpeed());

                if(aliens[i][j].moveRight){
                    aliens[i][j].setX(aliens[i][j].getX()+ aliens[i][j].getSpeed());
                }
            }
        }

        //check if we need to switch directions
        for(int i = 0; i< aliens.length; i++){
            for (int j = 0; j< aliens[0].length; j++){

                if(aliens[i][j].getX()>600){
                    aliensMoveLeftRight(1);
                    break;
                }

                if(aliens[i][j].getX()<0){
                    aliensMoveLeftRight(2);
                    break;
                }
            }
        }
    }

    public void aliensMoveLeftRight(int d){
        for(int i = 0; i< aliens.length; i++){
            for (int j = 0; j< aliens[0].length; j++){
                if(d==1){
                    aliens[i][j].moveLeft=true;
                    aliens[i][j].moveRight=false;
                }else{
                    aliens[i][j].moveLeft=false;
                    aliens[i][j].moveRight=true;
                }

                aliens[i][j].setY(aliens[i][j].getY()+10);

                if (aliens[i][j].getY()>500){
                    gameOn = false;
                    gameLost = true;
                }

            }
        }
    }

    @Override
    public void keyTyped (KeyEvent e ){

    }

    @Override
    public void keyPressed (KeyEvent e){
        int k = e.getKeyCode();

        // Q key
        if (k==81){
            resetGame();
            gameOn = true;
        }

        spaceship.setLeftRight(k);
        // space key
        if(k==32)  {
            bullet.fired =true;
            bullet.setX(spaceship.getX() + (spaceship.getWidth()/2));
            bullet.setY(spaceship.getY() );
        }
    }

    @Override
    public void keyReleased (KeyEvent e ){
        //left arrow
        if (e.getKeyCode() == 37){
            spaceship.stopLeft();
        }

        //right arrow
        if (e.getKeyCode() == 39){
            spaceship.stopRight();
        }

    }
    @Override
    public void run() {
        //For game FPS
        final int FPS = 60;
        final int TARGET_TIME = 1000000000 / FPS;

        while (true) {// infinite loop
            long startTime = System.nanoTime();
            repaint();
            long loopTime = System.nanoTime() - startTime;
            if(loopTime < TARGET_TIME){
                long sleep = (TARGET_TIME - loopTime) / 1000000; // We have to divide by million as delayLoop method accepts time in milliseconds (Convert nano to milli)
                delayLoop(sleep);
            }
        }
    }

    private void delayLoop(long speed){
        try {
            Thread.sleep(speed);
        } catch (InterruptedException ex){
            System.err.println(ex);
        }
    }

}