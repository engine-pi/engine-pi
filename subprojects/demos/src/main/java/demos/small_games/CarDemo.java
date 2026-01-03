/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha-examples/src/main/java/ea/example/showcase/car/CarDemo.java
 *
 * Engine Alpha ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2011 - 2020 Michael Andonie and contributors.
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
package demos.small_games;

import static pi.Random.range;
import static pi.Vector.vector;
import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Objects;

import pi.Game;
import pi.Layer;
import pi.Scene;
import pi.Vector;
import pi.actor.Actor;
import pi.actor.BodyType;
import pi.Circle;
import pi.actor.Image;
import pi.actor.Polygon;
import pi.actor.PrismaticJoint;
import pi.Rectangle;
import pi.actor.RevoluteJoint;
import pi.actor.TileRegistration;
import pi.actor.TileMap;
import pi.event.CollisionEvent;
import pi.event.FrameUpdateListener;
import pi.event.PeriodicTask;
import pi.physics.FixtureBuilder;

public class CarDemo extends Scene implements FrameUpdateListener
{
    private static final int WIDTH = 1240;

    private static final int HEIGHT = 812;

    private static final double GROUND_FRICTION = 0.6;

    private static final double GROUND_RESTITUTION = 0.3;

    private static final int MOTOR_SPEED = 80;

    private static final Color GROUND_COLOR = new Color(85, 86, 81);

    private static final int ZOOM = 60;

    private final CarBody carBody;

    private final Wheel wheelFront;

    private final Wheel wheelBack;

    public CarDemo()
    {
        info().title("Auto-Simulation").help(
                "Tastenkürzel:\nl: vorwärts waren\nj: rückwärts fahren\nLeertaste: bremsen");
        backgroundColor(new Color(207, 239, 252));
        Layer blend = new Layer();
        Rectangle blender = new Rectangle((double) WIDTH / ZOOM,
                (double) HEIGHT / ZOOM);
        blender.color(Color.BLACK);
        blend.add(blender);
        blend.parallaxRotation(0);
        blend.parallaxPosition(0, 0);
        blend.layerPosition(10);
        addLayer(blend);
        delay(.2, () -> blender.animateOpacity(.3, 0));
        Layer background = new Layer();
        background.layerPosition(-2);
        background.parallaxPosition(.5, -.025);
        Rectangle backgroundColor = new Rectangle(400, 100);
        backgroundColor.position(-200, -105);
        backgroundColor.color(new Color(0, 194, 111));
        background.add(backgroundColor);
        for (int i = -200; i < 200; i += 10)
        {
            background.add(createBackgroundTile(i));
        }
        addLayer(background);
        createGround(-70, -49).color(new Color(200, 104, 73));
        Actor left = createGround(-50, -20);
        Actor middle = createGround(-10, 70);
        Actor right = createGround(85, 170);
        createGround(169, 200).color(new Color(200, 104, 73));
        createRope(-20, -10, left, middle);
        createRope(70, 85, middle, right);
        createHill(5, range(1, 2));
        createHill(25, range(1, 2));
        createHill(45, range(1, 2));
        Layer decoration = new Layer();
        decoration.layerPosition(-1);
        var tiles = new TileRegistration(27, 1, .5);
        tiles.position(-9, -10);
        tiles.makeStatic();
        for (int i = 0; i < tiles.getTileCountX(); i++)
        {
            tiles.setTile(i, 0, TileMap.createFromImage("car/tile01.png"));
        }
        decoration.add(tiles);
        addLayer(decoration);
        carBody = new CarBody(0, -8f);
        wheelFront = new Wheel(1.36, -8.75, new Axle(1.36, -8.6, carBody));
        wheelBack = new Wheel(-1, -8.75, new Axle(-1, -8.6, carBody));
        // Wheels automatically add axes, and axes add the car body
        add(wheelFront, wheelBack);
        gravity(vector(0, -9.81));
        camera().meter(ZOOM);
        focus(carBody);
        camera().offset(vector(0, 3));
    }

    private Actor createBackgroundTile(int x)
    {
        Image image = new Image("car/background-color-grass.png", 10, 10);
        image.position(x, -7);
        image.makeStatic();
        return image;
    }

    private void createRope(int startX, int endX, Actor left, Actor right)
    {
        int length = (endX - startX);
        RopeSegment[] rope = new RopeSegment[length];
        for (int i = 0; i < length; i++)
        {
            rope[i] = new RopeSegment(.8, 0.2);
            rope[i].position(startX + i + 0.1, -10.2);
            rope[i].color(new Color(175, 90, 30));
            rope[i].makeDynamic();
            rope[i].density(150);
            rope[i].friction(GROUND_FRICTION);
            rope[i].elasticity(GROUND_RESTITUTION);
            rope[i].borderRadius(.5);
            if (i == 0)
            {
                rope[0].createRevoluteJoint(left, vector(-.1, .2)).setLimits(0,
                        0.1);
            }
            else
            {
                if (i == length - 1)
                {
                    rope[length - 1].createRevoluteJoint(right, vector(.9, .2))
                            .setLimits(0, 0.1);
                }
                rope[i - 1].createRevoluteJoint(rope[i], vector(.9, .2))
                        .setLimits(0, 0.1);
            }
        }
        add(rope);
    }

