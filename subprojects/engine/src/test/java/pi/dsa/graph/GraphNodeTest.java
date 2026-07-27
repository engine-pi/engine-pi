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
package pi.dsa.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import pi.EnLocale;
import pi.graphics.geom.Vector;

/**
 * @author Josef Friedrich
 */
class GraphNodeTest
{

    GraphNode node = new GraphNode("test", 1.2, 3.4);

    @Test
    void label()
    {
        assertEquals("test", node.label());
    }

    @Test
    void x()
    {
        assertEquals(1.2, node.x());
    }

    @Test
    void y()
    {
        assertEquals(3.4, node.y());
    }

    @Test
    void position()
    {
        Vector pos = node.position();
        assertEquals(1.2, pos.x());
        assertEquals(3.4, pos.y());
    }

    @Test
    void formattedLabel()
    {
        assertEquals("test    ", node.formattedLabel(8));

    }

    @ExtendWith(EnLocale.class)
    @Test
    void generateJavaCode()
    {
        assertEquals("g.addNode(\"test\", 1.20, 3.40);",
            node.generateJavaCode());
    }
}
