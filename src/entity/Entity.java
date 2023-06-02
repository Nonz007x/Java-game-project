package entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

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
    public Set<String> collisionDirections = new HashSet<>();

    public void addCollisionDirection(String direction) {
       collisionDirections.add(direction);
    }

}
