package me.emil.game.main;

public class GameThread extends Thread
{
    private final Game game;
    
    public GameThread(Game game)
    {
        this.game = game;
    }
    
    @Override
    public void run() {
        double fpsSet = 120;
        double upsSet = 60;
        
        double timePerFrame = 1000000000.0 / fpsSet;
        double timePerUpdate = 1000000000.0 / upsSet;
        
        long lastFrame = System.nanoTime();
        long lastUpdate = System.nanoTime();
        long lastTimeCheck = System.currentTimeMillis();
        
        int frames = 0;
        int updates = 0;
        
        while (true) {
            long now = System.nanoTime();
            
            if (now - lastFrame >= timePerFrame) {
                if(game.isGameScenePlaying())
                    game.getGameScreen().repaint();
                
                lastFrame = now;
                frames++;
            }
            
            if (now - lastUpdate >= timePerUpdate) {
                if(!game.isGamePaused() && game.isGameScenePlaying())
                    game.getGameScene().tick();
                
                lastUpdate = now;
                updates++;
            }
            
            if (System.currentTimeMillis() - lastTimeCheck >= 1000) {
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
                lastTimeCheck = System.currentTimeMillis();
            }
        }
    }
}
