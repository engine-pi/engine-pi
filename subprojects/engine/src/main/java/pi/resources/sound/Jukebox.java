package pi.resources.sound;

import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

import pi.annotations.Internal;

/**
 * Statische Fassade fuer {@link AudioEngine}.
 *
 * @author Josef Friedrich
 */
public final class Jukebox
{

    public static final ExecutorService EXECUTOR = AudioEngine
        .getInstance().executor;

    @Internal
    private Jukebox()
    {
        throw new UnsupportedOperationException();
    }

    private static AudioEngine engine()
    {
        return AudioEngine.getInstance();
    }

    public static MusicPlayback playMusic(Sound sound)
    {
        return engine().playMusic(sound);
    }

    public static MusicPlayback playMusic(Sound sound, boolean restart,
            boolean stop)
    {
        return engine().playMusic(sound, restart, stop);
    }

    public static MusicPlayback playMusic(Music track)
    {
        return engine().playMusic(track);
    }

    public static MusicPlayback playMusic(String music)
    {
        return engine().playMusic(music);
    }

    public static MusicPlayback playMusic(String music, boolean restart,
            boolean stop)
    {
        return engine().playMusic(music, restart, stop);
    }

    public static MusicPlayback playMusic(Music track, boolean restart)
    {
        return engine().playMusic(track, restart);
    }

    public static MusicPlayback playMusic(Music track, boolean restart,
            boolean stop)
    {
        return engine().playMusic(track, restart, stop);
    }

    public static MusicPlayback playMusic(Music track,
            Consumer<? super MusicPlayback> config, boolean restart,
            boolean stop)
    {
        return engine().playMusic(track, config, restart, stop);
    }

    public static MusicPlayback playIntroTrack(String intro, String loop)
    {
        return engine().playIntroTrack(intro, loop);
    }

    public static MusicPlayback getMusic()
    {
        return engine().music();
    }

    public static Collection<MusicPlayback> getAllMusic()
    {
        return engine().allMusic();
    }

    public static void stopMusic()
    {
        engine().stopMusic();
    }

    public static Sound getSound(String filePath)
    {
        return engine().sound(filePath);
    }

    public static SoundPlayback createSoundPlayback(Sound sound, boolean loop)
    {
        return engine().createSoundPlayback(sound, loop);
    }

    public static SoundPlayback createSoundPlayback(String filePath,
            boolean loop)
    {
        return engine().createSoundPlayback(filePath, loop);
    }

    public static void addSound(SoundPlayback playback)
    {
        engine().addSound(playback);
    }

    public static SoundPlayback playSound(Sound sound, boolean loop)
    {
        return engine().playSound(sound, loop);
    }

    public static SoundPlayback playSound(final String filePath, boolean loop)
    {
        return engine().playSound(filePath, loop);
    }

    public static SoundPlayback playSound(final String filePath)
    {
        return engine().playSound(filePath);
    }
}
