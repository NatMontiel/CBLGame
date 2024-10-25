package me.emil.game.main;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/** Creates the sound used in the game.
 * 
 */
public class Sound {

    Clip clip;
    URL[] soundURL = new URL[8];

    /** Gets the sound files from the resource folder.
    *
    */
    public Sound() {
        soundURL[0] = getClass().getResource("/sound/backgroundMusic.wav");
        soundURL[1] = getClass().getResource("/sound/backupExplosion.wav");
        soundURL[2] = getClass().getResource("/sound/boneHit.wav");
        soundURL[3] = getClass().getResource("/sound/cauldronExplosion.wav");
        soundURL[4] = getClass().getResource("/sound/pilgrimWITCHscream.wav");
        soundURL[5] = getClass().getResource("/sound/placimg.wav");
        soundURL[6] = getClass().getResource("/sound/rubies.wav");
        soundURL[7] = getClass().getResource("/sound/vampHypnosis.wav");

        // Test sound loading
        testSoundLoading();

    }

        
    /** Gets the i'th url from the sound method.
     * 
     */
    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            if (clip != null) {
                clip.open(ais);
                System.out.println("Clip initialized and sound file loaded: " + soundURL[i]);
            } else {
                System.out.println("Clip initialization failed." + soundURL[i]);
            }
        } catch (Exception e) {
            System.out.println("Error loading sound file at index: " + i);
            e.printStackTrace();
        }
    }
                                                                             


    /** Begins playing the sound files.
     * 
     */
    public void play() {

        if (clip != null) {
            clip.start();
        } else {
            System.out.println("Clip is null, cannot play sound.");
        }

    }

    /** Loops the sound file.
     * 
     */
    public void loop() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            System.out.println("Clip is null, cannot loop sound.");
        }
    }

    /** Stops playing the sound file.
     * 
     */
    public void stop() {

        clip.stop();

    }


    /**.
     * 
     */
    public void testSoundLoading() {
        URL testUrl = getClass().getResource("/sound/backgroundMusic.wav");
        if (testUrl != null) {
            System.out.println("Sound file found!");
        } else {
            System.out.println("Sound file not found.");
        }
    }

    


}