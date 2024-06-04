/*
 * Source: https://github.com/engine-alpha/tutorials/blob/master/src/eatutorials/statefulanimation/StatefulAnimationTestScene.java
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
package rocks.friedrich.engine_omega.tutorials.statefulanimation;

import java.awt.Color;
import java.util.ArrayList;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.engine_omega.Vector;
import rocks.friedrich.engine_omega.actor.BodyType;
import rocks.friedrich.engine_omega.actor.Circle;
import rocks.friedrich.engine_omega.actor.Rectangle;
import rocks.friedrich.engine_omega.physics.FixtureBuilder;
import rocks.friedrich.engine_omega.physics.FixtureData;

public class StatefulAnimationDemo extends Scene
{
    public StatefulAnimationDemo()
    {
        StatefulPlayerCharacter character = new StatefulPlayerCharacter();
        setupGround();
        add(character);
        getCamera().setFocus(character);
        setGravity(new Vector(0, -9.81));
    }

    private void setupGround()
    {
        Rectangle ground = makePlatform(200, 0.2);
        ground.setCenter(0, -5);
        ground.setColor(new Color(255, 195, 150));
        makePlatform(12, 0.3).setCenter(16, -1);
        makePlatform(7, 0.3).setCenter(25, 2);
        makePlatform(20, 0.3).setCenter(35, 6);
        makeBall(5).setCenter(15, 3);
    }

    private Rectangle makePlatform(double w, double h)
    {
        Rectangle r = new Rectangle(w, h);
        r.setColor(new Color(0, 194, 255));
        r.setBodyType(BodyType.STATIC);
        ArrayList<FixtureData> platformFixtures = new ArrayList<>();
        FixtureData top = new FixtureData(FixtureBuilder
                .axisParallelRectangular(.1, h - 0.1, w - .2, 0.1));
        top.setFriction(.2);
        top.setRestitution(0);
        FixtureData left = new FixtureData(
                FixtureBuilder.axisParallelRectangular(0, 0, 0.1, h));
        left.setFriction(0);
        left.setRestitution(0);
        FixtureData right = new FixtureData(
                FixtureBuilder.axisParallelRectangular(w - .1, 0, 0.1, h));
        right.setFriction(0);
        right.setRestitution(0);
        FixtureData bottom = new FixtureData(
                FixtureBuilder.axisParallelRectangular(0, 0, w, .1));
        bottom.setFriction(0);
        bottom.setRestitution(0);
        platformFixtures.add(top);
        platformFixtures.add(left);
        platformFixtures.add(right);
        platformFixtures.add(bottom);
        r.setFixtures(() -> platformFixtures);
        add(r);
        return r;
    }

    private Circle makeBall(double d)
    {
        Circle circle = new Circle(d);
        circle.setBodyType(BodyType.DYNAMIC);
        add(circle);
        return circle;
    }

    public static void main(String[] args)
    {
        Game.start(1200, 820, new StatefulAnimationDemo());
        Game.setDebug(true);
    }
}
