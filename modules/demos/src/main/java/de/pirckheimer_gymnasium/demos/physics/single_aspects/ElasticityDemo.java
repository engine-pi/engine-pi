/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2024 Josef Friedrich and contributors.
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
package de.pirckheimer_gymnasium.demos.physics.single_aspects;

import java.text.DecimalFormat;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.Circle;
import de.pirckheimer_gymnasium.engine_pi.actor.Rectangle;
import de.pirckheimer_gymnasium.engine_pi.actor.Text;

/**
 * Demonstriert die Methode
 * {@link de.pirckheimer_gymnasium.engine_pi.actor.Actor#setElasticity(double)}
 */
public class ElasticityDemo extends Scene
{
    private final Rectangle ground;

    public ElasticityDemo()
    {
        getCamera().setMeter(20);
        // Ein Reckteck als Boden, auf dem die Kreise abprallen.
        ground = new Rectangle(24, 1);
        ground.setPosition(-12, -16);
        // Wir setzen die Elastizität auf 0, damit beim ersten Kreis mit der
        // Stoßzahl 0 demonstriert werden kann, dass dieser nicht abprallt.
        ground.setElasticity(0);
        ground.makeStatic();
        setGravity(0, -9.81);
        add(ground);
        double elasticity = 0;
        for (double x = -11.5; x < 12; x += 2)
        {
            createCircleWithElasticity(x, elasticity);
            elasticity += 0.1;
        }
    }

    private void createCircleWithElasticity(double x, double elasticity)
    {
        Circle circle = new Circle(1);
        add(circle);
        circle.setElasticity(elasticity);
        circle.setPosition(x, 5);
        circle.makeDynamic();
        // Eine Beschriftung mit der Stoßzahl unterhalb des Kollisionsrechtecks
        DecimalFormat df = new DecimalFormat("0.00");
        Text label = new Text(df.format(elasticity), 0.8);
        label.setPosition(x, -17);
        label.makeStatic();
        add(label);
    }

    public static void main(String[] args)
    {
        Game.start(new ElasticityDemo(), 600, 800);
    }
}
