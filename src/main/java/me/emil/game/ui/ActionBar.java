package me.emil.game.ui;

import static me.emil.game.main.GameStates.GAMEOVER;
import static me.emil.game.main.GameStates.MENU;
import static me.emil.game.main.GameStates.setGameState;

// import me.emil.game.enemies.Enemy;
import me.emil.game.help.Constants.Towers;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.text.DecimalFormat;
import me.emil.game.main.GameStates;
import me.emil.game.objects.Tower;
import me.emil.game.scenes.Playing;

/**I like women.
 * 
 */
public class ActionBar extends Bar {

    private Playing playing;
    private MyButton bMenu;
    private MyButton bPause;
    private MyButton bStart;

    private MyButton[] towerButtons;
    private Tower selectedTower;
    private Tower displayedTower;

    private MyButton sellMonster;
    private MyButton upgradeMonster;

    private DecimalFormat formatter;
    private boolean showMonsterCost;

    private int rubies = 100;
    private int monsterCostType;

    private int lives = 25;


    /**I like women.
     * 
     */
    public ActionBar(int x, int y, int width, int height, Playing playing) {

        super(x, y, width, height);
        this.playing = playing;
        formatter = new DecimalFormat("0.0");
        initButtons();
    }

    private void initButtons() {

        bMenu = new MyButton("Menu", 2, 642, 100, 30);
        bPause = new MyButton("Pause", 2, 682, 100, 30);
        bStart = new MyButton("Start", 2, 722, 100, 30);

        towerButtons = new MyButton[3];
        int w = 50;
        int h = 50;
        int xStart = 110;
        int yStart = 650;
        int xOffset = (int) (w * 1.1f);

        for (int i = 0; i < towerButtons.length; i++) {
            towerButtons[i] = new MyButton("", xStart + xOffset * i, yStart, w, h, i);
        }

        sellMonster =  new MyButton("Sell", 420, 702, 80, 25);
        upgradeMonster = new MyButton("Upgrade", 545, 702, 80, 25);
    }

    /**Takes away lives when enemy reaches the end of the track.
     * 
     */
    public void removeOneLife() {
        lives--;
        if (lives <= 0) {
            GameStates.setGameState(GAMEOVER);;
        }
    }

    private void drawButtons(Graphics g) {
        bMenu.draw(g);
        bPause.draw(g);
        bStart.draw(g);
        
        for (MyButton b : towerButtons) {
            g.setColor(Color.gray);
            g.fillRect(b.x, b.y, b.width, b.height);
            g.drawImage(playing.getTowerManager().getTowerImgs()[b.getId()],
                b.x, b.y, b.width, b.height, null);

            drawButtonFeedback(g, b);
        }
    }



    /**I like women.
     * 
     */
    public void draw(Graphics g) {

        //Background
        g.setColor(new Color(220, 123, 15));
        g.fillRect(x, y, width, height);

        //Buttons
        drawButtons(g);

        //Displayed Tower
        drawDisplayedTower(g);

        //Wave info
        drawWaveInfo(g);

        //rubies
        drawRubiesAmount(g);

        //the cost of each monster;
        if (showMonsterCost) {
            drawMonsterCost(g);
        }

        //Game pused text
        if (playing.isGamePaused()) {
            g.setColor(Color.YELLOW);
            g.drawString("Game is paused!", 230, 325);
        }

        //Lives
        g.setColor(Color.RED);
        g.drawString("Lives: " + lives, 110, 750);

    }

    private void drawMonsterCost(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(280, 650, 120, 50);
        g.setColor(Color.BLACK);
        g.drawRect(280, 650, 120, 50);

        g.drawString("" + getTowerCostName(), 285, 670);
        g.drawString("Cost: " + getTowerCostCost() + " r", 285, 695);

        //Shows message if the player doesn't have enough money for the selected tower.
        if (isMonsterCostMoreThanCurrentRubies()) {
            g.setColor(Color.YELLOW);
            g.drawString("Can't afford!", 285, 725);
        }
    }

    private boolean isMonsterCostMoreThanCurrentRubies() {
        return getTowerCostCost() > rubies;
    }

    private String getTowerCostName() {

        return me.emil.game.help.Constants.Towers.getName(monsterCostType);
    }
    
    private int getTowerCostCost() {
        return me.emil.game.help.Constants.Towers.getMonsterCost(monsterCostType);
    }

