package main;

import java.awt.event.*;
import javax.swing.Timer;

public class MouseHandler implements MouseMotionListener, MouseListener {
    public int mouseX;
    public int mouseY;
    public boolean isClicked;
    public double rotationAngleRad;
    private final Timer timer;

    GamePanel gp;

    public MouseHandler(GamePanel gp) {
        timer = new Timer(100, e -> {
//            getMousePos();
        });
        timer.setRepeats(true);
        this.gp = gp;
    }

    public void getMousePos() {
        System.out.println("Mouse position: " + mouseX + ", " + mouseY);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // Update the mouse position while dragging
        mouseX = e.getX();
        mouseY = e.getY();
        rotationAngleRad = Math.atan2(mouseY - gp.player.playerScreenPosY - 30, mouseX - gp.player.playerScreenPosX - 30);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Update the mouse position while moving
        mouseX = e.getX();
        mouseY = e.getY();
        rotationAngleRad = Math.atan2(mouseY - gp.player.playerScreenPosY - 30, mouseX - gp.player.playerScreenPosX - 30);
    }

    public void startTracking() {
        // Start the timer to continuously update the mouse position
        timer.start();
    }

    public void stopTracking() {
        timer.stop();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        isClicked = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
//        isClicked = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}