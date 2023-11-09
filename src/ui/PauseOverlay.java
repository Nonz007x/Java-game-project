package ui;

import gamestates.Arena;
import gamestates.Gamestate;
import gamestates.Playing;
import gamestates.State;
import level.LevelManager;
import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;

public class PauseOverlay {
    private final Playing playing;
    private int selectedChoice = 0;
    private final Color backgroundColor = new Color(0, 0, 0, 150);

    private static final int MAX_CHOICE = 4;

    public PauseOverlay(Playing playing) {
        this.playing = playing;
    }

    private void submit() {
        switch (selectedChoice) {
            case 0 -> playing.unpauseGame();
            case 1 -> {
                if (Gamestate.state == Gamestate.PLAYING)
                    Playing.newGame();
                else Arena.resetAll();
            }
            case 2 -> {
            }
            case 3 -> playing.toMainMenu();
        }
    }

    private void circleChoice(int step) {
        selectedChoice = selectedChoice + step >= 0 ? (selectedChoice + step) % 4 : 3;
    }

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        switch (code) {
            case KeyEvent.VK_S -> circleChoice(1);
            case KeyEvent.VK_W -> circleChoice(-1);
            case KeyEvent.VK_E -> submit();
        }
    }

    public void draw(Graphics g) {
        g.setColor(backgroundColor);
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
        g.setColor(Color.WHITE);
        g.drawString("Pause", 1, 25);
        g.drawString("Resume", 1, 100);
        g.drawString("RESTART", 1, 150);
        g.drawString("Options", 1, 200);
        g.drawString("Quit", 1, 250);
        g.drawString(String.valueOf(selectedChoice), Game.GAME_WIDTH / 2, 125);

    }
}
