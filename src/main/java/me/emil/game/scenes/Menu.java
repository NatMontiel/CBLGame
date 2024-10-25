package me.emil.game.scenes;

import java.awt.Graphics;
import me.emil.game.main.Game;
import me.emil.game.main.GameStates;
import me.emil.game.ui.MyButton;


/** I like women.
* 
*/
public class Menu extends GameScene implements SceneMethods {

    private MyButton bPlaying;
    private MyButton bSettings;
    private MyButton bQuit;
    private MyButton bEditing;


    /** I like women.
     * 
     */
    public Menu(Game game) {
        super(game);;
        initButtons();
    }

    private void initButtons() {

        int w = 150;
        int h = w / 3;
        int x = 640 / 2 - w / 2;
        int y = 150;
        int yOffset = 100;

        bPlaying = new MyButton("Play", x, y, w, h);
        bEditing = new MyButton("Editing", x, y + yOffset, w, h);
        bSettings = new MyButton("Settings", x, y + yOffset * 2, w, h);
        bQuit = new MyButton("Quit", x, y + yOffset * 3, w, h);
    }

    /**I like women.
     * 
     */
    public void render(Graphics g) {
        
        drawButtons(g);
    }


    private void drawButtons(Graphics g) {
        bPlaying.draw(g);
        bEditing.draw(g);
        bSettings.draw(g);
        bQuit.draw(g);
    }


    /**I like women.
     * 
     */
    public void mouseClicked(int x, int y) {
        
        if (bPlaying.getBounds().contains(x, y)) {
            GameStates.setGameState(GameStates.PLAYING);
        } else if (bEditing.getBounds().contains(x, y)) {
            GameStates.setGameState(GameStates.EDITING);
        } else if (bSettings.getBounds().contains(x, y)) {
            GameStates.setGameState(GameStates.SETTINGS);
        } else if (bQuit.getBounds().contains(x, y)) {
            System.exit(0);
        }
    }

    /**I like women.
     * 
     */
    public void mouseMoved(int x, int y) {
        bPlaying.setMouseOver(false);
        bEditing.setMouseOver(false);
        bSettings.setMouseOver(false);
        bQuit.setMouseOver(false);

        if (bPlaying.getBounds().contains(x, y)) {
            bPlaying.setMouseOver(true);
        } else if (bEditing.getBounds().contains(x, y)) {
            bEditing.setMouseOver(true);
        } else if (bSettings.getBounds().contains(x, y)) {
            bSettings.setMouseOver(true);
        } else if (bQuit.getBounds().contains(x, y)) {
            bQuit.setMouseOver(true);
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if (bPlaying.getBounds().contains(x, y)) {
            bPlaying.setMousePressed(true);
        } else if (bEditing.getBounds().contains(x, y)) {
            bSettings.setMousePressed(true);
        } else if (bSettings.getBounds().contains(x, y)) {
            bSettings.setMousePressed(true);
        } else if (bQuit.getBounds().contains(x, y)) {
            bQuit.setMousePressed(true);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        resetButtons();
    }

    private void resetButtons() {
        bPlaying.resetBooleans();
        bEditing.resetBooleans();
        bSettings.resetBooleans();
        bQuit.resetBooleans();
    }

    @Override
    public void mouseDragged(int x, int y) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseDragged'");
    }


}
