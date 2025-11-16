package de.pirckheimer_gymnasium.demos.classes.dsa.turtle;

import de.pirckheimer_gymnasium.engine_pi.Turtle;

// Go to file: file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/engine/src/main/java/de/pirckheimer_gymnasium/engine_pi/dsa/turtle/TurtlePen.java

/**
 * Demonstiert, wie mit <b>minimalen</b> Programmieraufwand eine
 * <b>Turtle</b>-Grafik gezeichnet werden kann.
 */
public class TurtlePenDemo
{
    public static void main(String[] args)
    {
        Turtle turtle = new Turtle();
        turtle.pen.color("red").thickness(1);
        turtle.move(5);

        turtle.pen.color("green").thickness(3);
        turtle.rotate(120);
        turtle.move(5);

        turtle.pen.color("blue").thickness(5);
        turtle.rotate(120);
        turtle.move(5);

        turtle.pen.lift();

        turtle.move(7);
        turtle.pen.lower().color("brown");
        turtle.move(2);
    }
}
