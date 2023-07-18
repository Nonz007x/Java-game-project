package utils;

public class Constants {
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
