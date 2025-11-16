package de.pirckheimer_gymnasium.demos.classes.dsa.turtle;

import de.pirckheimer_gymnasium.engine_pi.Turtle;

// Go to file: file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/engine/src/main/java/de/pirckheimer_gymnasium/engine_pi/dsa/turtle/TurtleBackgroundController.java

/**
 * Demonstiert, wie mit <b>minimalen</b> Programmieraufwand eine
 * <b>Turtle</b>-Grafik gezeichnet werden kann.
 */
public class TurtleBackgroundControllerDemo
{
    public static void main(String[] args)
    {
        Turtle turtle = new Turtle();
        turtle.background.color("blue");
        turtle.move();
        turtle.rotate(120);
        turtle.move();
        turtle.rotate(120);
        turtle.move();
        turtle.background.clear();
    }
}
