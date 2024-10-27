package me.emil.game.interact;

import me.emil.game.interact.ui.ToolBar;
import me.emil.game.main.Game;
import me.emil.game.main.GameState;
import me.emil.game.object.type.TileType;
import me.emil.game.tile.Tile;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class EditScene extends GameScene {
	private final ToolBar toolbar;
	private Tile selectedTile;
	private int mouseX;
	private int mouseY;
	private int lastTileX;
	private int lastTileY;
	private int lastTileid;
	private boolean drawSelect = false;

	public EditScene(Game game) {
		super(game, GameState.EDITING);

		this.toolbar = new ToolBar(this, game, 0, 640, 640, 160);
		this.toolbar.init();
	}

	@Override
	public void onRender(Graphics graphics) {
		for (int y = 0; y < game.getLvl().length; y++) {
			for (int x = 0; x < game.getLvl()[y].length; x++) {
				int id = game.getLvl()[y][x];

				if (isAnimation(id))
					graphics.drawImage(getSprite(id, animationIndex), x * 32, y * 32, null);
				else
					graphics.drawImage(getSprite(id), x * 32, y * 32, null);
			}
		}

		toolbar.render(graphics);

		if (selectedTile != null && drawSelect)
			graphics.drawImage(selectedTile.getSprite(), mouseX, mouseY, 32, 32, null);

		graphics.drawImage(toolbar.getStartPathImg(), game.getStartPosition().getxCord() * 32, game.getStartPosition().getyCord() * 32, 32, 32, null);
		graphics.drawImage(toolbar.getEndPathImg(), game.getEndPosition().getxCord() * 32, game.getEndPosition().getyCord() * 32, 32, 32, null);
	}

	/**
	 * I am a woman.
	 */
	public void setSelectedTile(Tile tile) {
		this.selectedTile = tile;
		drawSelect = true;
	}

	private void changeTile(int x, int y) {
		if (y >= 640)
			return;

		if (selectedTile != null) {
			int tileX = x / 32;
			int tileY = y / 32;

			if (selectedTile.getId() >= 0) {
				if (lastTileX == tileX && lastTileY == tileY && lastTileid == selectedTile.getId())
					return;

				lastTileX = tileX;
				lastTileY = tileY;
				lastTileid = selectedTile.getId();

				game.getLvl()[tileY][tileX] = selectedTile.getId();

			} else {
				int id = game.getLvl()[tileY][tileX];

				if (game.getTileManager().getTile(id).getTileType() == TileType.ROAD) {
					if (selectedTile.getId() == -1) {
						game.getStartPosition().setxCord(tileX);
						game.getStartPosition().setyCord(tileY);
					} else {
						game.getEndPosition().setxCord(tileX);
						game.getEndPosition().setyCord(tileY);
					}
				}
			}
		}
	}

	@Override
	public void onMouseClick(int x, int y) {
		if (y < 640)
			changeTile(mouseX, mouseY);
	}

	@Override
	public void onMouseMove(int x, int y) {
		if (y >= 640) {
			drawSelect = false;
		} else {
			drawSelect = true;
			mouseX = (x / 32) * 32;
			mouseY = (y / 32) * 32;
		}
	}

	@Override
	public void mouseDragged(int x, int y) {
		if (y < 640)
			changeTile(x, y);
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_R)
			toolbar.rotateSprite();
	}
}
