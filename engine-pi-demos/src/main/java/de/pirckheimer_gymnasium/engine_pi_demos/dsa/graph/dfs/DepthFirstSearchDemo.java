package de.pirckheimer_gymnasium.engine_pi_demos.dsa.graph.dfs;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.dsa.graph.GraphCollection;
import de.pirckheimer_gymnasium.engine_pi.dsa.graph.GraphVisualizer;

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
        getCamera().setCenter(10, 6);
    }

    public void visit(String label)
    {
        dfs.visitNode(dfs.getNodeIndex(label));
    }

    public static void main(String[] args)
    {
        DepthFirstSearchDemo scene = new DepthFirstSearchDemo();
        Game.start(scene);
        scene.visit("H");
    }

}
