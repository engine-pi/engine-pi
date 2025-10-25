package de.pirckheimer_gymnasium.engine_pi.algorithms.graph;

/**
 *
 *
 * @author Josef Friedrich
 *
 * @since 0.36.0
 */
public class Edge
{
    private Node from;

    private Node to;

    private double weight;

    private boolean directed;

    public Edge(Node from, Node to, double weight, boolean directed)
    {
        this.from = from;
        this.to = to;
        this.weight = weight;
        this.directed = directed;
    }

    public Node getFrom()
    {
        return from;
    }

    public Node getTo()
    {
        return to;
    }

    public double getWeight()
    {
        return weight;
    }

    public boolean isDirected()
    {
        return directed;
    }

}
