package utils;

import java.util.HashSet;
import java.util.Set;

public class Constants {
    public static class Collision {
        public static final int NO_COLLISION = 0;
        public static final int FULL_COLLISION = 1;
        public static final int TOP_COLLISION = 2;
        public static final int LEFT_COLLISION = 3;
        public static final int BOTTOM_COLLISION = 4;
        public static final int RIGHT_COLLISION = 5;
        public static final int TOP_LEFT_COLLISION = 6;
        public static final int LEFT_BOTTOM_COLLISION = 7;
        public static final int BOTTOM_RIGHT_COLLISION = 8;
        public static final int TOP_RIGHT_COLLISION = 9;
        public static final int RIGHT_LEFT_COLLISION = 10;
        public static final int TOP_BOTTOM_COLLISION = 11;
        public static final int LEFT_RIGHT_COLLISION = 12;
        public static final int TOP_LEFT_RIGHT_COLLISION = 13;
        public static final int TOP_LEFT_BOTTOM_COLLISION = 14;
        public static final int LEFT_BOTTOM_RIGHT_COLLISION = 15;
        public static final int TOP_RIGHT_BOTTOM_RIGHT_COLLISION = 16;

    }

    public static class EnemyConstants {

        public static final int GRANDPRIX = 0;

        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int ATTACK = 2;
        public static final int HIT = 3;
        public static final int DEAD = 4;

        public static final int GRANDPRIX_WIDTH_DEFAULT = 64;
        public static final int GRANDPRIX_HEIGHT_DEFAULT = 64;

        public static int GetSpriteAmount(int player_action) {
            return switch (player_action) {
                case RUNNING -> 3;
                case HIT -> 4;
                default -> 1;
            };
        }


    }
    public static class PlayerConstants {
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int ATTACK = 2;
        public static final int HIT = 3;
        public static final int DEAD = 4;

        public static int GetSpriteAmount(int player_action) {
            return switch (player_action) {
                case RUNNING -> 3;
                case HIT -> 4;
                default -> 1;
            };
        }
    }
}
