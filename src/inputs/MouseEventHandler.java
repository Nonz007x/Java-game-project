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
            case ARENA -> gamePanel.getGame().getArena().mouseClicked(e);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (Gamestate.state) {
            case MENU -> gamePanel.getGame().getMenu().mousePressed(e);
            case PLAYING -> gamePanel.getGame().getPlaying().mousePressed(e);
            case ARENA -> gamePanel.getGame().getArena().mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (Gamestate.state) {
            case MENU -> gamePanel.getGame().getMenu().mouseReleased(e);
            case PLAYING -> gamePanel.getGame().getPlaying().mouseReleased(e);
            case ARENA -> gamePanel.getGame().getArena().mouseReleased(e);
        }
    }


    @Override
    public void mouseDragged(MouseEvent e) {
        switch (Gamestate.state) {
            case MENU -> gamePanel.getGame().getMenu().mouseDragged(e);
            case PLAYING -> gamePanel.getGame().getPlaying().mouseDragged(e);
            case ARENA -> gamePanel.getGame().getArena().mouseDragged(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        gamePanel.getGame().getPlaying().mouseMoved(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}