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
            tileNum1 = gp.tileM.getTile(entityTopRow,entityLeftCol);
            tileNum2 = gp.tileM.getTile(entityTopRow,entityRightCol);
            if (gp.tileM.isCollision(tileNum1) || gp.tileM.isCollision(tileNum2)) {
                entity.addCollisionDirection("TOP");
                System.out.println("Add TOP");
            }
        }
        if (gp.player.keyH.downPressed) {
            tileNum1 = gp.tileM.getTile(entityBottomRow,entityLeftCol);
            tileNum2 = gp.tileM.getTile(entityBottomRow,entityRightCol);
            if (gp.tileM.isCollision(tileNum1) || gp.tileM.isCollision(tileNum2)) {
                entity.addCollisionDirection("BOTTOM");
                System.out.println("Add BOTTOM");
            }
        }
        if (gp.player.keyH.leftPressed) {
            tileNum1 = gp.tileM.getTile(entityTopRow,entityLeftCol);
            tileNum2 = gp.tileM.getTile(entityBottomRow,entityLeftCol);
            if (gp.tileM.isCollision(tileNum1) || gp.tileM.isCollision(tileNum2)) {
                entity.addCollisionDirection("LEFT");
                System.out.println("Add LEFT");
            }
        }
        if (gp.player.keyH.rightPressed) {
            tileNum1 = gp.tileM.getTile(entityTopRow,entityRightCol);
            tileNum2 = gp.tileM.getTile(entityBottomRow,entityRightCol);
            if (gp.tileM.isCollision(tileNum1) || gp.tileM.isCollision(tileNum2)) {
                entity.addCollisionDirection("RIGHT");
                System.out.println("Add RIGHT");
            }
        }
    }
}

