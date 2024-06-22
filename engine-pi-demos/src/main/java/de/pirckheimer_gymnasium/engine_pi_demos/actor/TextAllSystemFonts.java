package de.pirckheimer_gymnasium.engine_pi_demos.actor;

import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.Text;
import de.pirckheimer_gymnasium.engine_pi.event.KeyStrokeListener;

public class TextAllSystemFonts extends Scene implements KeyStrokeListener
{
    public TextAllSystemFonts()
    {
        Text überschrift = new Text("Alle System-Schriftarten", 2f);
        überschrift.setPosition(-12, 3);
        überschrift.setColor("black");
        add(überschrift);
        loadFonts();
        setBackgroundColor("white");
    }

    private void loadFonts()
    {
        GraphicsEnvironment ge = GraphicsEnvironment
                .getLocalGraphicsEnvironment();
        String[] systemFonts = ge.getAvailableFontFamilyNames();
        for (int i = 0; i < systemFonts.length; i++)
        {
            String fontName = systemFonts[i];
            Text text = new Text(fontName, 1, fontName);
            text.setPosition(-12, -1 * i);
            text.setColor("black");
            add(text);
        }
    }

    @Override
    public void onKeyDown(KeyEvent keyEvent)
    {
        if (keyEvent.getKeyCode() == KeyEvent.VK_UP)
        {
            getCamera().moveBy(0, 5);
        }
        else if (keyEvent.getKeyCode() == KeyEvent.VK_DOWN)
        {
            getCamera().moveBy(0, -5);
        }
    }

    public static void main(String[] args)
    {
        Game.start(1020, 520, new TextAllSystemFonts());
        Game.setTitle("Text Example");
    }
}
