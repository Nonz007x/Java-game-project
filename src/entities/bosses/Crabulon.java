package entities.bosses;

import entities.Boss;
import gamestates.Playing;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.PlayerConstants.IDLE;
import static utils.Constants.PlayerConstants.RUNNING;
import static utils.LoadSave.CRABULON;

public class Crabulon extends Boss {
    public Crabulon(int x, int y, int width, int height) {
        super(x, y, width, height);
        initHitbox(32, 8, width - 64, height - 8);
        loadBossImages();
    }

    @Override
    public void update(int[][] collisionTile, Playing playing) {

        checkCollision(collisionTile);
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

        counter++;

        if (counter % 60 == 0) {
            aimAtPlayer(playing);
            velocityX = 4;
        }

        if (counter % 120 == 0) {
            velocityX = 0;
        }

        if (counter % 180 == 0) {
            velocityX = -4;
        }

        if (counter % 240 == 0) {
            counter = 0;
        }

        updateXPos(velocityX);
        updateYPos(velocityY);

        if (velocityX != 0 || velocityY != 0) {
            state = RUNNING;
        }
        else {
            state = IDLE;
        }

    }

    private void loadBossImages() {
        final int stateSize = 3;
        final int animationSize = 6;

        BufferedImage sprites = LoadSave.GetSprite(CRABULON);

        int spriteWidth = 312;
        int spriteHeight = 196;

        bossAnimation = new BufferedImage[stateSize][animationSize];

        for (int i = 0; i < stateSize; i++)
            for (int j = 0; j < animationSize; j++) {
                bossAnimation[i][j] = sprites.getSubimage(j * spriteWidth, i * spriteHeight, spriteWidth, spriteHeight);
            }
    }

}
