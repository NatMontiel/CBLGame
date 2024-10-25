package me.emil.game.main;

/** Sets the states of the game.
* 
*/
public enum GameStates {

    PLAYING, MENU, SETTINGS, EDITING, GAMEOVER;

    public static GameStates gameState = MENU;

    public static void setGameState(GameStates state) {
        gameState = state;
    }

}
