package objects;

import entities.Player;
import gamestates.Playing;
import Level.LevelManager;
import Level.Level;

import java.awt.*;
import java.util.ArrayList;

public class ObjectManager {
    private Playing playing;
    private Level currentLevel;

    private ArrayList<Projectile> projectiles = new ArrayList<>();

    public ObjectManager(Playing playing) {
        this.playing = playing;
        currentLevel = LevelManager.GetCurrentLevel();
        projectiles.add(new Projectile(200, 200, 1, -1, -1));
//            loadImgs();
    }

    public void update(int[][] collisionTile, Player player) {
        updateProjectiles(collisionTile, player);
    }

    public void draw(Graphics2D g) {
        drawProjectiles(g);
    }

    private void updateProjectiles(int[][] collisionTile, Player player) {
        for (Projectile p : projectiles) {
            if (p.isActive()) {
                p.updatePos();
                if (p.getHitbox().intersects(player.getHitbox())) {
                    p.setActive(false);
                }
            }
        }
    }

    private void drawProjectiles(Graphics g) {
        for (Projectile p : projectiles)
            if (p.isActive())
                g.drawRect((int) p.getHitbox().x, (int) p.getHitbox().y, 10, 10);
//                g.drawImage(cannonBallImg, (int) (p.getHitbox().x - xLvlOffset), (int) (p.getHitbox().y), CANNON_BALL_WIDTH, CANNON_BALL_HEIGHT, null);
    }
}
