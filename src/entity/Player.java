package entity;

import main.GamePanel;
import utils.HelpMethods;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static utils.Constants.PlayerConstants.*;

public class Player extends Entity {
    private BufferedImage[][] animations;
    private int[][] lvlData;
    private int aniTick, aniIndex, aniSpeed = 10;
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
    public final int screenX;
    public final int screenY;
    private double mouseX, mouseY;


    public Player() {
        this.hitBox = new Rectangle(15, 18, 20, 33);

        this.speed = 4;

        this.screenX = GamePanel.screenWidth / 2 - (GamePanel.tileSize / 2);
        this.screenY = GamePanel.screenHeight / 2 - (GamePanel.tileSize / 2);

        this.state = IDLE;

        setSpawn();
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

    public void loadAnimations() {
        try {
            animations = new BufferedImage[2][4];
            String[] spriteNames = {
                    "traveler_right_1",
                    "traveler_run_right_1",
                    "traveler_run_right_2",
                    "traveler_run_right_3"
            };

            animations[0][0] = initializePlayer(spriteNames[0]);
            for (int i = 1; i < spriteNames.length; i++)
                animations[1][i - 1] = initializePlayer(spriteNames[i]);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BufferedImage initializePlayer(String imagePath) throws IOException {

        String resourcePath = "/res/player/" + imagePath + ".png";
        InputStream resourceStream = getClass().getResourceAsStream(resourcePath);

        if (resourceStream == null) {
            throw new FileNotFoundException("Missing texture resource: " + resourcePath);
        }

        BufferedImage image = ImageIO.read(resourceStream);

        return HelpMethods.scaleImage(image, GamePanel.tileSize, GamePanel.tileSize);
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
        if (mouseX >= playerScreenPosX + (double) GamePanel.tileSize / 2) {
            flipW = 1;
            flipX = 0;
        } else {
            flipW = -1;
            flipX = 48;
        }
    }

    public void teleport() {
        int deltaX = (int) Math.round(mouseX - playerScreenPosX);
        int deltaY = (int) Math.round(mouseY - playerScreenPosY);

        worldX += deltaX;
        worldY += deltaY;
    };


    private void updatePos() {
        // checkTile(this);
        moving = false;
        velocityX = 0;
        velocityY = 0;
//        if ((!left && !right && !up && !down) || (right && left) || (up && down))
//            return;

        if (up && !collisionDirections.contains("TOP")) {
            System.out.println("TOP!");
            velocityY = -speed;
        }
        if (down && !collisionDirections.contains("BOTTOM")) {
            velocityY = speed;
        }
        if (left && !collisionDirections.contains("LEFT")) {
            velocityX = -speed;
        }
        if (right && !collisionDirections.contains("RIGHT")) {
            velocityX = speed;
        }


        if (dodgeActive && dodgeCooldown <= 0) {
            velocityY = (float) -(Math.sin(rotationAngleRad) * 15);
            velocityX = (float) -(Math.cos(rotationAngleRad) * 15);

            updateXPos(velocityX);
            updateYPos(velocityY);
            return;
        }
        updateXPos(velocityX);
        updateYPos(velocityY);
        collisionDirections.clear();
        moving = true;
    }


    private void updateXPos(float velocity) {
//        if(CanMoveHere()) {
        worldX += velocity;
//        }
    }

    private void updateYPos(float velocity) {
        worldY += velocity;
    }

    public void dodge() {
        if (dodgeActive)
            return;
        System.out.println("Dodged!");
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

        int screenWidth = GamePanel.screenWidth;
        int screenHeight = GamePanel.screenHeight;

        //GET WORLD WIDTH FROM LEVEL!! gp
        int worldWidth = 34*48;
        int worldHeight = 50*48;

        int maxRightOffset = worldWidth - worldX;
        playerScreenPosX = Math.max(playerScreenPosX, screenWidth - maxRightOffset);

        int maxBottomOffset = worldHeight - worldY;
        playerScreenPosY = Math.max(playerScreenPosY, screenHeight - maxBottomOffset);
    }


    public void render(Graphics2D g2) {

        g2.drawImage(animations[state][aniIndex], playerScreenPosX + flipX, playerScreenPosY, 48 * flipW, 48, null);

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
        g.drawRect(playerScreenPosX + hitBox.x, playerScreenPosY + hitBox.y, hitBox.width, hitBox.height);

    }

    public int getPlayerScreenPosX() {
        return playerScreenPosX;
    }

    public int getPlayerScreenPosY() {
        return playerScreenPosY;
    }

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }
}
