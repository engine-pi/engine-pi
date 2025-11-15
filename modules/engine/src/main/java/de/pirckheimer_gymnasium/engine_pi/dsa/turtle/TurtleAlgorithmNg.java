package de.pirckheimer_gymnasium.engine_pi.dsa.turtle;

import java.util.function.Supplier;

import de.pirckheimer_gymnasium.engine_pi.annotations.Internal;
import de.pirckheimer_gymnasium.engine_pi.util.TimeUtil;

// Demo: file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/demos/src/main/java/de/pirckheimer_gymnasium/demos/classes/dsa/turtle/TurtleDemo.java

/**
 * Eine Hilfsklasse zum Formulieren eines <b>Turtle-Algorithmus</b>.
 *
 * <p>
 * Diese abstrakte Klasse kann geerbt werden, um einen <b>Algorithmus</b> zur
 * Zeichnung einer Turtle-Grafik zu formulieren. Folgendes Code-Beispiel
 * illustiert die Verwendung dieser Hilfsklasse:
 * </p>
 *
 * <pre>{@code
 * import de.pirckheimer_gymnasium.engine_pi.dsa.turtle.TurtleAlgorithm;
 *
 * public class TriangleTurtle extends TurtleAlgorithm
 * {
 *     @Override
 *     public void draw()
 *     {
 *         for (int i = 0; i < 3; i++)
 *         {
 *             turtle.move(4);
 *             turtle.rotate(120);
 *         }
 *     }
 *
 *     public static void main(String[] args)
 *     {
 *         new TriangleTurtle().show();
 *     }
 * }
 * }</pre>
 *
 * <p>
 * Da Turtle-Algorithmen oft rekursiv sind, reicht es nicht den Algorithmus in
 * eine Methode zu schreiben. Die Klasse implementiert auch die
 * {@link Runnable}-Schnittstelle, um lang laufende Algorithmen in einem eigenen
 * Thread laufen zu lassen und gegenfalls zu stoppen, wenn er zu lange läuft.
 * </p>
 *
 * @author Josef Friedrich
 *
 * @since 0.38.0
 */
public abstract class TurtleAlgorithmNg implements Runnable
{
    /**
     * Die <b>Schildkröte</b>, die eine Grafik zeichnet.
     */
    protected TurtleControl turtle;

    /**
     * Eine Callback-Funktion, die aufgerufen wird, wenn die Turtle-Grafik
     * fertig gezeichnet wurde.
     */
    protected Runnable onFinished;

    protected final InitialTurtleState initalState = new InitialTurtleState();

    protected Supplier<Boolean> onRepeat;

    protected boolean clearBeforeRun = false;

    /**
     * Wartet, nachdem die draw-Methode fertig ausgeführt wurde, die angegeben
     * Anzahl an Sekunden bevor zur nächsten Wiederholung übergegangen wird.
     */
    protected double waitAfterFinish = 0.0;

    /**
     * In dieser Methode soll der Turtle-Algorithmus formuliert werden.
     */
    protected abstract void draw();

    /**
     * @hidden
     */
    @Internal
    public void run()
    {
        draw();
        if (waitAfterFinish > 0)
        {
            TimeUtil.sleep((int) (1000 * waitAfterFinish));
        }
        if (onFinished != null)
        {
            onFinished.run();
        }
    }

    public TurtleAlgorithmNg onFinished(Runnable onFinished)
    {
        this.onFinished = onFinished;
        return this;
    }

    public TurtleAlgorithmNg onRepeat(Supplier<Boolean> afterRepeat)
    {
        this.onRepeat = afterRepeat;
        return this;
    }

}
