package rocks.friedrich.engine_omega.demos.actor;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.Scene;

public class RegularPolygonDemo extends Scene
{
    public RegularPolygonDemo()
    {
        int x = -12;
        for (int i = 3; i < 8; i++)
        {
            createRegularPolygon(i, x, 0);
            createText(i + "", x - 0.25, -3);
            x += 6;
        }
    }

    public static void main(String[] args)
    {
        Game.setDebug(true);
        Game.start(new RegularPolygonDemo());
    }
}
