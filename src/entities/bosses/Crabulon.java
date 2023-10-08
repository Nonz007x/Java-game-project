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
    private boolean aiming = false;
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

        if (counter == 60) {
            aiming = true;
            velocityX = 4;
        }

        if (counter == 120) {
            aimAtPlayer(playing);
            aiming = false;
            velocityX = 0;
        }

        if (counter == 180) {
            velocityX = -4;
        }

        if (counter == 240) {
            velocityX = 0;
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
        CRABULON_IMAGES = LoadSave.GetImagesFromSpriteSheet(CRABULON, 312, 196, 3, 6);
    }

    @Override
    public void draw(Graphics2D g2, int xOffset, int yOffset) {
        super.draw(g2, xOffset, yOffset);
        if (!active)
            return;
        if (aiming) {
            int deltaX = playerPos.x - worldX;
            int deltaY = playerPos.y - worldY;

            double scaleFactor = 100.0;

            double scaledDeltaX = deltaX * scaleFactor;
            double scaledDeltaY = deltaY * scaleFactor;

            int newX = worldX + xOffset + (int) scaledDeltaX;
            int newY = worldY + yOffset + (int) scaledDeltaY;

            g2.drawLine(worldX + xOffset, worldY + yOffset, newX, newY);
        }
    }

    private void shootLaser(Playing playing) {
        int playerX = (int) playing.getPlayer().getHitbox().getX();
        int playerY = (int) playing.getPlayer().getHitbox().getY();

        int deltaX = playerX - worldX;
        int deltaY = playerY - worldY;

        double distance = Math.max(0.000000001, Math.sqrt(deltaX * deltaX + deltaY * deltaY));

        double directionX = deltaX / distance;
        double directionY = deltaY / distance;

    }
}
