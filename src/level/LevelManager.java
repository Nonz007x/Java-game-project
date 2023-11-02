package level;

import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import java.util.ArrayList;

import static utils.HelpMethods.ScaleImage;

public class LevelManager {
    Game game;

    private int aniTick, aniIndex;
    private int waterfallAniIndex;
    private BufferedImage[] waterfallSprite;
    private int waterAniIndex;
    private static final BufferedImage[] levelSprites;
    private static final ArrayList<Level> levels;
    private static int lvlIndex;

    static {
        levelSprites = new BufferedImage[2401];
        levels = new ArrayList<>();
        lvlIndex = 0;
        InitializeTileImage();
        BuildAllLevels();
    }

    public LevelManager(Game game) {

        this.game = game;
        createWaterfall();
    }

    private static void BuildAllLevels() {
        String[] allLevels = LoadSave.GetAllLevels();
        for (String path : allLevels) {
            levels.add(new Level(path));
        }
    }

    private static void InitializeTileImage() {
        BufferedImage imageSet = LoadSave.GetSprite(LoadSave.LEVEL_SPRITE);
        int counter = 1;

        for (int row = 0;  row < 60;  row++) {
            for (int col = 0; col < 40; col++) {
                BufferedImage tileImage = imageSet.getSubimage(16 * col, 16 *  row, 16, 16);
                BufferedImage scaledImage = ScaleImage(tileImage, Game.TILE_SIZE, Game.TILE_SIZE);
                levelSprites[counter] = scaledImage;
                counter++;
            }
        }
    }

    private void createWaterfall() {
        waterfallSprite = new BufferedImage[216];
        BufferedImage img = LoadSave.GetSprite(LoadSave.LEVEL_SPRITE);
        int counter = 0;
        for (int row = 1; row <= 2; row++) {
            for (int col = 0; col < 4; col++) {
                waterfallSprite[counter] = img.getSubimage(col * 16, row*16, 16, 16);
                counter++;
            }
        }
    }

    public void draw(Graphics2D g2, int playerX, int playerY, int screenPosX, int screenPosY) {
        int tileWidth = Game.TILE_SIZE;
        int tileHeight = Game.TILE_SIZE;
        Level currentLevel = GetCurrentLevel();
        BufferedImage[] sprites = levelSprites;

        int startRow = Math.max(0, (playerY / tileHeight) - Game.ROW);
        int startCol = Math.max(0, (playerX / tileWidth) - Game.COLUMN);
        int endRow = Math.min(currentLevel.getWorldRow(), (playerY / tileHeight) + Game.ROW);
        int endCol = Math.min(currentLevel.getWorldCol(), (playerX / tileWidth)  + Game.COLUMN);

        for (int worldRow = startRow; worldRow < endRow; worldRow++) {
            for (int worldCol = startCol; worldCol < endCol; worldCol++) {
                int tileNum = currentLevel.getSpriteIndex(0, worldCol, worldRow);
                int tileNum2 = currentLevel.getSpriteIndex(1, worldCol, worldRow);
                int tileX = worldCol * tileWidth - playerX + screenPosX;
                int tileY = worldRow * tileHeight - playerY + screenPosY;

                if (tileNum == 41) {
                    g2.drawImage(waterfallSprite[waterfallAniIndex], tileX, tileY, tileWidth, tileHeight, null);
                } else {
                    g2.drawImage(sprites[tileNum], tileX, tileY, null);
                }
                g2.drawImage(sprites[tileNum2], tileX, tileY, null);
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

    public static Level GetCurrentLevel() {
        return levels.get(lvlIndex);
    }

    public static int GetLevelIndex() {
        return lvlIndex;
    }

    public static void SetLevelIndex(int index) {
        lvlIndex = index;
    }

    public void toggleLevel() {
        lvlIndex = (lvlIndex + 1) % levels.size();
        game.getPlaying().getPlayer().loadLvlData(GetCurrentLevel().getCollisionTile());
    }

}
