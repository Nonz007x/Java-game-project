package entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

public abstract class Entity {
    public int prevX, prevY;
    public int worldX, worldY;
    public int velocityX, velocityY;
    public int speed;
    protected BufferedImage[] spriteArr;
    public String direction;
    protected int spriteCounter = 0;
    protected int spriteNum = 1;
    public Rectangle hitBox;
    public Set<String> collisionDirections = new HashSet<>();

    public void addCollisionDirection(String direction) {
       collisionDirections.add(direction);
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void updatePosition (int newX, int newY) {
        prevX = getX();
        prevY =  getY();

        worldX += newX;
        worldY += newY;
    }
    public int getX() {
        return worldX;
    }
    public int getY() {
        return worldY;
    }

    public void setX(int x) {
        this.worldX = x;
    }
    public void setY(int y) {
        this.worldY = y;
    }
    public int getDeltaX() {
        return getX() - prevX;
    }
    public int getDeltaY() {
        return getY() - prevY;
    }
}
