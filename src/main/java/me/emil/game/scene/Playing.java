package me.emil.game.scene;

/** I like women.
 *
*/
public class Playing
{
    /*private int[][] lvl = new int[0][0];

    private final ActionBar actionBar;
    private int mouseX;
    private int mouseY;
    private final EnemyManager enemyManager;
    private final TowerManager towerManager;
    private final ProjectileManager projectileManager;
    private PathPoint start = new PathPoint(0, 0);
    private PathPoint end = new PathPoint(0, 0);
    private Tower selectedTower;
    private boolean gamePaused;
    public Playing(Game game) {
        super(game);

        loadDefaultLevel();

        actionBar = new ActionBar(game, 0, 640, 640, 160, this);

        enemyManager = new EnemyManager(this, start, end);
        towerManager = new TowerManager(this);
        projectileManager = new ProjectileManager(this);
        
        Track.BACKGROUND_MUSIC.playAndLoop();
    }
    
    private void loadDefaultLevel() {
        lvl = StorageUtil.getLevelData("new_level");
        List<PathPoint> points = StorageUtil.getLevelPathPoints("new_level");
        
        if(points.isEmpty())
            return;
        
        start = points.get(0);
        end = points.get(1);
    }

    public void setLevel(int[][] lvl) {
        this.lvl = lvl;
    }


    public void update() {

        if (!gamePaused) {
            updateTick();
            enemyManager.update();
    
            if (isAllEnemiesDead() && isThereMoreWaves()) {
                enemyManager.startWaveTimer();
                actionBar.addRubies(1000); //
                //check for timer
                if (isWaveTimerOver()) {
                    //increase wave timer
                    enemyManager.increaseWaveIndex();
                    enemyManager.getEnemies().clear();
                    enemyManager.resetEnemyIndex();
                }
            }
            
            if(enemyManager.isTimeForNewEnemy())
                enemyManager.spawnEnemy(enemyManager.getNextEnemy());
    
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

    public void setSelectedTile(Tower selectedTower) {
        
        this.selectedTower = selectedTower;
    }


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

    private void drawWaveInfos(Graphics g) {
        
    }

    private void drawHighLight(Graphics g) {
        g.setColor(Color.white);
        g.drawRect(mouseX, mouseY, 32, 32);
    }

    private void drawSelectedTower(Graphics g) {
        if (selectedTower != null) {
            g.drawImage(towerManager.getTowerImgs()[selectedTower.getTowerType().getId()],
                mouseX, mouseY, null);
        }
    }

    private void drawLevel(Graphics g) {
        for (int y = 0; y < lvl.length; y++) {
            for (int x = 0; x < lvl[y].length; x++) {
                int id = lvl[y][x];
                
                if (isAnimation(id))
                    g.drawImage(getSprite(id, animationIndex), x * 32, y * 32, null);
                else
                    g.drawImage(getSprite(id), x * 32, y * 32, null);
            }
        }
    }

  
    public TileType getTileType(int x, int y) {
        int xCord = x / 32;
        int yCord = y / 32;

        if (xCord < 0 || xCord > 19)
            return TileType.WATER;

        if (yCord < 0 || yCord > 19)
            return TileType.WATER;
        
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


    public void noTower() {
        Tower t = getTowerAt(mouseX, mouseY);
        if (t != null)
            actionBar.displayTower(t);
    }

    private void removeRubies(TowerType towerType) {
        actionBar.payForMonster(towerType);
    }

    public void removeMonster(Tower displayedTower) {
        towerManager.removeTower(displayedTower);
    }

    public void upgradeTower(Tower displayedTower) {
        towerManager.upgradeTower(displayedTower);
    }

    private Tower getTowerAt(int x, int y) {
        return towerManager.getTowerAt(x, y);
    }

    private boolean isTileGrass(int x, int y) {
        int id = lvl[y / 32][x / 32];
        TileType tileType = game.getTileManager().getTile(id).getTileType();
        return tileType == TileType.GRASS;
    }

    public void rewardPlayer(EnemyType enemyType) {
        actionBar.addRubies(enemyType.getReward());
    }

    public void setGamePaused(boolean gamePaused) {
        this.gamePaused = gamePaused;
    }


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

 
    public void reset() {
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
    }*/

}
