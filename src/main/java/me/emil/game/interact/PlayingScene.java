package me.emil.game.interact;

import me.emil.game.entity.Entity;
import me.emil.game.main.Game;
import me.emil.game.main.GameState;
import me.emil.game.entity.Tower;
import me.emil.game.object.type.TileType;
import me.emil.game.interact.ui.ActionBar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class PlayingScene extends GameScene
{
    private int mouseX;
    private int mouseY;
    private Tower selectedTower;
    private final ActionBar actionBar;
    
    public PlayingScene(Game game)
    {
        super(game, GameState.PLAYING);
        this.game = game;
        this.actionBar = new ActionBar(this, game, 0, 640, 640, 160);
        this.actionBar.init();
    }
    
    @Override
    public void reset()
    {
        mouseX = 0;
        mouseY = 0;
        
        selectedTower = null;
    }
    
    public void setSelectedTower(Tower selectedTower)
    {
        if(this.selectedTower != null)
            this.selectedTower.kill();
        
        this.selectedTower = selectedTower;
    }
    
    public Tower getSelectedTower()
    {
        return selectedTower;
    }
    
    @Override
    public void onRender(Graphics graphics) {
        for (int y = 0; y < game.getLvl().length; y++) {
            for (int x = 0; x < game.getLvl()[y].length; x++) {
                int id = game.getLvl()[y][x];
                
                if (game.getTileManager().isSpriteAnimation(id))
                    graphics.drawImage(game.getTileManager().getAniSprite(id, animationIndex), x * 32, y * 32, null);
                else
                    graphics.drawImage(game.getTileManager().getSprite(id), x * 32, y * 32, null);
            }
        }
        
        spawnSelectedTower();
        
        actionBar.render(graphics);
        game.getEntityManager().render((Graphics2D) graphics);
        
        graphics.setColor(Color.white);
        graphics.drawRect(mouseX, mouseY, 32, 32);
    }
    
    private void spawnSelectedTower() {
        if (selectedTower == null)
            return;
        
        game.getEntityManager().addEntity(selectedTower);
        selectedTower.getPosition().set(mouseX, mouseY);
        
        if(!selectedTower.isSpawned())
            selectedTower.spawn();
    }
    
    @Override
    public void onMouseClick(int x, int y) {
        if(y >= 640)
            return;
        
        if(selectedTower != null && isTileGrass(mouseX, mouseY) && (getTowerAt(mouseX, mouseY) == null || getTowerAt(mouseX, mouseY).isUsedForSetup())) {
            actionBar.payForMonster(selectedTower.getTowerType());
            selectedTower.getPosition().set(mouseX, mouseY);
            selectedTower.doneWithSetup();
            game.getEntityManager().addEntity(selectedTower);
            selectedTower = null;
        }
        
        if(selectedTower == null)
        {
            Tower tower = getTowerAt(mouseX, mouseY);
            
            if (tower != null && !actionBar.isDisplayingTower())
                actionBar.displayTower(tower);
            else if(actionBar.getDisplayedTower() != tower)
                actionBar.displayTower(tower);
            else if(tower == null)
                actionBar.displayTower(null);
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            selectedTower = null;
    }
    
    @Override
    public void onMouseMove(int x, int y) {
        if(y >= 640)
            return;
        
        mouseX = (x / 32) * 32;
        mouseY = (y / 32) * 32;
    }
    
    private Tower getTowerAt(int x, int y) {
        Entity entity = game.getEntityManager().getEntityAt(x, y);
        
        if(entity instanceof Tower)
            return (Tower) entity;
        
        return null;
    }
    
    private boolean isTileGrass(int x, int y) {
        int id = game.getLvl()[y / 32][x / 32];
        TileType tileType = game.getTileManager().getTile(id).getTileType();
        return tileType == TileType.GRASS;
    }
    
    @Override
    public void onTick() {
        if (!game.isGamePaused()) {
            if(game.getWaveManager().getWave() == null) {
                game.setGameState(GameState.GAMEOVER);
                return;
            }
            
            game.getWaveManager().tick();
            game.getEntityManager().tick();
        }
    }
}
