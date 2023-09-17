package gamestates;

import Level.LevelManager;
import entity.Player;
import entity.enemies.EnemyManager;
import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;


public class Playing extends State implements Statemethods {
    private static Player player;
    private static LevelManager levelManager;
    private static EnemyManager enemyManager;

    public Playing(Game game) {
        super(game);
        initClasses();
        enemyManager.loadEnemies(LevelManager.GetCurrentLevel());
    }

    private void initClasses() {
        player = new Player(48, 48, this);
        levelManager = new LevelManager(game);
        enemyManager = new EnemyManager(this);
        player.loadLvlData(LevelManager.GetCurrentLevel().getCollisionTile());
    }

    @Override
    public void update() {
        player.update();
        levelManager.update();
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        levelManager.render(g2, player.getX(), player.getY(), player.getPlayerScreenPosX(), player.getPlayerScreenPosY());
        enemyManager.draw(g2, player.getX(), player.getY(), player.getPlayerScreenPosX(), player.getPlayerScreenPosY());
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
            enemyManager.loadEnemies(LevelManager.GetCurrentLevel());
        }
        if (code == KeyEvent.VK_ESCAPE) {
            setGamestate(Gamestate.MENU);
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
