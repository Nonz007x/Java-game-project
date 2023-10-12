package objects.projectiles;

import objects.PlayerProjectile;
import utils.LoadSave;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static utils.Constants.Projectile.BUCKSHOT_DAMAGE;

public class BuckShot extends PlayerProjectile {
    private static final BufferedImage[] BUCKSHOT_IMAGE;

    static {
        BUCKSHOT_IMAGE = LoadSave.GetImagesFromSpriteSheet("player_bullet.png", 10, 10, 1);
    }

    public BuckShot(int x, int y, double directionX, double directionY) {
        super(x, y, 10, directionX, directionY, BUCKSHOT_DAMAGE, 150);
        hitbox = new Rectangle2D.Float(x, y, 16, 16);
        animations = BUCKSHOT_IMAGE;
    }
}
