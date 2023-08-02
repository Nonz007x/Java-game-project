package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import main.GamePanel;

public class KeyHandler implements KeyListener {
    public boolean  spacePressed;
    GamePanel gamePanel;

    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        gamePanel.getGame().getPlaying().keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        gamePanel.getGame().getPlaying().keyReleased(e);
    }
}
