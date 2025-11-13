package de.pirckheimer_gymnasium.demos.classes.dsa.turtle;

import de.pirckheimer_gymnasium.engine_pi.dsa.turtle.Turtle;
import de.pirckheimer_gymnasium.engine_pi.dsa.turtle.TurtleAlgorithm;

public class SetDirectionTurtle extends TurtleAlgorithm
{

    public SetDirectionTurtle()
    {
        this(new Turtle());
    }

    public SetDirectionTurtle(Turtle turtle)
    {
        super(turtle);
        initalState.speed(2);
    }

    private void setDirection(double direction)
    {
        turtle.lowerPen();
        turtle.setPosition(0, 0);
        turtle.setDirection(direction);
        turtle.move(4);
        turtle.liftPen();

    }

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
