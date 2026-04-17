/*
 * Source: https://github.com/gurkenlabs/litiengine/blob/main/litiengine/src/main/java/de/gurkenlabs/litiengine/sound/SoundEngine.java
 *
 * MIT License
 *
 * Copyright (c) 2016 - 2024 Gurkenlabs
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package pi.resources.sound;

import static pi.Controller.sounds;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.LineUnavailableException;

import pi.annotations.Internal;

/**
 * Die {@link SoundEngine}-Klasse bietet Methoden an, um <b>Klänge</b> (Sound)
 * und <b>Musik</b> (Music) im Spiel <b>wiederzugeben</b>.
 *
 * <p>
 * Jede Audio-Datei kann sowohl als Musik als auch als Klang abgespielt. Der
 * Hauptunterschied zwischen Musik und Klang ist: Die
 * {@code playMusic()}-Methoden ermöglichen eine feinere Steuerung der
 * Wiedergabe durch zwei Parameter:
 * </p>
 *
 * <ol>
 * <li>Parameter {@code restart}: Ob die aktuelle Musikwiedergabe von neuem
 * gestartet werden kann.</li>
 * <li>Parameter {@code stop}: Ob die neu gestartet Musikwiedergabe eine
 * laufende Musikwiedergabe stoppen soll.</li>
 * </ol>
 *
 * <p>
 * Die {@link SoundEngine} kann standardmäßig {@code .wav}, {@code .mp3} und
 * {@code .ogg} Dateien abspielen. Wenn andere Dateierweiterungen benötigt
 * werden, muss eine eigene SPI-Implementierung geschrieben und dem Projekt
 * hinzugefügt werden.
 * </p>
 *
 * @author Steffen Wilke
 * @author Matthias Wilke
 */
public final class SoundEngine
{
    private static final SoundEngine INSTANCE = new SoundEngine();

    public final ExecutorService executor = Executors
        .newCachedThreadPool(new ThreadFactory()
        {
            private int id = 0;

            @Override
            public Thread newThread(Runnable r)
            {
                return new Thread(r, "Sound Playback Thread " + ++id);
            }
        });

    private final Logger log = Logger.getLogger(SoundEngine.class.getName());

    /**
     * Die aktuelle Musikwiedergabe.
     */
    private MusicPlayback music;

    private final Collection<MusicPlayback> allMusic = ConcurrentHashMap
        .newKeySet();

    private final Collection<SoundPlayback> allSounds = ConcurrentHashMap
        .newKeySet();

    private SoundContainer soundsContainer = sounds;

    private boolean restartDefault = false;

    /**
     * Standardwert für einige Methoden-Overload, die Musik abspielen. Gibt an,
     * ob momentan abspielende Musik gestoppt werden soll.
     */
    private boolean stopDefault = true;

    /**
     * Ein privater Konstruktor, um sicherzustellen, dass nur eine Instanz
     * existiert.
     */
    @Internal
    private SoundEngine()
    {
    }

    /**
     * Liefert die Singleton-Instanz der {@link SoundEngine}.
     *
     * @return Die Singleton-Instanz.
     */
    public static SoundEngine getInstance()
    {
        return INSTANCE;
    }

    /**
     * Sets the currently playing track to a {@code LoopedTrack} with the
     * specified music {@code Sound}. This has no effect if the specified track
     * is already playing.
     *
     * @param sound Der Klang, der abgespielt werden soll.
     *
     * @return Ermöglicht die Steuerung der Musikwiedergabe.
     */
    public MusicPlayback playMusic(Sound sound)
    {
        return playMusic(new LoopedTrack(sound), restartDefault, stopDefault);
    }

