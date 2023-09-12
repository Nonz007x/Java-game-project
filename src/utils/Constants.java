package utils;

public class Constants {

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
