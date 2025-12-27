package demos.classes.dsa.turtle;

import pi.Turtle;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/engine/src/main/java/de/pirckheimer_gymnasium/engine_pi/dsa/turtle/TurtleAnimationController.java

/**
 * Demonstiert, wie mit <b>minimalen</b> Programmieraufwand eine
 * <b>Turtle</b>-Grafik gezeichnet werden kann.
 */
public class TurtleAnimationControllerDemo
{
    Turtle turtle = new Turtle();

    public TurtleAnimationControllerDemo()
    {
        turtle = new Turtle();
        turtle.setPosition(-4, -6);
        drawPolygone(6, 5);
        turtle.animation.warp();
        turtle.pen.color("green").thickness(1);
        drawPolygone(7, 4);
    }

    private void drawPolygone(int sides, double length)
    {
        for (int i = 0; i < sides; i++)
        {
            turtle.animation.speed(i + 3);
            turtle.forward(length);
            turtle.left(360 / sides);
        }
    }

    public static void main(String[] args)
    {
        new TurtleAnimationControllerDemo();
    }
}
