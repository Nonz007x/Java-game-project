package Main;

import Entity.Player;
import Tile.TileManager;

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
    public final int worldWHeight = tileSize * maxWorldRow;
    // Frames per second
    int FPS = 60;

    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    MouseHandler mouseH = new MouseHandler(this);
    Thread gameThread;
    public Player player = new Player(this, keyH, mouseH);
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

        double delay = 1000.0 /FPS;
        double delta = 0;
        long lastTime = System.currentTimeMillis();
        long currentTime;
        long timer = 0;
        int frameCount = 0;

        mouseH.startTracking();
        while(gameThread != null) {
            currentTime = System.currentTimeMillis();
            delta += (currentTime - lastTime) / delay;
            timer += currentTime - lastTime;
            lastTime = currentTime;

            if(delta >= 1) {
                update();
                repaint();
                delta--;
                frameCount++;
                if (timer >= 1000) {
                    System.out.println("FPS: " + frameCount);
                    frameCount = 0;
                    timer = 0;
                }
            }

        }
    }

    public void update(){
        player.update();

    }
    @Override
    public void paintComponent(Graphics g){

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        tileM.render(g2);
        player.render(g2);

        if (mouseH.isClicked) {
            g2.setColor(Color.RED);
            g2.fillRect(player.screenX,player.screenY,tileSize,tileSize);
        }
        g2.dispose();
    }
}
