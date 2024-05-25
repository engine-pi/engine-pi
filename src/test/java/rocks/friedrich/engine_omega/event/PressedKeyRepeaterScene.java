package rocks.friedrich.engine_omega.event;

import java.awt.event.KeyEvent;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.Scene;

public class PressedKeyRepeaterScene extends Scene
{
    PressedKeyRepeater keyRepeater;

    public PressedKeyRepeaterScene()
    {
        keyRepeater = new PressedKeyRepeater();
        keyRepeater.addTask(KeyEvent.VK_RIGHT, () -> {
            System.out.println("right");
        });
        keyRepeater.addTask(KeyEvent.VK_LEFT, () -> {
            System.out.println("left");
        });
    }

    public static void main(String[] args)
    {
        Game.start(400, 300, new PressedKeyRepeaterScene());
    }
}
