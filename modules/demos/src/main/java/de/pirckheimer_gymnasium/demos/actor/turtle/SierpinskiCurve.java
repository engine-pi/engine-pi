package de.pirckheimer_gymnasium.demos.actor.turtle;

import de.pirckheimer_gymnasium.engine_pi.actor.Turtle;

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
public class SierpinskiCurve
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
     * Die Länge der kurzen Linien.
     */
    private double length2;

    /**
     * Legt die Schildkröte an und startet die Zeichnung.
     *
     * @param depth Die Rekursionstiefe.
     */
    public SierpinskiCurve(int depth)
    {
        t = new Turtle();
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
        t.liftPen();
        t.setPosition(x0, y0);
        t.lowerPen();
        drawElementA(depth);
        t.setRotation(315);
        t.move(length2);
        drawElementB(depth);
        t.setRotation(225);
        t.move(length2);
        drawElementC(depth);
        t.setRotation(135);
        t.move(length2);
        drawElementD(depth);
        t.setRotation(45);
        t.move(length2);
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
            t.setRotation(315);
            t.move(length2);
            drawElementB(depth - 1);
            t.setRotation(0);
            t.move(length);
            drawElementD(depth - 1);
            t.setRotation(45);
            t.move(length2);
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
            t.setRotation(225);
            t.move(length2);
            drawElementC(depth - 1);
            t.setRotation(270);
            t.move(length);
            drawElementA(depth - 1);
            t.setRotation(315);
            t.move(length2);
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
            t.setRotation(135);
            t.move(length2);
            drawElementD(depth - 1);
            t.setRotation(180);
            t.move(length);
            drawElementB(depth - 1);
            t.setRotation(225);
            t.move(length2);
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
            t.setRotation(45);
            t.move(length2);
            drawElementA(depth - 1);
            t.setRotation(90);
            t.move(length);
            drawElementC(depth - 1);
            t.setRotation(135);
            t.move(length2);
            drawElementD(depth - 1);
        }
    }

    public static void main(String[] args)
    {
        new SierpinskiCurve(5);
    }
}
