package objects;

import gamestates.Playing;
import objects.projectiles.PlayerProjectile;

import java.awt.*;
import java.util.ArrayList;


public class ProjectileManager {

    private static Playing playing;
    private static final ArrayList<Projectile> projectiles = new ArrayList<>();

    public ProjectileManager(Playing playing) {
        ProjectileManager.playing = playing;
    }

    public void update(int[][] collisionTile) {
        for (Projectile p : projectiles) {
            if (!p.isActive() || !p.isWithinTickLimit()) {
                p.setActive(false);
                continue;
            }
            p.update(collisionTile, playing);
        }
    }

    public void draw(Graphics2D g, int xOffset, int yOffset) {
        for (Projectile p : projectiles)
            if (p.isActive())
                p.draw(g, xOffset, yOffset);
    }

    public static ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    public static void addProjectile(int x, int y, int speed, double directionX, double directionY) {
        projectiles.add(new Projectile(x, y, speed, directionX, directionY));
    }

    public static void addPlayerProjectile(int x, int y, int speed, double directionX, double directionY) {
        projectiles.add(new PlayerProjectile(x, y, speed, directionX, directionY));
    }
}
