package entity.enemies;

import static utils.Constants.EnemyConstants.GRANDPRIX_HEIGHT_DEFAULT;
import static utils.Constants.EnemyConstants.GRANDPRIX_WIDTH_DEFAULT;

public class GrandPrix extends Enemy {

    public GrandPrix(int x, int y) {
        super(x, y, GRANDPRIX_WIDTH_DEFAULT, GRANDPRIX_HEIGHT_DEFAULT);
    }

    @Override
    public void attack() {

    }

}
