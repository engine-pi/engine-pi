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
package de.pirckheimer_gymnasium.engine_pi.dsa.graph;

import static de.pirckheimer_gymnasium.engine_pi.util.TimeUtil.sleep;

import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.LabeledEdge;
import de.pirckheimer_gymnasium.engine_pi.actor.LabeledNode;

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

    private Scene scene;

    public GraphVisualizer(Scene scene)
    {
        this.scene = scene;
    }

    public GraphVisualizer(Scene scene, Graph graph)
    {
        this.scene = scene;
        setGraph(graph);
    }

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
     * Die Methode führt folgende Schritte aus:
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
        labeledNodes = new LabeledNode[graph.getNodesCount()];
        labeledEdges = new LabeledEdge[graph.getEdgesCount()];
        // Zuerst werden die Kanten eingezeichnet.
        for (int i = 0; i < graph.getEdgesCount(); i++)
        {
            GraphEdge edge = graph.getEdge(i);
            LabeledEdge labledEdge = new LabeledEdge(
                    edge.getFrom().getPosition(), edge.getTo().getPosition());
            if (edge.getWeight() != 1)
            {
                labledEdge.setLabel(String.valueOf(edge.getWeight()));
            }
            scene.add(labledEdge);
            labeledEdges[i] = labledEdge;
        }
        // Dann die Knoten.
        for (int i = 0; i < graph.getNodesCount(); i++)
        {
            GraphNode node = graph.getNode(i);
            LabeledNode labeledNode = new LabeledNode(node.getLabel());
            labeledNode.setCenter(node.getPosition());
            scene.add(labeledNode);
            labeledNodes[i] = labeledNode;
        }
    }

    public void setNodeColor(int index, String color, int sleepMilliSeconds)
    {
        labeledNodes[index].setColor(color);
        sleep(sleepMilliSeconds);
    }

    public void setNodeColor(int index, String color)
    {
        setNodeColor(index, color, 0);
    }

    public void setNodeColor(String label, String color, int sleepMilliSeconds)
    {
        setNodeColor(graph.getNodeIndex(label), color, sleepMilliSeconds);
    }

    public void setNodeColor(String label, String color)
    {
        setNodeColor(label, color, 0);
    }
}
