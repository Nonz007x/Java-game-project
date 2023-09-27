package entities;

import entities.enemies.GrandPrix;
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

    public void draw(Graphics2D g2, int xOffset, int yOffset) {
        drawGrandPrix(g2, xOffset, yOffset);
    }


    private void drawGrandPrix(Graphics2D g2, int xOffset, int yOffset) {
        for (GrandPrix gp : currentLevel.getGrandPrixs()) {
            g2.drawImage(grandPrixArr[gp.getState()][gp.getAniIndex()], gp.getX() + xOffset + gp.getflipX(),
                    gp.getY() + yOffset, gp.getWidth() * gp.getflipW(), gp.getHeight(), null);
            g2.setColor(Color.PINK);
            gp.drawHitbox(g2, xOffset, yOffset);
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
