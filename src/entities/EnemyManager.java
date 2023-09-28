package entities;

import gamestates.Playing;
import Level.Level;

import java.util.ArrayList;

public class EnemyManager {

    private Playing playing;
    private Level currentLevel;
    private ArrayList<Enemy> enemies = new ArrayList<>();

    public EnemyManager(Playing playing) {
        this.playing = playing;
    }

    public void loadEnemies(Level level) {
        this.currentLevel = level;
        enemies = currentLevel.getEnemies();
    }

    public void update(int[][] collisionTile, Playing playing) {
        for (Enemy e : enemies) {
            e.update(collisionTile, playing);
        }
    }

    public ArrayList<Enemy> getDrawables() {
        return enemies;
    }
}
