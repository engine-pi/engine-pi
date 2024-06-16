package de.pirckheimer_gymnasium.engine_pi.event;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import de.pirckheimer_gymnasium.engine_pi.Game;

/**
 * Führt bei <b>gedrückter Taste</b> mehrmals die gleiche Aufgabe in einem
 * bestimmten zeitlichen Abstand aus.
 *
 * @author Josef Friedrich
 */
public class PressedKeyRepeater implements KeyStrokeListener
{
    private final List<KeyAction> keyActions;

    private final List<Executor> executors;

    /**
     * In Sekunden
     */
    private double defaultInterval = 0.03;

    /**
     * In Sekunden
     */
    private double defaultInitialInterval = 0.15;

    /**
     * Führt die einzelnen Aufgaben einer Tastenaktion aus.
     */
    private class Executor implements FrameUpdateListener, KeyStrokeListener
    {
        private double countdown;

        private final KeyAction action;

        public Executor(KeyAction action)
        {
            this.action = action;
            countdown = action.getInitialInterval();
            Game.addKeyStrokeListener(this);
            Game.addFrameUpdateListener(this);
            action.runInitialTask();
            action.runRepeatedTask();
        }

        @Override
        public void onFrameUpdate(double deltaSeconds)
        {
            countdown -= deltaSeconds;
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
     * Eine Aktion ordnet einer bestimmten Taste eine oder mehrere Aufgaben zu,
     * die bei gedrückter Taste ausgeführt werden.
     */
    private class KeyAction
    {
        /**
         * Der Code der Taste, durch die die Aufgaben gestartet werden.
         *
         * @see java.awt.event.KeyEvent
         */
        private final int keyCode;

        /**
         * Die Aufgabe, die <b>unmittelbar</b> ausgeführt wird, wenn die
         * entsprechende Taste <b>gedrückt</b> wird.
         */
        private Runnable initialTask;

        /**
         * Die Aufgabe, die in einem bestimmten Zeitintervall <b>wiederholt</b>
         * wird.
         */
        private final Runnable repeatedTask;

        /**
         * Die Aufgabe, die beim <b>Loslassen</b> der entsprechenden Taste,
         * ausgeführt wird.
         */
        private Runnable finalTask;

        /**
         * Die zeitliche Verzögerung (in Sekunden) zwischen dem ersten
         * Tastendruck und der ersten Wiederholung der Aufgabe.
         */
        private double initialInterval;

        /**
         * In Sekunden
         */
        private double interval;

        public KeyAction(int keyCode, Runnable reatedTask, double interval,
                double initialInterval)
        {
            this.keyCode = keyCode;
            this.interval = interval;
            this.repeatedTask = reatedTask;
            this.initialInterval = initialInterval;
        }

        public KeyAction(int keyCode, Runnable runnable)
        {
            this.keyCode = keyCode;
            this.repeatedTask = runnable;
        }

        public KeyAction(int keyCode, Runnable repeatedTask, Runnable finalTask)
        {
            this.keyCode = keyCode;
            this.repeatedTask = repeatedTask;
            this.finalTask = finalTask;
        }

        public KeyAction(int keyCode, Runnable initialTask,
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
                return defaultInterval;
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
                return defaultInitialInterval;
            }
            return initialInterval;
        }
    }

    public PressedKeyRepeater(double interval, double initialInterval)
    {
        if (interval > 0)
        {
            defaultInterval = interval;
        }
        if (initialInterval > 0)
        {
            defaultInitialInterval = initialInterval;
        }
        keyActions = new ArrayList<>();
        executors = new ArrayList<>();
        Game.addKeyStrokeListener(this);
    }

    public PressedKeyRepeater()
    {
        this(0, 0);
    }

    public void addTask(int keyCode, Runnable repeatedTask, double interval,
            double initialInterval)
    {
        keyActions.add(new KeyAction(keyCode, repeatedTask, interval,
                initialInterval));
    }

    public void addTask(int keyCode, Runnable repeatedTask)
    {
        keyActions.add(new KeyAction(keyCode, repeatedTask));
    }

    public void addTask(int keyCode, Runnable repeatedTask, Runnable finalTask)
    {
        keyActions.add(new KeyAction(keyCode, repeatedTask, finalTask));
    }

    public void addTask(int keyCode, Runnable initialTask,
            Runnable repeatedTask, Runnable finalTask)
    {
        keyActions.add(
                new KeyAction(keyCode, initialTask, repeatedTask, finalTask));
    }

    /**
     * Stoppt alle Ausführungen.
     */
    public void stop()
    {
        for (Executor executor : executors)
        {
            executor.stop();
        }
        executors.clear();
    }

    @Override
    public void onKeyDown(KeyEvent e)
    {
        for (KeyAction task : keyActions)
        {
            if (e.getKeyCode() == task.getKeyCode())
            {
                executors.add(new Executor(task));
            }
        }
    }
}
