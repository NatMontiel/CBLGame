package me.emil.game.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import me.emil.game.object.PathPoint;

/** Loads and saves a level.
 * 
 */
public class StorageUtil
{
    public static String DIRECTORY = System.getProperty("user.dir") + File.separator + "AppData" + File.separator + "Roaming" + File.separator + "CBLGame";
    
    public static BufferedImage getSpriteAtlas() {
        BufferedImage image = null;
        InputStream is = StorageUtil.class.getClassLoader().getResourceAsStream("spriteatlas.png");

        try {
            image = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    /** Checks if the file of the level exists.
     *  Sets the start and end of the path the me.emil.game.enemies walk on.
     * 
     */
    public static void saveLevel(String name, int[][] idArr, PathPoint start, PathPoint end) {
        CustomFile levelFile = new CustomFile(DIRECTORY, "/" + name + ".txt");

        if (levelFile.exists())
        {
            List<String> fileContent = new ArrayList<>();
            
            for (Integer i : Util.dimConvert(idArr))
                fileContent.add(String.valueOf(i));
            
            fileContent.add(String.valueOf(start.getxCord()));
            fileContent.add(String.valueOf(start.getyCord()));
            fileContent.add(String.valueOf(end.getxCord()));
            fileContent.add(String.valueOf(end.getyCord()));
            
            levelFile.setContent(fileContent);
        }
        else
            levelFile.createNewFile();
    }

    /** Reads the data in the level file.
     * 
     */
    public static int[][] getLevelData(String name) {
        CustomFile lvlFile = new CustomFile(DIRECTORY, "/" + name + ".txt");

        if (lvlFile.exists())
            return Util.arrayListTo2Dint(lvlFile.getContent(), 20, 20);
            
        lvlFile.createNewFile();
        return Util.arrayListTo2Dint(new ArrayList<>(), 20, 20);
    }

    /** Gets the level path points.
     * 
     *
     */
    public static List<PathPoint> getLevelPathPoints(String name) {
        CustomFile lvlFile = new CustomFile(DIRECTORY, "/" + name + ".txt");

        if (lvlFile.exists()) {
            List<String> list = lvlFile.getContent();
            
            if(list.isEmpty())
                return new ArrayList<>();
            
            List<PathPoint> points = new ArrayList<>();

            points.add(new PathPoint(Integer.parseInt(list.get(400)), Integer.parseInt(list.get(401))));
            points.add(new PathPoint(Integer.parseInt(list.get(402)), Integer.parseInt(list.get(403))));

            return points;
        } else {
           lvlFile.createNewFile();
            return new ArrayList<>();
        } 
    }

    /** Creates a level using the level file
     */
    public static void createLevel(String name, int[] idArr) {
        CustomFile newLevel = new CustomFile(DIRECTORY, "/" + name + ".txt");
        
        if(!newLevel.exists()) {
           newLevel.createNewFile();
            
            List<String> fileContent = new ArrayList<>();
            
            for (Integer i : idArr)
                fileContent.add(String.valueOf(i));
            
            PathPoint pathPoint = new PathPoint(0, 0);
            
            fileContent.add(String.valueOf(pathPoint.getxCord()));
            fileContent.add(String.valueOf(pathPoint.getyCord()));
            fileContent.add(String.valueOf(pathPoint.getxCord()));
            fileContent.add(String.valueOf(pathPoint.getyCord()));
            
            newLevel.setContent(fileContent);
        }
    }
}
