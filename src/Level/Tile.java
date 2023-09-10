package Level;

import java.awt.image.BufferedImage;

public class Tile {

    private BufferedImage image;
    public boolean collision = false;

    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    public void setImage(BufferedImage scaledImage) {
        this.image = scaledImage;
    }

    public BufferedImage getImage() {
        return  image;
    }
}
