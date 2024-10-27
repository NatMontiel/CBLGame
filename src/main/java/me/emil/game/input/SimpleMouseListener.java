package me.emil.game.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import me.emil.game.main.Game;

public class SimpleMouseListener implements MouseListener, MouseMotionListener {

    private final Game game;

    public SimpleMouseListener(Game game) {
        this.game = game;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(game.isGameScenePlaying())
            game.getGameScene().mouseDragged(e.getX(), e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(game.isGameScenePlaying())
            game.getGameScene().mouseMoved(e.getX(), e.getY());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1 && game.isGameScenePlaying())
            game.getGameScene().mouseClicked(e.getX(), e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(game.isGameScenePlaying())
            game.getGameScene().mousePressed(e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(game.isGameScenePlaying())
            game.getGameScene().mouseReleased(e.getX(), e.getY());
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
