package entities;

import gamestates.Playing;
import main.Game;
import objects.ProjectileManager;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import static utils.Constants.PlayerConstants.*;
import static utils.HelpMethods.*;

public class Player extends Entity {

    private BufferedImage[][] gunFlashes;
    private BufferedImage boomstick;
    private int gunFlashAniIndex;
    private int boomstickFlipY, boomstickFlipH;
    private int sgFlashFlipY, sgFlashFlipH;
    private int gunFlashTick;
    private boolean shooting;
    private int[][] lvlData;
    private int playerScreenPosX;
    private int playerScreenPosY;
    private boolean moving = false;
    private boolean left, right, up, down;
    private boolean dodgeActive;
    private int dodgeTick;
    private int dodgeCooldown;

    private double rotationAngleRad;
    private double tempRadian;
    private final int screenX = Game.GAME_WIDTH / 2 - (Game.TILE_SIZE / 2);
    private final int screenY = Game.GAME_HEIGHT / 2 - (Game.TILE_SIZE / 2);
    private Point mouseLocation = new Point(0, 0);

    public Player(int width, int height, Playing playing) {
        super(width, height);
        this.speed = 4;
        this.state = IDLE;

        setSpawn();
        initHitbox(15, 18, 20, 33);
        loadAnimations();
    }

    public void setSpawn() {
        this.worldX = 96;
        this.worldY = 96;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void updateMousePosition(MouseEvent e) {
        mouseLocation.x = e.getX();
        mouseLocation.y = e.getY();
        rotationAngleRad = Math.atan2(mouseLocation.y - playerScreenPosY - 24, mouseLocation.x - playerScreenPosX - 24);
    }

    public void calculateRad() {
        tempRadian = rotationAngleRad;
    }

    public void loadAnimations() {
        BufferedImage playerSprites = LoadSave.GetSprite(LoadSave.PLAYER_SPRITES);
        BufferedImage shotgunFlashSprites = LoadSave.GetSprite("shotgun_flashes.png");

        boomstick = LoadSave.GetSprite("boomstick.png");
        gunFlashes = loadImages(shotgunFlashSprites, 7, 1, 16, 16);
        animations = loadImages(playerSprites, 4, 2, 16, 16);
    }

    public void update() {
        updatePos();
        updateCamera();
        setAnimation();
        updateMouseEvent();
        updateAnimationTick();
        updataDodge();
    }

    private void updateMouseEvent() {
        final int halfTileSize = Game.TILE_SIZE / 2;

        flipW = (mouseLocation.x >= playerScreenPosX + halfTileSize) ? 1 : -1;
        flipX = (flipW == 1) ? 0 : width;
        boomstickFlipH = (flipW == 1) ? 1 : -1;
        boomstickFlipY = (flipW == 1) ? 0 : 16;
        sgFlashFlipH = (flipW == 1) ? 1 : -1;
        sgFlashFlipY = (flipW == 1) ? 0 : 58;
    }

    public void teleport() {
        worldX += mouseLocation.x - playerScreenPosX;
        worldY += mouseLocation.y - playerScreenPosY;
        hitbox.x += mouseLocation.x - playerScreenPosX;
        hitbox.y += mouseLocation.y - playerScreenPosY;
    }

    private void updatePos() {
        moving = false;
        velocityX = 0;
        velocityY = 0;

        checkCollision();

        if (up && !down && !collisionUp) velocityY = -speed;
        if (down && !up && !collisionDown) velocityY = speed;
        if (left && !right && !collisionLeft) velocityX = -speed;
        if (right && !left && !collisionRight) velocityX = speed;

        if (dodgeActive) {
            // velocity = direction * 15 (dodge speed)
            velocityY = (float) -(Math.sin(tempRadian) * 15);
            velocityX = (float) -(Math.cos(tempRadian) * 15);

            checkCollision();
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

            updateXPos(velocityX);
            updateYPos(velocityY);
            return;
        }
        updateXPos(velocityX);
        updateYPos(velocityY);
        moving = true;
    }

    private void checkCollision() {
        collisionUp = CheckCollisionUp((int) hitbox.x, (int) (hitbox.y + velocityY), (int) hitbox.width, lvlData);
        collisionDown = CheckCollisionDown((int) hitbox.x, (int) (hitbox.y + velocityY), (int) hitbox.width, (int) hitbox.height, lvlData);
        collisionLeft = CheckCollisionLeft((int) (hitbox.x + velocityX), (int) hitbox.y, (int) hitbox.height, lvlData);
        collisionRight = CheckCollisionRight((int) (hitbox.x + velocityX), (int) hitbox.y, (int) hitbox.width, (int) hitbox.height, lvlData);
    }

    public void dodge() {
        if (!dodgeActive && dodgeCooldown < 0)
            dodgeActive = true;
    }

    private void updataDodge() {
        if (dodgeActive) {
            dodgeTick++;
            if (dodgeTick >= 6) {
                dodgeTick = 0;
                dodgeCooldown = 45;
                dodgeActive = false;
            }
        }
        dodgeCooldown--;
    }

    public void shoot() {
        if (shooting)
            return;
        shooting = true;
        float directionX = (float) Math.cos(rotationAngleRad);
        float directionY = (float) Math.sin(rotationAngleRad);

        float centerX = hitbox.x + hitbox.width / 2;
        float centerY = hitbox.y + hitbox.height / 2;

        float projectileX = centerX + directionX * 10;
        float projectileY = centerY + directionY * 10;

        ProjectileManager.addPlayerProjectile((int) projectileX, (int) projectileY, 10, directionX, directionY);
    }


    private void setAnimation() {
        int startAni = state;

        if (velocityX != 0 || velocityY != 0)
            state = RUNNING;
        else
            state = IDLE;

        if (startAni != state)
            resetAniTick();
    }

    private void resetAniTick() {
        aniTick = 0;
        aniIndex = 0;
    }

    private void updateAnimationTick() {
        aniTick++;
        if (shooting) {
            gunFlashTick++;
            if (gunFlashTick >= 5) {
                gunFlashTick = 0;
                gunFlashAniIndex = (gunFlashAniIndex + 1) % 7;

                if (gunFlashAniIndex == 0) {
                    shooting = false;
                }
            }
        }
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex = (aniIndex + 1) % GetSpriteAmount(state);
        }
    }

