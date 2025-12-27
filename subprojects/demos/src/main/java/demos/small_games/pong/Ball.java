package demos.small_games.pong;

import pi.actor.BodyType;
import pi.actor.Circle;

public class Ball extends Circle
{
    public Ball()
    {
        super();
        setColor("white");
        setBodyType(BodyType.DYNAMIC);
        setElasticity(1);
    }
}
