package de.pirckheimer_gymnasium.engine_pi_demos.actor;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.ImageFont;
import de.pirckheimer_gymnasium.engine_pi.actor.ImageFontCaseSensitivity;
import de.pirckheimer_gymnasium.engine_pi.actor.ImageFontText;
import de.pirckheimer_gymnasium.engine_pi.util.TextAlignment;

import java.util.concurrent.atomic.AtomicInteger;

public class ImageFontTextDemo extends Scene
{
    ImageFontText helloWorld;

    ImageFontText counterText;

    public ImageFontTextDemo()
    {
        setBackgroundColor("yellow");
        ImageFont font = new ImageFont("pixel-text",
                ImageFontCaseSensitivity.TO_UPPER);
        getCamera().setMeter(8);
        helloWorld = new ImageFontText(font, "Hello, World.\nHello, Universe");
        AtomicInteger counter = new AtomicInteger();
        counterText = new ImageFontText(font, "0", 10, TextAlignment.RIGHT);
        counterText.setPosition(0, -4);
        add(helloWorld);
        repeat(0.05, () -> {
            counterText.setContent(String.valueOf(counter.getAndIncrement()));
        });
        add(counterText);
    }

    public static void main(String[] args)
    {
        Game.start(1020, 520, new ImageFontTextDemo());
        Game.setTitle("Text Example");
    }
}
