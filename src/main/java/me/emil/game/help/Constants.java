package me.emil.game.help;

/** Sets the constant values for each object in the game.
* 
*/
public class Constants {

    /** Sets the values of the projectiles the towers use to attack.
    * 
    */
    public static class Projectile {

        public static final int BONES = 0;
        public static final int BATS = 1;
        public static final int CAULDRON = 2;

        /**Sets the speed for each projectile.
         *  
        */
        public static float getSpeed(int type) {
            switch (type) {
                case BONES:
                    return 8f;
                case CAULDRON:
                    return 4f;
                case BATS:
                    return 6f;
                default:
                    return 0f;
            }
        }
    }

    /** Sets the constant values for each tower.
     * 
     */
    public static class Towers {
        public static final int WITCH = 0;
        public static final int SKELETON = 1;
        public static final int VAMPIRE = 2;

        /**.
         */
        public static int getMonsterCost(int towerType) {
            switch (towerType) {
                case WITCH:
                    return 65;
                case SKELETON:
                    return 30;
                case VAMPIRE:
                    return 45;
                default:
                    return 0;
            }
        }
        

        /** Sets the names of the towers.
        * 
        */
        public static String getName(int towerType) {
            switch (towerType) {
                case WITCH:
                    return "Witch";
                case SKELETON:
                    return "Skeleton";
                case VAMPIRE:
                    return "Vampire";
                default:
                    return "";
            }
        }

        /** Sets the amount of damage the towers give the me.emil.game.enemies.
        * 
        */
        public static int getStartDmg(int towerType) {
            switch (towerType) {
                case WITCH:
                    return 100;
                case SKELETON:
                    return 25;
                case VAMPIRE:
                    return 10;
                default:
                    return 0;
            }
        }

        /** Sets the range of the towers.
        * 
        */
        public static float getDefaultRange(int towerType) {
            switch (towerType) {
                case WITCH:
                    return 100;
                case SKELETON:
                    return 75;
                case VAMPIRE:
                    return 125;
                default:
                    return 0;
            }
        }

        /** Sets the cool down time for each of the towers.
        * 
        */
        public static float getDefaultCooldown(int towerType) {
            switch (towerType) {
                case WITCH:
                    return 50;
                case SKELETON:
                    return 15;
                case VAMPIRE:
                    return 25;
                default:
                    return 0;
            }
        }
    }

    /** Sets the values for the directions the projectiles are going.
     * 
     */
    public static class Direction {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    /** Sets the values of the me.emil.game.enemies.
     * 
     */
    public static class Enemies {
        public static final int VILLAGER = 0;
        public static final int PRIEST = 1;
        public static final int KNIGHT = 2;
        public static final int HUNTER = 3;

        /**I am a lesbian.
         * 
         */
        public static int getReward(int enemyType) {
            switch (enemyType) {
                case VILLAGER:
                    return 5;
                case PRIEST:
                    return 10;
                case KNIGHT:
                    return 25;
                case HUNTER:
                    return 30;
                default:
                    return 0;
            }
        }

        /** Sets the speed for each enemy.
        * 
        */
        public static float getSpeed(int enemyType) {
            switch (enemyType) {
                case VILLAGER:
                    return 0.5f;
                case PRIEST:
                    return 0.65f;
                case KNIGHT:
                    return 0.45f;
                case HUNTER:
                    return 0.75f;
                default:
                    return 0;
            }
        }

        /** Sets the starting health of each enemy.
        * 
        */
        public static int getStartHealth(int enemyType) {
            switch (enemyType) {
                case VILLAGER:
                    return 100;
                case PRIEST:
                    return 80;
                case KNIGHT:
                    return 250;
                case HUNTER:
                    return 120;
                default:
                    break;
            }
            return 0;
        }
    }

    /** Sets the value for each tile type.
     * 
     */
    public static class Tiles {
        public static final int WATER_TILE = 0;
        public static final int GRASS_TILE = 1;
        public static final int ROAD_TILE = 2;
    }

}
