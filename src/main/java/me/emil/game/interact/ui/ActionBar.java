package me.emil.game.interact.ui;

import me.emil.game.entity.Tower;
import me.emil.game.interact.PlayingScene;
import me.emil.game.main.Game;
import me.emil.game.main.GameState;
import me.emil.game.object.type.TowerType;
import me.emil.game.wave.Wave;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.text.DecimalFormat;

public class ActionBar extends Bar {
	private final SimpleButton[] towerButtons = new SimpleButton[3];
	private final PlayingScene scene;
	private final DecimalFormat formatter = new DecimalFormat("0.0");
	private Tower displayedTower;
	private boolean showMonsterCost;

	private TowerType towerType;

	public ActionBar(PlayingScene scene, Game game, int x, int y, int width, int height) {
		super(game, x, y, width, height);
		this.scene = scene;
	}

	public void init() {
		scene.registerButton(new MenuButton(2, 642, 100, 30));
		scene.registerButton(new PauseButton(2, 682, 100, 30));
		scene.registerButton(new StartButton(2, 722, 100, 30));

		int w = 50;
		int h = 50;
		int xStart = 110;
		int yStart = 650;
		int xOffset = (int) (w * 1.1f);

		for (int i = 0; i < towerButtons.length; i++) {
			towerButtons[i] = new TowerButton(i, xStart + xOffset * i, yStart, w, h);
			scene.registerButton(towerButtons[i]);
		}

		scene.registerButton(new SellButton(420, 702, 80, 25));
		scene.registerButton(new UpgradeButton(545, 702, 80, 25));
	}

	public boolean isDisplayingTower() {
		return displayedTower != null;
	}

	public Tower getDisplayedTower() {
		return displayedTower;
	}

	public void displayTower(Tower tower) {
		displayedTower = tower;
	}

	public void render(Graphics graphics) {
		//Background
		graphics.setColor(new Color(220, 123, 15));
		graphics.fillRect(x, y, width, height);

		//Displayed Tower
		drawDisplayedTower(graphics);

		//Wave info
		drawWaveInfo(graphics);

		//rubies
		graphics.drawString("Rubies : " + game.getPlayer().getRubies(), 110, 725);

		//the cost of each monster;
		if (showMonsterCost)
			drawMonsterCost(graphics);

		//Game pused text
		if (game.isGamePaused()) {
			graphics.setColor(Color.YELLOW);
			graphics.drawString("Game is paused!", 230, 325);
		}

		//Lives
		graphics.setColor(Color.RED);
		graphics.drawString("Lives: " + game.getPlayer().getLives(), 110, 750);
	}

	private void drawDisplayedTower(Graphics graphics) {
		if (displayedTower != null) {
			graphics.setColor(Color.gray);
			graphics.fillRect(410, 645, 220, 85);
			graphics.setColor(Color.black);
			graphics.drawRect(410, 645, 220, 85);
			graphics.drawRect(420, 650, 50, 50);

			graphics.drawImage(game.getEntityManager().getEntityDrawer(Tower.class).getImages()[displayedTower.getTowerType().getId()],
					420, 650, 50, 50, null);
			graphics.setFont(new Font("LucidaSans", Font.BOLD, 15));
			graphics.drawString(displayedTower.getTowerType().getName(), 490, 660);
			graphics.drawString("ID: " + displayedTower.getId(), 490, 675);
			graphics.drawString("Tier: " + displayedTower.getTier(), 560, 660);

			graphics.setColor(Color.cyan);
			graphics.drawRect((int) displayedTower.getPosition().getX(), (int) displayedTower.getPosition().getY(), 32, 32);
			graphics.setColor(Color.white);
			graphics.drawOval((int) displayedTower.getPosition().getX() + 16 - (int) (displayedTower.getRange() * 2) / 2,
					(int) displayedTower.getPosition().getY() + 16 - (int) (displayedTower.getRange() * 2) / 2,
					(int) displayedTower.getRange() * 2, (int) displayedTower.getRange() * 2);

			drawButtonFeedback(graphics, scene.getButton("Sell"));

			//Upgrade button
			if (displayedTower.getTier() < 3 && game.getPlayer().getRubies() >= getUpgradeAmount(displayedTower)) {
				scene.getButton("Upgrade").render(graphics);
				drawButtonFeedback(graphics, scene.getButton("Upgrade"));
			}

			if (scene.getButton("Sell").isMouseOver())
				graphics.drawString("Sell for: " + getSellAmount(displayedTower) + " r", 490, 700);
			else if (scene.getButton("Upgrade").isMouseOver() && game.getPlayer().getRubies() >= getUpgradeAmount(displayedTower))
				graphics.drawString("Upgrade for: " + getUpgradeAmount(displayedTower) + " r", 490, 700);
		}
	}

	private void drawWaveInfo(Graphics graphics) {
		graphics.setFont(new Font("LucidaSans", Font.BOLD, 20));
		Wave wave = game.getWaveManager().getWave();

		if (wave == null)
			return;

		if (wave.isWaveTimerStarted()) {
			float timeLeft = game.getWaveManager().getWave().getTimeLeft();
			String formatedText = formatter.format(timeLeft);

			graphics.setColor(Color.BLACK);
			graphics.drawString("Time left: " + formatedText, 450, 750);
		}

		int remaining = game.getWaveManager().getWave().getAmountOfAliveEnemies();

		graphics.setColor(Color.YELLOW);
		graphics.drawString("Enemies left : " + remaining, 280, 780);

		int current = game.getWaveManager().getWaveIndex();
		int size = game.getWaveManager().getWaveAmount();

		graphics.drawString("Wave " + (current + 1) + "/" + size, 450, 780);
	}

