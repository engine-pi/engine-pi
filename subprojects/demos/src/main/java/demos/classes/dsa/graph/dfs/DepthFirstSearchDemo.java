/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2025, 2026 Josef Friedrich and contributors.
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
package demos.classes.dsa.graph.dfs;

import pi.Controller;
import pi.Scene;
import pi.dsa.graph.GraphCollection;
import pi.dsa.graph.GraphVisualizer;

public class DepthFirstSearchDemo extends Scene
{
    GraphVisualizer visualizer;

    DepthFirstSearch dfs;

    public DepthFirstSearchDemo()
    {
        visualizer = new GraphVisualizer(this);
        dfs = new DepthFirstSearch(visualizer);
        dfs.importGraph(GraphCollection.Cornelsen6Beispielgraph2());
        visualizer.setGraph(dfs);
    }

    public void visit(String label)
    {
        dfs.visitNode(dfs.nodeIndex(label));
    }

    public static void main(String[] args)
    {
        DepthFirstSearchDemo scene = new DepthFirstSearchDemo();
        Controller.start(scene);
        scene.focusCenter();
        scene.visit("H");
    }

}
