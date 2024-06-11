package de.pirckheimer_gymnasium.engine_pi.demos;

import java.awt.Color;
import java.awt.event.KeyEvent;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.Image;
import de.pirckheimer_gymnasium.engine_pi.event.KeyStrokeListener;

public class ScreenshotDemo extends Scene implements KeyStrokeListener
{
    public ScreenshotDemo()
    {
        Image image = new Image(
                "Pixel-Adventure-1/Main Characters/Virtual Guy/Fall (32x32).png",
                32);
        add(image);
        image.setCenter(0, 0);
        getCamera().setMeter(320);
        setBackgroundColor(Color.WHITE);
    }

    @Override
    public void onKeyDown(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
        case KeyEvent.VK_P:
            Game.takeScreenshot("screenshot.png");
            break;

        case KeyEvent.VK_J:
            Game.takeScreenshot("screenshot.jpg");
            break;

        case KeyEvent.VK_G:
            Game.takeScreenshot("screenshot.gif");
            break;

        default:
            Game.takeScreenshot("screenshot.png");
            break;
        }
    }

    public static void main(String[] args)
    {
        Game.start(new ScreenshotDemo());
    }
}
