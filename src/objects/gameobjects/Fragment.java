package objects.gameobjects;

import main.Game;
import objects.SuperObject;
import utils.Constants;
import utils.LoadSave;

import java.awt.image.BufferedImage;
import java.util.Random;

public class Fragment extends SuperObject {
    private static final Random random = new Random();
    private static final BufferedImage image;
    private static final int MIN_X = 3;
    private static final int MAX_X = 28;
    private static final int MIN_Y = 3;
    private static final int MAX_Y = 14;

    static {
        image = LoadSave.getSprite("fragment.png");
    }

    public Fragment() {
        super(0, 0, Constants.GameObject.FRAGMENT);
        initHitBox(-48, -48, 48, 48);
        super.image = image;
        isActive = false;
    }

    public void setPosition() {
        int newX = random.nextInt(MAX_X - MIN_X + 1) + MIN_X;
        int newY = random.nextInt(MAX_Y - MIN_Y + 1) + MIN_Y;
        x = newX * Game.TILE_SIZE;
        y = newY * Game.TILE_SIZE;
        hitBox.x = newX * Game.TILE_SIZE;
        hitBox.y = newY * Game.TILE_SIZE;
    }


}
