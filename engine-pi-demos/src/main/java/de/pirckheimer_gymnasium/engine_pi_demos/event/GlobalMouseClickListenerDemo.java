package de.pirckheimer_gymnasium.engine_pi_demos.event;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;

/**
 * Demonstriert die statische Methode
 * {@link de.pirckheimer_gymnasium.engine_pi.Game#addMouseClickListener(de.pirckheimer_gymnasium.engine_pi.event.MouseClickListener)}.
 */
public class GlobalMouseClickListenerDemo
{
    public static void main(String[] args)
    {
        Game.start(new Scene());
        Game.addMouseClickListener((vector, button) -> {
            System.err.println("x: %s, y: %s, button %s"
                    .formatted(vector.getX(), vector.getY(), button));
        });
    }
}
