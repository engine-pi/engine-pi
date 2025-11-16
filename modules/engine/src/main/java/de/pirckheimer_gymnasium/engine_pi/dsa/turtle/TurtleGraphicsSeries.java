package de.pirckheimer_gymnasium.engine_pi.dsa.turtle;

import java.util.function.Supplier;

import de.pirckheimer_gymnasium.engine_pi.annotations.Internal;

/**
 * @since 0.40.0
 */
public abstract class TurtleGraphicsSeries extends TurtleGraphics
{

    /**
     * @since 0.40.0
     */
    protected Supplier<Boolean> beforeRepeat;

    /**
     * @since 0.40.0
     */
    protected Supplier<Boolean> afterRepeat;

    /**
     * Gibt an wie of eine Turtle-Grafik gezeichnet werden soll. {@code -1}
     * zeichnet die Grafik in einer endlos Schleife.
     *
     * @since 0.40.0
     */
    protected int numberOfSeries = -1;

    /**
     * @since 0.40.0
     */
    public TurtleGraphicsSeries onRepeat(Supplier<Boolean> before,
            Supplier<Boolean> after)
    {
        this.beforeRepeat = before;
        this.afterRepeat = after;
        return this;
    }

    /**
     * Wird ganz am Anfang der Grafik-Reihe ausgeführt.
     *
     * @since 0.40.0
     */
    protected void onSeriesStart()
    {

    }

    /**
     * Wird bevor jedem Zeichenvorgang ausgeführt.
     *
     * @since 0.40.0
     */
    protected void onDrawStart()
    {

    }

    /**
     * Wird nach jedem Zeichenvorgang ausgeführt.
     *
     * @since 0.40.0
     */
    protected void onDrawEnd()
    {

    }

    /**
     * Wird ganz am Ende der Grafik-Reihe ausgeführt.
     *
     * @since 0.40.0
     */
    protected void onSeriesEnd()
    {

    }

    /**
     * @since 0.40.0
     */
    @Internal
    @Override
    public void run()
    {
        onSeriesStart();
        int counter = 0;
        this.onRepeat(afterRepeat);
        // -1 zeichnet die Grafik unendlich oft
        while (numberOfSeries == -1 || counter < numberOfSeries)
        {
            if (beforeRepeat != null && !beforeRepeat.get())
            {
                break;
            }
            counter++;
            onDrawStart();
            draw();
            onDrawEnd();
            if (afterRepeat != null && !afterRepeat.get())
            {
                break;
            }
        }
        onSeriesEnd();
    }

}
