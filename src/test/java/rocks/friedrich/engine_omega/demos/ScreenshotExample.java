package rocks.friedrich.engine_omega.examples;

import java.awt.Color;
import java.awt.event.KeyEvent;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.engine_omega.actor.Image;
import rocks.friedrich.engine_omega.event.KeyListener;

public class ScreenshotExample extends Scene implements KeyListener
{
    public ScreenshotExample()
    {
        Image image = new Image(
                "Pixel-Adventure-1/Main Characters/Virtual Guy/Fall (32x32).png",
                32);
        add(image);
        image.setCenter(0, 0);
        getCamera().setZoom(320);
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
        Game.start(600, 400, new ScreenshotExample());
    }
}
