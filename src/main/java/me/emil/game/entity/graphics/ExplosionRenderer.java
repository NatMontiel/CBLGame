package me.emil.game.entity.graphics;

import me.emil.game.entity.Explosion;
import me.emil.game.util.StorageUtil;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

public class ExplosionRenderer implements EntityRenderer<Explosion> {
	private BufferedImage[] explodeImages;

	@Override
	public Class<Explosion> getEntityClass() {
		return Explosion.class;
	}

	@Override
	public BufferedImage[] getImages() {
		return explodeImages;
	}

	@Override
	public void init() {
		BufferedImage atlas = StorageUtil.getSpriteAtlas();
		explodeImages = new BufferedImage[7];

		for (int i = 0; i < 7; i++)
			explodeImages[i] = atlas.getSubimage(i * 32, 64, 32, 32);
	}

	public void draw(Graphics2D g2d, List<Explosion> entities) {
		for (Explosion e : entities) {
			g2d.drawImage(explodeImages[e.getIndex()], (int) e.getPosition().getX() - 16, (int) e.getPosition().getY() - 16, null);
		}
	}
}
