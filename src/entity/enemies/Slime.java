package entity.enemies;

import entity.Player;

public class Slime {

    public void attack(Player player) {
        player.setSpeed(0);
    }
}
