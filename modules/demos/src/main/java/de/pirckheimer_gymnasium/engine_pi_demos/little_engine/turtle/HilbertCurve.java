package de.pirckheimer_gymnasium.engine_pi_demos.little_engine.turtle;

import de.pirckheimer_gymnasium.engine_pi.little_engine.Turtle;

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
public class HilbertCurve
{
    /**
     * Die Schildkröte.
     */
    private Turtle t;

    /**
     * Die Linienlänge.
     */
    private double length;

    /**
     * Legt die Schildkröte an und startet die Zeichnung.
     *
     * @param depth Die Rekursionstiefe.
     */
    public HilbertCurve(int depth)
    {
        t = new Turtle();
        length = 400.0 / Math.pow(2.0, depth);
        t.liftPen();
        t.setPosition((int) (length / 2.0), (int) (length / 2.0));
        t.lowerPen();
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
            t.rotate(90);
        }
        else
        {
            t.rotate(-90);
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
            t.move(length);
            rotate(!left);
            drawElement(left, depth - 1);
            t.move(length);
            drawElement(left, depth - 1);
            rotate(!left);
            t.move(length);
            drawElement(!left, depth - 1);
            rotate(left);
        }
    }

    public static void main(String[] args)
    {
        new HilbertCurve(4);
    }
}
