/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha/src/main/java/ea/internal/physics/PhysicsData.java
 *
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
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
package pi.physics;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import de.pirckheimer_gymnasium.jbox2d.common.Vec2;
import de.pirckheimer_gymnasium.jbox2d.dynamics.Body;
import de.pirckheimer_gymnasium.jbox2d.dynamics.BodyDef;
import de.pirckheimer_gymnasium.jbox2d.dynamics.Fixture;
import de.pirckheimer_gymnasium.jbox2d.dynamics.FixtureDef;
import pi.actor.Actor;
import pi.annotations.Getter;
import pi.annotations.Internal;
import pi.annotations.Setter;
import pi.graphics.geom.Vector;

/**
 * Diese Klasse wrappt die wesentlichen physikalischen Eigenschaften eines
 * {@link Actor}-Objekts.
 *
 * @hidden
 */
@Internal
public class PhysicsData
{
    static final double DEFAULT_DENSITY = 10;

    static final double DEFAULT_FRICTION = 0;

    static final double DEFAULT_RESTITUTION = 0.5;

    static final BodyType DEFAULT_BODY_TYPE = BodyType.SENSOR;

    private boolean rotationLocked = false;

    private double x = 0;

    private double y = 0;

    private double rotation = 0;

    private double globalDensity = DEFAULT_DENSITY;

    private double globalFriction = DEFAULT_FRICTION;

    private double globalRestitution = DEFAULT_RESTITUTION;

    private double torque = 0;

    private double angularVelocity = 0;

    private double gravityScale = 1;

    private double linearDamping = 0;

    private double angularDamping = 0;

    private Double mass;

    private Vector velocity = Vector.NULL;

    private BodyType type = DEFAULT_BODY_TYPE;

    private Supplier<List<FixtureData>> fixtures;

    /**
     * Erstellt ein Proxydatenset basierend auf einem JBox2D-Body
     *
     * @param body Der zu kopierende Körper.
     */
    public static PhysicsData fromBody(Body body, BodyType type)
    {
        PhysicsData data = new PhysicsData(extractFixturesFromBody(body));
        // Global Fixture Vals are blindly taken from first Fixture
        data.globalDensity(body.fixtureList.density);
        data.globalFriction(body.fixtureList.friction);
        data.globalRestitution(body.fixtureList.restitution);
        data.rotationLocked(body.isFixedRotation());
        data.gravityScale(body.gravityScale);
        data.x(body.getPosition().x);
        data.y(body.getPosition().y);
        data.rotation(Math.toDegrees(body.getAngle()));
        data.torque(body.torque);
        data.setVelocity(Vector.of(body.linearVelocity));
        data.angularVelocity(Math.toDegrees(body.angularVelocity) / 360);
        data.bodyType(type);
        data.angularDamping(body.getAngularDamping());
        data.linearDamping(body.getLinearDamping());
        return data;
    }

    public static Supplier<List<FixtureData>> extractFixturesFromBody(Body body)
    {
        final ArrayList<FixtureData> fixtureData = new ArrayList<>();
        for (Fixture fixture = body.fixtureList; fixture != null; fixture = fixture.next)
        {
            fixtureData.add(FixtureData.fromFixture(fixture));
        }
        return () -> fixtureData;
    }

    /**
     * Default-Konstruktor erstellt ein Proxydatenset mit Standardwerten.
     *
     * @param fixtures Eine Funktion, die eine gut abschätzende Shape für das
     *     zugehörige {@link Actor}-Objekt berechnet.
     */
    public PhysicsData(Supplier<List<FixtureData>> fixtures)
    {
        fixtures(fixtures);
    }

    /**
     * Erstellt Fixture-Definitions für alle Shapes des Actors.
     */
    public FixtureDef[] createFixtureDefs()
    {
        List<FixtureDef> fixtureDefs = new ArrayList<>();
        List<FixtureData> fixtureList = this.fixtures().get();
        for (FixtureData fixtureData : fixtureList)
        {
            fixtureDefs.add(fixtureData.createFixtureDef(this));
        }
        return fixtureDefs.toArray(new FixtureDef[0]);
    }

