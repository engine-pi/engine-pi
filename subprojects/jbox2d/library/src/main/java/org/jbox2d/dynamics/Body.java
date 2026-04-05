/*
 * Copyright (c) 2013, Daniel Murphy
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 	* Redistributions of source code must retain the above copyright notice,
 * 	  this list of conditions and the following disclaimer.
 * 	* Redistributions in binary form must reproduce the above copyright notice,
 * 	  this list of conditions and the following disclaimer in the documentation
 * 	  and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.jbox2d.dynamics;

import org.jbox2d.collision.broadphase.BroadPhase;
import org.jbox2d.collision.shapes.MassData;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Rot;
import org.jbox2d.common.Sweep;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.contacts.Contact;
import org.jbox2d.dynamics.contacts.ContactEdge;
import org.jbox2d.dynamics.joints.JointEdge;

/**
 * A rigid body. These are created via World.createBody.
 *
 * @repolink https://github.com/erincatto/box2d/blob/main/src/dynamics/b2_body.cpp
 * @repolink https://github.com/erincatto/box2d/blob/main/include/box2d/b2_body.h
 *
 * @author Daniel Murphy
 */
public class Body
{

    public static final int islandFlag = 0x0001;

    public static final int awakeFlag = 0x0002;

    public static final int autoSleepFlag = 0x0004;

    public static final int bulletFlag = 0x0008;

    public static final int fixedRotationFlag = 0x0010;

    public static final int activeFlag = 0x0020;

    public static final int toiFlag = 0x0040;

    /**
     * The body type: static, kinematic, or dynamic. Note: if a dynamic body
     * would have zero mass, the mass is set to one.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_body.h#L72-L74
     */
    public BodyType type;

    public int flags;

    public int islandIndex;

    /**
     * The body origin transform.
     */
    public final Transform xf = new Transform();

    /**
     * The previous transform for particle simulation
     */
    public final Transform xf0 = new Transform();

    /**
     * The swept motion for CCD
     */
    public final Sweep sweep = new Sweep();

    public final Vec2 linearVelocity = new Vec2();

    /**
     * The angular velocity of the body.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_body.h#L86-L87
     */
    public float angularVelocity;

    public final Vec2 force = new Vec2();

    public float torque;

    public World world;

    /**
     * The previous body in the world's body list.
     */
    public Body prev;

    /**
     * The next body in the world's body list.
     */
    public Body next;

    public Fixture fixtureList;

    public int fixtureCount;

    public JointEdge jointList;

    public ContactEdge contactList;

    /**
     * The mass.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_body.h#L457
     */
    public float mass;

    /**
     * The inverse mass ({@code 1 / mass}).
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_body.h#L457
     */
    public float invMass;

    /**
     * The rotational inertia about the center of mass.
     *
     * <p>
     * The moment of inertia, otherwise known as the mass moment of
     * inertia,https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_body.h#L459-L460
     * angular/rotational mass, second moment of mass, or most accurately,
     * rotational inertia, of a rigid body is a quantity that determines the
     * torque needed for a desired angular acceleration about a rotational axis,
     * akin to how mass determines the force needed for a desired acceleration.
     * </p>
     *
     * <a href="https://en.wikipedia.org/wiki/Moment_of_inertia">Wikipedia:
     * Moment of inertia</a>
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_body.h#L459-L460
     */
    public float I;

    /**
     * The inverse rotational inertia about the center of mass.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_body.h#L459-L460
     */
    public float invI;

    /**
     * Linear damping is used to reduce the linear velocity. The damping
     * parameter can be larger than 1.0f but the damping effect becomes
     * sensitive to the time step when the damping parameter is large. Units are
     * {@code 1 / time}.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_body.h#L89-L93
     */
    public float linearDamping;

    /**
     * Angular damping is used to reduce the angular velocity. The damping
     * parameter can be larger than 1.0f but the damping effect becomes
     * sensitive to the time step when the damping parameter is large. Units are
     * {@code 1 / time}.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_body.h#L95-L99
     */
    public float angularDamping;

    /**
     * Scale the gravity applied to this body.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_body.h#L123-L124
     */
    public float gravityScale;

    public float sleepTime;

    /**
     * Use this to store application specific body data.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_body.h#L120-L121
     */
    public Object userData;

