package utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

public class LoadSave {
    public static final String PLAYER_SPRITES = "player_sprites.png";
    public static final String LEVEL_SPRITE = "overworld.png";
    public static final String HAUNTING_GHOSTS = "hauntingghosts.png";

    public static String[] GetAllLevels() {
        String PATH = "/res/maps/";
        URL url = LoadSave.class.getResource(PATH);
        File file = null;

        try {
            assert url != null;
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        assert file != null;
        File[] files = file.listFiles((dir, name) -> name.toLowerCase().endsWith(".json"));

        assert files != null;

        String[] filesSorted = new String[files.length];
        for (int i = 0; i < filesSorted.length; i++) {
            StringBuilder filePathBuilder = new StringBuilder(PATH).append("level_").append(i + 1).append(".json");
            filesSorted[i] = filePathBuilder.toString();
        }
        return filesSorted;
    }


    public static BufferedImage GetSprite(String fileName) {
        BufferedImage img = null;
        InputStream is = LoadSave.class.getResourceAsStream("/res/sprites/" + fileName);
        try {
            img = ImageIO.read(is);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }

}
