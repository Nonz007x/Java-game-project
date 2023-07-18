package utils;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class LoadSave {
    public static final String PLAYER_SPRITES = " ";

    public static File[] getAllLevels() {
        URL url = LoadSave.class.getResource("/res/maps");
        File file = null;

        try {
            assert url != null;
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        File[] files = file.listFiles();
        File[] filesSorted = new File[files.length];

        for (int i = 0; i < filesSorted.length; i++) {
            for (File value : files) {
                if (value.getName().equals((i + 1) + ".txt")) {
                    filesSorted[i] = value;
                }
            }
        }

        return filesSorted;
    }

}
