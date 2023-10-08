package entities;

import gamestates.Playing;
import Level.Level;
import objects.Projectile;
import objects.ProjectileManager;

import java.util.ArrayList;

public class EnemyManager {

    private Playing playing;
    private Level currentLevel;
    private static ArrayList<Enemy> enemies = new ArrayList<>();

    public EnemyManager(Playing playing) {
        this.playing = playing;
    }

    public void loadEnemies(Level level) {
        this.currentLevel = level;
        enemies = currentLevel.getEnemies();
    }

    // TODO check enemy and projectile collision logic here
    public void update(int[][] collisionTile) {
        ArrayList<Projectile> playerProjectiles = ProjectileManager.getPlayerProjectiles();
        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            if (e.isActive()) {
                e.update(collisionTile, playing);
                for (int j = 0; j < playerProjectiles.size(); j++) {
                    Projectile projectile = playerProjectiles.get(j);
                    if (e.getHitbox().intersects(projectile.getHitbox())) {
                        e.takeDamage(20);
                        projectile.setActive(false);
                        playerProjectiles.remove(j);
                        break;
                    }
                }

                if (e.currentHealth <= 0) {
                    e.setActive(false);
                    enemies.remove(i);
                }
            }
        }
    }

    public ArrayList<Enemy> getDrawables() {
        return enemies;
    }

    public void addEnemy(Enemy e) {
        enemies.add(e);
    }
}
