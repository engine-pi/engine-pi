/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha/src/main/java/ea/internal/physics/BodyHandler.java
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
package de.pirckheimer_gymnasium.engine_pi.physics;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import de.pirckheimer_gymnasium.jbox2d.collision.AABB;
import de.pirckheimer_gymnasium.jbox2d.common.Vec2;
import de.pirckheimer_gymnasium.jbox2d.dynamics.Body;
import de.pirckheimer_gymnasium.jbox2d.dynamics.Fixture;
import de.pirckheimer_gymnasium.jbox2d.dynamics.contacts.ContactEdge;

import de.pirckheimer_gymnasium.engine_pi.Vector;
import de.pirckheimer_gymnasium.engine_pi.actor.Actor;
import de.pirckheimer_gymnasium.engine_pi.actor.BodyType;
import de.pirckheimer_gymnasium.engine_pi.annotations.Internal;
import de.pirckheimer_gymnasium.engine_pi.event.CollisionEvent;

/**
 * Ein <code>Body-Handler</code> kümmert sich um die <i>physikalische
 * Darstellung</i> eines {@link Actor}-Objekts.<br>
 * Er übernimmt zwei wesentliche Aufgaben:
 * <ul>
 * <li>Die Kontrolle und Steuerung innerhalb der <b>Physics-Engine</b> aus Sicht
 * des respektiven {@link Actor}-Objekts.</li>
 * <li>Die Speicherung der <i>räumlichen Eigenschaften</i> (Position und
 * Rotation) des respektiven {@link Actor}-Objekts.</li>
 * </ul>
 */
public class BodyHandler implements PhysicsHandler
{
    private static final Vec2 NULL_VECTOR = new Vec2();

    private static final int DEFAULT_MASK_BITS = 0xFFFF;

    /**
     * Referenz auf den Handler der World, in der sich der Body befindet.
     */
    private final WorldHandler worldHandler;

    /**
     * Der starre Körper (rigid body) als die physische Repräsentation des
     * analogen {@link Actor}-Objekts in der Physics-Engine.
     */
    private final Body body;

    private BodyType type;

    /**
     * Erstellt einen neuen Body-Handler
     *
     * @hidden
     */
    @Internal
    public BodyHandler(Actor actor, PhysicsData physicsData,
            WorldHandler worldHandler)
    {
        this.worldHandler = worldHandler;
        this.body = physicsData.createBody(worldHandler, actor);
        setType(physicsData.getType());
    }

    public Body getBody()
    {
        return body;
    }

    @Override
    public void moveBy(Vector meters)
    {
        synchronized (worldHandler)
        {
            worldHandler.assertNoWorldStep();
            Vec2 vector = meters.toVec2();
            body.setTransform(vector.addLocal(body.getPosition()),
                    body.getAngle());
            // Wake up body, ensures in-engine (JB2D) adjustments will happen,
            // e.g. collision rejustment
            body.setAwake(true);
        }
    }

    @Override
    public Vector getCenter()
    {
        if (type == BodyType.DYNAMIC || type == BodyType.PARTICLE)
        {
            return Vector.of(body.getWorldCenter());
        }
        return Vector.of(calculateBodyAABB().getCenter());
    }

