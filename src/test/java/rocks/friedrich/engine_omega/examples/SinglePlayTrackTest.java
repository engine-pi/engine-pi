package rocks.friedrich.engine_omega.examples;

import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

import rocks.friedrich.engine_omega.io.ResourceLoader;
import rocks.friedrich.engine_omega.sound.SinglePlayTrack;
import rocks.friedrich.engine_omega.sound.Sound;
import rocks.friedrich.engine_omega.sound.SoundEngine;

public class SinglePlayTrackTest
{
    public static void main(String[] args)
            throws IOException, UnsupportedAudioFileException
    {
        Sound sound = new Sound(
                ResourceLoader.loadAsStream("sounds/audio-logo.mp3"),
                "audio-logo");
        SinglePlayTrack track = new SinglePlayTrack(sound);
        SoundEngine engine = new SoundEngine();
        engine.playMusic(track);
    }
}
