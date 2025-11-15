package de.pirckheimer_gymnasium.demos.classes.dsa.turtle;

import de.pirckheimer_gymnasium.engine_pi.dsa.turtle.TurtleScene;

/**
 * Demonstiert, wie mit <b>minimalen</b> Programmieraufwand eine
 * <b>Turtle</b>-Grafik gezeichnet werden kann.
 */
public class MinimalTurtleDemo
{
    public static void main(String[] args)
    {
        TurtleScene turtle = new TurtleScene();
        turtle.move();
        turtle.rotate(120);
        turtle.move();
        turtle.rotate(120);
        turtle.move();
    }
}
