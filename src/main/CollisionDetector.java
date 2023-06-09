package main;

import Tile.TileManager;
import entity.Entity;

public class CollisionDetector {
    GamePanel gp;

    public CollisionDetector(GamePanel gp) {

        this.gp = gp;
    }

    public void checkTile(Entity entity) {

        TileManager tileManager = gp.tileM;
        int tileSize = gp.tileSize;

        int entityLeftWorldX = entity.worldX + entity.hitBox.x;
        int entityRightWorldX = entity.worldX + entity.hitBox.x + entity.hitBox.width;
        int entityTopWorldY = entity.worldY + entity.hitBox.y;
        int entityBottomWorldY = entity.worldY + entity.hitBox.y + entity.hitBox.height;

        int playerX = entity.worldX + entity.hitBox.x + entity.hitBox.width / 2;
        int playerY = entity.worldY + entity.hitBox.y + entity.hitBox.height / 2;

        int playerTileX = playerX / tileSize;
        int playerTileY = playerY / tileSize;

        int entityLeftCol = entityLeftWorldX / tileSize;
        int entityRightCol = entityRightWorldX / tileSize;
        int entityTopRow = entityTopWorldY / tileSize;
        int entityBottomRow = entityBottomWorldY / tileSize;

        int tileNum;

        tileNum = tileManager.getTileNum(entityTopRow, playerTileX);
        if (tileManager.tile[tileNum].isCollision()) {
            entity.addCollisionDirection("TOP");
        }

        tileNum = tileManager.getTileNum(entityBottomRow, playerTileX);
        if (tileManager.tile[tileNum].isCollision()) {
            entity.addCollisionDirection("BOTTOM");
        }

        tileNum = tileManager.getTileNum(playerTileY, entityLeftCol);
        if (tileManager.tile[tileNum].isCollision()) {
            entity.addCollisionDirection("LEFT");
        }

        tileNum = tileManager.getTileNum(playerTileY, entityRightCol);
        if (tileManager.tile[tileNum].isCollision()) {
            entity.addCollisionDirection("RIGHT");
        }
    }
}

