package utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.regex.Pattern;

public class LoadSave {
    public static final String PLAYER_SPRITES = "player_sprites.png";
    public static final String LEVEL_SPRITE = "tiles.png";
    public static final String GRANDPRIX = "grand_prix.png";
    public static final String CRABULON = "crabulon/Crabulon.png";

    public static String[] GetAllLevels() {
        String directoryPath = "/maps/";
        InputStream inputStream = LoadSave.class.getResourceAsStream(directoryPath);

        if (inputStream != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
//                return reader.lines()
//                        .filter(fileName -> Pattern.matches("level_\\d\\.json$", fileName))
//                        .peek(fileName -> System.out.println("Found file: " + fileName))
//                        .map(fileName -> "/maps/" + fileName)
//                        .toArray(String[]::new);
                return new String[]{"/maps/level_1.json", "/maps/level_2.json", "/maps/level_3.json", "/maps/level_4.json"};
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("The specified directory " + directoryPath + " does not exist.");
        }

        return new String[0];
    }

    public static BufferedImage GetSprite(String fileName) {
        BufferedImage img = null;
        InputStream is = LoadSave.class.getResourceAsStream("/res/sprites/" + fileName);
        try {
            if (is != null) {
                img = ImageIO.read(is);
            } else {
                InputStream placeholderStream = LoadSave.class.getResourceAsStream("/res/sprites/placeholder.png");
                if (placeholderStream != null) {
                    img = ImageIO.read(placeholderStream);
                } else {
                    System.err.println("Placeholder image not found.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }

    public static BufferedImage[][] GetImagesFromSpriteSheet(String fileName, int spriteWidth, int spriteHeight, int row, int col) {
        BufferedImage sheet = LoadSave.GetSprite(fileName);

        BufferedImage[][] sprites = new BufferedImage[row][col];

        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++) {
                sprites[i][j] = sheet.getSubimage(j * spriteWidth, i * spriteHeight, spriteWidth, spriteHeight);
            }
        return sprites;
    }

    public static BufferedImage[] GetImagesFromSpriteSheet(String fileName, int spriteWidth, int spriteHeight, int size, boolean isColumn) {
        BufferedImage sheet = LoadSave.GetSprite(fileName);

        BufferedImage[] sprites = new BufferedImage[size];
        if (isColumn) {
            for (int i = 0; i < size; i++)
                sprites[i] = sheet.getSubimage(0, i * spriteHeight, spriteWidth, spriteHeight);
        } else {
            for (int i = 0; i < size; i++)
                sprites[i] = sheet.getSubimage(i * spriteWidth, 0, spriteWidth, spriteHeight);
        }
        return sprites;
    }
}
