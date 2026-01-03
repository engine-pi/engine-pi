package pi.dsa.graph;

import pi.annotations.Getter;

class DirectedGraphEdge
{
    /**
     * Der <b>Startknoten</b>.
     */
    private GraphNode from;

    /**
     * Der <b>Endknoten</b>.
     */
    private GraphNode to;

    /**
     * Die <b>Gewichtung</b> der Kante.
     */
    private int weight;

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
