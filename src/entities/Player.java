package entities;

import gamestates.Playing;
import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utils.Constants.PlayerConstants.*;
import static utils.HelpMethods.*;

public class Player extends Entity {
    private BufferedImage[][] animations;
    private BufferedImage boomstick;
    private int boomstickFlipY, boomstickFlipH;
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
        BufferedImage img = LoadSave.GetSprite(LoadSave.PLAYER_SPRITES);
        boomstick = LoadSave.GetSprite("boomstick.png");
        animations = new BufferedImage[2][4];

        for (int i = 0; i < animations.length; i++)
            for (int j = 0; j < animations[0].length; j++) {
                animations[i][j] = img.getSubimage(j * 16, i * 16, 16, 16);
            }
    }

    public void update() {

        updatePos();
        updateCamera();
        setAnimation();
        updateMouseEvent();
        updateAnimationTick();

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

    private void updateMouseEvent() {
        if (mouseLocation.x >= playerScreenPosX + (double) Game.TILE_SIZE / 2) {
            flipW = 1;
            flipX = 0;
            boomstickFlipH = 1;
            boomstickFlipY = 0;
        } else {
            flipW = -1;
            flipX = width;
            boomstickFlipH = -1;
            boomstickFlipY = boomstick.getHeight();
        }
    }

    public void teleport() {
        worldX = mouseLocation.x;
        worldY = mouseLocation.y;
        hitbox.x = mouseLocation.x + 15;
        hitbox.y = mouseLocation.y + 18;
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
            // velocity = direction * dodge speed (15)
            velocityY = (float) -(Math.sin(tempRadian) * 15);
            velocityX = (float) -(Math.cos(tempRadian) * 15);

            checkCollision();
            if (velocityX > 0 && collisionRight) {
                velocityX = 0;
            }
            if (velocityX < 0 && collisionLeft) {
                velocityX = 0;
            }
            if (velocityY < 0 && collisionUp) {
                velocityY = 0;
            }
            if (velocityY > 0 && collisionDown) {
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
        if (dodgeActive)
            return;
        if (dodgeCooldown < 0)
            dodgeActive = true;
    }

    private void setAnimation() {
        int startAni = state;

        if (moving && (velocityX != 0 || velocityY != 0))
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
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(state)) {
                aniIndex = 0;
            }

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


    public void draw(Graphics2D g2) {

        g2.drawImage(animations[state][aniIndex], playerScreenPosX + flipX, playerScreenPosY, width * flipW, height, null);
        drawWeapon(g2);

        g2.setColor(Color.RED);
        g2.fillRect(mouseLocation.x, mouseLocation.y, 4, 4);
        drawHitbox(g2);
    }

    private void drawWeapon(Graphics2D g2) {
        g2.translate(playerScreenPosX + 25, playerScreenPosY + 40);
        g2.rotate(rotationAngleRad);
        g2.drawImage(boomstick, -boomstick.getWidth() / 2, -boomstick.getHeight() / 2 + boomstickFlipY, 40, 16 * boomstickFlipH, null);
        g2.dispose();
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