    /**
     * Sets the currently playing track to a {@code LoopedTrack} with the
     * specified music {@code Sound}. This has no effect if the specified track
     * is already playing.
     *
     * @param sound Der Klang, der abgespielt werden soll.
     * @param restart Ob die laufende Musikwiedergabe des eigenen Tracks
     *     (bestimmt mit {@link Object#equals(Object)}) neu gestartet werden
     *     soll.
     * @param stop Ob die laufende Musikwiedergabe gestoppt werden soll.
     *
     * @return Ermöglicht die Steuerung der Musikwiedergabe.
     */
    public MusicPlayback playMusic(Sound sound, boolean restart, boolean stop)
    {
        return playMusic(new LoopedTrack(sound), null, restart, stop);
    }

    /**
     * Sets the currently playing track to the specified track. This has no
     * effect if the specified track is already playing.
     *
     * @param track Die Audiospur, die gespielt werden soll.
     *
     * @return Ermöglicht die Steuerung der Musikwiedergabe.
     */
    public MusicPlayback playMusic(Track track)
    {
        return playMusic(track, null, restartDefault, stopDefault);
    }

    /**
     * Spielt die als Zeichenkette angegebene Audio-Datei in einer
     * Endlosschleife ab. Wird diese Audio-Datei bereits abgespielt, so wird
     * diese Wiedergabe nicht unterbrochen. Der Aufruf dieser Methode ist dann
     * ohne Wirkung.
     *
     * @param music Die als Zeichenkette angegebene Audio-Datei, die abgespielt
     *     werden soll.
     *
     * @return Ermöglicht die Steuerung der Musikwiedergabe.
     */
    public MusicPlayback playMusic(String music)
    {
        return playMusic(getSound(music));
    }

    /**
     * Sets the currently playing track to a {@code LoopedTrack} with the
     * specified music {@code Sound}. This has no effect if the specified track
     * is already playing.
     *
     * @param music Die als Zeichenkette angegebene Audio-Datei, die abgespielt
     *     werden soll
     * @param restart Ob die laufende Musikwiedergabe des eigenen Tracks
     *     (bestimmt mit {@link Object#equals(Object)}) neu gestartet werden
     *     soll.
     * @param stop Ob die laufende Musikwiedergabe gestoppt werden soll.
     *
     * @return Ermöglicht die Steuerung der Musikwiedergabe.
     */
    public MusicPlayback playMusic(String music, boolean restart, boolean stop)
    {
        return playMusic(getSound(music), restart, stop);
    }

    /**
     * Sets the currently playing track to the specified track.
     *
     * @param track Die Audiospur, die gespielt werden soll.
     * @param restart Ob die laufende Musikwiedergabe des eigenen Tracks
     *     (bestimmt mit {@link Object#equals(Object)}) neu gestartet werden
     *     soll.
     *
     * @return Ermöglicht die Steuerung der Musikwiedergabe.
     */
    public MusicPlayback playMusic(Track track, boolean restart)
    {
        return playMusic(track, null, restart, stopDefault);
    }

    /**
     * Plays the specified track.
     *
     * @param track Die Audiospur, die gespielt werden soll.
     * @param restart Ob die laufende Musikwiedergabe des eigenen Tracks
     *     (bestimmt mit {@link Object#equals(Object)}) neu gestartet werden
     *     soll.
     * @param stop Ob die laufende Musikwiedergabe gestoppt werden soll.
     *
     * @return Ermöglicht die Steuerung der Musikwiedergabe.
     */
    public MusicPlayback playMusic(Track track, boolean restart, boolean stop)
    {
        return playMusic(track, null, restart, stop);
    }

