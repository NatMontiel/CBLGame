package me.emil.game.interact.ui;

import me.emil.game.main.Game;

import java.awt.Color;
import java.awt.Graphics;

public class Bar {
	protected Game game;
	protected int x;
	protected int y;
	protected int width;
	protected int height;

	public Bar(Game game, int x, int y, int width, int height) {
		this.game = game;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	protected final void drawButtonFeedback(Graphics g, SimpleButton b) {
		//Mouse over
		if (b.isMouseOver()) {
			g.setColor(Color.white);
		} else {
			g.setColor(Color.BLACK);
		}

		//Border
		g.drawRect(b.x, b.y, b.width, b.height);

		//Mouse pressed
		if (b.isMousePressed()) {
			g.drawRect(b.x + 1, b.y + 1, b.width - 2, b.height - 2);
			g.drawRect(b.x + 2, b.y + 2, b.width - 4, b.height - 4);
		}
	}
}
