package main;

import Tile.TileManager;
import entity.Entity;

public class CollisionDetector {
    private GamePanel gp;

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

        int entityX = entity.worldX + entity.hitBox.x + entity.hitBox.width / 2;
        int entityY = entity.worldY + entity.hitBox.y + entity.hitBox.height / 2;

        int entityTileX = entityX / tileSize;
        int entityTileY = entityY / tileSize;

        int entityLeftCol = entityLeftWorldX / tileSize;
        int entityRightCol = entityRightWorldX / tileSize;
        int entityTopRow = entityTopWorldY / tileSize;
        int entityBottomRow = entityBottomWorldY / tileSize;

        checkCollision(tileManager, entity, entityTopRow, entityTileX, "TOP");
        checkCollision(tileManager, entity, entityBottomRow, entityTileX, "BOTTOM");
        checkCollision(tileManager, entity, entityTileY, entityLeftCol, "LEFT");
        checkCollision(tileManager, entity, entityTileY, entityRightCol, "RIGHT");
    }

    private void checkCollision(TileManager tileManager, Entity entity, int row, int col, String direction) {
        int tileNum = tileManager.getTileNum(row, col);
        if (tileManager.tile[tileNum].isCollision()) {
            entity.addCollisionDirection(direction);
        }
    }
}
