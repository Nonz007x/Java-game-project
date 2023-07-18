package tile;

import entity.Player;
import main.GamePanel;
import main.UtilityTools;
import utils.LoadSave;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;

public class TileManager {

    GamePanel gp;
    Player player;
    public Tile[] tile;
    private final int[][] mapTileNum;

    public TileManager(GamePanel gp, Player player) {

        this.gp = gp;
        this.player = player;
        tile = new Tile[10];
        mapTileNum = new int[gp.maxWorldRow][gp.maxWorldCol];
        System.out.println(Arrays.toString(LoadSave.getAllLevels()));

        getTileImage();
        // FIX THIS
        loadMap("/res/maps/1.txt");
    }

    public void getTileImage() {
        try {

            initializeTile(0, "Grass16x16", false);
            initializeTile(1, "missing_texture", true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeTile(int index , String imagePath, boolean collision) throws IOException {

        UtilityTools utilityTools = new UtilityTools();
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
            BufferedImage scaledImage = utilityTools.scaleImage(image, gp.tileSize, gp.tileSize);

            newTile.setImage(scaledImage);
            newTile.setCollision(collision);

            tile[index] = newTile;

        } catch (IOException e) {
            throw new IOException("Failed to load image: " + imagePath, e);
        }

    }
    public void loadMap(String filePath) {

        try {

            InputStream is = getClass().getResourceAsStream(filePath);
            assert is != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(is));


            for (int row = 0; row < gp.maxWorldRow; row++) {
                String line = br.readLine();
                String[] numbers = line.split(" ");

                for (int col = 0; col < gp.maxWorldCol; col++) {
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[row][col] = num;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void render(Graphics2D g2) {
        // Rendering

        int worldWidth = gp.worldWidth;
        int worldHeight = gp.worldHeight;

        int playerScreenX = player.screenX;
        int playerScreenY = player.screenY;
        int playerWorldX = player.getX();
        int playerWorldY = player.getY();

        int tileSize = gp.tileSize;
        int screenWidth = gp.screenWidth;
        int screenHeight = gp.screenHeight;

        for (int worldRow = 0; worldRow < gp.maxWorldRow; worldRow++) {
            int tileY = worldRow * tileSize;
            int screenY = tileY - playerWorldY + playerScreenY;

            for (int worldCol = 0; worldCol < gp.maxWorldCol; worldCol++) {
                int tileNum = mapTileNum[worldRow][worldCol];

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
                    g2.drawImage(tile[tileNum].image, screenX, screenY, null);
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

    public int getTileNum(int row, int col) {
        return mapTileNum[row][col];
    }
}
