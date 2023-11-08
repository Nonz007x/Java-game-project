package gamestates;

import java.awt.event.MouseEvent;
import main.Game;

public abstract class State {

    protected static Game game;

    public State(Game game) {
        State.game = game;
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
        switch (state) {
            case ARENA -> game.getArena().initArena();
            case PLAYING -> game.getPlaying().initPlaying();
        }
        Gamestate.state = state;
    }



}