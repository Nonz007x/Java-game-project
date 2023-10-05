package Level;

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

        int worldWidth = levels.get(lvlIndex).getWorldCol() * Game.TILE_SIZE;
        int worldHeight = levels.get(lvlIndex).getWorldRow() * Game.TILE_SIZE;

        for (int worldRow = 0; worldRow < GetCurrentLevel().getWorldRow(); worldRow++) {
            int tileY = worldRow * Game.TILE_SIZE;
            int screenY = tileY - playerY + screenPosY;

            for (int worldCol = 0; worldCol < GetCurrentLevel().getWorldCol(); worldCol++) {
                int tileNum = levels.get(lvlIndex).getSpriteIndex(0, worldCol, worldRow);
                int tileNum2 = levels.get(lvlIndex).getSpriteIndex(1,worldCol, worldRow);

                int tileX = worldCol * Game.TILE_SIZE;
                int screenX = tileX - playerX + screenPosX;

                // Stop camera at the edge
                if (screenPosX > playerX)
                    screenX = tileX;

                if (screenPosY > playerY)
                    screenY = tileY;

                int rightOffset = Game.GAME_WIDTH - screenPosX;
                if (rightOffset > worldWidth - playerX)
                    screenX = Game.GAME_WIDTH - (worldWidth - tileX);

                int bottomOffset = Game.GAME_HEIGHT - screenPosY;
                if (bottomOffset > worldHeight - playerY)
                    screenY = Game.GAME_HEIGHT - (worldHeight - tileY);

                if (IsTileVisible(screenX, screenY, Game.GAME_WIDTH, Game.GAME_HEIGHT, Game.TILE_SIZE)) {
                    if (tileNum == 41) {
                        g2.drawImage(waterfallSprite[waterfallAniIndex], screenX, screenY, Game.TILE_SIZE, Game.TILE_SIZE, null);
                    } else {
                        g2.drawImage(levelSprites[tileNum], screenX, screenY, null);
                    }
                    g2.drawImage(levelSprites[tileNum2], screenX, screenY, null);


                }
            }
        }
    }

    private static boolean IsTileVisible(int screenX, int screenY, int screenWidth, int screenHeight, int tileSize) {
        return screenX > -tileSize &&
                screenX < screenWidth &&
                screenY > -tileSize &&
                screenY < screenHeight;
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
