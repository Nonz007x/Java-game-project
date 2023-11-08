package objects;

import entities.Player;
import gamestates.Playing;

import java.awt.geom.Rectangle2D;

public abstract class SuperObject {
    protected int x, y, objType;
    protected boolean isActive;
    protected Rectangle2D.Float hitBox;

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

}
