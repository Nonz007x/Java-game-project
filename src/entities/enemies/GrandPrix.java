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
    private static final int CHARGE_TICK = 90;
    private static final int CHARGE_COOLDOWN = 35;
    private int currentChargeTick = 0;

    static {
        grandPrixImages = loadImages(LoadSave.GetSprite(LoadSave.GRANDPRIX), 3, 3, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    // TODO Object pooling
    public GrandPrix(int x, int y) {
        super(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT, 35);
        animations = grandPrixImages;
        initHitBox(5, 10, DEFAULT_WIDTH - 5, DEFAULT_HEIGHT - 10);
        setSpeed(DEFAULT_SPEED);
        setDetectionRange(DEFAULT_DETECTION_RANGE);
    }

    @Override
    protected void updateBehavior(int[][] collisionTile, Playing playing) {

//        currentChargeTick++;
//        if (currentChargeTick == 0) {
//            chase(playing.getPlayer(), 5 * DEFAULT_SPEED);
//        }
//        else if (currentChargeTick >= CHARGE_TICK) {
//            velocityX = 0;
//            velocityY = 0;
//            int x = RandomDirection() * RandomNumber(64, 112) + playing.getPlayer().getHitBoxCenterX();
//            int y = RandomDirection() * RandomNumber(64, 112) + playing.getPlayer().getHitBoxCenterY();;
//            currentChargeTick = -CHARGE_COOLDOWN;
//            teleport(x, y);
//        }
        chase(playing.getPlayer(), DEFAULT_SPEED);
        checkMove(collisionTile);
        updateXPos(velocityX);
        updateYPos(velocityY);

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
    }
}
