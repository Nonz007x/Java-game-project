package Level;

import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utils.HelpMethods.ScaleImage;

public class LevelManager {
    Game game;

    private static final BufferedImage[] levelSprites = new BufferedImage[2400];
    private static final ArrayList<Level> levels = new ArrayList<>();
    private static int lvlIndex = 0;

    public LevelManager(Game game) {
        this.game = game;

        InitializeTileImage();
        BuildAllLevels();
    }

    private static void BuildAllLevels() {
        String[] allLevels = LoadSave.GetAllLevels();
        for (String path : allLevels) {
            levels.add(new Level(path));
        }
    }

    private static void InitializeTileImage() {
        BufferedImage imageSet = LoadSave.GetSprite(LoadSave.LEVEL_SPRITE);
        int counter = 0;

        for (int row = 0;  row < 60;  row++) {
            for (int col = 0; col < 40; col++) {
                BufferedImage tileImage = imageSet.getSubimage(16 * col, 16 *  row, 16, 16);
                BufferedImage scaledImage = ScaleImage(tileImage, Game.TILE_SIZE, Game.TILE_SIZE);
                levelSprites[counter] = scaledImage;
                counter++;
            }
        }
    }

    public void render(Graphics2D g2, int playerX, int playerY, int screenPosX, int screenPosY) {

        int worldWidth = GetCurrentLevel().getWorldCol() * Game.TILE_SIZE;
        int worldHeight = GetCurrentLevel().getWorldRow() * Game.TILE_SIZE;

        int tileSize = Game.TILE_SIZE;
        int screenWidth = Game.GAME_WIDTH;
        int screenHeight = Game.GAME_HEIGHT;

        for (int worldRow = 0; worldRow < GetCurrentLevel().getWorldRow(); worldRow++) {
            int tileY = worldRow * tileSize;
            int screenY = tileY - playerY + screenPosY;

            for (int worldCol = 0; worldCol < GetCurrentLevel().getWorldCol(); worldCol++) {
                int tileNum = GetCurrentLevel().getSpriteIndex(worldCol, worldRow);
                int tileNum2 = GetCurrentLevel().getSpriteIndex(1,worldCol, worldRow);

                int tileX = worldCol * tileSize;
                int screenX = tileX - playerX + screenPosX;

                // Stop camera at the edge
                if (screenPosX > playerX)
                    screenX = tileX;

                if (screenPosY > playerY)
                    screenY = tileY;


                int rightOffset = screenWidth - screenPosX;
                if (rightOffset > worldWidth - playerX)
                    screenX = screenWidth - (worldWidth - tileX);


                int bottomOffset = screenHeight - screenPosY;
                if (bottomOffset > worldHeight - playerY)
                    screenY = screenHeight - (worldHeight - tileY);


                if (IsTileVisible(screenX, screenY, screenWidth, screenHeight, tileSize)) {
                    g2.drawImage(levelSprites[tileNum], screenX, screenY, null);
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
