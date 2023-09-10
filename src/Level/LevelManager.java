package Level;

import main.Game;
import utils.HelpMethods;
import utils.LoadSave;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class LevelManager {
    Game game;
    private final Tile[] tiles;
    private final ArrayList<Level> levels;

    private int lvlIndex = 0;

    public LevelManager(Game game) {
        this.game = game;
        tiles = new Tile[10];

        levels = new ArrayList<>();
        initializeTileImage();
        buildAllLevels();
    }

    private void buildAllLevels() {
        String[] allLevels = LoadSave.GetAllLevels();
        for (String path : allLevels)
            levels.add(new Level(path));
    }

    private void initializeTileImage() {
        try {

            initializeTile(0, "Grass16x16", false);
            initializeTile(1, "missing_texture", true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeTile(int index , String imagePath, boolean collision) throws IOException  {
        try {
            Tile newTile = new Tile();

            // Load the image resource
            String resourcePath = "/res/tiles/" + imagePath + ".png";
            InputStream resourceStream = getClass().getResourceAsStream(resourcePath);

            if (resourceStream == null) {
                throw new FileNotFoundException("Missing texture resource: " + resourcePath);
            }

            BufferedImage image = ImageIO.read(resourceStream);

            // Scale the image
            BufferedImage scaledImage = HelpMethods.ScaleImage(image, Game.TILE_SIZE, Game.TILE_SIZE);

            newTile.setImage(scaledImage);
            newTile.setCollision(collision);

            tiles[index] = newTile;

        } catch (IOException e) {
            throw new IOException("Failed to load image: " + imagePath, e);
        }

    }

    public void render(Graphics2D g2) {

        int worldWidth = getCurrentLevel().getWorldCol() * 48;
        int worldHeight = getCurrentLevel().getWorldRow() * 48;

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
                int tileNum = getCurrentLevel().getSpriteIndex(worldCol,worldRow);

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
                    g2.drawImage(tiles[tileNum].getImage(), screenX, screenY, null);
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
