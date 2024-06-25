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
 * Eine Schnittstelle zum An- und Abmelden von Beobachtern, die auf die
 * Aktualisierungen der Einzelbilder reagieren.
 *
 * <p>
 * Die Schnittstelle {@link FrameUpdateListenerRegistration} definiert Methoden
 * zur Zeitsteuerung. Sie wird von den Klassen
 * {@link de.pirckheimer_gymnasium.engine_pi.actor.Actor Actor},
 * {@link de.pirckheimer_gymnasium.engine_pi.Scene Scene} und
 * {@link de.pirckheimer_gymnasium.engine_pi.Layer Layer} implementieren
 * </p>
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
     * Führt die übergebene Aufgabe mit einer vorgegebenen Verzögerung aus.
     *
     * @param delay Die Verzögerung in Sekunden.
     * @param task  Die Aufgabe, die nach Ablauf der Verzögerung ausgeführt
     *              wird.
     *
     * @return Der Beobachter, der manuell abgemeldet werden kann, falls die
     *         Ausführung abgebrochen werden soll.
     */
    @API
    default FrameUpdateListener delay(double delay, Runnable task)
    {
        // Später können wir den Return-Type auf SingleTask ändern, falls das
        // notwendig werden sollte
        FrameUpdateListener singleTask = new SingleTask(delay, task, this);
        addFrameUpdateListener(singleTask);
        return singleTask;
    }

    /**
     * Führt eine <b>Aufgabe</b> in einem bestimmten <b>Zeitintervall</b>
     * wiederholend aus. Die Ausführung wird nach einer bestimmten <b>Anzahl an
     * Wiederholungen</b> unterbrochen und als letzte Wiederholungen eine
     * <b>abschließende Aufgabe</b> ausgeführt.
     *
     * @param interval    Die Zeit zwischen den Ausführungen in Sekunden.
     * @param repetitions Die <b>Anzahl an Wiederholungen</b> der Aufgabe. Gibt
     *                    an, wie oft die Aufgabe wiederholt wird. Ist dieses
     *                    Attribut auf {@code -1} gesetzt, so wird die Aufgabe
     *                    unendlich oft wiederholt.
     * @param task        Die Aufgabe, die regelmäßig ausgeführt wird.
     * @param finalTask   Die Aufgabe, die als letzte Aufgabe ausgeführt wird.
     *
     * @author Josef Friedrich
     */
    @API
    default PeriodicTaskExecutor repeat(double interval, int repetitions,
            PeriodicTask task, PeriodicTask finalTask)
    {
        PeriodicTaskExecutor periodicTask = new PeriodicTaskExecutor(interval,
                repetitions, task, finalTask, this);
        addFrameUpdateListener(periodicTask);
        return periodicTask;
    }

    /**
     * Führt eine <b>Aufgabe</b> in einem bestimmten <b>Zeitintervall</b>
     * wiederholend aus.
     *
     * @param interval Das Zeitintervall in Sekunden.
     * @param task     Die Aufgabe, die regelmäßig ausgeführt wird.
     *
     * @return Ein Objekt der Klasse {@link PeriodicTaskExecutor}, der manuell
     *         abgemeldet werden kann, falls die Ausführung abgebrochen werden
     *         soll.
     */
    @API
    default PeriodicTaskExecutor repeat(double interval, PeriodicTask task)
    {
        return repeat(interval, -1, task, null);
    }
}
