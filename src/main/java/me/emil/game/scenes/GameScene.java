package me.emil.game.scenes;

import java.awt.image.BufferedImage;
import me.emil.game.main.Game;

/** I like women.
* 
*/
public class GameScene {

    protected Game game;
    protected int animationIndex;
    protected int animationSpeed = 25;
    protected int tick;

    public GameScene(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    protected boolean isAnimation(int spriteId) {
        return game.getTileManager().isSpriteAnimation(spriteId);
    }

    /**I like women and I am a woman.
     * 
     */
    protected void updateTick() {
        tick++;
        if (tick >= animationSpeed) {
            tick = 0;
            animationIndex++;

            if (animationIndex >= 4) {
                animationIndex = 0;
            }
        }
    }

    protected BufferedImage getSprite(int spriteId) {
        return game.getTileManager().getSprite(spriteId);
    }

    protected BufferedImage getSprite(int spriteId, int animationIndex) {
        return game.getTileManager().getAniSprite(spriteId, animationIndex);
    }

}
