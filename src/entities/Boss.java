package entities;

import gamestates.Playing;
import objects.projectiles.BasicBullet;

public abstract class Boss extends Enemy {
    protected int counter = 0;

    public Boss(int x, int y, int width, int height, int health) {
        super(x, y, width, height, health);
    }

    protected void shootAtPlayer(Playing playing, int speed, int damage) {
        int startX = getHitBoxCenterX();
        int startY = getHitBoxCenterY();
        int playerX = playing.getPlayer().getHitBoxCenterX();
        int playerY = playing.getPlayer().getHitBoxCenterY();

        int deltaX = playerX - startX;
        int deltaY = playerY - startY;

        double distance = Math.max(0.000000001, Math.sqrt(deltaX * deltaX + deltaY * deltaY));

        double directionX = deltaX / distance;
        double directionY = deltaY / distance;

        shootProjectile(new BasicBullet(startX, startY, speed, directionX, directionY, damage));
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
        hitBox.x = worldX + hitBoxOffsetX;
        hitBox.y = worldY + hitBoxOffsetY;
        currentHealth = maxHealth;
        velocityX = 0;
        velocityY = 0;
        aniIndex = 0;
        aniTick = 0;
        counter = 0;
    }
}