    /**
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_body.cpp#L31-L104
     */
    public Body(final BodyDef bd, World world)
    {
        assert (bd.position.isValid());
        assert (bd.linearVelocity.isValid());
        assert (bd.gravityScale >= 0.0f);
        assert (bd.angularDamping >= 0.0f);
        assert (bd.linearDamping >= 0.0f);
        flags = 0;
        if (bd.bullet)
        {
            flags |= bulletFlag;
        }
        if (bd.fixedRotation)
        {
            flags |= fixedRotationFlag;
        }
        if (bd.allowSleep)
        {
            flags |= autoSleepFlag;
        }
        if (bd.awake)
        {
            flags |= awakeFlag;
        }
        if (bd.active)
        {
            flags |= activeFlag;
        }
        this.world = world;
        xf.p.set(bd.position);
        xf.q.set(bd.angle);
        sweep.localCenter.setZero();
        sweep.c0.set(xf.p);
        sweep.c.set(xf.p);
        sweep.a0 = bd.angle;
        sweep.a = bd.angle;
        sweep.alpha0 = 0.0f;
        jointList = null;
        contactList = null;
        prev = null;
        next = null;
        linearVelocity.set(bd.linearVelocity);
        angularVelocity = bd.angularVelocity;
        linearDamping = bd.linearDamping;
        angularDamping = bd.angularDamping;
        gravityScale = bd.gravityScale;
        force.setZero();
        torque = 0.0f;
        sleepTime = 0.0f;
        type = bd.type;
        if (type == BodyType.DYNAMIC)
        {
            mass = 1f;
            invMass = 1f;
        }
        else
        {
            mass = 0f;
            invMass = 0f;
        }
        I = 0.0f;
        invI = 0.0f;
        userData = bd.userData;
        fixtureList = null;
        fixtureCount = 0;
    }

    /**
     * Creates a fixture and attach it to this body. Use this function if you
     * need to set some fixture parameters, like friction. Otherwise, you can
     * create the fixture directly from a shape. If the density is non-zero,
     * this function automatically updates the mass of the body. Contacts are
     * not created until the next time step.
     *
     * @param def The fixture definition.
     *
     * @warning This function is locked during callbacks.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_body.cpp#L204-L211
     */
    public final Fixture createFixture(FixtureDef def)
    {
        assert (!world.isLocked());
        if (world.isLocked())
        {
            return null;
        }
        Fixture fixture = new Fixture();
        fixture.create(this, def);
        if ((flags & activeFlag) == activeFlag)
        {
            BroadPhase broadPhase = world.contactManager.broadPhase;
            fixture.createProxies(broadPhase, xf);
        }
        fixture.next = fixtureList;
        fixtureList = fixture;
        ++fixtureCount;
        fixture.body = this;
        // Adjust mass properties if needed.
        if (fixture.density > 0.0f)
        {
            resetMassData();
        }
        // Let the world know we have a new fixture. This will cause new
        // contacts
        // to be created at the beginning of the next time step.
        world.flags |= World.NEW_FIXTURE;
        return fixture;
    }

    private final FixtureDef fixDef = new FixtureDef();

    /**
     * Creates a fixture from a shape and attach it to this body. This is a
     * convenience function. Use FixtureDef if you need to set parameters like
     * friction, restitution, user data, or filtering. If the density is
     * non-zero, this function automatically updates the mass of the body.
     *
     * @param shape The shape to be cloned.
     * @param density The shape density (set to zero for static bodies).
     *
     * @warning This function is locked during callbacks.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_body.cpp#L204-L211
     */
    public final Fixture createFixture(Shape shape, float density)
    {
        fixDef.shape = shape;
        fixDef.density = density;
        return createFixture(fixDef);
    }

    /**
     * Destroy a fixture. This removes the fixture from the broad-phase and
     * destroys all contacts associated with this fixture. This will
     * automatically adjust the mass of the body if the body is dynamic and the
     * fixture has positive density. All fixtures attached to a body are
     * implicitly destroyed when the body is destroyed.
     *
     * @param fixture The fixture to be removed.
     *
     * @warning This function is locked during callbacks.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_body.cpp#L213-L288
     */
    public final void destroyFixture(Fixture fixture)
    {
        assert (!world.isLocked());
        if (world.isLocked())
        {
            return;
        }
        assert (fixture.body == this);
        // Remove the fixture from this body's singly linked list.
        assert (fixtureCount > 0);
        Fixture node = fixtureList;
        Fixture last = null; // java change
        boolean found = false;
        while (node != null)
        {
            if (node == fixture)
            {
                node = fixture.next;
                found = true;
                break;
            }
            last = node;
            node = node.next;
        }
        // You tried to remove a shape that is not attached to this body.
        assert (found);
        // java change, remove it from the list
        if (last == null)
        {
            fixtureList = fixture.next;
        }
        else
        {
            last.next = fixture.next;
        }
        // Destroy any contacts associated with the fixture.
        ContactEdge edge = contactList;
        while (edge != null)
        {
            Contact c = edge.contact;
            edge = edge.next;
            Fixture fixtureA = c.getFixtureA();
            Fixture fixtureB = c.getFixtureB();
            if (fixture == fixtureA || fixture == fixtureB)
            {
                // This destroys the contact and removes it from
                // this body's contact list.
                world.contactManager.destroy(c);
            }
        }
        if ((flags & activeFlag) == activeFlag)
        {
            BroadPhase broadPhase = world.contactManager.broadPhase;
            fixture.destroyProxies(broadPhase);
        }
        fixture.destroy();
        fixture.body = null;
        fixture.next = null;
        fixture = null;
        --fixtureCount;
        // Reset the mass data.
        resetMassData();
    }

