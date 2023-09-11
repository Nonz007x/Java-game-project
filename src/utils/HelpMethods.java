package utils;

import java.awt.*;

import main.Game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class HelpMethods {

    private HelpMethods() {
        throw new AssertionError("This class should not be instantiated.");
    }

    public static BufferedImage ScaleImage(BufferedImage originalImage, int width, int height) {
        if (originalImage == null) {
            throw new IllegalArgumentException("Input image is null");
        }

        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Invalid dimensions: width=" + width + ", height=" + height);
        }

        BufferedImage scaledImage = new BufferedImage(width, height, originalImage.getType());

        Graphics2D g2 = null;
        try {
            g2 = scaledImage.createGraphics();
            g2.drawImage(originalImage, 0, 0, width, height, null);
        } finally {
            if (g2 != null) {
                g2.dispose();
            }
        }

        return scaledImage;
    }

    public static boolean CheckCollisionUp(int x, int y, int width, int xOffset, int yOffset, int[][] lvlData) {
        return IsSolid(x + xOffset, y + yOffset, lvlData) ||
                IsSolid(x + width + xOffset, y + yOffset, lvlData);
    }

    public static boolean CheckCollisionDown(int x, int y, int width, int height, int xOffset, int yOffset, int[][] lvlData) {
        return IsSolid(x + xOffset, y + height + yOffset, lvlData) ||
                IsSolid(x + width + xOffset, y + height + yOffset, lvlData);
    }

    public static boolean CheckCollisionLeft(int x, int y, int height, int xOffset, int yOffset, int[][] lvlData) {
        return IsSolid(x + xOffset - 4, y + yOffset + 4, lvlData) ||
                IsSolid(x + xOffset - 4, y + height + yOffset - 4, lvlData);
    }

    public static boolean CheckCollisionRight(int x, int y, int width, int height, int xOffset, int yOffset, int[][] lvlData) {
        return IsSolid(x + width + xOffset + 4, y + yOffset + 4, lvlData) ||
                IsSolid(x + width + xOffset + 4, y + height + yOffset - 4, lvlData);
    }

    public static boolean CanMoveHere(int x, int y, int width, int height, int[][] lvlData) {
        if (!IsSolid(x, y, lvlData))
            if (!IsSolid(x + width, y + height, lvlData))
                if (!IsSolid(x + width, y, lvlData))
                    return !IsSolid(x, y + height, lvlData);
        return false;
    }

    private static boolean IsSolid(int x, int y, int[][] lvlData) {
        int maxWidth = lvlData[0].length * Game.TILE_SIZE;
        int maxHeight = lvlData.length * Game.TILE_SIZE;
        if (x < 0 || x >= maxWidth)
            return true;
        if (y < 0 || y >= maxHeight)
            return true;
        int xIndex = x / Game.TILE_SIZE;
        int yIndex = y / Game.TILE_SIZE;

        return IsTileSolid(xIndex, yIndex, lvlData);
    }

    public static boolean IsTileSolid(int xTile, int yTile, int[][] lvlData) {
        int value = lvlData[yTile][xTile];

        return value == 283;
    }

    public static String loadProperty(String property, String filename) {
        Properties prop = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = loader.getResourceAsStream(filename);

        if (stream == null) {
            System.err.println("Failed to load resource: " + filename);
            return null;
        }

        try {
            prop.load(stream);
            return prop.getProperty(property);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
