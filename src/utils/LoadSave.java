package utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.regex.Pattern;

public class LoadSave {
    public static final String PLAYER_SPRITES = "player_sprites.png";
    public static final String LEVEL_SPRITE = "overworld.png";
    public static final String HAUNTING_GHOSTS = "hauntingghosts.png";
    public static final String GRANDPRIX = "grand_prix.png";
    public static final String CRABULON = "crabulon/Crabulon.png";

    public static String[] GetAllLevels() {
        String directoryPath = "/maps/";
        InputStream inputStream = LoadSave.class.getResourceAsStream(directoryPath);

        if (inputStream != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                return reader.lines()
                        .filter(fileName -> Pattern.matches("level_\\d\\.json$", fileName))
                        .peek(fileName  -> System.out.println("Found file: " + fileName))
                        .map(fileName -> "/maps/" + fileName)
                        .toArray(String[]::new);
//                return new String[]{"/maps/level_1.json", "/maps/level_2.json", "/maps/level_3.json"};
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("The specified directory "  + directoryPath + " does not exist.");
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
}
