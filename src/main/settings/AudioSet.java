package settings;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class AudioSet {
    private static void playAudio(String soundURL) {
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File(soundURL).getAbsoluteFile());
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            clip.open(audioInputStream);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        clip.start();
    }

    public static void playButtonClick() {
        playAudio("data/soundfx/buttonclick.wav");
    }

    public static void playAddShip() {
        playAudio("data/soundfx/splash.wav");
    }

    public static void playError() {
        playAudio("data/soundfx/error.wav");
    }

    public static void playMiss() {
        playAudio("data/soundfx/shoot.wav");
        try {
            TimeUnit.MILLISECONDS.sleep(1800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        playAudio("data/soundfx/waterdrop.wav");
    }

    public static void playHit() {
        playAudio("data/soundfx/shoot.wav");
        try {
            TimeUnit.MILLISECONDS.sleep(1800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        playAudio("data/soundfx/explode.wav");
    }

    public static void playVictory() {
        playAudio("data/soundfx/victory.wav");
    }

    public static void playLoser() {
        playAudio("data/soundfx/loser.wav");
    }
}
