package Tile;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {

    GamePanel gp;
    Tile[] tile;
    int[][] mapTileNum;

    public TileManager(GamePanel gp) {

        this.gp = gp;
        tile = new Tile[10];
        mapTileNum = new int[gp.maxWorldRow][gp.maxWorldCol];

        getTileImage();
        loadMap("/res/maps/map01.txt");
    }

    public void getTileImage() {

        try {
            tile[0] = new Tile() {{
                image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/tiles/Grass16x16.png")));
            }};
            tile[1] = new Tile() {{
                image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/tiles/missing_texture.png")));
            }};

        } catch (IOException e) {
            e.printStackTrace();
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
        int playerScreenX = gp.player.screenX;
        int playerScreenY = gp.player.screenY;
        int playerWorldX = gp.player.worldX;
        int playerWorldY = gp.player.worldY;

        int tileSize = gp.tileSize;
        int screenWidth = gp.screenWidth;
        int screenHeight = gp.screenHeight;
        boolean cameraLockX = playerWorldX < tileSize * 7.5;
        boolean cameraLockY = playerWorldY < tileSize * 5.5;
        boolean cameraLocked = cameraLockY || playerWorldX < tileSize * 7.5;

        if (!cameraLocked) {
            for (int worldRow = 0; worldRow < gp.maxWorldRow; worldRow++) {
                int worldY = worldRow * tileSize;
                int screenY = worldY - playerWorldY + playerScreenY;

                for (int worldCol = 0; worldCol < gp.maxWorldCol; worldCol++) {
                    int tileNum = mapTileNum[worldRow][worldCol];

                    int worldX = worldCol * tileSize;
                    int screenX = worldX - playerWorldX + playerScreenX;

                    if (screenX > -tileSize &&
                            screenX < screenWidth &&
                            screenY > -tileSize &&
                            screenY < screenHeight) {
                        g2.drawImage(tile[tileNum].image, screenX, screenY, tileSize, tileSize, null);
                    }
                }
            }
        } else {
            int col;
            int row = 0;
            int y = 0;
            int x = 0;
            for (; row < gp.maxWorldRow; row++, y += gp.tileSize) {

                int worldY = row * tileSize;
                int screenY = worldY - playerWorldY + playerScreenY;

                for (col = 0; col < gp.maxWorldCol; col++, x += gp.tileSize) {

                    int worldX = col * tileSize;
                    int screenX = worldX - playerWorldX + playerScreenX;
                    int tileNum = mapTileNum[row][col];
                    if (cameraLockX && cameraLockY) {
                        g2.drawImage(tile[tileNum].image, x, y, tileSize, tileSize, null);
                    }
                    else if (cameraLockY) {
                        g2.drawImage(tile[tileNum].image, screenX, y, tileSize, tileSize, null);
                    }
                    else if (cameraLockX) {
                        g2.drawImage(tile[tileNum].image, x, screenY, tileSize, tileSize, null);
                    }
                }
                x = 0;
            }
        }
    }
}
