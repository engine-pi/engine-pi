/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha/src/main/java/ea/event/FrameUpdateListenerContainer.java
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

/**
 * Eine Schnittstelle zum An- und Abmelden von Beobachtern, die auf
 * Aktualisierungen der Einzelbilder reagieren.
 *
 * Die Schnittstelle {@link FrameUpdateListenerRegistration} definiert Methoden zur
 * Zeitsteuerung. Sie wird von den Klassen
 * {@link de.pirckheimer_gymnasium.engine_pi.actor.Actor Actor},
 * {@link de.pirckheimer_gymnasium.engine_pi.Scene Scene} und
 * {@link de.pirckheimer_gymnasium.engine_pi.Layer Layer} implementieren
 */
@API
public interface FrameUpdateListenerRegistration
{
    /**
     * @return Liste der {@link FrameUpdateListener}
     */
    EventListeners<FrameUpdateListener> getFrameUpdateListeners();

    /**
     * Fügt einen neuen {@link FrameUpdateListener} hinzu.
     */
    @API
    default void addFrameUpdateListener(FrameUpdateListener listener)
    {
        getFrameUpdateListeners().add(listener);
    }

    /**
     * Entfernt einen {@link FrameUpdateListener}.
     */
    @API
    default void removeFrameUpdateListener(FrameUpdateListener listener)
    {
        getFrameUpdateListeners().remove(listener);
    }

    /**
     * Führt das übergebene Runnable mit Verzögerung aus.
     *
     * @param runnable Wird im nächsten Frame ausgeführt.
     */
    @API
    default void defer(Runnable runnable)
    {
        FrameUpdateListener frameUpdateListener = new FrameUpdateListener()
        {
            @Override
            public void onFrameUpdate(double time)
            {
                removeFrameUpdateListener(this);
                runnable.run();
            }
        };
        addFrameUpdateListener(frameUpdateListener);
    }

    /**
     * Führt das übergebene Runnable mit einer vorgegebenen Verzögerung aus.
     *
     * @param timeInSeconds Verzögerung
     * @param runnable      Wird nach Ablauf der Verzögerung ausgeführt
     *
     * @return Listener, der manuell abgemeldet werden kann, falls die
     *         Ausführung abgebrochen werden soll.
     */
    @API
    default FrameUpdateListener delay(double timeInSeconds, Runnable runnable)
    {
        // Später können wir den Return-Type auf SingleTask ändern, falls das
        // notwendig werden sollte
        FrameUpdateListener singleTask = new SingleTask(timeInSeconds, runnable,
                this);
        addFrameUpdateListener(singleTask);
        return singleTask;
    }

    /**
     * Führt das übergebene Runnable mit Verzögerung wiederholend aus.
     *
     * @param intervalInSeconds Verzögerung
     * @param runnable          Wird immer wieder nach Ablauf der Verzögerung
     *                          ausgeführt
     *
     * @return Ein Objekt der Klasse {@link PeriodicTask}, der manuell
     *         abgemeldet werden kann, falls die Ausführung abgebrochen werden
     *         soll.
     */
    @API
    default PeriodicTask repeat(double intervalInSeconds, Runnable runnable)
    {
        PeriodicTask periodicTask = new PeriodicTask(intervalInSeconds,
                runnable, this);
        addFrameUpdateListener(periodicTask);
        return periodicTask;
    }
}
