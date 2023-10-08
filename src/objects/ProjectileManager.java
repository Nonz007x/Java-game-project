package objects;

import gamestates.Playing;

import java.awt.*;
import java.util.ArrayList;


public class ProjectileManager {

    private static Playing playing;
    private static final ArrayList<Projectile> projectiles = new ArrayList<>();
    private static final ArrayList<Projectile> playerProjectiles = new ArrayList<>();


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

        for (Projectile p : playerProjectiles) {
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
        for (Projectile p : playerProjectiles)
            if (p.isActive())
                p.draw(g, xOffset, yOffset);
    }

    public static ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }
    public static ArrayList<Projectile> getPlayerProjectiles() {
        return playerProjectiles;
    }

    public static void addProjectile(Projectile projectile, boolean fromPlayer) {
        if (fromPlayer)
            playerProjectiles.add(projectile);
        else
            projectiles.add(projectile);
    }

    public void reset() {
        playerProjectiles.clear();
        projectiles.clear();

    }
}
