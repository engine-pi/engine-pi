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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Josef Friedrich
 */
class CoordinatesystemConfigTest
{
    CoordinatesystemConfig config;

    @BeforeEach
    void setUp()
    {
        config = new CoordinatesystemConfig();
    }

    @Test
    void defaultValues()
    {
        assertEquals(-1, config.linesNMeter());
        assertFalse(config.labelsOnIntersections());
    }

    @Test
    void linesNMeter()
    {
        config.linesNMeter(5);
        assertEquals(5, config.linesNMeter());

        config.linesNMeter(10);
        assertEquals(10, config.linesNMeter());
    }

    @Test
    void linesNMeterChaining()
    {
        assertSame(config, config.linesNMeter(3));
    }

    @Test
    void labelsOnIntersections()
    {
        config.labelsOnIntersections(true);
        assertTrue(config.labelsOnIntersections());

        config.labelsOnIntersections(false);
        assertFalse(config.labelsOnIntersections());
    }

    @Test
    void labelsOnIntersectionsChaining()
    {
        assertSame(config, config.labelsOnIntersections(true));
    }
}
