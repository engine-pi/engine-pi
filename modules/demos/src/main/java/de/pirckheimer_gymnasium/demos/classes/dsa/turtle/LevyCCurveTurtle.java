package de.pirckheimer_gymnasium.demos.classes.dsa.turtle;

import de.pirckheimer_gymnasium.engine_pi.dsa.turtle.Turtle;
import de.pirckheimer_gymnasium.engine_pi.dsa.turtle.TurtleAlgorithm;

/**
 * Zeichnet die Lévy-C-Kurve.
 *
 * @author Albert Wiedemann
 *
 * @version 1.0
 */
public class LevyCCurveTurtle extends TurtleAlgorithm
{
    private int depth;

    /**
     * Legt die Schildkröte an.
     */
    public LevyCCurveTurtle(Turtle turtle, int depth)
    {
        super(turtle);
        this.depth = depth;
    }

    public LevyCCurveTurtle(Turtle turtle)
    {
        this(turtle, 1);
    }

    public LevyCCurveTurtle()
    {
        this(new Turtle());
    }

    public void draw()
    {
        initalState.position(-4, 0).speed(1);
        drawCurve(depth);
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
     */
    void drawCurve(int depth)
    {
        drawPart(7, depth);
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
            turtle.rotate(45);
            // 1 / Math.sqrt(2) == 0.7071067811865475
            // sqrt -> square root -> Quadratwurzel
            drawPart(length * 0.7071, depth - 1);
            turtle.rotate(-90);
            drawPart(length * 0.7071, depth - 1);
            turtle.rotate(45);
        }
        else
        {
            turtle.move(length);
        }
    }

    public void showDifferentDepths()
    {
        repeat(() -> {
            depth++;
            initalState.speed(10 * depth);
            if (depth > 10)
            {
                return false;
            }
            return true;
        });
    }

    public static void main(String[] args)
    {
        new LevyCCurveTurtle().clearBeforeRun().waitAfterFinish(2)
                .showDifferentDepths();
    }
}
