package de.pirckheimer_gymnasium.engine_pi_demos.little_engine.turtle;

import de.pirckheimer_gymnasium.engine_pi.little_engine.Turtle;

/**
 * Rahmenklasse zum Zeichen von Mustern aus Quadraten
 *
 * <p>
 * Die ursprünglich deutsche Name dieser Klasse war {@code RekursiveGrafik}.
 * </p>
 *
 * @author Albert Wiedemann
 *
 * @version 1.0
 */
class RecursiveSquareDrawing
{
    /**
     * Die Turtle zum Zeichnen
     */
    Turtle turtle;

    /**
     * Der Konstruktor legt die Turtle an.
     */
    RecursiveSquareDrawing()
    {
        turtle = new Turtle();
        turtle.setColor("blau");
        turtle.setPosition(400, 200);
    }

    /**
     * Zeichnet ein Quadrat mit gegebener Seitenlänge an der Stelle der Turtle
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war
     * {@code QuadratZeichnen}.
     * </p>
     *
     * @param length Seitenlänge des Quadrats
     */
    private void drawSquare(double length)
    {
        for (int i = 0; i < 4; i += 1)
        {
            turtle.move(length);
            turtle.rotate(-90);
        }
    }

    /**
     * Zeichnet ein Element der Perlenkette
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war
     * {@code PerlenketteZeichnen}.
     * </p>
     *
     * @param numberOfBeads (Rest-)anzahl der Perlen (Quadrate)
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
            turtle.move(20);
            drawSquare(10);
        }
    }

    /**
     * Zeichnet ein Element der Quadratmuster
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war
     * {@code QuadratmusterZeichnen}.
     * </p>
     *
     * @param numberOfSquares (Rest-)anzahl der Quadrate
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
     * Zeichnet Quadratmuster im Kreis
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war {@code MusterZeichnen}.
     * </p>
     *
     * @param numberOfSquares insgesamte Anzahl der Quadrate
     * @param remainingSquares Anzahl der noch zu zeichnenden Quadrate
     */
    void drawSquareCircle(int numberOfSquares, int remainingSquares)
    {
        if (remainingSquares > 0)
        {
            turtle.liftPen();
            turtle.rotate(360 / numberOfSquares);
            turtle.move(50);
            turtle.lowerPen();
            drawSquare(20);
            turtle.liftPen();
            turtle.rotate(180);
            turtle.move(50);
            turtle.lowerPen();
            turtle.rotate(180);
            drawSquareCircle(numberOfSquares, remainingSquares - 1);
        }
    }

    /**
     * Zeichnet Quadratmuster im Kreis mit variablem Radius
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war
     * {@code MusterZeichnen2}.
     * </p>
     *
     * @param numberOfSquares Anzahl der Quadrate
     * @param remainingSquares Anzahl der noch zu zeichnenden Quadrate
     * @param radius der Radius des Kreises mit den Quadraten
     */
    private void drawSquareCircleWithRadius(int numberOfSquares,
            int remainingSquares, int radius)
    {
        if (remainingSquares > 0)
        {
            turtle.liftPen();
            turtle.rotate(360 / numberOfSquares);
            turtle.move(radius);
            turtle.lowerPen();
            drawSquare(radius / 2);
            turtle.liftPen();
            turtle.rotate(180);
            turtle.move(radius);
            turtle.lowerPen();
            turtle.rotate(180);
            drawSquareCircleWithRadius(numberOfSquares, remainingSquares - 1,
                    radius);
        }
    }

    /**
     * Zeichnet eine Folge immer kleinerer Quadratkreise
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war
     * {@code QuadratkreiseZeichnen}.
     * </p>
     *
     * @param numberOfSquares Anzahl der Quadrate
     * @param radius der Radius des Kreises mit den Quadraten
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
