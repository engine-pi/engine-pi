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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Josef Friedrich
 */
class GameConfigTest
{
    GameConfig config;

    @BeforeEach
    void setUp()
    {
        config = new GameConfig();
    }

    @Test
    void defaultValue()
    {
        assertTrue(config.instantMode());
    }

    @Test
    void setValue()
    {
        config.instantMode(false);
        assertFalse(config.instantMode());

        config.instantMode(true);
        assertTrue(config.instantMode());
    }

    @Test
    void chaining()
    {
        assertSame(config, config.instantMode(false));
    }
}
