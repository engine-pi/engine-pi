package de.pirckheimer_gymnasium.engine_pi_demos.event;

import java.awt.event.KeyEvent;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.Text;
import de.pirckheimer_gymnasium.engine_pi.event.PeriodicTask;

/**
 * Demonstriert die Methode
 * {@link de.pirckheimer_gymnasium.engine_pi.event.FrameUpdateListenerRegistration#repeat(double, Runnable)}.
 *
 * <p>
 * Im Spielfenster wird eine Zahl hochgezählt. Über die <b>Leertaste</b> kann
 * die periodische Aufgabe gestoppt oder erneut gestartet werden.
 * </p>
 */
public class RepeatDemo extends Scene
{
    public RepeatDemo()
    {
        setBackgroundColor("white");
        add(new CounterText());
    }

    private class CounterText extends Text
    {
        private int counter = 0;

        PeriodicTask task;

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
            });
        }

        public void start()
        {
            task = repeat(1, () -> {
                counter++;
                setContent(counter);
            });
        }

        public void stop()
        {
            task.unregister();
            task = null;
        }
    }

    public static void main(String[] args)
    {
        Game.start(new RepeatDemo());
    }
}
