package me.emil.game.manager;

//import static me.emil.game.help.Constants.Towers.ARCHER;
//import static me.emil.game.help.Constants.Towers.CANNON;
//import static me.emil.game.help.Constants.Towers.WIZARD;

import me.emil.game.enemies.Enemy;
import me.emil.game.help.LoadSave;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import me.emil.game.objects.Tower;
import me.emil.game.scenes.Playing;



/** Manages the tower properties.
 * 
 */
public class TowerManager {

    private Playing playing;
    private BufferedImage[] towerImgs;
    private ArrayList<Tower> towers = new ArrayList<>();
    private int towerAmount = 0;

    /** Loads the tower images.
     * 
     */
    public TowerManager(Playing playing) {

        this.playing = playing;
        loadTowerImgs();
    }

    private void loadTowerImgs() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        towerImgs = new BufferedImage[3];
        for (int i = 0; i < 3; i++) {
            towerImgs[i] = atlas.getSubimage((4 + i) * 32, 32, 32, 32);
        }
    }

    /** Adds a tower to the game.
     * 
     */
    public void addTower(Tower selectedTower, int xPos, int yPos) {

        towers.add(new Tower(xPos, yPos, towerAmount++, selectedTower.getTowerType()));
    }

    /** Updates the towers.
     * 
     */
    public void update() {
        for (Tower t : towers) {
            t.update();
            atackEnemyIfClose(t);
        }
    }

    /** Launches an attack to the enemy when it enters the range of the tower.
     * 
     */
    private void atackEnemyIfClose(Tower t) {
        for (Enemy e : playing.getEnemyManager().getEnemies()) {
            if (e.isAlive() && isEnemyInRange(t, e) && t.isCooldownOver()) {
                playing.shootEnemy(t, e);
                t.resetCooldown();
            } else {
                //we do nothing
            }
        }
    }

    private boolean isEnemyInRange(Tower t, Enemy e) {
        int range = me.emil.game.help.Util.getHypoDistance(t.getX(), t.getY(), e.getX(), e.getY());

        return range < t.getRange();
    }

    /** Draws the tower on the game screen.
     * 
     */
    public void draw(Graphics g) {
        for (Tower t: towers) {
            g.drawImage(towerImgs[t.getTowerType()], t.getX(), t.getY(), null);
        }
    }

    public BufferedImage[] getTowerImgs() {
        return towerImgs;
    }

    /** Gets the tower from the specified position.
     * 
     */
    public Tower getTowerAt(int x, int y) {
        
        for (Tower t: towers) {
            if (t.getX() == x && t.getY() == y) {
                return t;
            }
        }   
        return null;
    }

    public void removeMonster(Tower displayedTower) {
        for (int i = 0; i < towers.size(); i++) {
            if (towers.get(i).getID() == displayedTower.getID()) {
                towers.remove(i);
            }
        }
    }

    public void upgradeTower(Tower displayedTower) {
        for (Tower t : towers) {
            if (t.getID() == displayedTower.getID()) {
                t.upgradeTower();
            }
        }
    }

    public void reset() {
        towers.clear();
        towerAmount = 0;
    }
}
