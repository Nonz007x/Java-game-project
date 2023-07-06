package main;

import entity.Player;
import Tile.TileManager;
import entity.enemies.Enemy;
import entity.enemies.Slime;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    private final int originTileSize = 16;
    private final int tileScale = 3;
    public final int tileSize = originTileSize * tileScale; // 48x48
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768
    public final int screenHeight = tileSize * maxScreenRow; // 576

    // WORLD SETTINGS
    public final int maxWorldCol = 34;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    // TICK
    private final int ticksPerSecond = 60 ;
    int FPS = 60;

    TileManager tileM = new TileManager(this);
    public CollisionDetector collisionDetector = new CollisionDetector(this);
    KeyHandler keyH = new KeyHandler();
    MouseHandler mouseH = new MouseHandler(this);
    private Thread gameThread;
    public Player player = new Player(this, keyH, mouseH);

    // DEBUG
    boolean showFps = true;

    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        this.addMouseMotionListener(mouseH);
        this.addMouseListener(mouseH);
    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run(){

        double delta = 0;
        double elapsedTime;
        long lastTime = System.currentTimeMillis();
        long currentTime;

        mouseH.startTracking();
        while(gameThread != null) {
            currentTime = System.currentTimeMillis();
            elapsedTime = currentTime - lastTime;
            lastTime = currentTime;

            delta += elapsedTime;

            double tickDuration = 1000.0 / ticksPerSecond;
            if (delta >= tickDuration) {
                update();
                repaint();
                delta -= tickDuration;
            }
        }
    }

    private void printFps(int frameCount) {
        System.out.println("FPS: " + frameCount);
    }

    public void update(){
        player.update();
    }
    @Override
    public void paintComponent(Graphics g){

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

//        long drawStart = System.nanoTime();

        tileM.render(g2);
        player.render(g2);

/*
        long drawEnd = System.nanoTime();
        long drawTime = drawEnd - drawStart;
        System.out.println(drawTime);
*/

        g2.dispose();
    }
}
