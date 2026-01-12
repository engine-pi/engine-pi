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

import java.awt.event.KeyEvent;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import pi.Controller;
import pi.Scene;
import pi.dsa.graph.Graph;
import pi.dsa.graph.GraphArrayMatrix;
import pi.dsa.graph.GraphCollection;
import pi.dsa.graph.GraphVisualizer;
import pi.event.KeyStrokeListener;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/engine/src/main/java/pi/dsa/graph/GraphCollection.java

public class GraphCollectionDemo extends Scene implements KeyStrokeListener
{
    private GraphVisualizer visualizer;

    private Graph graph;

    private String[] methodNames;

    private int currentNameIndex;

    public GraphCollectionDemo()
    {
        visualizer = new GraphVisualizer(this);
        graph = new GraphArrayMatrix();
        methodNames = GraphCollection.getMethodNames();
        visualizer.setGraph(graph);
    }

    public void importGraph(String methodName)
    {
        try
        {
            Method method = GraphCollection.class.getMethod(methodName);
            if (Modifier.isStatic(method.getModifiers())
                    && Modifier.isPublic(method.getModifiers()))
            {
                graph = new GraphArrayMatrix();
                graph.importGraph((Graph) method.invoke(null));
                visualizer.setGraph(graph);
                focusCenter();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void importNextGraph()
    {
        importGraph(methodNames[currentNameIndex]);
        if (currentNameIndex == methodNames.length - 1)
        {
            currentNameIndex = 0;
        }
        else
        {
            currentNameIndex++;
        }
    }

    @Override
    public void onKeyDown(KeyEvent event)
    {
        importNextGraph();
    }

    public static void main(String[] args)
    {
        GraphCollectionDemo scene = new GraphCollectionDemo();
        Controller.start(scene);
        scene.importNextGraph();
        // scene.importGraph("OldenburgKapitel10Aufgabe7Nr3");
    }

}
