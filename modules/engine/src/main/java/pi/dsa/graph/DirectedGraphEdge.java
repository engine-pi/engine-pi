package pi.dsa.graph;

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

}
