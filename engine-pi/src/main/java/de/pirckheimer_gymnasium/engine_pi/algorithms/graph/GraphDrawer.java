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
package de.pirckheimer_gymnasium.engine_pi.algorithms.graph;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Layer;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.Circle;

/**
 * <b>Zeichnet</b> einen <b>Graphen</b> in eine Szene.
 *
 * @author Josef Friedrich
 *
 * @since 0.36.0
 */
public class GraphDrawer
{
    private Circle[] nodes;

    public GraphDrawer(Scene scene, Graph graph)
    {
        nodes = new Circle[graph.getNodesCount()];
        for (int i = 0; i < graph.getNodesCount(); i++)
        {
            Node node = graph.getNode(i);
            Circle circle = new Circle();
            circle.setPosition(node.getX(), node.getY());
            scene.add(circle);
            nodes[i] = circle;
        }
    }

    public static void main(String[] args)
    {
        Game.debug();
        Game.start(scene -> {
            Layer layer = scene.getMainLayer();
            new GraphDrawer(scene, Graph.getCornelsenBeispielgraph2());
        });
    }
}
