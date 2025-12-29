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
package pi.event;

import pi.annotations.API;
import pi.annotations.Internal;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/demos/src/main/java/demos/classes/event/PeriodicTaskExecutorDemo.java

/**
 * Führt <b>Aufgaben regelmäßig</b> aus.
 *
 * @author Niklas Keller
 * @author Josef Friedrich
 *
 * @see FrameUpdateListenerRegistration#repeat(double, PeriodicTask)
 * @see FrameUpdateListenerRegistration#repeat(double, int, PeriodicTask,
 *     PeriodicTask)
 */
public final class PeriodicTaskExecutor implements FrameUpdateListener
{
    /**
     * Aktuelle Zeit bis zur nächsten Ausführung in Sekunden.
     */
    private double countdown;

    /**
     * Intervall in Sekunden.
     */
    private double interval;

    /**
     * Zählt, wie oft die Aufgabe bereits ausgeführt wurde.
     */
    private int counter;

    /**
     * Die <b>Anzahl an Wiederholungen</b> der Aufgabe.
     *
     * <p>
     * Gibt an, wie oft die Aufgabe wiederholt wird. Ist dieses Attribut auf
     * {@code -1} gesetzt, so wird die Aufgabe unendlich oft wiederholt.
     * </p>
     */
    private int repetitions = -1;

    /**
     * Die Aufgabe, die regelmäßig ausgeführt wird.
     */
    private PeriodicTask task;

    /**
     * Die Aufgabe, die als letzte Aufgabe ausgeführt wird.
     */
    private PeriodicTask finalTask;

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
     * <b>übergeordneten Behälter</b> verlangt, in dem diese periodische Aufgabe
     * angemeldet wurde. Die Ausführung wird nach einer bestimmten <b>Anzahl an
     * Wiederholungen</b> unterbrochen und als letzte Wiederholungen eine
     * <b>abschließende Aufgabe</b> ausgeführt.
     *
     * @param interval Die Zeit zwischen den Ausführungen in Sekunden.
     * @param repetitions Die <b>Anzahl an Wiederholungen</b> der Aufgabe. Gibt
     *     an, wie oft die Aufgabe wiederholt wird. Ist dieses Attribut auf
     *     {@code -1} gesetzt, so wird die Aufgabe unendlich oft wiederholt.
     * @param task Die Aufgabe, die regelmäßig ausgeführt wird.
     * @param finalTask Die Aufgabe, die als letzte Aufgabe ausgeführt wird.
     * @param container Eine Referenz auf den übergeordneten Behälter, in dem
     *     diese periodische Aufgabe angemeldet wurde. Diese Referenz wird dazu
     *     verwendet, um die periodische Ausführung abzumelden und dadurch zu
     *     stoppen.
     *
     * @author Josef Friedrich
     */
    public PeriodicTaskExecutor(double interval, int repetitions,
            PeriodicTask task, PeriodicTask finalTask,
            FrameUpdateListenerRegistration container)
    {
        setInterval(interval);
        this.repetitions = repetitions;
        this.task = task;
        this.finalTask = finalTask;
        this.container = container;
    }

    /**
     * Erzeugt eine neue periodische Aufgabe, die eine Referenz auf den
     * übergeordneten Behälter verlangt, in dem diese periodische Aufgabe
     * angemeldet wurde.
     *
     * @param interval Die Zeit zwischen den Ausführungen in Sekunden.
     * @param task Die Aufgabe, die regelmäßig ausgeführt wird.
     * @param container Eine Referenz auf den übergeordneten Behälter, in dem
     *     diese periodische Aufgabe angemeldet wurde. Diese Referenz wird dazu
     *     verwendet, um die periodische Ausführung abzumelden und dadurch zu
     *     stoppen.
     *
     * @author Josef Friedrich
     */
    public PeriodicTaskExecutor(double interval, PeriodicTask task,
            FrameUpdateListenerRegistration container)
    {
        setInterval(interval);
        this.task = task;
        this.container = container;
    }

    /**
     * Erzeugt eine neue periodische Aufgabe ohne eine Referenz auf den
     * übergeordneten Behälter, in dem diese periodische Aufgabe angemeldet
     * wurde.
     *
     * @param interval Die Zeit zwischen den Ausführungen in Sekunden.
     * @param task Die Aufgabe, die regelmäßig ausgeführt wird. Ein Objekt vom
     *     Typ {@link Runnable}, das eine ausführbare Methode enthält oder ein
     *     Lambda-Ausdruck.
     */
    public PeriodicTaskExecutor(double interval, PeriodicTask task)
    {
        this(interval, task, null);
    }

