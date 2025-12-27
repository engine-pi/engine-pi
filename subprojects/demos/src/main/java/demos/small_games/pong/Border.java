package demos.small_games.pong;

import pi.actor.BodyType;
import pi.actor.Rectangle;

public class Border extends Rectangle
{
    public Border()
    {
        super(20, 1);
        setColor("gray");
        setBodyType(BodyType.STATIC);
        setElasticity(1);

    }
}
