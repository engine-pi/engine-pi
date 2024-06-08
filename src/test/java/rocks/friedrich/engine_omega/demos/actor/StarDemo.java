package rocks.friedrich.engine_omega.demos.actor;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.actor.Star;

public class StarDemo extends ActorBaseScene
{
    public StarDemo()
    {
        // Kippt beim Aufprall
        Star triangle = new Star();
        triangle.makeStatic();
        triangle.rotateBy(45);
        add(triangle);
    }

    public static void main(String[] args)
    {
        Game.setDebug(true);
        Game.start(new StarDemo());
    }
}
