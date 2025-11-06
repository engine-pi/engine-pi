package de.pirckheimer_gymnasium.demos.dsa.turtle;

import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.dsa.turtle.TurtleAlgorithm;

public class SquareTurtle extends TurtleAlgorithm
{

    public SquareTurtle(Scene scene)
    {
        super(scene);
    }

    public SquareTurtle()
    {
        super();
    }

    @Override
    public void draw()
    {
        for (int i = 0; i < 4; i++)
        {
            turtle.move(4);
            turtle.rotate(90);
        }
    }

    public static void main(String[] args)
    {
        new SquareTurtle().show();
    }
}
