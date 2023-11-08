package objects;

import level.LevelManager;
import entities.Enemy;
import gamestates.Playing;
import utils.HelpMethods;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public abstract class PlayerProjectile extends Projectile {
    private static final int KNOCKBACK_STRENGTH = 4;
    public PlayerProjectile(int x, int y, int speed, double directionX, double directionY, int damage, int TICK_TO_LIVE) {
        super(x, y, speed, directionX, directionY, damage, TICK_TO_LIVE);
    }

    @Override
    public void update(int[][] collisionTile, Playing playing) {
        super.update(collisionTile, playing);
        ArrayList<Enemy> enemies = playing.getEnemiesAndBosses();
        if (HelpMethods.IsProjectileHittingLevel(this, collisionTile)) {
            setActive(false);
        }
        for (Enemy e : enemies) {
            if (checkEnemyHit(hitBox, e) && e.isActive() && e.isHitBoxEnabled()) {
                e.takeDamage(damage);
                e.knockBack(directionX, directionY, KNOCKBACK_STRENGTH, LevelManager.getCurrentLevel().getCollisionTile());
                setActive(false);
            }
        }
    }

    protected boolean checkEnemyHit(Rectangle2D hitBox, Enemy enemy) {
        return hitBox.intersects(enemy.getHitBox());
    }
}
