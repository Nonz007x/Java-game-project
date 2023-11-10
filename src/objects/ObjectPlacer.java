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
                ObjectManager.addGate(new Gate(12, 19, 3, 3, 3, true));
                ObjectManager.addGate(new Gate(2, 44, 3, 3, 3, true));
                ObjectManager.addGate(new Gate(60, 38, 3, 3, 1, true));
                ObjectManager.addGate(new Gate(61, 38, 3, 3, 1, true));
                ObjectManager.addGate(new Gate(62, 38, 3, 3, 1, true));
                ObjectManager.addGate(new Gate(63, 38, 3, 3, 1, true));
                ObjectManager.addGate(new Gate(64, 38, 3, 3, 1, true));
                ObjectManager.addGate(new Gate(65, 38, 3, 3, 1, true));
            }
            case 1-> {
                ObjectManager.addGate(new Gate(19, 21, 3, 3, 2, false));
                ObjectManager.addGate(new Gate(20, 21, 3, 3, 2, false));
            }
            case 2 -> {
                ObjectManager.addGate(new Gate(1, 19, 3, 3, 3, false));
                ObjectManager.addGate(new Gate(1, 20, 3, 3, 3, false));
            }
            case 3-> {
                ObjectManager.setFragment();
            }
        }
    }
}
