package me.emil.game.entity;

public class Player
{
    private int rubies = 100;
    private int lives = 25;
    
    public int getRubies()
    {
        return rubies;
    }
    
    public void setRubies(int rubies)
    {
        this.rubies = rubies;
    }
    
    public void addRubies(int rubies)
    {
        this.rubies += rubies;
    }
    
    public void removeRubies(int rubies)
    {
        this.rubies -= rubies;
    }
    
    public int getLives()
    {
        return lives;
    }
    
    public void setLives(int lives)
    {
        this.lives = lives;
    }
    
    public boolean removeOneLife() {
        lives--;
        return lives <= 0;
    }
}
