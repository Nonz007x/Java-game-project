package objects;

import gamestates.Playing;
import main.Game;
import objects.Projectile;

import java.awt.geom.Rectangle2D;

import static utils.HelpMethods.IsProjectileHittingLevel;

public abstract class PlayerProjectile extends Projectile {
    public PlayerProjectile(int x, int y, int speed, double directionX, double directionY, int damage, int TICK_TO_LIVE) {
        super(x, y, speed, directionX, directionY, damage, TICK_TO_LIVE);
    }
}
