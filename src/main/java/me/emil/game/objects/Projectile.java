package me.emil.game.objects;

import java.awt.geom.Point2D;
//import java.awt.geom.Point2D.Float;

/**I like women and I am a woman.
 * 
 */
public class Projectile {

    private Point2D.Float pos;
    private int id;
    private int projectileType;
    private int dmg;

    private float xSpeed;
    private float ySpeed;
    private float rotation;
    private boolean active = true;

    /** Sets the starting values of the projectiles.
     * 
     */
    public Projectile(float x, float y, float xSpeed, float ySpeed,
        int dmg, float rotation, int id, int projectileType) {
        pos = new Point2D.Float(x, y);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.dmg = dmg;
        this.rotation = rotation;
        this.id = id;
        this.projectileType = projectileType;
    }

    /**.
     * 
     */
    public void reuse(int x, int y, float xSpeed, float ySpeed, int dmg, float rotate) {
        pos = new Point2D.Float(x, y);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.dmg = dmg;
        this.rotation = rotate;
        active = true;
    }

    /** Determines how the projectile will move.
     * 
     */
    public void move() {
        pos.x += xSpeed;
        pos.y += ySpeed;
    }

    public Point2D.Float getPos() {
        return pos;
    }

    public void setPos(Point2D.Float pos) {
        this.pos = pos;
    }

    public int getId() {
        return id;
    }

    public int getProjectileType() {
        return projectileType;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getDmg() {
        return dmg;
    }

    public float getRotation() {
        return rotation;
    }

}