    /**
     * Set the position of the body's origin and rotation. This breaks any
     * contacts and wakes the other bodies. Manipulating a body's transform may
     * cause non-physical behavior. Note: contacts are updated on the next call
     * to World.step().
     *
     * @param position The world position of the body's local origin.
     * @param angle The world rotation in radians.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_body.cpp#L420-L445
     */
    public final void setTransform(Vec2 position, float angle)
    {
        assert (!world.isLocked());
        if (world.isLocked())
        {
            return;
        }
        xf.q.set(angle);
        xf.p.set(position);
        // sweep.c0 = sweep.c = Mul(xf, sweep.localCenter);
        Transform.mulToOutUnsafe(xf, sweep.localCenter, sweep.c);
        sweep.a = angle;
        sweep.c0.set(sweep.c);
        sweep.a0 = sweep.a;
        BroadPhase broadPhase = world.contactManager.broadPhase;
        for (Fixture f = fixtureList; f != null; f = f.next)
        {
            f.synchronize(broadPhase, xf, xf);
        }
    }

    /**
     * Get the body transform for the body's origin.
     *
     * @return The world transform of the body's origin.
     */
    public final Transform getTransform()
    {
        return xf;
    }

    /**
     * Get the world body origin position. Do not modify.
     *
     * @return The world position of the body's origin.
     */
    public final Vec2 getPosition()
    {
        return xf.p;
    }

    /**
     * Get the angle in radians.
     *
     * @return The current world rotation angle in radians.
     */
    public final float getAngle()
    {
        return sweep.a;
    }

    /**
     * Get the world position of the center of mass. Do not modify.
     */
    public final Vec2 getWorldCenter()
    {
        return sweep.c;
    }

    /**
     * Get the local position of the center of mass. Do not modify.
     */
    public final Vec2 getLocalCenter()
    {
        return sweep.localCenter;
    }

    /**
     * Set the linear velocity of the center of mass.
     *
     * @param v The new linear velocity of the center of mass.
     */
    public final void setLinearVelocity(Vec2 v)
    {
        if (type == BodyType.STATIC)
        {
            return;
        }
        if (Vec2.dot(v, v) > 0.0f)
        {
            setAwake(true);
        }
        linearVelocity.set(v);
    }

    /**
     * Get the linear velocity of the center of mass. Do not modify, instead use
     * {@link #setLinearVelocity(Vec2)}.
     *
     * @return The linear velocity of the center of mass.
     */
    public final Vec2 getLinearVelocity()
    {
        return linearVelocity;
    }

    /**
     * Set the angular velocity.
     *
     * @param w The new angular velocity in radians/second.
     */
    public final void setAngularVelocity(float w)
    {
        if (type == BodyType.STATIC)
        {
            return;
        }
        if (w * w > 0f)
        {
            setAwake(true);
        }
        angularVelocity = w;
    }

    /**
     * Get the angular velocity.
     *
     * @return The angular velocity in radians/second.
     */
    public final float getAngularVelocity()
    {
        return angularVelocity;
    }

    /**
     * Get the gravity scale of the body.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_body.h#L610-L613
     *
     */
    public float getGravityScale()
    {
        return gravityScale;
    }

    /**
     * Set the gravity scale of the body.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_body.h#L615-L618
     */
    public void setGravityScale(float gravityScale)
    {
        this.gravityScale = gravityScale;
    }

