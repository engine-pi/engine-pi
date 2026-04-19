/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2025 Josef Friedrich and contributors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package pi.resources.sound;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import javax.sound.sampled.AudioFormat;

import pi.Controller;
import pi.annotations.API;
import pi.annotations.ChainableMethod;
import pi.annotations.Getter;
import pi.annotations.Setter;
import pi.debug.ToStringFormatter;

// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/classes/resources/sound/MulitpleSoundsTrackDemo.java

/**
 * Ein {@link Track}, der mehrere {@link Sound}s nacheinander abspielt.
 *
 * <p>
 * Alle Klänge müssen dasselbe {@link AudioFormat} verwenden. Optional kann der
 * Track so konfiguriert werden, dass die enthaltenen Sounds nach dem letzten
 * Eintrag wieder von vorne abgespielt werden.
 * </p>
 *
 * @author Josef Friedrich
 *
 * @since 0.47.0
 */
public class MulitpleSoundsTrack implements Track
{
    /* MulitpleSoundsTrack(String[]|Sound[]|List<Sound> sounds) */

    /**
     * Erstellt einen {@link Track} durch Angabe der Klänge als
     * <b>Dateipfade</b>, der standardmäßig <b>wiederholt</b> wird.
     *
     * @param filePath Die <b>Klänge</b>, die in ihrer übergebenen Reihenfolge
     *     abgespielt werden.
     *
     * @since 0.47.0
     */
    public MulitpleSoundsTrack(String... filePath)
    {
        this(true, filePath);
    }

    /**
     * Erstellt einen {@link Track} durch Angabe einer variablen Anzahl an
     * {@link Sound}-Parametern, der standardmäßig <b>wiederholt</b> wird.
     *
     * @param sound Die <b>Klänge</b>, die in ihrer übergebenen Reihenfolge
     *     abgespielt werden.
     *
     * @since 0.47.0
     */
    public MulitpleSoundsTrack(Sound... sound)
    {
        this(true, sound);
    }

    /**
     * Erstellt einen {@link Track} durch Angabe einer {@link List Liste} mit
     * {@link Sound}-Objekten, der standardmäßig <b>wiederholt</b> wird.
     *
     * @param sounds Die <b>Klänge</b>, die in ihrer übergebenen Reihenfolge
     *     abgespielt werden.
     *
     * @since 0.47.0
     */
    public MulitpleSoundsTrack(List<Sound> sounds)
    {
        this(true, sounds);
    }

    /* MulitpleSoundsTrack(boolean loop, String[]|Sound[]|List<Sound> sounds) */

    /**
     * Erstellt einen {@link Track} durch Angabe der Klänge als
     * <b>Dateipfade</b>.
     *
     * @param loop {@code true}, wenn nach dem letzten Klang wieder beim ersten
     *     Klang begonnen werden soll, sonst {@code false}.
     * @param filePath Die <b>Klänge</b>, die in ihrer übergebenen Reihenfolge
     *     abgespielt werden.
     *
     * @since 0.47.0
     */
    public MulitpleSoundsTrack(boolean loop, String... filePath)
    {
        this.sounds = Controller.sounds.getMultipleAsList(filePath);
        checkSounds();
        this.loop = loop;
    }

    /**
     * Erstellt einen {@link Track} durch Angabe einer variablen Anzahl an
     * {@link Sound}-Parametern.
     *
     * @param loop {@code true}, wenn nach dem letzten Klang wieder beim ersten
     *     Klang begonnen werden soll, sonst {@code false}.
     * @param sounds Die <b>Klänge</b>, die in ihrer übergebenen Reihenfolge
     *     abgespielt werden.
     *
     * @since 0.47.0
     */
    public MulitpleSoundsTrack(boolean loop, Sound... sounds)
    {
        this.sounds = List.copyOf(Arrays.asList(sounds));
        checkSounds();
        this.loop = loop;
    }

    /**
     * Erstellt einen {@link Track} durch Angabe einer {@link List Liste} mit
     * {@link Sound}-Objekten, der standardmäßig <b>wiederholt</b> wird.
     *
     * @param loop {@code true}, wenn nach dem letzten Klang wieder beim ersten
     *     Klang begonnen werden soll, sonst {@code false}.
     * @param sounds Die <b>Klänge</b>, die in ihrer übergebenen Reihenfolge
     *     abgespielt werden.
     *
     * @since 0.47.0
     */
    public MulitpleSoundsTrack(boolean loop, List<Sound> sounds)
    {
        this.sounds = List.copyOf(sounds);
        checkSounds();
        this.loop = loop;
    }

