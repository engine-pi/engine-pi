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
import java.util.Objects;

import javax.sound.sampled.AudioFormat;

import pi.annotations.Getter;

/**
 * Ein {@code Track}, der eine Liste von {@code Sound}s einmalig in
 * Einfügereihenfolge abspielt.
 *
 * @author Josef Friedrich
 *
 * @since 0.47.0
 */
public class PlaylistTrack implements Track
{
    private final List<Sound> sounds;

    /**
     * Initialisiert einen neuen {@code PlaylistTrack} für die angegebenen
     * Sounds.
     *
     * @param sounds Die Sounds, die in Einfügereihenfolge abgespielt werden.
     */
    public PlaylistTrack(Sound... sounds)
    {
        Objects.requireNonNull(sounds);
        if (sounds.length == 0)
        {
            throw new IllegalArgumentException(
                    "playlist must contain at least one sound");
        }

        final Sound first = Objects.requireNonNull(sounds[0]);
        for (int i = 1; i < sounds.length; i++)
        {
            Sound current = Objects.requireNonNull(sounds[i]);
            if (!first.format().matches(current.format()))
            {
                throw new IllegalArgumentException(
                        first.format() + " does not match " + current.format());
            }
        }

        this.sounds = List.copyOf(Arrays.asList(sounds));
    }

    public PlaylistTrack(List<Sound> sounds)
    {
        this.sounds = sounds;
    }

    @Override
    public Iterator<Sound> iterator()
    {
        return sounds.iterator();
    }

    @Getter
    @Override
    public AudioFormat format()
    {
        return sounds.get(0).format();
    }

    @Override
    public boolean equals(Object anObject)
    {
        return this == anObject || anObject instanceof PlaylistTrack pt
                && sounds.equals(pt.sounds);
    }

    @Override
    public int hashCode()
    {
        return sounds.hashCode() * 31 + 0x4f9a1b3d;
    }

    /**
     * @hidden
     */
    @Override
    public String toString()
    {
        return "playlist track: " + sounds;
    }
}
