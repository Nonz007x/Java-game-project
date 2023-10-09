package utils;

import java.util.HashSet;
import java.util.Set;

public class Constants {

    public static class Menu {
        public static int PLAY = 0;
        public static int OPTIONS = 1;
        public static int EXIT = 2;
        public static int MAX_CHOICE = 3;

    }

    public static class Projectile {
        public static final int DEFAULT_TICKS_TO_LIVE = 600;
        public static final int BUCKSHOT_DAMAGE = 10;
    }

    public static class BossConstants {
        public static final int CRABULON_HEALTH = 100;
        public static final int CRABULON_WIDTH_DEFAULT = 316;
        public static final int CRABULON_HEIGHT_DEFAULT = 196;
    }

    public static class EnemyConstants {

        public static final int GRANDPRIX = 0;
        public static final int CRABULON = 50;

        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int ATTACK = 2;
        public static final int HIT = 3;
        public static final int DEAD = 4;

        public static final int GRANDPRIX_WIDTH_DEFAULT = 64;
        public static final int GRANDPRIX_HEIGHT_DEFAULT = 64;

        public static int GetSpriteAmount(int enemy_action, int enemy_type) {
            switch (enemy_type) {
                case GRANDPRIX -> {
                    return 3;
                }

                case CRABULON -> {
                    return 6;
                }
                default -> {
                    return -1;
                }
            }
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
