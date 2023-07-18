package main;

import tile.TileManager;
import entity.Entity;

import java.awt.*;

public class CollisionDetector {
    private static GamePanel gp;

    public CollisionDetector(GamePanel gp) {
        CollisionDetector.gp = gp;
    }

    public static void checkTile(Entity entity) {
        TileManager tileManager = gp.tileM;
        int tileSize = gp.tileSize;
        Rectangle hitBox = entity.hitBox;
        int entityWorldX = entity.worldX;
        int entityWorldY = entity.worldY;

        int entityLeftWorldX = entityWorldX + hitBox.x;
        int entityRightWorldX = entityLeftWorldX + hitBox.width;
        int entityTopWorldY = entityWorldY + hitBox.y;
        int entityBottomWorldY = entityTopWorldY + hitBox.height;

        int entityX = entityLeftWorldX + hitBox.width / 2;
        int entityY = entityTopWorldY + hitBox.height / 2;

        int entityTileX = entityX / tileSize;
        int entityTileY = entityY / tileSize;
        int entityLeftCol = entityLeftWorldX / tileSize;
        int entityRightCol = entityRightWorldX / tileSize;
        int entityTopRow = entityTopWorldY / tileSize;
        int entityBottomRow = entityBottomWorldY / tileSize;

        // Check collision for top row tile
        int tileNum = tileManager.getTileNum(entityTopRow, entityTileX);
        if (tileManager.tile[tileNum].isCollision()) {
            entity.addCollisionDirection("TOP");
        }

        // Check collision for bottom row tile
        tileNum = tileManager.getTileNum(entityBottomRow, entityTileX);
        if (tileManager.tile[tileNum].isCollision()) {
            entity.addCollisionDirection("BOTTOM");
        }

        // Check collision for left column tile
        tileNum = tileManager.getTileNum(entityTileY, entityLeftCol);
        if (tileManager.tile[tileNum].isCollision()) {
            entity.addCollisionDirection("LEFT");
        }

        // Check collision for right column tile
        tileNum = tileManager.getTileNum(entityTileY, entityRightCol);
        if (tileManager.tile[tileNum].isCollision()) {
            entity.addCollisionDirection("RIGHT");

        }
    }

//    public static boolean canMoveHere(int x, int y, int width, int height) {
//
//    }
//
//    private static boolean IsSolid(int x, int y) {

//    }
}