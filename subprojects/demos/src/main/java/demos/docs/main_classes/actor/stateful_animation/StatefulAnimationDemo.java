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
package demos.docs.main_classes.actor.stateful_animation;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/docs/manual/main-classes/actor/stateful-animation.md

import java.awt.Color;
import java.util.ArrayList;

import pi.Controller;
import pi.Scene;
import pi.Circle;
import pi.Rectangle;
import pi.physics.FixtureBuilder;
import pi.physics.FixtureData;

public class StatefulAnimationDemo extends Scene
{
    public StatefulAnimationDemo()
    {
        StatefulPlayerCharacter character = new StatefulPlayerCharacter();
        setupGround();
        add(character);
        focus(character);
        gravityOfEarth();
    }

    private void setupGround()
    {
        Rectangle ground = makePlatform(200, 0.2);
        ground.center(0, -5);
        ground.color(new Color(255, 195, 150));
        makePlatform(12, 0.3).center(16, -1);
        makePlatform(7, 0.3).center(25, 2);
        makePlatform(20, 0.3).center(35, 6);
        makeBall(5).center(15, 3);
    }

    private Rectangle makePlatform(double w, double h)
    {
        Rectangle r = new Rectangle(w, h);
        r.color(new Color(0, 194, 255));
        r.makeStatic();
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
        r.fixtures(() -> platformFixtures);
        add(r);
        return r;
    }

    private Circle makeBall(double d)
    {
        Circle circle = new Circle(d);
        circle.makeDynamic();
        add(circle);
        return circle;
    }

    public static void main(String[] args)
    {
        Controller.start(new StatefulAnimationDemo(), 1200, 820);
    }
}
