package pi.resources.sound;

import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

import pi.annotations.Internal;

/**
 * Statische Fassade fuer {@link SoundEngine}.
 */
public final class Jukebox
{

    public static final ExecutorService EXECUTOR = SoundEngine
        .getInstance().executor;

    @Internal
    private Jukebox()
    {
        throw new UnsupportedOperationException();
    }

    private static SoundEngine soundEngine()
    {
        return SoundEngine.getInstance();
    }

    public static MusicPlayback playMusic(Sound sound)
    {
        return soundEngine().playMusic(sound);
    }

    public static MusicPlayback playMusic(Sound sound, boolean restart,
            boolean stop)
    {
        return soundEngine().playMusic(sound, restart, stop);
    }

    public static MusicPlayback playMusic(Track track)
    {
        return soundEngine().playMusic(track);
    }

    public static MusicPlayback playMusic(String music)
    {
        return soundEngine().playMusic(music);
    }

    public static MusicPlayback playMusic(String music, boolean restart,
            boolean stop)
    {
        return soundEngine().playMusic(music, restart, stop);
    }

    public static MusicPlayback playMusic(Track track, boolean restart)
    {
        return soundEngine().playMusic(track, restart);
    }

    public static MusicPlayback playMusic(Track track, boolean restart,
            boolean stop)
    {
        return soundEngine().playMusic(track, restart, stop);
    }

    public static MusicPlayback playMusic(Track track,
            Consumer<? super MusicPlayback> config, boolean restart,
            boolean stop)
    {
        return soundEngine().playMusic(track, config, restart, stop);
    }

    public static MusicPlayback playIntroTrack(String intro, String loop)
    {
        return soundEngine().playIntroTrack(intro, loop);
    }

    public static MusicPlayback getMusic()
    {
        return soundEngine().music();
    }

    public static Collection<MusicPlayback> getAllMusic()
    {
        return soundEngine().allMusic();
    }

    public static void stopMusic()
    {
        soundEngine().stopMusic();
    }

    public static Sound getSound(String filePath)
    {
        return soundEngine().sound(filePath);
    }

    public static SoundPlayback createSoundPlayback(Sound sound, boolean loop)
    {
        return soundEngine().createSoundPlayback(sound, loop);
    }

    public static SoundPlayback createSoundPlayback(String filePath,
            boolean loop)
    {
        return soundEngine().createSoundPlayback(filePath, loop);
    }

    public static void addSound(SoundPlayback playback)
    {
        soundEngine().addSound(playback);
    }

    public static SoundPlayback playSound(Sound sound, boolean loop)
    {
        return soundEngine().playSound(sound, loop);
    }

    public static SoundPlayback playSound(final String filePath, boolean loop)
    {
        return soundEngine().playSound(filePath, loop);
    }

    public static SoundPlayback playSound(final String filePath)
    {
        return soundEngine().playSound(filePath);
    }
}
