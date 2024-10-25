package me.emil.game.enemies;

import static me.emil.game.help.Constants.Direction.DOWN;
import static me.emil.game.help.Constants.Direction.LEFT;
import static me.emil.game.help.Constants.Direction.RIGHT;
import static me.emil.game.help.Constants.Direction.UP;

import java.awt.Rectangle;
import me.emil.game.manager.EnemyManager;


/** Determines all the properties of an enemy.
 * 
 */
public abstract class Enemy {

    protected EnemyManager enemyManager;
    protected float x;
    protected float y;
    protected Rectangle bounds;
    protected int health;
    protected int id;
    protected int enemyType;
    protected int lastDir;
    protected int maxHealth;
    protected boolean alive = true;
    protected int slowTickLimit = 120;
    protected int slowTick = slowTickLimit;

    /** Determines the starting values of the enemy.
     * 
     */
    public Enemy(float x, float y, int id, int enemyType, EnemyManager enemyManager) {

        this.x = x;
        this.y = y;
        this.id = id;
        this.enemyType = enemyType;
        this.enemyManager = enemyManager;
        bounds = new Rectangle((int) x, (int) y, 32, 32); 
        lastDir = -1; 
        setStartHealth();
    }

    private void setStartHealth() {
        health = me.emil.game.help.Constants.Enemies.getStartHealth(enemyType);
        maxHealth = health;
    }

    /** Takes the amount of damage the enemy received as an input.
     *  Reduces the health value of the enemy.
     * 
     */
    public void hurt(int dmg) {
        this.health -= dmg;
        if (health <= 0) {
            alive = false;
            enemyManager.rewardPlayer(enemyType);
        }
    }

    /**I am a lesbian.
     * 
     */
    public void slow() {

        slowTick = 0;
    }

    /** Receives the variables speed and direction as input.
     *  Returns the change of direction so that the enemy can move.  
     * 
     */
    public void move(float speed, int dir) {
        lastDir = dir;
        if (slowTick < slowTickLimit) {
            slowTick++;
            speed *= 0.5f;
        }
        
        changeDir(speed, dir);

        updateHitBox();
    }

    /**.
     * 
     */
    public void changeDir(float speed, int dir) {
        switch (dir) {
            case LEFT:
                this.x -= speed;
                break;
            case UP:
                this.y -= speed;
                break;
            case RIGHT:
                this.x += speed;
                break;
            case DOWN:
                this.y += speed;
                break;
            default:
                break;
        }
    }

    private void updateHitBox() { 
        bounds.x = (int) x;
        bounds.y = (int) y;
    }

    /** Sets the position of the enemy in the game screen.
     * 
     */
    public void setPos(int x, int y) {
        this.x = (float) x;
        this.y = (float) y;
    }

    /**Kills enemy at the end of the track.
     * 
     */
    public void kill() {
        alive = false;
        health = 0;
    }

    public float getHealthBarFlaot() {
        return health / (float) maxHealth;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public int getHealth() {
        return health;
    }

    public int getID() {
        return id;
    }

    public int getEnemyType() {
        return enemyType;
    }

    public int getLastDir() {
        return lastDir;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isSlowed() {
        return slowTick < slowTickLimit;
    }

}
