package me.emil.game.manager;

import static me.emil.game.help.Constants.Direction.DOWN;
import static me.emil.game.help.Constants.Direction.LEFT;
import static me.emil.game.help.Constants.Direction.RIGHT;
import static me.emil.game.help.Constants.Direction.UP;
import static me.emil.game.help.Constants.Enemies.HUNTER;
import static me.emil.game.help.Constants.Enemies.KNIGHT;
import static me.emil.game.help.Constants.Enemies.PRIEST;
import static me.emil.game.help.Constants.Enemies.VILLAGER;
import static me.emil.game.help.Constants.Enemies.getSpeed;
import static me.emil.game.help.Constants.Tiles.ROAD_TILE;

import me.emil.game.enemies.Enemy;
import me.emil.game.enemies.Hunter;
import me.emil.game.enemies.Knight;
import me.emil.game.enemies.Priest;
import me.emil.game.enemies.Villager;
import me.emil.game.help.LoadSave;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import me.emil.game.objects.PathPoint;
import me.emil.game.scenes.Playing;


/** Manages the behaviour of the me.emil.game.enemies in the game.
 * 
 */
public class EnemyManager {

    private Playing playing;
    private BufferedImage[] enemyImgs;
    private ArrayList<Enemy> enemies = new ArrayList<>(); 
    private PathPoint start;
    private PathPoint end;
    private int hpBarWidth = 20;
    private BufferedImage slowEffect;

    /** Sets the start and end of the path the me.emil.game.enemies take.
    * 
    */
    public EnemyManager(Playing playing, PathPoint start, PathPoint end) {
        this.playing = playing;
        enemyImgs = new BufferedImage[4];
        this.start = start;
        this.end = end;

        loadEffectImg();
        loadEnemyImgs();
    }

    private void loadEffectImg() {
        slowEffect = LoadSave.getSpriteAtlas().getSubimage(32 * 9, 32 * 2, 32, 32);
    }

    private void loadEnemyImgs() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        for (int i = 0; i < 4; i++) {
            enemyImgs[i] = atlas.getSubimage(32 * i, 32, 32, 32);
        }
    }

    /** Updates the me.emil.game.enemies while they are alive.
     * 
     */
    public void update() {
        for (Enemy e : enemies) {
            if (e.isAlive()) {
                updateEnemyMove(e);
            }
        }
    }

    /** Determines where the me.emil.game.enemies can move.
     *  Kills an enemy if it reaches the end of the path.
     * 
     */
    public void updateEnemyMove(Enemy e) {

        if (e.getLastDir() == -1) {
            setNewDirectionAndMove(e);
        }

        int newX = (int) (e.getX() + getSpeedAndWidth(e.getLastDir(), e.getEnemyType()));
        int newY = (int) (e.getY() + getSpeedAndHeight(e.getLastDir(), e.getEnemyType()));

        if (getTileType(newX, newY) == ROAD_TILE) {
            e.move(getSpeed(e.getEnemyType()), e.getLastDir());
        } else if (isAtEnd(e)) {
            e.kill();
            playing.removeOneLive();
        } else {
            setNewDirectionAndMove(e);
        }
    }

    private void setNewDirectionAndMove(Enemy e) {
        int dir = e.getLastDir();

        int xCord = (int) (e.getX() / 32);
        int yCord = (int) (e.getY() / 32);

        fixEnemyOffsetTile(e, dir, xCord, yCord);

        if (isAtEnd(e)) {
            return;
        }

        if (dir == LEFT || dir == RIGHT) {
            leftRight(e);
        } else {
            upDown(e);
        }
    }

    private void leftRight(Enemy e) {
        int newY = (int) (e.getY() + getSpeedAndHeight(UP, e.getEnemyType()));
        if (getTileType((int) e.getX(), newY) == ROAD_TILE) {
            e.move(getSpeed(e.getEnemyType()), UP);
        } else {
            e.move(getSpeed(e.getEnemyType()), DOWN);
        }
    }

    private void upDown(Enemy e) {

        int newX = (int) (e.getX() + getSpeedAndWidth(RIGHT, e.getEnemyType()));
        if (getTileType(newX, (int) e.getY()) == ROAD_TILE) {
            e.move(getSpeed(e.getEnemyType()), RIGHT);
        } else {
            e.move(getSpeed(e.getEnemyType()), LEFT);
        }
    }

    private void fixEnemyOffsetTile(Enemy e, int dir, int xCord, int yCord) {
        switch (dir) {
            case RIGHT:
                if (xCord < 19) {
                    xCord++;
                }
                break;
            case DOWN:
                if (yCord < 19) {
                    yCord++;
                }
                break;
            default:
                break;
        }
        
        e.setPos(xCord * 32, yCord * 32);
    }

    private boolean isAtEnd(Enemy e) {
        if (e.getX() == end.getxCord() * 32) {
            if (e.getY() == end.getyCord() * 32) {
                return true;
            }
        }
        return false;
    }

    private int getTileType(int x, int y) {
        return playing.getTileType(x, y);
    }

    private float getSpeedAndHeight(int dir, int enemyType) {

        if (dir == UP) {
            return -getSpeed(enemyType);
        } else if (dir == DOWN) {
            return getSpeed(enemyType) + 32;
        }

        return 0;
    }

    private float getSpeedAndWidth(int dir, int enemyType) {
        if (dir == LEFT) {
            return -getSpeed(enemyType);
        } else if (dir == RIGHT) {
            return getSpeed(enemyType) + 32;
        }

        return 0;

    }

    /** Adds the me.emil.game.enemies to the game.
     * 
     */
    public void addEnemy(int enemyType) {

        int x = start.getxCord() * 32;
        int y = start.getyCord() * 32;
        switch (enemyType) {
            case VILLAGER:
                enemies.add(new Villager(x, y, 0, this));
                break;
            case PRIEST:
                enemies.add(new Priest(x, y, 0, this));
                break;
            case KNIGHT:
                enemies.add(new Knight(x, y, 0, this));
                break;
            case HUNTER:
                enemies.add(new Hunter(x, y, 0, this));
                break;
            default:
                break;
        }
    }

    public void spawnEnemy(int nextEnemy) {
        addEnemy(nextEnemy);
    }

    /** Draws the health bar above the me.emil.game.enemies.
     * 
     */
    public void draw(Graphics g) {
        for (Enemy e : enemies) {
            if (e.isAlive()) {
                drawEnemy(e, g);
                drawHealthBar(e, g);
                drawEffects(e, g);
            }
        }
    }

    private void drawEffects(Enemy e, Graphics g) {
        
        if (e.isSlowed()) {
            g.drawImage(slowEffect, (int) e.getX(), (int) e.getY(), null);
        }
    }

    private void drawHealthBar(Enemy e, Graphics g) {
        g.setColor(Color.red);
        g.fillRect((int) e.getX() + 16 - (getNewBatWidth(e) / 2), 
            (int) e.getY() - 10, getNewBatWidth(e), 3);
    }

    private int getNewBatWidth(Enemy e) {
        return (int) (hpBarWidth * e.getHealthBarFlaot());
    }

    private void drawEnemy(Enemy e, Graphics g) {
        g.drawImage(enemyImgs[e.getEnemyType()], (int) e.getX(), (int) e.getY(), null);
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    /**I am a lesbian.
     * 
     */
    public int getAmountOfAliveEnemies() {
        int size = 0;
        for (Enemy e : enemies) {
            if (e.isAlive()) {
                size++;
            }
        }
        return size;
    }

    public void rewardPlayer(int enemyType) {
        playing.rewardPlayer(enemyType);
    }

    public void reset() {
        enemies.clear();
    }
}
