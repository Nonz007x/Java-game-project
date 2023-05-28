package Entity;

import Main.GamePanel;
import Main.KeyHandler;
import Main.MouseHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;
    MouseHandler mouseH;
    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH, MouseHandler mouseH) {

        this.gp = gp;
        this.keyH = keyH;
        this.mouseH = mouseH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {

        worldX = gp.tileSize * -1;
        worldY = (int) (gp.tileSize * 5.5);
        defaultSpeed = 4;
        speed = defaultSpeed;
        direction = "right";
    }

    public void getPlayerImage() {
        try {
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/traveler_left_1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/traveler_run_left_1.png")));
            left3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/traveler_run_left_2.png")));
            left4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/traveler_run_left_3.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/traveler_right_1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/traveler_run_right_1.png")));
            right3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/traveler_run_right_2.png")));
            right4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/traveler_run_right_3.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (mouseH.isClicked) {
            System.out.println("Mouse Clicked!");
        }
        if (mouseH.mouseX >= gp.screenWidth / 2) {
            direction = "right";
        } else {
            direction = "left";
        }

        if (keyH.upPressed) {
            worldY -= speed;
        }
        if (keyH.downPressed) {
            worldY += speed;
        }
        if (keyH.leftPressed) {
            worldX -= speed;
        }
        if (keyH.rightPressed) {
            worldX += speed;
        }
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            int sprintSpeed = 8;
            speed = keyH.shiftPressed ? sprintSpeed : defaultSpeed;
            spriteCounter += keyH.shiftPressed ? 2 : 1;

            if (spriteCounter > 10) {
                spriteNum = spriteNum > 3 ? 2 : ++spriteNum;
                spriteCounter = 0;
            }
        } else {
            spriteNum = 1;
            spriteCounter = 10;
        }
    }

    public void render(Graphics2D g2) {

        BufferedImage image = null;
        switch (direction) {
            case "right" -> {
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
                if (spriteNum == 3) {
                    image = right3;
                }
                if (spriteNum == 4) {
                    image = right4;
                }
            }
            case "left" -> {
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
                if (spriteNum == 3) {
                    image = left3;
                }
                if (spriteNum == 4) {
                    image = left4;
                }
            }
            default -> {
            }
        }

        int x = screenX;
        int y = screenY;

        int worldWidth = gp.worldWidth;
        int worldHeight = gp.worldHeight;

        int screenWidth = gp.screenWidth;
        int screenHeight = gp.screenHeight;

        if (screenX > worldX) {
            x = worldX;
        }
        if (screenY > worldY) {
            y = worldY;
        }

        int rightOffset = screenWidth - screenX;
        if (rightOffset > worldWidth - worldX) {
            x = screenWidth - (worldWidth - worldX);
        }

        int bottomOffset = screenHeight - screenY;
        if (bottomOffset > worldHeight - worldY) {
            y = screenHeight - (worldHeight - worldY);
        }
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);

//        g2.setColor(Color.WHITE);
//        g2.fillRect(x, y, gp.tileSize, gp.tileSize);

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
