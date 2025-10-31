package de.pirckheimer_gymnasium.engine_pi_demos.dsa.graph.dfs;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.Image;
import de.pirckheimer_gymnasium.engine_pi.dsa.graph.GraphDrawer;

public class GraphDrawerDemo extends Scene
{

    public GraphDrawerDemo()
    {
        Image graphImage = new Image(
                // "/data/school/Archiv/schulbuecher/Informatik/Oldenbourg/Informatik-Oberstufe-1_Oldenbourg_2009_Lehrer/kapitel_iii/pics/k10_a2_1.png",
                "/home/jf/Nextcloud/graph-schoolbooks/Cornelsen/11-spb/Seite-58.png",
                40);
        add(graphImage);

        new GraphDrawer(this);

        getCamera().setFocus(graphImage);
    }

    public static void main(String[] args)
    {
        Game.start(new GraphDrawerDemo(), 1200, 800);
    }

}
