package entities;

import gamestates.Playing;
import objects.ProjectileManager;
import objects.projectiles.BouncyBullet;

public abstract class Boss extends Enemy {
    protected int counter = 0;

    public Boss(int x, int y, int width, int height, int health) {
        super(x, y, width, height, health);
    }

    public void update(int[][] collisionTile, Playing playing) {
    }

    protected void aimAtPlayer(Playing playing) {
        int playerX = (int) playing.getPlayer().getHitbox().getX();
        int playerY = (int) playing.getPlayer().getHitbox().getY();

        int deltaX = playerX - worldX;
        int deltaY = playerY - worldY;

        double distance = Math.max(0.000000001, Math.sqrt(deltaX * deltaX + deltaY * deltaY));

        double directionX = deltaX / distance;
        double directionY = deltaY / distance;

        ProjectileManager.addProjectile(new BouncyBullet(worldX + 20, worldY + 20, 10, directionX, directionY, 15));
    }
}
