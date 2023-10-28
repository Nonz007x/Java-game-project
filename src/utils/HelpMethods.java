package utils;

import java.awt.*;

import main.Game;
import objects.Projectile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;

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

    public static boolean CheckCollisionUp(int x, int y, int width, int[][] collisionTile) {
        return IsSolid(x, y, collisionTile) ||
                IsSolid(x + width, y, collisionTile);
    }

    public static boolean CheckCollisionDown(int x, int y, int width, int height, int[][] collisionTile) {
        return IsSolid(x, y + height, collisionTile) ||
                IsSolid(x + width, y + height, collisionTile);
    }

    public static boolean CheckCollisionLeft(int x, int y, int height, int[][] collisionTile) {
        return IsSolid(x - 4, y + 4, collisionTile) ||
                IsSolid(x - 4, y + height - 4, collisionTile);
    }

    public static boolean CheckCollisionRight(int x, int y, int width, int height, int[][] collisionTile) {
        return IsSolid(x + width + 4, y + +4, collisionTile) ||
                IsSolid(x + width + 4, y + height - 4, collisionTile);
    }

    public static boolean IsProjectileHittingLevel(Projectile p, int[][] collisionTile) {
        return IsSolid((int) (p.getHitbox().x + p.getHitbox().width / 2), (int) (p.getHitbox().y + p.getHitbox().height / 2), collisionTile);
    }

    public static boolean CanMoveHere(int x, int y, int width, int height, int[][] collisionTile) {
        if (!IsSolid(x, y, collisionTile))
            if (!IsSolid(x + width, y + height, collisionTile))
                if (!IsSolid(x + width, y, collisionTile))
                    return !IsSolid(x, y + height, collisionTile);
        return false;
    }

    private static boolean IsSolid(int x, int y, int[][] collisionTile) {
        int maxWidth = collisionTile[0].length * Game.TILE_SIZE;
        int maxHeight = collisionTile.length * Game.TILE_SIZE;
        if (x < 0 || x >= maxWidth)
            return true;
        if (y < 0 || y >= maxHeight)
            return true;
        int xIndex = x / Game.TILE_SIZE;
        int yIndex = y / Game.TILE_SIZE;

        int value = collisionTile[yIndex][xIndex];
        return value == 1;
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

    public static int RandomDirection() {
        Random random = new Random();
        return random.nextBoolean() ? 1 : -1;
    }

    public static int RandomNumber(int lowerBound, int upperBound) {
        return new Random().nextInt(upperBound + 1 - lowerBound) + lowerBound;
    }
}
