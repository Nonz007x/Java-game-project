package objects;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import gamestates.Playing;
import main.Game;

import static utils.HelpMethods.IsProjectileHittingLevel;

public class Projectile {
    protected Rectangle2D.Float hitbox;
    protected int TICKS_TO_LIVE = 600;
    protected boolean isPlayerProjectile;
    protected BufferedImage animations[];
    protected int aniTick, aniIndex;
    protected int current_tick;
    protected int speed;
    protected double directionX;
    protected double directionY;
    protected boolean active = true;

    public Projectile(int x, int y, int speed, double directionX, double directionY) {
        this.speed = speed;
        this.directionX = directionX;
        this.directionY = directionY;
        this.isPlayerProjectile = false;
        hitbox = new Rectangle2D.Float(x, y, 50, 50);
    }

    public Projectile(int x, int y, int speed, double directionX, double directionY, boolean isPlayerProjectile, int TICKS_TO_LIVE) {
        this(x, y, speed, directionX, directionY);
        this.isPlayerProjectile = isPlayerProjectile;
        this.TICKS_TO_LIVE = TICKS_TO_LIVE;
    }

    public Projectile(int x, int y, int speed, double directionX, double directionY, int TICKS_TO_LIVE) {
        this(x, y, speed, directionX, directionY);
        this.TICKS_TO_LIVE = TICKS_TO_LIVE;
    }

    protected void updatePos() {
        // update velocity
        hitbox.y += directionY * speed;
        hitbox.x += directionX * speed;
    }

    public void setPos(int x, int y) {
        hitbox.x = x;
        hitbox.y = y;
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isWithinTickLimit() {
        return current_tick < TICKS_TO_LIVE;
    }

    public void draw(Graphics2D g2, int xOffset, int yOffset) {
        if (active) {
            g2.fillRect((int) hitbox.x + xOffset, (int) hitbox.y + yOffset, (int) hitbox.width, (int) hitbox.height);
//            g2.drawImage(animations[aniIndex], (int) hitbox.x + xOffset, (int) hitbox.y + yOffset, (int) hitbox.width, (int) hitbox.height, null);
        }
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

}
