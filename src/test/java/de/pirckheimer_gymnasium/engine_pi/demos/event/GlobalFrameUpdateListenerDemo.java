package de.pirckheimer_gymnasium.engine_pi.demos.event;

import java.awt.event.KeyEvent;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;

public class GlobalFrameUpdateListenerDemo
{
    public static void main(String[] args)
    {
        Game.setDebug(true);
        Game.start(400, 300, new Scene());
        Game.addKeyStrokeListener((e) -> {
            if (e.getKeyCode() == KeyEvent.VK_SPACE)
            {
                Game.addFrameUpdateListener((deltaSeconds) -> {
                    System.out.println(deltaSeconds);
                });
            }
        });
    }
}
