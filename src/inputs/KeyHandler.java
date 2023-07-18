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
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            gamePanel.getPlayer().setUp(true);
        }
        if (code == KeyEvent.VK_A) {
            gamePanel.getPlayer().setLeft(true);
        }
        if (code == KeyEvent.VK_S) {
            gamePanel.getPlayer().setDown(true);
        }
        if (code == KeyEvent.VK_D) {
            gamePanel.getPlayer().setRight(true);
        }
        if(code == KeyEvent.VK_SHIFT) {
            gamePanel.getPlayer().dodge();
        }
        if(code == KeyEvent.VK_SPACE) {
            spacePressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            gamePanel.getPlayer().setUp(false);
        }
        if (code == KeyEvent.VK_A) {
            gamePanel.getPlayer().setLeft(false);
        }
        if (code == KeyEvent.VK_S) {
            gamePanel.getPlayer().setDown(false);
        }
        if (code == KeyEvent.VK_D) {
            gamePanel.getPlayer().setRight(false);
        }
        if(code == KeyEvent.VK_SPACE) {
            spacePressed = false;
        }
    }
}
