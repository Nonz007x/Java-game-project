package entity;

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
    private int[][] lvlData;
    private int aniTick, aniIndex;
    private final int aniSpeed = 10;
    private int flipW = 1;
    private int flipX = 0;
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
    private double mouseX, mouseY;


    public Player(int width, int height, Playing playing) {
        super(width, height);
        this.speed = 4;
        this.state = IDLE;

        setSpawn();
        initHitbox(15, 18, 20, 33);
        System.out.println(worldX);
        System.out.println(worldY);
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
        mouseX = e.getX();
        mouseY = e.getY();
        rotationAngleRad = Math.atan2(mouseY - playerScreenPosY - 24, mouseX - playerScreenPosX - 24);
    }

    public void calculateRad() {
        tempRadian = rotationAngleRad;
    }

    public void loadAnimations() {
        BufferedImage img = LoadSave.GetSprite(LoadSave.PLAYER_SPRITES);
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
            if (dodgeTick >= 21) {
                dodgeTick = 0;
                dodgeActive = false;
            }
        }
        dodgeCooldown--;
    }

    private void updateMouseEvent() {
        if (mouseX >= playerScreenPosX + (double) Game.TILE_SIZE / 2) {
            flipW = 1;
            flipX = 0;
        } else {
            flipW = -1;
            flipX = width;
        }
    }

    public void teleport() {
        int deltaX = (int) Math.round(mouseX - playerScreenPosX);
        int deltaY = (int) Math.round(mouseY - playerScreenPosY);

        worldX += deltaX;
        worldY += deltaY;
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

        if (dodgeActive && dodgeCooldown <= 0) {
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
        collisionUp = CheckCollisionUp(worldX, (int) (worldY + velocityY), (int) hitbox.width, (int) hitbox.x, (int) hitbox.y, lvlData);
        collisionDown = CheckCollisionDown(worldX, (int) (worldY + velocityY), (int) hitbox.width, (int) hitbox.height, (int) hitbox.x, (int) hitbox.y, lvlData);
        collisionLeft = CheckCollisionLeft((int) (worldX + velocityX), worldY, (int) hitbox.height, (int) hitbox.x, (int) hitbox.y, lvlData);
        collisionRight = CheckCollisionRight((int) (worldX + velocityX), worldY, (int) hitbox.width, (int) hitbox.height, (int) hitbox.x, (int) hitbox.y, lvlData);
    }

    private void updateXPos(float velocity) {
        worldX += velocity;
    }

    private void updateYPos(float velocity) {
        worldY += velocity;
    }

    public void dodge() {
        if (dodgeActive)
            return;

        dodgeActive = true;
        dodgeCooldown = 15;
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


    public void render(Graphics2D g2) {

        g2.drawImage(animations[state][aniIndex], playerScreenPosX + flipX, playerScreenPosY, width * flipW, height, null);

        Graphics2D rotatedG2 = (Graphics2D) g2.create();
        rotatedG2.setColor(Color.GREEN);
        rotatedG2.translate(playerScreenPosX + 30, playerScreenPosY + 30);
        rotatedG2.rotate(rotationAngleRad);
        rotatedG2.fillRect(0, 0, 12, 12);
        rotatedG2.dispose();

        g2.setColor(Color.RED);
        g2.fillRect((int) Math.round(mouseX), (int) Math.round(mouseY), 4, 4);
        drawHitbox(g2);

    }

    private void drawHitbox(Graphics2D g) {
        g.setColor(Color.RED);
        g.drawRect(playerScreenPosX + (int) hitbox.x, playerScreenPosY + (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
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
