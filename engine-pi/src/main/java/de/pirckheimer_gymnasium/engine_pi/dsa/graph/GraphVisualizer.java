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

import de.pirckheimer_gymnasium.engine_pi.Game;
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

    public GraphVisualizer(Scene scene, Graph graph)
    {
        this.graph = graph;
        labeledNodes = new LabeledNode[graph.getNodesCount()];
        labeledEdges = new LabeledEdge[graph.getEdgesCount()];
        for (int i = 0; i < graph.getEdgesCount(); i++)
        {
            GraphEdge edge = graph.getEdge(i);
            LabeledEdge labledEdge = new LabeledEdge(
                    edge.getFrom().getPosition(), edge.getTo().getPosition());
            scene.add(labledEdge);
            labeledEdges[i] = labledEdge;
        }
        for (int i = 0; i < graph.getNodesCount(); i++)
        {
            GraphNode node = graph.getNode(i);
            LabeledNode labeledNode = new LabeledNode(node.getLabel());
            labeledNode.setCenter(node.getPosition());
            scene.add(labeledNode);
            labeledNodes[i] = labeledNode;
        }
    }

    public void setNodeColor(int index, String color)
    {
        labeledNodes[index].setColor(color);
    }

    public void setNodeColor(String label, String color)
    {
        setNodeColor(graph.getNodeIndex(label), color);
    }

    public static void main(String[] args)
    {
        Game.start(scene -> {
            GraphVisualizer visualizer = new GraphVisualizer(scene,
                    GraphCollection.Cornelsen6Beispielgraph2());
            scene.getCamera().setCenter(10, 6);
            visualizer.setNodeColor("B", "green");

        });
    }
}
