package objects.projectiles;

import objects.PlayerProjectile;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static utils.Constants.Projectile.BUCKSHOT_DAMAGE;

public class BuckShot extends PlayerProjectile {
    private static BufferedImage BUCKSHOT_IMAGE;

    public BuckShot(int x, int y, double directionX, double directionY) {
        super(x, y, 10, directionX, directionY, BUCKSHOT_DAMAGE, 30);
        hitbox = new Rectangle2D.Float(x, y, 10, 10);
    }
}
