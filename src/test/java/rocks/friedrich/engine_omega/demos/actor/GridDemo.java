package rocks.friedrich.engine_omega.demos.actor;

import static rocks.friedrich.engine_omega.resources.Container.colors;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.actor.Grid;

public class GridDemo extends ActorBaseScene
{
    public GridDemo()
    {
        Grid grid1 = new Grid(3, 3, 1);
        grid1.makeStatic();
        add(grid1);
        Grid grid2 = new Grid(5, 7, 1);
        grid2.setBackground(colors.getOrange());
        grid2.setColor(colors.getRed());
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
