package de.pirckheimer_gymnasium.engine_pi_demos.little_engine.turtle;

import de.pirckheimer_gymnasium.engine_pi.little_engine.Triangle;

/**
 * Erzeugt das Sierpinski-Dreieck.
 *
 * <p>
 * Der ursprünglich deutsche Name dieser Klasse war {@code SierpinskiDreieck}.
 * </p>
 *
 * @author Albert Wiedemann
 *
 * @version 1.0
 */
public class SierpinskiTriangle
{

    /**
     * Legt das Grunddreieck an und stößt die Rekursion an.
     *
     * @param depth Die Rekursionstiefe.
     */
    public SierpinskiTriangle(int depth)
    {
        Triangle base = new Triangle();
        base.setColor("weiß");
        base.setPosition(400, 10);
        base.setSize(600, 520);
        makeStep(400, 10, 600, 520, depth);
    }

    /**
     * Ermittelt den Rekursionsschritt.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war
     * {@code SchrittAusführen}.
     * </p>
     *
     * @param x Die x-Position der Dreiecksspitze.
     * @param y Die y-Position der Dreiecksspitze.
     * @param width Die Breite des Umgebungsdreiecks.
     * @param height Die Höhe des Umgebungsdreiecks.
     * @param depth Die restliche Rekursionstiefe.
     */
    private void makeStep(int x, int y, int width, int height, int depth)
    {
        if (depth > 0)
        {
            makeStep(x, y, width / 2, height / 2, depth - 1);
            makeStep(x - width / 4, y + height / 2, width / 2, height / 2,
                    depth - 1);
            makeStep(x + width / 4, y + height / 2, width / 2, height / 2,
                    depth - 1);
        }
        else
        {
            Triangle base = new Triangle();
            base.setColor("schwarz");
            base.setPosition(x, y);
            base.setSize(width, height);
        }
    }

    public static void main(String[] args)
    {
        new SierpinskiTriangle(5);
    }
}
