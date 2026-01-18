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

import pi.graphics.geom.Vector;

/**
 * @author Josef Friedrich
 */
public class GraphNodeTest
{

    GraphNode node = new GraphNode("test", 1.2, 3.4);

    @Test
    void testGetLabel()
    {
        assertEquals(node.label(), "test");
    }

    @Test
    void testGetX()
    {
        assertEquals(node.x(), 1.2);
    }

    @Test
    void testGetY()
    {
        assertEquals(node.y(), 3.4);
    }

    @Test
    void testGetPosition()
    {
        Vector pos = node.position();
        assertEquals(pos.x(), 1.2);
        assertEquals(pos.y(), 3.4);
    }

    @Test
    void testGetFormattedLabel()
    {
        assertEquals(node.formattedLabel(8), "test    ");

    }

    @Test
    void testGenerateJavaCode()
    {
        assertEquals(node.generateJavaCode(),
            "g.addNode(\"test\", 1.20, 3.40);");
    }
}
