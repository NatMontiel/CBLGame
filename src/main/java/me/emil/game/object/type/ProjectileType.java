package me.emil.game.object.type;

public enum ProjectileType {
	BONES(0, 8F), CAULDRON(1, 4F), BATS(2, 6F);

	private final int id;
	private final float speed;

	ProjectileType(int id, float speed) {
		this.id = id;
		this.speed = speed;
	}

	public int getId() {
		return id;
	}

	public float getSpeed() {
		return speed;
	}
}
