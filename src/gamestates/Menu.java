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

    private final Color backgroundColor = new Color(0, 0, 0, 150);
    private final Color textColor = Color.WHITE;

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

        g.setColor(backgroundColor);
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        g.setColor(Color.WHITE);

        FontMetrics fm = g.getFontMetrics();
        String text = "Quantum Bullets";

        int x = (Game.GAME_WIDTH - fm.stringWidth(text)) / 2;

        g.drawString(text, x, 25);
        String[] menuItems = {"Play", "Options", "Quit"};
        int menuItemY = 100;

        for (int i = 0; i < menuItems.length; i++) {
            if (i == selectedChoice) {
                g.fillRect(1, menuItemY + 1, 25, 1);
            }

            g.drawString(menuItems[i], 1, menuItemY);
            menuItemY += 50;
        }
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
            case KeyEvent.VK_S, KeyEvent.VK_DOWN -> circleChoice(1);
            case KeyEvent.VK_W, KeyEvent.VK_UP -> circleChoice(-1);
            case KeyEvent.VK_E, KeyEvent.VK_ENTER -> submit();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
