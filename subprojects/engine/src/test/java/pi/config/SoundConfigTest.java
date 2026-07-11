/*
 * Engine Pi ist eine anfaengerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2026 Josef Friedrich and contributors.
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
package pi.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * @author Josef Friedrich
 */
class SoundConfigTest
{
    SoundConfig config;

    @BeforeEach
    void setUp()
    {
        config = new SoundConfig();
    }

    @Test
    void defaultValues()
    {
        assertEquals(0.5, config.soundVolume());
        assertEquals(0.5, config.musicVolume());
    }

    @Nested
    class SoundVolumeTest
    {
        @Test
        void setValue()
        {
            config.soundVolume(0.2);
            assertEquals(0.2, config.soundVolume());
            config.soundVolume(0.9);
            assertEquals(0.9, config.soundVolume());
        }

        @Test
        void chaining()
        {
            assertSame(config, config.soundVolume(0.8));
        }
    }

    @Nested
    class MusicVolumeTest
    {
        @Test
        void setValue()
        {
            config.musicVolume(0.3);
            assertEquals(0.3, config.musicVolume());
            config.musicVolume(0.7);
            assertEquals(0.7, config.musicVolume());
        }

        @Test
        void chaining()
        {
            assertSame(config, config.musicVolume(0.8));
        }
    }
}
