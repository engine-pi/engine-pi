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

import javax.sound.sampled.LineUnavailableException;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import pi.annotations.API;
import pi.annotations.Getter;
import pi.annotations.Internal;

/**
 * Die {@link AudioEngine}-Klasse bietet Methoden an, um <b>Klänge</b> (Sound)
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
 * Die {@link AudioEngine} kann standardmäßig {@code .wav}, {@code .mp3} und
 * {@code .ogg} Dateien abspielen. Wenn andere Dateierweiterungen benötigt
 * werden, muss eine eigene SPI-Implementierung geschrieben und dem Projekt
 * hinzugefügt werden.
 * </p>
 *
 * @author Steffen Wilke
 * @author Matthias Wilke
 */
@java.lang.SuppressWarnings("squid:S6548")
public final class AudioEngine
{
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
    private AudioEngine()
    {
    }

    private static final AudioEngine INSTANCE = new AudioEngine();

    /**
     * Liefert die Singleton-Instanz der {@link AudioEngine}.
     *
     * @return Die Singleton-Instanz.
     */
    @API
    public static AudioEngine getInstance()
    {
        return INSTANCE;
    }

    /* sound */

    /**
     * Spielt den <b>Klang</b> <b>einmalig</b> ab, der durch einen
     * <b>Dateipfad</b> als <b>Zeichenkette</b> angegeben wird.
     *
     * @param filePath Der <b>Pfad</b> zur Audiodatei als Zeichenkette.
     *
     * @return Die gestartete Klangwiedergabe.
     */
    @API
    public SoundPlayback playSound(final String filePath)
    {
        return playSound(filePath, false);
    }

    /**
     * Spielt den <b>Klang</b> ab, der durch einen <b>Dateipfad</b> als
     * <b>Zeichenkette</b> angegeben wird.
     *
     * @param filePath Der <b>Pfad</b> zur Audiodatei als Zeichenkette.
     * @param loop Gibt an, ob der Klang in einer <b>Schleife</b> abgespielt
     *     werden soll.
     *
     * @return Die gestartete Klangwiedergabe.
     */
    @API
    public SoundPlayback playSound(final String filePath, boolean loop)
    {
        return playSound(sound(filePath), loop);
    }

    /**
     * Spielt den <b>Klang</b> ab, der als {@link Sound}-Objekt angegeben wird.
     *
     * @param sound Der abzuspielende Klang.
     * @param loop Gibt an, ob der Klang in einer Schleife abgespielt werden
     *     soll.
     *
     * @return Die gestartete Klangwiedergabe oder {@code null}, falls kein
     *     Klang übergeben wurde.
     */
    @API
    public SoundPlayback playSound(Sound sound, boolean loop)
    {
        if (sound == null)
        {
            return null;
        }
        SoundPlayback playback = createSoundPlayback(sound, loop);
        playback.start();
        return playback;
    }

    /**
     * Liefert den {@link Sound} zur angegebenen Datei.
     *
     * @param filePath Der <b>Pfad</b> zur Audiodatei als Zeichenkette.
     *
     * @return Der geladene Klang.
     */
    @API
    public Sound sound(String filePath)
    {
        return soundsContainer.get(filePath);
    }

