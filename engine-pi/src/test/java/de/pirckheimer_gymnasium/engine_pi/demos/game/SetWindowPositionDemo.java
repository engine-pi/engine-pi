package de.pirckheimer_gymnasium.engine_pi.demos.game;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.Vector;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Demonstriert die Methode
 * {@link de.pirckheimer_gymnasium.engine_pi.Game#setWindowPosition(int, int)}
 */
public class SetWindowPositionDemo
{
    public static void main(String[] args)
    {
        Game.start(new Scene());
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        Vector vector = Game.getWindowSize();
        int windowWidth = (int) vector.getX();
        int windowHeight = (int) vector.getY();
        Game.addKeyStrokeListener((event) -> {
            switch (event.getKeyCode())
            {
            case KeyEvent.VK_1 ->
            {
                // links oben
                Game.setWindowPosition(0, 0);
            }
            case KeyEvent.VK_2 ->
            {
                // rechts oben
                Game.setWindowPosition(screenWidth - windowWidth, 0);
            }
            case KeyEvent.VK_3 ->
            {
                // links unten
                Game.setWindowPosition(0, screenHeight - windowHeight);
            }
            case KeyEvent.VK_4 ->
            {
                // rechts unten
                Game.setWindowPosition(screenWidth - windowWidth,
                        screenHeight - windowHeight);
            }
            case KeyEvent.VK_5 ->
            {
                // zentrieren
                Game.setWindowPosition((screenWidth - windowWidth) / 2,
                        (screenHeight - windowHeight) / 2);
            }
            }
        });
    }
}
