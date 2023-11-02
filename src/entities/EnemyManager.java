package entities;

import gamestates.Playing;
import level.Level;

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

    public void update(int[][] collisionTile) {
        for (Enemy e : enemies) {
            if (e.isActive()) {
                e.update(collisionTile, playing);
            }
        }
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public void addEnemy(Enemy e) {
        enemies.add(e);
    }
}
