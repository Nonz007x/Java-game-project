package entities;

import gamestates.Playing;
import Level.Level;

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

    public void update(int[][] collisionTile, Playing playing) {
        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            if (e.isActive()) {
                e.update(collisionTile, playing);
                if(e.getHitbox().intersects(playing.getPlayer().hitbox)) {
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
