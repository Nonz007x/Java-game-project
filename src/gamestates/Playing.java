package gamestates;

import Level.LevelManager;
import entities.BossManager;
import entities.Player;
import entities.EnemyManager;
import entities.enemies.GrandPrix;
import main.Game;
import objects.ObjectManager;
import objects.ProjectileManager;
import ui.PauseOverlay;
import utils.Drawable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;


public class Playing extends State implements Statemethods {
    private static Player player;
    private static LevelManager levelManager;
    private static EnemyManager enemyManager;
    private static PauseOverlay pauseOverlay;
    private static ObjectManager objectManager;
    private static BossManager bossManager;
    private static ProjectileManager projectileManager;

    private int xOffset, yOffset;

    private static boolean paused = false;

    public Playing(Game game) {
        super(game);
        initClasses();
        enemyManager.loadEnemies(LevelManager.GetCurrentLevel());
        bossManager.loadBosses(LevelManager.GetCurrentLevel());
    }

    private void initClasses() {
        levelManager = new LevelManager(game);
        enemyManager = new EnemyManager(this);
        bossManager = new BossManager(this);
        projectileManager = new ProjectileManager(this);
        objectManager = new ObjectManager(this);
        pauseOverlay = new PauseOverlay(this);
        player = new Player( this);
        player.loadLvlData(LevelManager.GetCurrentLevel().getCollisionTile());
    }

    @Override
    public void update() {
        if (!paused) {
            player.update();
            calcOffsets();
            enemyManager.update(LevelManager.GetCurrentLevel().getCollisionTile());
            levelManager.update();
            projectileManager.update(LevelManager.GetCurrentLevel().getCollisionTile());
            objectManager.update(LevelManager.GetCurrentLevel().getCollisionTile());
            bossManager.update();
        }
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        levelManager.draw(g2, player.getX(), player.getY(), player.getPlayerScreenPosX(), player.getPlayerScreenPosY());
        // Create a list to store all drawable objects
        List<Drawable> drawableObjects = new ArrayList<>();

        // Add all objects you want to draw to the list
        drawableObjects.add(player);
        drawableObjects.addAll(enemyManager.getDrawables());
        drawableObjects.addAll(bossManager.getDrawables());
        // TODO
//        drawableObjects.addAll(objectManager.getDrawables(xOffset, yOffset));

        // Sort the list based on the Y-coordinate of each object
        drawableObjects.sort(Comparator.comparingDouble(Drawable::getY));

        // Draw the objects in the sorted order
        for (Drawable drawable : drawableObjects) {
            drawable.draw(g2, xOffset, yOffset);
        }
        projectileManager.draw(g2, xOffset, yOffset);
        if (paused) {
            g2.setColor(new Color(0, 0, 0, 150));
            g2.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            pauseOverlay.draw(g);
        }
    }

    private void calcOffsets() {
        xOffset = player.getPlayerScreenPosX() - player.getX();
        yOffset = player.getPlayerScreenPosY() - player.getY();
    }

    public void updateMouse(MouseEvent e) {
        player.updateMousePosition(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!paused) {
            if (e.getButton() == MouseEvent.BUTTON1) {
//                player.teleport();
                player.shoot();
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
            resetAll();
            enemyManager.loadEnemies(LevelManager.GetCurrentLevel());
            bossManager.loadBosses(LevelManager.GetCurrentLevel());
        }

        if (code == KeyEvent.VK_SPACE) {
                player.calculateRad();
                player.dodge();
        }

        if (code == KeyEvent.VK_ESCAPE) {
            paused = !paused;
        }

        if (code == KeyEvent.VK_R) {
            enemyManager.addEnemy(new GrandPrix(10, 20));
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

    private void resetAll() {
        projectileManager.reset();
    }
    public Player getPlayer() {
        return player;
    }

    public void unpauseGame() {
        paused = false;
    }

    public ObjectManager getObjectManager() {
        return objectManager;
    }

}
