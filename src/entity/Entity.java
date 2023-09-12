package entity;

import java.awt.geom.Rectangle2D;

public abstract class Entity {
    private int prevX, prevY;
    protected int width, height;
    protected int worldX, worldY;
    protected float velocityX, velocityY;
    protected int speed;
    protected int state;
    protected Rectangle2D.Float hitbox;
    protected boolean collisionUp;
    protected boolean collisionDown;
    protected boolean collisionLeft;
    protected boolean collisionRight;

    public Entity(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Entity(int x, int y, int width, int height) {
        this.width = width;
        this.height = height;
        this.worldX = x;
        this.worldY = y;
    }

    public final void setSpeed(int speed) {
        this.speed = speed;
    }

    public final void updatePosition (int newX, int newY) {
        prevX = getX();
        prevY =  getY();

        worldX += newX;
        worldY += newY;
    }
    public final int getX() {
        return worldX;
    }
    public final int getY() {
        return worldY;
    }

    public final int getWidth() {
        return width;
    }

    public final int getHeight() {
        return height;
    }

    public final void setX(int x) {
        this.worldX = x;
    }
    public final void setY(int y) {
        this.worldY = y;
    }

    protected final void initHitbox(float x, float y, float width, float height) {
        hitbox = new Rectangle2D.Float(x, y, width, height);
    }

//    protected void drawHitbox(Graphics2D g) {
//        // For debugging the hitbox
//        g.setColor(Color.PINK);
//        g.drawRect((int) hitbox.x, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
//
//    }

}
