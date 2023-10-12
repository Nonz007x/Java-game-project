package objects.projectiles;

import gamestates.Playing;
import main.Game;
import objects.EnemyProjectile;
import utils.LoadSave;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static utils.Constants.Projectile.DEFAULT_TICKS_TO_LIVE;

public class BouncyBullet extends EnemyProjectile {
    private int bounceCount = 0;
    private static BufferedImage[] BOUNCY_BULLET;

    static {
        BOUNCY_BULLET = LoadSave.GetImagesFromSpriteSheet("enemy_bullet.png", 10, 10, 1);
    }

    public BouncyBullet(int x, int y, int speed, double directionX, double directionY, int damage) {
        super(x, y, speed, directionX, directionY, damage, DEFAULT_TICKS_TO_LIVE);
        hitbox = new Rectangle2D.Float(x, y , 16, 16);
        animations = BOUNCY_BULLET;
    }

    public void update(int[][] collisionTile, Playing playing) {
        current_tick++;
        updatePos();
        if (checkPlayerHit(hitbox, playing.getPlayer())) {
            playing.getPlayer().takeDamage(damage);
            setActive(false);
        }

        if (bounceCount >= 2) {
            setActive(false);
        }

        if (hitbox.x <= 0 || hitbox.x >= collisionTile[0].length * Game.TILE_SIZE) {
            bounceCount++;
            directionX = -directionX;
        }

        if (hitbox.y <= 0 || hitbox.y >= collisionTile.length * Game.TILE_SIZE) {
            bounceCount++;
            directionY = -directionY;
        }
    }
}
