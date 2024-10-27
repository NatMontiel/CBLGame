package me.emil.game.sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.net.URL;

/**
 * Creates the sound used in the game.
 */
class PlayableTrack {
	private final URL url;
	private Clip clip;

	/**
	 * Gets the sound files from the resource folder.
	 */
	public PlayableTrack(URL url) {
		this.url = url;

		initializeSound();
	}

	/**
	 * Begins playing the sound files.
	 */
	public void play() {
		if (clip != null)
			clip.start();
	}

	/**
	 * Loops the sound file.
	 */
	public void loop() {
		if (clip != null)
			clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	/**
	 * Stops playing the sound file.
	 */
	public void stop() {
		clip.stop();
	}

	/**
	 * Gets the sound as file
	 */
	private void initializeSound() {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(url);
			clip = AudioSystem.getClip();

			if (clip != null)
				clip.open(ais);
		} catch (Exception e) {
			System.err.println("Cannot load " + url.getPath() + ": " + e.getMessage());
		}
	}
}