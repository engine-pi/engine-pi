package de.pirckheimer_gymnasium.engine_pi_demos.dsa.graph;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.dsa.graph.GraphCollection;
import de.pirckheimer_gymnasium.engine_pi.dsa.graph.GraphVisualizer;

public class DepthFirstSearchDemo
{

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