    /**
     * Apply a force at a world point. If the force is not applied at the center
     * of mass, it will generate a torque and affect the angular velocity. This
     * wakes up the body.
     *
     * @param force The world force vector, usually in Newtons (N).
     * @param point The world position of the point of application.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_body.h#L743-L761
     */
    public final void applyForce(Vec2 force, Vec2 point)
    {
        if (type != BodyType.DYNAMIC)
        {
            return;
        }
        if (!isAwake())
        {
            setAwake(true);
        }
        // force.addLocal(force);
        // Vec2 temp = tltemp.get();
        // temp.set(point).subLocal(sweep.c);
        // torque += Vec2.cross(temp, force);
        this.force.x += force.x;
        this.force.y += force.y;
        torque += (point.x - sweep.c.x) * force.y
                - (point.y - sweep.c.y) * force.x;
    }

    /**
     * Apply a force to the center of mass. This wakes up the body.
     *
     * @param force The world force vector, usually in Newtons (N).
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_body.h#L763-L780
     */
    public final void applyForceToCenter(Vec2 force)
    {
        if (type != BodyType.DYNAMIC)
        {
            return;
        }
        if (!isAwake())
        {
            setAwake(true);
        }
        this.force.x += force.x;
        this.force.y += force.y;
    }

    /**
     * Apply a torque. This affects the angular velocity without affecting the
     * linear velocity of the center of mass. This wakes up the body.
     *
     * @param torque About the z-axis (out of the screen), usually in N-m.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_body.h#L782-L799
     */
    public final void applyTorque(float torque)
    {
        if (type != BodyType.DYNAMIC)
        {
            return;
        }
        if (!isAwake())
        {
            setAwake(true);
        }
        this.torque += torque;
    }

    /**
     * Apply an impulse at a point. This immediately modifies the velocity. It
     * also modifies the angular velocity if the point of application is not at
     * the center of mass. This wakes up the body if 'wake' is set to true. If
     * the body is sleeping and 'wake' is false, then there is no effect.
     *
     * @param impulse The world impulse vector, usually in N-seconds or kg-m/s.
     * @param point The world position of the point of application.
     * @param wake Also wake up the body.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_body.h#L801-L819
     */
    public final void applyLinearImpulse(Vec2 impulse, Vec2 point, boolean wake)
    {
        if (type != BodyType.DYNAMIC)
        {
            return;
        }
        if (!isAwake())
        {
            if (wake)
            {
                setAwake(true);
            }
            else
            {
                return;
            }
        }
        linearVelocity.x += impulse.x * invMass;
        linearVelocity.y += impulse.y * invMass;
        angularVelocity += invI * ((point.x - sweep.c.x) * impulse.y
                - (point.y - sweep.c.y) * impulse.x);
    }

    /**
     * Apply an angular impulse.
     *
     * @param impulse The angular impulse in units of kg*m*m/s
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_body.h#L840-L857
     */
    public void applyAngularImpulse(float impulse)
    {
        if (type != BodyType.DYNAMIC)
        {
            return;
        }
        if (!isAwake())
        {
            setAwake(true);
        }
        angularVelocity += invI * impulse;
    }

    /**
     * Get the total mass of the body.
     *
     * @return The mass, usually in kilograms (kg).
     */
    public final float getMass()
    {
        return mass;
    }

    /**
     * Get the central rotational inertia of the body.
     *
     * @return The rotational inertia, usually in kg-m^2.
     */
    public final float getInertia()
    {
        return I + mass * (sweep.localCenter.x * sweep.localCenter.x
                + sweep.localCenter.y * sweep.localCenter.y);
    }

    /**
     * Get the mass data of the body. The rotational inertia is relative to the
     * center of mass.
     */
    public final void getMassData(MassData data)
    {
        // data.mass = mass;
        // data.I = I + mass * Vec2.dot(sweep.localCenter,
        // sweep.localCenter);
        // data.center.set(sweep.localCenter);
        data.mass = mass;
        data.I = I + mass * (sweep.localCenter.x * sweep.localCenter.x
                + sweep.localCenter.y * sweep.localCenter.y);
        data.center.x = sweep.localCenter.x;
        data.center.y = sweep.localCenter.y;
    }

