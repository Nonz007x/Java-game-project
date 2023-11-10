package objects;

import entities.Player;
import gamestates.Playing;
import level.LevelManager;
import level.Level;
import objects.gameobjects.Fragment;
import objects.gameobjects.Gate;
import objects.gameobjects.Potion;

import java.awt.*;
import java.util.ArrayList;

public class ObjectManager {
    private Playing playing;
    private Level currentLevel;

    private static final ArrayList<Gate> gates = new ArrayList<>();
    private static final ArrayList<Potion> potions = new ArrayList<>();
    private static final Fragment fragment = new Fragment();

    public ObjectManager(Playing playing) {
        this.playing = playing;
        currentLevel = LevelManager.getCurrentLevel();
    }

    public void update() {
        checkCollide(Playing.getPlayer());
    }

    public void checkCollide(Player player) {
        for (Gate gate : gates) {
            if (gate.isActive)
                if (gate.isCollidedWithPlayer(player)) {
                    gate.movePlayer();
                }
        }

        for (Potion potion : potions) {
            if (potion.isActive) {
                if (potion.isCollidedWithPlayer(player)) {
                    player.setPotion(player.getPotionAmount() + 1);
                    potion.setActive(false);
                }
            }
        }

        if (fragment.isActive) {
            if (fragment.isCollidedWithPlayer(player)) {
                fragment.setPosition();
            }
        }
    }

    public void draw(Graphics2D g, int xOffset, int yOffset) {
        for (Potion potion : potions) {
            if (potion.isActive) {
                potion.draw(g, xOffset, yOffset);
            }
        }

        if (fragment.isActive) {
            System.out.println("sd");
            fragment.draw(g, xOffset, yOffset);
        }
    }

    public static void setFragment() {
        fragment.setActive(true);
        fragment.setPosition();
    }
    public static void setGateActive(boolean active) {
        for (Gate gate: gates) {
            gate.setActive(true);
        }
    }

    public static void addGate(Gate gate) {
        gates.add(gate);
    }

    public static void addPotion(Potion potion) {
        potions.add(potion);
    }

    public static void clearAll() {
        gates.clear();
        potions.clear();
        fragment.setActive(false);
    }
}
