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

/**
 * Implementation der Tiefensuche.
 *
 * @author Josef Friedrich
 *
 * @since 0.36.0
 */
public class DepthFirstSearch extends GraphArrayMatrix
{
    private GraphVisualizer visualizer;

    private boolean[] visited;

    public DepthFirstSearch(GraphVisualizer visualizer)
    {
        super();
        this.visualizer = visualizer;
        visited = new boolean[100];
    }

    /**
     * Setzt den Rekursionsschritt bei einem Knoten um.
     *
     * @param nodeIndex Der Index bzw. die Nummer des aktuell zu besuchenden
     *     Knotens.
     */
    public void visitNode(int nodeIndex)
    {
        visualizer.setNodeColor(nodeIndex, "orange", 500);
        visited[nodeIndex] = true;
        for (int i = 0; i < getNodesCount(); i++)
        {
            // es gibt eine Kante und deren Zielknoten ist noch nicht besucht
            if (matrix[nodeIndex][i] > 0 && !visited[i])
            {
                visitNode(i);
            }
        }
        visualizer.setNodeColor(nodeIndex, "green", 500);
    }
}
