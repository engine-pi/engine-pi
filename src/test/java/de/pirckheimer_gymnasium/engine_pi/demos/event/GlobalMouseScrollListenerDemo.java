package de.pirckheimer_gymnasium.engine_pi.demos.event;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;

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