    /**
     * Set the mass properties to override the mass properties of the fixtures.
     * Note that this changes the center of mass position. Note that creating or
     * destroying fixtures can also alter the mass. This function has no effect
     * if the body isn't dynamic.
     *
     * @param massData The mass properties.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_body.cpp#L356-L395
     */
    public final void setMassData(MassData massData)
    {
        // TODO_ERIN adjust linear velocity and torque to account for movement
        // of center.
        assert (!world.isLocked());
        if (world.isLocked())
        {
            return;
        }
        if (type != BodyType.DYNAMIC)
        {
            return;
        }
        invMass = 0.0f;
        I = 0.0f;
        invI = 0.0f;
        mass = massData.mass;
        if (mass <= 0.0f)
        {
            mass = 1f;
        }
        invMass = 1.0f / mass;
        if (massData.I > 0.0f && (flags & fixedRotationFlag) == 0)
        {
            I = massData.I - mass * Vec2.dot(massData.center, massData.center);
            assert (I > 0.0f);
            invI = 1.0f / I;
        }
        final Vec2 oldCenter = world.getPool().popVec2();
        // Move center of mass.
        oldCenter.set(sweep.c);
        sweep.localCenter.set(massData.center);
        // sweep.c0 = sweep.c = Mul(xf, sweep.localCenter);
        Transform.mulToOutUnsafe(xf, sweep.localCenter, sweep.c0);
        sweep.c.set(sweep.c0);
        // Update center of mass velocity.
        // linearVelocity += Cross(angularVelocity, sweep.c - oldCenter);
        final Vec2 temp = world.getPool().popVec2();
        temp.set(sweep.c).subLocal(oldCenter);
        Vec2.crossToOut(angularVelocity, temp, temp);
        linearVelocity.addLocal(temp);
        world.getPool().pushVec2(2);
    }

    private final MassData pmd = new MassData();

    /**
     * This resets the mass properties to the sum of the mass properties of the
     * fixtures. This normally does not need to be called unless you called
     * setMassData to override the mass, and you later want to reset the mass.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_body.cpp#L290-L354
     */
    public final void resetMassData()
    {
        // Compute mass data from shapes. Each shape has its own density.
        mass = 0.0f;
        invMass = 0.0f;
        I = 0.0f;
        invI = 0.0f;
        sweep.localCenter.setZero();
        // Static and kinematic bodies have zero mass.
        if (type == BodyType.STATIC || type == BodyType.KINEMATIC)
        {
            // sweep.c0 = sweep.c = xf.position;
            sweep.c0.set(xf.p);
            sweep.c.set(xf.p);
            sweep.a0 = sweep.a;
            return;
        }
        assert (type == BodyType.DYNAMIC);
        // Accumulate mass over all fixtures.
        final Vec2 localCenter = world.getPool().popVec2();
        localCenter.setZero();
        final Vec2 temp = world.getPool().popVec2();
        final MassData massData = pmd;
        for (Fixture f = fixtureList; f != null; f = f.next)
        {
            if (f.density == 0.0f)
            {
                continue;
            }
            f.getMassData(massData);
            mass += massData.mass;
            // center += massData.mass * massData.center;
            temp.set(massData.center).mulLocal(massData.mass);
            localCenter.addLocal(temp);
            I += massData.I;
        }
        // Compute center of mass.
        if (mass > 0.0f)
        {
            invMass = 1.0f / mass;
            localCenter.mulLocal(invMass);
        }
        else
        {
            // Force all dynamic bodies to have a positive mass.
            mass = 1.0f;
            invMass = 1.0f;
        }
        if (I > 0.0f && (flags & fixedRotationFlag) == 0)
        {
            // Center the inertia about the center of mass.
            I -= mass * Vec2.dot(localCenter, localCenter);
            assert (I > 0.0f);
            invI = 1.0f / I;
        }
        else
        {
            I = 0.0f;
            invI = 0.0f;
        }
        Vec2 oldCenter = world.getPool().popVec2();
        // Move center of mass.
        oldCenter.set(sweep.c);
        sweep.localCenter.set(localCenter);
        // sweep.c0 = sweep.c = Mul(xf, sweep.localCenter);
        Transform.mulToOutUnsafe(xf, sweep.localCenter, sweep.c0);
        sweep.c.set(sweep.c0);
        // Update center of mass velocity.
        // linearVelocity += Cross(angularVelocity, sweep.c - oldCenter);
        temp.set(sweep.c).subLocal(oldCenter);
        Vec2.crossToOutUnsafe(angularVelocity, temp, oldCenter);
        linearVelocity.addLocal(oldCenter);
        world.getPool().pushVec2(3);
    }

    /**
     * Get the world coordinates of a point given the local coordinates.
     *
     * @param localPoint A point on the body measured relative the body's
     *     origin.
     *
     * @return The same point expressed in world coordinates.
     */
    public final Vec2 getWorldPoint(Vec2 localPoint)
    {
        Vec2 v = new Vec2();
        getWorldPointToOut(localPoint, v);
        return v;
    }

    public final void getWorldPointToOut(Vec2 localPoint, Vec2 out)
    {
        Transform.mulToOut(xf, localPoint, out);
    }

