package gamestates;

import java.awt.event.MouseEvent;
import main.Game;

public abstract class State {

    protected final Game game;

    public State(Game game) {
        this.game = game;
    }

//    public boolean isIn(MouseEvent e, MenuButton mb) {
//        return mb.getBounds().contains(e.getX(), e.getY());
//    }

    public Game getGame() {
        return game;
    }

    public static void setGamestate(Gamestate state) {
//        switch (state) {
//            case MENU -> game.getAudioPlayer().playSong(AudioPlayer.MENU_1);
//            case PLAYING -> game.getAudioPlayer().setLevelSong(game.getPlaying().getLevelManager().getLevelIndex());
//        }

        Gamestate.state = state;
    }



}