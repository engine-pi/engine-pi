package de.pirckheimer_gymnasium.engine_pi_demos.event;

import java.awt.event.KeyEvent;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.event.KeyStrokeListener;
import de.pirckheimer_gymnasium.engine_pi.event.PressedKeyRepeater;

/**
 * Demonstriert die Klasse
 * {@link de.pirckheimer_gymnasium.engine_pi.event.PressedKeyRepeater}.
 */
public class PressedKeyRepeaterDemo extends Scene implements KeyStrokeListener
{
    PressedKeyRepeater repeater;

    public PressedKeyRepeaterDemo()
    {
        repeater = new PressedKeyRepeater(0.5, 1);
        repeater.addListener(KeyEvent.VK_RIGHT, () -> {
            System.out.println("right");
        });
        repeater.addListener(KeyEvent.VK_LEFT,
                () -> System.out.println("left initial"),
                () -> System.out.println("left"),
                () -> System.out.println("left final"));
    }

    public void onKeyDown(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            System.out.println("stop");
            repeater.stop();
        }
    }

    public static void main(String[] args)
    {
        Game.start(400, 300, new PressedKeyRepeaterDemo());
    }
}
