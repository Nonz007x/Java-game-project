package inputs;

import entity.Player;
import main.GamePanel;

import java.awt.event.*;
import javax.swing.Timer;


public class MouseEventHandler implements MouseListener, MouseMotionListener {
    private final GamePanel gamePanel;
    private final Timer timer;
    private double rotationAngleRad;
    private int mouseX, mouseY;

    public MouseEventHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        timer = new Timer(100, e -> {});
        timer.setRepeats(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        gamePanel.getPlayer().mouseClicked(true);
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
        updateMousePosition(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        updateMousePosition(e);
    }

    private void updateMousePosition(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        calculateRotationAngle();
        updatePlayerState();
    }

    private void calculateRotationAngle() {
        int playerScreenPosX = gamePanel.getPlayer().getPlayerScreenPosX();
        int playerScreenPosY = gamePanel.getPlayer().getPlayerScreenPosY();
        rotationAngleRad = Math.atan2(mouseY - playerScreenPosY - 24, mouseX - playerScreenPosX - 24);
//        System.out.println(Math.toDegrees(rotationAngleRad));
    }

    private void updatePlayerState() {
        Player player = gamePanel.getPlayer();
        player.setRotationAngleRad(rotationAngleRad);
        player.setMousePosition(mouseX, mouseY);
    }

    public void startTracking() {
        timer.start();
    }

}