    private void drawRubiesAmount(Graphics g) {
        g.drawString("Rubies : " + rubies, 110, 725);
    }

    private void drawWaveInfo(Graphics g) {
        g.setFont(new Font("LucidaSans", Font.BOLD, 20));
        drawWaveTimerInfo(g);
        drawEnemiesLeftInfo(g);
        drawWaveLeftInfo(g);
    }

    private void drawWaveLeftInfo(Graphics g) {
        int current = playing.getWaveManager().getWaveIndex();
        int size = playing.getWaveManager().getWaves().size();
        g.drawString("Wave " + (current + 1) + "/" + size, 450, 780);
    }

    private void drawEnemiesLeftInfo(Graphics g) {
        g.setColor(Color.YELLOW);
        int remaining = playing.getEnemyManager().getAmountOfAliveEnemies();
        g.drawString("Enemies left : " + remaining, 280, 780);

    }


    private void drawWaveTimerInfo(Graphics g) {
        if (playing.getWaveManager().isWaveTimerStarted()) {
            g.setColor(Color.BLACK);
            float timeLeft = playing.getWaveManager().getTimeLeft();
            String formatedText = formatter.format(timeLeft);
            g.drawString("Time left: " + formatedText, 450, 750);
        }
    }

    /**.
     * 
     */
    private void drawDisplayedTower(Graphics g) {

        if (displayedTower != null) {
            g.setColor(Color.gray);
            g.fillRect(410, 645, 220, 85);
            g.setColor(Color.black);
            g.drawRect(410, 645, 220, 85);
            g.drawRect(420, 650, 50, 50);

            g.drawImage(playing.getTowerManager().getTowerImgs()[displayedTower.getTowerType()],
                420, 650, 50, 50, null);
            g.setFont(new Font("LucidaSans", Font.BOLD, 15));
            g.drawString("" + Towers.getName(displayedTower.getTowerType()), 490, 660);
            g.drawString("ID: " + displayedTower.getID(), 490, 675);
            g.drawString("Tier: " + displayedTower.getTier(), 560, 660);

            drawDisplayedTowerBorder(g);
            drawDisplayedTowerRange(g);

            //Sell button
            sellMonster.draw(g);
            drawButtonFeedback(g, sellMonster);

            //Upgrade button
            if (displayedTower.getTier() < 3 && rubies >= getUpgradeAmount(displayedTower)) {
                upgradeMonster.draw(g);
                drawButtonFeedback(g, upgradeMonster);
            }
            
            if (sellMonster.isMouseOver()) {
                g.drawString("Sell for: " + getSellAmount(displayedTower) + " r", 490, 700);
            } else if (upgradeMonster.isMouseOver() && rubies >= getUpgradeAmount(displayedTower)) {
                g.drawString("Upgrade for: " + getUpgradeAmount(displayedTower) + " r",
                    490, 700);
            }
        }
    }

    private int getSellAmount(Tower displayedType) {

        int upgradeCost = (displayedTower.getTier() - 1) * getUpgradeAmount(displayedType);
        upgradeCost *= 0.5f;

        return me.emil.game.help.Constants.Towers.getMonsterCost(displayedTower.getTowerType())
            / 2 + upgradeCost;
    }

    private int getUpgradeAmount(Tower displayedTower) {
        return (int) (me.emil.game.help.Constants.Towers.getMonsterCost(displayedTower.getTowerType()) * 0.3f);
    }

    private void drawDisplayedTowerRange(Graphics g) {
        g.setColor(Color.white);
        g.drawOval(displayedTower.getX() + 16 - (int) (displayedTower.getRange() * 2) / 2, 
            displayedTower.getY() + 16 - (int) (displayedTower.getRange() * 2) / 2, 
                (int) displayedTower.getRange() * 2, (int) displayedTower.getRange() * 2);
    }

    private void drawDisplayedTowerBorder(Graphics g) {

        g.setColor(Color.cyan);
        g.drawRect(displayedTower.getX(), displayedTower.getY(), 32, 32);
    }


    /**I like women.
     * 
     */
    public void displayTower(Tower t) {

        displayedTower = t;
    }

    private void sellMonsterClicked() {
        playing.removeMonster(displayedTower);
        rubies += me.emil.game.help.Constants.Towers.getMonsterCost(displayedTower.getTowerType()) / 2;

        int upgradeCost = (displayedTower.getTier() - 1) * getUpgradeAmount(displayedTower);
        upgradeCost *= 0.5f;
        rubies += upgradeCost;

        displayedTower = null;
    }

