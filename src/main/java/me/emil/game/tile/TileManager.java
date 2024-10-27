package me.emil.game.tile;

import me.emil.game.object.type.TileType;
import me.emil.game.util.StorageUtil;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the tiles.
 */
public class TileManager {

	public Tile grass;
	public Tile water;
	public Tile road;
	public Tile roadLR;
	public Tile roadTB;
	public Tile roadBtoR;
	public Tile roadLtoB;
	public Tile roadLtoT;
	public Tile roadTtoR;
	public Tile borderLWaterCorner;
	public Tile tileLWaterCorner;
	public Tile tileRWaterCorner;
	public Tile borderRWaterCorner;
	public Tile tWater;
	public Tile rWater;
	public Tile bWater;
	public Tile lWater;
	public Tile tLslice;
	public Tile tRslice;
	public Tile bRslice;
	public Tile bLslice;

	public BufferedImage atlas;
	public List<Tile> tiles = new ArrayList<>();

	public List<Tile> roadsS = new ArrayList<>();
	public List<Tile> roadsC = new ArrayList<>();
	public List<Tile> corners = new ArrayList<>();
	public List<Tile> beaches = new ArrayList<>();
	public List<Tile> islands = new ArrayList<>();

	public void init() {
		this.atlas = StorageUtil.getSpriteAtlas();
		int id = 0;

		tiles.add(grass = new Tile(getSprite(9, 0), id++, TileType.GRASS));
		tiles.add(water = new Tile(getAniSprites(0, 0), id++, TileType.WATER));

		roadsS.add(road = new Tile(getSprite(8, 0), id++, TileType.ROAD));
		roadsS.add(roadTB = new Tile(getRotImg(getSprite(8, 0), 90), id++, TileType.ROAD));

		roadsC.add(roadBtoR = new Tile(getBuildRotImageRoad(getSprite(8, 0),
				getSprite(7, 0), 0), id++, TileType.ROAD));
		roadsC.add(roadLtoB = new Tile(getBuildRotImageRoad(getSprite(8, 0),
				getSprite(7, 0), 90), id++, TileType.ROAD));
		roadsC.add(roadLtoT = new Tile(getBuildRotImageRoad(getSprite(8, 0),
				getSprite(7, 0), 180), id++, TileType.ROAD));
		roadsC.add(roadTtoR = new Tile(getBuildRotImageRoad(getSprite(8, 0),
				getSprite(7, 0), 270), id++, TileType.ROAD));

		corners.add(borderLWaterCorner = new Tile(getBuildRotImage(getAniSprites(0, 0),
				getSprite(5, 0), 0), id++, TileType.WATER));
		corners.add(tileLWaterCorner = new Tile(getBuildRotImage(getAniSprites(0, 0),
				getSprite(5, 0), 90), id++, TileType.WATER));
		corners.add(tileRWaterCorner = new Tile(getBuildRotImage(getAniSprites(0, 0),
				getSprite(5, 0), 180), id++, TileType.WATER));
		corners.add(borderRWaterCorner = new Tile(getBuildRotImage(getAniSprites(0, 0),
				getSprite(5, 0), 270), id++, TileType.WATER));

		beaches.add(tWater = new Tile(getBuildRotImage(
				getAniSprites(0, 0), getSprite(6, 0), 0), id++, TileType.WATER));
		beaches.add(rWater = new Tile(getBuildRotImage(
				getAniSprites(0, 0), getSprite(6, 0), 90), id++, TileType.WATER));
		beaches.add(bWater = new Tile(getBuildRotImage(
				getAniSprites(0, 0), getSprite(6, 0), 180), id++, TileType.WATER));
		beaches.add(lWater = new Tile(getBuildRotImage(
				getAniSprites(0, 0), getSprite(6, 0), 270), id++, TileType.WATER));

		islands.add(tLslice = new Tile(getBuildRotImage(
				getAniSprites(0, 0), getSprite(4, 0), 0), id++, TileType.WATER));
		islands.add(tRslice = new Tile(getBuildRotImage(
				getAniSprites(0, 0), getSprite(4, 0), 90), id++, TileType.WATER));
		islands.add(bRslice = new Tile(getBuildRotImage(
				getAniSprites(0, 0), getSprite(4, 0), 180), id++, TileType.WATER));
		islands.add(bRslice = new Tile(getBuildRotImage(
				getAniSprites(0, 0), getSprite(4, 0), 270), id, TileType.WATER));

		tiles.addAll(roadsS);
		tiles.addAll(roadsC);
		tiles.addAll(corners);
		tiles.addAll(beaches);
		tiles.addAll(islands);

	}

