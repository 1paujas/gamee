package JumpyJava;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

import javax.sound.sampled.*;
import java.io.File;

public class LagFreeSound {
    private Clip clip;

    public LagFreeSound(String path) {
        try {
            File file = new File(path);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioIn); // Loaded into RAM once right here
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (clip != null) {
            // Rewind to the beginning in case it was already played
            clip.setFramePosition(0);
            clip.start();
        }
    }
}