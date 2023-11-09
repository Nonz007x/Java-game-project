package ui;

import gamestates.Arena;
import gamestates.Gamestate;
import gamestates.Playing;
import gamestates.State;
import level.LevelManager;
import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.font.TextAttribute;
import java.util.Collections;

import static utils.Constants.UI.F_DEFAULT;
import static utils.Constants.UI.OVERLAY_BG_COLOR;

public class GameOverOverlay {
    FontMetrics fm;

    private final Playing playing;
    private int selectedChoice = 0;

    public GameOverOverlay(Playing playing) {
        this.playing = playing;
    }

    private void submit() {
        switch (selectedChoice) {
            case 0 -> {
                if (Gamestate.state == Gamestate.PLAYING)
                    Playing.newGame();
                else Arena.resetAll();
            }
            case 1 -> playing.toMainMenu();
        }
    }

    private void circleChoice() {
        selectedChoice ^= 1;
    }

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        switch (code) {
            case KeyEvent.VK_S, KeyEvent.VK_W -> circleChoice();
            case KeyEvent.VK_E -> submit();
        }
    }

    public void draw(Graphics g) {
        g.setColor(OVERLAY_BG_COLOR);
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        Font baseFont = new Font("Dialog", Font.PLAIN, 80);
        g.setFont(baseFont);

        String text = "YOU DIED";
        FontMetrics fm = g.getFontMetrics(baseFont);
        int centerX = Game.GAME_WIDTH / 2;
        int x = centerX - fm.stringWidth(text) / 2;

        g.setColor(Color.RED);
        g.drawString(text, x, Game.GAME_HEIGHT / 2);
        g.setFont(F_DEFAULT);

        String[] options = {"respawn", "rage quit"};

        for (int i = 0; i < options.length; i++) {
            g.setColor(Color.WHITE);
            Font optionFont = (i == selectedChoice) ?
                    new Font("Dialog", Font.BOLD, 24).deriveFont(Collections.singletonMap(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON)) :
                    F_DEFAULT;
            g.setFont(optionFont);

            fm = g.getFontMetrics(optionFont);
            int optionX = centerX - fm.stringWidth(options[i]) / 2;
            int y = Game.GAME_HEIGHT / 2 + 150 + i * 100;
            g.drawString(options[i], optionX, y);
        }
    }


}
