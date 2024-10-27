package me.emil.game.interact;

import me.emil.game.interact.ui.SimpleButton;
import me.emil.game.main.Game;
import me.emil.game.main.GameState;

import java.awt.Color;
import java.awt.Graphics;

public class SettingsScene extends GameScene {
	public SettingsScene(Game game) {
		super(game, GameState.SETTINGS);
		initButtons();
	}

	private void initButtons() {
		registerButton(new MenuButton(2, 2, 100, 30));
	}

	@Override
	public void onRender(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, 640, 640);
	}

	private final class MenuButton extends SimpleButton {
		public MenuButton(int x, int y, int width, int height) {
			super("Menu", x, y, width, height);
		}

		@Override
		public void onClick() {
			game.setGameState(GameState.MENU);
		}
	}
}
