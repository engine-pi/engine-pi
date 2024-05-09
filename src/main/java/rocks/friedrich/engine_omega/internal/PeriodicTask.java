/*
 * Engine Omega ist eine anfängerorientierte 2D-Gaming Engine.
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
package rocks.friedrich.engine_omega.internal;

import rocks.friedrich.engine_omega.FrameUpdateListener;
import rocks.friedrich.engine_omega.internal.annotations.API;
import rocks.friedrich.engine_omega.internal.annotations.Internal;

/**
 * Eine periodische Aufgabe, die regelmäßig ausgeführt wird.
 *
 * @author Niklas Keller
 *
 * @see rocks.friedrich.engine_omega.event.FrameUpdateListenerContainer
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
     * Konstruktor.
     *
     * @param intervalInSeconds Zeit zwischen den Ausführungen in Sekunden.
     */
    public PeriodicTask(double intervalInSeconds, Runnable runnable)
    {
        setInterval(intervalInSeconds);
        this.countdown = intervalInSeconds;
        this.runnable = runnable;
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
     * @param deltaSeconds Die Zeit in Sekunden, die seit dem letzten Update
     *                     vergangen sind.
     */
    @Override
    @Internal
    public void onFrameUpdate(double deltaSeconds)
    {
        countdown -= deltaSeconds;
        if (countdown < 0)
        {
            countdown += interval;
            runnable.run();
        }
    }
}
