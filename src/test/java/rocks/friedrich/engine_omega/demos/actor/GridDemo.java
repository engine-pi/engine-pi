package rocks.friedrich.engine_omega.demos.actor;

import java.awt.Color;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.engine_omega.actor.Grid;

public class GridDemo extends Scene
{
    public GridDemo()
    {
        add(new Grid(3, 3, 1));
        Grid grid = new Grid(3, 3, 1);
        grid.setBackground(Color.ORANGE);
        grid.setColor(Color.RED);
        grid.setPosition(4, 0);
        add(grid);
    }

    public static void main(String[] args)
    {
        Game.start(new GridDemo());
    }
}
