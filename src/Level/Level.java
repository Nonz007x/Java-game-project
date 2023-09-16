package Level;

import entity.enemies.GrandPrix;
import main.Game;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

import static utils.HelpMethods.loadProperty;

public class Level {
    private static int lvlId = 0;
    private int[][][] lvlData;
    private final ArrayList<GrandPrix> grandPrixs = new ArrayList<>();
    private int worldRow, worldCol;
    private int maxTilesOffset;
    private int maxLvlOffsetX;

    public Level(String path) {
        lvlId++;
        loadLevel(path);
    }

    private void loadLevel(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            assert is != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int rowCount = 0;
            int colCount = 0;
            String line;

            //check columns
            while (!Objects.equals(line = br.readLine(), "end")) {
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
            lvlData = new int[2][worldRow][worldCol];


            br.close();
            is = getClass().getResourceAsStream(filePath);
            assert is != null;
            br = new BufferedReader(new InputStreamReader(is));

            // create map
            rowCount = 0;
            while (!Objects.equals(line = br.readLine(), "end")) {
                String[] numbers = line.split(" ");
                for (int col = 0; col < numbers.length; col++) {
                    int num = Integer.parseInt(numbers[col]);
                    lvlData[0][rowCount][col] = num;
                }

                rowCount++;
            }

            // create overlay
            rowCount = 0;
            while ((line = br.readLine()) != null) {
                String[] numbers = line.split(" ");
                for (int col = 0; col < numbers.length; col++) {
                    int num = Integer.parseInt(numbers[col]);
                    lvlData[1][rowCount][col] = num;
                }

                rowCount++;
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
            grandPrixs.add(new GrandPrix(200, 200));
        }

        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            assert is != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String line;

            for (int i = 0; i < worldRow+1; i++) {
                br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
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
        return lvlData[0][y][x];
    }
    public int getSpriteIndex(int layer, int x, int y) {
        return lvlData[layer][y][x];
    }

    public int[][][] getLevelData() {
        return lvlData;
    }

    public int getWorldRow() {
        return worldRow;
    }

    public int getWorldCol() {
        return worldCol;
    }
}
