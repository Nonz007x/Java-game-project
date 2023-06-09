package Tile;

import java.awt.image.BufferedImage;

public class Tile {

    public BufferedImage image;
    public boolean collision = false;

    public boolean isCollision() {
        return collision;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    public void setImage(BufferedImage scaledImage) {
        this.image = scaledImage;
    }
}