	private void drawMonsterCost(Graphics graphics) {
		graphics.setColor(Color.GRAY);
		graphics.fillRect(280, 650, 120, 50);
		graphics.setColor(Color.BLACK);
		graphics.drawRect(280, 650, 120, 50);

		graphics.drawString(towerType.getName(), 285, 670);
		graphics.drawString("Cost: " + towerType.getMonsterCost() + " r", 285, 695);

		//Shows message if the player doesn't have enough money for the selected tower.

		if (towerType.getMonsterCost() > game.getPlayer().getRubies()) {
			graphics.setColor(Color.YELLOW);
			graphics.drawString("Can't afford!", 285, 725);
		}
	}

	private int getSellAmount(Tower displayedType) {
		float upgradeCost = (float) ((displayedTower.getTier() - 1) * getUpgradeAmount(displayedType)) * 0.5F;
		return (int) ((displayedTower.getTowerType().getMonsterCost() / 2F) + upgradeCost);
	}

	private int getUpgradeAmount(Tower displayedTower) {
		return (int) (displayedTower.getTowerType().getMonsterCost() * 0.3f);
	}

	public void payForMonster(TowerType towerType) {
		game.getPlayer().removeRubies(towerType.getMonsterCost());
	}

	private boolean isRubiesEnoughForMonster(TowerType towerType) {
		if (towerType == null)
			return false;

		return game.getPlayer().getRubies() >= towerType.getMonsterCost();
	}

	/**
	 * Resets everything so the game can be played again.
	 */
	public void resetEverything() {
		towerType = TowerType.WITCH; //Can be anything
		showMonsterCost = false;
		displayedTower = null;
	}

	private final class MenuButton extends SimpleButton {
		public MenuButton(int x, int y, int width, int height) {
			super("Menu", x, y, width, height);
		}

		@Override
		public void onClick() {
			game.resetEverything();
			game.setGameState(GameState.MENU);
		}
	}

	private final class PauseButton extends SimpleButton {
		public PauseButton(int x, int y, int width, int height) {
			super("Pause", x, y, width, height);
		}

		@Override
		public void onClick() {
			game.setGamePaused(!game.isGamePaused());

			if (game.isGamePaused())
				setText("Resume");
			else
				setText("Pause");
		}
	}

	private final class StartButton extends SimpleButton {
		public StartButton(int x, int y, int width, int height) {
			super("Start", x, y, width, height);
		}

		@Override
		public void onClick() {
			game.getWaveManager().getWave().start();
		}
	}

	private final class TowerButton extends SimpleButton {
		public TowerButton(int id, int x, int y, int width, int height) {
			super("", x, y, width, height, id);
		}

		@Override
		protected void onRender(Graphics graphics) {
			graphics.setColor(Color.gray);
			graphics.fillRect(x, y, width, height);
			graphics.drawImage(game.getEntityManager().getEntityDrawer(Tower.class).getImages()[getId()], x, y, width, height, null);

			drawButtonFeedback(graphics, this);
		}

		@Override
		public void onClick() {
			displayedTower = null;

			if (!isRubiesEnoughForMonster(TowerType.get(getId())))
				return;

			Tower selectedTower = new Tower(game, 0, -1, TowerType.get(getId()), true);
			scene.setSelectedTower(selectedTower);
		}

		@Override
		public void onMouseMoved() {
			if (displayedTower != null && scene.getButton("Upgrade").isMouseOver() && displayedTower.getTier() < 3)
				return;

			showMonsterCost = false;

			for (SimpleButton button : towerButtons)
				button.setMouseOver(false);

			for (SimpleButton button : towerButtons) {
				if (button.isMouseOver()) {
					showMonsterCost = true;
					towerType = TowerType.get(button.getId());
					return;
				}
			}
		}
	}

	private final class SellButton extends SimpleButton {

		public SellButton(int x, int y, int width, int height) {
			super("Sell", x, y, width, height);
		}

		@Override
		protected boolean doRender() {
			return displayedTower != null;
		}

		@Override
		public void onClick() {
			if (displayedTower == null)
				return;

			displayedTower.kill();
			game.getEntityManager().removeEntity(displayedTower);
			game.getPlayer().addRubies(displayedTower.getTowerType().getMonsterCost() / 2);

			int upgradeCost = (int) ((displayedTower.getTier() - 1) * getUpgradeAmount(displayedTower) * 0.5F);
			game.getPlayer().addRubies(upgradeCost);

			displayedTower = null;
		}
	}

	private final class UpgradeButton extends SimpleButton {

		public UpgradeButton(int x, int y, int width, int height) {
			super("Upgrade", x, y, width, height);
		}

		@Override
		protected boolean doRender() {
			return displayedTower != null;
		}

		@Override
		public void onClick() {
			if (displayedTower.getTier() >= 3 || game.getPlayer().getRubies() < getUpgradeAmount(displayedTower))
				return;

			displayedTower.upgradeTower();
			game.getPlayer().removeRubies(getUpgradeAmount(displayedTower));
		}
	}
}
