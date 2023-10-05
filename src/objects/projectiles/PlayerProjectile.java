package objects.projectiles;

import gamestates.Playing;
import main.Game;
import objects.Projectile;

import java.awt.geom.Rectangle2D;

import static utils.HelpMethods.IsProjectileHittingLevel;

public class PlayerProjectile extends Projectile {
    public PlayerProjectile(int x, int y, int speed, double directionX, double directionY) {
        super(x, y, speed, directionX, directionY, true, 1200);
        hitbox = new Rectangle2D.Float(x, y, 10, 10);
    }

    public void update(int[][] collisionTile, Playing playing) {
        current_tick++;
        updatePos();
        if (IsProjectileHittingLevel(this, collisionTile)) {
            setActive(false);
        }
    }
}
