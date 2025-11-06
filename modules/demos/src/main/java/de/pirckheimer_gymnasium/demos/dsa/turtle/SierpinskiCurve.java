package de.pirckheimer_gymnasium.demos.dsa.turtle;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.dsa.turtle.Turtle;
import de.pirckheimer_gymnasium.engine_pi.dsa.turtle.TurtleAlgorithm;

/**
 * Zeichnet die Sierpinski-Kurve.
 *
 * <p>
 * Der ursprünglich deutsche Name dieser Klasse war {@code SierpinskiKurve}.
 * </p>
 *
 * @author Albert Wiedemann
 *
 * @version 1.0
 */
public class SierpinskiCurve extends TurtleAlgorithm
{
    /**
     * Die Schildkröte.
     */
    private Turtle turtle;

    /**
     * Die Linienlänge.
     */
    private double length;

    /**
     * Die Länge der kurzen Linien.
     */
    private double length2;

    private int depth;

    /**
     * Legt die Schildkröte an und startet die Zeichnung.
     *
     * @param depth Die Rekursionstiefe.
     */
    public SierpinskiCurve(Scene scene, int depth)
    {
        super(scene);
        this.depth = depth;
    }

    public void run()
    {
        length = 4;
        double x0 = 0;
        double y0 = 8 - length;
        for (int i = 1; i <= depth; i += 1)
        {
            x0 -= length;
            length = length / 2.0;
            y0 -= length;
        }
        length2 = length * 1.414213562373095;
        length = length * 2.0;
        turtle.liftPen();
        turtle.setPosition(x0, y0);
        turtle.lowerPen();
        drawElementA(depth);
        turtle.setRotation(315);
        turtle.move(length2);
        drawElementB(depth);
        turtle.setRotation(225);
        turtle.move(length2);
        drawElementC(depth);
        turtle.setRotation(135);
        turtle.move(length2);
        drawElementD(depth);
        turtle.setRotation(45);
        turtle.move(length2);
    }

    /**
     * Zeichnet das Element A der Sierpinski-Kurve.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war
     * {@code ElementAZeichnen}.
     * </p>
     *
     * @param depth Die Rekursionstiefe.
     */
    private void drawElementA(int depth)
    {
        if (depth > 0)
        {
            drawElementA(depth - 1);
            turtle.setRotation(315);
            turtle.move(length2);
            drawElementB(depth - 1);
            turtle.setRotation(0);
            turtle.move(length);
            drawElementD(depth - 1);
            turtle.setRotation(45);
            turtle.move(length2);
            drawElementA(depth - 1);
        }
    }

    /**
     * Zeichnet das Element B der Sierpinski-Kurve.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war
     * {@code ElementBZeichnen}.
     * </p>
     *
     * @param depth Die Rekursionstiefe.
     */
    private void drawElementB(int depth)
    {
        if (depth > 0)
        {
            drawElementB(depth - 1);
            turtle.setRotation(225);
            turtle.move(length2);
            drawElementC(depth - 1);
            turtle.setRotation(270);
            turtle.move(length);
            drawElementA(depth - 1);
            turtle.setRotation(315);
            turtle.move(length2);
            drawElementB(depth - 1);
        }
    }

    /**
     * Zeichnet das Element C der Sierpinski-Kurve.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war
     * {@code ElementCZeichnen}.
     * </p>
     *
     * @param depth Die Rekursionstiefe.
     */
    private void drawElementC(int depth)
    {
        if (depth > 0)
        {
            drawElementC(depth - 1);
            turtle.setRotation(135);
            turtle.move(length2);
            drawElementD(depth - 1);
            turtle.setRotation(180);
            turtle.move(length);
            drawElementB(depth - 1);
            turtle.setRotation(225);
            turtle.move(length2);
            drawElementC(depth - 1);
        }
    }

    /**
     * Zeichnet das Element D der Sierpinski-Kurve.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war
     * {@code ElementDZeichnen}.
     * </p>
     *
     * @param depth Die Rekursionstiefe.
     */
    private void drawElementD(int depth)
    {
        if (depth > 0)
        {
            drawElementD(depth - 1);
            turtle.setRotation(45);
            turtle.move(length2);
            drawElementA(depth - 1);
            turtle.setRotation(90);
            turtle.move(length);
            drawElementC(depth - 1);
            turtle.setRotation(135);
            turtle.move(length2);
            drawElementD(depth - 1);
        }
    }

    public static void main(String[] args)
    {
        Scene scene = Game.start();
        new SierpinskiCurve(scene, 5).run();

    }
}
