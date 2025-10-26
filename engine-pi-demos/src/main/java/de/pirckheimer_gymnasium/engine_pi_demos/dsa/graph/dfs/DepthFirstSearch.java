package de.pirckheimer_gymnasium.engine_pi_demos.dsa.graph.dfs;

import de.pirckheimer_gymnasium.engine_pi.dsa.graph.GraphArrayMatrix;
import de.pirckheimer_gymnasium.engine_pi.dsa.graph.GraphVisualizer;

class DepthFirstSearch extends GraphArrayMatrix
{
    private GraphVisualizer visualizer;

    private boolean[] visited;

    public DepthFirstSearch(GraphVisualizer visualizer)
    {
        super();
        this.visualizer = visualizer;
        visited = new boolean[100];
    }

    /**
     * Setzt den Rekursionsschritt bei einem Knoten um.
     *
     * @param nodeIndex Der Index bzw. die Nummer des aktuell zu besuchenden
     *     Knotens.
     */
    public void visitNode(int nodeIndex)
    {
        visualizer.setNodeColor(nodeIndex, "orange", 500);
        visited[nodeIndex] = true;
        for (int i = 0; i < getNodesCount(); i++)
        {
            // es gibt eine Kante und deren Zielknoten ist noch nicht
            // besucht
            if (matrix[nodeIndex][i] > 0 && !visited[i])
            {
                visitNode(i);
            }
        }
        visualizer.setNodeColor(nodeIndex, "green", 500);
    }
}
