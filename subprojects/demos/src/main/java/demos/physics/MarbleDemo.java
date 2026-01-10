/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha-examples/src/main/java/ea/example/showcase/MarbleDemo.java
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
package demos.physics;

import java.awt.Color;
import java.awt.event.KeyEvent;

import pi.Controller;
import pi.Random;
import pi.Scene;
import pi.Circle;
import pi.Rectangle;
import pi.event.FrameUpdateListener;
import pi.event.KeyStrokeListener;
import pi.graphics.geom.Vector;
import pi.physics.BodyType;

/**
 * Eine kleine Demo zum Verhalten vieler Partikel ähnlicher Physik-Objekte in
 * der Engine.
 *
 * @version 11.04.2017
 *
 * @author Michael Andonie
 */
public class MarbleDemo extends Scene implements KeyStrokeListener
{
    class Funnel
    {
        /**
         * Dicke des Trichters
         */
        private static final int THICKNESS = 10;

        /**
         * Länge der schrägen Wand.
         */
        private static final int LENGTH_SLANTED = 200;

        private static final int LENGTH_VERTICAL = 120;

        private static final int NARROW_RADIUS = 50;

        public Funnel()
        {
            // slanted
            Rectangle slantedLeft = new Rectangle(THICKNESS, LENGTH_SLANTED);
            slantedLeft.position(-NARROW_RADIUS + THICKNESS * 0.25,
                    LENGTH_VERTICAL - THICKNESS * 0.75);
            Rectangle slatedRight = new Rectangle(THICKNESS, LENGTH_SLANTED);
            slatedRight.position(NARROW_RADIUS, LENGTH_VERTICAL);
            // vertical
            Rectangle verticalLeft = new Rectangle(THICKNESS, LENGTH_VERTICAL);
            verticalLeft.position(-NARROW_RADIUS, 0);
            Rectangle verticalRight = new Rectangle(THICKNESS, LENGTH_VERTICAL);
            verticalRight.position(NARROW_RADIUS, 0);
            Rectangle[] allRectangles = new Rectangle[] { slantedLeft,
                    slatedRight, verticalLeft, verticalRight };
            for (Rectangle r : allRectangles)
            {
                r.color(Color.WHITE);
                add(r);
                r.makeStatic();
            }
            gravity(new Vector(0, -30));
            slantedLeft.rotation(45);
            slatedRight.rotation(-45);
        }
    }

    /**
     * Der Boden des Trichters. Kann durchlässig gemacht werden.
     */
    private final Rectangle ground;

    public MarbleDemo()
    {
        info().title("Murmel-Demo").description(
                "Eine kleine Demo zum Verhalten vieler Partikel ähnlicher Physik-Objekte in der Engine.")
                .help("Eine beliebige Taste öffnet den Trichter");
        // Trichter
        new Funnel();
        repeat(0.2, (counter) -> {
            Circle marble = makeMarble();
            add(marble);
            marble.makeDynamic();
            marble.position(0, 500);
            marble.applyImpulse(new Vector(Random.range() * 200 - 100,
                    Random.range() * -300 - 100));
        });
        ground = new Rectangle(Funnel.NARROW_RADIUS * 2 + Funnel.THICKNESS,
                Funnel.THICKNESS);
        ground.position(-Funnel.NARROW_RADIUS, -Funnel.THICKNESS);
        ground.makeStatic();
        add(ground);
        camera().meter(0.5);
    }

    @Override
    public void onKeyDown(KeyEvent e)
    {
        if (ground.bodyType() == BodyType.STATIC)
        {
            ground.makeSensor();
            ground.color(new Color(255, 255, 255, 100));
        }
        else
        {
            ground.makeStatic();
            ground.color(Color.WHITE);
        }
    }

    /**
     * Erstellt eine neue Murmel.
     *
     * @return eine Murmel. Farbe und Größe variieren.
     */
    public Circle makeMarble()
    {
        class Marble extends Circle implements FrameUpdateListener
        {
            public Marble(float diameter)
            {
                super(diameter);
            }

            @Override
            public void onFrameUpdate(double pastTime)
            {
                if (this.center().length() > 1000)
                {
                    MarbleDemo.this.remove(this);
                }
            }
        }
        Circle marble = new Marble(Random.range(20) + 10);
        marble.makeDynamic();
        marble.gravityScale(2);
        marble.color(new Color(Random.range(255), Random.range(255),
                Random.range(255)));
        return marble;
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new MarbleDemo(), 1000, 800);
    }
}
