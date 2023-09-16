package Level;

import java.awt.image.BufferedImage;

public class Tile {
    public Tile(BufferedImage image, boolean collision) {
        this.image = image;
        this.fullCollision = collision;
        this.leftCollision = collision;
        this.rightCollision = collision;
        this.topCollision =  collision;
        this.bottomCollision = collision;
    }

    public Tile(BufferedImage image, boolean leftCollision, boolean rightCollision, boolean topCollision, boolean bottomCollision) {
        this.image = image;
        this.fullCollision = false;
        this.leftCollision = leftCollision;
        this.rightCollision = rightCollision;
        this.topCollision = topCollision;
        this.bottomCollision = bottomCollision;
    }

    private final BufferedImage image;
    private final boolean fullCollision;
    private final boolean leftCollision;
    private final boolean rightCollision;
    private final boolean topCollision;
    private final boolean bottomCollision;

    public BufferedImage image() {
        return image;
    }

    public boolean hasFullCollision() {
        return fullCollision;
    }

    public boolean hasLeftCollision() {
        return leftCollision;
    }

    public boolean hasRightCollision() {
        return rightCollision;
    }

    public boolean hasTopCollision() {
        return topCollision;
    }

    public boolean hasBottomCollision() {
        return bottomCollision;
    }
}