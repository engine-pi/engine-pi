package de.pirckheimer_gymnasium.demos.dsa.graph.dfs;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.Image;
import de.pirckheimer_gymnasium.engine_pi.dsa.graph.GraphDrawer;

public class GraphDrawerDemo extends Scene
{

    public GraphDrawerDemo()
    {
        Image graphImage = new Image(
                "/home/jf/Nextcloud/graph-schoolbooks/Oldenbourgh/k11_a2_1.png",
                // "/home/jf/Nextcloud/graph-schoolbooks/Cornelsen/11-spb/Seite-58.png",
                10);
        add(graphImage);

        new GraphDrawer(this);

        getCamera().setFocus(graphImage);
    }

    public static void main(String[] args)
    {
        Game.start(new GraphDrawerDemo(), 1200, 800);
    }

}