    /**
     * Erzeugt ein {@link SoundPlayback}-Objekt, das vor dem Start konfiguriert
     * werden kann.
     *
     * <p>
     * Im Gegensatz zu den {@code playSound}-Methoden muss das von dieser
     * Methode zurückgegebene {@code SoundPlayback}-Objekt manuell mit
     * {@link Playback#start()} gestartet werden. Die benötigten Ressourcen
     * werden jedoch <em>sofort</em> beim Aufruf dieser Methode reserviert und
     * bleiben belegt, bis die Wiedergabe abgebrochen oder beendet wird.
     * </p>
     *
     * @param sound Der abzuspielende Klang.
     * @param loop Gibt an, ob der Klang in einer Schleife abgespielt werden
     *     soll.
     *
     * @return Ein konfigurierbares {@code SoundPlayback}-Objekt, das manuell
     *     gestartet werden muss.
     */
    @API
    public @NonNull SoundPlayback createSoundPlayback(Sound sound, boolean loop)
    {
        try
        {
            return new SoundPlayback(sound, loop);
        }
        catch (LineUnavailableException | IllegalArgumentException e)
        {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Erzeugt ein {@link SoundPlayback}-Objekt zur angegebenen Datei.
     *
     * @param filePath Der <b>Pfad</b> zur Audiodatei als Zeichenkette.
     * @param loop Gibt an, ob der Klang in einer Schleife abgespielt werden
     *     soll.
     *
     * @return Ein konfigurierbares {@link SoundPlayback}-Objekt.
     */
    @API
    public SoundPlayback createSoundPlayback(String filePath, boolean loop)
    {
        return createSoundPlayback(sound(filePath), loop);
    }

    private final Collection<SoundPlayback> allSounds = ConcurrentHashMap
        .newKeySet();

    /**
     * Liefert eine <b>Liste</b> mit allen <b>Klangwiedergaben</b>.
     *
     * @return Eine Liste mit allen <b>Klangwiedergaben</b>.
     *
     * @since 0.47.0
     */
    @API
    @Getter
    public synchronized Collection<SoundPlayback> allSounds()
    {
        return Collections.unmodifiableCollection(allSounds);
    }

    /**
     * <b>Fügt</b> eine <b>Klangwiedergabe</b> zur internen Verwaltung hinzu.
     *
     * @param playback Die zu verwaltende <b>Klangwiedergabe</b>.
     */
    @API
    public void addSound(SoundPlayback playback)
    {
        allSounds.add(playback);
    }

    /* music */

    /* playMusic(String|Sound|Music music) */

    /**
     * Spielt die als Zeichenkette angegebene Audio-Datei in einer
     * Endlosschleife ab.playbacks.size()
     *
     * <p>
     * Wird diese Audio-Datei bereits abgespielt, so wird diese Wiedergabe nicht
     * unterbrochen. Der Aufruf dieser Methode ist dann ohne Wirkung.
     * </p>
     *
     * @param music Die als Zeichenkette angegebene Audio-Datei, die abgespielt
     *     werden soll.
     *
     * @return Ermöglicht die Steuerung der Musikwiedergabe.
     */
    @API
    public MusicPlayback playMusic(String music)
    {
        return playMusic(sound(music));
    }

    /**
     * Setzt die aktuell abgespielte Spur auf einen {@link LoopedMusic} mit dem
     * angegebenen Musik-{@link Sound}.
     *
     * <p>
     * Hat keine Wirkung, wenn die angegebene Spur bereits läuft.
     * </p>
     *
     * @param sound Der Klang, der abgespielt werden soll.
     *
     * @return Ermöglicht die Steuerung der Musikwiedergabe.
     */
    @API
    public MusicPlayback playMusic(Sound sound)
    {
        return playMusic(new LoopedMusic(sound), restartDefault, stopDefault);
    }

    /**
     * Setzt die aktuell abgespielte Audiospur auf die angegebene Audiospur. Hat
     * keine Wirkung, wenn die angegebene Spur bereits läuft.
     *
     * @param music Die Audiospur, die gespielt werden soll.
     *
     * @return Ermöglicht die Steuerung der Musikwiedergabe.
     */
    @API
    public MusicPlayback playMusic(Music music)
    {
        return playMusic(music, null, restartDefault, stopDefault);
    }

    /* playMusic(Music music, restart) */

    /**
     * Setzt die aktuell abgespielte <b>Audiospur</b> auf die angegebene
     * Audiospur.
     *
     * @param music Die Audiospur, die gespielt werden soll.
     * @param restart Ob die laufende Musikwiedergabe der eigenen Musik
     *     (bestimmt mit {@link Object#equals(Object)}) <b>neu gestartet</b>
     *     werden soll.
     *
     * @return Ermöglicht die Steuerung der Musikwiedergabe.
     */
    @API
    public MusicPlayback playMusic(Music music, boolean restart)
    {
        return playMusic(music, null, restart, stopDefault);
    }

    /* playMusic(String|Sound|Music music, restart, top) */

    /**
     * Setzt die aktuell abgespielte Audiospur auf einen {@link LoopedMusic} mit
     * dem angegebenen Musik-{@link Sound}. Hat keine Wirkung, wenn die
     * angegebene Spur bereits läuft.
     *
     * @param music Die als Zeichenkette angegebene Audio-Datei, die abgespielt
     *     werden soll
     * @param restart Ob die laufende Musikwiedergabe der eigenen Musik
     *     (bestimmt mit {@link Object#equals(Object)}) <b>neu gestartet</b>
     *     werden soll.
     * @param stop Ob die laufende Musikwiedergabe <b>gestoppt</b> werden soll.
     *
     * @return Ermöglicht die Steuerung der Musikwiedergabe.
     */
    @API
    public MusicPlayback playMusic(String music, boolean restart, boolean stop)
    {
        return playMusic(sound(music), restart, stop);
    }

    /**
     * Setzt die aktuell abgespielte Spur auf einen {@link LoopedMusic} mit dem
     * angegebenen Musik-{@link Sound}. Hat keine Wirkung, wenn die angegebene
     * Spur bereits läuft.
     *
     * @param sound Der Klang, der abgespielt werden soll.
     * @param restart Ob die laufende Musikwiedergabe der eigenen Musik
     *     (bestimmt mit {@link Object#equals(Object)}) <b>neu gestartet</b>
     *     werden soll.
     * @param stop Ob die laufende Musikwiedergabe <b>gestoppt</b> werden soll.
     *
     * @return Ermöglicht die Steuerung der Musikwiedergabe.
     */
    @API
    public MusicPlayback playMusic(Sound sound, boolean restart, boolean stop)
    {
        return playMusic(new LoopedMusic(sound), null, restart, stop);
    }

    /**
     * <b>Spielt</b> die angegebene <b>Audiospur</b> ab.
     *
     * @param music Die Audiospur, die gespielt werden soll.
     * @param restart Ob die laufende Musikwiedergabe der eigenen Musik
     *     (bestimmt mit {@link Object#equals(Object)}) <b>neu gestartet</b>
     *     werden soll.
     * @param stop Ob die laufende Musikwiedergabe <b>gestoppt</b> werden soll.
     *
     * @return Ermöglicht die Steuerung der Musikwiedergabe.
     */
    @API
    public MusicPlayback playMusic(Music music, boolean restart, boolean stop)
    {
        return playMusic(music, null, restart, stop);
    }

    /**
     * Die aktuelle Musikwiedergabe.
     */
    private @Nullable MusicPlayback currentMusic;

    /**
     * Liefert die aktuell relevante <b>„Hauptmusik“</b>. In der Regel ist das
     * die letzte Wiedergabe, die mit {@code playMusic} gestartet wurde. Wenn
     * die Musik gestoppt wurde, ist der Rückgabewert {@code null}.
     *
     * @return Die Hauptmusik oder {@code null}.
     */
    @API
    @Getter
    public synchronized @Nullable MusicPlayback music()
    {
        return currentMusic;
    }

    private final Collection<MusicPlayback> allMusic = ConcurrentHashMap
        .newKeySet();

    /**
     * Spielt die angegebene <b>Audiospur</b> ab und erlaubt optional eine
     * Konfiguration vor dem Start.
     *
     * @param music Die Audiospur, die gespielt werden soll.
     * @param config Funktion zur Konfiguration der Wiedergabe vor dem Start;
     *     kann {@code null} sein.
     * @param restart Ob die laufende Musikwiedergabe der eigenen Musik
     *     (bestimmt mit {@link Object#equals(Object)}) neu gestartet werden
     *     soll.
     * @param stop Ob die laufende Musikwiedergabe gestoppt werden soll.
     *
     * @return Ermöglicht die Steuerung der Musikwiedergabe.
     */
    @API
    public synchronized @NonNull MusicPlayback playMusic(Music music,
            Consumer<? super MusicPlayback> config, boolean restart,
            boolean stop)
    {
        if (!restart && currentMusic != null && currentMusic.isPlaying()
                && currentMusic.music().equals(music))
        {
            return currentMusic;
        }
        try
        {
            MusicPlayback playback = new MusicPlayback(music);
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
            currentMusic = playback;
            return playback;
        }
        catch (LineUnavailableException | IllegalArgumentException e)
        {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Spielt zwei als Zeichenkette angegeben Audiodateien ab: die Erste nur
     * <b>einmalig</b> und die darauf Folgende in einer <b>Endlosschleife</b>.
     *
     * @param intro Die <b>Eingangsmusik</b> als Zeichenkette angegeben.
     * @param loop Die zu <b>wiederholende</b> Musik als Zeichenkette.
     *
     * @return Ermöglicht die Steuerung der Musikwiedergabe.
     */
    @API
    public MusicPlayback playIntroMusic(String intro, String loop)
    {
        return playMusic(new IntroMusic(sound(intro), sound(loop)));
    }

    /**
     * Liefert eine <b>Liste</b> mit allen <b>Musikwiedergaben</b>.
     *
     * @return Eine Liste mit allen Musikwiedergaben.
     */
    @API
    @Getter
    public synchronized Collection<MusicPlayback> allMusic()
    {
        return Collections.unmodifiableCollection(allMusic);
    }

    /**
     * <b>Stoppt</b> die Wiedergabe der aktuellen Hintergrundmusik.
     */
    @API
    public synchronized void stopMusic()
    {
        for (MusicPlayback music : allMusic)
        {
            music.cancel();
        }
    }
}
