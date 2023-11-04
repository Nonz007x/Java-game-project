package objects.projectiles;

import objects.PlayerProjectile;
import utils.LoadSave;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static utils.Constants.Projectile.BUCKSHOT_DAMAGE;

public class BuckShot extends PlayerProjectile {
    private static final BufferedImage[] BUCKSHOT_IMAGE;

    static {
        BUCKSHOT_IMAGE = LoadSave.GetImagesFromSpriteSheet("player_bullet.png", 10, 10, 1, false);
    }

    public BuckShot(int x, int y, double directionX, double directionY, int ttl, int damage) {
        super(x, y, ttl <= 15 ? 10 : 20, directionX, directionY, damage, ttl);
        hitBox = new Rectangle2D.Float(x, y, 16, 16);
        animations = BUCKSHOT_IMAGE;
    }

    public BuckShot(int x, int y, double directionX, double directionY, int ttl) {
        super(x, y, ttl <= 15 ? 10 : 20, directionX, directionY, BUCKSHOT_DAMAGE, ttl);
        hitBox = new Rectangle2D.Float(x, y, 16, 16);
        animations = BUCKSHOT_IMAGE;
    }

    public BuckShot(int x, int y, double directionX, double directionY) {
        this(x, y, directionX, directionY, 15); // Default TTL value
    }
}