    /**
     * Prüft, ob mindestens ein Klang vorhanden ist und alle Klänge dasselbe
     * Audioformat besitzen.
     *
     * @since 0.47.0
     *
     * @throws IllegalArgumentException Wenn die {@link #sounds}-Liste leer ist.
     */
    private void checkSounds()
    {
        Objects.requireNonNull(sounds);
        if (sounds.isEmpty())
        {
            throw new IllegalArgumentException(
                    "Die Klasse MulitpleSoundsTrack benötigt mindestens einen Klang (Sound).");
        }

        final Sound first = Objects.requireNonNull(sounds.get(0));
        for (int i = 1; i < sounds.size(); i++)
        {
            Sound current = Objects.requireNonNull(sounds.get(i));
            if (!first.format().matches(current.format()))
            {
                throw new IllegalArgumentException(
                        "Die Audioformate der Klänge (Sounds) stimmen nicht überein: "
                                + first.format() + " und " + current.format());
            }
        }
    }

    private class LoopingIterator implements Iterator<Sound>
    {
        private int index = 0;

        @Override
        public boolean hasNext()
        {
            return true;
        }

        @Override
        public Sound next()
        {
            if (sounds.isEmpty())
            {
                throw new NoSuchElementException();
            }
            Sound sound = sounds.get(index);
            index = (index + 1) % sounds.size();
            return sound;
        }
    }

    @Override
    public Iterator<Sound> iterator()
    {
        if (loop)
        {
            return new LoopingIterator();
        }
        return sounds.iterator();
    }

    /* soundes */

    /**
     * Die <b>Klänge</b>, die in ihrer übergebenen Reihenfolge abgespielt
     * werden.
     *
     * @since 0.47.0
     */
    private List<Sound> sounds;

    /**
     * Gibt die <b>Klänge</b> zurück, die in ihrer übergebenen Reihenfolge
     * abgespielt werden.
     *
     * @return Die <b>Klänge</b>, die in ihrer übergebenen Reihenfolge
     *     abgespielt werden.
     *
     * @since 0.47.0
     */
    @API
    @Getter
    public List<Sound> sounds()
    {
        return sounds;
    }

    /**
     * Setzt die <b>Klänge</b>, die in ihrer übergebenen Reihenfolge abgespielt
     * werden.
     *
     * @param sounds Die <b>Klänge</b>, die in ihrer übergebenen Reihenfolge
     *     abgespielt werden.
     *
     * @since 0.47.0
     */
    @API
    @Setter
    @ChainableMethod
    public MulitpleSoundsTrack sounds(List<Sound> sounds)
    {
        this.sounds = sounds;
        checkSounds();
        return this;
    }

    /* loop */

    /**
     * Ob dieser Track in einer <b>Endlosschleife</b> abgespielt wird.
     *
     * @since 0.47.0
     */
    private boolean loop;

    /**
     * Gibt an, ob dieser Track in einer <b>Endlosschleife</b> abgespielt wird.
     *
     * @return {@code true}, wenn der Track nach dem letzten Klang wieder von
     *     vorne beginnt.
     *
     * @since 0.47.0
     */
    @API
    @Getter
    public boolean loop()
    {
        return loop;
    }

    /**
     * Setzt, ob dieser Track in einer <b>Endlosschleife</b> abgespielt wird.
     *
     * @param loop Ob dieser Track in einer <b>Endlosschleife</b> abgespielt
     *     wird.
     *
     * @since 0.47.0
     */
    @API
    @Setter
    @ChainableMethod
    public MulitpleSoundsTrack loop(boolean loop)
    {
        this.loop = loop;
        return this;
    }

    /**
     * Liefert das Audioformat aller in diesem Track enthaltenen Klänge.
     *
     * @return Das gemeinsame Audioformat des Tracks.
     *
     * @since 0.47.0
     */
    @Getter
    @Override
    public AudioFormat format()
    {
        return sounds.get(0).format();
    }

    /**
     * @hidden
     */
    @Override
    public boolean equals(Object other)
    {
        if (this == other)
        {
            return true;
        }

        if (other instanceof MulitpleSoundsTrack otherTrack)
        {
            if (loop != otherTrack.loop())
            {
                return false;
            }

            if (sounds.size() != otherTrack.sounds.size())
            {
                return false;
            }

            for (int i = 0; i < sounds.size(); i++)
            {
                if (sounds.get(i) != otherTrack.sounds.get(i))
                {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * @hidden
     */
    @Override
    public int hashCode()
    {
        return sounds.hashCode() * 31 + Boolean.hashCode(loop) + 0x4f9a1b3d;
    }

    /**
     * @hidden
     */
    @Override
    public String toString()
    {
        var formatter = new ToStringFormatter(this);

        formatter.append("sounds", sounds);

        if (!loop)
        {
            formatter.append("loop", loop);
        }
        return formatter.format();
    }
}
