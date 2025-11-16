package de.pirckheimer_gymnasium.engine_pi.dsa.turtle.graphics;

import de.pirckheimer_gymnasium.engine_pi.dsa.turtle.TurtleGraphics;

public class SquareTurtle extends TurtleGraphics
{

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
        new SquareTurtle().start();
    }
}