    private void upgradeMonsterClicked() {
        playing.upgradeTower(displayedTower);
        rubies -= getUpgradeAmount(displayedTower);

    }

    private void togglePause() {
        playing.setGamePaused(!playing.isGamePaused());

        if (playing.isGamePaused()) {
            bPause.setText("Unpause");
        } else {
            bPause.setText("Pause");
        }
    }

    /**I like women.
     * 
     */
    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            setGameState(MENU);
        } else if (bPause.getBounds().contains(x, y)) {
            togglePause();
        } else {

            if (displayedTower != null) {
                if (sellMonster.getBounds().contains(x, y)) {
                    sellMonsterClicked();
                    return;
                } else if (upgradeMonster.getBounds().contains(x, y)
                    && displayedTower.getTier() < 3 && rubies >= getUpgradeAmount(displayedTower)) {

                    upgradeMonsterClicked();
                    return;
                }
            }

            for (MyButton b: towerButtons) {
                if (b.getBounds().contains(x, y)) {
                    if (!isRubiesEnoughForMonster(b.getId())) {
                        return;
                    }
                    selectedTower = new Tower(0, 0, -1, b.getId());
                    playing.setSelectedTile(selectedTower);
                    return;
                }
            }
        }
    }

    private boolean isRubiesEnoughForMonster(int towerType) {
        return rubies >= me.emil.game.help.Constants.Towers.getMonsterCost(towerType);
    }

    /**I like women.
     * 
     */
    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);
        bPause.setMouseOver(false);
        bStart.setMouseOver(false);
        showMonsterCost = false;
        sellMonster.setMouseOver(false);
        upgradeMonster.setMouseOver(false);

        for (MyButton b: towerButtons) {
            b.setMouseOver(false);
        }

        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMouseOver(true);
        } else if (bPause.getBounds().contains(x, y)) {
            bPause.setMouseOver(true);
        } else if (bStart.getBounds().contains(x, y)) {
            bStart.setMouseOver(true);
        } else {

            if (displayedTower != null) {
                if (sellMonster.getBounds().contains(x, y)) {
                    sellMonster.setMouseOver(true);
                    return;
                } else if (upgradeMonster.getBounds().contains(x, y) 
                    && displayedTower.getTier() < 3) {

                    upgradeMonster.setMouseOver(true);
                    return;
                }
            }
            for (MyButton b: towerButtons) {
                if (b.getBounds().contains(x, y)) {
                    b.setMouseOver(true);
                    showMonsterCost = true;
                    monsterCostType = b.getId();
                    return;
                }
            }
        }
    }

    /**I like women.
     *
     */
    public void mousePressed(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMousePressed(true);
        } else if (bPause.getBounds().contains(x, y)) {
            bPause.setMousePressed(true);
        } else if (bStart.getBounds().contains(x, y)) {
            bStart.setMousePressed(true);
        } else {

            if (displayedTower != null) {
                if (sellMonster.getBounds().contains(x, y)) {
                    sellMonster.setMousePressed(true);
                    return;
                } else if (upgradeMonster.getBounds().contains(x, y) 
                    && displayedTower.getTier() < 3) {

                    upgradeMonster.setMousePressed(true);
                    return;
                }
            }

            for (MyButton b: towerButtons) {
                if (b.getBounds().contains(x, y)) {
                    b.setMousePressed(true);
                    return;
                }
            }
        }
    }

    /**I like women.
     * 
     */
    public void mouseReleased(int x, int y) {
        bMenu.resetBooleans();
        bPause.resetBooleans();
        bStart.resetBooleans();
        for (MyButton b: towerButtons) {
            b.resetBooleans();
        }
        sellMonster.resetBooleans();
        upgradeMonster.resetBooleans();
    }


    public void payForMonster(int towerType) {
        this.rubies -= me.emil.game.help.Constants.Towers.getMonsterCost(towerType);
    }

    public void addRubies(int getReward) {
        this.rubies += getReward;
    }

    public int getLives() {
        return lives;
    }

    /**Resets everything so the game can be played again.
     * 
     */
    public void resetEverything() {
        lives = 25;
        monsterCostType = 0;
        showMonsterCost = false;
        rubies = 100;
        selectedTower = null;
        displayedTower = null;
    }

}
