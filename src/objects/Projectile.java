package objects;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import entities.Player;
import gamestates.Playing;

public class Projectile {
    protected int damage;
    protected Rectangle2D.Float hitBox;
    protected int TICKS_TO_LIVE;
    protected BufferedImage animations[];
    protected int aniTick, aniIndex;
    protected int current_tick;
    protected int speed;
    protected double directionX;
    protected double directionY;
    protected boolean active = true;

    public Projectile(int x, int y, int speed, double directionX, double directionY, int damage) {
        this.speed = speed;
        this.directionX = directionX;
        this.directionY = directionY;
        this.damage = damage;
        this.TICKS_TO_LIVE = 600;
    }

    public Projectile(int x, int y, int speed, double directionX, double directionY, int damage, int TICKS_TO_LIVE) {
        this(x, y, speed, directionX, directionY, damage);
        this.TICKS_TO_LIVE = TICKS_TO_LIVE;
    }

    protected void updatePos() {
        // update velocity
        hitBox.y += directionY * speed;
        hitBox.x += directionX * speed;
    }

    public void setPos(int x, int y) {
        hitBox.x = x;
        hitBox.y = y;
    }

    public Rectangle2D.Float getHitBox() {
        return hitBox;
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

    public int getDamage() {
        return damage;
    }


    public void draw(Graphics2D g2, int xOffset, int yOffset) {
        if (active) {
            g2.drawImage(animations[aniIndex], (int) hitBox.x + xOffset, (int) hitBox.y + yOffset, (int) hitBox.width, (int) hitBox.height, null);
//            g2.fillRect((int) hitBox.x + xOffset, (int) hitBox.y + yOffset, (int) hitBox.width, (int) hitBox.height);
        }
    }

    public void update(int[][] collisionTile, Playing playing) {
        current_tick++;
        updatePos();
    }

    protected void updateAnitick(int cooldown, int maxSprite) {
        aniTick++;
        if (aniTick >= cooldown) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= maxSprite) {
                aniIndex = 0;
            }
        }
    }

}
