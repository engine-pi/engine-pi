/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2024 Josef Friedrich and contributors.
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

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import pi.Controller;
import pi.annotations.Getter;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/demos/src/main/java/demos/classes/event/PressedKeyRepeaterDemo.java

/**
 * Führt bei <b>gedrückter Taste mehrmals</b> die <b>gleiche Aufgabe</b> in
 * einem <b>bestimmten zeitlichen Abstand</b> aus.
 *
 * <p>
 * Folgender Code-Ausschnitt registriert zwei Beobachter, nämlich einen für die
 * linke Pfeiltaste und einen für die rechte Pfeiltaste. Die auszuführenden
 * Aufgaben werden dabei als Lambda-Ausdrücke ({@code () -> {}}) notiert.
 * </p>
 *
 * <pre>{@code
 * import java.awt.event.KeyEvent;
 *
 * PressedKeyRepeater repeater = new PressedKeyRepeater(0.5, 1);
 * repeater.addListener(KeyEvent.VK_RIGHT, () -> {
 *     System.out.println("right");
 * });
 * repeater.addListener(KeyEvent.VK_LEFT,
 *         () -> System.out.println("left initial"),
 *         () -> System.out.println("left"),
 *         () -> System.out.println("left final"));
 * }</pre>
 *
 * <p>
 * Wird die rechte Taste gedrückt gehalten, erscheint sofort der Text
 * {@code right} in der Konsole, nach einer Sekunde ({@code 1}) ein weiteres
 * {@code right} und dann immer nach einer halben Sekunde ({@code 0.5})
 * {@code right}.
 * </p>
 *
 * <p>
 * Wird die linke Taste gedrückt gehalten, erscheint sofort der Text
 * {@code initial left} und in der nächten Zeile {@code left}, nach einer
 * Sekunde ein weiteres {@code left} und dann immer nach einer halben Sekunde
 * {@code left}. Beim Loslassen der Taste erscheint {@code final left}
 * </p>
 *
 * @author Josef Friedrich
 *
 * @since 0.9.0
 */
public class PressedKeyRepeater implements KeyStrokeListener
{
    private final List<PressedKeyListener> listeners;

    private final List<PressedKeyExecutor> executors;

    /**
     * Der Standardwert für das <b>Zeitintervall</b> (in Sekunden), nach dem die
     * Aufgabe <b>wiederholt</b> wird.
     */
    private double DEFAULT_INTERVAL = 0.03;

    /**
     * Der Standardwert für die Verzögerung (in Sekunden) zwischen dem <b>ersten
     * Tastendruck</b> und der <b>ersten Wiederholung</b> der Aufgabe.
     */
    private double DEFAULT_INITIAL_INTERVAL = 0.15;

    /**
     * Führt die einzelnen Aufgaben einer Tastenaktion aus.
     */
    private class PressedKeyExecutor
            implements FrameUpdateListener, KeyStrokeListener
    {
        private double countdown;

        private final PressedKeyListener action;

        public PressedKeyExecutor(PressedKeyListener action)
        {
            this.action = action;
            countdown = action.initialInterval();
            Controller.addKeyStrokeListener(this);
            Controller.addFrameUpdateListener(this);
            action.runInitialTask();
            action.runRepeatedTask();
        }

        @Override
        public void onFrameUpdate(double pastTime)
        {
            countdown -= pastTime;
            if (countdown < 0)
            {
                action.runRepeatedTask();
                countdown = action.interval();
            }
        }

        public void onKeyDown(KeyEvent e)
        {
            // Do nothing
        }

        public void stop()
        {
            Controller.removeFrameUpdateListener(this);
            Controller.removeKeyStrokeListener(this);
            action.runFinalTask();
        }

        @Override
        public void onKeyUp(KeyEvent e)
        {
            if (e.getKeyCode() == action.keyCode())
            {
                stop();
                executors.remove(this);
            }
        }
    }

    /**
     * Ein Beobachter, der auf länger gedrückte Tasten reagiert und dann in
     * einem bestimmten Zeitintervall eine Aufgabe mehrmals wiederholt.
     */
    private class PressedKeyListener
    {
        /**
         * Die <b>Nummer der Taste</b>, durch die die Aufgaben gestartet werden.
         * Beispielsweise kann mit dem statischen Attribut {@code KeyEvent.VK_A}
         * die Tastennummer der Taste „A“ bestimmt werden.
         *
         * @see KeyEvent
         */
        private final int keyCode;

        /**
         * Das <b>Zeitintervall</b> (in Sekunden) nach dem die Aufgabe
         * <b>wiederholt</b> wird.
         */
        private double interval;

