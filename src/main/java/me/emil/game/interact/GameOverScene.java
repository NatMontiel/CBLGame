package me.emil.game.interact;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.emil.game.main.Game;
import me.emil.game.main.GameState;
import me.emil.game.interact.ui.SimpleButton;

public class GameOverScene extends GameScene {

    private SimpleButton bReplay;
    private SimpleButton bMenu;

    public GameOverScene(Game game) {
        super(game, GameState.GAMEOVER);
        initButtons();
    }

    private void initButtons() {
        int w = 150;
        int h = w / 3;
        int x = 640 / 2 - w / 2;
        int y = 300;
        int yOffset = 100;

        bMenu = new MenuButton(x, y, w, h);
        bReplay = new ReplayButton(x, y + yOffset, w, h);
    }
    
    private final class MenuButton extends SimpleButton
    {
        public MenuButton(int x, int y, int w, int h)
        {
            super("Menu", x, y, w, h);
        }
        
        @Override
        public void onClick()
        {
            game.setGameState(GameState.MENU);
        }
    }
    
    private final class ReplayButton extends SimpleButton
    {
        public ReplayButton(int x, int y, int w, int h)
        {
            super("Replay", x, y, w, h);
        }
        
        @Override
        public void onClick()
        {
            game.resetEverything();
            game.setGameState(GameState.PLAYING);
        }
    }

    @Override
    public void onRender(Graphics g)
    {
        //game over text
        g.setFont(new Font("LucidaSans", Font.BOLD, 50));
        g.setColor(Color.RED);
        g.drawString("Game over!", 160, 50);
        
        //buttons
        g.setFont(new Font("LucidaSans", Font.BOLD, 20));
        
        bMenu.render(g);
        bReplay.render(g);
    }
}
