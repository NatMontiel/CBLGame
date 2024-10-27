package me.emil.game.entity;

import me.emil.game.main.Game;

public final class Explosion extends Entity {
	private int explodeTick = 0;
	private int explodeIndex = 0;

	public Explosion(Game game, int x, int y, int maxHealth, int damage) {
		super(game, new EntityPosition(x, y), maxHealth, damage);
	}

	public int getIndex() {
		return explodeIndex;
	}

	@Override
	public void tick() {
		explodeTick++;

		if (explodeIndex >= 6)
			kill();

		if (explodeTick >= 12) {
			explodeTick = 0;
			explodeIndex++;
		}
	}
}
