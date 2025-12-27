package demos.small_games.pong;

import pi.actor.BodyType;
import pi.actor.Rectangle;

/**
 * Heißt im Deutschen oft Schläger
 *
 * @repolink https://github.com/MeghnaSaha/PongGame-JAVA/blob/master/Paddle.java
 */
public class Paddle extends Rectangle
{

    public Paddle()
    {
        super(1, 5);
        setColor("white");
        setBodyType(BodyType.STATIC);
        setElasticity(1);
    }

}