    /**
     * Setzt das Intervall dieses periodischen Tasks neu.
     *
     * @param interval Das neue Intervall. Zeit zwischen den Ausführungen in
     *     Sekunden. Muss größer als 0 sein.
     *
     * @return Eine Instanz dieses Objekts, damit das Objekt über verkettete
     *     Setter konfiguriert werden kann.
     */
    @API
    public PeriodicTaskExecutor setInterval(double interval)
    {
        if (interval <= 0)
        {
            throw new RuntimeException(
                    "Das Interval eines periodischen Tasks muss größer als 0 sein, war "
                            + interval);
        }
        this.interval = interval;
        this.countdown = interval;
        return this;
    }

    /**
     * Gibt das aktuelle Intervall der periodischen Aufgabe aus.
     *
     * @return Das aktuelle Intervall. Die Zeit zwischen den Ausführungen in
     *     Sekunden.
     */
    @API
    public double getInterval()
    {
        return interval;
    }

    /**
     * Setzt die <b>Anzahl an Wiederholungen</b> der Aufgabe.
     *
     * @param repetitions Die <b>Anzahl an Wiederholungen</b> der Aufgabe.
     *
     * @return Eine Instanz dieses Objekts, damit das Objekt über verkettete
     *     Setter konfiguriert werden kann.
     */
    public PeriodicTaskExecutor setRepetitions(int repetitions)
    {
        this.repetitions = repetitions;
        return this;
    }

    /**
     * Gibt die <b>Anzahl an Wiederholungen</b> der Aufgabe zurück.
     *
     * @return Die <b>Anzahl an Wiederholungen</b> der Aufgabe.
     */
    public int getRepetitions()
    {
        return repetitions;
    }

    /**
     * Setzt die <b>Aufgabe</b>, die regelmäßig ausgeführt wird.
     *
     * @param task Die <b>Aufgabe</b>, die regelmäßig ausgeführt wird.
     *
     * @return Eine Instanz dieses Objekts, damit das Objekt über verkettete
     *     Setter konfiguriert werden kann.
     */
    public PeriodicTaskExecutor setTask(PeriodicTask task)
    {
        this.task = task;
        return this;
    }

    /**
     * Gibt die <b>Aufgabe</b>, die regelmäßig ausgeführt wird, zurück.
     *
     * @return Die <b>Aufgabe</b>, die regelmäßig ausgeführt wird.
     */
    public PeriodicTask getTask()
    {
        return task;
    }

    /**
     * Setzt die Aufgabe, die als <b>letzte Aufgabe</b> ausgeführt wird.
     *
     * @param finalTask Die Aufgabe, die als <b>letzte Aufgabe</b> ausgeführt
     *     wird..
     *
     * @return Eine Instanz dieses Objekts, damit das Objekt über verkettete
     *     Setter konfiguriert werden kann.
     */
    public PeriodicTaskExecutor setFinalTask(PeriodicTask finalTask)
    {
        this.finalTask = finalTask;
        return this;
    }

    /**
     * Gibt die Aufgabe, die als <b>letzte Aufgabe</b> ausgeführt wird, zurück.
     *
     * @return Die Aufgabe, die als <b>letzte Aufgabe</b> ausgeführt wird.
     */
    public PeriodicTask getFinalTask()
    {
        return finalTask;
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
     *
     * @throws RuntimeException Falls kein {@link #container} gesetzt ist.
     */
    public void unregister()
    {
        if (container != null)
        {
            container.removeFrameUpdateListener(this);
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
     * Stoppt die periodische Ausführung und wirft dabei keine Ausnahme. Sie
     * kann dann nicht mehr neu gestartet werden.
     *
     * @author Josef Friedrich
     */
    public void unregisterSafe()
    {
        if (container != null)
        {
            container.removeFrameUpdateListener(this);
        }
    }

    /**
     * @param pastTime Die Zeit in Sekunden, die seit der letzten Aktualisierung
     *     vergangen ist.
     *
     * @hidden
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
        while (countdown < 0)
        {
            if (repetitions > 0)
            {
                repetitions--;
            }
            countdown += interval;
            if (repetitions == 0 && finalTask != null)
            {
                counter++;
                finalTask.run(counter);
            }
            else
            {
                counter++;
                task.run(counter);
            }
            if (repetitions == 0)
            {
                unregister();
                return;
            }
        }
    }
}