    /**
     * Get the world coordinates of a vector given the local coordinates.
     *
     * @param localVector A vector fixed in the body.
     *
     * @return The same vector expressed in world coordinates.
     */
    public final Vec2 getWorldVector(Vec2 localVector)
    {
        Vec2 out = new Vec2();
        getWorldVectorToOut(localVector, out);
        return out;
    }

    public final void getWorldVectorToOut(Vec2 localVector, Vec2 out)
    {
        Rot.mulToOut(xf.q, localVector, out);
    }

    public final void getWorldVectorToOutUnsafe(Vec2 localVector, Vec2 out)
    {
        Rot.mulToOutUnsafe(xf.q, localVector, out);
    }

    /**
     * Gets a local point relative to the body's origin given a world point.
     *
     * @param worldPoint Point in world coordinates.
     *
     * @return The corresponding local point relative to the body's origin.
     */
    public final Vec2 getLocalPoint(Vec2 worldPoint)
    {
        Vec2 out = new Vec2();
        getLocalPointToOut(worldPoint, out);
        return out;
    }

    public final void getLocalPointToOut(Vec2 worldPoint, Vec2 out)
    {
        Transform.mulTransToOut(xf, worldPoint, out);
    }

    /**
     * Gets a local vector given a world vector.
     *
     * @param worldVector A vector in world coordinates.
     *
     * @return The corresponding local vector.
     */
    public final Vec2 getLocalVector(Vec2 worldVector)
    {
        Vec2 out = new Vec2();
        getLocalVectorToOut(worldVector, out);
        return out;
    }

    public final void getLocalVectorToOut(Vec2 worldVector, Vec2 out)
    {
        Rot.mulTrans(xf.q, worldVector, out);
    }

    public final void getLocalVectorToOutUnsafe(Vec2 worldVector, Vec2 out)
    {
        Rot.mulTransUnsafe(xf.q, worldVector, out);
    }

    /**
     * Get the world linear velocity of a world point attached to this body.
     *
     * @param worldPoint A point in world coordinates.
     *
     * @return The world velocity of a point.
     */
    public final Vec2 getLinearVelocityFromWorldPoint(Vec2 worldPoint)
    {
        Vec2 out = new Vec2();
        getLinearVelocityFromWorldPointToOut(worldPoint, out);
        return out;
    }

    public final void getLinearVelocityFromWorldPointToOut(Vec2 worldPoint,
            Vec2 out)
    {
        final float tempX = worldPoint.x - sweep.c.x;
        final float tempY = worldPoint.y - sweep.c.y;
        out.x = -angularVelocity * tempY + linearVelocity.x;
        out.y = angularVelocity * tempX + linearVelocity.y;
    }

    /**
     * Get the world velocity of a local point.
     *
     * @param localPoint A point in local coordinates.
     *
     * @return The world velocity of a point.
     */
    public final Vec2 getLinearVelocityFromLocalPoint(Vec2 localPoint)
    {
        Vec2 out = new Vec2();
        getLinearVelocityFromLocalPointToOut(localPoint, out);
        return out;
    }

    public final void getLinearVelocityFromLocalPointToOut(Vec2 localPoint,
            Vec2 out)
    {
        getWorldPointToOut(localPoint, out);
        getLinearVelocityFromWorldPointToOut(out, out);
    }

    /**
     * Get the linear damping of the body.
     */
    public final float getLinearDamping()
    {
        return linearDamping;
    }

    /**
     * Set the linear damping of the body.
     */
    public final void setLinearDamping(float linearDamping)
    {
        this.linearDamping = linearDamping;
    }

    /**
     * Get the angular damping of the body.
     */
    public final float getAngularDamping()
    {
        return angularDamping;
    }

    /**
     * Set the angular damping of the body.
     */
    public final void setAngularDamping(float angularDamping)
    {
        this.angularDamping = angularDamping;
    }

    public BodyType getType()
    {
        return type;
    }

    /**
     * Set the type of this body. This may alter the mass and velocity.
     */
    public void setType(BodyType type)
    {
        assert (!world.isLocked());
        if (world.isLocked())
        {
            return;
        }
        if (this.type == type)
        {
            return;
        }
        this.type = type;
        resetMassData();
        if (this.type == BodyType.STATIC)
        {
            linearVelocity.setZero();
            angularVelocity = 0.0f;
            sweep.a0 = sweep.a;
            sweep.c0.set(sweep.c);
            synchronizeFixtures();
        }
        setAwake(true);
        force.setZero();
        torque = 0.0f;
        // Delete the attached contacts.
        ContactEdge ce = contactList;
        while (ce != null)
        {
            ContactEdge ce0 = ce;
            ce = ce.next;
            world.contactManager.destroy(ce0.contact);
        }
        contactList = null;
        // Touch the proxies so that new contacts will be created (when
        // appropriate)
        BroadPhase broadPhase = world.contactManager.broadPhase;
        for (Fixture f = fixtureList; f != null; f = f.next)
        {
            int proxyCount = f.proxyCount;
            for (int i = 0; i < proxyCount; ++i)
            {
                broadPhase.touchProxy(f.proxies[i].proxyId);
            }
        }
    }

