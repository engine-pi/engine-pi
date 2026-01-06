package pi.dsa.graph;

import pi.annotations.Getter;

class DirectedGraphEdge
{
    /**
     * Der <b>Startknoten</b>.
     */
    private final GraphNode from;

    /**
     * Der <b>Endknoten</b>.
     */
    private final GraphNode to;

    /**
     * Die <b>Gewichtung</b> der Kante.
     */
    private final int weight;

    public DirectedGraphEdge(GraphNode from, GraphNode to, int weight)
    {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    @Getter
    public GraphNode from()
    {
        return from;
    }

    @Getter
    public GraphNode to()
    {
        return to;
    }

    @Getter
    public int weight()
    {
        return weight;
    }

}
