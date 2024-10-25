package me.emil.game.main;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class Sound2 {
    private static final int BUFFER_SIZE = 128000;

    public static void testSound()
    {
        try
        {
            File soundFile = new File(Sound2.class.getResource("/sound/backgroundMusic.wav").toURI());
            playSound(soundFile);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * @param filename the name of the file that is going to be played
     */
    public static void playSound(File yourFile) {

        AudioInputStream stream;
        AudioFormat format;
        DataLine.Info info;
        Clip clip;

        try {
            stream = AudioSystem.getAudioInputStream(yourFile);
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);

            clip.open(stream);
            clip.start();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}