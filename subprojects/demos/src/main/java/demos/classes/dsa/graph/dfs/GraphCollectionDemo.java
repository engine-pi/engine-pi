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
