package gamestates;

import Level.LevelManager;
import entity.Player;
import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;


public class Playing extends State implements Statemethods {
    private Player player;
    private LevelManager levelManager;

    public Playing(Game game) {
        super(game);
        initClasses();
    }

    private void initClasses() {
        player = new Player(48,48,this);
        levelManager = new LevelManager(game);
        player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
    }

    @Override
    public void update() {
        player.update();
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        levelManager.render(g2);
        player.render(g2);
        g2.dispose();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            player.teleport();
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            player.calculateRad();
            player.dodge();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Not implemented, but can be added if needed
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Not implemented, but can be added if needed
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        player.updateMousePosition(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        player.updateMousePosition(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            player.setUp(true);
        }
        if (code == KeyEvent.VK_A) {
            player.setLeft(true);
        }
        if (code == KeyEvent.VK_S) {
            player.setDown(true);
        }
        if (code == KeyEvent.VK_D) {
            player.setRight(true);
        }
        if (code == KeyEvent.VK_F) {
            levelManager.toggleLevel();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            player.setUp(false);
        }
        if (code == KeyEvent.VK_A) {
            player.setLeft(false);
        }
        if (code == KeyEvent.VK_S) {
            player.setDown(false);
        }
        if (code == KeyEvent.VK_D) {
            player.setRight(false);
        }
    }

    public Player getPlayer() {
        return player;
    }
}
