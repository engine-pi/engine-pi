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

/**
 * @author Josef Friedrich
 */
class GraphEdgeTest
{
    GraphEdge edge = new GraphEdge(new GraphNode("a"), new GraphNode("b"), 2,
            true);

    @Test
    void from()
    {
        assertEquals("a", edge.from().label());
    }

    @Test
    void to()
    {
        assertEquals("b", edge.to().label());
    }

    @Test
    void weight()
    {
        assertEquals(2, edge.weight());
    }

    @Test
    void isDirected()
    {
        assertEquals(true, edge.isDirected());
    }

    @Test
    void generateJavaCode()
    {
        assertEquals("g.addEdge(\"b\", \"a\", 2, true);",
            edge.generateJavaCode());
    }
}
