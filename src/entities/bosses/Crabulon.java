package entities.bosses;

import entities.Boss;
import gamestates.Playing;
import objects.projectiles.BouncyBullet;
import objects.projectiles.LaserBeam;
import utils.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static utils.Constants.BossConstants.*;
import static utils.Constants.PlayerConstants.IDLE;
import static utils.Constants.PlayerConstants.RUNNING;
import static utils.LoadSave.CRABULON;

public class Crabulon extends Boss {
    private Point playerPos = new Point();
    private Point tempPlayerPos = new Point();
    private boolean aiming = false, hold = false;
    private static final BufferedImage[][] CRABULON_IMAGES;

    static {
        CRABULON_IMAGES = LoadSave.GetImagesFromSpriteSheet(CRABULON, 312, 196, 3, 6);
    }

    public Crabulon(int x, int y) {
        super(x, y, CRABULON_WIDTH_DEFAULT, CRABULON_HEIGHT_DEFAULT, CRABULON_HEALTH);
        initHitBox(32, 8, width - 64, height - 8);
        animations = CRABULON_IMAGES;
    }

    @Override
    protected void updateBehavior(int[][] collisionTile, Playing playing) {
        if (checkPlayerHit(hitBox, playing.getPlayer())) {
            playing.getPlayer().takeDamage(20);
        }

        playerPos.x = playing.getPlayer().getHitBoxCenterX();
        playerPos.y = playing.getPlayer().getHitBoxCenterY();

        checkMove(collisionTile);
        counter++;

        if (counter >= 60 && counter <= 90) {
            chase(playing.getPlayer(), 5);
            aiming = true;
        }

        if (counter == 100) {
            aiming = false;
            tempPlayerPos.x = playerPos.x;
            tempPlayerPos.y = playerPos.y;
        }

        if (counter == 110) {
            velocityX = 0;
            velocityY = 0;
        }

        if (counter == 119) {

            for (int i = 0; i < 360; i += 10) {
                double angleInRadians = Math.toRadians(i);

                shootBouncyBullet(new double[]{Math.cos(angleInRadians), Math.sin(angleInRadians)});
            }
        }

        if (counter == 120 || counter == 126 || counter == 132) {
            int predictionX = (int) playing.getPlayer().getVelocityX() * 40;
            int predictionY = (int) playing.getPlayer().getVelocityY() * 40;
            double[] directions = aimAtPos(getHitBoxCenterX(), getHitBoxCenterY(),
                    playerPos.x + predictionX,
                    playerPos.y + predictionY);

            double[] directionsNoPrediction = aimAtPos(getHitBoxCenterX(), getHitBoxCenterY(),
                    playerPos.x,
                    playerPos.y);

            shootBouncyBullet(directions);
            shootBouncyBullet(directionsNoPrediction);

        }

        if (counter == 180) {
            chase(playing.getPlayer(), 15);
        }

        if (counter == 240) {
            counter = 0;
        }

        updateXPos(velocityX);
        updateYPos(velocityY);

        state = (velocityX != 0 || velocityY != 0) ? RUNNING : IDLE;
    }

    private void shootBouncyBullet(double[] directions) {
        shootProjectile(new BouncyBullet(getHitBoxCenterX(),
                getHitBoxCenterY(),
                10,
                directions[0],
                directions[1],
                15));
    }

    @Override
    public void setActive(boolean active) {
        super.setActive(active);
        System.out.println("Crabulon has been defeated!");
    }

    @Override
    public void draw(Graphics2D g2, int xOffset, int yOffset) {
        if (!active)
            return;
        super.draw(g2, xOffset, yOffset);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        g2.drawImage(animations[state][aniIndex], worldX + xOffset + getflipX(), worldY + yOffset, width * getflipW(), height, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        if (aiming) {
            int targetX = playerPos.x + xOffset;
            int targetY = playerPos.y + yOffset;
            g2.drawLine(getHitBoxCenterX() + xOffset, getHitBoxCenterY() + yOffset, targetX, targetY);
        }
    }
}
