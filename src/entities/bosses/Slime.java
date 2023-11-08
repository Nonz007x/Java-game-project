package entities.bosses;

import entities.Boss;
import entities.Player;
import gamestates.Playing;
import objects.projectiles.BasicBullet;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

import static utils.Constants.BossConstants.*;

public class Slime extends Boss {
    private static final BufferedImage[][] images;
    private static final int DEFAULT_SPEED = 4;
    private boolean alternate = false;
    private boolean jumping = false;
    private int drawOffset = 0;
    private int jumpOffset = 0;
    private final Random random = new Random();
    private int moveSet = 0;
    private int slamTimes = 3;

    int executionCount = 0;
    private final int MAX_EXECUTIONS = 10;

    static {
        images = LoadSave.GetImagesFromSpriteSheet("slime_blue.png", 32, 32, 4, 4);
    }

    public Slime(int x, int y) {
        super(x, y, SLIME_WIDTH_DEFAULT, SLIME_HEIGHT_DEFAULT, SLIME_HEALTH);
        animations = images;
        setSpeed(DEFAULT_SPEED);
        initHitBox(90, 90, 140, 140);
        aniSpeed = 30;
    }

    @Override
    protected void updateBehavior(int[][] collisionTile, Playing playing) {
        counter++;
        hitBoxEnable = !jumping;
        if (currentHealth > 0) {
            switch (moveSet) {
                case 0, 1, 2, 3, 4 -> groundSlam(playing);
                case 5, 6 -> barrageSlam(playing);
                case 7, 8, 9 -> bounce(playing);
            }

        } else {
            if (counter % 12 == 0) {
                dying();
            }
        }
        checkMove(collisionTile);
        updateXPos(velocityX);
        updateYPos(velocityY);
    }

    private void groundSlam(Playing playing) {
        if (counter > 40 && counter <= 80) {
            performJump();
        } else if (counter > 80 && counter <= 200) {
            chase(Playing.getPlayer(), 10);
        } else if (counter > 200 && counter <= 240) {
            stopMoving();
        } else if (counter > 240) {
            executeSlam(playing);
            randomMoveSet();
        }
    }

    private void barrageSlam(Playing playing) {
        if (slamTimes == 0) {
            randomMoveSet();
            slamTimes = 3;
        } else {
            if (counter > 0 && counter <= 10) {
                performJump();
            } else if (counter == 11) {
                moveTowardsPlayer(Playing.getPlayer());
            } else if (counter > 20 && counter <= 30) {
                stopMoving();
            } else if (counter == 31) {
                slamTimes--;
                executeSlam(playing);
                barrageSlam(playing);
            }
        }
    }

    private void bounce(Playing playing) {
        if (counter == 60) {
            chase(Playing.getPlayer(), 20);
        }

        if (checkPlayerHit(hitBox, Playing.getPlayer())) {
            Playing.getPlayer().takeDamage(30);
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

        if (counter >= 360) {
            counter = 0;
            velocityX = 0;
            velocityY = 0;
            randomMoveSet();
        }
    }

    private void randomMoveSet() {
        moveSet = random.nextInt(10);
    }

    private void performJump() {
        jumping = true;
        jumpOffset -= 80;
    }

    private void moveTowardsPlayer(Player player) {
        if (getHitBoxCenterX() < player.getHitBoxCenterX()) {
            velocityX = 10;
        } else {
            velocityX = -10;
        }
    }

    private void stopMoving() {
        velocityX = 0;
        velocityY = 0;
        jumpOffset += 80;
    }

    private void executeSlam(Playing playing) {
        jumping = false;
        counter = 0;
        if (checkPlayerHit(hitBox, Playing.getPlayer())) {
            Playing.getPlayer().takeDamage(50);
        }
        shootBulletsInRange(0, 360, 10);
    }

    @Override
    public void draw(Graphics2D g2, int xOffset, int yOffset) {
        if (active) {
            g2.setColor(new Color(0, 0, 0, 100));
            g2.fillOval((int) hitBox.x + xOffset, (int) hitBox.y + 48 + yOffset, (int) getHitBoxWidth(), (int) getHitBoxHeight() - 48);
            g2.drawImage(animations[state][aniIndex], worldX + xOffset + drawOffset + getflipX(),
                    worldY + yOffset + drawOffset + jumpOffset,
                    width * getflipW(), height,
                    null);
        }

    }

    public void shootBulletsInRange(double startAngle, double endAngle, double angleIncrement) {
        for (double j = startAngle; j < endAngle; j += angleIncrement) {
            double angleInRadians = Math.toRadians(j);

            shootProjectile(new BasicBullet(getHitBoxCenterX(),
                    getHitBoxCenterY(),
                    8,
                    Math.cos(angleInRadians),
                    Math.sin(angleInRadians),
                    15));
        }
    }

    @Override
    public void takeDamage(int damage) {
        currentHealth -= damage;
    }

    private void dying() {
        if (alternate) {
            shootBulletsInRange(0, 360, 12);
        } else {
            shootBulletsInRange(5, 365, 12);
        }
        width -= 32;
        height -= 32;

        drawOffset += 17;

        executionCount++;
        alternate = !alternate;

        if (executionCount >= MAX_EXECUTIONS) {
            drawOffset = 0;
            setActive(false);
        }
    }

    @Override
    public void resetEnemy() {
        super.resetEnemy();
        width = SLIME_WIDTH_DEFAULT;
        height = SLIME_HEIGHT_DEFAULT;
        jumpOffset = 0;
        drawOffset = 0;
        executionCount = 0;
    }
}
