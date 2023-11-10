package entities.enemies;

import entities.Enemy;
import gamestates.Playing;
import utils.LoadSave;

import java.awt.image.BufferedImage;

import static utils.Constants.EnemyConstants.MON_CIRCLE;
import static utils.Constants.EnemyConstants.getSpriteAmount;

public class MonCircle extends Enemy {
    private static final BufferedImage[][] images;
    static {
        images = LoadSave.getImagesFromSpriteSheet("monCircle.png", 32, 32, 1, 2);
    }
    public MonCircle(int x, int y) {
        super(x, y, 48, 48, Integer.MAX_VALUE);
        animations = images;
        enemyType = MON_CIRCLE;
        initHitBox(0,0, 48, 48);
        velocityY = 10;
        velocityX = 10;
    }

    @Override
    protected void updateBehavior(int[][] collisionTile, Playing playing) {
        if (checkPlayerHit(hitBox, Playing.getPlayer())) {
            Playing.getPlayer().takeDamage(Integer.MAX_VALUE);
        }
        if (collisionLeft) {
            velocityX = 10;
        } else if (collisionRight) {
            velocityX = -10;
        } else if (collisionUp) {
            velocityY = 10;
        } else if (collisionDown) {
            velocityY = -10;
        }
        checkMove(collisionTile);
        updateXPos(velocityX);
        updateYPos(velocityY);
    }

    @Override
    public void resetEnemy() {
        super.resetEnemy();
        velocityY = 10;
        velocityX = 10;
    }
}
