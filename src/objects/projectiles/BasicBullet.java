package objects.projectiles;

import gamestates.Playing;
import objects.EnemyProjectile;
import utils.UtilMethods;
import utils.LoadSave;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static utils.Constants.Projectile.DEFAULT_TICKS_TO_LIVE;

public class BasicBullet extends EnemyProjectile {
    private static final BufferedImage[] BASIC_BULLET;

    static {
        BASIC_BULLET = LoadSave.getImagesFromSpriteSheet("enemy_bullet.png", 10, 10, 1, false);
    }

    public BasicBullet(int x, int y, int speed, double directionX, double directionY, int damage) {
        super(x, y, speed, directionX, directionY, damage, DEFAULT_TICKS_TO_LIVE);
        hitBox = new Rectangle2D.Float(x, y , 16, 16);
        animations = BASIC_BULLET;
    }

    public void update(int[][] collisionTile, Playing playing) {
        current_tick++;
        updatePos();
        if (UtilMethods.IsProjectileHittingLevel(this, collisionTile)) {
            setActive(false);
        }
        if (checkPlayerHit(hitBox, Playing.getPlayer())) {
            Playing.getPlayer().takeDamage(damage);
            setActive(false);
        }
    }
}
