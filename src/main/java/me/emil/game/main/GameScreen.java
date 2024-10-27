package me.emil.game.main;

import javax.swing.JPanel;
import me.emil.game.input.KeyboardListener;
import me.emil.game.input.SimpleMouseListener;

import java.awt.Dimension;
import java.awt.Graphics;

/**
 * Sets the properties of the game screen.
 */
public class GameScreen extends JPanel {

	private final Game game;

	/**
	 * Sets the size of the game screen.
	 */
	public GameScreen(Game game) {
		this.game = game;
	}

	/**
	 * Sets inputs the screen can receive.
	 */
	public void init() {
		SimpleMouseListener simpleMouseListener = new SimpleMouseListener(game);
		KeyboardListener keyboardListener = new KeyboardListener(game);

		addMouseListener(simpleMouseListener);
		addMouseMotionListener(simpleMouseListener);
		addKeyListener(keyboardListener);

		requestFocus();
		setPanelSize();
	}

	private void setPanelSize() {
		Dimension size = new Dimension(640, 800);
		setMinimumSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
	}

	/**
	 * Sets the graphics of the game.
	 */
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);

		if (game.isGameScenePlaying())
			game.getGameScene().render(graphics);
	}
}