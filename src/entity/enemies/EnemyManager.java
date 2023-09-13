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

    private void loadEnemyImgs() {
        grandPrixArr = getImgArr(LoadSave.GetSprite(LoadSave.HAUNTING_GHOSTS), 3, 4, 64, 64);
    }

    private BufferedImage[][] getImgArr(BufferedImage atlas, int xSize, int ySize, int spriteW, int spriteH) {
        // TODO
        BufferedImage[][] tempArr = new BufferedImage[ySize][xSize];
        for (int j = 0; j < tempArr.length; j++)
            for (int i = 6; i < 9; i++) {
                tempArr[j][i - 6] = atlas.getSubimage(i * spriteW, j * spriteH, spriteW, spriteH);
            }
        return tempArr;
    }

    private void drawGrandPrix(Graphics2D g2, int playerX, int playerY, int screenPosX, int screenPosY) {
        for (GrandPrix gp : currentLevel.getHauntingGhost()) {
            g.drawImage(grandPrixArr[2][2], gp.getX() - playerX + screenPosX,
                    gp.getY() - playerY + screenPosY, gp.getWidth(), gp.getHeight(), null);
        }
    }
}
