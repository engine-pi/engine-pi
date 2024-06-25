package de.pirckheimer_gymnasium.engine_pi_demos.event;

import java.awt.event.KeyEvent;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.Text;
import de.pirckheimer_gymnasium.engine_pi.event.PeriodicTaskExecutor;

/**
 * Demonstriert die Klasse
 * {@link de.pirckheimer_gymnasium.engine_pi.event.PeriodicTaskExecutor}.
 *
 * <p>
 * Im Spielfenster wird eine Zahl hochgezählt. Über die Taste <b>P</b> kann die
 * periodische Aufgabe pausiert oder fortgesetzt werden. Über die
 * <b>Leertaste</b> kann die periodische Aufgabe gestoppt, was jedoch mit einer
 * <b>Fehlermeldung</b> (so gewollt) fehlschlägt.
 * </p>
 */
public class PeriodicTaskDemo extends Scene
{
    private PeriodicTaskExecutor task;

    public PeriodicTaskDemo()
    {
        setBackgroundColor("white");
        add(new CounterText());
    }

    private class CounterText extends Text
    {
        public CounterText()
        {
            super("0", 2);
            setCenter(0, 0);
            start();
            addKeyStrokeListener((e) -> {
                if (e.getKeyCode() == KeyEvent.VK_SPACE)
                {
                    if (task == null)
                    {
                        start();
                    }
                    else
                    {
                        stop();
                    }
                }
                else if (e.getKeyCode() == KeyEvent.VK_P)
                {
                    if (task.isPaused)
                    {
                        task.resume();
                    }
                    else
                    {
                        task.pause();
                    }
                }
            });
        }

        public void start()
        {
            task = new PeriodicTaskExecutor(0.1, (counter) -> {
                setContent(counter);
            });
            addFrameUpdateListener(task);
        }

        public void stop()
        {
            task.unregister();
            task = null;
        }
    }

    public static void main(String[] args)
    {
        Game.start(400, 200, new PeriodicTaskDemo());
    }
}
