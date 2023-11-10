package entities;

import level.Level;
import gamestates.Playing;
import objects.ObjectManager;
import utils.Drawable;

import java.awt.*;
import java.util.ArrayList;

public class BossManager implements Drawable {
    private Level currentLevel;
    private static ArrayList<Boss> bosses = new ArrayList<>();
    private static final ArrayList<Boss> tempBosses = new ArrayList<>();
    private Playing playing;

    public BossManager(Playing playing) {
        this.playing = playing;
    }

    public void loadBosses(Level level) {
        this.currentLevel = level;
        bosses = currentLevel.getBosses();
        tempBosses.addAll(bosses);
    }

    public void update() {
        boolean anyBossActive = false;

        for (Boss boss : tempBosses) {
            if (boss.isActive()) {
                anyBossActive = true;
                boss.updateAnimationTick();
                boss.update(currentLevel.getCollisionTile(), playing);
            }
        }

        if (!anyBossActive) {
            ObjectManager.setGateActive(false);
        }
    }

    @Override
    public void draw(Graphics2D g, int xOffset, int yOffset) {
        for (Boss boss : tempBosses) {
            if (boss != null) {
                boss.draw(g, xOffset, yOffset);
//                boss.drawHitBox(g, xOffset, yOffset);
            }
        }
    }

    public ArrayList<Boss> getBosses() {
        return bosses;
    }

    @Override
    public int getY() {
        return 0;
    }

    public void addTempBoss(Boss e) {
        tempBosses.add(e);
    }

    public ArrayList<Boss> getTempBosses() {
        return tempBosses;
    }

    public void reset() {
        tempBosses.clear();
    }
}
