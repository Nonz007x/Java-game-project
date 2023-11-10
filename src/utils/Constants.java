package utils;

import java.awt.*;

public class Constants {
    public static class GameObject {
        public static int FRAGMENT = 0;
        public static int GATE = 1;
        public static int Potion = 2;
    }

    public static class UI {
        public static final Font F_DEFAULT = new Font("Dialog", Font.PLAIN, 12);
        public static final Color OVERLAY_BG_COLOR = new Color(0, 0, 0, 150);
    }

    public static class Menu {

        public static Color backgroundColor = new Color(0, 0, 0, 150);
        public static final Color textColor = Color.WHITE;

    }

    public static class Projectile {
        public static final int DEFAULT_TICKS_TO_LIVE = 600;
        public static final int BUCKSHOT_DAMAGE = 10;
    }

    public static class BossConstants {
        public static final int ULTRA_GRAND_PRIX_HEALTH = 1069;
        public static final int ULTRA_GRAND_PRIX_WIDTH_DEFAULT = 96;
        public static final int ULTRA_GRAND_PRIX_HEIGHT_DEFAULT = 96;

        public static final int CRABULON_HEALTH = 100;
        public static final int CRABULON_WIDTH_DEFAULT = 316;
        public static final int CRABULON_HEIGHT_DEFAULT = 196;

        public static final int SLIME_HEALTH = 1500;
        public static final int SLIME_WIDTH_DEFAULT = 320;
        public static final int SLIME_HEIGHT_DEFAULT = 320;
    }

    public static class EnemyConstants {

        public static final int GRANDPRIX = 1;
        public static final int CRABULON = 50;
        public static final int MON_CIRCLE = 2;

        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int ATTACK = 2;
        public static final int HIT = 3;
        public static final int DEAD = 4;

        public static final int GRANDPRIX_WIDTH_DEFAULT = 64;
        public static final int GRANDPRIX_HEIGHT_DEFAULT = 64;

        public static int getSpriteAmount(int enemy_action, int enemy_type) {
            switch (enemy_type) {
                case GRANDPRIX -> {
                    return 3;
                }

                case CRABULON -> {
                    return 6;
                }

                case MON_CIRCLE -> {
                    return 2;
                }

                default -> {
                    return 0;
                }
            }
        }


    }

    public static class PlayerConstants {
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int DEAD = 2;

        public static int getSpriteAmount(int player_action) {
            return switch (player_action) {
                case IDLE, DEAD -> 1;
                case RUNNING -> 3;
                default -> throw new IllegalStateException("Unexpected value: " + player_action);
            };
        }
    }
}
