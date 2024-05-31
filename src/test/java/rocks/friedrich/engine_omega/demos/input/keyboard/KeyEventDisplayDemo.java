package rocks.friedrich.engine_omega.demos.input.keyboard;

import java.awt.event.KeyEvent;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.engine_omega.actor.Text;
import rocks.friedrich.engine_omega.event.KeyListener;

public class KeyEventDisplayDemo extends Scene
{
    public KeyEventDisplayDemo()
    {
        add(new KeyText());
    }

    private class KeyText extends Text implements KeyListener
    {
        public KeyText()
        {
            super("Press a key", 1);
            setCenter(0, 0);
        }

        @Override
        public void onKeyDown(KeyEvent keyEvent)
        {
            String text = KeyEvent.getKeyText(keyEvent.getKeyCode());
            text = text.replace(" ", "_");
            text = text.toUpperCase();
            setContent("VK_" + text);
            setCenter(0, 0);
        }
    }

    public static void main(String[] args)
    {
        Game.start(new KeyEventDisplayDemo());
    }
}
