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

    public Enemy() {
        EnemyState currentState = EnemyState.IDLE;
    }

    public abstract void attack();
}
