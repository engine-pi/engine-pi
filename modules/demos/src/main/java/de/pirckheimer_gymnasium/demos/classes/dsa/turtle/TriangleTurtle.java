package de.pirckheimer_gymnasium.demos.classes.dsa.turtle;

import de.pirckheimer_gymnasium.engine_pi.dsa.turtle.Turtle;
import de.pirckheimer_gymnasium.engine_pi.dsa.turtle.TurtleAlgorithm;

public class TriangleTurtle extends TurtleAlgorithm
{

    public TriangleTurtle(Turtle turtle)
    {
        super(turtle);
    }

    public TriangleTurtle()
    {
        super();
    }

    @Override
    public void draw()
    {
        for (int i = 0; i < 3; i++)
        {
            turtle.move(4);
            turtle.rotate(120);
        }
    }

    public static void main(String[] args)
    {
        new TriangleTurtle().show();
    }
}
