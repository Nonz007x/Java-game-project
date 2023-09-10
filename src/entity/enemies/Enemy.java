package entity.enemies;

import entity.Entity;

public abstract class Enemy extends Entity {

    public enum EnemyState {
        IDLE,
        PATROL,
        CHASE,
        ATTACK,
        RETREAT
    }

    public Enemy(int width, int height) {
        super(width, height);
        EnemyState currentState = EnemyState.IDLE;
    }

    public abstract void attack();
}
