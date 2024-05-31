package rocks.friedrich.engine_omega.examples.event;

import java.awt.event.KeyEvent;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.engine_omega.actor.Text;
import rocks.friedrich.engine_omega.event.PeriodicTask;

public class PerodicTaskExample extends Scene
{
    private PeriodicTask task;

    public PerodicTaskExample()
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
            addKeyListener((e) -> {
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
        Game.start(400, 200, new PerodicTaskExample());
    }
}
