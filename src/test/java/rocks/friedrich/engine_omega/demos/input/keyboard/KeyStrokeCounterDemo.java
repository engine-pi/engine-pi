package rocks.friedrich.engine_omega.demos.input.keyboard;

import java.awt.event.KeyEvent;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.engine_omega.actor.Text;
import rocks.friedrich.engine_omega.event.KeyListener;

public class KeyStrokeCounterDemo extends Scene
{
    public KeyStrokeCounterDemo()
    {
        add(new CounterText());
    }

    private class CounterText extends Text implements KeyListener
    {
        private int counter = 0;

        public CounterText()
        {
            super("You pressed 0 keys.", 2);
            setCenter(0, 0);
        }

        @Override
        public void onKeyDown(KeyEvent keyEvent)
        {
            counter++;
            setContent("You pressed " + counter + " keys.");
            setCenter(0, 0);
        }
    }

    public static void main(String[] args)
    {
        Game.start(700, 200, new KeyStrokeCounterDemo());
    }
}
