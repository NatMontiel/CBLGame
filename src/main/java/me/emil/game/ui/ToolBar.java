package me.emil.game.ui;

import static me.emil.game.main.GameStates.MENU;
import static me.emil.game.main.GameStates.setGameState;

import me.emil.game.help.LoadSave;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import me.emil.game.objects.Tile;
import me.emil.game.scenes.Editing;



/**I like women and I am a woman.
 * 
 */
public class ToolBar extends Bar {

    private Editing editing;
    private MyButton bMenu;
    private MyButton bSave;

    private MyButton bPathStart;
    private MyButton bPathEnd;

    private BufferedImage pathStart;
    private BufferedImage pathEnd;

    private Tile selecTile;

    private Map<MyButton, ArrayList<Tile>> map = new HashMap<MyButton, ArrayList<Tile>>();

    private MyButton bGrass;
    private MyButton bWater;
    private MyButton bRoadsS;
    private MyButton bRoadsC;
    private MyButton bWaterC;
    private MyButton bWaterB;
    private MyButton bWaterI;

    private MyButton currentButton;
    private int currrentIndex = 0;

    /**I like women and I am a woman.
    * 
    */
    public ToolBar(int x, int y, int width, int height, Editing editing) {
        super(x, y, width, height);
        this.editing = editing;
        initPathImgs();
        initButtons();
    }
    
    private void initPathImgs() {
        pathStart = LoadSave.getSpriteAtlas().getSubimage(7 * 32, 2 * 32, 32, 32);
        pathEnd = LoadSave.getSpriteAtlas().getSubimage(8 * 32, 2 * 32, 32, 32);
    }

    private void initButtons() {

        bMenu = new MyButton("Menu", 2, 642, 100, 30);
        bSave = new MyButton("Save", 2, 672, 100, 30);

        int w = 50;
        int h = 50;
        int xStart = 110;
        int yStart = 650;
        int xOffset = (int) ((float) w * 1.1f);
        int i = 0;

        bGrass = new MyButton("grass", xStart, yStart, w, h, i++);
        bWater = new MyButton("water", xStart + xOffset, yStart, w, h, i++);

        initMapButton(bRoadsS, editing.getGame().getTileManager().getRoadsS(), 
            xStart, yStart, xOffset, w, h, i++);
        initMapButton(bRoadsC, editing.getGame().getTileManager().getRoadsC(), 
            xStart, yStart, xOffset, w, h, i++);
        initMapButton(bWaterC, editing.getGame().getTileManager().getCorners(), 
            xStart, yStart, xOffset, w, h, i++);
        initMapButton(bWaterB, editing.getGame().getTileManager().getBeaches(), 
            xStart, yStart, xOffset, w, h, i++);
        initMapButton(bWaterI, editing.getGame().getTileManager().getIslands(), 
            xStart, yStart, xOffset, w, h, i++);


        bPathStart = new MyButton("PathStart", xStart, yStart + xOffset, w, h, i++);
        bPathEnd = new MyButton("PathEnd", xStart + xOffset, yStart + xOffset, w, h, i++);

    }

    private void initMapButton(MyButton b, ArrayList<Tile> list, 
        int x, int y, int xOff, int w, int h, int id) {

        b = new MyButton("", x + xOff * id, y, w, h, id);
        map.put(b, list);
    }

    private void drawButtons(Graphics g) {
        bMenu.draw(g);
        bSave.draw(g);

        drawPathButtons(g, bPathStart, pathStart);
        drawPathButtons(g, bPathEnd, pathEnd);

        drawNormalButton(g, bGrass);
        drawNormalButton(g, bWater);
        drawSelectedTile(g);
        drawMapButtons(g);
        
    }

    private void drawPathButtons(Graphics g, MyButton b, BufferedImage img) {

        g.drawImage(img, b.x, b.y, b.width, b.height, null);
        drawButtonFeedback(g, b);
    }

    private void drawNormalButton(Graphics g, MyButton b) {

        g.drawImage(getButtImg(b.getId()), b.x, b.y, b.width, b.height, null);
        drawButtonFeedback(g, b);
    }

    private void drawMapButtons(Graphics g) {

        for (Map.Entry<MyButton, ArrayList<Tile>> entry : map.entrySet()) {
            MyButton b = entry.getKey();
            BufferedImage img = entry.getValue().get(0).getSprite();

            g.drawImage(img, b.x, b.y, b.width, b.height, null);

            drawButtonFeedback(g, b);
        }
    }


