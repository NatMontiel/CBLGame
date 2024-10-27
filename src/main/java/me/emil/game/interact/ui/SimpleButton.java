package me.emil.game.interact.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class SimpleButton
{
    public int id = -1;
    public int x;
    public int y;
    public int width;
    public int height;
    private String text;
    private Rectangle bounds;
    private boolean mouseOver;
    private boolean mousePressed;

    public SimpleButton(String text, int x, int y, int width, int height) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        initBounds();
    }
    
    public SimpleButton(String text, int x, int y, int width, int height, int id) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = id;

        initBounds();
    }
    
    public String getText()
    {
        return text;
    }
    
    private void initBounds() {
        bounds = new Rectangle(x, y, width, height);
    }
    
    public int getId() {
        return id;
    }

    public final void render(Graphics graphics) {
        if(!doRender())
            return;
        
        //Body
        drawBody(graphics);

        //Border
        drawBorder(graphics);

        //Text
        drawText(graphics);
        
        onRender(graphics);
    }
    
    protected boolean doRender() {
        return true;
    }
    
    protected void onRender(Graphics graphics) {}
    
    private void drawBorder(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
        
        if (mousePressed) {
            g.drawRect(x + 1, y + 1, width - 2, height - 2);
            g.drawRect(x + 2, y + 2, width - 4, height - 4);
        }
    }

    private void drawBody(Graphics g) {
        if (mouseOver)
            g.setColor(Color.GRAY);
        else
            g.setColor(Color.WHITE);
        
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
    
    public abstract void onClick();
    
    public void onMouseMoved() {
        setMouseOver(true);
    }
    
    public void onMousePressed() {
        setMousePressed(true);
    }
    
    public void onMouseReleased() {
        resetBooleans();
    }
}
