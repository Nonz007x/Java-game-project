package objects;

import gamestates.Playing;

import java.awt.*;
import java.util.ArrayList;

import static utils.HelpMethods.IsProjectileHittingLevel;

public class ProjectileManager {

    private static Playing playing;
    private static ArrayList<Projectile> projectiles = new ArrayList<>();

    public ProjectileManager(Playing playing) {
        this.playing = playing;
    }

    public void update(int[][] collisionTile) {
        for (Projectile p : projectiles) {
            if (p.isActive()) {
                p.updatePos();
                if (p.getHitbox().intersects(playing.getPlayer().getHitbox())) {
                    p.setActive(false);
                } else if (IsProjectileHittingLevel(p, collisionTile)) {
                    p.setActive(false);
                }
            }
        }
    }

    public void draw(Graphics g) {
        for (Projectile p : projectiles)
            if (p.isActive())
                g.fillRect((int) p.getHitbox().x, (int) p.getHitbox().y, 10, 10);
    }

    public static ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    public static void addProjectile(int x, int y, int speed, int directionX, int directionY) {
        projectiles.add(new Projectile(x, y, speed, directionX, directionY));
    }
}