    private Ground createGround(double startX, double endX)
    {
        Ground ground = new Ground(startX, endX);
        add(ground);
        return ground;
    }

    private void createHill(double x, double height)
    {
        double offset = 180;
        for (int j = 0; j < 40 - 1; j += 1)
        {
            Polygon ground = new HillSegment(
                    vector(x + j / 2f, -10), vector(x + j / 2f
                            + 1, -10),
                    vector(x + (j + 1) / 2f,
                            -10 + Math
                                    .cos(Math.toRadians(
                                            ((j + 1) / 2f) * 18 + offset))
                                    * height + height),
                    vector(x + j / 2f,
                            -10 + Math.cos(Math.toRadians(j / 2f * 18 + offset))
                                    * height + height));
            ground.moveBy(0, -0.01);
            ground.makeStatic();
            ground.color(GROUND_COLOR);
            ground.friction(GROUND_FRICTION);
            ground.elasticity(GROUND_RESTITUTION);
            ground.density(50);
            add(ground);
        }
    }

    @Override
    public void onFrameUpdate(double pastTime)
    {
        boolean left = Game.isKeyPressed(KeyEvent.VK_J);
        boolean right = Game.isKeyPressed(KeyEvent.VK_L);
        // Antriebssteuerung
        if (left ^ right)
        {
            wheelFront.setMotorSpeed(right ? MOTOR_SPEED : -MOTOR_SPEED);
            wheelBack.setMotorSpeed(right ? MOTOR_SPEED : -MOTOR_SPEED);
        }
        else if (Game.isKeyPressed(KeyEvent.VK_SPACE))
        {
            wheelFront.setMotorSpeed(0);
            wheelBack.setMotorSpeed(0);
        }
        else
        {
            wheelFront.setMotorEnabled(false);
            wheelBack.setMotorEnabled(false);
        }
        if (carBody.center().x() < -65)
        {
            carBody.applyForce(vector(10000 * (-65 - carBody.center().x()), 0),
                    carBody.center());
        }
        else if (carBody.center().x() > 195)
        {
            carBody.applyForce(vector(10000 * (195 - carBody.center().x()), 0),
                    carBody.center());
        }
        if (carBody.center().y() < -20)
        {
            Game.transitionToScene(new CarDemo());
        }
    }

    private static Actor createParticle(double size, Vector center,
            Color initialColor, Vector impulse)
    {
        Circle particle = new Circle(size);
        particle.bodyType(BodyType.PARTICLE);
        particle.layerPosition(2);
        particle.color(initialColor);
        particle.center(center);
        particle.animateParticle(range(.1, 3f));
        particle.animateColor(range(.3, .6), Color.BLACK);
        particle.applyImpulse(impulse);
        particle.gravityScale(1);
        particle.linearDamping(range(18, 22));
        particle.layerPosition(-1);
        return particle;
    }

    private static Actor createSplitter(Vector center)
    {
        Polygon splitter = new Polygon(vector(0, 0), vector(0.15, 0),
                vector(0.15, 0.05));
        splitter.bodyType(BodyType.PARTICLE);
        splitter.rotateBy(range(0, 360));
        splitter.layerPosition(2);
        splitter.color(new Color(119, 82, 54));
        splitter.center(center.add(range(-.2, .2), range(-.2, .2)));
        splitter.animateParticle(range(.1, 3f));
        splitter.gravityScale(1);
        splitter.linearDamping(range(18, 22));
        splitter.layerPosition(-1);
        return splitter;
    }

    private static PeriodicTask createSplitterEmitter(Actor actor)
    {
        return (counter) -> {
            for (CollisionEvent<Actor> collision : actor.collisions())
            {
                if (collision.colliding() instanceof Wood
                        && actor.velocity().length() > 1)
                {
                    for (Vector point : collision.points())
                    {
                        Objects.requireNonNull(actor.layer())
                                .add(createSplitter(point));
                    }
                }
            }
        };
    }

    private static class Ground extends Rectangle implements Mud
    {
        public Ground(double startX, double endX)
        {
            super(endX - startX, 10);
            position(startX, -20);
            color(GROUND_COLOR);
            makeStatic();
            friction(GROUND_FRICTION);
            elasticity(GROUND_RESTITUTION);
            density(150);
            borderRadius(.1);
        }
    }

