package objects;

import gamestates.Playing;
import level.LevelManager;
import level.Level;

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

    public void draw(Graphics2D g, int xOffset, int yOffset) {

    }
}
