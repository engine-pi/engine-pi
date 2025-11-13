package de.pirckheimer_gymnasium.demos.classes.dsa.turtle;

import de.pirckheimer_gymnasium.engine_pi.dsa.turtle.Turtle;
import de.pirckheimer_gymnasium.engine_pi.dsa.turtle.TurtleAlgorithm;

public class SquareTurtle extends TurtleAlgorithm
{

    public SquareTurtle(Turtle turtle)
    {
        super(turtle);
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
        SquareTurtle turtle = new SquareTurtle();
        turtle.clearBeforeRun();
        turtle.repeat(3);
    }
}
