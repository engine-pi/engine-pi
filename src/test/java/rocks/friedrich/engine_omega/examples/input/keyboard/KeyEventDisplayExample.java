package rocks.friedrich.engine_omega.examples.input.keyboard;

import java.awt.event.KeyEvent;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.engine_omega.actor.Text;
import rocks.friedrich.engine_omega.event.KeyListener;

public class KeyEventDisplayExample extends Scene
{
    public KeyEventDisplayExample()
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
        Game.start(600, 400, new KeyEventDisplayExample());
    }
}
