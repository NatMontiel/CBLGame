package me.emil.game.wave;

import me.emil.game.main.Game;
import me.emil.game.object.type.EnemyType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WaveManager
{
    private final Game game;
    private final List<Wave> waves = new ArrayList<>();
    private int waveIndex = 0;
    
    public WaveManager(Game game)
    {
        this.game = game;
    }
    
    public void init()
    {
        EnemyType v = EnemyType.VILLAGER;
        EnemyType k = EnemyType.KNIGHT;
        EnemyType p = EnemyType.PRIEST;
        EnemyType h = EnemyType.HUNTER;
        
        waves.add(new Wave(game, new ArrayList<>(Arrays.asList(v, v, v, v, v, v, v, v, v, p))));
        waves.add(new Wave(game, new ArrayList<>(Arrays.asList(k, v, v, v, v, v, v, v, v, p))));
        waves.add(new Wave(game, new ArrayList<>(Arrays.asList(h, h, h, h, h, h, h, h, h, h))));
        waves.add(new Wave(game, new ArrayList<>(Arrays.asList(k, v, v, v, v, v, v, v, v, v, v, v, v, v, v, v, v, v, v, v, v, v, v))));
    }
    
    public Wave getWave()
    {
        return waveIndex >= waves.size() ? null : waves.get(waveIndex);
    }
    
    public int getWaveIndex()
    {
        return waveIndex;
    }
    
    /** Increases the wave index.
     *
     */
    public void increaseWaveIndex() {
        waveIndex++;
    }
    
    public int getWaveAmount() {
        return waves.size();
    }
    
    public void tick()
    {
        Wave currentWave = getWave();
        
        if(currentWave == null || !currentWave.isWaveTimerStarted())
            return;
        
        currentWave.tick();
        
        if(currentWave.isAllEnemiesDead() || currentWave.isWaveTimerOver())
        {
            game.getPlayer().addRubies(100);
            getWave().reset();
            increaseWaveIndex();
        }
        
        if(currentWave.isTimeForNewEnemy())
            currentWave.spawnNextEnemy();
    }
}
