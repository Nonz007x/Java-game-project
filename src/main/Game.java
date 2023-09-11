package main;

import gamestates.Gamestate;
import gamestates.Playing;
import gamestates.Menu;

import java.awt.*;

public class Game implements Runnable {

    private static GamePanel gamePanel;
    private Thread gameThread;
    private static Playing playing;
    private static Menu menu;

    public static final int TILES_DEFAULT_SIZE = 16;
    private static final int SCALE = 3;
    public static final int TILE_SIZE = TILES_DEFAULT_SIZE * SCALE; // 48x48
    public static final int TILE_IN_WIDTH = 16;
    public static final int TILE_IN_HEIGHT = 12;
    public static final int GAME_WIDTH = TILE_SIZE * TILE_IN_WIDTH; // 768
    public static final int GAME_HEIGHT = TILE_SIZE * TILE_IN_HEIGHT; // 576

    private static final int FPS_SET = 120;
    private static final int UPS_SET = 60;

    public Game() {
        initClasses();
        gamePanel = new GamePanel(this);
        new GameWindow(gamePanel);
        gamePanel.requestFocusInWindow();
        startGameThread();
    }

    private void initClasses() {
        menu = new Menu(this);
        playing = new Playing(this);
    }

    private void startGameThread() {
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

    public void update() {
        switch (Gamestate.state) {
            case MENU -> menu.update();
            case PLAYING -> playing.update();
//            case OPTIONS -> gameOptions.update();
//            case CREDITS -> credits.update();
            case QUIT -> System.exit(0);
        }
    }

    public void render(Graphics g) {
        switch (Gamestate.state) {
            case MENU -> menu.draw(g);
            case PLAYING -> playing.draw(g);
//            case OPTIONS -> gameOptions.draw(g);
//            case CREDITS -> credits.draw(g);
        }
    }

    public Playing getPlaying() {
        return playing;
    }

    public Menu getMenu() {
        return menu;
    }


}
