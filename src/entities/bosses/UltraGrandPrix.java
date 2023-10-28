package entities.bosses;

import entities.Boss;
import gamestates.Playing;
import objects.projectiles.BasicBullet;
import objects.projectiles.BouncyBullet;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.BossConstants.*;
import static utils.HelpMethods.RandomDirection;
import static utils.HelpMethods.RandomNumber;

public class UltraGrandPrix extends Boss {
    private static final int CHARGE_TICK = 25;
    private static final int CHARGE_COOLDOWN = 60;
    private int currentChargeTick = 0;
    private int chaseCount = 0;
    private boolean found = false;

    private static final BufferedImage[][] IMAGES;

    static {
        IMAGES = LoadSave.GetImagesFromSpriteSheet("ultra_grand_prix.png", 64, 64, 3, 3);
    }

    public UltraGrandPrix(int x, int y) {
        super(x, y, ULTRA_GRAND_PRIX_WIDTH_DEFAULT, ULTRA_GRAND_PRIX_HEIGHT_DEFAULT, ULTRA_GRAND_PRIX_HEALTH);
        initHitbox(5, 10, ULTRA_GRAND_PRIX_WIDTH_DEFAULT - 5, ULTRA_GRAND_PRIX_HEIGHT_DEFAULT - 10);
        setSpeed(4);
        animations = IMAGES;
    }

    protected void updateBehavior(int[][] collisionTile, Playing playing) {
        flipX = velocityX >= 0 ? 0 : width;
        flipW = velocityX >= 0 ? 1 : -1;

        // Phase 1
        if (currentHealth > maxHealth * 0.3) {
            if (!found) {
                double centerYDifference = getHitboxCenterY() - playing.getPlayer().getHitboxCenterY();
                double tolerance = 24.0;

                if (centerYDifference > tolerance) {
                    velocityY = -speed;
                } else if (centerYDifference < -tolerance) {
                    velocityY = speed;
                }

                if (centerYDifference > -tolerance && centerYDifference < tolerance) {
                    chase(playing.getPlayer(), speed * 2);
                    found = true;
                }
            }
            if (found) {
                counter++;
                if (collisionRight || collisionLeft) {
                    velocityY = 0;
                    found = false;
                    counter = 0;
                } else if (counter == 20) {
                    shootAtPlayer(playing, 10, 15);
                    shootProjectile(new BasicBullet(getHitboxCenterX(),
                            getHitboxCenterY(),
                            10,
                            0,
                            -1,
                            15));

                    shootProjectile(new BasicBullet(getHitboxCenterX(),
                            getHitboxCenterY(),
                            10,
                            0,
                            1,
                            15));
                    counter = 0;
                }
            }
            updateXPos(velocityX);
            updateYPos(velocityY);
        }
        // Phase 2
        else {
            currentChargeTick++;

            if (currentChargeTick == 0) {
                chase(playing.getPlayer(), 20);
            } else if (currentChargeTick >= CHARGE_TICK) {
                if (chaseCount == 0 && currentChargeTick == CHARGE_TICK + 10) {
                    teleport(playing);
                    currentChargeTick = -CHARGE_COOLDOWN;
                    chaseCount++;
                } else if (chaseCount == 1) {
                    if (currentChargeTick == CHARGE_TICK) {
                        velocityX = 0;
                        velocityY = 0;
                    } else if (currentChargeTick == CHARGE_TICK + 10) {
                        chase(playing.getPlayer(), 20);
                    } else if (currentChargeTick >= CHARGE_TICK * 2 + 10) {
                        teleport(playing);
                        chaseCount++;
                        currentChargeTick = -CHARGE_COOLDOWN;
                    }
                } else {
                    chaseCount = 0;
                }
            }


        }

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

    protected void teleport(Playing playing) {
        velocityX = 0;
        velocityY = 0;
        int directionX = RandomNumber(-1, 1);
        int directionY = directionX == 0 ? RandomDirection() : RandomNumber(-1, 1);

        int x = directionX * 128 + playing.getPlayer().getHitboxCenterX();
        int y = directionY * 128 + playing.getPlayer().getHitboxCenterY();

        currentChargeTick = -CHARGE_COOLDOWN;
        super.teleport((int) (x - getHitboxWidth() / 2), (int) (y - getHitboxHeight() / 2));
    }

    public void draw(Graphics2D g2, int xOffset, int yOffset) {
        if (active) {
            drawHitbox(g2, xOffset, yOffset);
            g2.drawImage(animations[state][aniIndex], worldX + xOffset + flipX, worldY + yOffset, width * flipW, height, null);
        }
    }

    @Override
    public void resetEnemy() {
        super.resetEnemy();
        found = false;
        counter = 0;
    }
}
