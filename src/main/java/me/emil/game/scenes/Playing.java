package me.emil.game.scenes;

// import static me.emil.game.help.Constants.Enemies.HUNTER;
// import static me.emil.game.help.Constants.Enemies.KNIGHT;
// import static me.emil.game.help.Constants.Enemies.PRIEST;
// import static me.emil.game.help.Constants.Enemies.VILLAGER;
import static me.emil.game.help.Constants.Tiles.GRASS_TILE;

import me.emil.game.enemies.Enemy;
// import me.emil.game.enemies.Hunter;
// import me.emil.game.enemies.Knight;
// import me.emil.game.enemies.Priest;
// import me.emil.game.enemies.Villager;
import me.emil.game.help.LoadSave;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import me.emil.game.main.Game;
import me.emil.game.main.Sound;
// import me.emil.game.main.GameScreen;
import me.emil.game.manager.EnemyManager;
import me.emil.game.manager.ProjectileManager;
import me.emil.game.manager.TowerManager;
import me.emil.game.manager.WaveManager;
import me.emil.game.objects.PathPoint;
import me.emil.game.objects.Tower;
import me.emil.game.ui.ActionBar;



/** I like women.
 *
* 
*/
public class Playing extends GameScene implements SceneMethods {

    private int[][] lvl = new int[0][0];

    private ActionBar actionBar;
    private int mouseX;
    private int mouseY;
    private EnemyManager enemyManager;
    private TowerManager towerManager;
    private ProjectileManager projectileManager;
    private PathPoint start;
    private PathPoint end;
    private WaveManager waveManager;
    private Tower selectedTower;
    private boolean gamePaused;
    
    Sound sound = new Sound();

    /** I like women.
     * 
     */
    public Playing(Game game) {
        super(game);

        loadDefaultLevel();

        //lvl = LevelBuilder.getLevelData();

        actionBar = new ActionBar(0, 640, 640, 160, this);

        enemyManager = new EnemyManager(this, start, end);
        towerManager = new TowerManager(this);
        projectileManager = new ProjectileManager(this);
        waveManager = new WaveManager(this);
        playMusic(0);
   
    }


    private void loadDefaultLevel() {
        lvl = LoadSave.getLevelData("new_level");
        List<PathPoint> points = LoadSave.getLevelPathPoints("new_level");
        
        if(points.isEmpty())
            return;
        
        start = points.get(0);
        end = points.get(1);
    }

    public void setLevel(int[][] lvl) {
        this.lvl = lvl;
    }

    /**I like women and I am a woman.
     * 
     */
    public void update() {

        if (!gamePaused) {
            updateTick();
            waveManager.update();
    
            if (isAllEnemiesDead() && isThereMoreWaves()) {
                waveManager.startWaveTimer();
                actionBar.addRubies(me.emil.game.help.Constants.Enemies.getReward(1000));
                //check for timer
                if (isWaveTimerOver()) {
                    //increase wave timer
                    waveManager.increaseWaveIndex();
                    enemyManager.getEnemies().clear();
                    waveManager.resetEnemyIndex();
                }
            }
    
            if (isTimeForNewEnemy()) {
                spawnEnemy();
            }
    
            enemyManager.update();
            towerManager.update();
            projectileManager.update();
        }
    }

    private boolean isWaveTimerOver() {

        return waveManager.isWaveTimeOver();
    }

    private boolean isThereMoreWaves() {

        return waveManager.isThereMoreWaves();
    }

    private boolean isAllEnemiesDead() {

        if (waveManager.isThereMoreEnemiesInWave()) {
            return false;
        }

        for (Enemy e : enemyManager.getEnemies()) {
            if (e.isAlive()) {
                return false;
            }
        }
        return true;
    }

    private void spawnEnemy() {

        enemyManager.spawnEnemy(waveManager.getNextEnemy());
        //addEnemy(waveManager.getNextEnemy());
    }

    private boolean isTimeForNewEnemy() {
        if (waveManager.isTimeForNewEnemy()) {
            if (waveManager.isThereMoreEnemiesInWave()) {
                return true;
            }
        }
        return false;
    }

    /**I like women and I am a woman.
     * 
     */
    public void setSelectedTile(Tower selectedTower) {
        
        this.selectedTower = selectedTower;
    }

