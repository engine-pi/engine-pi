/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha-examples/src/main/java/ea/example/showcase/Particles.java
 *
 * Engine Alpha ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2011 - 2018 Michael Andonie and contributors.
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
package demos.physics;

import java.awt.Color;
import java.awt.event.KeyEvent;

import pi.Controller;
import pi.Scene;
import pi.Circle;
import pi.Rectangle;
import pi.animation.AnimationMode;
import pi.animation.ValueAnimator;
import pi.animation.interpolation.ReverseEaseDouble;
import pi.event.FrameUpdateListener;
import pi.event.KeyStrokeListener;
import pi.graphics.geom.Vector;

public class ParticlesDemo extends Scene implements KeyStrokeListener
{
    private static final int WIDTH = 1240;

    private static final int HEIGHT = 812;

    /**
     * Startet ein Sandbox-Fenster.
     */
    public ParticlesDemo()
    {
        Rectangle left = new Rectangle(200, 10);
        left.anchor(-WIDTH / 6.0 - 150, -50);
        left.rotateBy(-21);
        left.makeStatic();
        left.color(Color.white);
        left.restitution(15f);
        add(left);
        Rectangle right = new Rectangle(200, 10);
        right.anchor(WIDTH / 6.0, 0);
        right.rotateBy(45);
        right.makeStatic();
        right.color(Color.white);
        right.restitution(15);
        add(right);
        addKeyStrokeListener(this);
        repeat(1, (counter) -> createCircle(mousePosition(), Color.YELLOW));
        Rectangle r1 = new Rectangle(WIDTH, 10);
        r1.anchor(-WIDTH / 2.0, -HEIGHT / 2.0);
        Rectangle r2 = new Rectangle(10, HEIGHT);
        r2.anchor(-WIDTH / 2.0, -HEIGHT / 2.0);
        Rectangle r3 = new Rectangle(WIDTH, 10);
        r3.anchor(-WIDTH / 2.0, HEIGHT / 2.0 - 10);
        Rectangle r4 = new Rectangle(10, HEIGHT);
        r4.anchor(WIDTH / 2.0 - 10, -HEIGHT / 2.0);
        add(r1, r2, r3, r4);
        r1.makeStatic();
        r2.makeStatic();
        r3.makeStatic();
        r4.makeStatic();
        r1.color(Color.DARK_GRAY);
        r2.color(Color.DARK_GRAY);
        r3.color(Color.DARK_GRAY);
        r4.color(Color.DARK_GRAY);
        r1.addCollisionListener((event) -> remove(event.colliding()));
        gravity(new Vector(0, -600));
        camera().meter(1);
        left.animateColor(5, Color.YELLOW);
        this.addFrameUpdateListener(new ValueAnimator<>(5, left::x,
                new ReverseEaseDouble(left.x(), left.x() + 200),
                AnimationMode.REPEATED, this));
    }

    private void createCircle(Vector position, Color color)
    {
        Circle circle = new Circle(6);
        FrameUpdateListener emitter = repeat(0.01, (counter) -> {
            Circle particle = new Circle(3);
            particle.anchor(circle.center().subtract(new Vector(1, 1)));
            particle.color(Color.RED);
            particle.layerPosition(-1);
            particle.animateParticle(.5);
            particle.animateColor(.25, Color.YELLOW);
            particle
                .applyImpulse(new Vector(6000 * ((float) Math.random() - .5),
                        6000 * ((float) Math.random() - .5)));
            add(particle);
        });
        circle.anchor(position);
        circle.makeDynamic();
        circle.color(color);
        circle.addFrameUpdateListener(emitter);
        add(circle);
    }

    @Override
    public void onKeyDown(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_LESS)
        {
            camera().rotateBy(0.1);
        }
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.debug();
        Controller.start(new ParticlesDemo(), WIDTH, HEIGHT);
    }
}
