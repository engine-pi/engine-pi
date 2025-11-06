package de.pirckheimer_gymnasium.demos.dsa.turtle;

import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.dsa.turtle.TurtleAlgorithm;

public class SquareTurtle extends TurtleAlgorithm
{
    public SquareTurtle(Scene scene)
    {
        super(scene);
    }

    @Override
    public void run()
    {
        for (int i = 0; i < 9; i++)
        {
            turtle.move(4);
            turtle.rotate(120);
        }
    }

}
