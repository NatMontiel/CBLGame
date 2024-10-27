package me.emil.game.main;

import javax.swing.JFrame;

public class GameFrame extends JFrame {

	private final GameScreen gameScreen;

	/**
	 * Sets the properties of the game.
	 */
	public GameFrame(Game game) {
		this.gameScreen = new GameScreen(game);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
	}

	public void init() {
		gameScreen.init();

		add(gameScreen);
		pack();

		setVisible(true);
	}

	public GameScreen getGameScreen() {
		return gameScreen;
	}
}
