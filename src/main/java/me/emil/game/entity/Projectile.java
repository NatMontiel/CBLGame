package me.emil.game.entity;

import me.emil.game.main.Game;
import me.emil.game.object.type.ProjectileType;

public final class Projectile extends Entity
{
    private final ProjectileType projectileType;
    
    public Projectile(Game game, float x, float y, int damage, ProjectileType projectileType)
    {
        super(game, new EntityPosition(x, y, 16, 16), 1, damage);
        this.projectileType = projectileType;
    }
    
    public ProjectileType getProjectileType()
    {
        return projectileType;
    }
    
    @Override
    public void tick()
    {
        move();
        
        if(isOutOfBounds()) {
            kill();
            return;
        }
        
        if (isHittingEnemy()) {
            kill();
            
            if (projectileType == ProjectileType.CAULDRON) {
                Explosion explosion = new Explosion(game, (int) getPosition().getX(), (int) getPosition().getY(), 1, 0);
                explosion.spawn();
                game.getEntityManager().addEntity(explosion);
                explodeOnEnemies();
            }
        }
    }
    
    private void explodeOnEnemies() {
        for (Enemy e : game.getEntityManager().getEntitiesOfType(Enemy.class)) {
            if (e.isAlive()) {
                float radius = 40.0f;
                
                float xDist = Math.abs(getPosition().getX() - e.getPosition().getX());
                float yDist = Math.abs(getPosition().getY() - e.getPosition().getY());
                
                float realDist = (float) Math.hypot(xDist, yDist);
                
                if (realDist <= radius) {
                    e.hurt(getDamage());
                    game.getPlayer().addRubies(e.getEnemyType().getReward());
                }
            }
        }
    }
    
    private boolean isHittingEnemy() {
        if(isOutOfBounds())
            return false;
        
        for(Enemy enemy : game.getEntityManager().getEntitiesOfType(Enemy.class))
        {
            if(enemy.getPosition().getBounds().intersects(getPosition().getBounds()))
            {
                game.getPlayer().addRubies(enemy.getEnemyType().getReward());
                enemy.hurt(getDamage());
                
                if(projectileType == ProjectileType.BATS)
                    enemy.slow();
                
                return true;
            }
        }
        
        return false;
    }
    
    private boolean isOutOfBounds() {
        return !(getPosition().getX() > 0) || !(getPosition().getX() <= 640) || !(getPosition().getY() >= 0) || !(getPosition().getY() <= 640);
    }
}
