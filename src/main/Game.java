package main;

import gamestates.Gamestate;
import gamestates.Playing;

import java.awt.*;

public class Game implements Runnable {

    private GamePanel gamePanel;
    private Thread gameThread;
    private Playing playing;

    private static final int originTileSize = 16;
    private static final int tileScale = 3;
    public static final int tileSize = originTileSize * tileScale; // 48x48
    public static final int maxScreenCol = 16;
    public static final int maxScreenRow = 12;
    public static final int GAME_WIDTH = tileSize * maxScreenCol; // 768
    public static final int GAME_HEIGHT = tileSize * maxScreenRow; // 576

    private static final int FPS_SET = 120;
    private static final int UPS_SET = 60;

    public Game() {
        System.out.println("size: " + GAME_WIDTH + " : " + GAME_HEIGHT);
        initClasses();
        gamePanel = new GamePanel(this);
        new GameWindow(gamePanel);
        gamePanel.requestFocusInWindow();
        startGameThread();
    }

    private void initClasses() {
        playing = new Playing(this);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;

        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;

        while (true) {

            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {

                update();
                updates++;
                deltaU--;

            }

            if (deltaF >= 1) {

                gamePanel.repaint();
                frames++;
                deltaF--;

            }
        }
    }

    private void update() {
        playing.update();
    }

    public void render(Graphics g) {
        playing.draw(g);
//        switch (Gamestate.state) {
//            case MENU -> menu.draw(g);
//            case PLAYING -> playing.draw(g);
//            case OPTIONS -> gameOptions.draw(g);
//            case CREDITS -> credits.draw(g);
//        }
    }

    public Playing getPlaying() {
        return playing;
    }


}
