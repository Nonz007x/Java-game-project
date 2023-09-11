package Level;

import entity.enemies.GrandPrix;
import main.Game;

import java.io.*;
import java.util.ArrayList;

import static utils.HelpMethods.loadProperty;

public class Level {
    private static int lvlId = 0;
    private int[][] lvlData;
    private final ArrayList<GrandPrix> grandPrixs = new ArrayList<>();
    private int worldRow, worldCol;
    private int maxTilesOffset;
    private int maxLvlOffsetX;

    public Level(String path) {
        lvlId++;
        loadLevel(path);
        calcLvlOffsets();
    }

    private void loadLevel(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            assert is != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int rowCount = 0;
            int colCount = 0;
            String line;

            while ((line = br.readLine()) != null) {
                String[] numbers = line.split(" ");
                if (colCount == 0) {
                    colCount = numbers.length;
                } else if (colCount != numbers.length) {
                    throw new IllegalArgumentException("Inconsistent number of columns in the level data.");
                }
                rowCount++;
            }

            worldRow = rowCount;
            worldCol = colCount;
            lvlData = new int[worldRow][worldCol];

            br.close();
            is = getClass().getResourceAsStream(filePath);
            assert is != null;
            br = new BufferedReader(new InputStreamReader(is));

            rowCount = 0;
            while ((line = br.readLine()) != null) {
                String[] numbers = line.split(" ");
                for (int col = 0; col < numbers.length; col++) {
                    int num = Integer.parseInt(numbers[col]);
                    lvlData[rowCount][col] = num;
                }

                rowCount++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        loadEnemies();
    }

    private void loadEnemies() {
        int isHaunted = Integer.parseInt(loadProperty("grandprix", "res/maps/level_" + lvlId + ".properties"));
        System.out.println(isHaunted);
        if (isHaunted == 1) {
            System.out.println("create grandprix");
            grandPrixs.add(new GrandPrix(96, 96));
        }
    }

    private void calcLvlOffsets() {
        maxTilesOffset = getWorldCol() - Game.TILE_IN_WIDTH;
        maxLvlOffsetX = Game.TILE_SIZE * maxTilesOffset;
    }

    public ArrayList<GrandPrix> getHauntingGhost() {
        return grandPrixs;
    }

    public int getLvlOffset() {
        return maxLvlOffsetX;
    };
    public int getSpriteIndex(int x, int y) {
        return lvlData[y][x];
    }

    public int[][] getLevelData() {
        return lvlData;
    }

    public int getWorldRow() {
        return worldRow;
    }

    public int getWorldCol() {
        return worldCol;
    }
}
