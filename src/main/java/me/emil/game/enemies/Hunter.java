package me.emil.game.enemies;

import static me.emil.game.help.Constants.Enemies.HUNTER;

import me.emil.game.manager.EnemyManager;

/** Uses the properties of the class me.emil.game.enemies to make an orc.
 * 
 */
public class Hunter extends Enemy {

    /** Determines the starting values of the orc.
     * 
     */
    public Hunter(float x, float y, int id, EnemyManager em) {
        super(x, y, id, HUNTER, em);
    }
}
