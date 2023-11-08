package ui;

import main.Game;

import java.awt.*;

import utils.Callback;

public class TransitionOverlay {
    private static int alpha = 0;
    private static int alphaIncrement = 5;
    private static boolean increasing = true;
    public static boolean drawing = false;
    private static Callback callback = ()->{};


    public static void playTransition(Callback callback) {
        TransitionOverlay.callback = callback;
        drawing = true;
    }

    public static void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, alpha));
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        if (increasing) {
            alpha += alphaIncrement;
            if (alpha >= 255) {
                alpha = 255;
                increasing = false;
                callback.onComplete();
                callback = ()->{};
            }
        } else {
            alpha -= alphaIncrement;
            if (alpha <= 0) {
                alpha = 0;
                increasing = true;
                drawing = false;
            }
        }
    }
}
