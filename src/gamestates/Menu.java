package gamestates;

import main.Game;
import ui.MenuButton;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Menu extends State implements Statemethods {
    private MenuButton[] menuButtons;
    public Menu(Game game) {
        super(game);
    }
    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.drawString("Menu", Game.GAME_WIDTH/2, (int) (Game.GAME_HEIGHT * 0.2));
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_ESCAPE) {
            setGamestate(Gamestate.PLAYING);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
