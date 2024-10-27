package me.emil.game.interact;

import me.emil.game.main.Game;
import me.emil.game.main.GameState;
import me.emil.game.interact.ui.SimpleButton;

public class MenuScene extends GameScene
{
    public MenuScene(Game game) {
        super(game, GameState.MENU);
        initButtons();
    }

    private void initButtons() {
        int w = 150;
        int h = w / 3;
        int x = 640 / 2 - w / 2;
        int y = 150;
        int yOffset = 100;

        registerButton(new GameStateButton("Play", x, y, w, h, GameState.PLAYING));
        registerButton(new GameStateButton("Editing", x, y + yOffset, w, h, GameState.EDITING));
        registerButton(new GameStateButton("Settings", x, y + yOffset * 2, w, h, GameState.SETTINGS));
        registerButton(new GameStateButton("Quit", x, y + yOffset * 3, w, h, null));
    }
    
    public final class GameStateButton extends SimpleButton {
        
        private final GameState gameState;
        
        public GameStateButton(String text, int x, int y, int w, int h, GameState state)
        {
            super(text, x, y, w, h);
            gameState = state;
        }
        
        @Override
        public void onClick()
        {
            if(gameState == null)
                System.exit(0);
            
            game.setGameState(gameState);
        }
    }
}
