package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UtilityTools {


    public BufferedImage scaleImage(BufferedImage originalImage, int width, int height) {
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
}
