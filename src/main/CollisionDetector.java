package main;

import entity.Entity;

public class CollisionDetector {
    GamePanel gp;
    static int cs = 0;


    public CollisionDetector(GamePanel gp) {

        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        int tileSize = gp.tileSize;

        int entityLeftWorldX = entity.worldX + entity.hitBox.x;
        int entityRightWorldX = entity.worldX + entity.hitBox.x + entity.hitBox.width;
        int entityTopWorldY = entity.worldY + entity.hitBox.y;
        int entityBottomWorldY = entity.worldY + entity.hitBox.y + entity.hitBox.height;

        int playerX = entity.worldX + entity.hitBox.x + entity.hitBox.width / 2;
        int playerY = entity.worldY + entity.hitBox.y + entity.hitBox.height / 2;

        int playerTileX = (playerX / tileSize);
        int playerTileY = (playerY / tileSize);


        int entityLeftCol = entityLeftWorldX / tileSize;
        int entityRightCol = entityRightWorldX / tileSize;
        int entityTopRow = entityTopWorldY / tileSize;
        int entityBottomRow = entityBottomWorldY / tileSize;


        if (gp.tileM.isCollision(gp.tileM.getTile(entityTopRow, playerTileX)) || gp.tileM.isCollision(gp.tileM.getTile(entityTopRow, playerTileX))) {
            entity.addCollisionDirection("TOP");
//            System.out.println("Collision TOP!");
        }
        if (gp.tileM.isCollision(gp.tileM.getTile(entityBottomRow, playerTileX)) || gp.tileM.isCollision(gp.tileM.getTile(entityBottomRow, playerTileX))) {
            entity.addCollisionDirection("BOTTOM");
//            System.out.println("Collision BOTTOM!");
        }
        if (gp.tileM.isCollision(gp.tileM.getTile(playerTileY, entityLeftCol)) || gp.tileM.isCollision(gp.tileM.getTile(playerTileY, entityLeftCol))) {
            entity.addCollisionDirection("LEFT");
//            System.out.println("Collision LEFT!");
        }
        if (gp.tileM.isCollision(gp.tileM.getTile(playerTileY, entityRightCol)) || gp.tileM.isCollision(gp.tileM.getTile(playerTileY, entityRightCol))) {
            entity.addCollisionDirection("RIGHT");
//            System.out.println("Collision RIGHT!");
        }

    }
}

