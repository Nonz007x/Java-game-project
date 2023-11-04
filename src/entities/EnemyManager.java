package entities;

import gamestates.Playing;
import level.Level;

import java.util.ArrayList;

public class EnemyManager {

    private Playing playing;
    private Level currentLevel;
    private static ArrayList<Enemy> enemies = new ArrayList<>();
    private static ArrayList<Enemy> tempEnemies = new ArrayList<>();

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
        for (Enemy e : tempEnemies) {
            if (e.isActive()) {
                e.update(collisionTile, playing);
            }
        }
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public ArrayList<Enemy> getTempEnemies() {
        return tempEnemies;
    }

    public void addEnemy(Enemy e) {
        enemies.add(e);
    }

    public void addTempEnemy(Enemy e) {
        tempEnemies.add(e);
    }
}
