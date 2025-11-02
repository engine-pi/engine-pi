package de.pirckheimer_gymnasium.engine_pi.dsa.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class GraphEdgeTest
{
    GraphEdge edge = new GraphEdge(new GraphNode("a"), new GraphNode("b"), 2,
            true);

    @Test
    void testGetFrom()
    {
        assertEquals(edge.getFrom().getLabel(), "a");
    }

    @Test
    void testGetTo()
    {
        assertEquals(edge.getTo().getLabel(), "b");
    }

    @Test
    void testGetWeight()
    {
        assertEquals(edge.getWeight(), 2);
    }

    @Test
    void testIsDirected()
    {
        assertEquals(edge.isDirected(), true);
    }

    @Test
    void testGenerateJavaCode()
    {
        assertEquals(edge.generateJavaCode(),
                "g.addEdge(\"b\", \"a\", 2, true);");
    }
}
