package me.emil.game.entity.graphics;

import me.emil.game.entity.Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

public interface EntityRenderer<T extends Entity> {
	Class<T> getEntityClass();

	BufferedImage[] getImages();

	void init();

	void draw(Graphics2D graphics2D, List<T> entities);
}
