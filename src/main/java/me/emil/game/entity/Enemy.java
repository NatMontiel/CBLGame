package me.emil.game.entity;

import me.emil.game.main.Game;
import me.emil.game.main.GameState;
import me.emil.game.object.type.Direction;
import me.emil.game.object.type.EnemyType;
import me.emil.game.object.type.TileType;

public final class Enemy extends Entity {
	private final EnemyType enemyType;
	private final int slowTickLimit = 120;
	private int slowTick = slowTickLimit;
	private boolean slowed = false;
	private Direction lastDirection = Direction.UP;

	/**
	 * Determines the starting values of the enemy.
	 */
	public Enemy(Game game, float x, float y, EnemyType enemyType) {
		super(game, new EntityPosition(x, y, 32, 32), enemyType.getInitialHealth(), 0);

		this.enemyType = enemyType;
		this.getPosition().setSpeed(enemyType.getSpeed(), enemyType.getSpeed());
	}

	public EnemyType getEnemyType() {
		return enemyType;
	}

	public boolean isSlowed() {
		return slowed;
	}

	public void slow() {
		slowTick = 0;
		slowed = true;
		getPosition().setSpeed(getPosition().getxSpeed() * 0.5f, getPosition().getySpeed() * 0.5f);
	}

	@Override
	public void move(Direction direction) {
		this.lastDirection = direction;
		super.move(direction);
	}

	@Override
	public void tick() {
		if (!isAlive()) return;

		// Slowing effect handling
		if (slowTick < slowTickLimit && slowed) {
			slowTick++;
			return;
		}

		if (slowTick >= slowTickLimit && slowed) {
			getPosition().setSpeed(getPosition().getxSpeed() * 2f, getPosition().getySpeed() * 2f);
			slowed = false;
		}

		// Check if the enemy has reached the end
		if (isAtEnd()) {
			kill();
			if (game.getPlayer().removeOneLife()) game.setGameState(GameState.GAMEOVER);
			return;
		}

		// Check if last direction is still valid
		int nextX = fixEnemyOffsetTileX((int) Math.floor(getPosition().getX() + getSpeedAndWidth(lastDirection) / 32F));
		int nextY = fixEnemyOffsetTileY((int) Math.floor(getPosition().getY() + getSpeedAndHeight(lastDirection) / 32F));

		if (game.getTileType(nextX * 32, nextY * 32) == TileType.ROAD) {
			move(lastDirection);  // Continue in the same direction
		} else {
			// Find a new direction only if we cannot move in lastDirection
			lastDirection = getNewDirection();
			move(lastDirection);
		}
	}

	private Direction getNewDirection() {
		int xCord = fixEnemyOffsetTileX((int) Math.floor(getPosition().getX() / 32F));
		int yCord = fixEnemyOffsetTileY((int) Math.floor(getPosition().getY() / 32F));

		// Attempt to prioritize `RIGHT`, then `DOWN`, then `UP`, then `LEFT`
		if (game.getTileType((xCord + 1) * 32, yCord * 32) == TileType.ROAD && lastDirection != Direction.LEFT) {
			return Direction.RIGHT;
		} else if (game.getTileType(xCord * 32, (yCord + 1) * 32) == TileType.ROAD && lastDirection != Direction.UP) {
			return Direction.DOWN;
		} else if (game.getTileType(xCord * 32, (yCord - 1) * 32) == TileType.ROAD && lastDirection != Direction.DOWN) {
			return Direction.UP;
		} else if (game.getTileType((xCord - 1) * 32, yCord * 32) == TileType.ROAD && lastDirection != Direction.RIGHT) {
			return Direction.LEFT;
		}

		// If all directions are blocked, return the last direction as a fallback
		return lastDirection;
	}


	private int fixEnemyOffsetTileX(int xCord) {
		if (lastDirection == Direction.LEFT && xCord < 19)
			return xCord + 1;

		return xCord;
	}

	private int fixEnemyOffsetTileY(int yCord) {
		if (lastDirection == Direction.UP && yCord < 19)
			return yCord + 1;

		return yCord;
	}

	private boolean isAtEnd() {
		if (getPosition().getX() / 32 == game.getEndPosition().getxCord())
			return getPosition().getY() / 32 == game.getEndPosition().getyCord();

		return false;
	}

	private float getSpeedAndHeight(Direction direction) {
		if (direction == Direction.UP)
			return -enemyType.getSpeed();
		else if (direction == Direction.DOWN)
			return enemyType.getSpeed();

		return 0;
	}

	private float getSpeedAndWidth(Direction direction) {
		if (direction == Direction.LEFT)
			return -enemyType.getSpeed();
		else if (direction == Direction.RIGHT)
			return enemyType.getSpeed();

		return 0;
	}
}
