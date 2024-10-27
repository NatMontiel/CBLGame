package me.emil.game.tile;

import me.emil.game.object.type.TileType;

import java.awt.image.BufferedImage;

public class Tile {
	private final BufferedImage[] sprite;
	private final int id;
	private final TileType tileType;

	public Tile(BufferedImage sprite, int id, TileType tileType) {
		this.sprite = new BufferedImage[1];
		this.sprite[0] = sprite;
		this.id = id;
		this.tileType = tileType;
	}

	public Tile(BufferedImage[] sprite, int id, TileType tileType) {
		this.sprite = sprite;
		this.id = id;
		this.tileType = tileType;
	}

	public TileType getTileType() {
		return tileType;
	}

	public BufferedImage getSprite(int animationIndex) {
		return sprite[animationIndex];
	}

	public BufferedImage getSprite() {
		return sprite[0];
	}

	public boolean isAnimation() {
		return sprite.length > 1;
	}

	public int getId() {
		return id;
	}
}
