package me.emil.game.enemies;

import static me.emil.game.help.Constants.Enemies.VILLAGER;

import me.emil.game.manager.EnemyManager;

/** Uses the properties of the enemy class to make a wolf.
 * 
 */
public class Villager extends Enemy {

    /** Determines the starting values of the wolf.
     * 
     */
    public Villager(float x, float y, int id, EnemyManager em) {
        super(x, y, id, VILLAGER, em);
    }
}
