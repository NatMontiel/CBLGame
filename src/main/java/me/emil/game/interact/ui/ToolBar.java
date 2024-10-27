package me.emil.game.interact.ui;

import me.emil.game.interact.EditScene;
import me.emil.game.main.Game;
import me.emil.game.main.GameState;
import me.emil.game.object.type.TileType;
import me.emil.game.tile.Tile;
import me.emil.game.util.StorageUtil;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToolBar extends Bar {

	private final EditScene scene;
	private final Map<SimpleButton, List<Tile>> map = new HashMap<>();
	private BufferedImage pathStart;
	private BufferedImage pathEnd;
	private Tile selecTile;
	private SimpleButton currentButton;
	private int currrentIndex = 0;

	public ToolBar(EditScene scene, Game game, int x, int y, int width, int height) {
		super(game, x, y, width, height);
		this.scene = scene;
	}

	public void init() {
		this.pathStart = StorageUtil.getSpriteAtlas().getSubimage(7 * 32, 2 * 32, 32, 32);
		this.pathEnd = StorageUtil.getSpriteAtlas().getSubimage(8 * 32, 2 * 32, 32, 32);

		int w = 50;
		int h = 50;
		int xStart = 110;
		int yStart = 650;
		int xOffset = (int) ((float) w * 1.1f);
		int i = 0;

		scene.registerButton(new GrassButton(i++, xStart, yStart, w, h));
		scene.registerButton(new WaterButton(i++, xStart + xOffset, yStart, w, h));

		initMapButton(scene.getGame().getTileManager().getRoadsS(), i++, xStart, yStart, xOffset, w, h);
		initMapButton(scene.getGame().getTileManager().getRoadsC(), i++, xStart, yStart, xOffset, w, h);
		initMapButton(scene.getGame().getTileManager().getCorners(), i++, xStart, yStart, xOffset, w, h);
		initMapButton(scene.getGame().getTileManager().getBeaches(), i++, xStart, yStart, xOffset, w, h);
		initMapButton(scene.getGame().getTileManager().getIslands(), i++, xStart, yStart, xOffset, w, h);

		scene.registerButton(new PathStartButton(i++, xStart, yStart + xOffset, w, h));
		scene.registerButton(new PathEndButton(i++, xStart + xOffset, yStart + xOffset, w, h));

		scene.registerButton(new MenuButton(i++, 2, 642, 100, 30));
		scene.registerButton(new SaveButton(i, 2, 672, 100, 30));
	}

	private void initMapButton(List<Tile> list, int id, int x, int y, int xOff, int w, int h) {
		SimpleButton button = new MapButton(id, x, y, xOff, w, h);
		scene.registerButton(button);
		map.put(button, list);
	}

	public BufferedImage getStartPathImg() {
		return pathStart;
	}

	public BufferedImage getEndPathImg() {
		return pathEnd;
	}

	public void render(Graphics g) {
		//Background
		g.setColor(new Color(220, 123, 15));
		g.fillRect(x, y, width, height);

		if (selecTile != null) {
			g.drawImage(selecTile.getSprite(), 550, 650, 50, 50, null);
			g.setColor(Color.BLACK);
			g.drawRect(550, 650, 50, 50);
		}
	}

	public void rotateSprite() {
		currrentIndex++;

		if (currrentIndex >= map.get(currentButton).size())
			currrentIndex = 0;

		selecTile = map.get(currentButton).get(currrentIndex);
		scene.setSelectedTile(selecTile);
	}

	public final class MapButton extends SimpleButton {
		public MapButton(int id, int x, int y, int xOff, int width, int height) {
			super("", x + xOff * id, y, width, height, id);
		}

		@Override
		protected void onRender(Graphics graphics) {
			BufferedImage img = map.get(this).get(0).getSprite();

			graphics.drawImage(img, x, y, width, height, null);
			drawButtonFeedback(graphics, this);
		}

		@Override
		public void onClick() {
			selecTile = map.get(this).get(0);
			scene.setSelectedTile(selecTile);

			currentButton = this;
			currrentIndex = 0;
		}
	}

	public final class MenuButton extends SimpleButton {
		public MenuButton(int id, int x, int y, int width, int height) {
			super("Menu", x, y, width, height, id);
		}

		@Override
		public void onClick() {
			game.setGameState(GameState.MENU);
		}
	}

	public final class SaveButton extends SimpleButton {
		public SaveButton(int id, int x, int y, int width, int height) {
			super("Save", x, y, width, height, id);
		}

		@Override
		public void onClick() {
			game.saveLevel();
			game.setGameState(GameState.MENU);
		}
	}

	public final class PathStartButton extends SimpleButton {
		public PathStartButton(int id, int x, int y, int width, int height) {
			super("", x, y, width, height, id);
		}

		@Override
		protected void onRender(Graphics graphics) {
			graphics.drawImage(pathStart, x, y, width, height, null);
			drawButtonFeedback(graphics, this);
		}

		@Override
		public void onClick() {
			selecTile = new Tile(pathStart, -1, TileType.WATER);
			scene.setSelectedTile(selecTile);
		}
	}

	public final class PathEndButton extends SimpleButton {
		public PathEndButton(int id, int x, int y, int width, int height) {
			super("", x, y, width, height, id);
		}

		@Override
		protected void onRender(Graphics graphics) {
			graphics.drawImage(pathEnd, x, y, width, height, null);
			drawButtonFeedback(graphics, this);
		}

		@Override
		public void onClick() {
			selecTile = new Tile(pathEnd, -2, TileType.WATER);
			scene.setSelectedTile(selecTile);
		}
	}

	public final class GrassButton extends SimpleButton {

		public GrassButton(int id, int x, int y, int width, int height) {
			super("Grass", x, y, width, height, id);
		}

		@Override
		protected void onRender(Graphics graphics) {
			graphics.drawImage(scene.getGame().getTileManager().getSprite(getId()), x, y, width, height, null);
			drawButtonFeedback(graphics, this);
		}

		@Override
		public void onClick() {
			selecTile = scene.getGame().getTileManager().getTile(getId());
			scene.setSelectedTile(selecTile);
		}
	}

	public final class WaterButton extends SimpleButton {

		public WaterButton(int id, int x, int y, int width, int height) {
			super("Water", x, y, width, height, id);
		}

		@Override
		protected void onRender(Graphics graphics) {
			graphics.drawImage(scene.getGame().getTileManager().getSprite(getId()), x, y, width, height, null);
			drawButtonFeedback(graphics, this);
		}

		@Override
		public void onClick() {
			selecTile = scene.getGame().getTileManager().getTile(getId());
			scene.setSelectedTile(selecTile);
		}
	}
}
