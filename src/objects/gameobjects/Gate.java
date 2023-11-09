package objects.gameobjects;

import gamestates.Playing;
import main.Game;
import objects.SuperObject;
import ui.TransitionOverlay;


import static utils.Constants.GameObject.GATE;

public class Gate extends SuperObject {
    private final int destX;
    private final int destY;
    private final int destLevel;

    public Gate(int x, int y, int destX, int destY, boolean active) {
        this(x, y, destX, destY, -1, active);
    }

    public Gate(int x, int y, int destX, int destY, int destLevel, boolean active) {
        super(x * Game.TILE_SIZE, y * Game.TILE_SIZE, GATE);
        this.isActive = active;
        initHitBox(0, 0, 48, 48);
        this.destX = destX * Game.TILE_SIZE;
        this.destY = destY * Game.TILE_SIZE;
        this.destLevel = destLevel;
    }

    public void movePlayer() {
        Playing.playTransition();
        TransitionOverlay.playTransition(() -> {
            if (destLevel != -1)
                Playing.loadNewLevel(destLevel);
            Playing.getPlayer().teleport(destX, destY);
        });
//        Playing.getPlayer().setSpawn(destX, destY);
    }
}
