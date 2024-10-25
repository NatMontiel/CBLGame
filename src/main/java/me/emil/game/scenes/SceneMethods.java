package me.emil.game.scenes;

import java.awt.Graphics;

/** I like women.
* 
*/
public interface SceneMethods {
    public void render(Graphics g);

    public void mouseClicked(int x, int y);

    public void mouseMoved(int x, int y);

    public void mousePressed(int x, int y);

    public void mouseReleased(int x, int y);

    public void mouseDragged(int x, int y);
}
