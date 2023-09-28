package entities;

import Level.Level;
import gamestates.Playing;
import utils.Drawable;

import java.awt.*;
import java.util.ArrayList;

public class BossManager implements Drawable {
    private Level currentLevel;
    private ArrayList<Boss> bosses = new ArrayList<>();
    private Playing playing;

    public BossManager(Playing playing) {
        this.playing = playing;
    }

    public void loadBosses(Level level) {
        this.currentLevel = level;
        bosses = currentLevel.getBosses();
    }

    public void update() {
        for (Boss boss : bosses) {
            boss.update(currentLevel.getCollisionTile(), playing);
            boss.updateAnimationTick();
        }
    }

    public void draw(Graphics2D g, int xOffset, int yOffset) {
        for (Boss boss : bosses) {
            if (boss != null) {
                boss.draw(g, xOffset, yOffset);
                boss.drawHitbox(g, xOffset, yOffset);
            }
        }
    }
}
