package de.pirckheimer_gymnasium.engine_pi.demos.game;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;

import java.awt.event.KeyEvent;

/**
 * Demonstriert die Methode {@link Game#setWindowPosition(int, int)}
 */
public class SetWindowSizeDemo
{
    public static void main(String[] args)
    {
        Game.start(new Scene());
        Game.addKeyStrokeListener((event) -> {
            switch (event.getKeyCode())
            {
            case KeyEvent.VK_1 ->
            {
                Game.setWindowSize(600, 400);
            }
            case KeyEvent.VK_2 ->
            {
                Game.setWindowSize(300, 200);
            }
            }
        });
    }
}
