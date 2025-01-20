package main;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main extends JFrame{

    int frameWidth = 600;
    int frameHeight = 600;

    public Main(){
        init();
    }

    // The method to set up
    private void init() {

        setTitle("Space Invaders");
        setSize(frameWidth, frameHeight);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // the event that triggers the end of the program
        setPreferredSize(getSize());
        setLayout(new BorderLayout());
        PanelGame panelGame = new PanelGame(getSize());
        add(panelGame);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                panelGame.resetGame();
            }
        });
    }

    // The main method
    public static void main(String... argv) {
        Main main = new Main();
        main.setVisible(true);

    }
}