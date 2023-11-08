package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import gamestates.Gamestate;
import main.GamePanel;

public class KeyHandler implements KeyListener {
    private final GamePanel gamePanel;

    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (Gamestate.state) {
            case MENU -> gamePanel.getGame().getMenu().keyPressed(e);
            case PLAYING -> gamePanel.getGame().getPlaying().keyPressed(e);
            case ARENA -> gamePanel.getGame().getArena().keyPressed(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (Gamestate.state) {
            case MENU -> gamePanel.getGame().getMenu().keyReleased(e);
            case PLAYING -> gamePanel.getGame().getPlaying().keyReleased(e);
            case ARENA -> gamePanel.getGame().getArena().keyReleased(e);
        }
    }
}
