package entities;

import objects.Projectile;
import objects.ProjectileManager;
import utils.Drawable;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static utils.HelpMethods.*;
import static utils.HelpMethods.CheckCollisionRight;

public abstract class Entity implements Drawable {
    protected int maxHealth;
    protected int currentHealth;
    protected int width, height;
    protected int worldX, worldY;
    protected float velocityX, velocityY;
    protected int speed;

    protected BufferedImage[][] animations;
    protected int flipW = 1;
    protected int flipX = 0;
    protected final int aniSpeed = 10;
    protected int aniTick, aniIndex;
    protected int state;

    protected Rectangle2D.Float hitbox;
    protected boolean collisionUp;
    protected boolean collisionDown;
    protected boolean collisionLeft;
    protected boolean collisionRight;

    public Entity(int width, int height, int health) {
        this.width = width;
        this.height = height;
        this.maxHealth = health;
        this.currentHealth = health;
    }

    public Entity(int x, int y, int width, int height, int health) {
        this(width, height, health);
        this.worldX = x;
        this.worldY = y;
    }
    public final void setSpeed(int speed) {
        this.speed = speed;
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
        hitbox = new Rectangle2D.Float(x + worldX, y + worldY, width, height);
    }

    protected final void initHitbox() {
        hitbox = new Rectangle2D.Float(worldX, worldY, width, height);
    }

    public int getState() {
        return state;
    }

    protected void newState(int state) {
        this.state = state;
        aniTick = 0;
        aniIndex = 0;
    }

    protected void updateXPos(float velocity) {
        worldX += (int) velocity;
        hitbox.x += (int) velocity;
    }

    protected void updateYPos(float velocity) {
        worldY += (int) velocity;
        hitbox.y += (int) velocity;
    }

    public void takeDamage(int damage) {
        currentHealth -= damage;
    }

    public void shootProjectile(Projectile projectile) {
        ProjectileManager.addProjectile(projectile);
    }

    protected void checkCollision(int[][] collisionTile) {
        collisionUp = CheckCollisionUp((int) hitbox.x, (int) (hitbox.y + velocityY), (int) hitbox.width, collisionTile);
        collisionDown = CheckCollisionDown((int) hitbox.x, (int) (hitbox.y + velocityY), (int) hitbox.width, (int) hitbox.height, collisionTile);
        collisionLeft = CheckCollisionLeft((int) (hitbox.x + velocityX), (int) hitbox.y, (int) hitbox.height, collisionTile);
        collisionRight = CheckCollisionRight((int) (hitbox.x + velocityX), (int) hitbox.y, (int) hitbox.width, (int) hitbox.height, collisionTile);
    }

    public static BufferedImage[][] loadImages(BufferedImage atlas, int xSize, int ySize, int spriteW, int spriteH) {
        BufferedImage[][] tempArr = new BufferedImage[ySize][xSize];
        for (int j = 0; j < tempArr.length; j++)
            for (int i = 0; i < tempArr[0].length; i++) {
                tempArr[j][i] = atlas.getSubimage(i * spriteW, j * spriteH, spriteW, spriteH);
            }
        return tempArr;
    }

    public void drawHitbox(Graphics2D g2, int xOffset, int yOffset) {
        // For debugging the hitbox
        g2.setColor(Color.PINK);
        g2.drawRect((int) hitbox.x + xOffset, (int) hitbox.y + yOffset, (int) hitbox.width, (int) hitbox.height);

    }

    public Rectangle2D getHitbox() {
        return hitbox;
    }

    public float getHitboxX() {
        return hitbox.x;
    }

    public float getHitboxY() {
        return hitbox.y;
    }

    public float getHitboxWidth() {
        return hitbox.width;
    }

    public float getHitboxHeight() {
        return hitbox.height;
    }
}
