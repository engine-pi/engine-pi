package rocks.friedrich.engine_omega.demos.actor;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.actor.Triangle;

public class TriangleDemo extends ActorBaseScene
{
    public TriangleDemo()
    {
        // Kippt beim Aufprall
        Triangle triangle = new Triangle(5, 2);
        triangle.makeDynamic();
        triangle.rotateBy(45);
        add(triangle);
        // Der Anker ist links unten.
        Triangle triangle2 = new Triangle(5, 5);
        triangle2.makeStatic();
        triangle2.setPosition(5, 0);
        add(triangle2);
    }

    public static void main(String[] args)
    {
        Game.setDebug(true);
        Game.start(new TriangleDemo());
    }
}
