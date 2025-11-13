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
    private Vector position = new Vector(0, 0);

    /**
     * Die Richtung, in die die Schildkröte schaut.
     *
     * @since 0.40.0
     */
    private double direction = 0;

    /**
     * Zeigt an, ob die Schildkröte momentan den <b>Stift gesenkt</b> hat und
     * zeichnet oder nicht.
     *
     * @since 0.40.0
     */
    private boolean penDown = true;

    /**
     * Die <b>Geschwindigkeit</b>, mit der sich die Schildkröte bewegt (in Meter
     * pro Sekunde).
     *
     * @since 0.40.0
     */
    private double speed = 6;

    /**
     * Im sogenannte Warp-Modus finden keine Animationen statt. Die
     * Turtle-Grafik wird so schnell wie möglich gezeichnet.
     *
     * @since 0.40.0
     */
    private boolean warpMode = false;

    /**
     * Ignoriert den Anfangszustand bei einer Ausführung des Algorithmus. Die
     * Schildkröte bleibt im Zustand, wie sie den Algorithmus verlassen hat.
     */
    private boolean ignore;

    private static InitialTurtleState getDefaults()
    {
        return new InitialTurtleState();
    }

    private static void copy(InitialTurtleState from, InitialTurtleState to)
    {
        to.position = from.position;
        to.direction = from.direction;
        to.penDown = from.penDown;
        to.speed = from.speed;
        to.warpMode = from.warpMode;
        to.ignore = from.ignore;
    }

    public InitialTurtleState set(InitialTurtleState state)
    {
        copy(state, this);
        return this;
    }

    public InitialTurtleState reset()
    {
        copy(getDefaults(), this);
        return this;
    }

    public InitialTurtleState ignore()
    {
        ignore = true;
        return this;
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
        if (ignore)
        {
            return;
        }
        turtle.setPosition(position);
        turtle.setDirection(direction);
        turtle.setPen(penDown);
        turtle.setSpeed(speed);
        turtle.setWarpMode(warpMode);
    }
}
