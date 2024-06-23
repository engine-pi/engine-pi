package de.pirckheimer_gymnasium.engine_pi_demos.event;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;

/**
 * Demonstriert die statische Methode
 * {@link de.pirckheimer_gymnasium.engine_pi.Game#addMouseScrollListener(de.pirckheimer_gymnasium.engine_pi.event.MouseScrollListener)}.
 */
public class GlobalMouseScrollListenerDemo
{
    public static void main(String[] args)
    {
        Game.start(new Scene());
        Game.addMouseScrollListener((event) -> {
            System.err.println("precise rotation: %s, rotation: %s".formatted(
                    event.getPreciseWheelRotation(), event.getWheelRotation()));
        });
    }
}
