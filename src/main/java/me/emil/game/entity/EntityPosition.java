package me.emil.game.entity;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class EntityPosition
{
    private final int boundWidth;
    private final int boundHeight;
    private float x;
    private float y;
    private float xSpeed;
    private float ySpeed;
    
    public EntityPosition(float x, float y)
    {
        this(x, y, 0, 0);
    }
    
    public EntityPosition(float x, float y, int boundWidth, int boundHeight)
    {
        this.x = x;
        this.y = y;
        this.boundWidth = boundWidth;
        this.boundHeight = boundHeight;
    }
    
    public int getBoundWidth()
    {
        return boundWidth;
    }
    
    public int getBoundHeight()
    {
        return boundHeight;
    }
    
    public Rectangle2D getBounds()
    {
        return new Rectangle2D.Float(x, y, boundWidth, boundHeight);
    }
    
    public float getX()
    {
        return x;
    }
    
    public void setX(float x)
    {
        this.x = x;
    }
    
    public float getY()
    {
        return y;
    }
    
    public void setY(float y)
    {
        this.y = y;
    }
    
    public void set(float x, float y)
    {
        this.x = x;
        this.y = y;
    }
    
    public float getxSpeed()
    {
        return xSpeed;
    }
    
    public void setxSpeed(float xSpeed)
    {
        this.xSpeed = xSpeed;
    }
    
    public float getySpeed()
    {
        return ySpeed;
    }
    
    public void setySpeed(float ySpeed)
    {
        this.ySpeed = ySpeed;
    }
    
    public void setSpeed(float xSpeed, float ySpeed)
    {
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }
    
    public Point2D.Float toPoint()
    {
        return new Point2D.Float(x, y);
    }
}
