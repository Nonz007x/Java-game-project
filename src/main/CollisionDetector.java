package main;

import entity.Entity;

public class CollisionDetector {
    GamePanel gp;


    public CollisionDetector(GamePanel gp) {

        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        int tileSize = gp.tileSize;

        int entityLeftWorldX = entity.worldX + entity.hitBox.x;
        int entityRightWorldX = entity.worldX + entity.hitBox.x + entity.hitBox.width;
        int entityTopWorldY = entity.worldY + entity.hitBox.y;
        int entityBottomWorldY = entity.worldY + entity.hitBox.y + entity.hitBox.height;

        int entityLeftCol = entityLeftWorldX / tileSize;
        int entityRightCol = entityRightWorldX / tileSize;
        int entityTopRow = entityTopWorldY / tileSize;
        int entityBottomRow = entityBottomWorldY / tileSize;

        int tileNum1, tileNum2;
        if (gp.player.keyH.upPressed) {
            entityTopRow = (entityTopWorldY) / tileSize;
            tileNum1 = gp.tileM.mapTileNum[entityTopRow][entityLeftCol];
            tileNum2 = gp.tileM.mapTileNum[entityTopRow][entityRightCol];
            if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                entity.collisionOn = true;
            }
        }
        if (gp.player.keyH.downPressed) {
            entityBottomRow = (entityBottomWorldY) / tileSize;
            tileNum1 = gp.tileM.mapTileNum[entityBottomRow][entityLeftCol];
            tileNum2 = gp.tileM.mapTileNum[entityBottomRow][entityRightCol];
            if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                entity.collisionOn = true;
            }
        }
        if (gp.player.keyH.leftPressed) {
            entityLeftCol = (entityLeftWorldX) / tileSize;
            tileNum1 = gp.tileM.mapTileNum[entityTopRow][entityLeftCol];
            tileNum2 = gp.tileM.mapTileNum[entityBottomRow][entityLeftCol];
            if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                entity.collisionOn = true;
            }
        }
        if (gp.player.keyH.rightPressed) {
            entityRightCol = (entityRightWorldX) / tileSize;
            tileNum1 = gp.tileM.mapTileNum[entityTopRow][entityRightCol];
            tileNum2 = gp.tileM.mapTileNum[entityBottomRow][entityRightCol];
            if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                entity.collisionOn = true;
            }
        }
    }
}