        /**
         * Die Verzögerung (in Sekunden) zwischen dem <b>ersten Tastendruck</b>
         * und der <b>ersten Wiederholung</b> der Aufgabe.
         */
        private double initialInterval;

        /**
         * Eine einmalige <b>Aufgabe</b>, die <b>unmittelbar</b> ausgeführt
         * wird, wenn die entsprechende Taste <b>gedrückt</b> wird.
         */
        private Runnable initialTask;

        /**
         * Die <b>Aufgabe</b>, die nach einem bestimmten Zeitintervall
         * <b>wiederholt</b> wird.
         */
        private final Runnable repeatedTask;

        /**
         * Eine einmalige <b>Aufgabe</b>, die beim <b>Loslassen</b> der
         * entsprechenden Taste, ausgeführt wird.
         */
        private Runnable finalTask;

        /**
         * Erzeugt einen <b>Beobachter</b>, der auf gedrückt gehaltene Tasten
         * reagiert.
         *
         * <p>
         * Dabei kann die <b>Tastennummer</b>, die sich <b>wiederholende
         * Aufgabe</b>, das <b>Zeitintervall der Wiederholung</b> und
         * <b>anfängliche Verzögerung</b> angegeben werden.
         * </p>
         *
         *
         * @param keyCode Die <b>Nummer der Taste</b>, durch die die Aufgaben
         *     gestartet werden. Beispielsweise kann mit dem statischen Attribut
         *     {@code KeyEvent.VK_A} die Tastennummer der Taste „A“ bestimmt
         *     werden.
         * @param repeatedTask Die <b>Aufgabe</b>, die nach einem bestimmten
         *     Zeitintervall <b>wiederholt</b> wird.
         * @param interval Das <b>Zeitintervall</b> (in Sekunden) nach dem die
         *     Aufgabe <b>wiederholt</b> wird.
         * @param initialInterval Die Verzögerung (in Sekunden) zwischen dem
         *     <b>ersten Tastendruck</b> und der <b>ersten Wiederholung</b> der
         *     Aufgabe.
         */
        public PressedKeyListener(int keyCode, Runnable repeatedTask,
                double interval, double initialInterval)
        {
            this.keyCode = keyCode;
            this.interval = interval;
            this.repeatedTask = repeatedTask;
            this.initialInterval = initialInterval;
        }

        /**
         * Erzeugt einen <b>Beobachter</b>, der auf gedrückt gehaltene Tasten
         * reagiert.
         *
         * <p>
         * Dabei kann die <b>Tastennummer</b> und die sich <b>wiederholende
         * Aufgabe</b> angegeben werden.
         * </p>
         *
         * @param keyCode Die <b>Nummer der Taste</b>, durch die die Aufgaben
         *     gestartet werden. Beispielsweise kann mit dem statischen Attribut
         *     {@code KeyEvent.VK_A} die Tastennummer der Taste „A“ bestimmt
         *     werden.
         * @param repeatedTask Die <b>Aufgabe</b>, die nach einem bestimmten
         *     Zeitintervall <b>wiederholt</b> wird.
         */
        public PressedKeyListener(int keyCode, Runnable repeatedTask)
        {
            this.keyCode = keyCode;
            this.repeatedTask = repeatedTask;
        }

        /**
         * Erzeugt einen <b>Beobachter</b>, der auf gedrückt gehaltene Tasten
         * reagiert.
         *
         * <p>
         * Dabei kann die <b>Tastennummer</b>, die sich <b>wiederholende
         * Aufgabe</b> und die <b>abschließende einmalige Aufgabe</b> angegeben
         * werden.
         * </p>
         *
         * @param keyCode Die <b>Nummer der Taste</b>, durch die die Aufgaben
         *     gestartet werden. Beispielsweise kann mit dem statischen Attribut
         *     {@code KeyEvent.VK_A} die Tastennummer der Taste „A“ bestimmt
         *     werden.
         * @param repeatedTask Die <b>Aufgabe</b>, die nach einem bestimmten
         *     Zeitintervall <b>wiederholt</b> wird.
         * @param finalTask Eine einmalige <b>Aufgabe</b>, die beim
         *     <b>Loslassen</b> der entsprechenden Taste, ausgeführt wird.
         */
        public PressedKeyListener(int keyCode, Runnable repeatedTask,
                Runnable finalTask)
        {
            this.keyCode = keyCode;
            this.repeatedTask = repeatedTask;
            this.finalTask = finalTask;
        }

