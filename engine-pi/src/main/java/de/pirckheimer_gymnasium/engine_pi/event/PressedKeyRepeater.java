package de.pirckheimer_gymnasium.engine_pi.event;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import de.pirckheimer_gymnasium.engine_pi.Game;

/**
 * Führt bei <b>gedrückter Taste mehrmals</b> die <b>gleiche Aufgabe</b> in
 * einem <b>bestimmten zeitlichen Abstand</b> aus.
 *
 * <p>
 * Foldender Code-Ausschnitt registriert zwei Beobachter, nämlich einen für die
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
 */
public class PressedKeyRepeater implements KeyStrokeListener
{
    private final List<PressedKeyListener> listeners;

    private final List<PressedKeyExecutor> executors;

    /**
     * Der Standardwert für das <b>Zeitintervall</b> (in Sekunden) nach dem die
     * Aufgabe <b>wiederholt</b> wird.
     */
    private double DEFAULT_INTERVAL = 0.03;

    /**
     * Der Standardwert für die zeitliche Verzögerung (in Sekunden) zwischen dem
     * <b>ersten Tastendruck</b> und der <b>ersten Wiederholung</b> der Aufgabe.
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
            countdown = action.getInitialInterval();
            Game.addKeyStrokeListener(this);
            Game.addFrameUpdateListener(this);
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
                countdown = action.getInterval();
            }
        }

        public void onKeyDown(KeyEvent e)
        {
            // Do nothing
        }

        public void stop()
        {
            Game.removeFrameUpdateListener(this);
            Game.removeKeyStrokeListener(this);
            action.runFinalTask();
        }

        @Override
        public void onKeyUp(KeyEvent e)
        {
            if (e.getKeyCode() == action.getKeyCode())
            {
                stop();
                executors.remove(this);
            }
        }
    }

    /**
     * Ein Beobachter, der auf länger gedrückte Tasten reagiert und dann in
     * einem bestimmten Zeitintervall eine Aufgaben mehrmals wiederholt.
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
         * Die zeitliche Verzögerung (in Sekunden) zwischen dem <b>ersten
         * Tastendruck</b> und der <b>ersten Wiederholung</b> der Aufgabe.
         */
        private double initialInterval;

        /**
         * Eine einmalige Aufgabe, die <b>unmittelbar</b> ausgeführt wird, wenn
         * die entsprechende Taste <b>gedrückt</b> wird.
         */
        private Runnable initialTask;

        /**
         * Die Aufgabe, die nach einem bestimmten Zeitintervall
         * <b>wiederholt</b> wird.
         */
        private final Runnable repeatedTask;

        /**
         * Eine einmalige Aufgabe, die beim <b>Loslassen</b> der entsprechenden
         * Taste, ausgeführt wird.
         */
        private Runnable finalTask;

