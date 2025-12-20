package pi.dsa.graph;

import java.util.ArrayList;

/**
 * Alle Kanten zwischen zwei Knoten. Maximal zwei Kanten können hinzugefügt
 * werden. Ist die Kante zwischen zwei Knoten ungerichtet, so gibt es in dieser
 * Klasse zwei gerichtete Kanten, nämlich von Knoten a zu Knoten b und von
 * Knoten b zu Knoten a.
 *
 * Die Klasse ist als Datenstruktur zum Zeichen von Kanten gedacht.
 */
class EdgesOfNodePair
{

    private Graph graph;

    private ArrayList<DirectedGraphEdge> edges;

    EdgesOfNodePair(Graph graph)
    {
        this.graph = graph;
        edges = new ArrayList<>();
    }

    void addDirectedEdge(int from, int to, int weight)
    {
        if (edges.size() >= 2)
        {
            throw new RuntimeException(
                    "Die maximale Anzahl an Kanten ist erreicht. Es können nur maximal zwei hinzugefügt werden.");
        }
        edges.add(new DirectedGraphEdge(graph.getNode(from), graph.getNode(to),
                weight));
    }

    /**
     * Gibt die Kanten dieses Knotenpaars zurück.
     *
     * <p>
     * Sind zwei Kanten vorhanden, wird die Reihenfolge so bestimmt, dass das
     * erste Element die Kante ist, die vom ersten Knoten ausgeht.
     * </p>
     *
     * @return ein Array von {@code DirectedGraphEdge}: Länge 1 bei einer Kante,
     *     sonst Länge 2 mit definierter Reihenfolge
     *
     * @throws RuntimeException wenn das Knotenpaar keine Kanten enthält
     */
    public DirectedGraphEdge[] getEdges()
    {
        if (edges.isEmpty())
        {
            throw new RuntimeException("Das Knotenpaar hat keine Kanten.");
        }
        if (edges.size() == 1)
        {
            return new DirectedGraphEdge[] { edges.get(0) };
        }

        if (getFirstNode().getLabel().equals(edges.get(0).getFrom().getLabel()))
        {
            return new DirectedGraphEdge[] { edges.get(0), edges.get(1) };
        }
        return new DirectedGraphEdge[] { edges.get(1), edges.get(0) };
    }

    public boolean isDirected()
    {
        return edges.size() == 1;
    }

    public int getWeight()
    {
        if (edges.size() == 1)
        {
            return edges.get(0).getWeight();
        }
        if (edges.size() == 0)
        {
            throw new RuntimeException("Das Knotenpaar hat keine Kanten");
        }
        if (edges.get(0).getWeight() != edges.get(1).getWeight())
        {
            throw new RuntimeException(
                    "Die zwei gerichteten Kanten haben unterschiedliche Gewichte und können deshalb nicht über diese Methode ermittelt werden.");
        }
        return edges.get(0).getWeight();
    }

    /**
     * Gibt die zwei Knoten sortiert nach den Bezeichnern zurück. Die Bezeichner
     * werden alphabetisch aufsteigend sortiert.
     *
     * @return Die zwei Knoten sortiert nach den Bezeichnern zurück.
     */
    public GraphNode[] getNodes()
    {
        if (edges.isEmpty())
        {
            throw new RuntimeException("Das Knotenpaar hat keine Kanten.");
        }
        DirectedGraphEdge edge = edges.get(0);
        GraphNode node1 = edge.getFrom();
        GraphNode node2 = edge.getTo();
        if (node1.getLabel().compareTo(node2.getLabel()) <= 0)
        {
            return new GraphNode[] { node1, node2 };
        }
        else
        {
            return new GraphNode[] { node2, node1 };
        }
    }

    /**
     * Gibt den Knoten, dessen Bezeichner alphabetisch vor dem zweiten Knoten
     * steht, zurück.
     *
     * @return Der Knoten, dessen Bezeichner alphabetisch vor dem zweiten Knoten
     *     steht.
     */
    public GraphNode getFirstNode()
    {
        return getNodes()[0];
    }

    /**
     * Gibt den Knoten, dessen Bezeichner alphabetisch nach dem ersten Knoten
     * steht, zurück.
     *
     * @return Der Knoten, dessen Bezeichner alphabetisch nach dem ersten Knoten
     *     steht.
     */
    public GraphNode getSecondNode()
    {
        return getNodes()[1];
    }

    public boolean isEmpty()
    {
        return edges.size() == 0;
    }
}