    @Override
    public boolean contains(Vector vector)
    {
        Vec2 point = vector.toVec2();
        for (Fixture fixture = body.fixtureList; fixture != null; fixture = fixture.next)
        {
            if (fixture.testPoint(point))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public Vector getPosition()
    {
        return Vector.of(body.getPosition());
    }

    @Override
    public double getRotation()
    {
        return (double) Math.toDegrees(body.getAngle());
    }

    @Override
    public void rotateBy(double degree)
    {
        synchronized (worldHandler)
        {
            worldHandler.assertNoWorldStep();
            body.setTransform(body.getPosition(),
                    (float) (body.getAngle() + Math.toRadians(degree)));
        }
    }

    @Override
    public void setRotation(double degree)
    {
        synchronized (worldHandler)
        {
            worldHandler.assertNoWorldStep();
            body.setTransform(body.getPosition(),
                    (float) Math.toRadians(degree));
        }
    }

    @Override
    public void setDensity(double density)
    {
        synchronized (worldHandler)
        {
            for (Fixture fixture = body.fixtureList; fixture != null; fixture = fixture.next)
            {
                fixture.setDensity((float) density);
            }
            body.resetMassData();
        }
    }

    @Override
    public double getDensity()
    {
        return body.fixtureList.getDensity();
    }

    @Override
    public void setGravityScale(double factor)
    {
        body.setGravityScale((float) factor);
        body.setAwake(true);
    }

    @Override
    public double getGravityScale()
    {
        return body.getGravityScale();
    }

    @Override
    public void setFriction(double friction)
    {
        synchronized (worldHandler)
        {
            for (Fixture fixture = body.fixtureList; fixture != null; fixture = fixture.next)
            {
                fixture.setFriction((float) friction);
            }
        }
    }

    @Override
    public double getFriction()
    {
        return body.fixtureList.getFriction();
    }

    @Override
    public void setRestitution(double elasticity)
    {
        synchronized (worldHandler)
        {
            for (Fixture fixture = body.fixtureList; fixture != null; fixture = fixture.next)
            {
                fixture.setRestitution((float) elasticity);
            }
        }
    }

    @Override
    public double getRestitution()
    {
        return body.fixtureList.getRestitution();
    }

    @Override
    public void setLinearDamping(double damping)
    {
        synchronized (worldHandler)
        {
            body.setLinearDamping((float) damping);
        }
    }

    @Override
    public double getLinearDamping()
    {
        return body.getLinearDamping();
    }

    @Override
    public void setAngularDamping(double damping)
    {
        synchronized (worldHandler)
        {
            body.setAngularDamping((float) damping);
        }
    }

    @Override
    public double getAngularDamping()
    {
        return body.getAngularDamping();
    }

    @Override
    public double getMass()
    {
        return body.getMass();
    }

    @Override
    public void applyForce(Vector force)
    {
        synchronized (worldHandler)
        {
            body.applyForceToCenter(force.toVec2());
        }
    }

    @Override
    public void applyTorque(double torque)
    {
        synchronized (worldHandler)
        {
            body.applyTorque((float) torque);
        }
    }

    @Override
    public void applyRotationImpulse(double rotationImpulse)
    {
        synchronized (worldHandler)
        {
            body.applyAngularImpulse((float) rotationImpulse);
        }
    }

    @Override
    public void setType(BodyType type)
    {
        synchronized (worldHandler)
        {
            worldHandler.assertNoWorldStep();
            if (type == this.type)
            {
                return;
            }
            this.type = type;
            body.setType(type.toBox2D());
            body.setActive(true);
            body.setAwake(true);
            for (Fixture fixture = body.fixtureList; fixture != null; fixture = fixture.next)
            {
                fixture.isSensor = type.isSensor();
                switch (type)
                {
                case SENSOR:
                    fixture.filter.categoryBits = WorldHandler.CATEGORY_PASSIVE;
                    fixture.filter.maskBits = DEFAULT_MASK_BITS
                            & ~WorldHandler.CATEGORY_PARTICLE;
                    break;

                case STATIC:
                    fixture.filter.categoryBits = WorldHandler.CATEGORY_STATIC;
                    fixture.filter.maskBits = DEFAULT_MASK_BITS;
                    break;

                case KINEMATIC:
                    fixture.filter.categoryBits = WorldHandler.CATEGORY_KINEMATIC;
                    fixture.filter.maskBits = DEFAULT_MASK_BITS;
                    break;

                case DYNAMIC:
                    fixture.filter.categoryBits = WorldHandler.CATEGORY_DYNAMIC;
                    fixture.filter.maskBits = DEFAULT_MASK_BITS
                            & ~WorldHandler.CATEGORY_PARTICLE;
                    break;

                case PARTICLE:
                    fixture.filter.categoryBits = WorldHandler.CATEGORY_PARTICLE;
                    fixture.filter.maskBits = WorldHandler.CATEGORY_STATIC
                            | WorldHandler.CATEGORY_KINEMATIC;
                    break;

                default:
                    throw new RuntimeException("Unknown body type: " + type);
                }
            }
        }
    }

    @Override
    public BodyType getType()
    {
        return type;
    }

    @Override
    public void applyForce(Vector forceInN, Vector globalLocation)
    {
        synchronized (worldHandler)
        {
            body.applyForce(forceInN.toVec2(), globalLocation.toVec2());
        }
    }

    @Override
    public void applyImpulse(Vector impulseInNs, Vector globalLocation)
    {
        synchronized (worldHandler)
        {
            body.applyLinearImpulse(impulseInNs.toVec2(),
                    globalLocation.toVec2(), true);
        }
    }

    @Override
    public void resetMovement()
    {
        synchronized (worldHandler)
        {
            body.setLinearVelocity(NULL_VECTOR);
            body.setAngularVelocity(0);
        }
    }

    @Override
    public void setVelocity(Vector metersPerSecond)
    {
        synchronized (worldHandler)
        {
            body.setLinearVelocity(metersPerSecond.toVec2());
        }
    }

    @Override
    public Vector getVelocity()
    {
        return Vector.of(body.getLinearVelocity());
    }

    @Override
    public void setAngularVelocity(double rotationsPerSecond)
    {
        synchronized (worldHandler)
        {
            body.setAngularVelocity(
                    (float) Math.toRadians(rotationsPerSecond * 360));
        }
    }

    @Override
    public double getAngularVelocity()
    {
        return (double) Math.toDegrees(body.getAngularVelocity()) / 360;
    }

    @Override
    public void setRotationLocked(boolean locked)
    {
        synchronized (worldHandler)
        {
            body.setFixedRotation(locked);
        }
    }

    @Override
    public boolean isRotationLocked()
    {
        return body.isFixedRotation();
    }

    private AABB calculateBodyAABB()
    {
        AABB bodyBounds = new AABB();
        bodyBounds.lowerBound.x = Float.MAX_VALUE;
        bodyBounds.lowerBound.y = Float.MAX_VALUE;
        bodyBounds.upperBound.x = -Float.MAX_VALUE;
        bodyBounds.upperBound.y = -Float.MAX_VALUE;
        for (Fixture fixture = body.fixtureList; fixture != null; fixture = fixture.next)
        {
            // TODO Include chain shapes (more than one child)
            bodyBounds.combine(bodyBounds, fixture.getAABB(0));
        }
        return bodyBounds;
    }

    @Override
    public boolean isGrounded()
    {
        if (this.getType() != BodyType.DYNAMIC)
        {
            throw new RuntimeException(
                    "Der Steh-Test ist nur für dynamische Objekte definiert");
        }
        AABB bodyBounds = calculateBodyAABB();
        // Test-AABB: Should be a rectangle right below the body
        // Minimal height, width of the body
        AABB testAABB = new AABB();
        final double epsilon = 0.0001;
        testAABB.lowerBound.set(bodyBounds.lowerBound.x,
                bodyBounds.lowerBound.y);
        testAABB.upperBound.set(bodyBounds.upperBound.x,
                (float) (bodyBounds.lowerBound.y + epsilon));
        Fixture[] groundCandidates = worldHandler.queryAABB(testAABB);
        for (Fixture fixture : groundCandidates)
        {
            Actor corresponding = (Actor) fixture.getBody().getUserData();
            if (corresponding != null
                    && corresponding.getBodyType() == BodyType.STATIC)
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public void setFixtures(Supplier<List<FixtureData>> fixtures)
    {
        synchronized (worldHandler)
        {
            PhysicsData physicsData = this.getPhysicsData();
            for (Fixture fixture = body.fixtureList; fixture != null; fixture = fixture.next)
            {
                body.destroyFixture(fixture);
            }
            for (FixtureData fixtureData : fixtures.get())
            {
                body.createFixture(fixtureData.createFixtureDef(physicsData));
            }
        }
    }

    /**
     * @hidden
     */
    @Override
    @Internal
    public PhysicsData getPhysicsData()
    {
        return PhysicsData.fromBody(body, getType());
    }

    @Override
    public void applyMountCallbacks(PhysicsHandler otherHandler)
    {
        // nothing to do
    }

    @Override
    public List<CollisionEvent<Actor>> getCollisions()
    {
        List<CollisionEvent<Actor>> contacts = new ArrayList<>();
        for (ContactEdge contact = body
                .getContactList(); contact != null; contact = contact.next)
        {
            // Contact exists with other Body. Next, check if they are actually
            // touching
            if (contact.contact.isTouching())
            {
                contacts.add(new CollisionEvent<>(contact.contact,
                        (Actor) contact.other.getUserData()));
            }
        }
        return contacts;
    }

    @Override
    public WorldHandler getWorldHandler()
    {
        return worldHandler;
    }

    /**
     * Legt den Schlafzustand des Körpers fest. Ein schlafender Körper hat sehr
     * geringe CPU-Kosten.
     *
     * <p>
     * Das Versetzen in den Schlafzustand setzt die Geschwindigkeit und den
     * Impuls eines Körpers auf null.
     * </p>
     *
     * @param value Der Schlafzustand des Körpers.
     */
    public void setAwake(boolean value)
    {
        body.setAwake(value);
    }
}
