package me.emil.game.sound;

public enum Track {
	BACKGROUND_MUSIC("backgroundMusic.wav"),
	BACKUP_EXPLOSION("backupExplosion.wav"),
	BONE_HIT("boneHit.wav"),
	CAULDRON_EXPLOSION("cauldronExplosion.wav"),
	PILGRIM_WITCH_SCREAM("pilgrimWitchScream.wav"),
	PLACING("placing.wav"),
	VAMPIRE_HYPNOSIS("vampireHypnosis.wav");

	private final PlayableTrack sound;

	Track(String fileName) {
		this.sound = new PlayableTrack(getClass().getResource("/sound/" + fileName));
	}

	public void play() {
		sound.play();
	}

	/**
	 * Loops the sound file.
	 */
	public void loop() {
		sound.loop();
	}

	public void playAndLoop() {
		play();
		loop();
	}

	public void stop() {
		sound.stop();
	}
}
