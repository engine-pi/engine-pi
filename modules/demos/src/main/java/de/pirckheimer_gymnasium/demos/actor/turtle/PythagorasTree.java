package de.pirckheimer_gymnasium.demos.actor.turtle;

import de.pirckheimer_gymnasium.engine_pi.actor.Turtle;

/**
 * Zeichnet den Pythagorasbaum.
 *
 * <p>
 * Der ursprünglich deutsche Name dieser Klasse war {@code PythagorasBaum}.
 * </p>
 *
 * TODO fix ...
 *
 * @author Albert Wiedemann
 *
 * @version 1.0
 */
public class PythagorasTree
{
    /**
     * Die Schildkröte.
     */
    Turtle t;

    /**
     * Seite a des rechtwinkeligen Ausgangsdreiecks
     */
    double a;

    /**
     * Seite b des rechtwinkeligen Ausgangsdreiecks
     */
    double b;

    /**
     * Seite c^2 des rechtwinkeligen Ausgangsdreiecks
     */
    double c2;

    /**
     * Seitenverhältnis für die Berechnung der C-Punkte der angefügten Dreiecke
     */
    double bc;

    /**
     * Seitenverhältnis für die Berechnung der C-Punkte der angefügten Dreiecke
     */
    double abc;

    /**
     * Besetzt die Konstanten und baut den Baum auf.
     *
     * @param depth Die Rekursionstiefe.
     */
    public PythagorasTree(int depth)
    {
        t = new Turtle();
        t.setSpeed(1000);
        // a = 30;
        // b = 40;
        a = 10;
        b = 10;
        // a = 12;
        // b = 5;
        c2 = a * a + b * b;
        bc = (b * b) / c2;
        abc = a * b / c2;
        drawRectangle(40, 10, 50, 10, 50, 20, 40, 20);
        makeStep(40, 20, 50, 20, depth);
    }

    /**
     * Berechnet die Richtung von Startpunkt zum Zielpunkt.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code RichtungGeben}.
     * </p>
     *
     * @param xSrc Die x-Koordinate des Startpunkts.
     * @param ySrc Die y-Koordinate des Startpunkts.
     * @param xDest Die x-Koordinate des Zielpunkts.
     * @param yDest Die y-Koordinate des Zielpunkts.
     *
     * @return Richtungswinkel zum Zielpunkt
     */
    private double getRotation(double xSrc, double ySrc, double xDest,
            double yDest)
    {
        double dx = xDest - xSrc;
        double dy = yDest - ySrc;
        double rotation = 0;
        if (dx == 0)
        {
            if (dy > 0)
            {
                rotation = 270;
            }
            else if (dy < 0)
            {
                rotation = 90;
            }
        }
        else if (dx > 0)
        {
            rotation = -Math.atan(dy / dx) * 18 / Math.PI;
        }
        else
        {
            rotation = 180 - Math.round(Math.atan(dy / dx) * 18 / Math.PI);
        }
        return rotation;
    }

    /**
     * Berechnet die Weglänge von Startpunkt zum Zielpunkt.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code LängeGeben}.
     * </p>
     *
     * @param xStart Die x-Koordinate des Startpunkts.
     * @param yStart Die y-Koordinate des Startpunkts.
     * @param xZiel Die x-Koordinate des Zielpunkts.
     * @param yZiel Die y-Koordinate des Zielpunkts.
     *
     * @return Die Weglänge zum Zielpunkt.
     */
    private double getLength(double xStart, double yStart, double xZiel,
            double yZiel)
    {
        double dX = xZiel - xStart;
        double dY = yZiel - yStart;
        return Math.sqrt(dX * dX + dY * dY);
    }

    /**
     * Zeichnet das Dreieck mit den angegebenen Eckpunkten
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war
     * {@code DreieckZeichnen}.
     * </p>
     *
     * @param aX Die x-Koordinate der Ecke A.
     * @param aY Die y-Koordinate der Ecke A.
     * @param bX Die x-Koordinate der Ecke B.
     * @param bY Die y-Koordinate der Ecke B.
     * @param cX Die x-Koordinate der Ecke C.
     * @param cY Die y-Koordinate der Ecke C.
     */
    private void drawTriangle(double aX, double aY, double bX, double bY,
            double cX, double cY)
    {
        t.setPosition((int) aX, (int) aY);
        t.setRotation(getRotation(aX, aY, bX, bY));
        t.move(getLength(aX, aY, bX, bY));
        t.setRotation(getRotation(bX, bY, cX, cY));
        t.move(getLength(bX, bY, cX, cY));
        t.setRotation(getRotation(cX, cY, aX, aY));
        t.move(getLength(cX, cY, aX, aY));
    }

    /**
     * Zeichnet das Viereck mit den angegebenen Eckpunkten
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war
     * {@code ViereckZeichnen}.
     * </p>
     *
     * @param aX Die x-Koordinate der Ecke A.
     * @param aY Die y-Koordinate der Ecke A.
     * @param bX Die x-Koordinate der Ecke B.
     * @param bY Die y-Koordinate der Ecke B.
     * @param cX Die x-Koordinate der Ecke C.
     * @param cY Die y-Koordinate der Ecke C.
     * @param dX Die x-Koordinate der Ecke D.
     * @param dY Die y-Koordinate der Ecke D.
     */
    private void drawRectangle(double aX, double aY, double bX, double bY,
            double cX, double cY, double dX, double dY)
    {
        t.setPosition((int) aX, (int) aY);
        t.setRotation(getRotation(aX, aY, bX, bY));
        t.move(getLength(aX, aY, bX, bY));
        t.setRotation(getRotation(bX, bY, cX, cY));
        t.move(getLength(bX, bY, cX, cY));
        t.setRotation(getRotation(cX, cY, dX, dY));
        t.move(getLength(cX, cY, dX, dY));
        t.setRotation(getRotation(dX, dY, aX, aY));
        t.move(getLength(dX, dY, aX, aY));
    }

    /**
     * Führt einen Rekursionsschritt aus
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war
     * {@code SchrittAusführen}.
     * </p>
     *
     * @param aX Die x-Koordinate der Ecke A der Basislinie.
     * @param aY Die y-Koordinate der Ecke A der Basislinie.
     * @param bX Die x-Koordinate der Ecke B der Basislinie.
     * @param bY Die y-Koordinate der Ecke B der Basislinie.
     * @param depth Die Rekursionstiefe.
     */
    private void makeStep(double aX, double aY, double bX, double bY, int depth)
    {
        if (depth > 0)
        {
            double cX = aX + (bX - aX) * bc - (bY - aY) * abc;
            double cY = aY + (bY - aY) * bc + (bX - aX) * abc;
            drawTriangle(aX, aY, bX, bY, cX, cY);
            double p1X = aX - (cY - aY);
            double p1Y = aY + (cX - aX);
            double p2X = cX - (cY - aY);
            double p2Y = cY + (cX - aX);
            drawRectangle(aX, aY, cX, cY, p2X, p2Y, p1X, p1Y);
            makeStep(p1X, p1Y, p2X, p2Y, depth - 1);
            p1X = cX - (bY - cY);
            p1Y = cY + (bX - cX);
            p2X = bX - (bY - cY);
            p2Y = bY + (bX - cX);
            drawRectangle(cX, cY, bX, bY, p2X, p2Y, p1X, p1Y);
            makeStep(p1X, p1Y, p2X, p2Y, depth - 1);
        }
    }

    public static void main(String[] args)
    {
        new PythagorasTree(5);
    }
}
