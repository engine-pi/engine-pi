/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha-examples/src/main/java/ea/example/basics/MinimalDemo.java
 *
 * Engine Alpha ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2011 - 2024 Michael Andonie and contributors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package demos;

import java.awt.Color;
import java.awt.event.KeyEvent;

import pi.Game;
import pi.Scene;
import pi.actor.Animation;
import pi.Rectangle;
import pi.actor.StatefulAnimation;
import pi.Text;
import pi.event.FrameUpdateListener;
import pi.event.KeyStrokeListener;
import pi.event.MouseScrollEvent;
import pi.event.MouseScrollListener;

public class MinimalDemo extends Scene
        implements KeyStrokeListener, MouseScrollListener, FrameUpdateListener
{
    private static final int WIDTH = 1020;

    private static final int HEIGHT = 520;

    /**
     * Eine einfache Demo
     */
    private final Rectangle rectangle;

    private final StatefulAnimation<String> character;

    public MinimalDemo()
    {
        rectangle = new Rectangle(50, 100);
        rectangle.setColor(Color.BLUE);
        add(rectangle);
        Animation animation = Animation.createFromAnimatedGif(
                "game-assets/jump/fx_explosion_b_anim.gif", 1, 1);
        animation.setPosition(200, 200);
        // animation.setOneTimeOnly();
        add(animation);
        Text text = new Text("Hallo!", 2);
        text.setPosition(-100, -100);
        text.setColor(Color.MAGENTA);
        add(text);
        // Stateful Animation
        // "leerer Automat" → Erstellt StatefulAnimation ohne Zustände
        character = new StatefulAnimation<>(64, 64);
        final String pathbase = "game-assets/dude/char/spr_m_traveler_";
        Animation idle = Animation
                .createFromAnimatedGif(pathbase + "idle_anim.gif", 1, 1);
        character.addState("idle", idle);
        character.addState("walking", Animation
                .createFromAnimatedGif(pathbase + "walk_anim.gif", 1, 1));
        character.addState("running", Animation
                .createFromAnimatedGif(pathbase + "run_anim.gif", 1, 1));
        character.addState("jumpingUp", Animation
                .createFromAnimatedGif(pathbase + "jump_1up_anim.gif", 1, 1));
        character.addState("midair", Animation.createFromAnimatedGif(
                pathbase + "jump_2midair_anim.gif", 1, 1));
        character.addState("falling", Animation
                .createFromAnimatedGif(pathbase + "jump_3down_anim.gif", 1, 1));
        character.addState("landing", Animation
                .createFromAnimatedGif(pathbase + "jump_4land_anim.gif", 1, 1));
        character.setStateTransition("landing", "idle");
        add(character);
    }

    @Override
    public void onKeyDown(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
        case KeyEvent.VK_RIGHT -> rectangle.moveBy(50, 0);
        case KeyEvent.VK_LEFT -> rectangle.moveBy(-50, 0);
        case KeyEvent.VK_C -> character.setState("midair");
        }
    }

    @Override
    public void onMouseScrollMove(MouseScrollEvent event)
    {
        double newZoom = getCamera().meter()
                + (event.getPreciseWheelRotation() * -0.2);
        if (newZoom > 0)
        {
            getCamera().meter(newZoom);
        }
    }

    @Override
    public void onFrameUpdate(double pastTime)
    {
        // Die Geschwindigkeit, in der sich die Kamera bewegt (pro Sekunde)
        float camSpeed = 600;
        if (Game.isKeyPressed(KeyEvent.VK_W))
        {
            // W ist gedrückt → Kamera nach oben bewegen.
            getCamera().moveBy(0, camSpeed * pastTime);
        }
        if (Game.isKeyPressed(KeyEvent.VK_S))
        {
            getCamera().moveBy(0, -camSpeed * pastTime);
        }
        if (Game.isKeyPressed(KeyEvent.VK_A))
        {
            getCamera().moveBy(-camSpeed * pastTime, 0);
        }
        if (Game.isKeyPressed(KeyEvent.VK_D))
        {
            getCamera().moveBy(camSpeed * pastTime, 0);
        }
    }

    /**
     * Main-Methode startet die Demo.
     *
     * @param args command-line params (irrelevant)
     */
    public static void main(String[] args)
    {
        Game.debug();
        Game.start(new MinimalDemo(), WIDTH, HEIGHT);
        Game.setTitle("Minimale Demo");
    }
}
