package me.emil.game.util;

import java.util.ArrayList;
import java.util.List;

/** Takes input from other methods.
 *  Returns a translation of the input as output.
 * 
 */
public class Util {

    /*public static int[][] getRoadDirArr(int [][] lvlTypeArr, PathPoint start, PathPoint end) {
        int [][] roadDirArr = new int[lvlTypeArr.length][lvlTypeArr[0].length];
        PathPoint curentTile = start;
        Direction lastDir = Direction.UP;

        while (!isCurrentSameAsEnd(curentTile, end)) {
            PathPoint prevTile = curentTile;
            curentTile = getNextRoadTile(prevTile, lastDir, lvlTypeArr);
        }

        return roadDirArr;
    }

    private static PathPoint getNextRoadTile(PathPoint prevTile, Direction lastDir, int[][] lvlTypeArr) {

        Direction testDir = lastDir;
        PathPoint testTile = getTileDir(prevTile, testDir, lastDir);
        return null;
    }
    
    private static PathPoint getTileDir(PathPoint prevTile, Direction testDir, Direction lastDir) {

        switch (testDir) {
            case LEFT:

                if (lastDir != Direction.RIGHT) {
                    return new PathPoint(prevTile.getxCord() - 1, prevTile.getyCord());
                }

            case UP:

                if (lastDir != Direction.DOWN) {
                    return new PathPoint(prevTile.getxCord(), prevTile.getyCord() - 1);
                }
            case RIGHT:

                if (lastDir != Direction.LEFT) {
                    return new PathPoint(prevTile.getxCord() + 1, prevTile.getyCord());
                }

            case DOWN:

                if (lastDir != Direction.UP) {
                    return new PathPoint(prevTile.getxCord(), prevTile.getyCord() + 1);
                }

            default:
                return null;
        }
    }

    private static boolean isCurrentSameAsEnd(PathPoint currentTile, PathPoint end) {
        
        return currentTile.getxCord() == end.getxCord() && currentTile.getyCord() == end.getyCord();
    }*/

    /** Converts the text from the level file into 2D images.
     * 
     */
    public static int[][] arrayListTo2Dint(List<String> list, int ySize, int xSize) {
        int[][] newArr = new int[ySize][xSize];

        for (int j = 0; j < newArr.length; j++) {
            for (int i = 0; i < newArr[j].length; i++) {
                int index = j * ySize + i;
                newArr[j][i] = Integer.parseInt(index >= list.size() ? "0" : list.get(index));
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
