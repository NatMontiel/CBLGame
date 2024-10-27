package me.emil.game.entity.graphics;

import me.emil.game.entity.Enemy;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

import me.emil.game.util.StorageUtil;

/** Manages the behaviour of the me.emil.game.enemies in the game.
 * 
 */
public class EnemyRenderer implements EntityRenderer<Enemy>
{
    private final BufferedImage[] enemyImages = new BufferedImage[4];
    private BufferedImage slowEffect;
    
    @Override
    public Class<Enemy> getEntityClass()
    {
        return Enemy.class;
    }
    
    @Override
    public BufferedImage[] getImages()
    {
        return enemyImages;
    }
    
    public void init() {
        BufferedImage atlas = StorageUtil.getSpriteAtlas();
        slowEffect = atlas.getSubimage(32 * 9, 32 * 2, 32, 32);
        
        for (int i = 0; i < 4; i++)
            enemyImages[i] = atlas.getSubimage(32 * i, 32, 32, 32);
    }
    
    @Override
    public void draw(Graphics2D graphics2D, List<Enemy> entities)
    {
        for (Enemy enemy : entities) {
            graphics2D.drawImage(enemyImages[enemy.getEnemyType().getId()], (int) enemy.getPosition().getX(), (int) enemy.getPosition().getY(), null);
            graphics2D.setColor(Color.red);
            graphics2D.fillRect((int) enemy.getPosition().getX() + 16 - (getNewBarWidth(enemy) / 2), (int) enemy.getPosition().getY() - 10, getNewBarWidth(enemy), 3);
            
            if(enemy.isSlowed())
                graphics2D.drawImage(slowEffect, (int) enemy.getPosition().getX(), (int) enemy.getPosition().getY(), null);
        }
    }
    
    private int getNewBarWidth(Enemy e) {
        int hpBarWidth = 20;
        return (int) (hpBarWidth * e.getHealthBarFloat());
    }
}
