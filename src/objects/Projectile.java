package objects;

import java.awt.geom.Rectangle2D;
import main.Game;
public class Projectile {
	private Rectangle2D.Float hitbox;
	private final int TICKS_TO_LIVE = 600;
	private int current_tick;
	private int speed;
	private double directionX;
	private double directionY;
	private boolean active = true;

	public Projectile(int x, int y, int speed, double directionX, double directionY) {
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

	public boolean isWithinTickLimit() {
		current_tick++;
		return current_tick < TICKS_TO_LIVE;
	}

}
