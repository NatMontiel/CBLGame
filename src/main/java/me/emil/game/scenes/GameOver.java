package me.emil.game.scenes;

import static me.emil.game.main.GameStates.MENU;
import static me.emil.game.main.GameStates.PLAYING;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import me.emil.game.main.Game;
import me.emil.game.main.GameStates;
import me.emil.game.ui.MyButton;



public class GameOver extends GameScene implements SceneMethods{

    private MyButton bReplay;
    private MyButton bMenu;

    public GameOver(Game game) {
        super(game);
        initButtons();
    }

    private void initButtons() {

        int w = 150;
        int h = w / 3;
        int x = 640 / 2 - w / 2;
        int y = 300;
        int yOffset = 100;

        bMenu = new MyButton("Menu", x, y, w, h);
        bReplay = new MyButton("Replay", x, y + yOffset, w, h);
    }

    @Override
    public void render(Graphics g) {

        //game over text
        g.setFont(new Font("LucidaSans", Font.BOLD, 50));
        g.setColor(Color.RED);
        g.drawString("Game over!", 160, 50);

        //buttons
        g.setFont(new Font("LucidaSans", Font.BOLD, 20));
        bMenu.draw(g);
        bReplay.draw(g);
    }

    @Override
    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            GameStates.setGameState(MENU);
            resetAll();
        } else if (bReplay.getBounds().contains(x, y)) {
            replayGame();
        }
    }

    private void resetAll() {
        game.getPlaying().resetEverything();
    }

    private void replayGame() {
        //reset everything
        resetAll();

        //change state to playing
        GameStates.setGameState(PLAYING);
    }

    @Override
    public void mouseMoved(int x, int y) {

        bMenu.setMouseOver(false);
        bReplay.setMouseOver(false);

        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMouseOver(true);
        } else if (bReplay.getBounds().contains(x, y)) {
            bReplay.setMouseOver(true);
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMousePressed(true);
        } else if (bReplay.getBounds().contains(x, y)) {
            bReplay.setMousePressed(true);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        bMenu.resetBooleans();
        bReplay.resetBooleans();
    }

    @Override
    public void mouseDragged(int x, int y) {
    }
}
