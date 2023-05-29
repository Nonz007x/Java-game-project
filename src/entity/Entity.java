package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    public int worldX, worldY;
    public int baseSpeed;
    public int speed;
    public BufferedImage[] spriteArr;
    public String direction;
    public String facing;

    protected int spriteCounter = 0;
    protected int spriteNum = 1;
    public Rectangle hitBox;
    public boolean collisionOn = false;

}
