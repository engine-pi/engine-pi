package rocks.friedrich.engine_omega.demos.actor;

import java.awt.Color;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.engine_omega.actor.Grid;
import rocks.friedrich.engine_omega.actor.Rectangle;

public class GridDemo extends Scene
{
    public GridDemo()
    {
        setGravity(0, -9.81);
        Grid grid1 = new Grid(3, 3, 1);
        grid1.makeStatic();
        add(grid1);
        Grid grid2 = new Grid(5, 7, 1);
        grid2.setBackground(Color.ORANGE);
        grid2.setColor(Color.RED);
        grid2.setPosition(4, 0);
        grid2.makeDynamic();

        add(grid2);

        Rectangle rectangle = new Rectangle(20, 1);

        rectangle.setPosition(-10, -8);
        rectangle.makeStatic();
        add(rectangle);
    }

    public static void main(String[] args)
    {
        Game.start(new GridDemo());
    }
}
