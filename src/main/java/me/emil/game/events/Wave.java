package me.emil.game.events;

import java.util.ArrayList;

/**.
 * 
 */
public class Wave {

    private ArrayList<Integer> enemyList;

    public Wave(ArrayList<Integer> enemList) {
        this.enemyList = enemList;
    }
    
    public ArrayList<Integer> getEnemyList() {
        return enemyList;
    }

}
