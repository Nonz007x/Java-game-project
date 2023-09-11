package Level;

import main.Game;
import utils.HelpMethods;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class LevelManager {
    Game game;
    private final Tile[] tiles;
    private final ArrayList<Level> levels;

    private int lvlIndex = 0;

    public LevelManager(Game game) {
        this.game = game;
        tiles = new Tile[1440];

        levels = new ArrayList<>();
        initializeTileImage();
        buildAllLevels();
    }

    private void buildAllLevels() {
        String[] allLevels = LoadSave.GetAllLevels();
        for (String path : allLevels) {
            levels.add(new Level(path));
        }
    }

    private void initializeTileImage() {
        BufferedImage imageSet = LoadSave.GetSprite(LoadSave.LEVEL_SPRITE);
        int counter = 0;
        for (int i = 0; i < 36; i++) {
            for (int j = 0; j < 40; j++) {
                BufferedImage tileImage = imageSet.getSubimage(16 * j, 16 * i, 16, 16);
                BufferedImage scaledImage = HelpMethods.ScaleImage(tileImage, Game.TILE_SIZE, Game.TILE_SIZE);
                tiles[counter] = new Tile(scaledImage, false);
                counter++;
            }
        }
    }

    public void render(Graphics2D g2) {

        int worldWidth = getCurrentLevel().getWorldCol() * Game.TILE_SIZE;
        int worldHeight = getCurrentLevel().getWorldRow() * Game.TILE_SIZE;

        int playerScreenX = game.getPlaying().getPlayer().getScreenX();
        int playerScreenY = game.getPlaying().getPlayer().getScreenY();
        int playerWorldX = game.getPlaying().getPlayer().getX();
        int playerWorldY = game.getPlaying().getPlayer().getY();

        int tileSize = Game.TILE_SIZE;
        int screenWidth = Game.GAME_WIDTH;
        int screenHeight = Game.GAME_HEIGHT;

        for (int worldRow = 0; worldRow < getCurrentLevel().getWorldRow(); worldRow++) {
            int tileY = worldRow * tileSize;
            int screenY = tileY - playerWorldY + playerScreenY;

            for (int worldCol = 0; worldCol < getCurrentLevel().getWorldCol(); worldCol++) {
                int tileNum = getCurrentLevel().getSpriteIndex(worldCol, worldRow);

                int tileX = worldCol * tileSize;
                int screenX = tileX - playerWorldX + playerScreenX;

                // Stop camera at the edge
                if (playerScreenX > playerWorldX)
                    screenX = tileX;

                if (playerScreenY > playerWorldY)
                    screenY = tileY;


                int rightOffset = screenWidth - playerScreenX;
                if (rightOffset > worldWidth - playerWorldX)
                    screenX = screenWidth - (worldWidth - tileX);


                int bottomOffset = screenHeight - playerScreenY;
                if (bottomOffset > worldHeight - playerWorldY)
                    screenY = screenHeight - (worldHeight - tileY);


                if (isTileVisible(screenX, screenY, screenWidth, screenHeight, tileSize)) {
                    g2.drawImage(tiles[tileNum].image(), screenX, screenY, null);
                }
            }
        }
    }

    private boolean isTileVisible(int screenX, int screenY, int screenWidth, int screenHeight, int tileSize) {
        return screenX > -tileSize &&
                screenX < screenWidth &&
                screenY > -tileSize &&
                screenY < screenHeight;
    }

    public Level getCurrentLevel() {
        return levels.get(lvlIndex);
    }

    public int getLevelIndex() {
        return lvlIndex;
    }

    public void setLevelIndex(int lvlIndex) {
        this.lvlIndex = lvlIndex;
    }

    public void toggleLevel() {
        lvlIndex = (lvlIndex + 1) % levels.size();
        game.getPlaying().getPlayer().loadLvlData(getCurrentLevel().getLevelData());
    }
}
