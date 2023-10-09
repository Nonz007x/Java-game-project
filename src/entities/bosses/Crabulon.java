package entities.bosses;

import entities.Boss;
import gamestates.Playing;
import objects.projectiles.BouncyBullet;
import objects.projectiles.LaserBeam;
import objects.projectiles.LaserSweep;
import utils.LoadSave;

import java.awt.*;
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
        initHitbox(32, 8, width - 64, height - 8);
        animations = CRABULON_IMAGES;
    }

    @Override
    public void update(int[][] collisionTile, Playing playing) {

        if (hitbox.intersects(playing.getPlayer().getHitbox())) {
            playing.getPlayer().takeDamage(20);
        }

        playerPos.x = playing.getPlayer().getHitboxCenterX();
        playerPos.y = playing.getPlayer().getHitboxCenterY();

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

            shootProjectile(new LaserBeam(getHitboxCenterX(),
                    getHitboxCenterY(),
                    tempPlayerPos.x,
                    tempPlayerPos.y,
                    40));
        }

        if (counter == 120 || counter == 126 || counter == 132) {
            int predictionX = (int) playing.getPlayer().getVelocityX() * 40;
            int predictionY = (int) playing.getPlayer().getVelocityY() * 40;
            double[] directions = aimAtPos(getHitboxCenterX(), getHitboxCenterY(),
                    playerPos.x + predictionX,
                    playerPos.y + predictionY);

            double[] directionsNoPrediction = aimAtPos(getHitboxCenterX(), getHitboxCenterY(),
                    playerPos.x,
                    playerPos.y);

            shootBouncyBullet(directions);
            shootBouncyBullet(directionsNoPrediction);

        }

        if (counter == 180) {
            chase(playing.getPlayer(), 8);
        }

        if (counter == 240) {
            counter = 0;
        }


        updateXPos(velocityX);
        updateYPos(velocityY);

        state = (velocityX != 0 || velocityY != 0) ? RUNNING : IDLE;
    }

    private void shootBouncyBullet(double[] directions) {
        shootProjectile(new BouncyBullet(getHitboxCenterX(),
                getHitboxCenterY(),
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
        super.draw(g2, xOffset, yOffset);
        if (aiming) {
            int targetX = playerPos.x + xOffset;
            int targetY = playerPos.y + yOffset;
            g2.drawLine(getHitboxCenterX() + xOffset, getHitboxCenterY() + yOffset, targetX, targetY);
        }
    }
}