    public void loadLvlData(int[][] lvlData) {
        this.lvlData = lvlData;
    }

    private void updateCamera() {
//        System.out.println(lvlData.length);
        playerScreenPosX = Math.min(screenX, worldX);
        playerScreenPosY = Math.min(screenY, worldY);

        int screenWidth = Game.GAME_WIDTH;
        int screenHeight = Game.GAME_HEIGHT;

        int worldWidth = lvlData[0].length * 48;
        int worldHeight = lvlData.length * 48;

        int maxRightOffset = worldWidth - worldX;
        playerScreenPosX = Math.max(playerScreenPosX, screenWidth - maxRightOffset);

        int maxBottomOffset = worldHeight - worldY;
        playerScreenPosY = Math.max(playerScreenPosY, screenHeight - maxBottomOffset);
    }


    public void draw(Graphics2D g2, int xOffset, int yOffset) {

        g2.drawImage(animations[state][aniIndex], playerScreenPosX + flipX, playerScreenPosY, width * flipW, height, null);
        drawWeapon(g2);
        g2.setColor(Color.RED);
        g2.fillRect(mouseLocation.x, mouseLocation.y, 4, 4);
        drawHitbox(g2);

    }

    public void drawHitbox(Graphics2D g) {
        g.setColor(Color.RED);
        g.drawRect(playerScreenPosX + 15, playerScreenPosY + 18, (int) hitbox.width, (int) hitbox.height);
    }

    private void drawWeapon(Graphics2D g2) {

        AffineTransform originalTransform = g2.getTransform();

        g2.translate(playerScreenPosX + 25, playerScreenPosY + 40);
        g2.rotate(rotationAngleRad);
        g2.drawImage(boomstick, -20, -8 + boomstickFlipY, 40, 16 * boomstickFlipH, null);
        if (shooting) {
            g2.drawImage(gunFlashes[0][gunFlashAniIndex], 20, sgFlashFlipY - 29, 48, 48 * sgFlashFlipH, null);
        }

        g2.setTransform(originalTransform);

    }

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public int getPlayerScreenPosX() {
        return playerScreenPosX;
    }

    public int getPlayerScreenPosY() {
        return playerScreenPosY;
    }
}
