package objects.projectiles;

import gamestates.Playing;
import main.Game;
import objects.EnemyProjectile;
import utils.HelpMethods;
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
        if (HelpMethods.IsProjectileHittingLevel(this, collisionTile)) {
            setActive(false);
        }
        if (checkPlayerHit(hitBox, playing.getPlayer())) {
            playing.getPlayer().takeDamage(damage);
            setActive(false);
        }
    }
}
