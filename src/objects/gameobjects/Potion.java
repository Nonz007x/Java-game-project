package objects.gameobjects;

import main.Game;
import objects.SuperObject;

import utils.Constants;
import utils.LoadSave;

public class Potion extends SuperObject {
    public Potion(int x, int y) {
        super(x * Game.TILE_SIZE, y * Game.TILE_SIZE, Constants.GameObject.Potion);
        image = LoadSave.getSprite("40degree.png");
        initHitBox(0, 0, 48, 48);
    }
}
