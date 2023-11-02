package gamestates;

import level.LevelManager;
import entities.BossManager;
import entities.Enemy;
import entities.Player;
import entities.EnemyManager;
import main.Game;
import objects.ObjectManager;
import objects.ProjectileManager;
import ui.Hud;
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
    private static Hud hud;

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
        player = new Player(this);
        hud = new Hud(this);
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
        Graphics2D graphics2D = (Graphics2D) g;

        // Draw the game level
        levelManager.draw(graphics2D, player.getX(), player.getY(), player.getPlayerScreenPosX(), player.getPlayerScreenPosY());

        // Prepare drawable objects for rendering
        List<Drawable> drawableObjects = prepareDrawableObjects();

        // Sort and draw the drawable objects
        drawSortedDrawableObjects(graphics2D, drawableObjects);

        // Draw projectiles and HUD
        projectileManager.draw(graphics2D, xOffset, yOffset);
        hud.draw(graphics2D);

        // Draw pause overlay if the game is paused
        if (paused) {
            pauseOverlay.draw(g);
        }
    }

    private List<Drawable> prepareDrawableObjects() {
        List<Drawable> drawableObjects = new ArrayList<>();
        drawableObjects.add(player);
        drawableObjects.addAll(enemyManager.getEnemies());
        drawableObjects.addAll(bossManager.getBosses());
        // drawableObjects.addAll(objectManager.getDrawables(xOffset, yOffset));
        return drawableObjects;
    }

    private void drawSortedDrawableObjects(Graphics2D graphics2D, List<Drawable> drawableObjects) {
        drawableObjects.sort(Comparator.comparingDouble(Drawable::getY));
        for (Drawable drawable : drawableObjects) {
            drawable.draw(graphics2D, xOffset, yOffset);
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

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!paused) {
            switch (e.getButton()) {
                case MouseEvent.BUTTON1 -> player.setLeftClicked(true);
                case MouseEvent.BUTTON3 -> player.setRightClicked(true);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!paused) {
            switch (e.getButton()) {
                case MouseEvent.BUTTON1 -> player.setLeftClicked(false);
                case MouseEvent.BUTTON3 -> player.setRightClicked(false);
            }
        }
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

        switch (code) {
            case KeyEvent.VK_W -> player.setUp(true);
            case KeyEvent.VK_A -> player.setLeft(true);
            case KeyEvent.VK_S -> player.setDown(true);
            case KeyEvent.VK_D -> player.setRight(true);
            case KeyEvent.VK_F -> {
                levelManager.toggleLevel();
                resetAll();
                enemyManager.loadEnemies(LevelManager.GetCurrentLevel());
                bossManager.loadBosses(LevelManager.GetCurrentLevel());
            }
            case KeyEvent.VK_ESCAPE -> paused = !paused;
            case KeyEvent.VK_R -> resetAll();
            case KeyEvent.VK_Q -> player.drinkPotion();
            case KeyEvent.VK_F1 -> Hud.toggleHud();
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

    public void resetAll() {
        paused = false;
        player.resetPlayer();
        projectileManager.reset();
        for (Enemy enemy : getEnemiesAndBosses()) {
            enemy.resetEnemy();
        }
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Enemy> getEnemiesAndBosses() {
        ArrayList<Enemy> enemies = new ArrayList<>();
        enemies.addAll(enemyManager.getEnemies());
        enemies.addAll(bossManager.getBosses());
        return enemies;
    }

    public BossManager getBossManager() {
        return bossManager;
    }

    public void unpauseGame() {
        paused = false;
    }

    public void pauseGame() {
        paused = true;
    }

    public ObjectManager getObjectManager() {
        return objectManager;
    }

}
