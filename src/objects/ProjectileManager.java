package objects;

import gamestates.Playing;

import java.awt.*;
import java.util.ArrayList;

import static utils.HelpMethods.IsProjectileHittingLevel;

public class ProjectileManager {

    private static Playing playing;
    private static ArrayList<Projectile> projectiles = new ArrayList<>();

    public ProjectileManager(Playing playing) {
        ProjectileManager.playing = playing;
    }

    public void update(int[][] collisionTile) {
        for (Projectile p : projectiles) {
            if (!p.isActive() || !p.isWithinTickLimit()) {
                p.setActive(false);
                continue;
            }

            p.updatePos();
            if (p.getHitbox().intersects(playing.getPlayer().getHitbox()) || IsProjectileHittingLevel(p, collisionTile)) {
                p.setActive(false);
            }
        }
    }

    public void draw(Graphics g, int xOffset, int yOffset) {
        for (Projectile p : projectiles)
            if (p.isActive())
                g.fillRect((int) p.getHitbox().x + xOffset, (int) p.getHitbox().y + yOffset, 10, 10);
    }

    public static ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    public static void addProjectile(int x, int y, int speed, double directionX, double directionY) {
        projectiles.add(new Projectile(x, y, speed, directionX, directionY));
    }
}
