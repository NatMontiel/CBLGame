package me.emil.game.object.type;

public enum TowerType {
	WITCH(0, ProjectileType.CAULDRON, 65, 100, 100, 4),
	SKELETON(1, ProjectileType.BONES, 30, 25, 75, 1),
	VAMPIRE(2, ProjectileType.BATS, 45, 10, 125, 2);

	private final int id;
	private final ProjectileType projectileType;
	private final int monsterCost;
	private final int attackDamage;
	private final int range;
	private final int cooldown;

	TowerType(int id, ProjectileType projectileType, int monsterCost, int attackDamage, int range, int cooldown) {
		this.id = id;
		this.projectileType = projectileType;
		this.monsterCost = monsterCost;
		this.attackDamage = attackDamage;
		this.range = range;
		this.cooldown = cooldown;
	}

	public static TowerType get(int id) {
		for (TowerType type : TowerType.values()) {
			if (type.id == id)
				return type;
		}
		return null;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name().charAt(0) + name().substring(1).toLowerCase();
	}

	public ProjectileType getProjectileType() {
		return projectileType;
	}

	public int getMonsterCost() {
		return monsterCost;
	}

	public int getAttackDamage() {
		return attackDamage;
	}

	public int getRange() {
		return range;
	}

	public int getCooldown() {
		return cooldown; //Seconds
	}
}
