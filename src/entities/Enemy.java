package entities;

import gamestates.Playing;
import main.Game;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import static utils.Constants.EnemyConstants.GetSpriteAmount;
import static utils.Constants.EnemyConstants.*;


public abstract class Enemy extends Entity {
    protected boolean active = true;
    protected int enemyType;
    protected int detectionRange;

    public Enemy(int x, int y, int width, int height, int health) {
        super(x, y, width, height, health);
        this.state = IDLE;
    }

    protected boolean isPlayerInRange(Player player) {
        int xRange = (int) Math.abs(player.getHitboxX() - hitbox.x);
        int yRange = (int) Math.abs(player.getHitboxY() - hitbox.y);
        int range = detectionRange * Game.TILE_SIZE;
        return xRange <= range && yRange <= range;
    }

    protected void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(state, enemyType)) {
                aniIndex = 0;
            }
        }
    }

    public int getAniIndex() {
        return aniIndex;
    }


    public int getflipX() {
        if (velocityX >= 0)
            return 0;
        else
            return width;
    }

    public int getflipW() {
        if (velocityX >= 0)
            return 1;
        else
            return -1;
    }

    protected void setDetectionRange(int range) {
        detectionRange = range;
    }

    public void takeDamage(int damage) {
        currentHealth -= damage;
        if (currentHealth <= 0) {
            setActive(false);
        }
    }

    public void shootLaser() {

    }

    protected boolean checkPlayerHit(Rectangle2D hitbox, Player player) {
        return hitbox.intersects(player.getHitbox());
    }

    protected boolean checkPlayerHit(Line2D.Float line, Player player) {
        float lineWidth = 80.0f;

        // Calculate the angle of the line
        double angle = Math.atan2(line.getY2() - line.getY1(), line.getX2() - line.getX1());

        // Calculate the offsets to add/subtract from the line endpoints
        double xOffset = lineWidth / 2 * Math.sin(angle);
        double yOffset = lineWidth / 2 * Math.cos(angle);

        // Calculate the coordinates of the vertices
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

    protected void chase(Player player, int speed) {
        int playerX = player.getHitboxCenterX();
        int playerY = player.getHitboxCenterY();

        int deltaX = playerX - getHitboxCenterX();
        int deltaY = playerY - getHitboxCenterY();

        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        if (distance > 0) {
            double directionX = deltaX / distance;
            double directionY = deltaY / distance;

            velocityX = (int) (speed * directionX);
            velocityY = (int) (speed * directionY);

        } else {
            velocityX = 0;
            velocityY = 0;
        }
    }

    protected void checkMove(int[][] collisionTile) {
        checkCollision(collisionTile);

        if ((velocityX > 0 && collisionRight) || (velocityX < 0 && collisionLeft)) {
            velocityX = 0;
        }

        if ((velocityY < 0 && collisionUp) || (velocityY > 0 && collisionDown)) {
            velocityY = 0;
        }
    }

    public void draw(Graphics2D g2, int xOffset, int yOffset) {
        if (active) {
            drawHitbox(g2, xOffset, yOffset);
            g2.drawImage(animations[state][aniIndex], worldX + xOffset + getflipX(), worldY + yOffset, width * getflipW(), height, null);
        }
    }

    public abstract void update(int[][] collisionTile, Playing playing);

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
