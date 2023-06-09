package entity;

import main.GamePanel;
import main.KeyHandler;
import main.MouseHandler;
import main.UtilityTools;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Player extends Entity {
    public int playerScreenPosX;
    public int playerScreenPosY;
    public KeyHandler keyH;
    GamePanel gp;
    MouseHandler mouseH;
    public final int screenX;
    public final int screenY;


    public Player(GamePanel gp, KeyHandler keyH, MouseHandler mouseH) {
        this.gp = gp;
        this.keyH = keyH;
        this.mouseH = mouseH;

        hitBox = new Rectangle(15, 18, 20, 33);

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

            spriteArr[0] = initializePlayer("traveler_left_1");
            spriteArr[1] = initializePlayer("traveler_run_left_1");
            spriteArr[2] = initializePlayer("traveler_run_left_2");
            spriteArr[3] = initializePlayer("traveler_run_left_3");
            spriteArr[4] = initializePlayer("traveler_right_1");
            spriteArr[5] = initializePlayer("traveler_run_right_1");
            spriteArr[6] = initializePlayer("traveler_run_right_2");
            spriteArr[7] = initializePlayer("traveler_run_right_3");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BufferedImage initializePlayer(String imagePath) throws IOException {

        UtilityTools utilityTools = new UtilityTools();

        // Load the image resource
        String resourcePath = "/resources/player/" + imagePath + ".png";
        InputStream resourceStream = getClass().getResourceAsStream(resourcePath);

        if (resourceStream == null) {
            throw new FileNotFoundException("Missing texture resource: " + resourcePath);
        }

        BufferedImage image = ImageIO.read(resourceStream);

        // Scale the image

        return utilityTools.scaleImage(image, gp.tileSize, gp.tileSize);
    }

    public void update() {
        if (mouseH.isClicked) {
//            System.out.println("ScreenX: " + screenX + "ScreenY: " + playerScreenPosY);
            worldX += mouseH.mouseX - playerScreenPosX;
            worldY += mouseH.mouseY - playerScreenPosY;

            mouseH.isClicked = false;
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
                worldY -= speed;
            }
            if (keyH.downPressed && !keyH.upPressed) {
                ghosting = false;
                worldY += speed;
            }
            if (keyH.leftPressed && !keyH.rightPressed) {
                ghosting = false;
                worldX -= speed;
            }
            if (keyH.rightPressed && !keyH.leftPressed) {
                ghosting = false;
                worldX += speed;
            }
            //CHECK COLLISION

            gp.collisionDetector.checkTile(this);

            if (collisionDirections.contains("TOP")) {
                worldY += speed;
            }
            if (collisionDirections.contains("BOTTOM")) {
                worldY -= speed;
            }
            if (collisionDirections.contains("LEFT")) {
                worldX += speed;
            }
            if (collisionDirections.contains("RIGHT")) {
                worldX -= speed;
            }


            collisionDirections.clear();

            if (keyH.shiftPressed) {
                sprint();
            } else {
                resetSpeed();
            }
            spriteCounter = keyH.shiftPressed ? spriteCounter + 2 : spriteCounter + 1;
            if (ghosting) {
                spriteNum = 1;
                spriteCounter = 10;
            } else if (spriteCounter > 10) {
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
        Map<String, Integer> spriteMapping = new HashMap<>();
        spriteMapping.put("left_1", 0);
        spriteMapping.put("left_2", 1);
        spriteMapping.put("left_3", 2);
        spriteMapping.put("left_4", 3);
        spriteMapping.put("right_1", 4);
        spriteMapping.put("right_2", 5);
        spriteMapping.put("right_3", 6);
        spriteMapping.put("right_4", 7);

        String spriteKey = facing + "_" + spriteNum;

        if (spriteMapping.containsKey(spriteKey)) {
            int spriteIndex = spriteMapping.get(spriteKey);
            image = spriteArr[spriteIndex];
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

        g2.drawImage(image, playerScreenPosX, playerScreenPosY, null);

//        Graphics2D rotatedG2 = (Graphics2D) g2.create();
//        rotatedG2.setColor(Color.GREEN);
//        rotatedG2.translate(screenX + 30, screenY + 30);
//        rotatedG2.rotate(mouseH.rotationAngleRad);
//        rotatedG2.fillRect(-6, -6, 12, 12);
//        rotatedG2.dispose();

        g2.setColor(Color.RED);
        g2.fillRect(mouseH.mouseX, mouseH.mouseY, 4, 4);

    }
}
