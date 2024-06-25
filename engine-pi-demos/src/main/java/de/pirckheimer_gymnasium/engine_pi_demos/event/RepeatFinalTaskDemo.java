package de.pirckheimer_gymnasium.engine_pi_demos.event;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;

/**
 * Demonstriert die Methode
 * {@link de.pirckheimer_gymnasium.engine_pi.event.FrameUpdateListenerRegistration#repeat(double, int, de.pirckheimer_gymnasium.engine_pi.event.PeriodicTask, de.pirckheimer_gymnasium.engine_pi.event.PeriodicTask)}.
 *
 * <p>
 * In der Konsole erscheint viermal {@code task} und einmal {@code final task}
 * </p>
 */
public class RepeatFinalTaskDemo
{
    public static void main(String[] args)
    {
        Game.start(new Scene()
        {
            {
                repeat(0.2, 5, (counter) -> System.out.println("task"),
                        (counter) -> System.out.println("final task"));
            }
        });
    }
}
