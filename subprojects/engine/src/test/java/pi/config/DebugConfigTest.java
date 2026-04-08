/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * @author Josef Friedrich
 */
public class DebugConfigTest
{
    DebugConfig config;

    @BeforeEach
    void setUp()
    {
        config = new DebugConfig();
    }

    @Nested
    class EnabledTest
    {
        @Test
        void defaultValue()
        {
            assertFalse(config.enabled());
        }

        @Test
        void setValue()
        {
            config.enabled(true);
            assertTrue(config.enabled());
            config.enabled(false);
            assertFalse(config.enabled());
        }

        @Test
        void chaining()
        {
            assertSame(config, config.enabled(true));
        }
    }

    @Nested
    class RenderActorsTest
    {
        @Test
        void defaultValue()
        {
            assertTrue(config.renderActors());
        }

        @Test
        void setValue()
        {
            config.renderActors(false);
            assertFalse(config.renderActors());
            config.renderActors(true);
            assertTrue(config.renderActors());
        }

        @Test
        void chaining()
        {
            assertSame(config, config.renderActors(false));
        }

        @Test
        void toggle()
        {
            assertTrue(config.renderActors());
            config.toggleRenderActors();
            assertFalse(config.renderActors());
            config.toggleRenderActors();
            assertTrue(config.renderActors());
        }

        @Test
        void toggleChaining()
        {
            assertSame(config, config.toggleRenderActors());
        }
    }

    @Nested
    class ActorCoordinatesTest
    {
        @Test
        void defaultValue()
        {
            assertFalse(config.actorCoordinates());
        }

        @Test
        void setValue()
        {
            config.actorCoordinates(true);
            assertTrue(config.actorCoordinates());
            config.actorCoordinates(false);
            assertFalse(config.actorCoordinates());
        }

        @Test
        void chaining()
        {
            assertSame(config, config.actorCoordinates(true));
        }

        @Test
        void toggleShowPositions()
        {
            assertFalse(config.actorCoordinates());
            assertTrue(config.toogleShowPositions());
            assertFalse(config.toogleShowPositions());
        }
    }

    @Nested
    class RenderAABBsTest
    {
        @Test
        void defaultValue()
        {
            assertFalse(config.renderAABBs());
        }

        @Test
        void setValue()
        {
            config.renderAABBs(true);
            assertTrue(config.renderAABBs());
            config.renderAABBs(false);
            assertFalse(config.renderAABBs());
        }

        @Test
        void chaining()
        {
            assertSame(config, config.renderAABBs(true));
        }
    }

    @Nested
    class UseANSIcolorsTest
    {
        @Test
        void defaultValue()
        {
            assertFalse(config.useANSIcolors());
        }

        @Test
        void setValue()
        {
            config.useANSIcolors(true);
            assertTrue(config.useANSIcolors());
            config.useANSIcolors(false);
            assertFalse(config.useANSIcolors());
        }

        @Test
        void chaining()
        {
            assertSame(config, config.useANSIcolors(true));
        }
    }
}
