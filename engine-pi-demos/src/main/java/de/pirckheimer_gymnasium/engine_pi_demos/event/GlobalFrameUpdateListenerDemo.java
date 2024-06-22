package de.pirckheimer_gymnasium.engine_pi_demos.event;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;

public class GlobalFrameUpdateListenerDemo
{
    public static void main(String[] args)
    {
        Game.start(new Scene());
        Game.addFrameUpdateListener((delta) -> System.out
                .println("Vergangene Zeit seit letztem Einzelbild: " + delta));
    }
}
