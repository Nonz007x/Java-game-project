package entities;

import gamestates.Playing;

public abstract class Boss extends Enemy{
    protected int counter = 0;

    public Boss(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public void update(int[][] collisionTile, Playing playing) {
        checkCollision(collisionTile);
        if (velocityX > 0 && collisionRight) {
            velocityX = 0;
        }
        if (velocityX < 0 && collisionLeft) {
            velocityX = 0;
        }
        if (velocityY < 0 && collisionUp) {
            velocityY = 0;
        }
        if (velocityY > 0 && collisionDown) {
            velocityY = 0;
        }
        updateXPos(velocityX);
        updateYPos(velocityY);
        counter++;
        if (counter % 60 == 0) {
            aimAtPlayer(playing);
        }
    }

    protected void aimAtPlayer(Playing playing) {
        int playerX = (int) playing.getPlayer().getHitbox().getX();
        int playerY = (int) playing.getPlayer().getHitbox().getY();

        int deltaX = playerX - worldX;
        int deltaY = playerY - worldY;

        double distance = Math.max(0.000000001, Math.sqrt(deltaX * deltaX + deltaY * deltaY));

        double directionX = deltaX / distance;
        double directionY = deltaY / distance;

        shootProjectile(worldX + 20, worldY + 20, 10, directionX, directionY);
    }
}
