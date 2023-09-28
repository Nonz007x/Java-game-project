package Level;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Boss;
import entities.Enemy;
import entities.bosses.Crabulon;
import entities.enemies.GrandPrix;

import java.io.*;
import java.util.ArrayList;

import static utils.Constants.BossConstants.*;
import static utils.HelpMethods.loadProperty;

public class Level {
    private static int lvlId = 0;
    private int[][][] lvlData;
    private int[][] collisionTile;

    private final ArrayList<Enemy> grandPrixs = new ArrayList<>();
    private final ArrayList<Boss> bosses = new ArrayList<>();
    private int worldRow, worldCol;

    public Level(String path) {
        lvlId++;
        loadLevel(path);
    }

    private void loadLevel(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            assert is != null;
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(is);

            int rowCount = jsonNode.get("height").asInt();
            int colCount = jsonNode.get("width").asInt();
            JsonNode background = jsonNode.get("layers").get(0).get("data");
            JsonNode foreground = jsonNode.get("layers").get(1).get("data");
            JsonNode collision = jsonNode.get("layers").get(2).get("data");

            worldRow = rowCount;
            worldCol = colCount;

            lvlData = new int[2][rowCount][colCount];
            collisionTile = new int[rowCount][colCount];

            int dataIndex = 0;

            for (int row = 0; row < rowCount; row++) {
                for (int col = 0; col < colCount; col++) {
                    lvlData[0][row][col] = background.get(dataIndex).asInt();
                    lvlData[1][row][col] = foreground.get(dataIndex).asInt();
                    collisionTile[row][col] = collision.get(dataIndex).asInt();
                    dataIndex++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        loadEnemies(filePath);
    }

    private void loadEnemies(String filePath) {
        //TODO
        int isHaunted = Integer.parseInt(loadProperty("grandprix", "res/maps/level_" + lvlId + ".properties"));
        if (isHaunted == 1) {
            grandPrixs.add(new GrandPrix(300, 300));
        }

        bosses.add(new Crabulon(10, 100, CRABULON_WIDTH_DEFAULT, CRABULON_HEIGHT_DEFAULT));
    }

    public ArrayList<Enemy> getEnemies() {
        return grandPrixs;
    }

    public ArrayList<Boss> getBosses() {
        return bosses;
    }

    public int getSpriteIndex(int x, int y) {
        return lvlData[0][y][x];
    }

    public int getSpriteIndex(int layer, int x, int y) {
        return lvlData[layer][y][x];
    }

    public int[][][] getLevelData() {
        return lvlData;
    }

    public int[][] getCollisionTile() {
        return collisionTile;
    }

    public int getWorldRow() {
        return worldRow;
    }

    public int getWorldCol() {
        return worldCol;
    }
}
