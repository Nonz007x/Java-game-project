package objects;

import entities.Player;
import gamestates.Playing;
import level.LevelManager;
import level.Level;
import objects.gameobjects.Gate;

import java.awt.*;
import java.util.ArrayList;

public class ObjectManager {
    private Playing playing;
    private Level currentLevel;

    private final ArrayList<Gate> gates = new ArrayList<>();

    public ObjectManager(Playing playing) {
        this.playing = playing;
        currentLevel = LevelManager.getCurrentLevel();
//            loadImgs();
    }

    public void update() {
        checkGate(Playing.getPlayer());
    }

    public void checkGate(Player player) {
        for (Gate gate : gates) {
            if (gate.isActive)
                if (gate.isCollidedWithPlayer(player)) {
                    gate.movePlayer();
                }
        }
    }

    public void draw(Graphics2D g, int xOffset, int yOffset) {

    }

    public void addGate(Gate gate) {
        gates.add(gate);
    }
}
