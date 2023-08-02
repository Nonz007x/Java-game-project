package inputs;

import entity.Player;
import main.GamePanel;

import java.awt.event.*;
import javax.swing.Timer;

import gamestates.Playing;


public class MouseEventHandler implements MouseListener, MouseMotionListener {
    private final GamePanel gamePanel;
    private final Timer timer;
    private double rotationAngleRad;
    private int mouseX, mouseY;

    public MouseEventHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        timer = new Timer(100, e -> {
        });
        timer.setRepeats(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        gamePanel.getGame().getPlaying().mouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Not implemented, but can be added if needed
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Not implemented, but can be added if needed
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        gamePanel.getGame().getPlaying().mouseDragged(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        gamePanel.getGame().getPlaying().mouseMoved(e);
    }


}