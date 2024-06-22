package de.pirckheimer_gymnasium.engine_pi_demos.event;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;

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
