package main;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;
import javax.swing.ImageIcon;

public class GameWindow {

    public GameWindow(GamePanel gamePanel) {

        JFrame jframe = new JFrame();

        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.add(gamePanel);

        jframe.setTitle("Quantum Bullets");

        ImageIcon icon = new ImageIcon("src/res/boomstick.png");
        jframe.setIconImage(icon.getImage());

        jframe.setResizable(true);
        jframe.pack();
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);
    }

}
