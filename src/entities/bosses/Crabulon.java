package entities.bosses;

import entities.Boss;
import gamestates.Playing;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.BossConstants.*;
import static utils.Constants.PlayerConstants.IDLE;
import static utils.Constants.PlayerConstants.RUNNING;
import static utils.LoadSave.CRABULON;

public class Crabulon extends Boss {
    private Point playerPos = new Point();
    private static BufferedImage[][] CRABULON_IMAGES;

    static {
        loadBossImages();
    }

    public Crabulon(int x, int y) {
        super(x, y, CRABULON_WIDTH_DEFAULT, CRABULON_HEIGHT_DEFAULT, CRABULON_HEALTH);
        initHitbox(32, 8, width - 64, height - 8);
        animations = CRABULON_IMAGES;
    }

    @Override
    public void update(int[][] collisionTile, Playing playing) {
        playerPos.x = (int) playing.getPlayer().getHitbox().getX();
        playerPos.y = (int) playing.getPlayer().getHitbox().getY();
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
        } else {
            state = IDLE;
        }

    }

    @Override
    public void setActive(boolean active) {
        super.setActive(active);
        System.out.println("Crabulon has been defeated!");
    }

    private static void loadBossImages() {
        final int stateSize = 3;
        final int animationSize = 6;

        BufferedImage sprites = LoadSave.GetSprite(CRABULON);

        int spriteWidth = 312;
        int spriteHeight = 196;

        CRABULON_IMAGES = new BufferedImage[stateSize][animationSize];

        for (int i = 0; i < stateSize; i++)
            for (int j = 0; j < animationSize; j++) {
                CRABULON_IMAGES[i][j] = sprites.getSubimage(j * spriteWidth, i * spriteHeight, spriteWidth, spriteHeight);
            }
    }

    @Override
    public void draw(Graphics2D g2, int xOffset, int yOffset) {
        super.draw(g2, xOffset, yOffset);
        g2.drawLine(worldX + xOffset, worldY + yOffset, playerPos.x + xOffset, playerPos.y + yOffset);
    }
}
