package de.pirckheimer_gymnasium.demos.classes.dsa.turtle;

import static de.pirckheimer_gymnasium.engine_pi.Vector.v;

import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.dsa.turtle.TurtleAlgorithm;

/**
 * Zeichnet den <a href="https://de.wikipedia.org/wiki/Pythagoras-Baum"></a>.
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
public class PythagorasTreeTurtle extends TurtleAlgorithm
{

    /**
     * Die Rekursionstiefe.
     */
    int depth;

    /**
     * Die Seite a des rechtwinkeligen Ausgangsdreiecks.
     */
    double a;

    /**
     * Die Seite b des rechtwinkeligen Ausgangsdreiecks.
     */
    double b;

    /**
     * Die Seite c^2 des rechtwinkeligen Ausgangsdreiecks.
     */
    double c2;

    /**
     * Das Seitenverhältnis für die Berechnung der C-Punkte der angefügten
     * Dreiecke.
     */
    double bc;

    /**
     * Das Seitenverhältnis für die Berechnung der C-Punkte der angefügten
     * Dreiecke.
     */
    double abc;

    /**
     * Besetzt die Konstanten und baut den Baum auf.
     *
     * @param depth Die Rekursionstiefe.
     */
    public PythagorasTreeTurtle(Scene scene, int depth)
    {
        super(scene);
        this.depth = depth;
        turtle.setSpeed(1000);
    }

    public PythagorasTreeTurtle(int depth)
    {
        this(new Scene(), depth);
    }

    public PythagorasTreeTurtle()
    {
        this(1);
    }

    public void draw()
    {
        a = 2;
        b = 2;
        c2 = a * a + b * b;
        bc = (b * b) / c2;
        abc = a * b / c2;
        drawRectangle(8, 2, 10, 2, 10, 4, 8, 4);
        makeStep(8, 4, 10, 4, depth);
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
        return v(xSrc, ySrc).subtract(v(xDest, yDest)).getAngle();
    }

    /**
     * Berechnet die Weglänge von Startpunkt zum Zielpunkt.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code LängeGeben}.
     * </p>
     *
     * @param xSrc Die x-Koordinate des Startpunkts.
     * @param ySrc Die y-Koordinate des Startpunkts.
     * @param xDest Die x-Koordinate des Zielpunkts.
     * @param yDest Die y-Koordinate des Zielpunkts.
     *
     * @return Die Weglänge zum Zielpunkt.
     */
    private double getLength(double xSrc, double ySrc, double xDest,
            double yDest)
    {
        return v(xSrc, ySrc).subtract(v(xDest, yDest)).getLength();
    }

    /**
     * Zeichnet das Dreieck mit den angegebenen Eckpunkten.
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
        turtle.setStartPosition(aX, aY);
        turtle.setDirection(getRotation(aX, aY, bX, bY));
        turtle.move(getLength(aX, aY, bX, bY));
        turtle.setDirection(getRotation(bX, bY, cX, cY));
        turtle.move(getLength(bX, bY, cX, cY));
        turtle.setDirection(getRotation(cX, cY, aX, aY));
        turtle.move(getLength(cX, cY, aX, aY));
    }

    /**
     * Zeichnet das Viereck mit den angegebenen Eckpunkten.
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
        turtle.setStartPosition(aX, aY);
        turtle.setDirection(getRotation(aX, aY, bX, bY));
        turtle.move(getLength(aX, aY, bX, bY));
        turtle.setDirection(getRotation(bX, bY, cX, cY));
        turtle.move(getLength(bX, bY, cX, cY));
        turtle.setDirection(getRotation(cX, cY, dX, dY));
        turtle.move(getLength(cX, cY, dX, dY));
        turtle.setDirection(getRotation(dX, dY, aX, aY));
        turtle.move(getLength(dX, dY, aX, aY));
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
        new PythagorasTreeTurtle().show();
    }
}
