package main;

import gamestates.Gamestate;
import gamestates.Playing;
import gamestates.Menu;

import java.awt.*;

public class Game implements Runnable {

    private static final boolean SHOW_FPS_UPS = true;
    private static GamePanel gamePanel;
    private Thread gameThread, renderThread;
    private static Playing playing;
    private static Menu menu;

    public static final int TILES_DEFAULT_SIZE = 16;
    public static final int SCALE = 3;
    public static final int TILE_SIZE = TILES_DEFAULT_SIZE * SCALE; // 48x48
    public static final int COLUMN = 32;
    public static final int ROW = 18;
    public static final int GAME_WIDTH = TILE_SIZE * COLUMN; // 768
    public static final int GAME_HEIGHT = TILE_SIZE * ROW; // 576

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
        renderThread = new Thread(this::renderLoop);
        gameThread.start();
        renderThread.start();
    }

    @Override
    public void run() {
        double timePerUpdate = 1000000000.0 / UPS_SET;
        long previousTime = System.nanoTime();
        double deltaU = 0;

        while (true) {
            long currentTime = System.nanoTime();
            deltaU += (currentTime - previousTime) / timePerUpdate;
            previousTime = currentTime;

            if (deltaU >= 1) {
                update();
                deltaU--;
            }
        }
    }

    private void renderLoop() {
        double timePerFrame = 1000000000.0 / FPS_SET;
        long previousTime = System.nanoTime();
        double deltaF = 0;

        int frames = 0;
        long lastCheck = System.currentTimeMillis();

        while (true) {
            long currentTime = System.nanoTime();
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaF >= 1) {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            if (SHOW_FPS_UPS)
                if (System.currentTimeMillis() - lastCheck >= 1000) {

                    lastCheck = System.currentTimeMillis();
                    System.out.println("FPS: " + frames);
                    frames = 0;
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

    public void draw(Graphics g) {
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
