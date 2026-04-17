/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 202 Josef Friedrich and contributors.
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import pi.resources.ResourceLoader;

/**
 * @author Josef Friedrich
 *
 * @since 0.47.0
 */
class MulitpleSoundsTrackTest
{
    @TempDir
    Path tempDir;

    @Nested
    class Constructors
    {
        @Test
        void defaultsToLoopWhenCreatedFromList() throws Exception
        {
            Sound first = createSound("first.wav", 44_100, 1);
            Sound second = createSound("second.wav", 44_100, 1);

            MulitpleSoundsTrack track = new MulitpleSoundsTrack(
                    List.of(first, second));

            assertTrue(track.loop());
            assertEquals(List.of(first, second), track.sounds());
        }

        @Test
        void loadsSoundsFromFilePaths() throws Exception
        {
            Path first = createFile("first.wav", 44_100, 1);
            Path second = createFile("second.wav", 44_100, 1);

            MulitpleSoundsTrack track = new MulitpleSoundsTrack(false,
                    first.toString(), second.toString());

            assertFalse(track.loop());
            assertEquals(2, track.sounds().size());
            assertEquals("first", track.sounds().get(0).name());
            assertEquals("second", track.sounds().get(1).name());
        }

        @Test
        void rejectsEmptySoundList()
        {
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new MulitpleSoundsTrack(false, List.of()));

            assertEquals(
                "Die Klasse MulitpleSoundsTrack benötigt mindestens einen Klang (Sound).",
                exception.getMessage());
        }

        @Test
        void rejectsSoundsWithDifferentFormats() throws Exception
        {
            Sound mono = createSound("mono.wav", 44_100, 1);
            Sound stereo = createSound("stereo.wav", 44_100, 2);

            assertThrows(IllegalArgumentException.class,
                () -> new MulitpleSoundsTrack(false, mono, stereo));
        }
    }

    @Nested
    class IteratorBehavior
    {
        @Test
        void iteratesOnceWhenLoopIsDisabled() throws Exception
        {
            Sound first = createSound("first.wav", 44_100, 1);
            Sound second = createSound("second.wav", 44_100, 1);
            MulitpleSoundsTrack track = new MulitpleSoundsTrack(false, first,
                    second);

            Iterator<Sound> iterator = track.iterator();

            assertTrue(iterator.hasNext());
            assertSame(first, iterator.next());
            assertTrue(iterator.hasNext());
            assertSame(second, iterator.next());
            assertFalse(iterator.hasNext());
            assertThrows(NoSuchElementException.class, iterator::next);
        }

        @Test
        void repeatsSequenceWhenLoopIsEnabled() throws Exception
        {
            Sound first = createSound("first.wav", 44_100, 1);
            Sound second = createSound("second.wav", 44_100, 1);
            MulitpleSoundsTrack track = new MulitpleSoundsTrack(true, first,
                    second);

            Iterator<Sound> iterator = track.iterator();

            assertTrue(iterator.hasNext());
            assertSame(first, iterator.next());
            assertSame(second, iterator.next());
            assertSame(first, iterator.next());
            assertSame(second, iterator.next());
            assertTrue(iterator.hasNext());
        }
    }

    @Nested
    class Accessors
    {
        @Test
        void formatUsesFirstSoundFormat() throws Exception
        {
            Sound first = createSound("first.wav", 22_050, 1);
            Sound second = createSound("second.wav", 22_050, 1);
            MulitpleSoundsTrack track = new MulitpleSoundsTrack(false, first,
                    second);

            assertEquals(first.format(), track.format());
        }

        @Test
        void loopSetterIsChainable() throws Exception
        {
            Sound first = createSound("first.wav", 44_100, 1);
            MulitpleSoundsTrack track = new MulitpleSoundsTrack(false, first);

            MulitpleSoundsTrack returned = track.loop(true);

            assertSame(track, returned);
            assertTrue(track.loop());
        }

        @Test
        void soundsSetterIsChainableAndRevalidatesInput() throws Exception
        {
            Sound first = createSound("first.wav", 44_100, 1);
            Sound second = createSound("second.wav", 44_100, 1);
            MulitpleSoundsTrack track = new MulitpleSoundsTrack(false, first);

            MulitpleSoundsTrack returned = track.sounds(List.of(first, second));

            assertSame(track, returned);
            assertEquals(List.of(first, second), track.sounds());
        }
    }

    @Nested
    class EqualsMethod
    {
        @Test
        void returnsTrueForSameInstance() throws Exception
        {
            Sound first = createSound("first.wav", 44_100, 1);
            Sound second = createSound("second.wav", 44_100, 1);
            MulitpleSoundsTrack track = new MulitpleSoundsTrack(true, first,
                    second);

            assertTrue(track.equals(track));
        }

        @Test
        void returnsTrueForSameSoundReferencesAndLoopValue() throws Exception
        {
            Sound first = createSound("first.wav", 44_100, 1);
            Sound second = createSound("second.wav", 44_100, 1);

            MulitpleSoundsTrack left = new MulitpleSoundsTrack(true, first,
                    second);
            MulitpleSoundsTrack right = new MulitpleSoundsTrack(true, first,
                    second);

            assertTrue(left.equals(right));
            assertTrue(right.equals(left));
        }

        @Test
        void returnsFalseForNullAndOtherType() throws Exception
        {
            Sound first = createSound("first.wav", 44_100, 1);
            MulitpleSoundsTrack track = new MulitpleSoundsTrack(true, first);

            assertFalse(track.equals(null));
            assertFalse(track.equals("not a track"));
        }

        @Test
        void returnsFalseWhenLoopDiffers() throws Exception
        {
            Sound first = createSound("first.wav", 44_100, 1);
            Sound second = createSound("second.wav", 44_100, 1);

            MulitpleSoundsTrack looping = new MulitpleSoundsTrack(true, first,
                    second);
            MulitpleSoundsTrack nonLooping = new MulitpleSoundsTrack(false,
                    first, second);

            assertFalse(looping.equals(nonLooping));
        }

        @Test
        void returnsFalseWhenOrderOrIdentityDiffers() throws Exception
        {
            Sound first = createSound("first.wav", 44_100, 1);
            Sound second = createSound("second.wav", 44_100, 1);
            Sound third = createSound("third.wav", 44_100, 1);

            MulitpleSoundsTrack track = new MulitpleSoundsTrack(true, first,
                    second);
            MulitpleSoundsTrack differentOrder = new MulitpleSoundsTrack(true,
                    second, first);
            MulitpleSoundsTrack differentIdentity = new MulitpleSoundsTrack(
                    true, first, third);

            assertFalse(track.equals(differentOrder));
            assertFalse(track.equals(differentIdentity));
        }
    }

    private Sound createSound(String fileName, float sampleRate, int channels)
            throws IOException, UnsupportedAudioFileException
    {
        Path file = createFile(fileName, sampleRate, channels);
        try (InputStream input = ResourceLoader.get(file.toString()))
        {
            return new Sound(input, file.getFileName().toString());
        }
    }

    private Path createFile(String fileName, float sampleRate, int channels)
            throws IOException
    {
        Path file = tempDir.resolve(fileName);
        AudioFormat format = new AudioFormat(sampleRate, 16, channels, true,
                false);
        byte[] audioData = new byte[format.getFrameSize() * 16];

        try (AudioInputStream stream = new AudioInputStream(
                new ByteArrayInputStream(audioData), format,
                audioData.length / format.getFrameSize()))
        {
            AudioSystem.write(stream, AudioFileFormat.Type.WAVE, file.toFile());
        }
        return file;
    }
}
