package de.pirckheimer_gymnasium.engine_pi.dsa.turtle;

import de.pirckheimer_gymnasium.engine_pi.Vector;

/**
 * Alle Einstellungen, die die Schildkröte braucht, um einen Algorithmus
 * sinnvoll zeichnen zu können.
 *
 * <p>
 * Falls der Algorithmus mehrmals hintereinander ausgeführt wird oder
 * verschiedene Algorithmen hintereinander ausgeführt werden, dann kann diese
 * Klasse benutzt werden, um den Anfangszustand zu speichern.
 * </p>
 *
 * @since 0.40.0
 */
public class InitialTurtleState
{
    /**
     * Die Startposition der Schildkröte.
     *
     * @since 0.40.0
     */
    private Vector position;

    /**
     * Die Richtung, in die die Schildkröte schaut.
     *
     * @since 0.40.0
     */
    private double direction;

    /**
     * Zeigt an, ob die Schildkröte momentan den <b>Stift gesenkt</b> hat und
     * zeichnet oder nicht.
     *
     * @since 0.40.0
     */
    private boolean penDown;

    /**
     * Die <b>Geschwindigkeit</b>, mit der sich die Schildkröte bewegt (in Meter
     * pro Sekunde).
     *
     * @since 0.40.0
     */
    private double speed;

    /**
     * Im sogenannte Warp-Modus finden keine Animationen statt. Die
     * Turtle-Grafik wird so schnell wie möglich gezeichnet.
     *
     * @since 0.40.0
     */
    private boolean warpMode;

    public InitialTurtleState()
    {
        this(new Vector(0, 0), 0, true, 6, false);
    }

    /**
     * @since 0.40.0
     */
    public InitialTurtleState(Vector position, double direction,
            boolean penDown, double speed, boolean warpMode)
    {
        this.position = position;
        this.direction = direction;
        this.penDown = penDown;
        this.speed = speed;
        this.warpMode = warpMode;
    }

    /**
     * @since 0.40.0
     */
    public InitialTurtleState position(Vector position)
    {
        this.position = position;
        return this;
    }

    /**
     * @since 0.40.0
     */
    public InitialTurtleState position(double x, double y)
    {
        position(new Vector(x, y));
        return this;
    }

    /**
     * @since 0.40.0
     */
    public InitialTurtleState direction(double direction)
    {
        this.direction = direction;
        return this;
    }

    /**
     * @since 0.40.0
     */
    public InitialTurtleState drawLine(boolean drawLine)
    {
        this.penDown = drawLine;
        return this;
    }

    /**
     * @since 0.40.0
     */
    public InitialTurtleState speed(double speed)
    {
        this.speed = speed;
        return this;
    }

    /**
     * @since 0.40.0
     */
    public InitialTurtleState warpMode(boolean warpMode)
    {
        this.warpMode = warpMode;
        return this;
    }

    /**
     * Wendet den Anfangszustand auf eine Schildkröte an.
     *
     * @since 0.40.0
     */
    public void apply(Turtle turtle)
    {
        turtle.setPosition(position);
        turtle.setDirection(direction);
        turtle.setPen(penDown);
        turtle.setSpeed(speed);
        turtle.setWarpMode(warpMode);
    }
}
