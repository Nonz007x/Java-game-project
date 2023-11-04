package objects;

import gamestates.Playing;

import java.awt.*;
import java.util.ArrayList;


public class ProjectileManager {

    private static Playing playing;
    private static final ArrayList<Projectile> projectiles = new ArrayList<>();


    public ProjectileManager(Playing playing) {
        ProjectileManager.playing = playing;
    }

    public void update(int[][] collisionTile) {
        for (int i = 0; i < projectiles.size(); i++) {
            Projectile p = projectiles.get(i);
            if (!p.isActive() || !p.isWithinTickLimit()) {
                p.setActive(false);
                continue;
            }
            p.update(collisionTile, playing);
        }
    }

    public void draw(Graphics2D g, int xOffset, int yOffset) {
        for (int i = 0; i < projectiles.size(); i++) {
            Projectile p = projectiles.get(i);
            if (p.isActive()) {
                p.draw(g, xOffset, yOffset);
            }
        }
    }

    public static ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    public static void addProjectile(Projectile projectile) {;
        projectiles.add(projectile);
    }

    public void reset() {
        projectiles.clear();
    }
}
