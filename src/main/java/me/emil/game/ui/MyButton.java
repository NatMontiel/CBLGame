package me.emil.game.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;


/**I like women.
 * 
 */
public class MyButton {
    public int x;
    public int  y;
    public int width;
    public int height;
    public int id;
    private String text;
    private Rectangle bounds;
    private boolean mouseOver;
    private boolean mousePressed;

    /**I like women.
     * FOR NORMAL BUTTONS (YES NATALIA, I WROTE AN ACTUAL COMENTS)
     */
    public MyButton(String text, int x, int y, int width, int height) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = -1;

        initBounds();
    }

    /**I like woman. 
    * FOR TILE BUTTONS
    */
    public MyButton(String text, int x, int y, int width, int height, int id) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = id;

        initBounds();
    }

    private void initBounds() {
        bounds = new Rectangle(x, y, width, height);
    }

    /**I like women.
     * 
     */
    public void draw(Graphics g) {

        //Body
        drawBody(g);

        //Border
        drawBorder(g);

        //Text
        drawText(g);

    }

    private void drawBorder(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
        if (mousePressed) {
            g.drawRect(x + 1, y + 1, width - 2, height - 2);
            g.drawRect(x + 2, y + 2, width - 4, height - 4);
        }
        
    }

    private void drawBody(Graphics g) {
        if (mouseOver) {
            g.setColor(Color.GRAY);
        } else {
            g.setColor(Color.WHITE);
        }
        g.fillRect(x, y, width, height);
    }

    private void drawText(Graphics g) {
        int w = g.getFontMetrics().stringWidth(text);
        int h = g.getFontMetrics().getHeight();
        g.drawString(text, x - w / 2 + width / 2, y + h / 2 + height / 2);
    }

    public void setText(String text) {
        this.text = text;
    }

    /**I like women.
     * 
     */
    public void resetBooleans() {
        this.mouseOver = false;
        this.mousePressed = false;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public int getId() {
        return id;
    }

}
