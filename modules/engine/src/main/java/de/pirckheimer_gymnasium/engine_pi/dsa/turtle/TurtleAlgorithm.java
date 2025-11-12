package de.pirckheimer_gymnasium.engine_pi.dsa.turtle;

import java.util.function.Supplier;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.annotations.Internal;

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
public abstract class TurtleAlgorithm implements Runnable
{
    /**
     * Die <b>Schildkröte</b>, die eine Grafik zeichnet.
     */
    protected Turtle turtle;

    /**
     * Eine Callback-Funktion, die aufgerufen wird, wenn die Turtle-Grafik
     * fertig gezeichnet wurde.
     */
    protected Runnable onFinished;

    protected InitialTurtleState initalState = new InitialTurtleState();

    /**
     * Fügt den <b>Turtle-Algorithmus</b> in eine <b>neue Szene</b>.
     *
     * @since 0.38.0
     */
    public TurtleAlgorithm()
    {
        this(new Turtle());
    }

    /**
     * Fügt den <b>Turtle-Algorithmus</b> in eine <b>vorhandet Szene</b>.
     *
     * @since 0.38.0
     */
    public TurtleAlgorithm(Turtle turtle)
    {
        this.turtle = turtle;
    }

    public TurtleAlgorithm onFinished(Runnable onFinished)
    {
        this.onFinished = onFinished;
        return this;
    }

    protected void initialize(InitialTurtleState state)
    {

    }

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
        if (initalState != null)
        {
            initialize(initalState);
            initalState.apply(turtle);
        }
        draw();
        if (onFinished != null)
        {
            onFinished.run();
        }
    }

    public void repeat(Supplier<Boolean> afterRun, boolean openWindow)
    {
        if (openWindow)
        {
            Game.startSafe(turtle);
        }
        while (true)
        {
            run();
            if (!afterRun.get())
            {
                break;
            }
        }
    }

    public void repeat(Supplier<Boolean> afterRun)
    {
        repeat(afterRun, true);
    }

    /**
     * <b>Löst</b> den Turtle-Algorithmus <b>aus</b>.
     *
     * @param openWindow Falls wahr, wir ein neues Fenster geöffnet.
     *
     * @since 0.38.0
     */
    private void trigger(boolean openWindow)
    {
        if (openWindow)
        {
            Game.start(turtle);
        }
        run();
    }

    /**
     * <b>Startet</b> den Turtle-Algorithmus und <b>öffnet</b> ein Fenster.
     *
     * @since 0.38.0
     */
    public void show()
    {
        trigger(true);
    }

    /**
     * <b>Startet</b> den Turtle-Algorithmus und öffnet <b>kein</b> Fenster.
     *
     * @since 0.38.0
     */
    public void start()
    {
        trigger(false);
    }

    /**
     * Gibt die <b>Schildkröte</b>, mit der gezeichnet wird, zurück.
     *
     * @return Die <b>Schildkröte</b>, mit der gezeichnet wird.
     *
     * @since 0.38.0
     */
    public Turtle getTurtle()
    {
        return turtle;
    }
}
