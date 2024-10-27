package me.emil.game.object.type;

public enum EnemyType {
	VILLAGER(0, 5, 0.5F, 100),
	PRIEST(1, 10, 0.65F, 80),
	KNIGHT(2, 25, 0.45F, 250),
	HUNTER(3, 30, 0.75F, 120);

	private final int id;
	private final int reward;
	private final float speed;
	private final int initialHealth;

	EnemyType(int id, int reward, float speed, int initialHealth) {
		this.id = id;
		this.reward = reward;
		this.speed = speed;
		this.initialHealth = initialHealth;
	}

	public static EnemyType get(int id) {
		for (EnemyType type : values()) {
			if (type.id == id)
				return type;
		}

		return null;
	}

	public int getId() {
		return id;
	}

	public int getReward() {
		return reward;
	}

	public float getSpeed() {
		return speed;
	}

	public int getInitialHealth() {
		return initialHealth;
	}
}
