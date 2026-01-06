/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2025 Josef Friedrich and contributors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package pi.dsa.graph;

import static pi.util.TimeUtil.sleep;

import pi.Scene;
import pi.actor.LabeledEdge;
import pi.actor.LabeledNode;
import pi.annotations.Setter;

/**
 * <b>Zeichnet</b> einen <b>Graphen</b> in eine Szene.
 *
 * @author Josef Friedrich
 *
 * @since 0.36.0
 */
public class GraphVisualizer
{
    private LabeledNode[] labeledNodes;

    private LabeledEdge[] labeledEdges;

    private Graph graph;

    private final Scene scene;

    public GraphVisualizer(Scene scene)
    {
        this.scene = scene;
    }

    public GraphVisualizer(Scene scene, Graph graph)
    {
        this.scene = scene;
        setGraph(graph);
    }

    /**
     * Entfernt alle grafischen Knoten und Kanten aus der Szene.
     */
    public void clear()
    {
        if (labeledNodes != null)
        {
            for (LabeledNode node : labeledNodes)
            {
                scene.remove(node);
            }
        }

        if (labeledEdges != null)
        {
            for (LabeledEdge edge : labeledEdges)
            {
                scene.remove(edge);
            }
        }
    }

    /**
     * Setzt den Graphen neu und aktualisiert die Darstellung.
     *
     * <p>
     * Die Methode führt folgende Schritte aus:
     * </p>
     *
     * <ul>
     * <li>Speichert den übergebenen Graphen in der Instanzvariable.</li>
     * <li>Räumt die aktuelle Szene auf.</li>
     * <li>Erstellt und fügt beschriftete Kanten ({@link LabeledEdge}) für jede
     * Kante des Graphen hinzu.</li>
     * <li>Erstellt und fügt beschriftete Knoten ({@link LabeledNode}) für jeden
     * Knoten des Graphen hinzu.</li>
     * </ul>
     *
     * Hinweis: Die Positionen der Knoten und Kanten werden aus dem übergebenen
     * Graphen übernommen.
     *
     * @param graph Der Graph, der visualisiert werden soll. Dieser Graph
     *     enthält die Knoten und Kanten, die in der Szene dargestellt werden.
     */
    public void setGraph(Graph graph)
    {
        this.graph = graph;
        clear();
        labeledNodes = new LabeledNode[graph.nodeCount()];
        labeledEdges = new LabeledEdge[graph.edgeCount()];
        // Zuerst werden die Kanten eingezeichnet.
        for (int i = 0; i < graph.edgeCount(); i++)
        {
            GraphEdge edge = graph.edge(i);
            LabeledEdge labeledEdge = new LabeledEdge(edge.from().position(),
                    edge.to().position());
            if (edge.weight() != 1)
            {
                labeledEdge.label(String.valueOf(edge.weight()));
            }
            scene.add(labeledEdge);
            labeledEdges[i] = labeledEdge;
        }
        // Dann die Knoten.
        for (int i = 0; i < graph.nodeCount(); i++)
        {
            GraphNode node = graph.node(i);
            LabeledNode labeledNode = new LabeledNode(node.label());
            labeledNode.center(node.position());
            scene.add(labeledNode);
            labeledNodes[i] = labeledNode;
        }
    }

    @Setter
    public void nodeColor(int index, String color, int sleepMilliSeconds)
    {
        labeledNodes[index].color(color);
        sleep(sleepMilliSeconds);
    }

    @Setter
    public void nodeColor(int index, String color)
    {
        nodeColor(index, color, 0);
    }

    @Setter
    public void nodeColor(String label, String color, int sleepMilliSeconds)
    {
        nodeColor(graph.nodeIndex(label), color, sleepMilliSeconds);
    }

    @Setter
    public void nodeColor(String label, String color)
    {
        nodeColor(label, color, 0);
    }
}
