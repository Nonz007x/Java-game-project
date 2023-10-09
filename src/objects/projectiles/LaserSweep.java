package objects.projectiles;

import gamestates.Playing;
import objects.EnemyProjectile;
import utils.LoadSave;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

import static utils.Constants.Projectile.DEFAULT_TICKS_TO_LIVE;

public class LaserSweep extends EnemyProjectile {
    private int startPointX;
    private int startPointY;
    private int endPointX;
    private int endPointY;
    private double angle = 0.0;
    private double angularSpeed = Math.toRadians(0.1);
    private static final BufferedImage LASER;
    private int width = 10000;
    private int height = 40;

    static {
        LASER = LoadSave.GetSprite("laser.png");
    }

    public LaserSweep(int startPointX, int startPointY, int damage) {
        super(startPointX, startPointY, 0, 0, 0, damage, DEFAULT_TICKS_TO_LIVE);
        this.startPointX = startPointX;
        this.startPointY = startPointY;
    }


    public void update(int[][] collisionTile, Playing playing) {
        if (!active)
            return;
        current_tick++;
        angle += angularSpeed;
        endPointX = (int) (startPointX + 10000 * Math.sin(angle));
        endPointY = (int) (startPointY - 10000 * Math.cos(angle));

        if (checkLaserHitPlayer(new Line2D.Float(startPointX, startPointY, endPointX, endPointY), playing.getPlayer(), 40.0f)) {
            playing.getPlayer().takeDamage(damage);
        }
    }

    @Override
    public void draw(Graphics2D g2, int xOffset, int yOffset) {
        if (!active)
            return;
        AffineTransform transform = new AffineTransform();
        transform.translate(startPointX + xOffset, startPointY + yOffset);
        transform.rotate(angle - Math.PI / 2);
        transform.translate(0, (double) -height /2);

        g2.setTransform(transform);
        g2.drawImage(LASER, 0, 0, width, height, null);

        g2.setTransform(new AffineTransform());
        g2.setColor(Color.BLUE);
        g2.drawLine(startPointX + xOffset, startPointY + yOffset, endPointX + xOffset, endPointY + yOffset);
    }
}