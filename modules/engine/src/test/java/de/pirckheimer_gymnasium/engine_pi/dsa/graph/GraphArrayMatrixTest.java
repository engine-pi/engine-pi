package de.pirckheimer_gymnasium.engine_pi.dsa.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GraphArrayMatrixTest
{
    private GraphArrayMatrix g;

    @BeforeEach
    public void setUp()
    {
        g = new GraphArrayMatrix(5);
    }

    @Test
    public void testAddNode()
    {
        g.addNode("A", 0, 0);
        g.addNode("B", 1, 1);
        assertEquals(2, g.getNodeCount());
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
        assertThrows(RuntimeException.class, () -> g.getEdgeWeight("A", "C"));
        assertThrows(RuntimeException.class, () -> g.getEdgeWeight("C", "B"));
    }

    @Test
    public void testAddNodeExceedingLimit()
    {
        g.addNode("A", 0, 0);
        g.addNode("B", 1, 1);
        g.addNode("C", 2, 2);
        g.addNode("D", 3, 3);
        g.addNode("E", 4, 4);
        // Matrix wird vergrößert
        g.addNode("F", 5, 5);
        assertEquals(g.getNodeCount(), 6);
    }

    @Test
    public void testAddEdgeInvalidNodes()
    {
        g.addNode("A", 0, 0);
        g.addNode("B", 1, 1);
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> g.addEdge("A", "C", 10, false));
        assertEquals(
                "Es konnte kein Knoten mit dem Bezeichner „C“ gefunden werden.",
                exception.getMessage());
        assertThrows(RuntimeException.class, () -> g.getEdgeWeight("A", "C"));
    }

    @Test
    public void testAllEdgesOfNodePairs()
    {
        g.addNode("B");
        g.addNode("A");
        g.addNode("C");
        g.addNode("D");
        g.addEdge("A", "B", 10, true);
        g.addEdge("A", "C");
        ArrayList<EdgesOfNodePair> all = g.getAllEdgesOfNodePairs();
        assertEquals(2, all.size());

        // ab
        EdgesOfNodePair AB = all.get(0);
        GraphNode[] nodes = AB.getNodes();
        // Muss sortiert sein, obwohl anders in die Matrix eingefügt.
        assertEquals("A", nodes[0].getLabel());
        assertEquals("B", nodes[1].getLabel());
        assertEquals(10, AB.getWeight());
        assertTrue(AB.isDirected());

        // ac
        EdgesOfNodePair AC = all.get(1);
        nodes = AC.getNodes();
        // Muss sortiert sein, obwohl anders in die Matrix eingefügt.
        assertEquals("A", nodes[0].getLabel());
        assertEquals("C", nodes[1].getLabel());
        assertEquals(1, AC.getWeight());
        assertFalse(AC.isDirected());
    }

    @Test
    public void testGenerateJavaCodes()
    {
        g.addNode("A", 0, 0);
        g.addNode("B", 1, 1);
        g.addEdge("A", "B", 10, true);
        assertEquals(g.generateJavaCode(), "// Anlegen der Knoten\n" + //
                "g.addNode(\"A\", 0.00, 0.00);\n" + //
                "g.addNode(\"B\", 1.00, 1.00);\n" + //
                "// Anlegen der Kanten\n" + //
                "g.addEdge(\"B\", \"A\", 10, true);");
    }
}
