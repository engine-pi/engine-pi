package de.pirckheimer_gymnasium.engine_pi.algorithms.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GraphMatrixTest
{
    private GraphMatrix g;

    @BeforeEach
    public void setUp()
    {
        g = new GraphMatrix(5);
    }

    @Test
    public void testAddNode()
    {
        g.addNode("A", 0, 0);
        g.addNode("B", 1, 1);
        assertEquals(2, g.getNodesCount());
    }

    @Test
    public void testAddEdge()
    {
        g.addNode("A", 0, 0);
        g.addNode("B", 1, 1);
        g.addEdge("A", "B", 10, false);
        assertEquals(10, g.getEdgeWeight("A", "B"));
        assertEquals(10, g.getEdgeWeight("B", "A"));
    }

    @Test
    public void testAddDirectedEdge()
    {
        g.addNode("A", 0, 0);
        g.addNode("B", 1, 1);
        g.addEdge("A", "B", 5, true);
        assertEquals(5, g.getEdgeWeight("A", "B"));
        assertEquals(-1, g.getEdgeWeight("B", "A"));
    }

    @Test
    public void testGetEdgeWeightInvalidNodes()
    {
        g.addNode("A", 0, 0);
        g.addNode("B", 1, 1);
        assertEquals(-1, g.getEdgeWeight("A", "C"));
        assertEquals(-1, g.getEdgeWeight("C", "B"));
    }

    @Test
    public void testAddNodeExceedingLimit()
    {
        g.addNode("A", 0, 0);
        g.addNode("B", 1, 1);
        g.addNode("C", 2, 2);
        g.addNode("D", 3, 3);
        g.addNode("E", 4, 4);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> g.addNode("F", 5, 5));
        assertEquals("Die maximale Anzahl an Knoten wurde überschritten: 6",
                exception.getMessage());
    }

    @Test
    public void testAddEdgeInvalidNodes()
    {
        g.addNode("A", 0, 0);
        g.addNode("B", 1, 1);
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> g.addEdge("A", "C", 10, false));
        assertEquals("Unbekannter Knoten mit dem Bezeichner: C",
                exception.getMessage());
        // Node "C" does not exist
        assertEquals(-1, g.getEdgeWeight("A", "C"));
    }
}
