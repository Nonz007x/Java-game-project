package entities.enemies;

import entities.Enemy;
import gamestates.Playing;
import utils.LoadSave;

import java.awt.image.BufferedImage;

import static utils.Constants.EnemyConstants.*;

public class GrandPrix extends Enemy {

    private static final BufferedImage[][] grandPrixImages;
    private static final int DEFAULT_WIDTH = GRANDPRIX_WIDTH_DEFAULT;
    private static final int DEFAULT_HEIGHT = GRANDPRIX_HEIGHT_DEFAULT;
    private static final int DEFAULT_SPEED = 3;
    private static final int DEFAULT_DETECTION_RANGE = 3;

    static {
        grandPrixImages = loadImages(LoadSave.GetSprite(LoadSave.GRANDPRIX), 3, 3, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
    // TODO Object pooling
    public GrandPrix(int x, int y) {
        super(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT, 35);

        animations = grandPrixImages;
        initHitbox(5, 10, DEFAULT_WIDTH - 5, DEFAULT_HEIGHT - 10);
        setSpeed(DEFAULT_SPEED);
        setDetectionRange(DEFAULT_DETECTION_RANGE);
    }

    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);
        System.out.println("ผีหลอกวิญญาณหลอน");
    }

    @Override
    public void setActive(boolean active) {
        super.setActive(active);
        System.out.println("ผีตายห่า");
    }

    public void update(int[][] collisionTile, Playing playing) {
        updateAnimationTick();
        updateBehavior(collisionTile, playing);
    }

    private void updateBehavior(int[][] collisionTile, Playing playing) {
        int playerX = playing.getPlayer().getX();
        int playerY = playing.getPlayer().getY();

        checkCollision(collisionTile);

        if (isPlayerInRange(playing.getPlayer())) {
            int deltaX = playerX - worldX;
            int deltaY = playerY - worldY;

            double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

            if (distance > 0) {
                double directionX = deltaX / distance;
                double directionY = deltaY / distance;

                velocityX = (int) (speed * directionX);
                velocityY = (int) (speed * directionY);

                if (velocityX > 0 && collisionRight) {
                    velocityX = 0;
                } else if (velocityX < 0 && collisionLeft) {
                    velocityX = 0;
                }

                if (velocityY < 0 && collisionUp) {
                    velocityY = 0;
                } else if (velocityY > 0 && collisionDown) {
                    velocityY = 0;
                }

                updateXPos(velocityX);
                updateYPos(velocityY);
            } else {
                velocityX = 0;
                velocityY = 0;
            }
        } else {
            state = 1;
            aniIndex = 0;
        }

        if (velocityX == 0 && velocityY == 0) {
            return;
            // Maintain previous state
        } else if (velocityY >= 0) {
            state = 0;
        } else if (velocityY < 0) {
            state = 2;
        }

        if (velocityX != 0) {
            state = 1;
        }

        if (hitbox.intersects(playing.getPlayer().getHitbox())) {
//            Gamestate.state = Gamestate.QUIT;
        }
    }
}
