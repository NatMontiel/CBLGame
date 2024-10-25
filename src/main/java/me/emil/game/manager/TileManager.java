package me.emil.game.manager;


import static me.emil.game.help.Constants.Tiles.GRASS_TILE;
import static me.emil.game.help.Constants.Tiles.ROAD_TILE;
import static me.emil.game.help.Constants.Tiles.WATER_TILE;

import me.emil.game.help.ImgFix;
import me.emil.game.help.LoadSave;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import me.emil.game.objects.Tile;



/** Manages the tiles.
 * 
 */
public class TileManager {

    public Tile grass;
    public Tile water;
    public Tile road;
    public Tile roadLR;
    public Tile roadTB;
    public Tile roadBtoR;
    public Tile roadLtoB;
    public Tile roadLtoT;
    public Tile roadTtoR;
    public Tile borderLWaterCorner;
    public Tile tileLWaterCorner;
    public Tile tileRWaterCorner;
    public Tile borderRWaterCorner;
    public Tile tWater;
    public Tile rWater;
    public Tile bWater;
    public Tile lWater;
    public Tile tLslice;
    public Tile tRslice;
    public Tile bRslice;
    public Tile bLslice;

    public BufferedImage atlas;
    public ArrayList<Tile> tiles = new ArrayList<>();

    public ArrayList<Tile> roadsS = new ArrayList<>();
    public ArrayList<Tile> roadsC = new ArrayList<>();
    public ArrayList<Tile> corners = new ArrayList<>();
    public ArrayList<Tile> beaches = new ArrayList<>();
    public ArrayList<Tile> islands = new ArrayList<>();

    /** Creates the tiles.
     * 
     */
    public TileManager() {

        loadAtlas();
        createTiles();

    }

    private void createTiles() {
        
        int id = 0;

        tiles.add(grass = new Tile(getSprite(9, 0), id++, GRASS_TILE));
        tiles.add(water = new Tile(getAniSprites(0, 0), id++, WATER_TILE));

        //road
        roadsS.add(road = new Tile(getSprite(8, 0), id++, ROAD_TILE));
        roadsS.add(roadTB = new Tile(ImgFix.getRotImg(getSprite(8, 0), 90), id++, ROAD_TILE));

        //corner of road
        //roadsC.add(roadBtoR = new Tile(getSprite(7, 0), id++, ROAD_TILE));
        roadsC.add(roadBtoR = new Tile(ImgFix.getBuildRotImageRoad(getSprite(8, 0),
            getSprite(7, 0), 0), id++, ROAD_TILE));
        roadsC.add(roadLtoB = new Tile(ImgFix.getBuildRotImageRoad(getSprite(8, 0),
            getSprite(7, 0), 90), id++, ROAD_TILE));
        roadsC.add(roadLtoT = new Tile(ImgFix.getBuildRotImageRoad(getSprite(8, 0), 
            getSprite(7, 0), 180), id++, ROAD_TILE));
        roadsC.add(roadTtoR = new Tile(ImgFix.getBuildRotImageRoad(getSprite(8, 0), 
            getSprite(7, 0), 270), id++, ROAD_TILE));

        //water corner
        corners.add(borderLWaterCorner = new Tile(ImgFix.getBuildRotImage(getAniSprites(0, 0),
             getSprite(5, 0), 0), id++, WATER_TILE));
        corners.add(tileLWaterCorner = new Tile(ImgFix.getBuildRotImage(getAniSprites(0, 0),
             getSprite(5, 0), 90), id++, WATER_TILE));
        corners.add(tileRWaterCorner = new Tile(ImgFix.getBuildRotImage(getAniSprites(0, 0),
             getSprite(5, 0), 180), id++, WATER_TILE));
        corners.add(borderRWaterCorner = new Tile(ImgFix.getBuildRotImage(getAniSprites(0, 0),
             getSprite(5, 0), 270), id++, WATER_TILE));


        beaches.add(tWater = new Tile(ImgFix.getBuildRotImage(
            getAniSprites(0, 0), getSprite(6, 0), 0), id++, WATER_TILE));
        beaches.add(rWater = new Tile(ImgFix.getBuildRotImage(
            getAniSprites(0, 0), getSprite(6, 0), 90), id++, WATER_TILE));
        beaches.add(bWater = new Tile(ImgFix.getBuildRotImage(
            getAniSprites(0, 0), getSprite(6, 0), 180), id++, WATER_TILE));
        beaches.add(lWater = new Tile(ImgFix.getBuildRotImage(
            getAniSprites(0, 0), getSprite(6, 0), 270), id++, WATER_TILE));

        

        islands.add(tLslice = new Tile(ImgFix.getBuildRotImage(
            getAniSprites(0, 0), getSprite(4, 0), 0), id++, WATER_TILE));
        islands.add(tRslice = new Tile(ImgFix.getBuildRotImage(
            getAniSprites(0, 0), getSprite(4, 0), 90), id++, WATER_TILE));
        islands.add(bRslice = new Tile(ImgFix.getBuildRotImage(
            getAniSprites(0, 0), getSprite(4, 0), 180), id++, WATER_TILE));
        islands.add(bRslice = new Tile(ImgFix.getBuildRotImage(
            getAniSprites(0, 0), getSprite(4, 0), 270), id++, WATER_TILE));

        tiles.addAll(roadsS);
        tiles.addAll(roadsC);
        tiles.addAll(corners);
        tiles.addAll(beaches);
        tiles.addAll(islands);

    }

    // private BufferedImage[] getImages(int firstX, int firstY, int secondX, int secondY) {
        
    //     return new BufferedImage[]{getSprite(firstX, firstY),
    //         getSprite(secondX, secondY)};
    // }


    private void loadAtlas() {
        atlas = LoadSave.getSpriteAtlas();
    }

    public Tile getTile(int id) {
        return tiles.get(id);
    }

    public BufferedImage getSprite(int id) {
        return tiles.get(id).getSprite();
    }

    private BufferedImage getSprite(int xCord, int yCord) {
        
        return atlas.getSubimage(xCord * 32, yCord * 32, 32, 32);
    }

    public BufferedImage getAniSprite(int id, int animationIndex) {
        return tiles.get(id).getSprite(animationIndex);
    }

    private BufferedImage[] getAniSprites(int xCord, int yCord) {

        BufferedImage[] arr = new BufferedImage[4];

        for (int i = 0; i < 4; i++) {
            arr[i] = getSprite(xCord + i, yCord);
        }

        return arr;
    }

    public boolean isSpriteAnimation(int spriteId) {
        return tiles.get(spriteId).isAnimation();
    }

    /** Gets the sides of the roads.
     * 
     */
    public ArrayList<Tile> getRoadsS() {
        
        return roadsS;
    }

    /** Gets the corners of the roads.
     * 
     */
    public ArrayList<Tile> getRoadsC() {
        
        return roadsC;
    }

    /** Gets the corners.
     * 
     */
    public ArrayList<Tile> getCorners() {
        
        return corners;
    }

    /** Gets beaches.
     * 
     */
    public ArrayList<Tile> getBeaches() {
        
        return beaches;
    }

    /** Gets the islands.
     *
     */
    public ArrayList<Tile> getIslands() {
        
        return islands;
    }

}
