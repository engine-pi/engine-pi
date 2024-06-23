package de.pirckheimer_gymnasium.engine_pi_demos.event;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;

/**
 * Demonstriert die statische Methode
 * {@link de.pirckheimer_gymnasium.engine_pi.Game#addKeyStrokeListener(de.pirckheimer_gymnasium.engine_pi.event.KeyStrokeListener)}.
 */
public class GlobalKeyStrokeListenerDemo
{
    public static void main(String[] args)
    {
        Game.start(new Scene());
        Game.addKeyStrokeListener((e) -> {
            System.out.println("Tastendruck empfangen: " + e.paramString());
        });
    }
}
