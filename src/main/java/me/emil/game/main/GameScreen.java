package me.emil.game.main;

import me.emil.game.inputs.KeyboardListener;
import me.emil.game.inputs.MyMouseListener;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;


/** Sets the properties of the game screen.
* 
*/
public class GameScreen extends JPanel {

    private Game game;
    private Dimension size;

    private MyMouseListener myMouseListener;
    private KeyboardListener keyboardListener;


    /** Sets the size of the game screen.
    * 
    */
    public GameScreen(Game game) {
        this.game = game;
        setPanelSize();

    }
    
    /** Sets the me.emil.game.inputs the screen can receive.
     * 
     */
    public void initInputs() {
        myMouseListener = new MyMouseListener(game);
        keyboardListener = new KeyboardListener(game);

        addMouseListener(myMouseListener);
        addMouseMotionListener(myMouseListener);
        addKeyListener(keyboardListener);

        requestFocus();
    }

    private void setPanelSize() {
        size = new Dimension(640, 800);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }

    

    /** Sets the graphics of the game.
     * 
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        game.getRender().render(g);
    }

    


}