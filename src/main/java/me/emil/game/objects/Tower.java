package me.emil.game.objects;

import static me.emil.game.help.Constants.Towers.SKELETON;
import static me.emil.game.help.Constants.Towers.VAMPIRE;
import static me.emil.game.help.Constants.Towers.WITCH;

/**I like woman and I am a woman.
 * 
 */
public class Tower {

    private int x;
    private int y;
    private int id;
    private int towerType;
    private int cdTick;
    private int dmg;

    private float range;
    private float cooldown;

    private int tier;

    /**I like woman and I am a woman.
     * 
     */
    public Tower(int x, int y, int id, int towerType) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.towerType = towerType;
        tier = 1;
        setDefoultDmg();
        setDefoultRange();
        setDefoultCooldown();
    }

    public void upgradeTower() {
        this.tier++;

        switch (towerType) {
            case SKELETON:
                dmg += 2;
                range += 20;
                cooldown -= 5;
                break;
            case WITCH:
                dmg += 5;
                range += 20;
                cooldown -= 15;
                break;
            case VAMPIRE:
                dmg += 1;
                range += 20;
                cooldown -= 10;
                break;
            default:
                break;
        }
    }
    
    public int getTier() {
        return tier;
    }

    public void update() {
        cdTick++;
    }

    public boolean isCooldownOver() {
        return cdTick >= cooldown;
    }

    public void resetCooldown() {
        cdTick = 0;
    }

    private void setDefoultCooldown() {
        cooldown = me.emil.game.help.Constants.Towers.getDefaultCooldown(towerType);
    }

    private void setDefoultRange() {
        range = me.emil.game.help.Constants.Towers.getDefaultRange(towerType);
    }

    public void setDefoultDmg() {
        dmg = me.emil.game.help.Constants.Towers.getStartDmg(towerType);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public int getTowerType() {
        return towerType;
    }

    public void setTowerType(int towerType) {
        this.towerType = towerType;
    }

    public int getDmg() {
        return dmg;
    }

    public float getRange() {
        return range;
    }

    public float getCooldown() {
        return cooldown;
    }



}
