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

    @Test
    void defaultEnabled()
    {
        assertFalse(config.enabled());
    }

    @Test
    void enabled()
    {
        config.enabled(true);
        assertTrue(config.enabled());
        config.enabled(false);
        assertFalse(config.enabled());
    }

    @Test
    void enabledChaining()
    {
        assertSame(config, config.enabled(true));
    }

    @Test
    void defaultVerbose()
    {
        assertFalse(config.verbose());
    }

    @Test
    void verbose()
    {
        config.verbose(true);
        assertTrue(config.verbose());
        config.verbose(false);
        assertFalse(config.verbose());
    }

    @Test
    void verboseChaining()
    {
        assertSame(config, config.verbose(true));
    }

    @Test
    void defaultRenderActors()
    {
        assertTrue(config.renderActors());
    }

    @Test
    void renderActors()
    {
        config.renderActors(false);
        assertFalse(config.renderActors());
        config.renderActors(true);
        assertTrue(config.renderActors());
    }

    @Test
    void renderActorsChaining()
    {
        assertSame(config, config.renderActors(false));
    }

    @Test
    void toggleRenderActors()
    {
        assertTrue(config.renderActors());
        config.toggleRenderActors();
        assertFalse(config.renderActors());
        config.toggleRenderActors();
        assertTrue(config.renderActors());
    }

    @Test
    void toggleRenderActorsChaining()
    {
        assertSame(config, config.toggleRenderActors());
    }

    @Test
    void defaultActorCoordinates()
    {
        assertFalse(config.actorCoordinates());
    }

    @Test
    void actorCoordinates()
    {
        config.actorCoordinates(true);
        assertTrue(config.actorCoordinates());
        config.actorCoordinates(false);
        assertFalse(config.actorCoordinates());
    }

    @Test
    void actorCoordinatesChaining()
    {
        assertSame(config, config.actorCoordinates(true));
    }

    @Test
    void toogleShowPositions()
    {
        assertFalse(config.actorCoordinates());
        assertTrue(config.toogleShowPositions());
        assertFalse(config.toogleShowPositions());
    }

    @Test
    void defaultRenderAABBs()
    {
        assertFalse(config.renderAABBs());
    }

    @Test
    void renderAABBs()
    {
        config.renderAABBs(true);
        assertTrue(config.renderAABBs());
        config.renderAABBs(false);
        assertFalse(config.renderAABBs());
    }

    @Test
    void renderAABBsChaining()
    {
        assertSame(config, config.renderAABBs(true));
    }
}
