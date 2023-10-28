package objects.projectiles;

import gamestates.Playing;
import objects.EnemyProjectile;
import utils.LoadSave;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

public class LaserBeam extends EnemyProjectile {
    private static final BufferedImage[] LASER;
    private int endPointX, endPointY;
    private int startPointX, startPointY;

    static {
//        LASER = LoadSave.GetSprite("laser.png");
        LASER = LoadSave.GetImagesFromSpriteSheet("Laser_sheet.png", 300, 100,  15, true);
    }

    public LaserBeam(int startPointX, int startPointY, int endPointX, int endPointY, int damage) {
        super(startPointX, startPointY, 0, 0, 0, damage, 120);
        this.startPointX = startPointX;
        this.startPointY = startPointY;
        this.endPointX = endPointX;
        this.endPointY = endPointY;

    }

    @Override
    public void update(int[][] collisionTile, Playing playing) {
        if (!active)
            return;
        current_tick++;

        int deltaX = endPointX - startPointX;
        int deltaY = endPointY - startPointY;

        double scaleFactor = 100.0;

        double scaledDeltaX = deltaX * scaleFactor;
        double scaledDeltaY = deltaY * scaleFactor;

        int newX = startPointX + (int) scaledDeltaX;
        int newY = startPointY + (int) scaledDeltaY;
        if (checkLaserHitPlayer(new Line2D.Float(startPointX, startPointY, newX, newY), playing.getPlayer(), 16)) {
            playing.getPlayer().takeDamage(damage);
        }
    }

    @Override
    public void draw(Graphics2D g2, int xOffset, int yOffset) {
        if (!active)
            return;
        updateAnitick(16, 14);
        int deltaX = endPointX - startPointX;
        int deltaY = endPointY - startPointY;

        double scaleFactor = 80.0;

        double scaledDeltaX = deltaX * scaleFactor;
        double scaledDeltaY = deltaY * scaleFactor;

        double angle = Math.atan2(scaledDeltaY, scaledDeltaX);

        AffineTransform transform = new AffineTransform();
        transform.translate(startPointX + xOffset, startPointY + yOffset);
        transform.rotate(angle);
        transform.translate(-166.5*2, -120*2);

        g2.setTransform(transform);
        g2.drawImage(LASER[aniIndex], 0, 0, 1000*2, 333*2, null);

        g2.setTransform(new AffineTransform());
    }

    protected void updateAnitick(int cooldown, int maxSprite) {
        aniTick++;
        if (aniTick >= cooldown) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= maxSprite) {
                setActive(false);
            }
        }
    }
}
