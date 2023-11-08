package gamestates;

import level.LevelManager;
import entities.BossManager;
import entities.Enemy;
import entities.Player;
import entities.EnemyManager;
import main.Game;
import objects.ObjectManager;
import objects.ProjectileManager;
import objects.gameobjects.Gate;
import ui.GameOverOverlay;
import ui.Hud;
import ui.PauseOverlay;
import ui.TransitionOverlay;
import utils.Drawable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;


public class Playing extends State implements Statemethods {
    protected static Player player;
    protected static LevelManager levelManager;
    protected static EnemyManager enemyManager;
    protected static PauseOverlay pauseOverlay;
    protected static GameOverOverlay gameOverOverlay;
    protected static ObjectManager objectManager;
    protected static BossManager bossManager;
    protected static ProjectileManager projectileManager;
    protected static Hud hud;

    protected int xOffset, yOffset;

    protected static boolean paused = false;
    protected static boolean gameOver = false;
    protected MouseEvent lastMouseEvent;

    public Playing(Game game) {
        super(game);
        initClasses();
    }

    public void initPlaying() {
        LevelManager.setLevelIndex(0);
        enemyManager.loadEnemies(LevelManager.getCurrentLevel());
        bossManager.loadBosses(LevelManager.getCurrentLevel());
    }

    private void initClasses() {
        levelManager = new LevelManager(this);
        enemyManager = new EnemyManager(this);
        bossManager = new BossManager(this);
        projectileManager = new ProjectileManager(this);
        objectManager = new ObjectManager(this);
        pauseOverlay = new PauseOverlay(this);
        gameOverOverlay = new GameOverOverlay(this);
        player = new Player(this);
        hud = new Hud(this);
        player.loadLvlData(LevelManager.getCurrentLevel().getCollisionTile());
    }

    @Override
    public void update() {
        if (!paused) {
            if (!gameOver)
                player.update();
            calcOffsets();
            enemyManager.update(LevelManager.getCurrentLevel().getCollisionTile());
            levelManager.update();
            projectileManager.update(LevelManager.getCurrentLevel().getCollisionTile());
            objectManager.update();
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
        } else if (gameOver) {
            gameOverOverlay.draw(g);
        }

        if (TransitionOverlay.drawing)
            TransitionOverlay.draw(g);
    }

    private List<Drawable> prepareDrawableObjects() {
        List<Drawable> drawableObjects = new ArrayList<>();
        drawableObjects.add(player);
        drawableObjects.addAll(enemyManager.getEnemies());
        drawableObjects.addAll(enemyManager.getTempEnemies());
        drawableObjects.addAll(bossManager.getTempBosses());
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
        if (e == null) {
            int x = 1;
            int y = 0;
            player.updateMousePosition(x, y);
        } else {
            player.updateMousePosition(e);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!paused || !gameOver) {
            switch (e.getButton()) {
                case MouseEvent.BUTTON1 -> player.setLeftClicked(true);
                case MouseEvent.BUTTON3 -> player.setRightClicked(true);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!paused || !gameOver) {
            switch (e.getButton()) {
                case MouseEvent.BUTTON1 -> player.setLeftClicked(false);
                case MouseEvent.BUTTON3 -> player.setRightClicked(false);
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (!paused && !gameOver) {
            updateMouse(e);
        }
        lastMouseEvent = e;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!paused && !gameOver) {
            updateMouse(e);
        }
        lastMouseEvent = e;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (paused) {
            pauseOverlay.keyPressed(e);
        }

        if (gameOver) {
            gameOverOverlay.keyPressed(e);
        }

        if (code == KeyEvent.VK_ESCAPE)
            togglePause();
        if (!gameOver && !paused)
            switch (code) {
                case KeyEvent.VK_W -> player.setUp(true);
                case KeyEvent.VK_A -> player.setLeft(true);
                case KeyEvent.VK_S -> player.setDown(true);
                case KeyEvent.VK_D -> player.setRight(true);
                case KeyEvent.VK_F -> loadNewLevel();
                case KeyEvent.VK_R -> loadNewLevel(LevelManager.getLevelIndex());
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

    public static void resetAll() {
        gameOver = false;
        paused = false;
        player.resetPlayer();
        projectileManager.reset();
        enemyManager.reset();
        bossManager.reset();
        for (Enemy enemy : getEnemiesAndBosses()) {
            enemy.resetEnemy();
        }

    }

    public static void loadAllEnemies() {
        enemyManager.loadEnemies(LevelManager.getCurrentLevel());
        bossManager.loadBosses(LevelManager.getCurrentLevel());
    }
    public static Player getPlayer() {
        return player;
    }

    public static ArrayList<Enemy> getEnemiesAndBosses() {
        ArrayList<Enemy> enemies = new ArrayList<>();
        enemies.addAll(enemyManager.getTempEnemies());
        enemies.addAll(enemyManager.getEnemies());
        enemies.addAll(bossManager.getBosses());
        enemies.addAll(bossManager.getTempBosses());
        return enemies;
    }

    public static LevelManager getLevelManager() {
        return levelManager;
    }

    public static ObjectManager getObjectManager() {
        return objectManager;
    }

    public static EnemyManager getEnemyManager() {
        return enemyManager;
    }

    public BossManager getBossManager() {
        return bossManager;
    }

    public static void loadNewLevel() {
        resetAll();
        LevelManager.toggleLevel();
        loadAllEnemies();
    }

    public static void loadNewLevel(int levelIndex) {
        resetAll();
        LevelManager.setLevelIndex(levelIndex);
        loadAllEnemies();
    }

    public void unpauseGame() {
        paused = false;
        updateMouse(lastMouseEvent);
    }

    public static void playTransition() {
        TransitionOverlay.drawing = true;
    }

    public void pauseGame() {
        paused = true;
    }

    public void togglePause() {
        if (!gameOver) {
            paused = !paused;
            updateMouse(lastMouseEvent);
        }
    }

    public void endGame() {
        gameOver = true;
    }

    public void continueGame() {
        gameOver = false;
        paused = false;
    }

    public void toMainMenu() {
        resetAll();
        continueGame();
        State.setGamestate(Gamestate.MENU);
    }
}
