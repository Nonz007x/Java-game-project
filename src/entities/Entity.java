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
    protected int initialWorldX;
    protected int initialWorldY;
    protected int worldX, worldY;
    protected float velocityX, velocityY;
    protected int speed;

    protected BufferedImage[][] animations;
    protected int flipW = 1;
    protected int flipX = 0;
    protected int aniSpeed = 10;
    protected int aniTick, aniIndex;
    protected int state;

    protected Rectangle2D.Float hitBox;
    protected int hitBoxOffsetX, hitBoxOffsetY;
    protected boolean collisionUp;
    protected boolean collisionDown;
    protected boolean collisionLeft;
    protected boolean collisionRight;

    protected boolean hitBoxEnable = true;

    public Entity(int width, int height, int health) {
        this.width = width;
        this.height = height;
        this.maxHealth = health;
        this.currentHealth = health;
    }

    public Entity(int x, int y, int width, int height, int health) {
        this(width, height, health);
        this.initialWorldX = x;
        this.initialWorldY = y;
        this.worldX = x;
        this.worldY = y;
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

    public final int getCurrentHealth() {
        return currentHealth;
    }

    public final int getMaxHealth() {
        return maxHealth;
    }

    public final float getHealthPercentage() {
        return (float) currentHealth / maxHealth;
    }

    public final float getVelocityX() {
        return velocityX;
    }

    public final float getVelocityY() {
        return velocityY;
    }

    public final boolean isHitBoxEnabled() {
        return hitBoxEnable;
    }

    public final Rectangle2D getHitBox() {
        return hitBox;
    }
    public final int getHitBoxCenterX() {
        return (int) (hitBox.x + hitBox.width / 2);
    }

    public final int getHitBoxCenterY() {
        return (int) (hitBox.y + hitBox.height / 2);
    }

    public final float getHitBoxX() {
        return hitBox.x;
    }

    public final float getHitBoxY() {
        return hitBox.y;
    }

    public final float getHitBoxWidth() {
        return hitBox.width;
    }

    public final float getHitBoxHeight() {
        return hitBox.height;
    }

    public final void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setVelocityX(float velocityX) {
        this.velocityX = velocityX;
    }

    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }

    public final void setX(int x) {
        this.worldX = x;
    }

    public final void setY(int y) {
        this.worldY = y;
    }

    protected final void initHitBox(float x, float y, float width, float height) {
        hitBox = new Rectangle2D.Float(x + worldX, y + worldY, width, height);
        hitBoxOffsetX = (int) x;
        hitBoxOffsetY = (int) y;
    }

    public void takeDamage(int damage) {
        currentHealth -= damage;
    }

    public void shootProjectile(Projectile projectile) {
        ProjectileManager.addProjectile(projectile);
    }

    protected void newState(int state) {
        this.state = state;
        aniTick = 0;
        aniIndex = 0;
    }

    protected void updateXPos(float velocity) {
        worldX += (int) velocity;
        hitBox.x += (int) velocity;
    }

    protected void updateYPos(float velocity) {
        worldY += (int) velocity;
        hitBox.y += (int) velocity;
    }

    protected void teleport(int x, int y) {
        worldX = x;
        hitBox.x = x;
        worldY = y;
        hitBox.y = y;
    }

    protected void checkCollision(int[][] collisionTile) {
        collisionUp = CheckCollisionUp((int) hitBox.x, (int) (hitBox.y + velocityY), (int) hitBox.width, collisionTile);
        collisionDown = CheckCollisionDown((int) hitBox.x, (int) (hitBox.y + velocityY), (int) hitBox.width, (int) hitBox.height, collisionTile);
        collisionLeft = CheckCollisionLeft((int) (hitBox.x + velocityX), (int) hitBox.y, (int) hitBox.height, collisionTile);
        collisionRight = CheckCollisionRight((int) (hitBox.x + velocityX), (int) hitBox.y, (int) hitBox.width, (int) hitBox.height, collisionTile);
    }

    public void knockBack(double directionX, double directionY, int knockbackStrength, int[][] collisionTile) {
        float initialVelocityX = velocityX;
        float initialVelocityY = velocityY;
        velocityX = (float) (directionX * knockbackStrength);
        velocityY = (float) (directionY * knockbackStrength);
        checkCollision(collisionTile);
        if (collisionRight) {
            velocityX = 0;
        } else if (collisionLeft) {
            velocityX = 0;
        }

        if (collisionUp) {
            velocityY = 0;
        } else if (collisionDown) {
            velocityY = 0;
        }
        updateXPos(velocityX);
        updateYPos(velocityY);
        velocityX = initialVelocityX;
        velocityY = initialVelocityY;
    }

    public static BufferedImage[][] loadImages(BufferedImage atlas, int xSize, int ySize, int spriteW, int spriteH) {
        BufferedImage[][] tempArr = new BufferedImage[ySize][xSize];
        for (int j = 0; j < tempArr.length; j++)
            for (int i = 0; i < tempArr[0].length; i++) {
                tempArr[j][i] = atlas.getSubimage(i * spriteW, j * spriteH, spriteW, spriteH);
            }
        return tempArr;
    }

    public void drawHitBox(Graphics2D g2, int xOffset, int yOffset) {
        g2.setColor(Color.RED);
        g2.drawRect((int) hitBox.x + xOffset, (int) hitBox.y + yOffset, (int) hitBox.width, (int) hitBox.height);
    }
}
