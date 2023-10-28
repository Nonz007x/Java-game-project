package entities;

import gamestates.Playing;
import objects.projectiles.BouncyBullet;


public abstract class Boss extends Enemy {
    protected int counter = 0;

    public Boss(int x, int y, int width, int height, int health) {
        super(x, y, width, height, health);
    }

    protected void shootAtPlayer(Playing playing, int speed, int damage) {
        int startX = getHitboxCenterX();
        int startY = getHitboxCenterY();
        int playerX = playing.getPlayer().getHitboxCenterX();
        int playerY = playing.getPlayer().getHitboxCenterY();

        int deltaX = playerX - startX;
        int deltaY = playerY - startY;

        double distance = Math.max(0.000000001, Math.sqrt(deltaX * deltaX + deltaY * deltaY));

        double directionX = deltaX / distance;
        double directionY = deltaY / distance;

        shootProjectile(new BouncyBullet(startX, startY, speed, directionX, directionY, damage));
    }

    protected double[] aimAtPos(int startX, int startY, int targetX, int targetY) {;

        int deltaX = targetX - startX;
        int deltaY = targetY - startY;

        double distance = Math.max(0.000000001, Math.sqrt(deltaX * deltaX + deltaY * deltaY));

        double directionX = deltaX / distance;
        double directionY = deltaY / distance;
        return new double[]{directionX, directionY};
    }

    @Override
    public void resetEnemy() {
        active = true;
        worldX = initialWorldX;
        worldY = initialWorldY;
        hitbox.x = worldX + hitboxOffsetX;
        hitbox.y = worldY + hitboxOffsetY;
        currentHealth = maxHealth;
        velocityX = 0;
        velocityY = 0;
        aniIndex = 0;
        aniTick = 0;
        counter = 0;
    }
}
