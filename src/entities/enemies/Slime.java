package entities.enemies;

import entities.Player;

public class Slime {

    public void attack(Player player) {
        player.setSpeed(0);
    }
}
