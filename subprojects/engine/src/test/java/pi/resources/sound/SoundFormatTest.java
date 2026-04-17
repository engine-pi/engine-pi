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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static pi.resources.sound.SoundFormat.UNSUPPORTED;
import static pi.resources.sound.SoundFormat.OGG;
import static pi.resources.sound.SoundFormat.MP3;
import static pi.resources.sound.SoundFormat.WAV;
import static pi.resources.sound.SoundFormat.get;
import static pi.resources.sound.SoundFormat.isSupported;

/**
 * @author Josef Friedrich
 *
 * @since 0.47.0
 */
class SoundFormatTest
{

    @Nested
    class GetTest
    {
        @Test
        void ignoringCaseAndLeadingDot()
        {

            assertEquals(OGG, get("ogg"));
            assertEquals(MP3, get(".MP3"));
            assertEquals(WAV, get("Wav"));
        }

        @Test
        void unsupportedForUnknownOrEmptyFormat()
        {
            assertEquals(UNSUPPORTED, get("music/audio.mp3"));
            assertEquals(UNSUPPORTED, get(null));
            assertEquals(UNSUPPORTED, get(""));
            assertEquals(UNSUPPORTED, get("flac"));
        }
    }

    @Nested
    class IsSupported
    {

        @Test
        void recognizesSupportedExtensionsFromString()
        {
            assertTrue(isSupported("intro.ogg"));
            assertTrue(isSupported("effect.MP3"));
            assertTrue(isSupported("voice.WaV"));
        }

        @Test
        void rejectsUnknownOrMissingExtensionsFromString()
        {
            assertFalse(isSupported("intro.flac"));
            assertFalse(isSupported("README"));
            assertFalse(isSupported((String) null));
        }

        @Test
        void recognizesSupportedExtensionsFromFile()
        {
            assertTrue(isSupported(new File("music/theme.ogg")));
            assertTrue(isSupported(new File("effects/HIT.MP3")));
        }
    }

    @Test
    void allExtensionsReturnsAllSupportedFormatsWithoutUnsupported()
    {
        assertArrayEquals(new String[] { "ogg", "mp3", "wav" },
            SoundFormat.allExtensions());
    }

    @Test
    void fileExtensionAndToStringUseLowerCase()
    {
        assertEquals(".ogg", SoundFormat.OGG.fileExtension());
        assertEquals("ogg", SoundFormat.OGG.toString());
        assertEquals("unsupported", SoundFormat.UNSUPPORTED.toString());
    }
}
