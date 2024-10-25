package me.emil.game.scenes;

import java.awt.Color;
import java.awt.Graphics;
import me.emil.game.main.Game;
import me.emil.game.main.GameStates;
import me.emil.game.ui.MyButton;

/** I like women.
* 
*/
public class Settings extends GameScene implements SceneMethods {

    private MyButton bMenu;

    /**I like women.
     *
     */
    public Settings(Game game) {
        super(game);
        initButtons();
    }

    private void initButtons() {
        bMenu = new MyButton("Menu", 2, 2, 100, 30);
    }

    @Override
    public void render(Graphics g) {
        
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, 640, 640);
        drawButtons(g);

    }

    private void drawButtons(Graphics g) {
        this.bMenu.draw(g);
    }

    @Override
    public void mouseClicked(int x, int y) {
        if (this.bMenu.getBounds().contains(x, y)) {
            GameStates.setGameState(GameStates.MENU);
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        this.bMenu.setMouseOver(false);
        if (this.bMenu.getBounds().contains(x, y)) {
            this.bMenu.setMouseOver(true);
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if (this.bMenu.getBounds().contains(x, y)) {
            this.bMenu.setMousePressed(true);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        this.resetButtons();
    }

    private void resetButtons() {
        this.bMenu.resetBooleans();
    }

    @Override
    public void mouseDragged(int x, int y) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseDragged'");
    }

}
