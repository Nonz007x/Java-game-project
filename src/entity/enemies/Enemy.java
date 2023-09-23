package entity.enemies;

import entity.Entity;
import entity.Player;
import main.Game;

import static utils.Constants.EnemyConstants.GetSpriteAmount;
import static utils.Constants.EnemyConstants.*;


public abstract class Enemy extends Entity {
    private int enemyType;
    private final int aniSpeed = 10;
    private int detectionRange;

    public Enemy(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.state = IDLE;
    }

    protected void move(int[][] collisionTile) {

//        float xSpeed = 0;

//        if (walkDir == LEFT)
//            xSpeed = -speed;
//        else
//            xSpeed = speed;

//        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData))
//            if (IsFloor(hitbox, xSpeed, lvlData)) {
//                hitbox.x += xSpeed;
//                return;
//            }

//        changeWalkDir();
    }

//    protected boolean canSeePlayer(int[][] lvlData, Player player) {
//        int playerTileY = player.getY() / Game.TILE_SIZE;
//        if (isPlayerInRange(player)) {
//            if (IsSightClear(lvlData, hitbox, player.hitbox, tileY))
//                return true;
//        }
//        return false;
//    }

    //    protected boolean isPlayerInRange(Player player) {
//        int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
//        return absValue <= attackDistance * 5;
//    }

    protected boolean isPlayerInRange(Player player) {
        int xRange = (int) Math.abs(player.getHitboxX() - hitbox.x);
        int yRange = (int) Math.abs(player.getHitboxY() - hitbox.y);
        int range = detectionRange * Game.TILE_SIZE;
        return xRange <= range && yRange <= range;
    }
    protected void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(state, enemyType)) {
                aniIndex = 0;
            }
        }
    }

    public int getAniIndex() {
        return aniIndex;
    }

    public int getflipX() {
        if (velocityX >= 0)
            return 0;
        else
            return width;
    }

    public int getflipW() {
        if (velocityX >= 0)
            return 1;
        else
            return -1;
    }

    protected void setDetectionRange(int range) {
        detectionRange = range;
    }
}
