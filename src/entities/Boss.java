package entities;

import objects.Projectile;
import objects.ProjectileManager;

public class Boss extends Entity {
    private int counter = 0;
    public Boss(int x, int y, int width, int height) {
        super(x, y, width, height);
        initHitbox(0, 0, width, height);
    }

    public void update() {
        counter++;
        if (counter > 60) {
            counter = 0;
            for (int i = 0; i < 20000; i++) {
                shootProjectile(220, 220, 1, -1, -1);
                shootProjectile(220, 220, 1, 1, 1);
                shootProjectile(220, 220, 1, 1, -1);
                shootProjectile(220, 220, 1, -1, 1);
            }
        }
    }

    public void shootProjectile(int x, int y, int speed, int directionX, int directionY) {
        ProjectileManager.addProjectile(x, y, speed, directionX, directionY);
    }

}
