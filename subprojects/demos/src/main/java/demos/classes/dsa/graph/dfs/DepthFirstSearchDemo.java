package demos.classes.dsa.graph.dfs;

import pi.Game;
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
        dfs.visitNode(dfs.getNodeIndex(label));
    }

    public static void main(String[] args)
    {
        DepthFirstSearchDemo scene = new DepthFirstSearchDemo();
        Game.start(scene);
        scene.focusCenter();
        scene.visit("H");
    }

}
