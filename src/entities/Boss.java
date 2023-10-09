package entities;

import gamestates.Playing;
import objects.projectiles.BouncyBullet;


public abstract class Boss extends Enemy {
    protected int counter = 0;

    public Boss(int x, int y, int width, int height, int health) {
        super(x, y, width, height, health);
    }

    public void update(int[][] collisionTile, Playing playing) {
    }

    protected void aimAtPlayer(Playing playing) {
        int playerX = playing.getPlayer().getHitboxCenterX();
        int playerY = playing.getPlayer().getHitboxCenterY();

        int deltaX = playerX - 1;
        int deltaY = playerY - 1;

        double distance = Math.max(0.000000001, Math.sqrt(deltaX * deltaX + deltaY * deltaY));

        double directionX = deltaX / distance;
        double directionY = deltaY / distance;

        shootProjectile(new BouncyBullet(1, 1, 10, directionX, directionY, 15));
    }

    protected double[] aimAtPos(int startX, int startY, int targetX, int targetY) {;

        int deltaX = targetX - startX;
        int deltaY = targetY - startY;

        double distance = Math.max(0.000000001, Math.sqrt(deltaX * deltaX + deltaY * deltaY));

        double directionX = deltaX / distance;
        double directionY = deltaY / distance;
        return new double[]{directionX, directionY};
    }
}
