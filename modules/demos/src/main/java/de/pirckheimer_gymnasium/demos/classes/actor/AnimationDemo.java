package de.pirckheimer_gymnasium.demos.classes.actor;

import java.awt.event.KeyEvent;

import pi.Game;
import pi.Scene;
import pi.actor.Animation;
import pi.event.KeyStrokeListener;

/**
 * Demonstiert die Klasse {@link Animation}.
 *
 * <p>
 * Wir erstellen über die statische Methode
 * {@link Animation#createFromSpritesheet(double, String, int, int, double, double)}
 * eine Figur. Über die Tasten 1-5 kann die Dauer eingetellt werden, wie lange
 * die Einzelbilder angezeigt werden.
 * </p>
 */
public class AnimationDemo extends Scene implements KeyStrokeListener
{
    Animation animation;

    public AnimationDemo()
    {
        animation = Animation.createFromSpritesheet(0.1,
                "Pixel-Adventure-1/Main Characters/Pink Man/Run (32x32).png",
                12, 1, 10.0, 10.0);
        add(animation);
        focusCenter();
    }

    @Override
    public void onKeyDown(KeyEvent event)
    {
        switch (event.getKeyCode())
        {
        case KeyEvent.VK_1:
            animation.setDuration(0.5);
            break;

        case KeyEvent.VK_2:
            animation.setDuration(0.25);
            break;

        case KeyEvent.VK_3:
            animation.setDuration(0.1);
            break;

        case KeyEvent.VK_4:
            animation.setDuration(0.05);
            break;

        case KeyEvent.VK_5:
            animation.setDuration(0.025);
            break;

        case KeyEvent.VK_S:
            animation.toggle();
            break;

        default:
            break;
        }
    }

    public static void main(String[] args)
    {
        Game.start(new AnimationDemo());
    }

}
