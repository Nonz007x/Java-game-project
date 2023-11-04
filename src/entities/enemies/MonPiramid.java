package entities.enemies;

import entities.Enemy;
import gamestates.Playing;
import utils.LoadSave;

import java.awt.image.BufferedImage;

public class MonPiramid extends Enemy {
    private static final BufferedImage[][] images;
    private static final int DEFAULT_WIDTH = 64;
    private static final int DEFAULT_HEIGHT = 64;
    private static final int DEFAULT_SPEED = 4;
    private static final int DEFAULT_DETECTION_RANGE = 10;

    static {
        images = loadImages(LoadSave.GetSprite("cumspitter.png"), 4, 1, 32, 32);
    }

    public MonPiramid(int x, int y) {
        super(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT, 1);

        animations = images;
        initHitBox(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setSpeed(DEFAULT_SPEED);
        setDetectionRange(DEFAULT_DETECTION_RANGE);
    }

    @Override
    public void takeDamage(int damage) {
    }
    @Override
    public void update(int[][] collisionTile, Playing playing) {
        updateAnimationTick();
        updateBehavior(collisionTile, playing);
    }

    @Override
    protected void updateBehavior(int[][] collisionTile, Playing playing) {
        chase(playing.getPlayer(), DEFAULT_SPEED);
        if (checkPlayerHit(hitBox, playing.getPlayer())) {
            playing.getPlayer().takeDamage(Integer.MAX_VALUE);
        }
        checkMove(collisionTile);
        updateXPos(velocityX);
        updateYPos(velocityY);
    }
}
