package me.emil.game.help;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.imageio.ImageIO;
import me.emil.game.objects.PathPoint;


/** Loads and saves a level.
 * 
 */
public class LoadSave {
    
    public static String DIRECTORY = System.getProperty("user.dir") + File.separator + "AppData" + File.separator + "Roaming" + File.separator + "CBLGame";

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
    
    /** Saves a level in the text file.
     * 
     */
    private static void writeToFile(CustomFile f, int[] idArr, PathPoint start, PathPoint end) {
        List<String> fileContent = new ArrayList<>();
        
        for (Integer i : idArr)
            fileContent.add(String.valueOf(i));
        
        fileContent.add(String.valueOf(start.getxCord()));
        fileContent.add(String.valueOf(start.getyCord()));
        fileContent.add(String.valueOf(end.getxCord()));
        fileContent.add(String.valueOf(end.getyCord()));
        
        f.setContent(fileContent);
    }

    /** Checks if the file of the level exists.
     *  Sets the start and end of the path the me.emil.game.enemies walk on.
     * 
     */
    public static void saveLevel(String name, int[][] idArr, PathPoint start, PathPoint end) {
        CustomFile levelFile = new CustomFile(DIRECTORY, "/" + name + ".txt");

        if (levelFile.exists())
            writeToFile(levelFile, Util.dimConvert(idArr), start, end);
        else
            levelFile.createNewFile();
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
        CustomFile lvlFile = new CustomFile(DIRECTORY, "/" + name + ".txt");

        if (lvlFile.exists()) {
            ArrayList<Integer> list = readFromFile(lvlFile);
            
            if(list.isEmpty())
                return null;
            
            return Util.arrayListTo2Dint(list, 20, 20);
        } else {
            lvlFile.createNewFile();
            return null;
        }  
    }

    /** Gets the level path points.
     * 
     *
     */
    public static List<PathPoint> getLevelPathPoints(String name) {

        CustomFile lvlFile = new CustomFile(DIRECTORY, "/" + name + ".txt");

        if (lvlFile.exists()) {
            List<Integer> list = readFromFile(lvlFile);
            
            if(list.isEmpty())
                return new ArrayList<>();
            
            List<PathPoint> points = new ArrayList<>();

            points.add(new PathPoint(list.get(400), list.get(401)));
            points.add(new PathPoint(list.get(402), list.get(403)));

            return points;
        } else {
           lvlFile.createNewFile();
            return new ArrayList<>();
        } 
    }

    /** Creates a level using the level file.
     * 
     *
     */
    public static void createLevel(String name, int[] idArr) {
        CustomFile newLevel = new CustomFile(DIRECTORY, "/" + name + ".txt");
        
        if(!newLevel.exists()) {
           newLevel.createNewFile();
           writeToFile(newLevel, idArr, new PathPoint(0, 0), new PathPoint(0, 0));
        }
    }
}
