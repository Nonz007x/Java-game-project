package Level;

import java.io.*;


public class Level {
    private int[][] lvlData;
    private int worldRow, worldCol;

    public Level(String path) {
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
    }

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
