package de.pirckheimer_gymnasium.engine_pi.dsa.graph;

/**
 * Stellt eine <b>Kante</b> in einem Graphen dar.
 *
 * <b>Wird ein Graph über eine Adjazenz-Matrix oder -Liste implementiert, ist
 * diese Klasse eigentlich nicht nötig. Beim Einfügen von Kanten in den Graphen
 * wird momentan zusätzlich zu den oben beschriebenen Datenstrukturen auch eine
 * Objekt dieser Klasse erzeugt. Dadurch entstehen Datendoppelungen. Vor allem
 * das Zeichnen von Kanten wird durch die Klasse {@link GraphEdge}
 * vereinfacht.</b>
 *
 * @author Josef Friedrich
 *
 * @since 0.36.0
 */
public class GraphEdge
{
    private GraphNode from;

    private GraphNode to;

    private int weight;

    private boolean directed;

    public GraphEdge(GraphNode from, GraphNode to, int weight, boolean directed)
    {
        this.from = from;
        this.to = to;
        this.weight = weight;
        this.directed = directed;
    }

    public GraphNode getFrom()
    {
        return from;
    }

    public GraphNode getTo()
    {
        return to;
    }

    public int getWeight()
    {
        return weight;
    }

    public boolean isDirected()
    {
        return directed;
    }
}