        /**
         * Erzeugt einen <b>Beobachter</b>, der auf gedrückt gehaltene Tasten
         * reagiert.
         *
         * <p>
         * Dabei kann die <b>Tastennummer</b>, die <b>anfängliche einmalige
         * Aufgabe</b>, die sich <b>wiederholende Aufgabe</b> und
         * <b>abschließende einmalige Aufgabe</b> angegeben werden.
         * </p>
         *
         * @param keyCode Die <b>Nummer der Taste</b>, durch die die Aufgaben
         *     gestartet werden. Beispielsweise kann mit dem statischen Attribut
         *     {@code KeyEvent.VK_A} die Tastennummer der Taste „A“ bestimmt
         *     werden.
         * @param initialTask Eine einmalige <b>Aufgabe</b>, die
         *     <b>unmittelbar</b> ausgeführt wird, wenn die entsprechende Taste
         *     <b>gedrückt</b> wird.
         * @param repeatedTask Die <b>Aufgabe</b>, die nach einem bestimmten
         *     Zeitintervall <b>wiederholt</b> wird.
         * @param finalTask Eine einmalige <b>Aufgabe</b>, die beim
         *     <b>Loslassen</b> der entsprechenden Taste, ausgeführt wird.
         */
        public PressedKeyListener(int keyCode, Runnable initialTask,
                Runnable repeatedTask, Runnable finalTask)
        {
            this.keyCode = keyCode;
            this.initialTask = initialTask;
            this.repeatedTask = repeatedTask;
            this.finalTask = finalTask;
        }

        @Getter
        public int keyCode()
        {
            return keyCode;
        }

        @Getter
        public double interval()
        {
            if (interval == 0)
            {
                return DEFAULT_INTERVAL;
            }
            return interval;
        }

        public void runInitialTask()
        {
            if (initialTask != null)
            {
                initialTask.run();
            }
        }

        public void runRepeatedTask()
        {
            repeatedTask.run();
        }

        public void runFinalTask()
        {
            if (finalTask != null)
            {
                finalTask.run();
            }
        }

        @Getter
        public double initialInterval()
        {
            if (initialInterval == 0.0)
            {
                return DEFAULT_INITIAL_INTERVAL;
            }
            return initialInterval;
        }
    }

    /**
     * Erzeugt einen Wiederholer durch Angabe des <b>Zeitintervalls der
     * Wiederholung</b> und der <b>anfänglichen Verzögerung</b>.
     *
     * @param interval Das <b>Zeitintervall</b> (in Sekunden) nach dem die
     *     Aufgabe <b>wiederholt</b> wird.
     * @param initialInterval Die Verzögerung (in Sekunden) zwischen dem
     *     <b>ersten Tastendruck</b> und der <b>ersten Wiederholung</b> der
     *     Aufgabe.
     */
    public PressedKeyRepeater(double interval, double initialInterval)
    {
        if (interval > 0)
        {
            DEFAULT_INTERVAL = interval;
        }
        if (initialInterval > 0)
        {
            DEFAULT_INITIAL_INTERVAL = initialInterval;
        }
        listeners = new ArrayList<>();
        executors = new ArrayList<>();
        Controller.addKeyStrokeListener(this);
    }

    /**
     * Erzeugt einen Wiederholer mit <b>Standardwerten für die
     * Zeitintervalle</b>.
     *
     * <p>
     * Der Standardwert für das <b>Zeitintervall der Wiederholung</b> ist
     * {@code 0.03} und der Standardwert für die <b>anfängliche zeitliche
     * Verzögerung</b> {@code 0.15}
     * </p>
     */
    public PressedKeyRepeater()
    {
        this(0, 0);
    }

    /**
     * Fügt einen <b>Beobachter</b> hinzu, der auf gedrückt gehaltene Tasten
     * reagiert; dabei kann die <b>Tastennummer</b>, die sich <b>wiederholende
     * Aufgabe</b>, das <b>Zeitintervall der Wiederholung</b> und <b>anfängliche
     * Verzögerung</b> angegeben werden.
     *
     * @param keyCode Die <b>Nummer der Taste</b>, durch die die Aufgaben
     *     gestartet werden. Beispielsweise kann mit dem statischen Attribut
     *     {@code KeyEvent.VK_A} die Tastennummer der Taste „A“ bestimmt werden.
     * @param repeatedTask Die <b>Aufgabe</b>, die nach einem bestimmten
     *     Zeitintervall <b>wiederholt</b> wird.
     * @param interval Das <b>Zeitintervall</b> (in Sekunden) nach dem die
     *     Aufgabe <b>wiederholt</b> wird.
     * @param initialInterval Die Verzögerung (in Sekunden) zwischen dem
     *     <b>ersten Tastendruck</b> und der <b>ersten Wiederholung</b> der
     *     Aufgabe.
     */
    public void addListener(int keyCode, Runnable repeatedTask, double interval,
            double initialInterval)
    {
        listeners.add(new PressedKeyListener(keyCode, repeatedTask, interval,
                initialInterval));
    }

