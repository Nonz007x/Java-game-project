package entities;

import gamestates.Playing;
import main.Game;
import objects.ProjectileManager;

import java.awt.*;

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