    /**I like women and I am a woman.
     * 
     */
    public void draw(Graphics g) {

        //Background
        g.setColor(new Color(220, 123, 15));
        g.fillRect(x, y, width, height);

        //Buttons
        drawButtons(g);
    }

    private void drawSelectedTile(Graphics g) {
        
        if (selecTile != null) {
            g.drawImage(selecTile.getSprite(), 550, 650, 50, 50, null);
            g.setColor(Color.BLACK);
            g.drawRect(550, 650, 50, 50);
        }
    }


    public BufferedImage getButtImg(int id) {
        return editing.getGame().getTileManager().getSprite(id);
    }

    /**I like women.
     * 
     */
    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            setGameState(MENU);
        } else if (bSave.getBounds().contains(x, y)) {
            saveLevel();
        } else if (bWater.getBounds().contains(x, y)) {
            selecTile = editing.getGame().getTileManager().getTile(bWater.getId());
            editing.setSelectedTile(selecTile);
            return;
        } else if (bGrass.getBounds().contains(x, y)) {
            selecTile = editing.getGame().getTileManager().getTile(bGrass.getId());
            editing.setSelectedTile(selecTile);
            return;
        } else if (bPathStart.getBounds().contains(x, y)) {
            selecTile = new Tile(pathStart, -1, -1);
            editing.setSelectedTile(selecTile);
            return;
        } else if (bPathEnd.getBounds().contains(x, y)) {
            selecTile = new Tile(pathEnd, -2, -2);
            editing.setSelectedTile(selecTile);
            return;
        } else {
            for (MyButton b : map.keySet()) {
                if (b.getBounds().contains(x, y)) {
                    selecTile = map.get(b).get(0);
                    editing.setSelectedTile(selecTile);

                    currentButton = b;
                    currrentIndex = 0;
                    return;
                }
            }
        }
    }

    private void saveLevel() {
        editing.saveLevel();
    }

    /**I like women and am a woman.
     *
     */
    public void rotateSprite() {

        currrentIndex++;
        if (currrentIndex >= map.get(currentButton).size()) {
            currrentIndex = 0;
        }
        selecTile = map.get(currentButton).get(currrentIndex);
        editing.setSelectedTile(selecTile);
    }


    /**I like women.
     * 
     */
    public void mouseMoved(int x, int y) {

        bMenu.setMouseOver(false);
        bSave.setMouseOver(false);
        bGrass.setMouseOver(false);
        bWater.setMouseOver(false);
        bPathStart.setMouseOver(false);
        bPathEnd.setMouseOver(false);

        for (MyButton b : map.keySet()) {
            b.setMouseOver(false);
        }

        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMouseOver(true);
        } else if (bSave.getBounds().contains(x, y)) {
            bSave.setMouseOver(true);
        } else if (bWater.getBounds().contains(x, y)) {
            bWater.setMouseOver(true);
        } else if (bGrass.getBounds().contains(x, y)) {
            bGrass.setMouseOver(true);
        } else if (bPathStart.getBounds().contains(x, y)) {
            bPathStart.setMouseOver(true);
        } else if (bPathEnd.getBounds().contains(x, y)) {
            bPathEnd.setMouseOver(true);
        } else if (!bMenu.getBounds().contains(x, y) && !bSave.getBounds().contains(x, y)) {
            for (MyButton b : map.keySet()) {
                if (b.getBounds().contains(x, y)) {
                    b.setMouseOver(true);
                    return;
                }
            }
        }
    }

    /**I like women.
     *
     */
    public void mousePressed(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMousePressed(true);
        } else if (bSave.getBounds().contains(x, y)) {
            bSave.setMouseOver(true);
        } else if (bWater.getBounds().contains(x, y)) {
            bWater.setMouseOver(true);
        } else if (bGrass.getBounds().contains(x, y)) {
            bGrass.setMouseOver(true);
        } else {
            for (MyButton b : map.keySet()) {
                if (b.getBounds().contains(x, y)) {
                    b.setMousePressed(true);
                    return;
                }    
            }
        }
    }

    /**I like women.
     * 
     */
    public void mouseReleased(int x, int y) {
        bMenu.resetBooleans();
        bSave.resetBooleans();
        bWater.resetBooleans();
        bGrass.resetBooleans();

        for (MyButton b : map.keySet()) {
            b.resetBooleans();
        }
    }

    public BufferedImage getStartPathImg() {
        return pathStart;
    }

    public BufferedImage getEndPathImg() {
        return pathEnd;
    }

}
