package me.emil.game.help;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.imageio.ImageIO;
import me.emil.game.objects.PathPoint;


/** Loads and saves a level.
 * 
 */
public class LoadSave {

    /** Sets the image to use for the graphics.
     * 
     */
    public static BufferedImage getSpriteAtlas() {

        BufferedImage img = null;
        InputStream is;
        is = LoadSave.class.getClassLoader().getResourceAsStream("spriteatlas.png");

        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return img;
    }


    /** Creates the level's text file.
     * 
     */
    public static void createFile() {

        File txtFile = new File("/testTextFile.txt");

        try {
            txtFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Saves a level in the text file.
     * 
     */
    private static void writeToFile(File f, int[] idArr, PathPoint start, PathPoint end) {

        try {
            PrintWriter pw = new PrintWriter(f); 
            for (Integer i : idArr) {
                pw.println(i);
            }

            pw.println(start.getxCord());
            pw.println(start.getyCord());
            pw.println(end.getxCord());
            pw.println(end.getyCord());

            pw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /** Checks if the file of the level exists.
     *  Sets the start and end of the path the me.emil.game.enemies walk on.
     * 
     */
    public static void saveLevel(String name, int[][] idArr, PathPoint start, PathPoint end) {
        File levelFile = new File("/" + name + ".txt");

        if (levelFile.exists()) {
            writeToFile(levelFile, Util.dimConvert(idArr), start, end);

        } else {
            System.out.println("File: " + name + " does not exist!");
            return;
        }
    }

    /** Reads the level file.
     * 
     */
    private static ArrayList<Integer> readFromFile(File file) {
        
        ArrayList<Integer> list = new ArrayList<>();

        try {
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                list.add(Integer.parseInt(sc.nextLine()));
            }

            sc.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return list;
    }

    /** Reads the data in the level file.
     * 
     */
    public static int[][] getLevelData(String name) {

        File lvlFile = new File("/" + name + ".txt");

        if (lvlFile.exists()) {
            ArrayList<Integer> list = readFromFile(lvlFile);
            return Util.arrayListTo2Dint(list, 20, 20);
        } else {
            System.out.println("File: " + name + "does not exist!");
            return null;
        }  
    }

    /** Gets the level path points.
     * 
     *
     */
    public static ArrayList<PathPoint> getLevelPathPoints(String name) {

        File lvlFile = new File("/" + name + ".txt");

        if (lvlFile.exists()) {
            ArrayList<Integer> list = readFromFile(lvlFile);
            ArrayList<PathPoint> points = new ArrayList<>();

            points.add(new PathPoint(list.get(400), list.get(401)));
            points.add(new PathPoint(list.get(402), list.get(403)));

            return points;
        } else {
            System.out.println("File: " + name + "does not exist!");
            return null;
        } 
    }

    /** Creates a level using the level file.
     * 
     *
     */
    public static void createLevel(String name, int[] idArr) {

        File newLevel = new File("/" + name + ".txt");

        if (newLevel.exists()) {
            System.out.println("File: " + name + "already exists!");
            return;
        } else {
            try {
                newLevel.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            writeToFile(newLevel, idArr, new PathPoint(0, 0), new PathPoint(0, 0));
        }
    }

    
}
