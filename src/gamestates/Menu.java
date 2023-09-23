package gamestates;

import main.Game;
import ui.MenuButton;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static utils.Constants.Menu.MAX_CHOICE;

public class Menu extends State implements Statemethods {
    Font font;
    FontMetrics fontMetrics;
    private int selectedChoice = 0;
    private MenuButton[] menuButtons;

    public Menu(Game game) {
        super(game);
    }

    private void submit() {
        switch (selectedChoice) {
            case 0 -> Gamestate.state = Gamestate.PLAYING;
            case 1 -> {
            }
            case 2 -> Gamestate.state = Gamestate.QUIT;
        }
    }

    private void circleChoice(int amount) {
        selectedChoice = selectedChoice + amount >= 0 ? (selectedChoice + amount) % MAX_CHOICE : MAX_CHOICE - 1;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
        g.setColor(Color.WHITE);
        g.drawString("Menu", 1, 25);
        g.drawString("Play", 1, 100);
        g.drawString("Options", 1, 150);
        g.drawString("Quit", 1, 200);
        g.drawString(String.valueOf(selectedChoice), Game.GAME_WIDTH / 2, 125);
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

        switch (code) {
            case KeyEvent.VK_S -> circleChoice(1);
            case KeyEvent.VK_W -> circleChoice(-1);
            case KeyEvent.VK_E -> submit();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
