package de.pirckheimer_gymnasium.demos.classes.dsa.turtle;

import pi.Turtle;

// Go to file: file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/engine/src/main/java/de/pirckheimer_gymnasium/engine_pi/dsa/turtle/TurtleWindowController.java

public class TurtleWindowControllerDemo
{
    public static void main(String[] args)
    {
        Turtle turtle = new Turtle();
        turtle.animation.speed(1);

        turtle.window.size(400, 400);
        turtle.forward();
        turtle.left(120);
        turtle.window.size(200, 200);
        turtle.forward();
        turtle.left(120);

        turtle.window.size(800, 800);

        turtle.forward();
    }
}
