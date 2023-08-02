package utils;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;

public class LoadSave {
    public static final String PLAYER_SPRITES = " ";

    public static String[] GetAllLevels() {
        URL url = LoadSave.class.getResource("/res/maps");
        File file = null;

        try {
            assert url != null;
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        assert file != null;
        File[] files = file.listFiles();
        assert files != null;

        String[] filesSorted = new String[files.length];
        for (int i = 0; i < filesSorted.length; i++) {
            for (File value : files) {
                if (value.getName().equals((i + 1) + ".txt")) {
                    filesSorted[i] = "/res/maps/" + value.getName(); // Store the filename with "/res/maps/" prefix
                    break;
                }
            }
        }

        return filesSorted;
    }

}
