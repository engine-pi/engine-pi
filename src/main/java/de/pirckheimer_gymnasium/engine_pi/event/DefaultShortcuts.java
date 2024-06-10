package de.pirckheimer_gymnasium.engine_pi.event;

import java.awt.event.KeyEvent;

import de.pirckheimer_gymnasium.engine_pi.Game;

/**
 * Registriert einige Tastenkürzel, die standardmäßig mit der Engine
 * mitgeliefert werden (z. B. ESC zum Schließen des Fensters, STRG+D zum An- und
 * Ausschalten des Debug-Modus).
 */
public class DefaultShortcuts implements KeyListener
{
    @Override
    public void onKeyDown(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
        case KeyEvent.VK_D -> {
            if (Game.isKeyPressed(KeyEvent.VK_CONTROL))
            {
                Game.toggleDebug();
            }
        }
        case KeyEvent.VK_ESCAPE -> {
            System.out.println("Exit");
            Game.exit();
        }
        }
    }
}
