package objects.projectiles;

import gamestates.Playing;
import main.Game;
import objects.Projectile;
import utils.LoadSave;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static utils.HelpMethods.IsProjectileHittingLevel;

public class BouncyBullet extends Projectile {
    private static BufferedImage[] BOUNCY_BULLET;

    static {
        BOUNCY_BULLET = LoadSave.GetImagesFromSpriteSheet("placeholder.png", 10, 10, 10);
    }

    public BouncyBullet(int x, int y, int speed, double directionX, double directionY, int damage) {
        super(x, y, speed, directionX, directionY, damage);
        hitbox = new Rectangle2D.Float(x, y, 10, 10);
        animations = BOUNCY_BULLET;
    }

    public void update(int[][] collisionTile, Playing playing) {
        current_tick++;
        updatePos();
        if (checkPlayerHit(hitbox, playing.getPlayer())) {
            setActive(false);
        }

        if (hitbox.x <= 0 || hitbox.x >= collisionTile[0].length * Game.TILE_SIZE) {
            directionX = -directionX;
        }

        if (hitbox.y <= 0 || hitbox.y >= collisionTile.length * Game.TILE_SIZE) {
            directionY = -directionY;
        }
    }
}
