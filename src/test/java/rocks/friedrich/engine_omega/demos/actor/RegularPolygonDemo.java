package rocks.friedrich.engine_omega.demos.actor;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.actor.RegularPolygon;

public class RegularPolygonDemo extends ActorBaseScene
{
    public RegularPolygonDemo()
    {
        RegularPolygon polygon = new RegularPolygon(5);
        polygon.makeDynamic();
        add(polygon);

    }

    public static void main(String[] args)
    {
        Game.setDebug(true);
        Game.start(new RegularPolygonDemo());
    }
}
