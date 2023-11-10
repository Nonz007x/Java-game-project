package level;

import entities.Player;
import gamestates.Playing;
import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import java.util.ArrayList;

import static utils.HelpMethods.ScaleImage;

public class LevelManager {
    Playing playing;

    private int aniTick, aniIndex;
    private int waterfallAniIndex;
    private BufferedImage[] waterfallSprite, torchSprite;
    private int waterAniIndex;
    private static final BufferedImage[] levelSprites;
    private static final ArrayList<Level> levels;
    private static int lvlIndex;

    private static final int TILE_HEIGHT = Game.TILE_SIZE;
    private static final int TILE_WIDTH = Game.TILE_SIZE;

    static {
        levelSprites = new BufferedImage[256];
        levels = new ArrayList<>();
        lvlIndex = 0;
        InitializeTileImage();
        BuildAllLevels();
    }

    public LevelManager(Playing playing) {
        this.playing = playing;
    }

    private static void BuildAllLevels() {
        String[] allLevels = LoadSave.GetAllLevels();
        for (String path : allLevels) {
            levels.add(new Level(path));
        }
    }

    private static void InitializeTileImage() {
        BufferedImage imageSet = LoadSave.getSprite(LoadSave.LEVEL_SPRITE);
        int index = 1;

        for (int row = 0;  row < 2;  row++) {
            for (int col = 0; col < 10; col++) {
                BufferedImage tileImage = imageSet.getSubimage(16 * col, 16 *  row, 16, 16);
                BufferedImage scaledImage = ScaleImage(tileImage, Game.TILE_SIZE, Game.TILE_SIZE);
                levelSprites[index] = scaledImage;
                index++;
            }
        }
    }

    private void createWaterfall() {
        torchSprite = new BufferedImage[8];
        waterfallSprite = new BufferedImage[8];
        BufferedImage img = LoadSave.getSprite(LoadSave.LEVEL_SPRITE);
        int index = 0;
        for (int row = 1; row <= 2; row++) {
            for (int col = 0; col < 4; col++) {
                waterfallSprite[index] = img.getSubimage(col * 16, row*16, 16, 16);
                index++;
            }
        }
    }

    public void draw(Graphics2D g2, int playerX, int playerY, int screenPosX, int screenPosY) {
        Level currentLevel = getCurrentLevel();

        int startRow = Math.max(0, (playerY / TILE_HEIGHT) - Game.ROW);
        int startCol = Math.max(0, (playerX / TILE_WIDTH) - Game.COLUMN);
        int endRow = Math.min(currentLevel.getWorldRow(), (playerY / TILE_HEIGHT) + Game.ROW + 1);
        int endCol = Math.min(currentLevel.getWorldCol(), (playerX / TILE_WIDTH)  + Game.COLUMN + 1);

        for (int worldRow = startRow; worldRow < endRow; worldRow++) {
            for (int worldCol = startCol; worldCol < endCol; worldCol++) {
                int tileNum = currentLevel.getSpriteIndex(0, worldCol, worldRow);
                int tileNum2 = currentLevel.getSpriteIndex(1, worldCol, worldRow);
                int tileX = worldCol * TILE_WIDTH - playerX + screenPosX;
                int tileY = worldRow * TILE_HEIGHT - playerY + screenPosY;

//                if (tileNum == 41) {
//                    g2.drawImage(waterfallSprite[waterfallAniIndex], tileX, tileY, TILE_WIDTH, TILE_HEIGHT, null);
//                } else {
                g2.drawImage(levelSprites[tileNum], tileX, tileY, null);
//                }
                g2.drawImage(levelSprites[tileNum2], tileX, tileY, null);
            }
        }
    }


    private static boolean IsTileVisible(int screenX, int screenY, int tileSize) {
        return screenX > -tileSize &&
                screenY > -tileSize;
    }

    public void update() {
        updateAnimation();
    }

    private void updateAnimation() {
        aniTick++;
        if (aniTick % 10 == 0) {
            waterfallAniIndex++;
            if (waterfallAniIndex >= 8) {
                waterfallAniIndex = 0;
            }
        }
    }

    public static Level getCurrentLevel() {
        return levels.get(lvlIndex);
    }

    public static Level getLevel(int index) {
        return levels.get(index);
    }

    public static int getLevelIndex() {
        return lvlIndex;
    }

    public static void setLevelIndex(int index) {
        if (index >= 0 && index < levels.size())
            lvlIndex = index;
        Playing.getPlayer().loadLvlData(getCurrentLevel().getCollisionTile());
    }

    public static void toggleLevel() {
        lvlIndex = (lvlIndex + 1) % levels.size();
        Playing.getPlayer().loadLvlData(getCurrentLevel().getCollisionTile());
    }
}
