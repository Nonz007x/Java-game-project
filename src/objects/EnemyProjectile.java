package objects;

import gamestates.Playing;

public class EnemyProjectile extends Projectile {
    public EnemyProjectile(int x, int y, int speed, double directionX, double directionY, int damage, int TICKS_TO_LIVE) {
        super(x, y, speed, directionX, directionY, damage, TICKS_TO_LIVE);
    }

    @Override
    public void update(int[][] collisionTile, Playing playing) {
        super.update(collisionTile, playing);
        if (checkPlayerHit(hitbox, playing.getPlayer())) {
            playing.getPlayer().takeDamage(damage);
            setActive(false);
        }
    }
}
