package ui;

import entities.Boss;
import entities.Player;
import gamestates.Playing;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;

public class Hud {
    private static final BufferedImage potionImage;

    static {
        potionImage = LoadSave.GetSprite("40degree.png");
    }

    private Playing playing;

    public Hud(Playing playing) {
        this.playing = playing;
    }

    private void drawBossHP(Graphics2D g2) {
        ArrayList<Boss> bosses = playing.getBossManager().getBosses();
        if (!bosses.isEmpty()) {
            int startX = GAME_WIDTH / 4;
            int startY = GAME_HEIGHT - 100;
            int endX = GAME_WIDTH / 2;
            int endY = GAME_HEIGHT - 75;
            int textX = GAME_WIDTH / 2 - 50;
            int textY = startY + 20;
            g2.setColor(Color.BLACK);
            g2.fillRect(startX - 5, startY - 5, GAME_WIDTH / 2 + 10, 35);
            for (Boss boss : bosses) {
                String text = boss.getCurrentHealth() + " / " + boss.getMaxHealth();
                GradientPaint gradient = new GradientPaint(new Point(startX, startY),
                        new Color(255, 0, 0),
                        new Point(endX, endY),
                        new Color(0, 255, 0),
                        false);
                g2.setPaint(gradient);
                g2.fillRect(startX, startY, (int) (boss.getHealthPercentage() * (GAME_WIDTH / 2)), 25);

                g2.setFont(new Font("Arial", Font.BOLD, 20));
                g2.setColor(Color.WHITE);
                g2.drawString(text, textX, textY);
            }
        }
    }

    private void drawPlayerHP(Graphics2D g2) {
        int startX = 10;
        int startY = 10;
        int endX = 100;
        int endY = 35;
        int textX = 10;
        int textY = startY + 20;
        Player player = playing.getPlayer();
        String text = player.getCurrentHealth() + " / " + player.getMaxHealth();

        g2.setColor(Color.BLACK);
        g2.fillRect(startX - 5, startY - 5, 310, 35);

        GradientPaint gradient = new GradientPaint(new Point(startX, startY),
                new Color(255, 0, 0),
                new Point(endX, endY),
                new Color(0, 255, 0),
                false);
        g2.setPaint(gradient);
        g2.fillRect(startX, startY, (int) (player.getHealthPercentage() * 300), 25);

        g2.setFont(new Font("Arial", Font.BOLD, 20));
        g2.setColor(Color.WHITE);
        g2.drawString(text, textX, textY);
    }

    private void drawPotionAmount(Graphics2D g2) {
        int potionAmount = playing.getPlayer().getPotionAmount();
        g2.drawImage(potionImage, 10, 40, 64, 64, null);
        g2.setColor(Color.WHITE);
        g2.drawString(String.valueOf(potionAmount), 70, 90);
    }

    public void draw(Graphics2D g2) {
        drawBossHP(g2);
        drawPlayerHP(g2);
        drawPotionAmount(g2);
    }
}
