package entities;

import Level.Level;
import gamestates.Playing;
import objects.Projectile;
import objects.ProjectileManager;
import utils.Drawable;

import java.awt.*;
import java.util.ArrayList;

public class BossManager implements Drawable {
    private Level currentLevel;
    private static ArrayList<Boss> bosses = new ArrayList<>();
    private Playing playing;

    public BossManager(Playing playing) {
        this.playing = playing;
    }

    public void loadBosses(Level level) {
        this.currentLevel = level;
        bosses = currentLevel.getBosses();
    }

    public void update() {
        for (Boss boss : bosses) {
            if (boss.isActive()) {
                boss.updateAnimationTick();
                boss.update(currentLevel.getCollisionTile(), playing);
            }
        }
    }

    @Override
    public void draw(Graphics2D g, int xOffset, int yOffset) {
        for (Boss boss : bosses) {
            if (boss != null) {
                boss.draw(g, xOffset, yOffset);
                boss.drawHitbox(g, xOffset, yOffset);
            }
        }
    }

    public ArrayList<Boss> getBosses() {
        return bosses;
    }

    @Override
    public int getY() {
        return 0;
    }
}
