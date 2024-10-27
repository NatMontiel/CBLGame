package me.emil.game.entity.graphics;

import me.emil.game.entity.Tower;
import me.emil.game.util.StorageUtil;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

public class TowerRenderer implements EntityRenderer<Tower> {
	private BufferedImage[] towerImages;

	@Override
	public Class<Tower> getEntityClass() {
		return Tower.class;
	}

	@Override
	public BufferedImage[] getImages() {
		return towerImages;
	}

	public void init() {
		BufferedImage atlas = StorageUtil.getSpriteAtlas();
		towerImages = new BufferedImage[3];

		for (int i = 0; i < 3; i++)
			towerImages[i] = atlas.getSubimage((4 + i) * 32, 32, 32, 32);
	}

	/**
	 * Draws the tower on the game screen.
	 */
	@Override
	public void draw(Graphics2D graphics, List<Tower> towers) {
		for (Tower tower : towers)
			graphics.drawImage(towerImages[tower.getTowerType().getId()], (int) tower.getPosition().getX(), (int) tower.getPosition().getY(), null);
	}
}