    /**
     * Erstellt eine Body-Definition für den Actor
     */
    public BodyDef createBodyDef()
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.angle = (float) Math.toRadians(this.rotation());
        bodyDef.position.set(new Vec2((float) x(), (float) y()));
        bodyDef.fixedRotation = isRotationLocked();
        bodyDef.linearVelocity = velocity().toVec2();
        bodyDef.angularVelocity = (float) Math
            .toRadians(angularVelocity() * 360);
        bodyDef.type = bodyType().toBox2D();
        bodyDef.active = true;
        bodyDef.gravityScale = (float) gravityScale;
        bodyDef.angularDamping = (float) angularDamping;
        bodyDef.linearDamping = (float) linearDamping;
        return bodyDef;
    }

    /**
     * Erstellt einen vollständigen Body basierend auf allen ProxyDaten.
     * <b>Macht keine Prüfungen, ob ein entsprechender Body bereits gebaut
     * wurde.</b>
     *
     * @param world Der World-Handler, in dessen World der Body erstellt werden
     *     soll.
     *
     * @return Der frisch erstellte Body nach allen Spezifikationen der
     *     Proxy-Daten.
     */
    Body createBody(WorldHandler world, Actor actor)
    {
        Body body = world.createBody(createBodyDef(), actor);
        for (FixtureDef fixtureDef : createFixtureDefs())
        {
            body.createFixture(fixtureDef);
        }
        return body;
    }

    /**
     * @hidden
     */
    @Internal
    public FixtureData[] generateFixtureData()
    {
        List<FixtureData> data = fixtures.get();
        return data.toArray(new FixtureData[data.size()]);
    }

    @Setter
    public void mass(Double mass)
    {
        this.mass = mass;
    }

    @Getter
    public Double mass()
    {
        return mass;
    }

    public boolean isRotationLocked()
    {
        return rotationLocked;
    }

    @Setter
    public void rotationLocked(boolean rotationLocked)
    {
        this.rotationLocked = rotationLocked;
    }

    @Getter
    public double x()
    {
        return x;
    }

    @Setter
    public void x(double x)
    {
        this.x = x;
    }

    @Getter
    public double y()
    {
        return y;
    }

    @Setter
    public void y(double y)
    {
        this.y = y;
    }

    @Getter
    public double rotation()
    {
        return rotation;
    }

    @Setter
    public void rotation(double rotation)
    {
        this.rotation = rotation;
    }

    @Getter
    public double linearDamping()
    {
        return linearDamping;
    }

    @Setter
    public void linearDamping(double linearDamping)
    {
        this.linearDamping = linearDamping;
    }

    @Getter
    public double angularDamping()
    {
        return angularDamping;
    }

    @Setter
    public void angularDamping(double angularDamping)
    {
        this.angularDamping = angularDamping;
    }

    @Getter
    public double globalDensity()
    {
        return globalDensity;
    }

    public void globalDensity(double globalDensity)
    {
        this.globalDensity = globalDensity;
    }

    @Getter
    public double gravityScale()
    {
        return gravityScale;
    }

    @Setter
    public void gravityScale(double factor)
    {
        this.gravityScale = factor;
    }

    @Getter
    public double globalFriction()
    {
        return globalFriction;
    }

    public void globalFriction(double globalFriction)
    {
        this.globalFriction = globalFriction;
    }

    @Getter
    public double globalRestitution()
    {
        return globalRestitution;
    }

    @Setter
    public void globalRestitution(double globalRestitution)
    {
        this.globalRestitution = globalRestitution;
    }

    @Getter
    public double torque()
    {
        return torque;
    }

    @Setter
    public void torque(double torque)
    {
        this.torque = torque;
    }

    @Getter
    public Vector velocity()
    {
        return velocity;
    }

    public void setVelocity(Vector velocity)
    {
        this.velocity = velocity;
    }

    @Getter
    public BodyType bodyType()
    {
        return type;
    }

    @Setter
    public void bodyType(BodyType type)
    {
        this.type = type;
    }

    @Getter
    public Supplier<List<FixtureData>> fixtures()
    {
        return fixtures;
    }

    @Setter
    public void fixtures(Supplier<List<FixtureData>> fixtures)
    {
        this.fixtures = fixtures;
    }

    @Getter
    public double angularVelocity()
    {
        return angularVelocity;
    }

    @Setter
    public void angularVelocity(double angularVelocity)
    {
        this.angularVelocity = angularVelocity;
    }
}
