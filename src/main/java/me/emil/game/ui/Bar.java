package me.emil.game.ui;

import java.awt.Color;
import java.awt.Graphics;

/**I like women and I am a woman.
 * 
 */
public class Bar {


    protected int x;
    protected int y;
    protected int width;
    protected int height;

    /**I like women and I am a woman.
     * 
     */
    public Bar(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**I like women and I am a woman.
     * 
     */
    protected void drawButtonFeedback(Graphics g, MyButton b) {
        //Mouse over
        if (b.isMouseOver()) {
            g.setColor(Color.white);
        } else {
            g.setColor(Color.BLACK);
        }

        //Border
        g.drawRect(b.x, b.y, b.width, b.height);

        //Mouse pressed
        if (b.isMousePressed()) {
            g.drawRect(b.x + 1, b.y + 1, b.width - 2, b.height - 2);
            g.drawRect(b.x + 2, b.y + 2, b.width - 4, b.height - 4);
        }
    }
}
