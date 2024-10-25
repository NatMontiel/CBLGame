package me.emil.game.inputs;

import static me.emil.game.main.GameStates.EDITING;
// import static me.emil.game.main.GameStates.MENU;
import static me.emil.game.main.GameStates.PLAYING;
// import static me.emil.game.main.GameStates.SETTINGS;
// import static me.emil.game.main.GameStates.PLAYING;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import me.emil.game.main.Game;
import me.emil.game.main.GameStates;

/** Allows the program to take input from the keyboard.
 * 
*/
public class KeyboardListener implements KeyListener {

    private Game game;

    public KeyboardListener(Game game) {
        this.game = game;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (GameStates.gameState == EDITING) {
            game.getEditing().keyPressed(e);;
        } else if (GameStates.gameState == PLAYING) {
            game.getPlaying().keyPressed(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

}
