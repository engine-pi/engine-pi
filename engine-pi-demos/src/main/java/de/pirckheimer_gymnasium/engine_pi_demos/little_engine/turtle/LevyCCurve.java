package de.pirckheimer_gymnasium.engine_pi_demos.little_engine.turtle;

import de.pirckheimer_gymnasium.engine_pi.little_engine.Turtle;

/**
 * Zeichnet die Lévy-C-Kurve.
 *
 * @author Albert Wiedemann
 *
 * @version 1.0
 */
public class LevyCCurve
{
    /**
     * Die Schildkröte.
     */
    private Turtle t;

    /**
     * Legt die Schildkröte an.
     */
    public LevyCCurve()
    {
        t = new Turtle();
        t.liftPen();
        t.setPosition(350, 250);
        t.lowerPen();
    }

    /**
     * Zeichnet die Levy-C-Kurve mit der gegebenen Tiefe in der angegebenen
     * Farbe.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code KurveZeichnen}.
     * </p>
     *
     * @param depth Die Rekursionstiefe.
     * @param color Die Linienfarbe.
     */
    void drawCurve(int depth, String color)
    {
        t.reset();
        t.setColor(color);
        t.setPosition(350, 250);
        drawPart(150, depth);
    }

    /**
     * Zeichnet die Levy-C-Kurve mit den Tiefen 0 bis 3.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war
     * {@code KurveZeichnen0bis3}.
     * </p>
     */
    void drawCurve0to3()
    {
        t.reset();
        t.setColor("schwarz");
        t.setPosition(350, 250);
        drawPart(150, 0);
        t.setColor("rot");
        t.setPosition(350, 250);
        drawPart(150, 1);
        t.setColor("grün");
        t.setPosition(350, 250);
        drawPart(150, 2);
        t.setColor("blau");
        t.setPosition(350, 250);
        drawPart(150, 3);
    }

    /**
     * Zeichnet ein Element der Kurve durch Ausführen des nächsten
     * Rekursionsschrittes.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war
     * {@code SchrittAusführen}.
     * </p>
     *
     * @param length Die Linienlänge.
     * @param depth Die (restliche) Rekursionstiefe.
     */
    private void drawPart(double length, int depth)
    {
        if (depth > 0)
        {
            t.rotate(45);
            //  1 / Math.sqrt(2) == 0.7071067811865475
            // sqrt -> square root -> Quadratwurzel
            drawPart(length * 0.7071, depth - 1);
            t.rotate(-90);
            drawPart(length * 0.7071, depth - 1);
            t.rotate(45);
        }
        else
        {
            t.move(length);
        }
    }

    public static void main(String[] args)
    {
        LevyCCurve curve = new LevyCCurve();
        // curve.drawCurve(10, "schwarz");
        curve.drawCurve0to3();
    }
}
