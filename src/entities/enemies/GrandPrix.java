package entities.enemies;

import entities.Enemy;
import gamestates.Playing;

import static utils.Constants.EnemyConstants.*;

public class GrandPrix extends Enemy {

    public GrandPrix(int x, int y) {
        super(x, y, GRANDPRIX_WIDTH_DEFAULT, GRANDPRIX_HEIGHT_DEFAULT);
        initHitbox(5, 10, GRANDPRIX_WIDTH_DEFAULT - 5, GRANDPRIX_HEIGHT_DEFAULT - 10);
        setSpeed(3);
        setDetectionRange(3);
    }

    public void update(int[][] collisionTile, Playing playing) {
        updateAnimationTick();
        updateBehavior(collisionTile, playing);
    }

    private void updateBehavior(int[][] collisionTile, Playing playing) {
        int playerX = playing.getPlayer().getX();
        int playerY = playing.getPlayer().getY();

        if (isPlayerInRange(playing.getPlayer())) {
            int deltaX = playerX - worldX;
            int deltaY = playerY - worldY;

            double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

            if (distance > 0) {
                double directionX = deltaX / distance;
                double directionY = deltaY / distance;

                velocityX = (int) (speed * directionX);
                velocityY = (int) (speed * directionY);

                updateXPos(velocityX);
                updateYPos(velocityY);
            } else {
                velocityX = 0;
                velocityY = 0;
            }
        } else {
            state = 1;
            aniIndex = 0;
        }

        if (velocityX == 0 && velocityY == 0) {
            return;
            // Maintain previous state
        } else if (velocityY >= 0) {
            state = 0;
        } else if (velocityY < 0) {
            state = 2;
        }

        if (velocityX != 0) {
            state = 1;
        }

        if (hitbox.intersects(playing.getPlayer().getHitbox())) {
//            Gamestate.state = Gamestate.QUIT;
        }


//       move(collisionTile);
//        switch (state) {
//            case IDLE -> {
//                newState(RUNNING);
//            }
//            case RUNNING -> {
//                if (canSeePlayer(lvlData, playing.getPlayer())) {
//                    turnTowardsPlayer(playing.getPlayer());
//                    if (isPlayerCloseForAttack(playing.getPlayer()))
//                        newState(ATTACK);
//                }
//                move(lvlData);
//            }
//            case ATTACK -> {
//                if (aniIndex == 0)
//                    attackChecked = false;
//                if (aniIndex == 3 && !attackChecked)
//                    checkPlayerHit(attackBox, playing.getPlayer());
//            }
//            case HIT -> {
//                if (aniIndex <= GetSpriteAmount(enemyType, state) - 2)
//                    pushBack(pushBackDir, lvlData, 2f);
//                updatePushBackDrawOffset();
//            }
//        }
    }

}
