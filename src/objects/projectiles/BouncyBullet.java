package objects.projectiles;

import gamestates.Playing;
import main.Game;
import objects.Projectile;

import java.awt.*;

import static utils.HelpMethods.IsProjectileHittingLevel;

public class BouncyBullet extends Projectile {

    public BouncyBullet(int x, int y, int speed, double directionX, double directionY, int damage) {
        super(x, y, speed, directionX, directionY, damage);
    }

    public void update(int[][] collisionTile, Playing playing) {
        current_tick++;
        updatePos();
        if (hitbox.intersects(playing.getPlayer().getHitbox()) || IsProjectileHittingLevel(this, collisionTile)) {
            setActive(false);
        }

        if (hitbox.x <= 0 || hitbox.x >= collisionTile[0].length * Game.TILE_SIZE) {
            directionX = -directionX;
        }

        if (hitbox.y <= 0 || hitbox.y >= collisionTile.length * Game.TILE_SIZE) {
            directionY = -directionY;
        }
    }

    @Override
    public void draw(Graphics2D g2, int xOffset, int yOffset) {
        super.draw(g2, xOffset, yOffset);
        g2.drawLine(0, 0, 100, 100);
    }
}
