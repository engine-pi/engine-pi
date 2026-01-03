package demos.classes.dsa.graph.dfs;

import pi.Game;
import pi.Scene;
import pi.actor.Image;
import pi.dsa.graph.GraphDrawer;

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

        camera().focus(graphImage);
    }

    public static void main(String[] args)
    {
        Game.start(new GraphDrawerDemo(), 1200, 800);
    }

}
