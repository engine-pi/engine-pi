package de.pirckheimer_gymnasium.engine_pi.dsa.turtle.graphics;

import de.pirckheimer_gymnasium.engine_pi.dsa.turtle.TurtleGraphics;

/**
 * Zeichnet die Hilbert-Kurve.
 *
 * <p>
 * Der ursprünglich deutsche Name dieser Klasse war {@code Hilbert}.
 * </p>
 *
 * @author Albert Wiedemann
 *
 * @version 1.0
 */
public class HilbertCurveTurtle extends TurtleGraphics
{
    /**
     * Die Linienlänge.
     */
    private double length;

    /**
     * Die Rekursionstiefe.
     */
    private int depth;

    /**
     * Legt die Schildkröte an und startet die Zeichnung.
     */
    public HilbertCurveTurtle()
    {
        initalState.position(-7, 7).speed(100);
        depth = 5;
    }

    public void draw()
    {
        length = 15 / Math.pow(2.0, depth);
        drawElement(false, depth);
    }

    /**
     * Dreht um 90° nach links oder rechts.
     *
     * @param left Wenn wahr, dann Linksdrehung.
     */
    private void rotate(boolean left)
    {
        if (left)
        {
            turtle.rotate(90);
        }
        else
        {
            turtle.rotate(-90);
        }
    }

    /**
     * Zeichnet ein Element der Kurve.
     *
     * @param left Wenn wahr, dann Linksdrehung.
     * @param depth Die restliche Rekursionstiefe.
     */
    private void drawElement(boolean left, int depth)
    {
        if (depth > 0)
        {
            rotate(left);
            drawElement(!left, depth - 1);
            turtle.move(length);
            rotate(!left);
            drawElement(left, depth - 1);
            turtle.move(length);
            drawElement(left, depth - 1);
            rotate(!left);
            turtle.move(length);
            drawElement(!left, depth - 1);
            rotate(left);
        }
    }

    public static void main(String[] args)
    {
        new HilbertCurveTurtle().start();
    }
}