        /**
         * Erzeugt einen <b>Beobachter</b>, der auf gedrückt gehaltene Tasten
         * reagiert.
         *
         * <p>
         * Dabei kann die <b>Tastennummer</b>, die sich <b>wiederholende
         * Aufgabe</b>, das <b>Zeitintervall der Wiederholung</b> und
         * <b>anfängliche zeitliche Verzögerung</b> angegeben werden.
         * </p>
         *
         *
         * @param keyCode         Die <b>Nummer der Taste</b>, durch die die
         *                        Aufgaben gestartet werden. Beispielsweise kann
         *                        mit dem statischen Attribut
         *                        {@code KeyEvent.VK_A} die Tastennummer der
         *                        Taste „A“ bestimmt werden.
         * @param repeatedTask    Die Aufgabe, die nach einem bestimmten
         *                        Zeitintervall <b>wiederholt</b> wird.
         * @param interval        Das <b>Zeitintervall</b> (in Sekunden) nach
         *                        dem die Aufgabe <b>wiederholt</b> wird.
         * @param initialInterval Die zeitliche Verzögerung (in Sekunden)
         *                        zwischen dem <b>ersten Tastendruck</b> und der
         *                        <b>ersten Wiederholung</b> der Aufgabe.
         */
        public PressedKeyListener(int keyCode, Runnable reatedTask,
                double interval, double initialInterval)
        {
            this.keyCode = keyCode;
            this.interval = interval;
            this.repeatedTask = reatedTask;
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
         * @param keyCode      Die <b>Nummer der Taste</b>, durch die die
         *                     Aufgaben gestartet werden. Beispielsweise kann
         *                     mit dem statischen Attribut {@code KeyEvent.VK_A}
         *                     die Tastennummer der Taste „A“ bestimmt werden.
         * @param repeatedTask Die Aufgabe, die nach einem bestimmten
         *                     Zeitintervall <b>wiederholt</b> wird.
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
         * @param keyCode      Die <b>Nummer der Taste</b>, durch die die
         *                     Aufgaben gestartet werden. Beispielsweise kann
         *                     mit dem statischen Attribut {@code KeyEvent.VK_A}
         *                     die Tastennummer der Taste „A“ bestimmt werden.
         * @param repeatedTask Die Aufgabe, die nach einem bestimmten
         *                     Zeitintervall <b>wiederholt</b> wird.
         * @param finalTask    Eine einmalige Aufgabe, die beim <b>Loslassen</b>
         *                     der entsprechenden Taste, ausgeführt wird.
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
         * @param keyCode      Die <b>Nummer der Taste</b>, durch die die
         *                     Aufgaben gestartet werden. Beispielsweise kann
         *                     mit dem statischen Attribut {@code KeyEvent.VK_A}
         *                     die Tastennummer der Taste „A“ bestimmt werden.
         * @param initialTask  Eine einmalige Aufgabe, die <b>unmittelbar</b>
         *                     ausgeführt wird, wenn die entsprechende Taste
         *                     <b>gedrückt</b> wird.
         * @param repeatedTask Die Aufgabe, die nach einem bestimmten
         *                     Zeitintervall <b>wiederholt</b> wird.
         * @param finalTask    Eine einmalige Aufgabe, die beim <b>Loslassen</b>
         *                     der entsprechenden Taste, ausgeführt wird.
         */
        public PressedKeyListener(int keyCode, Runnable initialTask,
                Runnable repeatedTask, Runnable finalTask)
        {
            this.keyCode = keyCode;
            this.initialTask = initialTask;
            this.repeatedTask = repeatedTask;
            this.finalTask = finalTask;
        }

        public int getKeyCode()
        {
            return keyCode;
        }

        public double getInterval()
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

        public double getInitialInterval()
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
     * Wiederholung</b> und der <b>anfänglichen zeitlichen Verzögerung</b>.
     *
     * @param interval        Das <b>Zeitintervall</b> (in Sekunden) nach dem
     *                        die Aufgabe <b>wiederholt</b> wird.
     * @param initialInterval Die zeitliche Verzögerung (in Sekunden) zwischen
     *                        dem <b>ersten Tastendruck</b> und der <b>ersten
     *                        Wiederholung</b> der Aufgabe.
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
        Game.addKeyStrokeListener(this);
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
     * zeitliche Verzögerung</b> angegeben werden.
     *
     * @param keyCode         Die <b>Nummer der Taste</b>, durch die die
     *                        Aufgaben gestartet werden. Beispielsweise kann mit
     *                        dem statischen Attribut {@code KeyEvent.VK_A} die
     *                        Tastennummer der Taste „A“ bestimmt werden.
     * @param repeatedTask    Die Aufgabe, die nach einem bestimmten
     *                        Zeitintervall <b>wiederholt</b> wird.
     * @param interval        Das <b>Zeitintervall</b> (in Sekunden) nach dem
     *                        die Aufgabe <b>wiederholt</b> wird.
     * @param initialInterval Die zeitliche Verzögerung (in Sekunden) zwischen
     *                        dem <b>ersten Tastendruck</b> und der <b>ersten
     *                        Wiederholung</b> der Aufgabe.
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
     * @param keyCode      Die <b>Nummer der Taste</b>, durch die die Aufgaben
     *                     gestartet werden. Beispielsweise kann mit dem
     *                     statischen Attribut {@code KeyEvent.VK_A} die
     *                     Tastennummer der Taste „A“ bestimmt werden.
     * @param repeatedTask Die Aufgabe, die nach einem bestimmten Zeitintervall
     *                     <b>wiederholt</b> wird.
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
     * @param keyCode      Die <b>Nummer der Taste</b>, durch die die Aufgaben
     *                     gestartet werden. Beispielsweise kann mit dem
     *                     statischen Attribut {@code KeyEvent.VK_A} die
     *                     Tastennummer der Taste „A“ bestimmt werden.
     * @param repeatedTask Die Aufgabe, die nach einem bestimmten Zeitintervall
     *                     <b>wiederholt</b> wird.
     * @param finalTask    Eine einmalige Aufgabe, die beim <b>Loslassen</b> der
     *                     entsprechenden Taste, ausgeführt wird.
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
     * @param keyCode      Die <b>Nummer der Taste</b>, durch die die Aufgaben
     *                     gestartet werden. Beispielsweise kann mit dem
     *                     statischen Attribut {@code KeyEvent.VK_A} die
     *                     Tastennummer der Taste „A“ bestimmt werden.
     * @param initialTask  Eine einmalige Aufgabe, die <b>unmittelbar</b>
     *                     ausgeführt wird, wenn die entsprechende Taste
     *                     <b>gedrückt</b> wird.
     * @param repeatedTask Die Aufgabe, die nach einem bestimmten Zeitintervall
     *                     <b>wiederholt</b> wird.
     * @param finalTask    Eine einmalige Aufgabe, die beim <b>Loslassen</b> der
     *                     entsprechenden Taste, ausgeführt wird.
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
            if (event.getKeyCode() == task.getKeyCode())
            {
                executors.add(new PressedKeyExecutor(task));
            }
        }
    }
}