    /**
     * Is this body treated like a bullet for continuous collision detection?
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_body.h#L632-L635
     */
    public final boolean isBullet()
    {
        return (flags & bulletFlag) == bulletFlag;
    }

    /**
     * Should this body be treated like a bullet for continuous collision
     * detection?
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_body.h#L620-L630
     */
    public final void setBullet(boolean flag)
    {
        if (flag)
        {
            flags |= bulletFlag;
        }
        else
        {
            flags &= ~bulletFlag;
        }
    }

    /**
     * You can disable sleeping on this body. If you disable sleeping, the body
     * will be woken.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_body.h#L675-L686
     */
    public void setSleepingAllowed(boolean flag)
    {
        if (flag)
        {
            flags |= autoSleepFlag;
        }
        else
        {
            flags &= ~autoSleepFlag;
            setAwake(true);
        }
    }

    /**
     * Is this body allowed to sleep?
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_body.h#L688-L691
     */
    public boolean isSleepingAllowed()
    {
        return (flags & autoSleepFlag) == autoSleepFlag;
    }

    /**
     * Set the sleep state of the body. A sleeping body has very low CPU cost.
     * Note that putting it to sleep will set its velocities and forces to zero.
     *
     * @param flag Set to true to wake the body, false to put it to sleep.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_body.h#L637-L658
     */
    public void setAwake(boolean flag)
    {
        if (flag)
        {
            if ((flags & awakeFlag) == 0)
            {
                flags |= awakeFlag;
                sleepTime = 0.0f;
            }
        }
        else
        {
            flags &= ~awakeFlag;
            sleepTime = 0.0f;
            linearVelocity.setZero();
            angularVelocity = 0.0f;
            force.setZero();
            torque = 0.0f;
        }
    }

    /**
     * Get the sleeping state of this body.
     *
     * @return true if the body is awake.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_body.h#L660-L663
     */
    public boolean isAwake()
    {
        return (flags & awakeFlag) == awakeFlag;
    }

    /**
     * Set the active state of the body. An inactive body is not simulated and
     * cannot be collided with or woken up. If you pass a flag of true, all
     * fixtures will be added to the broad-phase. If you pass a flag of false,
     * all fixtures will be removed from the broad-phase and all contacts will
     * be destroyed. Fixtures and joints are otherwise unaffected. You may
     * continue to create/destroy fixtures and joints on inactive bodies.
     * Fixtures on an inactive body are implicitly inactive and will not
     * participate in collisions, ray-casts, or queries. Joints connected to an
     * inactive body are implicitly inactive. An inactive body is still owned by
     * a World object and remains in the body list.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_body.cpp#L471-L515
     */
    public void setActive(boolean flag)
    {
        assert (!world.isLocked());
        if (flag == isActive())
        {
            return;
        }
        if (flag)
        {
            flags |= activeFlag;
            // Create all proxies.
            BroadPhase broadPhase = world.contactManager.broadPhase;
            for (Fixture f = fixtureList; f != null; f = f.next)
            {
                f.createProxies(broadPhase, xf);
            }
            // Contacts are created the next time step.
        }
        else
        {
            flags &= ~activeFlag;
            // Destroy all proxies.
            BroadPhase broadPhase = world.contactManager.broadPhase;
            for (Fixture f = fixtureList; f != null; f = f.next)
            {
                f.destroyProxies(broadPhase);
            }
            // Destroy the attached contacts.
            ContactEdge ce = contactList;
            while (ce != null)
            {
                ContactEdge ce0 = ce;
                ce = ce.next;
                world.contactManager.destroy(ce0.contact);
            }
            contactList = null;
        }
    }

    /**
     * Get the active state of the body.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_body.h#L665-L668
     */
    public boolean isActive()
    {
        return (flags & activeFlag) == activeFlag;
    }

