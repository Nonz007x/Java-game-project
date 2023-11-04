package gamestates;

import main.Game;
import ui.MenuButton;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.util.Collections;

import static utils.Constants.Menu.MAX_CHOICE;
import static utils.Constants.Menu.backgroundColor;

public class Menu extends State implements Statemethods {
    Font font;
    FontMetrics fontMetrics;
    private int selectedChoice = 0;
    private MenuButton[] menuButtons;

    private static final BufferedImage[] backgroundImages = new BufferedImage[8];
    private static int spriteIndex = 0;
    private static int spriteTick = 0;

    static {
        for (int i = 0; i < 8; i++) {
            backgroundImages[i] = LoadSave.GetSprite("title" + (i+1) + ".png");
        }
    }

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
        spriteTick++;
        if (spriteTick >= 10) {
            spriteTick = 0;
            spriteIndex = spriteIndex + 1 > 7 ? 0 : spriteIndex + 1;
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImages[spriteIndex], 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        g.setColor(backgroundColor);
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        g.setColor(Color.WHITE);

        g.setFont(new Font("Dialog", Font.PLAIN, 80));
        FontMetrics fm = g.getFontMetrics();
        String text = "Quantum Bullets";

        g.drawString(text, 1, 100);

        g.setFont(new Font("Dialog", Font.PLAIN, 40));
        String[] menuItems = {"Play", "Options", "Quit"};
        int menuItemY = 200;

        for (int i = 0; i < menuItems.length; i++) {
            if (i == selectedChoice) {
                g.setFont(g.getFont().deriveFont(Collections.singletonMap(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON)));
            }

            g.drawString(menuItems[i], 1, menuItemY);

            if (i == selectedChoice) {
                g.setFont(g.getFont().deriveFont(Collections.singletonMap(TextAttribute.UNDERLINE, -1)));
            }

            menuItemY += 100;
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
