package de.pirckheimer_gymnasium.demos.dsa.turtle;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.dsa.turtle.TurtleAlgorithm;

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
public class HilbertCurve extends TurtleAlgorithm
{

    /**
     * Die Linienlänge.
     */
    private double length;

    private int depth;

    /**
     * Legt die Schildkröte an und startet die Zeichnung.
     *
     * @param depth Die Rekursionstiefe.
     */
    public HilbertCurve(Scene scene, int depth)
    {
        super(scene);
        this.depth = depth;
    }

    public void run()
    {
        turtle.setSpeed(100);
        turtle.setPosition(-7, 7);
        length = 15 / Math.pow(2.0, depth);
        turtle.liftPen();
        turtle.lowerPen();
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
        Scene scene = Game.start();
        new HilbertCurve(scene, 5).run();
    }
}
