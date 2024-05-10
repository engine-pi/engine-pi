package rocks.friedrich.engine_omega.sound;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.junit.Test;

import rocks.friedrich.engine_omega.internal.io.ResourceLoader;

public class SinglePlayTrackTest
{
    @Test
    public void testPlayback() throws IOException,
            UnsupportedAudioFileException, LineUnavailableException
    {
        Sound sound = new Sound(
                ResourceLoader.loadAsStream("sounds/audio-logo.mp3"),
                "audio-logo");
        SinglePlayTrack track = new SinglePlayTrack(sound);
        MusicPlayback playback = new MusicPlayback(track);
        playback.play(sound);



        SoundEngine engine = new SoundEngine();

        engine.playMusic(track);

        // MusicPlayback playback = engine.playMusic(track);
        // playback.play();


    }


}
