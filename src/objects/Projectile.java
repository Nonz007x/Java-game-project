package objects;

import java.awt.geom.Rectangle2D;
import main.Game;
public class Projectile {
	private Rectangle2D.Float hitbox;
	private int speed;
	private int directionX;
	private int directionY;
	private boolean active = true;

	public Projectile(int x, int y, int speed, int directionX, int directionY) {
		this.speed = speed;
		this.directionX = directionX;
		this.directionY = directionY;
		hitbox = new Rectangle2D.Float(x, y, 10, 10);
	}

	public void updatePos() {
		// update velocity
		hitbox.y += directionY * speed;
		hitbox.x += directionX * speed;
	}
	public void setPos(int x, int y) {
		hitbox.x = x;
		hitbox.y = y;
	}

	public Rectangle2D.Float getHitbox() {
		return hitbox;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isActive() {
		return active;
	}

}
