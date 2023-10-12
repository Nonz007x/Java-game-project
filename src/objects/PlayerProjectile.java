package objects;

import Level.LevelManager;
import entities.Enemy;
import gamestates.Playing;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public abstract class PlayerProjectile extends Projectile {
    public PlayerProjectile(int x, int y, int speed, double directionX, double directionY, int damage, int TICK_TO_LIVE) {
        super(x, y, speed, directionX, directionY, damage, TICK_TO_LIVE);
    }

    @Override
    public void update(int[][] collisionTile, Playing playing) {
        super.update(collisionTile, playing);
        ArrayList<Enemy> enemies = playing.getEnemiesAndBosses();
        for (Enemy e : enemies) {
            if (checkEnemyHit(hitbox, e) && e.isActive()) {
                e.takeDamage(damage);
                e.knockback(directionX, directionY, 40, LevelManager.GetCurrentLevel().getCollisionTile());
                setActive(false);
            }
        }

    }

    protected boolean checkEnemyHit(Rectangle2D hitbox, Enemy enemy) {
        return hitbox.intersects(enemy.getHitbox());
    }
}
