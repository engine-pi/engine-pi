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

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import pi.graphics.geom.Direction;

/**
 * @author Josef Friedrich
 */
public class ConfigurationTest
{
    Configuration config = Configuration.getInstance();

    @Test
    public void all()
    {
        // Go to
        // file:///home/jf/repos/school/monorepo/inf/java/engine-pi/docs/manual/resources/config.md
        assertEquals(true, config.game.instantMode());

        assertEquals(768, config.graphics.windowWidth());
        assertEquals(576, config.graphics.windowHeight());
        assertEquals(Direction.NONE, config.graphics.windowPosition());
        assertEquals(32.0, config.graphics.pixelPerMeter());
        assertEquals(0.05, config.graphics.zoomChange());
        assertEquals(60, config.graphics.framerate());
        assertEquals("Gnome", config.graphics.colorScheme());
        assertEquals(1, config.graphics.pixelMultiplication());
        assertEquals(2, config.graphics.screenRecordingNFrames());

        assertEquals(0.5, config.sound.soundVolume());
        assertEquals(0.5, config.sound.musicVolume());

        assertEquals(false, config.debug.enabled());
        assertEquals(false, config.debug.verbose());
        assertEquals(true, config.debug.renderActors());
        assertEquals(false, config.debug.actorCoordinates());

        assertEquals(-1, config.coordinatesystem.linesNMeter());
        assertEquals(false, config.coordinatesystem.labelsOnIntersections());
    }
}
