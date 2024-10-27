package me.emil.game.main;

import me.emil.game.entity.EntityManager;
import me.emil.game.entity.Player;
import me.emil.game.interact.GameScene;
import me.emil.game.object.PathPoint;
import me.emil.game.object.type.TileType;
import me.emil.game.sound.Track;
import me.emil.game.tile.TileManager;
import me.emil.game.util.StorageUtil;
import me.emil.game.wave.WaveManager;

import java.util.List;

/**
 * Sets the properties of the game.
 * Makes the game screen visible.
 */
public class Game {
	private final GameFrame gameFrame;
	private final GameThread gameThread;
	private final TileManager tileManager;
	private final EntityManager entityManager;
	private final WaveManager waveManager;
	private final Player player = new Player();
	private PathPoint start = new PathPoint(0, 0);
	private PathPoint end = new PathPoint(0, 0);
	private int[][] lvl = new int[0][0];

	private boolean gamePaused = false;
	private GameState state = GameState.MENU;
	private GameScene gameScene;

	/**
	 * Sets the properties of the game.
	 */
	public Game() {
		this.tileManager = new TileManager();
		this.gameThread = new GameThread(this);
		this.entityManager = new EntityManager();
		this.waveManager = new WaveManager(this);
		this.gameFrame = new GameFrame(this);

		createDefaultLevel();
		loadDefaultLevel();

		getGameScreen().init();
		getTileManager().init();
		getEntityManager().init();
		getWaveManager().init();

		this.gameFrame.init();
		Track.BACKGROUND_MUSIC.playAndLoop();
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}

	public PathPoint getStartPosition() {
		return start;
	}

	public PathPoint getEndPosition() {
		return end;
	}

	public Player getPlayer() {
		return player;
	}

	public boolean isGameScenePlaying() {
		return getGameScene() != null;
	}

	public GameState getGameState() {
		return state;
	}

	public void setGameState(GameState state) {
		this.gameScene = state.createGameScene(this);
		this.state = gameScene.getGameState();
	}

	public GameScene getGameScene() {
		return gameScene;
	}

	public TileType getTileType(int x, int y) {
		int xCord = x / 32;
		int yCord = y / 32;

		if (xCord < 0 || xCord > 19)
			return TileType.WATER;

		if (yCord < 0 || yCord > 19)
			return TileType.WATER;

		int id = lvl[yCord][xCord];
		return tileManager.getTile(id).getTileType();
	}

	private void createDefaultLevel() {
		StorageUtil.createLevel("new_level", new int[400]);
	}

	private void start() {
		setGameState(GameState.MENU);
		gameThread.start();
	}

	public int[][] getLvl() {
		return lvl;
	}

	// Getters and setters
	public GameScreen getGameScreen() {
		return gameFrame.getGameScreen();
	}

	public TileManager getTileManager() {
		return tileManager;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public WaveManager getWaveManager() {
		return waveManager;
	}

	private void loadDefaultLevel() {
		lvl = StorageUtil.getLevelData("new_level");
		List<PathPoint> points = StorageUtil.getLevelPathPoints("new_level");

		if (points.isEmpty())
			return;

		start = points.get(0);
		end = points.get(1);
	}

	public boolean isGamePaused() {
		return gamePaused;
	}

	public void setGamePaused(boolean gamePaused) {
		this.gamePaused = gamePaused;
	}

	public void saveLevel() {
		StorageUtil.saveLevel("new_level", lvl, start, end);
	}

	public void resetEverything() {
		gameScene.reset();
		entityManager.reset();
		player.setRubies(100);
		player.setLives(25);
		gamePaused = false;
	}
}

