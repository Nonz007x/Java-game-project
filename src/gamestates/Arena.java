package gamestates;

import entities.Boss;
import entities.Enemy;
import entities.bosses.Slime;
import level.LevelManager;
import main.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Arena extends Playing {
    private static int time = 1200;
    private static final Timer timer = new Timer(100, e -> {
        time--;
    });
    private boolean pass = true;

    public Arena(Game game) {
        super(game);
    }

    public void initArena() {
        resetAll();
        LevelManager.setLevelIndex(2);
        bossManager.loadBosses(LevelManager.getCurrentLevel());
        timer.start();
    }

    @Override
    public void update() {
        super.update();
        for (Boss boss : bossManager.getTempBosses()) {
            pass = true;
            if (boss.isActive()) {
                pass = false;
                break;
            }
        }

        if (pass) {
            bossManager.addTempBoss(new Slime(1000, 500));
            pass = false;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (timer.isRunning())
                timer.stop();
            else timer.start();
        }
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        g.drawString(String.valueOf(time/10), Game.GAME_WIDTH / 2, 48);
    }

    public static void resetAll() {
        gameOver = false;
        paused = false;
        player.resetPlayer();
        projectileManager.reset();
        enemyManager.getTempEnemies().clear();
        bossManager.getTempBosses().clear();
        for (Enemy enemy : getEnemiesAndBosses()) {
            enemy.resetEnemy();
        }
        timer.restart();
        timer.start();
    }
}
