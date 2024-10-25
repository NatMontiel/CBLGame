package me.emil.game.enemies;

import static me.emil.game.help.Constants.Enemies.PRIEST;

import me.emil.game.manager.EnemyManager;

/** Creates a bat using the properties of the me.emil.game.enemies class.
 * 
 */
public class Priest extends Enemy {

    /** Places the bat at the indicated coordinates on the game screen.
     * 
     */
    public Priest(float x, float y, int id, EnemyManager em) {
        super(x, y, id, PRIEST, em);
    }
}
