package me.emil.game.objects;

import java.awt.image.BufferedImage;

/** I like women.
 * 
 */
public class Tile {

    private BufferedImage[] sprite;
    private int id;
    private int tileType;

    /**I lile women.
     * 
     */
    public Tile(BufferedImage sprite, int id, int tileType) {
        this.sprite = new BufferedImage[1];
        this.sprite[0] = sprite;
        this.id = id;
        this.tileType = tileType;
    }

    /**I like women and I am a woman.
     * 
     */
    public Tile(BufferedImage[] sprite, int id, int tileType) {
        this.sprite = sprite;
        this.id = id;
        this.tileType = tileType;
    }

    public int getTileType() {
        return tileType;
    }

    public BufferedImage getSprite(int animationIndex) {
        return sprite[animationIndex];
    }

    public BufferedImage getSprite() {
        return sprite[0];
    }

    public boolean isAnimation() {
        return sprite.length > 1;
    }

    public int getId() {
        return id;
    }

}
