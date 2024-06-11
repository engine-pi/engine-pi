package de.pirckheimer_gymnasium.engine_pi.demos.event;

import java.awt.event.KeyEvent;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.Text;
import de.pirckheimer_gymnasium.engine_pi.event.PeriodicTask;

public class PerodicTaskDemo extends Scene
{
    private PeriodicTask task;

    public PerodicTaskDemo()
    {
        add(new CounterText());
    }

    private class CounterText extends Text
    {
        private int counter = 0;

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
            task = new PeriodicTask(0.1, () -> {
                counter++;
                setContent(String.valueOf(counter));
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
        Game.start(400, 200, new PerodicTaskDemo());
    }
}
