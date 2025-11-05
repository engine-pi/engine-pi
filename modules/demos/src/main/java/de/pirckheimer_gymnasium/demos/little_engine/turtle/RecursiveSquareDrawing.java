package de.pirckheimer_gymnasium.demos.little_engine.turtle;

import de.pirckheimer_gymnasium.engine_pi.little_engine.Turtle;

/**
 * Rahmenklasse zum Zeichen von Mustern aus Quadraten.
 *
 * <p>
 * Der ursprünglich deutsche Name dieser Klasse war {@code RekursiveGrafik}.
 * </p>
 *
 * @author Albert Wiedemann
 *
 * @version 1.0
 */
public class RecursiveSquareDrawing
{
    /**
     * Die Schildkröte zum Zeichnen.
     */
    Turtle t;

    /**
     * Der Konstruktor legt die Schildkröte an.
     */
    public RecursiveSquareDrawing()
    {
        t = new Turtle();
        t.setColor("blau");
        t.setPosition(400, 200);
    }

    /**
     * Zeichnet ein Quadrat mit gegebener Seitenlänge an der Stelle der
     * Schildkröte.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war
     * {@code QuadratZeichnen}.
     * </p>
     *
     * @param length Die Seitenlänge des Quadrats.
     */
    private void drawSquare(double length)
    {
        for (int i = 0; i < 4; i += 1)
        {
            t.move(length);
            t.rotate(-90);
        }
    }

    /**
     * Zeichnet ein Element der Perlenkette.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war
     * {@code PerlenketteZeichnen}.
     * </p>
     *
     * @param numberOfBeads Die (Rest-)anzahl der Perlen (Quadrate).
     */
    void drawBeadChain(int numberOfBeads)
    {
        if (numberOfBeads == 1)
        {
            drawSquare(10);
        }
        else
        {
            drawBeadChain(numberOfBeads - 1);
            t.move(20);
            drawSquare(10);
        }
    }

    /**
     * Zeichnet ein Element der Quadratmuster.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war
     * {@code QuadratmusterZeichnen}.
     * </p>
     *
     * @param numberOfSquares Die (Rest-)anzahl der Quadrate.
     */
    void drawSquarePattern(int numberOfSquares)
    {
        if (numberOfSquares > 0)
        {
            drawSquare(numberOfSquares * 10);
            drawSquarePattern(numberOfSquares - 1);
        }
    }

    /**
     * Zeichnet Quadratmuster im Kreis.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code MusterZeichnen}.
     * </p>
     *
     * @param numberOfSquares Die insgesamte Anzahl der Quadrate.
     * @param remainingSquares Die Anzahl der noch zu zeichnenden Quadrate.
     */
    void drawSquareCircle(int numberOfSquares, int remainingSquares)
    {
        if (remainingSquares > 0)
        {
            t.liftPen();
            t.rotate(360 / numberOfSquares);
            t.move(50);
            t.lowerPen();
            drawSquare(20);
            t.liftPen();
            t.rotate(180);
            t.move(50);
            t.lowerPen();
            t.rotate(180);
            drawSquareCircle(numberOfSquares, remainingSquares - 1);
        }
    }

    /**
     * Zeichnet Quadratmuster im Kreis mit variablem Radius.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war
     * {@code MusterZeichnen2}.
     * </p>
     *
     * @param numberOfSquares Die Anzahl der Quadrate.
     * @param remainingSquares Die Anzahl der noch zu zeichnenden Quadrate.
     * @param radius Der Radius des Kreises mit den Quadraten.
     */
    private void drawSquareCircleWithRadius(int numberOfSquares,
            int remainingSquares, int radius)
    {
        if (remainingSquares > 0)
        {
            t.liftPen();
            t.rotate(360 / numberOfSquares);
            t.move(radius);
            t.lowerPen();
            drawSquare(radius / 2);
            t.liftPen();
            t.rotate(180);
            t.move(radius);
            t.lowerPen();
            t.rotate(180);
            drawSquareCircleWithRadius(numberOfSquares, remainingSquares - 1,
                    radius);
        }
    }

    /**
     * Zeichnet eine Folge immer kleinerer Quadratkreise.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war
     * {@code QuadratkreiseZeichnen}.
     * </p>
     *
     * @param numberOfSquares Die Anzahl der Quadrate.
     * @param radius Der Radius des Kreises mit den Quadraten.
     */
    public void drawMultipleSquareCircles(int numberOfSquares, int radius)
    {
        if (radius >= 30)
        {
            drawSquareCircleWithRadius(numberOfSquares, numberOfSquares,
                    radius);
            drawMultipleSquareCircles(numberOfSquares, radius / 2);
        }
    }

    public static void main(String[] args)
    {
        RecursiveSquareDrawing drawing = new RecursiveSquareDrawing();
        drawing.drawBeadChain(10);
        // drawing.drawSquarePattern(10);
        // drawing.drawSquareCircle(7, 7);
        // drawing.drawMultipleSquareCircles(7, 100);
    }
}
