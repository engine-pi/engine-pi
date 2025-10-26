package de.pirckheimer_gymnasium.engine_pi.dsa.graph;

/**
 * Implementation der Tiefensuche.
 */
public class DepthFirstSearch extends GraphArrayMatrix
{
    private boolean[] visited;

    public DepthFirstSearch(int maxNodes)
    {
        super(maxNodes);
        visited = new boolean[maxNodes];
    }

    /**
     * Setzt den Rekursionsschritt bei einem Knoten um.
     *
     * @param nodeIndex Die Index bzw. die Nummer des aktuell zu besuchenden
     *     Knotens.
     */
    public void visitNode(int nodeIndex)
    {
        for (int i = 0; i < getNodesCount(); i++)
        {
            // es gibt eine Kante und deren Zielknoten ist noch nicht besucht
            if (matrix[nodeIndex][i] > 0 && !visited[i])
            {
                visitNode(i);
            }
        }
    }

}
