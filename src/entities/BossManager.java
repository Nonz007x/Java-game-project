package entities;

import Level.Level;
import gamestates.Playing;

import java.awt.*;

public class BossManager {
    private static int bossDefeated;
    private Level currentLevel;
    private Playing playing;

    public BossManager(Playing playing) {
        this.playing = playing;
    }

    public void loadBosses(Level level) {
        this.currentLevel = level;
    }

    public void update() {
        for (Boss boss : currentLevel.getBosses()) {
            boss.update(currentLevel.getCollisionTile(), playing);
        }
    }

    public void draw(Graphics2D g, int xOffset, int yOffset) {
        for (Boss boss : currentLevel.getBosses()) {
//            boss.draw(g, xOffset, yOffset);
            boss.drawHitbox(g, xOffset, yOffset);
        }
    }
}
