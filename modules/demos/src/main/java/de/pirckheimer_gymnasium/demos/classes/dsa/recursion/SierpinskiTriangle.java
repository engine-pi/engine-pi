package de.pirckheimer_gymnasium.demos.classes.dsa.recursion;

import pi.instant.Triangle;

/**
 * Erzeugt das Sierpinski-Dreieck.
 *
 * <p>
 * Der ursprünglich deutsche Name dieser Klasse war {@code SierpinskiDreieck}.
 * </p>
 *
 * @author Albert Wiedemann
 * @author Josef Friedrich
 *
 * @version 1.0
 *
 * @since 0.33.0
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
        double width = 100;
        double height = 75;
        Triangle triangle = (Triangle) new Triangle(width, height)
                .setColor("weiß");
        triangle.focus();
        triangle.getCamera().setMeter(7);
        triangle.setMainSceneBackgroundColor("yellow");
        makeStep(0, 0, width, height, depth);
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
    private void makeStep(double x, double y, double width, double height,
            int depth)
    {
        if (depth > 0)
        {
            height = height / 2;
            width = width / 2;
            depth = depth - 1;
            // Dreieck links unten
            makeStep(x, y, width, height, depth);
            // Dreieck rechts unten
            makeStep(x + width, y, width, height, depth);
            // Dreieck oben
            makeStep(x + width / 2, y + height, width, height, depth);
        }
        else
        {
            new Triangle(width, height).setColor("black").setPosition(x, y);
        }
    }

    public static void main(String[] args)
    {
        new SierpinskiTriangle(5);
    }
}
