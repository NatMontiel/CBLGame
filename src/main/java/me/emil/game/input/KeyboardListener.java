package me.emil.game.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import me.emil.game.main.Game;

/** Allows the program to take input from the keyboard.
 * 
*/
public class KeyboardListener implements KeyListener {

    private final Game game;

    public KeyboardListener(Game game) {
        this.game = game;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        game.getGameScene().keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
