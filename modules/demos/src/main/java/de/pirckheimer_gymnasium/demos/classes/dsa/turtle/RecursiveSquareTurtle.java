package de.pirckheimer_gymnasium.demos.classes.dsa.turtle;

import de.pirckheimer_gymnasium.engine_pi.dsa.turtle.TurtleScene;
import de.pirckheimer_gymnasium.engine_pi.dsa.turtle.TurtleAlgorithm;

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
public class RecursiveSquareTurtle extends TurtleAlgorithm
{

    /**
     * Der Konstruktor legt die Schildkröte an.
     */
    public RecursiveSquareTurtle()
    {
        this(new TurtleScene());
    }

    public RecursiveSquareTurtle(TurtleScene turtle)
    {
        super(turtle);
        turtle.setSpeed(3);
    }

    private void reset()
    {
        turtle.setPosition(-8, 0);
        turtle.clearBackground();
    }

    public void draw()
    {
        reset();
        drawBeadChain(10);
        reset();
        drawSquarePattern(10);
        reset();
        drawSquareCircle(7, 7);
        reset();
        drawMultipleSquareCircles(7, 3);

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
            turtle.move(length);
            turtle.rotate(-90);
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
        double length = 0.4;
        if (numberOfBeads == 1)
        {
            drawSquare(length);
        }
        else
        {
            drawBeadChain(numberOfBeads - 1);
            turtle.move(2);
            drawSquare(length);
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
            drawSquare((double) numberOfSquares / 2);
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
            turtle.liftPen();
            turtle.rotate(360 / numberOfSquares);
            turtle.move(3);
            turtle.lowerPen();
            drawSquare(1);
            turtle.liftPen();
            turtle.rotate(180);
            turtle.move(3);
            turtle.lowerPen();
            turtle.rotate(180);
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
            int remainingSquares, double radius)
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
    public void drawMultipleSquareCircles(int numberOfSquares, double radius)
    {
        if (radius >= 1)
        {
            drawSquareCircleWithRadius(numberOfSquares, numberOfSquares,
                    radius);
            drawMultipleSquareCircles(numberOfSquares, radius / 2);
        }
    }

    public static void main(String[] args)
    {
        new RecursiveSquareTurtle().show();
    }
}