	public Tile getTile(int id) {
		return tiles.get(id);
	}

	public BufferedImage getSprite(int id) {
		return tiles.get(id).getSprite();
	}

	private BufferedImage getSprite(int xCord, int yCord) {
		return atlas.getSubimage(xCord * 32, yCord * 32, 32, 32);
	}

	public BufferedImage getAniSprite(int id, int animationIndex) {
		return tiles.get(id).getSprite(animationIndex);
	}

	private BufferedImage[] getAniSprites(int xCord, int yCord) {
		BufferedImage[] arr = new BufferedImage[4];

		for (int i = 0; i < 4; i++)
			arr[i] = getSprite(xCord + i, yCord);

		return arr;
	}

	public boolean isSpriteAnimation(int spriteId) {
		return tiles.get(spriteId).isAnimation();
	}

	/**
	 * Gets the sides of the roads.
	 */
	public List<Tile> getRoadsS() {
		return roadsS;
	}

	/**
	 * Gets the corners of the roads.
	 */
	public List<Tile> getRoadsC() {
		return roadsC;
	}

	/**
	 * Gets the corners.
	 */
	public List<Tile> getCorners() {
		return corners;
	}

	/**
	 * Gets beaches.
	 */
	public List<Tile> getBeaches() {
		return beaches;
	}

	/**
	 * Gets the islands.
	 */
	public List<Tile> getIslands() {
		return islands;
	}

	private BufferedImage getRotImg(BufferedImage image, int angle) {
		int w = image.getWidth();
		int h = image.getHeight();

		BufferedImage newImg = new BufferedImage(w, h, image.getType());
		Graphics2D g2d = newImg.createGraphics();

		g2d.rotate(Math.toRadians(angle), (double) w / 2, (double) h / 2);
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();

		return newImg;
	}

	/**
	 * Rotates only the second image + animations.
	 */
	private BufferedImage[] getBuildRotImage(BufferedImage[] images, BufferedImage secondImage, int angle) {
		int width = images[0].getWidth();
		int height = images[0].getHeight();

		BufferedImage[] arr = new BufferedImage[images.length];

		for (int i = 0; i < images.length; i++) {
			BufferedImage newImage = new BufferedImage(width, height, images[0].getType());
			Graphics2D graphics = newImage.createGraphics();

			graphics.drawImage(images[i], 0, 0, null);
			graphics.rotate(Math.toRadians(angle), (double) width / 2, (double) height / 2);
			graphics.drawImage(secondImage, 0, 0, null);
			graphics.dispose();

			arr[i] = newImage;
		}

		return arr;

	}

	/**
	 * Rotates only the second image + animations, for the road corner.
	 */
	private BufferedImage getBuildRotImageRoad(BufferedImage image, BufferedImage secondImage, int angle) {
		int width = image.getWidth();
		int height = image.getHeight();

		BufferedImage newImage = new BufferedImage(width, height, image.getType());
		Graphics2D graphics = newImage.createGraphics();

		graphics.drawImage(image, 0, 0, null);
		graphics.rotate(Math.toRadians(angle), (double) width / 2, (double) height / 2);
		graphics.drawImage(secondImage, 0, 0, null);
		graphics.dispose();

		return newImage;
	}

}
