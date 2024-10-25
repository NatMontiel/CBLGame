package me.emil.game.enemies;

import static me.emil.game.help.Constants.Enemies.KNIGHT;

import me.emil.game.manager.EnemyManager;

/** Uses the properties of the enemy class to create a knight.
 * 
 */
public class Knight extends Enemy {

    /** Determines the sterting values of the knight.
     * 
     */
    public Knight(float x, float y, int id, EnemyManager em) {
        super(x, y, id, KNIGHT, em);
    }
}
