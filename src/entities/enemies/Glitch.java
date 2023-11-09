package entities.enemies;

import entities.Enemy;
import gamestates.Playing;
import utils.LoadSave;

import java.awt.image.BufferedImage;

public class Glitch extends Enemy {
    private static final BufferedImage[][] images;
    private int counter = 0;

    static {
        images = LoadSave.GetImagesFromSpriteSheet("glitch.png", 16, 16, 1, 1);
    }
    public Glitch(int x, int y) {
        super(x, y, 48, 48, 30);
        animations = images;
        initHitBox(0, 0,  48, 48);
        setSpeed(0);
        setDetectionRange(20);
    }

    @Override
    public void update(int[][] collisionTile, Playing playing) {
        updateBehavior(collisionTile, playing);
    }

    @Override
    protected void updateBehavior(int[][] collisionTile, Playing playing) {
        if (isPlayerInRange(Playing.getPlayer())) {
            counter++;
            if (counter >= 60) {
                shootAtPlayer(20, 15);
                counter = 0;
            }
        } else {
            counter = 0;
        }
    }
}
