package rocks.friedrich.engine_omega.demos.event;

import java.awt.event.KeyEvent;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.engine_omega.event.KeyListener;
import rocks.friedrich.engine_omega.event.PressedKeyRepeater;

public class PressedKeyRepeaterDemo extends Scene implements KeyListener
{
    PressedKeyRepeater keyRepeater;

    public PressedKeyRepeaterDemo()
    {
        keyRepeater = new PressedKeyRepeater();
        keyRepeater.addTask(KeyEvent.VK_RIGHT, () -> {
            System.out.println("right");
        });
        keyRepeater.addTask(KeyEvent.VK_LEFT,
                () -> System.out.println("left initial"),
                () -> System.out.println("left"),
                () -> System.out.println("left final"));
    }

    public void onKeyDown(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            System.out.println("stop");
            keyRepeater.stop();
        }
    }

    public static void main(String[] args)
    {
        Game.start(400, 300, new PressedKeyRepeaterDemo());
    }
}