    /**
     * Fügt einen <b>Beobachter</b> hinzu, der auf gedrückt gehaltene Tasten
     * reagiert; dabei kann die <b>Tastennummer</b> und die sich
     * <b>wiederholende Aufgabe</b> angegeben werden.
     *
     * @param keyCode Die <b>Nummer der Taste</b>, durch die die Aufgaben
     *     gestartet werden. Beispielsweise kann mit dem statischen Attribut
     *     {@code KeyEvent.VK_A} die Tastennummer der Taste „A“ bestimmt werden.
     * @param repeatedTask Die <b>Aufgabe</b>, die nach einem bestimmten
     *     Zeitintervall <b>wiederholt</b> wird.
     */
    public void addListener(int keyCode, Runnable repeatedTask)
    {
        listeners.add(new PressedKeyListener(keyCode, repeatedTask));
    }

    /**
     * Fügt einen <b>Beobachter</b> hinzu, der auf gedrückt gehaltene Tasten
     * reagiert; dabei kann die <b>Tastennummer</b>, die sich <b>wiederholende
     * Aufgabe</b> und die <b>abschließende einmalige Aufgabe</b> angegeben
     * werden.
     *
     * @param keyCode Die <b>Nummer der Taste</b>, durch die die Aufgaben
     *     gestartet werden. Beispielsweise kann mit dem statischen Attribut
     *     {@code KeyEvent.VK_A} die Tastennummer der Taste „A“ bestimmt werden.
     * @param repeatedTask Die <b>Aufgabe</b>, die nach einem bestimmten
     *     Zeitintervall <b>wiederholt</b> wird.
     * @param finalTask Eine einmalige <b>Aufgabe</b>, die beim <b>Loslassen</b>
     *     der entsprechenden Taste, ausgeführt wird.
     */
    public void addListener(int keyCode, Runnable repeatedTask,
            Runnable finalTask)
    {
        listeners.add(new PressedKeyListener(keyCode, repeatedTask, finalTask));
    }

    /**
     * Fügt einen <b>Beobachter</b> hinzu, der auf gedrückt gehaltene Tasten
     * reagiert; dabei kann die <b>Tastennummer</b>, die <b>anfängliche
     * einmalige Aufgabe</b>, die sich <b>wiederholende Aufgabe</b> und
     * <b>abschließende einmalige Aufgabe</b> angegeben werden.
     *
     * @param keyCode Die <b>Nummer der Taste</b>, durch die die Aufgaben
     *     gestartet werden. Beispielsweise kann mit dem statischen Attribut
     *     {@code KeyEvent.VK_A} die Tastennummer der Taste „A“ bestimmt werden.
     * @param initialTask Eine einmalige <b>Aufgabe</b>, die <b>unmittelbar</b>
     *     ausgeführt wird, wenn die entsprechende Taste <b>gedrückt</b> wird.
     * @param repeatedTask Die <b>Aufgabe</b>, die nach einem bestimmten
     *     Zeitintervall <b>wiederholt</b> wird.
     * @param finalTask Eine einmalige <b>Aufgabe</b>, die beim <b>Loslassen</b>
     *     der entsprechenden Taste, ausgeführt wird.
     */
    public void addListener(int keyCode, Runnable initialTask,
            Runnable repeatedTask, Runnable finalTask)
    {
        listeners.add(new PressedKeyListener(keyCode, initialTask, repeatedTask,
                finalTask));
    }

    /**
     * <b>Stoppt</b> alle momentan laufenden Ausführungen der sich
     * wiederholenden Aufgabe.
     *
     * <p>
     * Die Aufgaben werden dann erst wieder ausgeführt, wenn die entsprechende
     * Taste erneut gedrückt wird.
     * </p>
     */
    public void stop()
    {
        for (PressedKeyExecutor executor : executors)
        {
            executor.stop();
        }
        executors.clear();
    }

    /**
     * @hidden
     */
    @Override
    public void onKeyDown(KeyEvent event)
    {
        for (PressedKeyListener task : listeners)
        {
            if (event.getKeyCode() == task.keyCode())
            {
                executors.add(new PressedKeyExecutor(task));
            }
        }
    }
}
