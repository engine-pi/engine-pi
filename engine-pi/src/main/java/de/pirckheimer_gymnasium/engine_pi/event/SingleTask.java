/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha/src/main/java/ea/internal/SingleTask.java
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
 * Eine einfache Aufgabe, der einmalig mit Verzögerung ausgeführt wird.
 *
 * @author Niklas Keller
 *
 * @see FrameUpdateListenerRegistration#delay(double, Runnable)
 */
@Internal
public final class SingleTask implements FrameUpdateListener
{
    /**
     * Verzögerung in Sekunden.
     */
    private final double delay;

    /**
     * Aktuelle Zeit bis zur nächsten Ausführung.
     */
    private double countdown;

    /**
     * Code, der nach X Sekunden ausgeführt wird.
     */
    private final Runnable task;

    /**
     * Sagt, ob die Aufgabe bereits vollständig ausgeführt wurde.
     */
    private boolean done;

    /**
     * Container, an dem die Aufgabe angemeldet wird, wo er sich auch selbst
     * wieder abmeldet.
     */
    private final FrameUpdateListenerRegistration parent;

    /**
     * @param delayInSeconds Zeit zwischen den Ausführungen in Sekunden.
     */
    public SingleTask(double delayInSeconds, Runnable task,
            FrameUpdateListenerRegistration parent)
    {
        this.delay = delayInSeconds;
        this.countdown = delayInSeconds;
        this.task = task;
        this.parent = parent;
    }

    /**
     * Gibt die Verzögerung in Sekunden aus.
     *
     * @return Die Verzögerung in Sekunden.
     */
    @API
    public double getDelay()
    {
        return delay;
    }

    /**
     * @return Sagt, ob die Aufgabe bereits vollständig ausgeführt wurde.
     */
    @API
    public boolean isDone()
    {
        return done;
    }

    /**
     * @param pastTime Die Zeit in Sekunden, die seit dem letzten Update
     *     vergangen
     *
     * @hidden
     */
    @Override
    public void onFrameUpdate(double pastTime)
    {
        countdown -= pastTime;
        if (!done && this.countdown < 0)
        {
            task.run();
            parent.removeFrameUpdateListener(this);
            done = true;
        }
    }
}
