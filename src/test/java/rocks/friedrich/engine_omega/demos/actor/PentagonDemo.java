package rocks.friedrich.engine_omega.demos.actor;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.actor.Pentagon;

public class PentagonDemo extends ActorBaseScene
{
    public PentagonDemo()
    {
        Pentagon pentagon = new Pentagon();
        pentagon.makeDynamic();
        add(pentagon);

    }

    public static void main(String[] args)
    {
        Game.setDebug(true);
        Game.start(new PentagonDemo());
    }
}
