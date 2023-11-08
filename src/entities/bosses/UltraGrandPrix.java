package entities.bosses;

import entities.Boss;
import entities.enemies.GrandPrix;
import gamestates.Playing;
import objects.projectiles.BasicBullet;
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
        initHitBox(5, 10, ULTRA_GRAND_PRIX_WIDTH_DEFAULT - 5, ULTRA_GRAND_PRIX_HEIGHT_DEFAULT - 10);
        setSpeed(4);
        animations = IMAGES;
    }

    protected void updateBehavior(int[][] collisionTile, Playing playing) {
        flipX = velocityX >= 0 ? 0 : width;
        flipW = velocityX >= 0 ? 1 : -1;
        if (checkPlayerHit(hitBox, playing.getPlayer())) {
            playing.getPlayer().takeDamage(25);
        }
        // Phase 1
        if (currentHealth > maxHealth * 0.3) {
            counter++;
            if (!found) {
                double centerYDifference = getHitBoxCenterY() - playing.getPlayer().getHitBoxCenterY();
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
                if (collisionRight || collisionLeft) {
                    velocityY = 0;
                    found = false;
                }
                if (counter % 40 == 0) {
                    shootProjectile(new BasicBullet(getHitBoxCenterX(),
                            getHitBoxCenterY(),
                            10,
                            0,
                            -1,
                            15));

                    shootProjectile(new BasicBullet(getHitBoxCenterX(),
                            getHitBoxCenterY(),
                            10,
                            0,
                            1,
                            15));
                } else if (counter % 20 == 0) {
                    shootProjectile(new BasicBullet(getHitBoxCenterX(),
                            getHitBoxCenterY(),
                            10,
                            flipW,
                            -0.5,
                            15));

                    shootProjectile(new BasicBullet(getHitBoxCenterX(),
                            getHitBoxCenterY(),
                            10,
                            flipW,
                            0.5,
                            15));
                }
            }

            if (counter % 30 == 0) {
                shootAtPlayer(playing, 10, 15);
            }

            if (counter % 240 == 0) {
                Playing.getEnemyManager().addTempEnemy(new GrandPrix(getHitBoxCenterX(), getHitBoxCenterY()));
                counter = 0;
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

        int x = directionX * 128 + playing.getPlayer().getHitBoxCenterX();
        int y = directionY * 128 + playing.getPlayer().getHitBoxCenterY();

        currentChargeTick = -CHARGE_COOLDOWN;
        super.teleport((int) (x - getHitBoxWidth() / 2), (int) (y - getHitBoxHeight() / 2));
    }

    public void draw(Graphics2D g2, int xOffset, int yOffset) {
        if (active) {
            drawHitBox(g2, xOffset, yOffset);
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
