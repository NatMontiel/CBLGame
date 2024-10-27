package me.emil.game.interact;

import me.emil.game.interact.ui.SimpleButton;
import me.emil.game.main.Game;
import me.emil.game.main.GameState;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public abstract class GameScene {
	protected final GameState gameState;
	private final List<SimpleButton> registeredButtons = new ArrayList<>();
	protected Game game;
	protected int animationIndex;
	protected int animationSpeed = 25;
	protected int tick;

	public GameScene(Game game, GameState gameState) {
		this.game = game;
		this.gameState = gameState;
	}

	public final void registerButton(SimpleButton button) {
		registeredButtons.add(button);
	}

	public final SimpleButton getButton(String name) {
		for (SimpleButton button : new ArrayList<>(registeredButtons)) {
			if (button.getText().equals(name)) {
				return button;
			}
		}

		return null;
	}

	public final void render(Graphics graphics) {
		onRender(graphics);

		for (SimpleButton button : new ArrayList<>(registeredButtons))
			button.render(graphics);
	}

	protected void onRender(Graphics graphics) {
	}

	public final void mouseClicked(int x, int y) {
		for (SimpleButton button : new ArrayList<>(registeredButtons)) {
			if (button.getBounds().contains(x, y)) {
				button.onClick();
			}
		}

		onMouseClick(x, y);
	}

	protected void onMouseClick(int x, int y) {
	}

	public final void mouseMoved(int x, int y) {
		for (SimpleButton button : new ArrayList<>(registeredButtons)) {
			if (button.getBounds().contains(x, y))
				button.onMouseMoved();
			else
				button.setMouseOver(false);
		}

		onMouseMove(x, y);
	}

	protected void onMouseMove(int x, int y) {
	}

	public final void mousePressed(int x, int y) {
		for (SimpleButton button : new ArrayList<>(registeredButtons)) {
			if (button.getBounds().contains(x, y)) {
				button.onMousePressed();
			}
		}
	}

	public final void mouseReleased(int x, int y) {
		for (SimpleButton button : new ArrayList<>(registeredButtons))
			button.onMouseReleased();
	}

	public final Game getGame() {
		return game;
	}

	public final GameState getGameState() {
		return gameState;
	}

	protected final boolean isAnimation(int spriteId) {
		return game.getTileManager().isSpriteAnimation(spriteId);
	}

	public final void tick() {
		tick++;

		if (tick >= animationSpeed) {
			tick = 0;
			animationIndex++;

			if (animationIndex >= 4)
				animationIndex = 0;
		}

		onTick();
	}

	protected final BufferedImage getSprite(int spriteId) {
		return game.getTileManager().getSprite(spriteId);
	}

	protected final BufferedImage getSprite(int spriteId, int animationIndex) {
		return game.getTileManager().getAniSprite(spriteId, animationIndex);
	}

	public void keyPressed(KeyEvent e) {

	}

	public void reset() {

	}

	public void mouseDragged(int x, int y) {

	}

	protected void onTick() {

	}
}
