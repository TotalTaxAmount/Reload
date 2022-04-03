package com.invisiblecat.reload.client.ui.sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class PlaySounds {
    private final File file;

    public PlaySounds(File f) {
        this.file = f;
    }
    public void playSound() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(this.file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
