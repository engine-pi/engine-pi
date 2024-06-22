package de.pirckheimer_gymnasium.engine_pi_demos.actor;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.actor.Triangle;

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
