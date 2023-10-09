package objects;

import entities.Player;
import gamestates.Playing;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class EnemyProjectile extends Projectile {
    public EnemyProjectile(int x, int y, int speed, double directionX, double directionY, int damage, int TICKS_TO_LIVE) {
        super(x, y, speed, directionX, directionY, damage, TICKS_TO_LIVE);
    }

    protected boolean checkPlayerHit(Rectangle2D.Float hitbox, Player player) {
        return (hitbox.intersects(player.getHitbox()));
    }

    public boolean checkPlayerHit(Line2D.Float hitbox, Player player) {
        return (hitbox.intersects(player.getHitbox()));
    }

    protected boolean checkLaserHitPlayer(Line2D.Float line, Player player, float lineWidth) {

        double angle = Math.atan2(line.getY2() - line.getY1(), line.getX2() - line.getX1());

        double xOffset = lineWidth / 2 * Math.sin(angle);
        double yOffset = lineWidth / 2 * Math.cos(angle);

        int[] xPoints = {
                (int) (line.getX1() - xOffset),
                (int) (line.getX2() - xOffset),
                (int) (line.getX2() + xOffset),
                (int) (line.getX1() + xOffset)
        };
        int[] yPoints = {
                (int) (line.getY1() + yOffset),
                (int) (line.getY2() + yOffset),
                (int) (line.getY2() - yOffset),
                (int) (line.getY1() - yOffset)
        };

        return new Polygon(xPoints, yPoints, 4).intersects(player.getHitbox());
    }
    @Override
    public void update(int[][] collisionTile, Playing playing) {
        super.update(collisionTile, playing);
        if (checkPlayerHit(hitbox, playing.getPlayer())) {
            playing.getPlayer().takeDamage(damage);
            setActive(false);
        }
    }
}
