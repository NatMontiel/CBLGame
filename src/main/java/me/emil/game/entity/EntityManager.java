package me.emil.game.entity;

import me.emil.game.entity.graphics.EnemyRenderer;
import me.emil.game.entity.graphics.EntityRenderer;
import me.emil.game.entity.graphics.ExplosionRenderer;
import me.emil.game.entity.graphics.ProjectileRenderer;
import me.emil.game.entity.graphics.TowerRenderer;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityManager
{
    private final List<Entity> entities = new ArrayList<>();
    private final Map<Class<? extends Entity>, EntityRenderer> entityDrawers = new HashMap<>();
    private int entityId;
    
    public EntityManager() {
        registerEntityDrawer(new EnemyRenderer());
        registerEntityDrawer(new ExplosionRenderer());
        registerEntityDrawer(new ProjectileRenderer());
        registerEntityDrawer(new TowerRenderer());
    }
    
    public <T extends Entity> EntityRenderer<T> getEntityDrawer(Class<T> drawerClass)
    {
        return entityDrawers.get(drawerClass);
    }
    
    public void addEntity(Entity entity)
    {
        this.entities.add(entity);
    }
    
    public void removeEntity(Entity entity)
    {
        this.entities.remove(entity);
    }
    
    public void removeEntitiesOfType(Class<? extends Entity> type) {
        entities.removeIf(entity -> entity.getClass().equals(type));
    }
    
    public void init() {
        for(EntityRenderer drawer : entityDrawers.values())
            drawer.init();
    }
    
    public Entity getEntityAt(int x, int y) {
        for(Entity entity : new ArrayList<>(entities)) {
            if(entity.getPosition().getX() == x && entity.getPosition().getY() == y)
                return entity;
        }
        
        return null;
    }
    
    public int getNextEntityId() {
        return entityId++;
    }
    
    public <T extends Entity> List<T> getEntitiesOfType(Class<T> type) {
        List<T> result = new ArrayList<>();
        
        for(Entity entity : new ArrayList<>(entities)) {
            if(entity == null)
            {
                entities.remove(null);
                continue;
            }
            
            if(entity.getClass().equals(type))
                result.add((T) entity);
        }
        
        return result;
    }
    
    public <T extends Entity> void registerEntityDrawer(EntityRenderer<T> drawer)
    {
        entityDrawers.put(drawer.getEntityClass(), drawer);
    }
    
    public void reset() {
        this.entities.clear();
    }
    
    public void render(Graphics2D graphics2D)
    {
        for(EntityRenderer entityRenderer : new ArrayList<>(entityDrawers.values()))
        {
            List<Entity> entities = getEntitiesOfType(entityRenderer.getEntityClass());
            entities.removeIf(entity -> !entity.isAlive() || !entity.isSpawned());
            entityRenderer.draw(graphics2D, entities);
        }
    }
    
    public void tick()
    {
        List<Entity> deadEntities = new ArrayList<>();
        
        for(Entity entity : new ArrayList<>(entities))
        {
            if(!entity.isAlive())
            {
                deadEntities.add(entity);
                continue;
            }
            
            entity.tick();
        }
        
        entities.removeAll(deadEntities);
    }
}
