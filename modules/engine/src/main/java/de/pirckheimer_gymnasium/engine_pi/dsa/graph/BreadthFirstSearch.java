package de.pirckheimer_gymnasium.engine_pi.dsa.graph;

import java.util.Vector;

/**
 * @see <a href=
 *     "https://github.com/bschlangaul-sammlung/java-fuer-examens-aufgaben/blob/main/src/main/java/org/bschlangaul/graph/algorithmen/BreitenSucheWarteschlange.java">Bschlangaul-Sammlung:
 *     BreitenSucheWarteschlange.java</a>
 *
 * @since 0.37.0
 */
public class BreadthFirstSearch extends GraphArrayMatrix
{
    private GraphVisualizer visualizer;

    /**
     * Eine Warteschlange
     */
    Vector<GraphNode> queue;

    private boolean[] visited;

    public BreadthFirstSearch(GraphVisualizer visualizer)
    {
        super();
        this.visualizer = visualizer;
        visited = new boolean[getNodeCount()];
    }

    private void markVisited(int nodeIndex)
    {
        visualizer.setNodeColor(nodeIndex, "orange", 500);
        visited[nodeIndex] = true;
        GraphNode node = getNode(nodeIndex);
        queue.add(node);
    }

    protected void visitNode(int nodeIndex)
    {
        markVisited(nodeIndex);
        while (!queue.isEmpty())
        {
            GraphNode node = queue.remove(0);
            for (int i = 0; i <= getNodeCount() - 1; i++)
            {
                if (matrix[getNodeIndex(
                        node)][i] != GraphEdge.NOT_REACHABLE_WEIGHT
                        && !visited[i])
                {
                    markVisited(nodeIndex);
                }
            }
        }
    }
}
