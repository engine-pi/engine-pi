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
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Josef Friedrich
 */
class CleanPropertiesTest
{
    private CleanProperties properties;

    @BeforeEach
    void setUp()
    {
        properties = new CleanProperties();
    }

    @Test
    void keysAreSortedAscending()
    {
        properties.setProperty("c", "3");
        properties.setProperty("a", "1");
        properties.setProperty("b", "2");

        List<Object> keys = Collections.list(properties.keys());

        assertEquals(List.of("a", "b", "c"), keys);
    }

    @Test
    void storeRemovesFirstHeaderLine() throws IOException
    {
        properties.setProperty("a", "1");
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        properties.store(out, "Kommentar");

        String content = out.toString(StandardCharsets.ISO_8859_1);
        assertFalse(content.startsWith("#"));
        assertTrue(content.contains("a=1"));
    }

    @Test
    void storeWritesEntriesInSortedOrder() throws IOException
    {
        properties.setProperty("c", "3");
        properties.setProperty("a", "1");
        properties.setProperty("b", "2");

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        properties.store(out, null);

        String content = out.toString(StandardCharsets.ISO_8859_1);
        List<String> lines = new ArrayList<>();
        for (String line : content.split("\\R"))
        {
            if (!line.isBlank())
            {
                lines.add(line);
            }
        }

        assertEquals(List.of("a=1", "b=2", "c=3"), lines);
    }
}
