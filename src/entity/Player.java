package entity;

import main.GamePanel;
import main.KeyHandler;
import main.MouseHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Player extends Entity {
    public int playerScreenPosX;
    public int playerScreenPosY;
    public KeyHandler keyH;
    GamePanel gp;
    MouseHandler mouseH;
    public final int screenX;
    public final int screenY;

    public Set<String> collisionDirections = new HashSet<>();

    public Player(GamePanel gp, KeyHandler keyH, MouseHandler mouseH) {
        this.gp = gp;
        this.keyH = keyH;
        this.mouseH = mouseH;

        hitBox = new Rectangle(15, 18, 24, 33);

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {

        worldX = 48;
        worldY = 48;
        baseSpeed = 4;
        speed = baseSpeed;
        facing = "right";
        direction = "right";
    }

    private void sprint() {
        speed = 8;
    }

    private void resetSpeed() {
        speed = baseSpeed;
    }
    public void getPlayerImage() {
        try {
            spriteArr = new BufferedImage[8];

            spriteArr[0] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/traveler_left_1.png")));
            spriteArr[1] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/traveler_run_left_1.png")));
            spriteArr[2] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/traveler_run_left_2.png")));
            spriteArr[3] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/traveler_run_left_3.png")));
            spriteArr[4] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/traveler_right_1.png")));
            spriteArr[5] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/traveler_run_right_1.png")));
            spriteArr[6] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/traveler_run_right_2.png")));
            spriteArr[7] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/traveler_run_right_3.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (mouseH.isClicked) {
            System.out.println("Mouse Clicked!");
        }
        if (mouseH.mouseX >= playerScreenPosX) {
            facing = "right";
        } else {
            facing = "left";
        }


        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            boolean ghosting = true;
            if (keyH.upPressed && !keyH.downPressed) {
                ghosting = false;
                collisionDirections.add("up");
                worldY -= speed;
            }
            if (keyH.downPressed && !keyH.upPressed) {
                ghosting = false;
                collisionDirections.add("down");
                worldY += speed;
            }
            if (keyH.leftPressed && !keyH.rightPressed) {
                ghosting = false;
                collisionDirections.add("left");
                worldX -= speed;
            }
            if (keyH.rightPressed && !keyH.leftPressed) {
                ghosting = false;
                collisionDirections.add("right");
                worldX += speed;
            }
            //CHECK COLLISION
            collisionOn = false;
            gp.collisionDetector.checkTile(this);

            if (collisionOn) {
                if (collisionDirections.contains("up")) {
                    worldY += speed;
                }
                if (collisionDirections.contains("down")) {
                    worldY -= speed;
                }
                if (collisionDirections.contains("left")) {
                    worldX += speed;
                }
                if (collisionDirections.contains("right")) {
                    worldX -= speed;
                }
            }

            collisionDirections.clear();

            if (keyH.shiftPressed) {
                sprint();
            } else {
                resetSpeed();
            }
            spriteCounter = keyH.shiftPressed ? spriteCounter + 2 : spriteCounter + 1;
            if(ghosting) {
                spriteNum = 1;
                spriteCounter = 10;
            }
            else if (spriteCounter > 10) {
                spriteNum = spriteNum > 3 ? 2 : spriteNum + 1;
                spriteCounter = 0;
            }
        } else {
            spriteNum = 1;
            spriteCounter = 10;
        }
    }

    public void render(Graphics2D g2) {

        BufferedImage image = null;
        switch (facing) {
            case "right" -> {
                if (spriteNum == 1) {
                    image = spriteArr[4];
                }
                else if (spriteNum == 2) {
                    image = spriteArr[5];
                }
                else if (spriteNum == 3) {
                    image = spriteArr[6];
                }
                else if (spriteNum == 4) {
                    image = spriteArr[7];
                }
            }
            case "left" -> {
                if (spriteNum == 1) {
                    image = spriteArr[0];
                }
                else if (spriteNum == 2) {
                    image = spriteArr[1];
                }
                else if (spriteNum == 3) {
                    image = spriteArr[2];
                }
                else if (spriteNum == 4) {
                    image = spriteArr[3];
                }
            }
            default -> {
            }
        }

        playerScreenPosX = screenX;
        playerScreenPosY = screenY;

        int worldWidth = gp.worldWidth;
        int worldHeight = gp.worldHeight;

        int screenWidth = gp.screenWidth;
        int screenHeight = gp.screenHeight;

        if (screenX > worldX) {
            playerScreenPosX = worldX;
        }
        if (screenY > worldY) {
            playerScreenPosY = worldY;
        }

        int rightOffset = screenWidth - screenX;
        if (rightOffset > worldWidth - worldX) {
            playerScreenPosX = screenWidth - (worldWidth - worldX);
        }

        int bottomOffset = screenHeight - screenY;
        if (bottomOffset > worldHeight - worldY) {
            playerScreenPosY = screenHeight - (worldHeight - worldY);
        }

        g2.drawImage(image, playerScreenPosX, playerScreenPosY, gp.tileSize, gp.tileSize, null);

        Graphics2D rotatedG2 = (Graphics2D) g2.create();
        rotatedG2.setColor(Color.GREEN);
        rotatedG2.translate(screenX + 30, screenY + 30);
        rotatedG2.rotate(mouseH.rotationAngleRad);
        rotatedG2.fillRect(-6, -6, 12, 12);
        rotatedG2.dispose();

        g2.setColor(Color.RED);
        g2.fillRect(mouseH.mouseX, mouseH.mouseY, 4, 4);
    }
}