    private static class HillSegment extends Polygon implements Mud
    {
        public HillSegment(Vector... vectors)
        {
            super(vectors);
        }
    }

    private static class Axle extends Rectangle implements FrameUpdateListener
    {
        private final PrismaticJoint spring;

        private final CarBody carBody;

        public Axle(double cx, double cy, CarBody carBody)
        {
            super(.2, .9);
            center(cx, cy);
            makeDynamic();
            color(new Color(255, 255, 255, 0));
            density(50);
            this.carBody = carBody;
            spring = createPrismaticJoint(carBody,
                    centerRelative().add(0, height() / 2), 90);
            spring.limits(-.15, .15);
            addMountListener(
                    () -> Objects.requireNonNull(layer()).add(carBody));
        }

        @Override
        public void onFrameUpdate(double pastTime)
        {
            // Federeffekt für die Achsen
            double translation = spring.translation();
            spring.motorSpeed(Math
                    .sin(min(max(-0.15, translation), 0.15) / .15 * Math.PI / 2)
                    * -.3);
            spring.maximumMotorForce(5000);
        }

        public CarBody getCarBody()
        {
            return carBody;
        }
    }

    private static class Wheel extends Image
    {
        private final RevoluteJoint motor;

        public Wheel(double cx, double cy, Axle axle)
        {
            super("car/wheel-back.png", 1.4, 1.4);
            fixture(() -> FixtureBuilder.circle(.7, .7, .7));
            center(cx, cy);
            density(100);
            makeDynamic();
            friction(.5);
            elasticity(.2);
            density(150);
            angularDamping(1);
            layerPosition(2);
            motor = createRevoluteJoint(axle, centerRelative());
            motor.setMaximumMotorTorque(5000);
            addMountListener(() -> Objects.requireNonNull(layer()).add(axle));
            addCollisionListener(axle.getCarBody(),
                    CollisionEvent::ignoreCollision);
            repeat(.025, (counter) -> {
                for (CollisionEvent<Actor> collision : collisions())
                {
                    if (collision.colliding() instanceof Mud)
                    {
                        double velocity = velocity().length();
                        double overTwist = abs(
                                angularVelocity() * Math.PI * 2 * 0.7)
                                / velocity;
                        boolean slowMoving = abs(velocity().x()) < 0.5
                                && abs(angularVelocity()) < 0.3;
                        if (overTwist > 0.95 && overTwist < 1.05 || slowMoving)
                        {
                            continue;
                        }
                        Vector impulse = collision.tangentNormal() //
                                .rotate(90) //
                                .multiply(min(max(-1, overTwist - 1), 1));
                        for (Vector point : collision.points())
                        {
                            double size = range(0.05, .15);
                            Vector center = point.add(
                                    point.distance(center()).multiply(size));
                            Color color = ((Mud) collision.colliding()).color();
                            Objects.requireNonNull(layer())
                                    .add(createParticle(size, center, color,
                                            impulse.rotate(range(-15, 15))));
                        }
                    }
                }
            });
            repeat(.25, createSplitterEmitter(this));
        }

        public void setMotorSpeed(int speed)
        {
            motor.setMotorSpeed(speed);
        }

        public void setMotorEnabled(boolean enabled)
        {
            motor.setMotorEnabled(enabled);
        }
    }

    private static class CarBody extends Image
    {
        public CarBody(double cx, double cy)
        {
            super("car/truck-240px.png", 4, 1.2);
            center(cx, cy);
            makeDynamic();
            density(100);
            angularDamping(0.3);
            friction(0.5);
            fixtures(
                    "R0,.45,2,.45&P2,1.2,2.6,1.15,3.8,0.8,3.95,0.45,2,0.45&R1,0,2,0.6");
            repeat(.05, (counter) -> {
                if (velocity().length() < 0.1)
                {
                    return;
                }
                for (CollisionEvent<Actor> collision : collisions())
                {
                    if (collision.colliding() instanceof Mud)
                    {
                        for (Vector point : collision.points())
                        {
                            double size = range(0.05, .15);
                            Vector impulse = vector(range(-1f, 1f),
                                    range(-1f, 1f));
                            Objects.requireNonNull(layer()).add(createParticle(
                                    size, point, Color.YELLOW, impulse));
                        }
                    }
                }
            });
            repeat(.25, createSplitterEmitter(this));
        }
    }

    private static class RopeSegment extends Rectangle implements Wood
    {
        public RopeSegment(double width, double height)
        {
            super(width, height);
        }
    }

    private interface Mud
    {
        Color color();
    }

    private interface Wood
    {
        // marker
    }

    public static void main(String[] args)
    {
        Game.instantMode(false);
        Game.start(new CarDemo(), WIDTH, HEIGHT);
    }
}
