package me.emil.game.main;

import me.emil.game.interact.EditScene;
import me.emil.game.interact.GameOverScene;
import me.emil.game.interact.GameScene;
import me.emil.game.interact.MenuScene;
import me.emil.game.interact.PlayingScene;
import me.emil.game.interact.SettingsScene;

import java.lang.reflect.InvocationTargetException;

/** Sets the states of the game.
* 
*/
public enum GameState
{
    PLAYING(PlayingScene.class), MENU(MenuScene.class), SETTINGS(SettingsScene.class), EDITING(EditScene.class), GAMEOVER(GameOverScene.class);
    
    private final Class<? extends GameScene> sceneClass;
    
    GameState(Class<? extends GameScene> sceneClass)
    {
        this.sceneClass = sceneClass;
    }
    
    public GameScene createGameScene(Game game)
    {
        try
        {
            return sceneClass.getConstructor(Game.class).newInstance(game);
        }
        catch(InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e)
        {
            throw new RuntimeException(e);
        }
    }
}
