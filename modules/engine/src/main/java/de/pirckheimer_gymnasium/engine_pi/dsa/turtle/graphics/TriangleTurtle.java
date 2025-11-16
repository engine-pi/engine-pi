package de.pirckheimer_gymnasium.engine_pi.dsa.turtle.graphics;

import de.pirckheimer_gymnasium.engine_pi.dsa.turtle.TurtleGraphics;

public class TriangleTurtle extends TurtleGraphics
{

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
        new TriangleTurtle().start();
    }
}
