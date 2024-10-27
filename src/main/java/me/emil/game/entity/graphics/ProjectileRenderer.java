package me.emil.game.entity.graphics;

import me.emil.game.util.StorageUtil;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import me.emil.game.entity.Projectile;
import me.emil.game.object.type.ProjectileType;

/** Manages the behaviour of the projectiles in the game.
 * 
 */
public class ProjectileRenderer implements EntityRenderer<Projectile>
{
    private BufferedImage[] projectileImages;
    
    @Override
    public Class<Projectile> getEntityClass()
    {
        return Projectile.class;
    }
    
    @Override
    public BufferedImage[] getImages()
    {
        return projectileImages;
    }
    
    @Override
    public void init()
    {
        BufferedImage atlas = StorageUtil.getSpriteAtlas();
        projectileImages = new BufferedImage[3];
        
        for(int i = 0; i < 3; i++)
            projectileImages[i] = atlas.getSubimage((7 + i) * 32, 32, 32, 32);
    }

    
    @Override
    public void draw(Graphics2D g2d, List<Projectile> projectiles)
    {
        for(Projectile projectile : projectiles)
        {
            if(projectile.getProjectileType() == ProjectileType.BONES)
            {
                g2d.translate(projectile.getPosition().getX(), projectile.getPosition().getY());
                g2d.rotate(Math.toRadians(90));
                g2d.drawImage(projectileImages[projectile.getProjectileType().getId()], -16, -16, null);
                g2d.rotate(-Math.toRadians(90));
                g2d.translate(-projectile.getPosition().getX(), -projectile.getPosition().getY());
            }
            else
            {
                g2d.drawImage(projectileImages[projectile.getProjectileType().getId()], (int) projectile.getPosition().getX() - 16, (int) projectile.getPosition().getY() - 16, null);
            }
        }
    }
}