    /**
     * Plays the specified track, optionally configuring it before starting.
     *
     * @param track Die Audiospur, die gespielt werden soll.
     * @param config A call to configure the playback prior to starting, which
     *     can be {@code null}
     * @param restart Ob die laufende Musikwiedergabe des eigenen Tracks
     *     (bestimmt mit {@link Object#equals(Object)}) neu gestartet werden
     *     soll.
     * @param stop Ob die laufende Musikwiedergabe gestoppt werden soll.
     *
     * @return Ermöglicht die Steuerung der Musikwiedergabe.
     */
    public synchronized MusicPlayback playMusic(Track track,
            Consumer<? super MusicPlayback> config, boolean restart,
            boolean stop)
    {
        if (!restart && music != null && music.isPlaying()
                && music.getTrack().equals(track))
        {
            return music;
        }
        try
        {
            MusicPlayback playback = new MusicPlayback(track);
            if (config != null)
            {
                config.accept(playback);
            }
            if (stop)
            {
                stopMusic();
            }
            allMusic.add(playback);
            playback.start();
            music = playback;
            return playback;
        }
        catch (LineUnavailableException | IllegalArgumentException e)
        {
            resourceFailure(e);
            return null;
        }
    }

    /**
     * Spielt zwei als Zeichenkette angegeben Audiodateien ab: die Erste nur
     * einmalig und die darauf Folgende in einer Endlosschleife.
     *
     * @param intro Die Eingangsmusik als Zeichenkette angegeben.
     * @param loop Die zu wiederholende Musik als Zeichenkette.
     *
     * @return Ermöglicht die Steuerung der Musikwiedergabe.
     */
    public MusicPlayback playIntroTrack(String intro, String loop)
    {
        return playMusic(new IntroTrack(getSound(intro), getSound(loop)));
    }

    /**
     * Gets the "main" music that is playing. This usually means the last call
     * to {@code playMusic}, though if the music has been stopped it will be
     * {@code null}.
     *
     * @return The main music, which could be {@code null}.
     */
    public synchronized MusicPlayback getMusic()
    {
        return music;
    }

    /**
     * Liefert eine Liste mit allen Musikwiedergaben.
     *
     * @return Eine Liste mit allen Musikwiedergaben.
     */
    public synchronized Collection<MusicPlayback> getAllMusic()
    {
        return Collections.unmodifiableCollection(allMusic);
    }

    /**
     * Stoppt die Wiedergabe der aktuellen Hintergrundmusik.
     */
    public synchronized void stopMusic()
    {
        for (MusicPlayback track : allMusic)
        {
            track.cancel();
        }
    }

    public Sound getSound(String filePath)
    {
        return soundsContainer.get(filePath);
    }

    /**
     * Creates an {@code SoundPlayback} object that can be configured prior to
     * starting.
     *
     * <p>
     * Unlike the {@code playSound} methods, the {@code SoundPlayback} objects
     * returned by this method must be started using the
     * {@link Playback#start()} method. However, necessary resources are
     * acquired <em>immediately</em> upon calling this method, and will remain
     * in use until the playback is either cancelled or finalized.
     *
     * @param sound The sound to play
     * @param loop Whether to loop the sound
     *
     * @return An {@code SoundPlayback} object that can be configured prior to
     *     starting, but will need to be manually started.
     */
    public SoundPlayback createSoundPlayback(Sound sound, boolean loop)
    {
        try
        {
            return new SoundPlayback(sound, loop);
        }
        catch (LineUnavailableException | IllegalArgumentException e)
        {
            resourceFailure(e);
            return null;
        }
    }

    public SoundPlayback createSoundPlayback(String filePath, boolean loop)
    {
        return createSoundPlayback(getSound(filePath), loop);
    }

    public void addSound(SoundPlayback playback)
    {
        allSounds.add(playback);
    }

    public SoundPlayback playSound(Sound sound, boolean loop)
    {
        if (sound == null)
        {
            return null;
        }
        SoundPlayback playback = createSoundPlayback(sound, loop);
        if (playback == null)
        {
            return null;
        }
        playback.start();
        return playback;
    }

    public SoundPlayback playSound(final String filePath, boolean loop)
    {
        return playSound(getSound(filePath), loop);
    }

    public SoundPlayback playSound(final String filePath)
    {
        return playSound(filePath, false);
    }

    private void resourceFailure(Throwable e)
    {
        log.log(Level.WARNING, "could not open a line", e);
    }
}
