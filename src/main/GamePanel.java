package main;

import entity.Player;
import inputs.MouseEventHandler;
import Level.LevelManager;
import inputs.KeyHandler;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    // SCREEN SETTINGS
    private static final int originTileSize = 16;
    private static final int tileScale = 3;
    public static final int tileSize = originTileSize * tileScale; // 48x48
    public static final int maxScreenCol = 16;
    public static final int maxScreenRow = 12;
    public static final int screenWidth = tileSize * maxScreenCol; // 768
    public static final int screenHeight = tileSize * maxScreenRow; // 576

    // WORLD SETTINGS
    public static final int maxWorldCol = 34;
    public static final int maxWorldRow = 50;
    public static final int worldWidth = tileSize * maxWorldCol;
    public static final int worldHeight = tileSize * maxWorldRow;

    Game game;
    MouseEventHandler mouseH;

    public GamePanel(Game game) {
        mouseH = new MouseEventHandler(this);
        this.game = game;
        setPanelSize();
        setBackground(Color.BLACK);
        addKeyListener(new KeyHandler(this));
        addMouseListener(mouseH);
        addMouseMotionListener(mouseH);
        setDoubleBuffered(true);
        setFocusable(true);
    }

    private void setPanelSize() {
        Dimension size = new Dimension(screenWidth,screenHeight);
        setPreferredSize(size);
    }

    @Override
    public void paintComponent(Graphics g){

        super.paintComponent(g);
        game.render(g);
    }

    public Game getGame() {
        return game;
    }

}
