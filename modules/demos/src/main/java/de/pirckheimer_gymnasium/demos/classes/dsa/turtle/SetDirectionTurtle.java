package de.pirckheimer_gymnasium.demos.classes.dsa.turtle;

import de.pirckheimer_gymnasium.engine_pi.dsa.turtle.Turtle;
import de.pirckheimer_gymnasium.engine_pi.dsa.turtle.TurtleAlgorithm;

public class SetDirectionTurtle extends TurtleAlgorithm
{

    public SetDirectionTurtle(Turtle turtle)
    {
        super(turtle);
        turtle.setSpeed(2);
    }

    public SetDirectionTurtle()
    {
        this(new Turtle());
    }

    private void setDirection(double direction)
    {
        turtle.lowerPen();
        turtle.setStartPosition(0, 0);
        turtle.setDirection(direction);
        turtle.move(4);
        turtle.liftPen();

    }

    @Override
    public void draw()
    {
        setDirection(0);
        setDirection(90);
        setDirection(180);
        setDirection(270);
    }

    public static void main(String[] args)
    {
        new SetDirectionTurtle().start();
    }
}