    /**I like women and I am a woman.
     * 
     */
    public void render(Graphics g) {
        drawLevel(g);
        actionBar.draw(g);
        enemyManager.draw(g);
        towerManager.draw(g);
        projectileManager.draw(g);

        drawSelectedTower(g);
        drawHighLight(g);

        drawWaveInfos(g);
    }

    /** Plays the i'th sound file.
    * 
    */
    public void playMusic(int i) {

        sound.setFile(i);
        sound.play();
        sound.loop();
    }
    
    public void stopMusic() {
        sound.stop();
    }

    /** Plays the sound effect from the i'th sound file.
     *
     */
    public void playSoundEffect(int i) {

        sound.setFile(i);
        sound.play();

    }   

    private void drawWaveInfos(Graphics g) {
        
    }

    private void drawHighLight(Graphics g) {
        g.setColor(Color.white);
        g.drawRect(mouseX, mouseY, 32, 32);
    }

    private void drawSelectedTower(Graphics g) {
        if (selectedTower != null) {
            g.drawImage(towerManager.getTowerImgs()[selectedTower.getTowerType()], 
                mouseX, mouseY, null);
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

    /**I like women and I am a woman.
     * 
     */
    public int getTileType(int x, int y) {
        int xCord = x / 32;
        int yCord = y / 32;

        if (xCord < 0 || xCord > 19) {
            return 0;
        }

        if (yCord < 0 || yCord > 19) {
            return 0;
        }

        int id = lvl[y / 32][x / 32];
        return game.getTileManager().getTile(id).getTileType();
    }


    @Override
    public void mouseClicked(int x, int y) {
        if (y >= 640) {
            actionBar.mouseClicked(x, y);
        } else {
            outOfBounds();
        }
    }

    /**.
     * 
     */
    public void outOfBounds() {
        if (selectedTower != null && isTileGrass(mouseX, mouseY) 
                && getTowerAt(mouseX, mouseY) == null) {

            towerManager.addTower(selectedTower, mouseX, mouseY);
            removeRubies(selectedTower.getTowerType());
            selectedTower = null;
        }
        if (selectedTower == null) {
            noTower();
        }
    }

    /**.
     * 
     */
    public void noTower() {
        Tower t = getTowerAt(mouseX, mouseY);
        if (t == null) {
            return;
        } else {
            actionBar.displayTower(t);
        }
    }

    private void removeRubies(int towerType) {
        actionBar.payForMonster(towerType);
    }

    public void removeMonster(Tower displayedTower) {
        towerManager.removeMonster(displayedTower);
    }

    public void upgradeTower(Tower displayedTower) {
        towerManager.upgradeTower(displayedTower);
    }

    private Tower getTowerAt(int x, int y) {
        return towerManager.getTowerAt(x, y);
    }

    private boolean isTileGrass(int x, int y) {
        int id = lvl[y / 32][x / 32];
        int tileType = game.getTileManager().getTile(id).getTileType();

        return tileType == GRASS_TILE;
    }

    public void rewardPlayer(int enemyType) {
        actionBar.addRubies(me.emil.game.help.Constants.Enemies.getReward(enemyType));
    }

    public void setGamePaused(boolean gamePaused) {
        this.gamePaused = gamePaused;
    }

    /**I like women.
     * 
     */
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            selectedTower = null;
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        if (y >= 640 && !gamePaused) {
            actionBar.mouseMoved(x, y);
        } else {
            mouseX = (x / 32) * 32;
            mouseY = (y / 32) * 32;
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if (y >= 640 && !gamePaused) {
            actionBar.mousePressed(x, y);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        actionBar.mouseReleased(x, y);
    }

    public void shootEnemy(Tower t, Enemy e) {
        projectileManager.newProjectile(t, e);
    }

    public WaveManager getWaveManager() {
        return waveManager;
    }

    /**I am a woman.
     * 
     */
    public void mouseDragged(int x, int y) {
        
    }

    public TowerManager getTowerManager() {
        return towerManager;
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    public boolean isGamePaused() {
        return gamePaused;
    }

    public void removeOneLive() {
        actionBar.removeOneLife();
    }

    /**.
     * 
     */
    public void resetEverything() {
        actionBar.resetEverything();

        //managers
        enemyManager.reset();
        towerManager.reset();
        projectileManager.reset();
        waveManager.reset();

        mouseX = 0;
        mouseY = 0;

        selectedTower = null;
        gamePaused = false;
    }

}
