package objects;

import entities.Player;
import gamestates.Playing;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public abstract class SuperObject {
    protected int x, y, objType;
    protected boolean isActive = true;
    protected Rectangle2D.Float hitBox;
    protected BufferedImage image = null;

    public SuperObject(int x, int y, int objType) {
        this.x = x;
        this.y = y;
        this.objType = objType;
    }

    public void initHitBox(int x, int y, int width, int height) {
        hitBox = new Rectangle2D.Float(x + this.x, y + this.y, width, height);
    }

    public boolean isCollidedWithPlayer(Player player) {
        return hitBox.intersects(player.getHitBox());
    }


    public void draw(Graphics2D g, int xOffset, int yOffset) {
        if (image != null) {
            g.drawImage(image, x + xOffset, y + yOffset, (int) hitBox.width, (int) hitBox.height, null);
        }
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
