package ui;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;

import static utils.Constants.Menu.MAX_CHOICE;

public class PauseOverlay {
    private final Playing playing;
    private int selectedChoice = 0;

    public PauseOverlay(Playing playing) {
        this.playing = playing;
    }

    private void submit() {
        switch (selectedChoice) {
            case 0 -> playing.unpauseGame();
            case 1 -> playing.resetAll();
            case 2 -> {}
            case 3 -> Gamestate.state = Gamestate.MENU;
        }
    }

    private void circleChoice(int amount) {
        selectedChoice = selectedChoice + amount >= 0 ? (selectedChoice + amount) % MAX_CHOICE : MAX_CHOICE - 1;
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
        g.setColor(Color.WHITE);
        g.drawString("Pause", 1, 25);
        g.drawString("Resume", 1, 100);
        g.drawString("RESTART", 1, 150);
        g.drawString("Options", 1, 200);
        g.drawString("Quit", 1, 250);
        g.drawString(String.valueOf(selectedChoice), Game.GAME_WIDTH / 2, 125);

    }
}
