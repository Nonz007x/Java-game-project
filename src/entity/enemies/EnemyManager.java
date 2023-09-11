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

    public void draw(Graphics g, int xLvlOffset) {
        drawGrandPrix(g, xLvlOffset);
    }

    private void loadEnemyImgs() {
        grandPrixArr = getImgArr(LoadSave.GetSprite(LoadSave.HAUNTING_GHOSTS), 3, 4, 64, 64);
    }

    private BufferedImage[][] getImgArr(BufferedImage atlas, int xSize, int ySize, int spriteW, int spriteH) {
//        BufferedImage[][] tempArr = new BufferedImage[ySize][xSize];
//        for (int j = 0; j < tempArr.length; j++)
//            for (int i = 0; i < tempArr[j].length; i++)
//                tempArr[j][i] = atlas.getSubimage(i * spriteW, j * spriteH, spriteW, spriteH);
//        return tempArr;

        BufferedImage[][] tempArr = new BufferedImage[ySize][xSize];
        for (int j = 0; j < tempArr.length; j++)
            for (int i = 6; i < 9; i++) {
                tempArr[j][i - 6] = atlas.getSubimage(i * spriteW, j * spriteH, spriteW, spriteH);
            }
        return tempArr;
    }

    private void drawGrandPrix(Graphics g, int xLvlOffset) {
        for (GrandPrix gp : currentLevel.getHauntingGhost())
//            if (hg.isActive()) {
            g.drawImage(grandPrixArr[2][2], xLvlOffset - 96,
                    96,
                    64*2,
                    64*2,
                    null);
//
//
//                g.drawImage(hauntingGhostArr[hg.getState()][hg.getAniIndex()], (int) hg.getHitbox().x - xLvlOffset - CRABBY_DRAWOFFSET_X + hg.flipX(),
//                        (int) hg.getHitbox().y - CRABBY_DRAWOFFSET_Y + (int) hg.getPushDrawOffset(), CRABBY_WIDTH * hg.flipW(), CRABBY_HEIGHT, null);

//				hg.drawHitbox(g, xLvlOffset);
//				hg.drawAttackBox(g, xLvlOffset);
//            }

    }
}
