package me.emil.game.entity;

import me.emil.game.main.Game;
import me.emil.game.object.type.Direction;

public abstract class Entity
{
    protected final Game game;
    private final int id;
    private final EntityPosition position;
    private final int maxHealth;
    private int damage;
    private int health;
    private boolean spawned;
    
    protected Entity(Game game, EntityPosition position, int maxHealth, int damage)
    {
        this.game = game;
        this.id = game.getEntityManager().getNextEntityId();
        this.position = position;
        this.damage = damage;
        this.health = maxHealth;
        this.maxHealth = maxHealth;
    }
    
    public int getId()
    {
        return id;
    }
    
    public final EntityPosition getPosition()
    {
        return position;
    }
    
    public final void spawn()
    {
        spawned = true;
    }
    
    public final boolean isSpawned()
    {
        return spawned;
    }
    
    public final boolean isAlive()
    {
        return health > 0 && spawned;
    }
    
    public final int getHealth()
    {
        return health;
    }
    
    public final void hurt(int damage)
    {
        this.health -= damage;
    }
    
    public final void kill()
    {
        this.health = 0;
        this.spawned = false;
    }
    
    public final void setHealth(int health)
    {
        this.health = health;
    }
    
    public final float getHealthBarFloat()
    {
        return getHealth() / (float) getMaxHealth();
    }
    
    public final int getDamage()
    {
        return damage;
    }
    
    public final void setDamage(int damage)
    {
        this.damage = damage;
    }
    
    public final int getMaxHealth()
    {
        return maxHealth;
    }
    
    public int distance(Entity other)
    {
        int xDist = (int) (position.getX() - other.position.getX());
        int yDist = (int) (position.getY() - other.position.getY());
        return (int) Math.round(Math.sqrt((xDist * xDist) + (yDist * yDist)));
    }
    
    public void move()
    {
        position.setX(position.getX() + getPosition().getxSpeed());
        position.setY(position.getY() + getPosition().getySpeed());
    }
    
    public void move(Direction direction)
    {
        if(direction == null)
            return;
        
        switch(direction)
        {
            case LEFT:
                position.setX(position.getX() - position.getxSpeed());
                break;
            case UP:
                position.setY(position.getY() - position.getySpeed());
                break;
            case RIGHT:
                position.setX(position.getX() + position.getxSpeed());
                break;
            case DOWN:
                position.setY(position.getY() + position.getySpeed());
                break;
        }
    }
    
    public abstract void tick();
}
