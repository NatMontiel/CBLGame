package me.emil.game.manager;

import me.emil.game.events.Wave;
import java.util.ArrayList;
import java.util.Arrays;
import me.emil.game.scenes.Playing;

/** Manages the properties of the enemy waves.
 * 
 */
public class WaveManager {

    @SuppressWarnings("unused")
    private Playing playing;
    private ArrayList<Wave> waves = new ArrayList<>();
    private int enemySpawnTickLimit = 60 * 1;
    private int enemySpawnTick = enemySpawnTickLimit;
    private int enemyIndex;
    private int waveIndex;
    private int waveTickLimit = 60 * 5;
    private int waveTick = 0;

    private boolean waveStartTimer;
    private boolean waveTickTimerOver;

    /** Creates the waves of me.emil.game.enemies.
     * 
     */
    public WaveManager(Playing playing) {
        this.playing = playing;
        createWaves();
    }

    /** Updates the timer of the next wave of me.emil.game.enemies.
     * 
     */
    public void update() {
        if (enemySpawnTick < enemySpawnTickLimit) {
            enemySpawnTick++;
        }

        if (waveStartTimer) {
            waveTick++;
            if (waveTick >= waveTickLimit) {
                waveTickTimerOver = true;
            }

        }
    }

    /** Increases the wave index.
     * 
     */
    public void increaseWaveIndex() {
        waveIndex++;
        waveTickTimerOver = false;
        waveStartTimer = false;
    }

    public void startWaveTimer() {
        waveStartTimer = true;
    }

    public boolean isWaveTimeOver() {
        return waveTickTimerOver;
    }

    /** Spawns the enemy waves.
     * 
     */
    public int getNextEnemy() {
        enemySpawnTick = 0;
        return waves.get(waveIndex).getEnemyList().get(enemyIndex++);
    }

    private void createWaves() {
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 1))));
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(2, 0, 0, 0, 0, 0, 0, 0, 0, 1))));
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(3, 3, 3, 3, 3, 3, 3, 3, 3, 3))));
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(2, 0, 0, 0, 0, 0, 0, 0, 
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0))));
    }

    public ArrayList<Wave> getWaves() {
        return waves;
    }

    public boolean isTimeForNewEnemy() {
        return enemySpawnTick >= enemySpawnTickLimit;
    }

    public boolean isThereMoreEnemiesInWave() {
        return enemyIndex < waves.get(waveIndex).getEnemyList().size();
    }

    public boolean isThereMoreWaves() {
        return waveIndex + 1 < waves.size();
    }

    public void resetEnemyIndex() {
        enemyIndex = 0;
    }

    public int getWaveIndex() {
        return waveIndex;
    }

    /** Gets the time left for the next wave.
     * 
     */
    public float getTimeLeft() {
        float ticksLeft = waveTickLimit - waveTick;
        return ticksLeft / 60.0f;
    }

    public boolean isWaveTimerStarted() {
        return waveStartTimer;
    }

    public void reset() {
        waves.clear();
        createWaves();
        enemyIndex = 0;
        waveIndex = 0;
        waveStartTimer = false;
        waveTickTimerOver = false;
        waveTick = 0;
        enemySpawnTick = enemySpawnTickLimit;
    }
 
}
