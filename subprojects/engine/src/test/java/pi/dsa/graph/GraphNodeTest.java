package pi.dsa.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import pi.Vector;

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
