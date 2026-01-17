package pi.dsa.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class GraphEdgeTest
{
    GraphEdge edge = new GraphEdge(new GraphNode("a"), new GraphNode("b"), 2,
            true);

    @Test
    void testGetFrom()
    {
        assertEquals(edge.from().label(), "a");
    }

    @Test
    void testGetTo()
    {
        assertEquals(edge.to().label(), "b");
    }

    @Test
    void testGetWeight()
    {
        assertEquals(edge.weight(), 2);
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
