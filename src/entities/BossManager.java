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
            if (!boss.isActive())
                continue;

            boss.updateAnimationTick();
            boss.update(currentLevel.getCollisionTile(), playing);
            ArrayList<Projectile> playerProjectiles = ProjectileManager.getPlayerProjectiles();
            for (int j = 0; j < playerProjectiles.size(); j++) {
                Projectile projectile = playerProjectiles.get(j);
                if (boss.getHitbox().intersects(projectile.getHitbox())) {
                    boss.takeDamage(20);
                    projectile.setActive(false);
                    playerProjectiles.remove(j);
                    break;
                }
            }

            if (boss.currentHealth <= 0) {
                boss.setActive(false);
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

    public ArrayList<Boss> getDrawables() {
        return bosses;
    }

    @Override
    public int getY() {
        return 0;
    }
}
