package level;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Boss;
import entities.Enemy;
import entities.bosses.Crabulon;
import entities.bosses.Slime;
import entities.bosses.UltraGrandPrix;
import entities.enemies.GrandPrix;
import entities.enemies.MonPiramid;
import main.Game;

import java.io.*;
import java.util.ArrayList;


public class Level {
    private static int lvlId = 0;
    private int[][][] lvlData;
    private int[][] collisionTile;

    private final ArrayList<Enemy> enemies = new ArrayList<>();
    private final ArrayList<Boss> bosses = new ArrayList<>();
    private int worldRow, worldCol;

    public Level(String path) {
        lvlId++;
        loadLevel(path);
    }

    private void loadLevel(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);

            if (is == null) {
                System.out.println("File : " + filePath + " does not exist.");
                return;
            }

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(is);

            int rowCount = jsonNode.get("height").asInt();
            int colCount = jsonNode.get("width").asInt();
            JsonNode background = jsonNode.get("layers").get(0).get("data");
            JsonNode foreground = jsonNode.get("layers").get(1).get("data");
            JsonNode collision = jsonNode.get("layers").get(2).get("data");
            JsonNode monster = jsonNode.get("layers").get(3).get("data");

            worldRow = rowCount;
            worldCol = colCount;

            lvlData = new int[2][rowCount][colCount];
            collisionTile = new int[rowCount][colCount];
            int[][] tempEnemies = new int[rowCount][colCount];

            int dataIndex = 0;

            for (int row = 0; row < rowCount; row++) {
                for (int col = 0; col < colCount; col++) {
                    lvlData[0][row][col] = background.get(dataIndex).asInt();
                    lvlData[1][row][col] = foreground.get(dataIndex).asInt();
                    collisionTile[row][col] = collision.get(dataIndex).asInt();
                    tempEnemies[row][col] = monster.get(dataIndex).asInt();
                    dataIndex++;
                }
            }
            loadEnemies(tempEnemies);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadEnemies(int[][] enemies) {
        //TODO
        if (enemies == null)
            return;

        for (int y = 0; y < enemies.length; y++) {
            for (int x = 0; x < enemies[0].length; x++) {
                int coordinate = enemies[y][x];
                switch (coordinate) {
                    case 1 -> this.enemies.add(new GrandPrix(x * Game.TILE_SIZE, y * Game.TILE_SIZE));
                    case 2 -> this.bosses.add(new Crabulon(x * Game.TILE_SIZE, y * Game.TILE_SIZE));
                    case 3 -> this.enemies.add(new MonPiramid(x * Game.TILE_SIZE, y * Game.TILE_SIZE));
                    case 4 -> this.bosses.add(new UltraGrandPrix(x * Game.TILE_SIZE, y * Game.TILE_SIZE));
                    case 5 -> this.bosses.add(new Slime(x * Game.TILE_SIZE, y * Game.TILE_SIZE));
                }
            }
        }
        // How to read from .properties extension
//        int isHaunted = Integer.parseInt(loadProperty("grandprix", "res/maps/level_" + lvlId + ".properties"));
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
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
