package de.pirckheimer_gymnasium.engine_pi;

import de.pirckheimer_gymnasium.engine_pi.dsa.turtle.TurtleController;

// Go to file: file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/demos/src/main/java/de/pirckheimer_gymnasium/demos/classes/dsa/turtle/TurtleDemo.java

public class Turtle extends TurtleController
{
    public Turtle()
    {
        super();
        Game.startSafe(scene);
    }

    public static void main(String[] args)
    {
        Turtle turtle = new Turtle();
        for (int i = 0; i < 3; i++)
        {
            turtle.lowerPen();
            turtle.move(3);
            turtle.rotate(120);
        }
    }
}
