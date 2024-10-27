package me.emil.game.entity;

import me.emil.game.main.Game;
import me.emil.game.object.type.TowerType;
import me.emil.game.util.Util;

public final class Tower extends Entity
{
    private TowerType towerType;
    private float range;
    private int tier = 1;
    private float cooldownTickLimit;
    private int cooldownTick;
    private boolean usedForSetup;
    
    public Tower(Game game, int x, int y, TowerType towerType)
    {
        this(game, x, y, towerType, false);
    }
    
    public Tower(Game game, int x, int y, TowerType towerType, boolean usedForSetup)
    {
        super(game, new EntityPosition(x, y), 1, towerType.getAttackDamage());
        
        this.towerType = towerType;
        this.range = towerType.getRange();
        this.cooldownTickLimit = towerType.getCooldown() * 3600;
        this.usedForSetup = usedForSetup;
    }
    
    public boolean isUsedForSetup()
    {
        return usedForSetup;
    }
    
    public void doneWithSetup()
    {
        usedForSetup = false;
    }
    
    public int getTier()
    {
        return tier;
    }
    
    public TowerType getTowerType()
    {
        return towerType;
    }
    
    public void setTowerType(TowerType towerType)
    {
        this.towerType = towerType;
    }
    
    public float getRange()
    {
        return range;
    }
    
    public float getCooldownTickLimit()
    {
        return cooldownTickLimit;
    }
    
    public void upgradeTower()
    {
        this.tier++;
        
        switch(towerType)
        {
            case SKELETON:
                setDamage(getDamage() + 2);
                range += 20;
                cooldownTickLimit -= 5;
                break;
            case WITCH:
                setDamage(getDamage() + 5);
                range += 20;
                cooldownTickLimit -= 15;
                break;
            case VAMPIRE:
                setDamage(getDamage() + 1);
                range += 20;
                cooldownTickLimit -= 10;
                break;
            default:
                break;
        }
    }
    
    public boolean isInCooldown()
    {
        return cooldownTick < cooldownTickLimit;
    }
    
    @Override
    public void tick()
    {
        if(usedForSetup)
            return;
        
        if(cooldownTick < cooldownTickLimit)
        {
            cooldownTick++;
            return;
        }
        
        cooldownTick = 0;
        
        for (Enemy enemy : game.getEntityManager().getEntitiesOfType(Enemy.class))
        {
            if (enemy.isAlive() && isEnemyInRange(enemy))
            {
                newProjectile(enemy);
                return; //1 enemy as target at the time
            }
        }
    }
    
    public void newProjectile(Enemy enemy) {
        // Get the current position of the tower and the enemy
        float towerX = getPosition().getX();
        float towerY = getPosition().getY();
        float enemyX = enemy.getPosition().getX();
        float enemyY = enemy.getPosition().getY();
        
        float deltaX = enemyX - towerX;
        float deltaY = enemyY - towerY;
        
        // Calculate the distance to normalize the vector
        float distance = (float) Math.hypot(deltaX, deltaY);
        
        // Avoid division by zero
        if (distance == 0) {
            return; // Exit early if the enemy is on top of the tower
        }
        
        // Normalize the direction
        float normalizedX = deltaX / distance;
        float normalizedY = deltaY / distance;
        
        // Calculate the speed based on the normalized direction
        float speed = towerType.getProjectileType().getSpeed();
        float xSpeed = normalizedX * speed;
        float ySpeed = normalizedY * speed;
        
        // Create a new projectile
        Projectile projectile = new Projectile(game, towerX, towerY, getDamage(), towerType.getProjectileType());
        projectile.getPosition().setSpeed(xSpeed, ySpeed);
        projectile.spawn();
        
        // Add the projectile to the entity manager
        game.getEntityManager().addEntity(projectile);
    }
    
    private boolean isEnemyInRange(Enemy enemy) {
        int range = Util.getHypoDistance(getPosition().getX(), getPosition().getY(), enemy.getPosition().getX(), enemy.getPosition().getY());
        return range < this.range;
    }
}
