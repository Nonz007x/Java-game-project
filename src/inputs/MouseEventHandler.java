package inputs;

import gamestates.Gamestate;
import main.GamePanel;

import java.awt.event.*;

public class MouseEventHandler implements MouseListener, MouseMotionListener {
    private final GamePanel gamePanel;

    public MouseEventHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (Gamestate.state) {
            case MENU -> gamePanel.getGame().getMenu().mouseClicked(e);
            case PLAYING -> gamePanel.getGame().getPlaying().mouseClicked(e);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        switch (Gamestate.state) {
            case MENU -> gamePanel.getGame().getMenu().mouseDragged(e);
            case PLAYING -> gamePanel.getGame().getPlaying().mouseDragged(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        gamePanel.getGame().getPlaying().mouseMoved(e);
    }


}