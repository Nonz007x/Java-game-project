package entity.enemies;

import gamestates.Playing;
import Level.Level;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EnemyManager {

    private Playing playing;
    private BufferedImage[][] grandPrixArr;
    private Level currentLevel;

    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImgs();
    }

    public void loadEnemies(Level level) {
        this.currentLevel = level;
    }

    public void draw(Graphics2D g2, int playerX, int playerY, int screenPosX, int screenPosY) {
        drawGrandPrix(g2, playerX, playerY, screenPosX, screenPosY);
    }

    private void drawGrandPrix(Graphics2D g2, int playerX, int playerY, int screenPosX, int screenPosY) {
        for (GrandPrix gp : currentLevel.getGrandPrixs()) {
            g2.drawImage(grandPrixArr[gp.getState()][gp.getAniIndex()], gp.getX() - playerX + screenPosX + gp.getflipX(),
                    gp.getY() - playerY + screenPosY, gp.getWidth() * gp.getflipW(), gp.getHeight(), null);
            g2.setColor(Color.PINK);
            g2.drawRect((int) gp.getHitboxX(),
                    (int) gp.getHitboxY(),
                    (int) gp.getHitboxWidth(),
                    (int) gp.getHitboxHeight());
        }
    }

    private void loadEnemyImgs() {
        grandPrixArr = getImgArr(LoadSave.GetSprite(LoadSave.GRANDPRIX), 3, 3, 64, 64);
    }

    private BufferedImage[][] getImgArr(BufferedImage atlas, int xSize, int ySize, int spriteW, int spriteH) {
        // TODO
        BufferedImage[][] tempArr = new BufferedImage[ySize][xSize];
        for (int j = 0; j < tempArr.length; j++)
            for (int i = 0; i < tempArr[0].length; i++) {
                tempArr[j][i] = atlas.getSubimage(i * spriteW, j * spriteH, spriteW, spriteH);
            }
        return tempArr;
    }

    public void update(int[][] collisionTile, Playing playing) {
        for (GrandPrix gp : currentLevel.getGrandPrixs()) {
            gp.update(collisionTile, playing);
        }
    }
}
