package rocks.friedrich.engine_omega.demos.event;

import java.awt.event.KeyEvent;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.Scene;

public class GlobalFrameUpdateListenerDemo
{
    public static void main(String[] args)
    {
        Game.setDebug(true);
        Game.start(400, 300, new Scene());
        Game.addKeyListener((e) -> {
            if (e.getKeyCode() == KeyEvent.VK_SPACE)
            {
                Game.addFrameUpdateListener((deltaSeconds) -> {
                    System.out.println(deltaSeconds);
                });
            }
        });
    }
}
