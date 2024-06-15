package de.pirckheimer_gymnasium.engine_pi.demos.actor;

import static de.pirckheimer_gymnasium.engine_pi.Resources.colorScheme;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.actor.Grid;

public class GridDemo extends ActorBaseScene
{
    public GridDemo()
    {
        Grid grid1 = new Grid(3, 3, 1);
        grid1.makeStatic();
        add(grid1);
        Grid grid2 = new Grid(5, 7, 1);
        grid2.setBackground(colorScheme.getOrange());
        grid2.setColor(colorScheme.getRed());
        grid2.rotateBy(-45);
        grid2.setPosition(4, 0);
        grid2.makeDynamic();
        add(grid2);
    }

    public static void main(String[] args)
    {
        Game.start(new GridDemo());
    }
}
