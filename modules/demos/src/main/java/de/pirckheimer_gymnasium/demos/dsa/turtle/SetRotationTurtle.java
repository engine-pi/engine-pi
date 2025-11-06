package de.pirckheimer_gymnasium.demos.dsa.turtle;

import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.dsa.turtle.TurtleAlgorithm;

public class SetRotationTurtle extends TurtleAlgorithm
{

    public SetRotationTurtle(Scene scene)
    {
        super(scene);
        turtle.setSpeed(2);
    }

    public SetRotationTurtle()
    {
        this(new Scene());
    }

    private void setRotation(double rotation)
    {
        turtle.lowerPen();
        turtle.setStartPosition(0, 0);
        turtle.setDirection(rotation);
        turtle.move(4);
        turtle.liftPen();

    }

    @Override
    public void run()
    {
        setRotation(0);
        setRotation(90);
        setRotation(180);
        setRotation(270);
    }

    public static void main(String[] args)
    {
        new SetRotationTurtle().start();
    }
}