    /**
     * Set this body to have fixed rotation. This causes the mass to be reset.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_body.cpp#L517-L537
     */
    public void setFixedRotation(boolean flag)
    {
        if (flag)
        {
            flags |= fixedRotationFlag;
        }
        else
        {
            flags &= ~fixedRotationFlag;
        }
        resetMassData();
    }

    /**
     * Does this body have fixed rotation?
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_body.h#L670-L673
     */
    public boolean isFixedRotation()
    {
        return (flags & fixedRotationFlag) == fixedRotationFlag;
    }

    /**
     * Get the list of all fixtures attached to this body.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_body.h#L693-L701
     */
    public final Fixture getFixtureList()
    {
        return fixtureList;
    }

    /**
     * Get the list of all joints attached to this body.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_body.h#L703-L711
     */
    public final JointEdge getJointList()
    {
        return jointList;
    }

    /**
     * Get the list of all contacts attached to this body.
     *
     * @warning this list changes during the time step, and you may miss some
     *     collisions if you don't use ContactListener.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_body.h#L713-L721
     */
    public final ContactEdge getContactList()
    {
        return contactList;
    }

    /**
     * Get the next body in the world's body list.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_body.h#L374-L376
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_body.h#L723-L731
     */
    public final Body getNext()
    {
        return next;
    }

    /**
     * Get the user data pointer that was provided in the body definition.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_body.h#L733-L741
     */
    public final Object getUserData()
    {
        return userData;
    }

    /**
     * Set the user data. Use this to store your application specific data.
     */
    public final void setUserData(Object data)
    {
        userData = data;
    }

    /**
     * Get the parent world of this body.
     */
    public final World getWorld()
    {
        return world;
    }

    // djm pooling
    private final Transform pxf = new Transform();

    /**
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_body.cpp#L447-L469
     */
    protected final void synchronizeFixtures()
    {
        final Transform xf1 = pxf;
        // xf1.position = sweep.c0 - Mul(xf1.R, sweep.localCenter);
        // xf1.q.set(sweep.a0);
        // Rot.mulToOutUnsafe(xf1.q, sweep.localCenter, xf1.p);
        // xf1.p.mulLocal(-1).addLocal(sweep.c0);
        // inlined:
        xf1.q.s = MathUtils.sin(sweep.a0);
        xf1.q.c = MathUtils.cos(sweep.a0);
        xf1.p.x = sweep.c0.x - xf1.q.c * sweep.localCenter.x
                + xf1.q.s * sweep.localCenter.y;
        xf1.p.y = sweep.c0.y - xf1.q.s * sweep.localCenter.x
                - xf1.q.c * sweep.localCenter.y;
        // end inline
        for (Fixture f = fixtureList; f != null; f = f.next)
        {
            f.synchronize(world.contactManager.broadPhase, xf1, xf);
        }
    }

    /**
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_body.h#L859-L863
     */
    public final void synchronizeTransform()
    {
        // xf.q.set(sweep.a);
        //
        // // xf.position = sweep.c - Mul(xf.R, sweep.localCenter);
        // Rot.mulToOutUnsafe(xf.q, sweep.localCenter, xf.p);
        // xf.p.mulLocal(-1).addLocal(sweep.c);
        //
        xf.q.s = MathUtils.sin(sweep.a);
        xf.q.c = MathUtils.cos(sweep.a);
        Rot q = xf.q;
        Vec2 v = sweep.localCenter;
        xf.p.x = sweep.c.x - q.c * v.x + q.s * v.y;
        xf.p.y = sweep.c.y - q.s * v.x - q.c * v.y;
    }

    /**
     * This is used to prevent connected bodies from colliding. It may lie,
     * depending on the collideConnected flag.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_body.cpp#L397-L418
     */
    public boolean shouldCollide(Body other)
    {
        // At least one body should be dynamic.
        if (type != BodyType.DYNAMIC && other.type != BodyType.DYNAMIC)
        {
            return false;
        }
        // Does a joint prevent collision?
        for (JointEdge jn = jointList; jn != null; jn = jn.next)
        {
            if (jn.other == other)
            {
                if (!jn.joint.getCollideConnected())
                {
                    return false;
                }
            }
        }
        return true;
    }

    protected final void advance(float t)
    {
        // Advance to the new safe time. This doesn't sync the broad-phase.
        sweep.advance(t);
        sweep.c.set(sweep.c0);
        sweep.a = sweep.a0;
        xf.q.set(sweep.a);
        // xf.position = sweep.c - Mul(xf.R, sweep.localCenter);
        Rot.mulToOutUnsafe(xf.q, sweep.localCenter, xf.p);
        xf.p.mulLocal(-1).addLocal(sweep.c);
    }
}
