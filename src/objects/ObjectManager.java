package objects;

import entities.Player;
import gamestates.Playing;
import Level.LevelManager;
import Level.Level;

import java.awt.*;

public class ObjectManager {
    private Playing playing;
    private Level currentLevel;

    public ObjectManager(Playing playing) {
        this.playing = playing;
        currentLevel = LevelManager.GetCurrentLevel();
//            loadImgs();
    }

    public void update(int[][] collisionTile) {
//        updateProjectiles(collisionTile, player);
    }

    public void draw(Graphics2D g) {

    }
}
