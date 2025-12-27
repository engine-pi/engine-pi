package demos.classes.dsa.turtle;

import pi.Turtle;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/engine/src/main/java/de/pirckheimer_gymnasium/engine_pi/dsa/turtle/TurtleBackgroundController.java

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
        turtle.forward();
        turtle.left(120);
        turtle.forward();
        turtle.left(120);
        turtle.forward();
        turtle.background.clear();
    }
}
