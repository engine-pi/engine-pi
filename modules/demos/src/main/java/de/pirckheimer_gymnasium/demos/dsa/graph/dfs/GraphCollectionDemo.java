package de.pirckheimer_gymnasium.demos.dsa.graph.dfs;

import java.awt.event.KeyEvent;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.dsa.graph.Graph;
import de.pirckheimer_gymnasium.engine_pi.dsa.graph.GraphArrayMatrix;
import de.pirckheimer_gymnasium.engine_pi.dsa.graph.GraphCollection;
import de.pirckheimer_gymnasium.engine_pi.dsa.graph.GraphVisualizer;
import de.pirckheimer_gymnasium.engine_pi.event.KeyStrokeListener;

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
        Game.start(scene);
        scene.importNextGraph();
        // scene.importGraph("OldenburgKapitel10Aufgabe7Nr3");
    }

}
