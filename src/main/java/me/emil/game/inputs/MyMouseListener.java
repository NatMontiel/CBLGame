package me.emil.game.inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import me.emil.game.main.Game;
import me.emil.game.main.GameStates;

/** Allows the program to take input from the mouse.
     * 
     */
public class MyMouseListener implements MouseListener, MouseMotionListener {

    private Game game;

    public MyMouseListener(Game game) {
        this.game = game;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        switch (GameStates.gameState) {
            case MENU:
                game.getMenu().mouseDragged(e.getX(), e.getY());
                break;

            case PLAYING:
                game.getPlaying().mouseDragged(e.getX(), e.getY());
                break;

            case SETTINGS:
                game.getSettings().mouseDragged(e.getX(), e.getY());
                break;
            case EDITING:
                game.getEditing().mouseDragged(e.getX(), e.getY());
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch (GameStates.gameState) {
            case MENU:
                game.getMenu().mouseMoved(e.getX(), e.getY());
                break;

            case PLAYING:
                game.getPlaying().mouseMoved(e.getX(), e.getY());
                break;
            case SETTINGS:
                game.getSettings().mouseMoved(e.getX(), e.getY());
                break;
            case EDITING:
                game.getEditing().mouseMoved(e.getX(), e.getY());
                break;
            case GAMEOVER:
                game.getGameOver().mouseMoved(e.getX(), e.getY());
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            switch (GameStates.gameState) {
                case MENU:
                    game.getMenu().mouseClicked(e.getX(), e.getY());
                    break;
                case PLAYING:
                    game.getPlaying().mouseClicked(e.getX(), e.getY());
                    break;
                case SETTINGS:
                    game.getSettings().mouseClicked(e.getX(), e.getY());
                    break;
                case EDITING:
                    game.getEditing().mouseClicked(e.getX(), e.getY());
                    break;
                case GAMEOVER:
                    game.getGameOver().mouseClicked(e.getX(), e.getY());
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (GameStates.gameState) {
            case MENU:
                game.getMenu().mousePressed(e.getX(), e.getY());
                break;

            case PLAYING:
                game.getPlaying().mousePressed(e.getX(), e.getY());
                break;

            case SETTINGS:
                game.getSettings().mousePressed(e.getX(), e.getY());
                break;
            case EDITING:
                game.getEditing().mousePressed(e.getX(), e.getY());
                break;
            case GAMEOVER:
                game.getGameOver().mousePressed(e.getX(), e.getY());
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (GameStates.gameState) {
            case MENU:
                game.getMenu().mouseReleased(e.getX(), e.getY());
                break;

            case PLAYING:
                game.getPlaying().mouseReleased(e.getX(), e.getY());
                break;

            case SETTINGS:
                game.getSettings().mouseReleased(e.getX(), e.getY());
                break;
            case EDITING:
                game.getEditing().mouseReleased(e.getX(), e.getY());
                break;
            case GAMEOVER:
                game.getGameOver().mouseReleased(e.getX(), e.getY());
                break;
            default:
                break;
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    
}
