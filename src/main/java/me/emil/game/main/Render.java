package me.emil.game.main;

import java.awt.Graphics;



/** Sets the rendering properties.
 * 
 */
public class Render {

    private Game game;


    /** Sets the game variable.
    * 
    */
    public Render(Game game) {
        this.game = game;
    }

    /** Sets the graphics for each game state.
    * 
    */
    public void render(Graphics g) {
        switch (GameStates.gameState) {
            case MENU:
                game.getMenu().render(g);
                break;
            case PLAYING:
                game.getPlaying().render(g);
                break;
            case SETTINGS:
                game.getSettings().render(g);
                break;
            case EDITING:
                game.getEditing().render(g);
                break;
            case GAMEOVER:
                game.getGameOver().render(g);
                break;
            default:
                break;
        }  
    }
}
