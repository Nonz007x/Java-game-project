package gamestates;

import Level.LevelManager;
import entity.Player;
import entity.enemies.Enemy;
import entity.enemies.EnemyManager;
import main.Game;
import ui.PauseOverlay;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;


public class Playing extends State implements Statemethods {
    private static Player player;
    private static LevelManager levelManager;
    private static EnemyManager enemyManager;
    private static PauseOverlay pauseOverlay;

    private static boolean paused = false;

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
        pauseOverlay = new PauseOverlay(this);
    }

    @Override
    public void update() {
        if (!paused) {
            player.update();
            enemyManager.update(LevelManager.GetCurrentLevel().getCollisionTile(), this);
            levelManager.update();
        }
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        levelManager.render(g2, player.getX(), player.getY(), player.getPlayerScreenPosX(), player.getPlayerScreenPosY());
        enemyManager.draw(g2, player.getX(), player.getY(), player.getPlayerScreenPosX(), player.getPlayerScreenPosY());
        player.render(g2);
        if (paused) {
            g2.setColor(new Color(0, 0, 0, 150));
            g2.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            pauseOverlay.draw(g);
        }
    }

    public void updateMouse(MouseEvent e) {
        player.updateMousePosition(e);
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if (!paused) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                player.teleport();
            } else if (e.getButton() == MouseEvent.BUTTON3) {
                player.calculateRad();
                player.dodge();
            }
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
//        if (!paused)
            updateMouse(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
//        if (!paused)
            updateMouse(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (paused) {
            pauseOverlay.keyPressed(e);
        }

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
            paused = !paused;
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

    public void unpauseGame() {
        paused = false;
    }
}
