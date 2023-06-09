package Tile;

import main.GamePanel;
import main.UtilityTools;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;

    public TileManager(GamePanel gp) {

        this.gp = gp;
        tile = new Tile[10];
        mapTileNum = new int[gp.maxWorldRow][gp.maxWorldCol];

        getTileImage();
        loadMap("/resources/maps/map01.txt");
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
            String resourcePath = "/resources/tiles/" + imagePath + ".png";
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

        int playerScreenX = gp.player.screenX;
        int playerScreenY = gp.player.screenY;
        int playerWorldX = gp.player.worldX;
        int playerWorldY = gp.player.worldY;

        int tileSize = gp.tileSize;
        int screenWidth = gp.screenWidth;
        int screenHeight = gp.screenHeight;

        for (int worldRow = 0; worldRow < gp.maxWorldRow; worldRow++) {
            int worldY = worldRow * tileSize;
            int screenY = worldY - playerWorldY + playerScreenY;

            for (int worldCol = 0; worldCol < gp.maxWorldCol; worldCol++) {
                int tileNum = mapTileNum[worldRow][worldCol];

                int worldX = worldCol * tileSize;
                int screenX = worldX - playerWorldX + playerScreenX;

                // Stop camera at the edge
                if (playerScreenX > playerWorldX) {
                    screenX = worldX;
                }
                if (playerScreenY > playerWorldY) {
                    screenY = worldY;
                }

                int rightOffset = screenWidth - playerScreenX;
                if (rightOffset > worldWidth - playerWorldX) {
                    screenX = screenWidth - (worldWidth - worldX);
                }

                int bottomOffset = screenHeight - playerScreenY;
                if (bottomOffset > worldHeight - playerWorldY) {
                    screenY = screenHeight - (worldHeight - worldY);
                }

                if (screenX > -tileSize &&
                        screenX < screenWidth &&
                        screenY > -tileSize &&
                        screenY < screenHeight) {
                    g2.drawImage(tile[tileNum].image, screenX, screenY, null);
                }
            }
        }
    }

    public int getTileNum(int row, int col) {
        return mapTileNum[row][col];
    }
}
