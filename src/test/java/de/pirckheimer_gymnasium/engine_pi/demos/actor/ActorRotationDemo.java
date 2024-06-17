package de.pirckheimer_gymnasium.engine_pi.demos.actor;

import static de.pirckheimer_gymnasium.engine_pi.Vector.v;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.Polygon;

public class ActorRotationDemo extends Scene
{
    public ActorRotationDemo()
    {
        createPolygon().setColor("yellow");
        createPolygon().setColor("green").setRotation(90);
        createPolygon().setColor("red").setRotation(180);
        createPolygon().setColor("blue").setRotation(270);
        getCamera().setMeter(60);
    }

    private Polygon createPolygon()
    {
        Polygon polygon = new Polygon(v(0, 0), v(1, 0.3), v(3, 0), v(1, -0.3));
        add(polygon);
        return polygon;
    }

    public static void main(String[] args)
    {
        Game.start(new ActorRotationDemo());
    }
}
