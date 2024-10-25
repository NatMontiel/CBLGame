package me.emil.game.scenes;

import static me.emil.game.help.Constants.Tiles.ROAD_TILE;

import me.emil.game.help.LoadSave;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import me.emil.game.main.Game;
import me.emil.game.objects.PathPoint;
import me.emil.game.objects.Tile;
import me.emil.game.ui.ToolBar;



/**I like women and I am a woman.
 * 
 */
public class Editing extends GameScene implements SceneMethods {

    private int[][] lvl;
    private Tile selecTile;
    private int mouseX;
    private int mouseY;
    private int lastTileX;
    private int lastTileY;
    private int lastTileid;
    private boolean drawSelect = false;
    private ToolBar toolbar;

    private PathPoint start;
    private PathPoint end;

    /**I like women and I am a woman.
     * 
     */
    public Editing(Game game) {
        super(game);
        loadDefaultLevel();
        toolbar = new ToolBar(0, 640, 640, 160, this);
    }

    private void loadDefaultLevel() {
        lvl = LoadSave.getLevelData("new_level");
        List<PathPoint> points = LoadSave.getLevelPathPoints("new_level");
        
        if(points.isEmpty())
            return;
        
        start = points.get(0);
        end = points.get(1);
    }

    public void update() {
        updateTick();
    }

    @Override
    public void render(Graphics g) {
        drawLevel(g);
        toolbar.draw(g);
        drawSelectedTile(g);
        drawPathPoints(g);
    }

    private void drawPathPoints(Graphics g) {

        if (start != null) {
            g.drawImage(toolbar.getStartPathImg(), start.getxCord() * 32, start.getyCord() * 32,
                32, 32, null);
        }
        if (end != null) {
            g.drawImage(toolbar.getEndPathImg(), end.getxCord() * 32, end.getyCord() * 32,
                32, 32, null);
        }
    }

    private void drawLevel(Graphics g) {
        for (int y = 0; y < lvl.length; y++) {
            for (int x = 0; x < lvl[y].length; x++) {
                int id = lvl[y][x];
                if (isAnimation(id)) {
                    g.drawImage(getSprite(id, animationIndex), x * 32, y * 32, null);
                } else {
                    g.drawImage(getSprite(id), x * 32, y * 32, null);
                }
            }
        }
    }


    private void drawSelectedTile(Graphics g) {
        if (selecTile != null && drawSelect) {
            g.drawImage(selecTile.getSprite(), mouseX, mouseY, 32, 32, null);
        }
    }

    /**I like women and I am a woman.
     * 
     */
    public void saveLevel() {

        LoadSave.saveLevel("new_level", lvl, start, end);
        game.getPlaying().setLevel(lvl);

    }

    /**I am a woman.
     * 
     */
    public void setSelectedTile(Tile tile) {
        this.selecTile = tile;
        drawSelect = true;
    }

    private void changeTile(int x, int y) {

        if (selecTile != null) {
            int tileX = x / 32;
            int tileY = y / 32;
            
            if (selecTile.getId() >= 0) {
                if (lastTileX == tileX && lastTileY == tileY && lastTileid == selecTile.getId()) {
                    return;
                }

                lastTileX = tileX;
                lastTileY = tileY;
                lastTileid = selecTile.getId();
    
                lvl[tileY][tileX] = selecTile.getId();
                
            } else {
                int id = lvl[tileY][tileX];
                if (game.getTileManager().getTile(id).getTileType() == ROAD_TILE) {
                    if (selecTile.getId() == -1) {
                        start = new PathPoint(tileX, tileY);
                    } else {
                        end = new PathPoint(tileX, tileY);
                    }
                }
            }

            
        }
    }

    @Override
    public void mouseClicked(int x, int y) {
        if (y >= 640) {
            toolbar.mouseClicked(x, y);
        } else {
            changeTile(mouseX, mouseY);
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        if (y >= 640) {
            toolbar.mouseMoved(x, y);
            drawSelect = false;
        } else {
            drawSelect = true;
            mouseX = (x / 32) * 32;
            mouseY = (y / 32) * 32;
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if (y >= 640) {
            toolbar.mousePressed(x, y);
            drawSelect = false;
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        toolbar.mouseReleased(x, y);
    }

    @Override
    public void mouseDragged(int x, int y) {
        if (y < 640) {
            changeTile(x, y);
        }
    }

    /**I like women and am a woman.
     *
     */
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_R) {
            toolbar.rotateSprite();
        }
    }


}
