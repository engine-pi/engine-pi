/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha/src/main/java/ea/internal/PeriodicTask.java
 *
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2011 - 2019 Michael Andonie and contributors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package de.pirckheimer_gymnasium.engine_pi.event;

import de.pirckheimer_gymnasium.engine_pi.annotations.API;
import de.pirckheimer_gymnasium.engine_pi.annotations.Internal;

/**
 * Eine periodische Aufgabe, die regelmäßig ausgeführt wird.
 *
 * @author Niklas Keller
 * @author Josef Friedrich
 *
 * @see FrameUpdateListenerRegistration#repeat(double, Runnable)
 */
public final class PeriodicTask implements FrameUpdateListener
{
    /**
     * Intervall in Sekunden.
     */
    private double interval;

    /**
     * Aktuelle Zeit bis zur nächsten Ausführung in Sekunden.
     */
    private double countdown;

    /**
     * Code, der alle X Sekunden ausgeführt wird.
     */
    private Runnable runnable;

    /**
     * Eine Referenz auf den übergeordneten Behälter, in dem diese periodische
     * Aufgabe angemeldet wurde. Diese Referenz wird dazu verwendet, um die
     * periodische Ausführung abzumelden und dadurch zu stoppen.
     */
    private FrameUpdateListenerRegistration container;

    /**
     * Gibt an, ob die Aufgabe pausiert ist.
     */
    public boolean isPaused;

    /**
     * Erzeugt eine neue periodische Aufgabe, die eine Referenz auf den
     * übergeordneten Behälter verlangt, in dem diese periodische Aufgabe
     * angemeldet wurde.
     *
     * @param intervalInSeconds Die Zeit zwischen den Ausführungen in Sekunden.
     * @param runnable          Ein Objekt vom Typ {@link Runnable}, das eine
     *                          ausführbare Methode enthält oder ein
     *                          Lambda-Ausdruck.
     * @param container         Eine Referenz auf den übergeordneten Behälter,
     *                          in dem diese periodische Aufgabe angemeldet
     *                          wurde. Diese Referenz wird dazu verwendet, um
     *                          die periodische Ausführung abzumelden und
     *                          dadurch zu stoppen.
     *
     * @author Josef Friedrich
     */
    public PeriodicTask(double intervalInSeconds, Runnable runnable,
            FrameUpdateListenerRegistration container)
    {
        setInterval(intervalInSeconds);
        this.countdown = intervalInSeconds;
        this.runnable = runnable;
        this.container = container;
    }

    /**
     * Erzeugt eine neue periodische Aufgabe ohne eine Referenz auf den
     * übergeordneten Behälter, in dem diese periodische Aufgabe angemeldet
     * wurde.
     *
     * @param intervalInSeconds Die Zeit zwischen den Ausführungen in Sekunden.
     * @param runnable          Ein Objekt vom Typ {@link Runnable}, das eine
     *                          ausführbare Methode enthält oder ein
     *                          Lambda-Ausdruck.
     */
    public PeriodicTask(double intervalInSeconds, Runnable runnable)
    {
        this(intervalInSeconds, runnable, null);
    }

    /**
     * Setzt das Intervall dieses periodischen Tasks neu.
     *
     * @param interval Das neue Intervall. Zeit zwischen den Ausführungen in
     *                 Sekunden. Muss größer als 0 sein.
     */
    @API
    public void setInterval(double interval)
    {
        if (interval <= 0)
        {
            throw new RuntimeException(
                    "Das Interval eines periodischen Tasks muss größer als 0 sein, war "
                            + interval);
        }
        this.interval = interval;
    }

    /**
     * Gibt das aktuelle Intervall der periodischen Aufgabe aus.
     *
     * @return Das aktuelle Intervall. Die Zeit zwischen den Ausführungen in
     *         Sekunden.
     */
    @API
    public double getInterval()
    {
        return interval;
    }

    /**
     * Pausiert die periodische Ausführung der Aufgabe.
     *
     * @author Josef Friedrich
     */
    public void pause()
    {
        isPaused = true;
    }

    /**
     * Führt die periodische Aufgabe fort.
     *
     * @author Josef Friedrich
     */
    public void resume()
    {
        isPaused = false;
    }

    /**
     * Schaltet je nach Zustand zwischen den Zuständen pausiert und nicht
     * pausiert hin und her.
     *
     * @author Josef Friedrich
     */
    public void toggle()
    {
        if (isPaused)
        {
            resume();
        }
        else
        {
            pause();
        }
    }

    /**
     * Stoppt die periodische Ausführung. Sie kann dann nicht mehr neu gestartet
     * werden.
     *
     * @author Josef Friedrich
     */
    public void unregister()
    {
        if (this.container != null)
        {
            this.container.removeFrameUpdateListener(this);
        }
        else
        {
            throw new RuntimeException(
                    "Die periodischen Aufgabe kann nicht angemeldet werden, "
                            + "da sie keine Referenz auf den übergeordneten Behälter hat,"
                            + " in dem diese periodische Aufgabe angemeldet wurde.");
        }
    }

    /**
     * @param pastTime Die Zeit in Sekunden, die seit der letzten Aktualisierung
     *                 vergangen ist.
     */
    @Override
    @Internal
    public void onFrameUpdate(double pastTime)
    {
        if (isPaused)
        {
            return;
        }
        countdown -= pastTime;
        if (countdown < 0)
        {
            countdown += interval;
            runnable.run();
        }
    }
}
