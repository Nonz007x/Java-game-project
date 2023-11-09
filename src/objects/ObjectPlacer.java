package objects;

import level.LevelManager;
import objects.gameobjects.Gate;
import objects.gameobjects.Potion;

public class ObjectPlacer {
    public static void placeObject() {
        ObjectManager.clearAll();
        switch (LevelManager.getLevelIndex()) {
            case 0 -> {
                System.out.println("Placing");
                for (int i = 30; i <= 36; i++) {
                    ObjectManager.addPotion(new Potion(i, 7));
                }
                ObjectManager.addGate(new Gate(12, 19, 0, 0, 1, true));
                ObjectManager.addGate(new Gate(2, 44, 0, 0, 2, true));
                ObjectManager.addGate(new Gate(60, 38, 0, 0, 1, true));
                ObjectManager.addGate(new Gate(61, 38, 0, 0, 1, true));
                ObjectManager.addGate(new Gate(62, 38, 0, 0, 1, true));
                ObjectManager.addGate(new Gate(63, 38, 0, 0, 1, true));
                ObjectManager.addGate(new Gate(64, 38, 0, 0, 1, true));
                ObjectManager.addGate(new Gate(65, 38, 0, 0, 1, true));
            }
        }
    }
}
