package main;

import inputs.MouseEventHandler;
import inputs.KeyHandler;

import javax.swing.*;
import java.awt.*;

import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;

public class GamePanel extends JPanel {
    private static Game game;
    private static MouseEventHandler mouseH;

    public GamePanel(Game game) {
        mouseH = new MouseEventHandler(this);
        GamePanel.game = game;
        setPanelSize();
        setBackground(Color.WHITE);
        addKeyListener(new KeyHandler(this));
        addMouseListener(mouseH);
        addMouseMotionListener(mouseH);
        setDoubleBuffered(true);
        setFocusable(true);
    }

    private void setPanelSize() {
        Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        setPreferredSize(size);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.draw(g);
    }

    public Game getGame() {
        return game;
    }

}
