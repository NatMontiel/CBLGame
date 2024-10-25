package me.emil.game.help;

import static me.emil.game.help.Constants.Direction.DOWN;
import static me.emil.game.help.Constants.Direction.LEFT;
import static me.emil.game.help.Constants.Direction.RIGHT;
import static me.emil.game.help.Constants.Direction.UP;

import java.util.ArrayList;

import me.emil.game.objects.PathPoint;

/** Takes input from other methods.
 *  Returns a translation of the input as output.
 * 
 */
public class Util {

    public static int[][] getRoadDirArr(int [][] lvlTypeArr, PathPoint start, PathPoint end) {
        int [][] roadDirArr = new int[lvlTypeArr.length][lvlTypeArr[0].length];
        PathPoint curentTile = start;
        int lastDir = -1;

        while (!isCurrentSameAsEnd(curentTile, end)) {
            PathPoint prevTile = curentTile;
            curentTile = getNextRoadTile(prevTile, lastDir, lvlTypeArr);
        }

        return roadDirArr;
    }

    private static PathPoint getNextRoadTile(PathPoint prevTile, int lastDir, int[][] lvlTypeArr) {

        int testDir = lastDir;
        PathPoint testTile = getTileDir(prevTile, testDir, lastDir);


        return null;
    }

    /**.
     * 
     */
    private static PathPoint getTileDir(PathPoint prevTile, int testDir, int lastDir) {

        switch (testDir) {
            case LEFT:

                if (lastDir != RIGHT) {
                    return new PathPoint(prevTile.getxCord() - 1, prevTile.getyCord());
                }

            case UP:

                if (lastDir != DOWN) {
                    return new PathPoint(prevTile.getxCord(), prevTile.getyCord() - 1);
                }
            case RIGHT:

                if (lastDir != LEFT) {
                    return new PathPoint(prevTile.getxCord() + 1, prevTile.getyCord());
                }

            case DOWN:

                if (lastDir != UP) {
                    return new PathPoint(prevTile.getxCord(), prevTile.getyCord() + 1);
                }

            default:
                return null;
        }
    }

    private static boolean isCurrentSameAsEnd(PathPoint currentTile, PathPoint end) {

        if (currentTile.getxCord() == end.getxCord() && currentTile.getyCord() == end.getyCord()) {
            return true;
        }
        return false;
    }

    /** Converts the text from the level file into 2D images.
     * 
     */
    public static int[][] arrayListTo2Dint(ArrayList<Integer> list, int ySize, int xSize) {

        int[][] newArr = new int[ySize][xSize];

        for (int j = 0; j < newArr.length; j++) {
            for (int i = 0; i < newArr[j].length; i++) {
                int index = j * ySize + i;
                newArr[j][i] = list.get(index); 
            }
        }

        return newArr;
    }

    /** Makes a one dimensional array from a two dimensional array.
     * 
     */
    public static int[] dimConvert(int[][] twoArr) {

        int[] oneArr = new int[twoArr.length * twoArr[0].length];

        for (int j = 0; j < twoArr.length; j++) {
            for (int i = 0; i < twoArr[j].length; i++) {
                int index = j * twoArr.length + i;
                oneArr[index] =  twoArr[j][i];
            }
        }

        return oneArr;
    }

    /** Determines the distance from the tower to the enemy.
     * 
     */
    public static int getHypoDistance(float x1, float y1, float x2, float y2) {

        float xDiff = Math.abs(x1 - x2);
        float yDiff = Math.abs(y1 - y2);

        return (int) Math.hypot(xDiff, yDiff);
    }
}
