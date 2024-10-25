package me.emil.game.manager;

import static me.emil.game.help.Constants.Projectile.BATS;
import static me.emil.game.help.Constants.Projectile.BONES;
import static me.emil.game.help.Constants.Projectile.CAULDRON;
import static me.emil.game.help.Constants.Towers.SKELETON;
import static me.emil.game.help.Constants.Towers.VAMPIRE;
import static me.emil.game.help.Constants.Towers.WITCH;

import me.emil.game.enemies.Enemy;
import me.emil.game.help.LoadSave;
import java.awt.Graphics;
import java.awt.Graphics2D;
// import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
// import javax.swing.ButtonGroup;
import me.emil.game.objects.Projectile;
import me.emil.game.objects.Tower;
import me.emil.game.scenes.Playing;

/** Manages the behaviour of the projectiles in the game.
 * 
 */
public class ProjectileManager {

    private Playing playing;
    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private ArrayList<Explosion> explosions = new ArrayList<>();
    private BufferedImage[] projImgs;
    private BufferedImage[] explodImgs;
    private int projId = 0;

    /** Adds the images of the projectiles to the game.
     * 
     */
    public ProjectileManager(Playing playing) {
        this.playing = playing;
        importImgs();
    }

    private void importImgs() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        projImgs = new BufferedImage[3];

        for (int i = 0; i < 3; i++) {
            projImgs[i] = atlas.getSubimage((7 + i) * 32, 32, 32, 32);
        }
        importExplosion(atlas);
    }

    private void importExplosion(BufferedImage atlas) {

        explodImgs = new BufferedImage[7];

        for (int i = 0; i < 7; i++) {
            explodImgs[i] = atlas.getSubimage(i * 32, 64, 32, 32);
        }
    }

    /** Links a projectile to a tower.
     * 
     */
    public void newProjectile(Tower t, Enemy e) {

        int type = getProjType(t);

        int xDist = (int) (t.getX() - e.getX());
        int yDist = (int) (t.getY() - e.getY());
        int totDist = Math.abs(xDist) + Math.abs(yDist);

        float xPer = (float) Math.abs(xDist) / totDist;

        float xSpeed = xPer * me.emil.game.help.Constants.Projectile.getSpeed(type);
        float ySpeed = me.emil.game.help.Constants.Projectile.getSpeed(type) - xSpeed;

        if (t.getX() > e.getX()) {
            xSpeed *= -1;
        } 
        if (t.getY() > e.getY()) {
            ySpeed *= -1; 
        }
        
        float rotate = 0;

        if (type == BONES) {
            float arcValue = (float) Math.atan(yDist / (float) xDist);
            rotate = (float) Math.toDegrees(arcValue);
            if (xDist < 0) {
                rotate += 180;
            }
        }
        
        for (Projectile p : projectiles) {
            if (!p.isActive()) { 
                if (p.getProjectileType() == type) {
                    p.reuse(t.getX() + 16, t.getY() + 16, xSpeed, ySpeed, t.getDmg(), rotate);
                    return;
                }
            }
        }

        projectiles.add(new Projectile(t.getX() + 16, t.getY() + 16, 
            xSpeed, ySpeed, t.getDmg(), rotate, projId++, type));
    }

    private int getProjType(Tower t) {
        switch (t.getTowerType()) {
            case SKELETON:
                return BONES;
            case WITCH:
                return CAULDRON;
            case VAMPIRE:
                return BATS;
            default: 
                return 0;
        }
    }

    /** Sets the properties of an explosion.
     * 
     */
    public class Explosion {

        private Point2D.Float pos;
        private int explodTick = 0;
        private int explodIndex = 0;

        public Explosion(Point2D.Float pos) {
            this.pos = pos;
        }
        
        /** Updates the state of the explosion.
         * 
         */
        public void update() {
            explodTick++;
            if (explodTick >= 12) {
                explodTick = 0;
                explodIndex++;
            }
        }

        public int getIndex() {
            return explodIndex;
        }

        public Point2D.Float getPos() {
            return pos;
        }
    }

    /** Updates the state of the projectiles.
     * 
     */
    public void update() {
        for (Projectile p : projectiles) {
            updateProjectile(p);
        }

        for (Explosion e : explosions) {
            if (e.getIndex() < 7) {
                e.update();
            }
        }
    }

    /** Updates the behaviour of the projectile.
     * 
     */
    public void updateProjectile(Projectile p) {

        if (p.isActive()) {
            p.move();
            if (isProjHittingEnemy(p)) {
                p.setActive(false);
                if (p.getProjectileType() == CAULDRON) {
                    explosions.add(new Explosion(p.getPos()));
                    explodeOnEnemies(p);
                }
            } else if (isProjOutOfBounds(p)) {
                p.setActive(false);
            }
        }
    }

    private void explodeOnEnemies(Projectile p) {

        for (Enemy e : playing.getEnemyManager().getEnemies()) {
            if (e.isAlive()) {
                float radius = 40.0f;

                float xDist = Math.abs(p.getPos().x - e.getX());
                float yDist = Math.abs(p.getPos().y - e.getY());

                float realDist = (float) Math.hypot(xDist, yDist);

                if (realDist <= radius) {
                    e.hurt(p.getDmg());
                }
            }
        }
    }

    private boolean isProjHittingEnemy(Projectile p) {
        for (Enemy e : playing.getEnemyManager().getEnemies()) {
            if (e.isAlive() && e.getBounds().contains(p.getPos())) {
                e.hurt(p.getDmg());
                if (p.getProjectileType() == BATS) {
                    e.slow();
                }

                return true;
            }
        }
        return false;
    }

    private boolean isProjOutOfBounds(Projectile p) {
        if (p.getPos().x > 0 && p.getPos().x <= 640 && p.getPos().y >= 0 && p.getPos().y <= 800) {
            return false;
        }
        return true;
    }

    /** Sets the properties of the projectiles.
     * 
     */
    public void draw(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        for (Projectile p : projectiles) {
            if (p.isActive()) {
                if (p.getProjectileType() == BONES) {
                    g2d.translate(p.getPos().x, p.getPos().y);
                    g2d.rotate(Math.toRadians(90));
                    g2d.drawImage(projImgs[p.getProjectileType()], -16, -16, null);
                    g2d.rotate(-Math.toRadians(90));
                    g2d.translate(-p.getPos().x, -p.getPos().y);
                } else {
                    g2d.drawImage(projImgs[p.getProjectileType()], 
                        (int) p.getPos().x - 16, (int) p.getPos().y - 16, null);
                }
                
            } 
        }

        drawExplosions(g2d);
        
    }

    private void drawExplosions(Graphics2D g2d) {
        for (Explosion e : explosions) {
            if (e.getIndex() < 7) {
                g2d.drawImage(explodImgs[e.getIndex()], 
                    (int) e.getPos().x - 16, (int) e.getPos().y - 16, null);
            }
        }
    }

    /**Resets everything for when retry is pressed.
    */
    public void reset() {
        projectiles.clear();
        explosions.clear();

        projId = 0;
    }


}
