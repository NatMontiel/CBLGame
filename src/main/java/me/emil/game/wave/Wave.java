package me.emil.game.wave;

import me.emil.game.entity.Enemy;
import me.emil.game.main.Game;
import me.emil.game.object.type.EnemyType;

import java.util.List;

public class Wave {
	private final Game game;
	private final List<EnemyType> toSpawnEnemies;
	private int enemySpawnTick = 0;
	private int enemyIndex = 0;
	private int waveTick = 0;
	private boolean waveStartTimer;

	public Wave(Game game, List<EnemyType> toSpawnEnemies) {
		this.game = game;
		this.toSpawnEnemies = toSpawnEnemies;
	}

	public void start() {
		waveStartTimer = true;
	}

	public void spawnNextEnemy() {
		EnemyType type = getNextEnemyType();

		if (type == null)
			return;

		int x = game.getStartPosition().getxCord() * 32;
		int y = game.getStartPosition().getyCord() * 32;

		Enemy enemy = new Enemy(game, x, y, type);
		enemy.spawn();
		game.getEntityManager().addEntity(enemy);

		enemyIndex++;
		enemySpawnTick = 0;
	}

	public void reset() {
		enemyIndex = 0;
		waveStartTimer = false;
		waveTick = 0;
		enemySpawnTick = 0;
		game.getEntityManager().removeEntitiesOfType(Enemy.class);
	}

	public void tick() {
		if (waveStartTimer) {
			waveTick++;
			enemySpawnTick++;
		}
	}

	public EnemyType getNextEnemyType() {
		if (enemyIndex >= toSpawnEnemies.size())
			return null;

		return toSpawnEnemies.get(enemyIndex);
	}

	public boolean isTimeForNewEnemy() {
		return enemySpawnTick >= 60 * 2; //2 sec
	}

	public boolean isThereMoreEnemiesInWave() {
		return enemyIndex < toSpawnEnemies.size();
	}

	public boolean isWaveTimerOver() {
		return getTimeLeft() <= 0;
	}

	public float getTimeLeft() {
		int waveTickLimit = 60 * 60; //1 min
		float ticksLeft = waveTickLimit - waveTick;
		return Math.max(ticksLeft / 120 * 60F, 0);
	}

	public boolean isWaveTimerStarted() {
		return waveStartTimer;
	}

	public int getAmountOfAliveEnemies() {
		int size = 0;

		for (Enemy enemy : game.getEntityManager().getEntitiesOfType(Enemy.class)) {
			if (enemy.isAlive())
				size++;
		}

		return size;
	}

	public boolean isAllEnemiesDead() {
		if (isThereMoreEnemiesInWave())
			return true;

		return getAmountOfAliveEnemies() <= 0;
	}
}
