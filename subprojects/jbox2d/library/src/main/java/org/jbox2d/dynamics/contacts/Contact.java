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
package org.jbox2d.dynamics.contacts;

import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.ContactID;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.ManifoldPoint;
import org.jbox2d.collision.WorldManifold;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Transform;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.pooling.WorldPool;

/**
 * The class manages contact between two shapes. A contact exists for each
 * overlapping AABB in the broad-phase (except if filtered). Therefore, a
 * contact object may exist that has no contact points.
 *
 * @author Daniel Murphy
 *
 * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_contact.h#L85-L384
 */
public abstract class Contact
{
    /**
     * Flags stored in flags Used when crawling contact graph when forming
     * islands.
     */
    public static final int ISLAND_FLAG = 0x0001;

    /**
     * Set when the shapes are touching.
     */
    public static final int TOUCHING_FLAG = 0x0002;

    /**
     * This contact can be disabled (by user)
     */
    public static final int ENABLED_FLAG = 0x0004;

    /**
     * This contact needs filtering because a fixture filter was changed.
     */
    public static final int FILTER_FLAG = 0x0008;

    /**
     * This bullet contact had a TOI event
     */
    public static final int BULLET_HIT_FLAG = 0x0010;

    public static final int TOI_FLAG = 0x0020;

    public int flags;

    /**
     * World pool and list pointers.
     */
    public Contact prev;

    public Contact next;

    /**
     * Nodes for connecting bodies.
     */
    public ContactEdge nodeA;

    public ContactEdge nodeB;

    public Fixture fixtureA;

    public Fixture fixtureB;

    public int indexA;

    public int indexB;

    public final Manifold manifold;

    public float toiCount;

    public float toi;

    public float friction;

    public float restitution;

    public float tangentSpeed;

    protected final WorldPool pool;

    protected Contact(WorldPool argPool)
    {
        fixtureA = null;
        fixtureB = null;
        nodeA = new ContactEdge();
        nodeB = new ContactEdge();
        manifold = new Manifold();
        pool = argPool;
    }

    /**
     * initialization for pooling
     */
    public void init(Fixture fA, int indexA, Fixture fB, int indexB)
    {
        flags = ENABLED_FLAG;
        fixtureA = fA;
        fixtureB = fB;
        this.indexA = indexA;
        this.indexB = indexB;
        manifold.pointCount = 0;
        prev = null;
        next = null;
        nodeA.contact = null;
        nodeA.prev = null;
        nodeA.next = null;
        nodeA.other = null;
        nodeB.contact = null;
        nodeB.prev = null;
        nodeB.next = null;
        nodeB.other = null;
        toiCount = 0;
        friction = Contact.mixFriction(fA.friction, fB.friction);
        restitution = Contact.mixRestitution(fA.restitution, fB.restitution);
        tangentSpeed = 0;
    }

    /**
     * Get the contact manifold. Do not modify the manifold unless you
     * understand the internals of JBox2D.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_contact.h#L92-L95
     */
    public Manifold getManifold()
    {
        return manifold;
    }

    /**
     * Get the world manifold.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_contact.h#L97-L98
     */
    public void getWorldManifold(WorldManifold worldManifold)
    {
        final Body bodyA = fixtureA.getBody();
        final Body bodyB = fixtureB.getBody();
        final Shape shapeA = fixtureA.getShape();
        final Shape shapeB = fixtureB.getShape();
        worldManifold.initialize(manifold,
            bodyA.getTransform(),
            shapeA.radius,
            bodyB.getTransform(),
            shapeB.radius);
    }

    /**
     * Is this contact touching?
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_contact.h#L100-L101
     */
    public boolean isTouching()
    {
        return (flags & TOUCHING_FLAG) == TOUCHING_FLAG;
    }

    /**
     * Enable/disable this contact.
     *
     * <p>
     * This can be used inside the pre-solve contact listener. The contact is
     * only disabled for the current time step (or sub-step in continuous
     * collisions).
     * </p>
     *
     * @param flag {@code true} if the contact should be enable else
     *     {@code false}.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_contact.h#L103-L106
     */
    public void setEnabled(boolean flag)
    {
        if (flag)
        {
            flags |= ENABLED_FLAG;
        }
        else
        {
            flags &= ~ENABLED_FLAG;
        }
    }

    /**
     * Has this contact been disabled?
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_contact.h#L108-L109
     */
    public boolean isEnabled()
    {
        return (flags & ENABLED_FLAG) == ENABLED_FLAG;
    }

    /**
     * Get the next contact in the world’s contact list.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_contact.h#L111-L113
     */
    public Contact getNext()
    {
        return next;
    }

    /**
     * Get fixture A in this contact.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_contact.h#L115-L117
     */
    public Fixture getFixtureA()
    {
        return fixtureA;
    }

    /**
     * Get the child primitive index for fixture A.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_contact.h#L119-L120
     */
    public int getChildIndexA()
    {
        return indexA;
    }

    /**
     * Get fixture B in this contact.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_contact.h#L122-L124
     */
    public Fixture getFixtureB()
    {
        return fixtureB;
    }

    /**
     * Get the child primitive index for fixture B.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_contact.h#L126-L127
     */
    public int getChildIndexB()
    {
        return indexB;
    }

    /**
     * Override the default friction mixture. You can call this in
     * {@link ContactListener#preSolve(Contact, Manifold)}. This value persists
     * until set or reset.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_contact.h#L129-L131
     */
    public void setFriction(float friction)
    {
        this.friction = friction;
    }

    /**
     * Get the friction.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_contact.h#L133-L134
     */
    public float getFriction()
    {
        return friction;
    }

    /**
     * Reset the friction mixture to the default value.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_contact.h#L136-L137
     */
    public void resetFriction()
    {
        friction = Contact.mixFriction(fixtureA.friction, fixtureB.friction);
    }

    /**
     * Override the default restitution mixture. You can call this in
     * {@link ContactListener#preSolve(Contact, Manifold)}. The value persists
     * until you set or reset.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_contact.h#L139-L141
     */
    public void setRestitution(float restitution)
    {
        this.restitution = restitution;
    }

    /**
     * Get the restitution.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_contact.h#L143-L144
     */
    public float getRestitution()
    {
        return restitution;
    }

    /**
     * Reset the restitution to the default value.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_contact.h#L146-L147
     */
    public void resetRestitution()
    {
        restitution = Contact.mixRestitution(fixtureA.restitution,
            fixtureB.restitution);
    }

    /**
     * Set the desired tangent speed for a conveyor belt behavior. In meters per
     * second.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_contact.h#L159-L160
     */
    public void setTangentSpeed(float speed)
    {
        tangentSpeed = speed;
    }

    /**
     * Get the desired tangent speed. In meters per second.
     *
     * @return the desired tangent speed. In meters per second.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_contact.h#L162-L163
     */
    public float getTangentSpeed()
    {
        return tangentSpeed;
    }

    /**
     * Evaluate this contact with your own manifold and transforms.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_contact.h#L165-L166
     */
    public abstract void evaluate(Manifold manifold, Transform xfA,
            Transform xfB);

    /**
     * Flag this contact for filtering. Filtering will occur the next time step.
     */
    public void flagForFiltering()
    {
        flags |= FILTER_FLAG;
    }

    // djm pooling
    private final Manifold oldManifold = new Manifold();

    @SuppressWarnings({ "squid:S3776", "java:S6541" })
    public void update(ContactListener listener)
    {
        oldManifold.set(manifold);
        // Re-enable this contact.
        flags |= ENABLED_FLAG;
        boolean touching;
        boolean wasTouching = (flags & TOUCHING_FLAG) == TOUCHING_FLAG;
        boolean sensorA = fixtureA.isSensor();
        boolean sensorB = fixtureB.isSensor();
        boolean sensor = sensorA || sensorB;
        Body bodyA = fixtureA.getBody();
        Body bodyB = fixtureB.getBody();
        Transform xfA = bodyA.getTransform();
        Transform xfB = bodyB.getTransform();
        // log.debug("TransformA: "+xfA);
        // log.debug("TransformB: "+xfB);
        if (sensor)
        {
            Shape shapeA = fixtureA.getShape();
            Shape shapeB = fixtureB.getShape();
            touching = pool.getCollision()
                .testOverlap(shapeA, indexA, shapeB, indexB, xfA, xfB);
            // Sensors don't generate manifolds.
            manifold.pointCount = 0;
        }
        else
        {
            evaluate(manifold, xfA, xfB);
            touching = manifold.pointCount > 0;
            // Match old contact ids to new contact ids and copy the
            // stored impulses to warm start the solver.
            for (int i = 0; i < manifold.pointCount; ++i)
            {
                ManifoldPoint mp2 = manifold.points[i];
                mp2.normalImpulse = 0.0f;
                mp2.tangentImpulse = 0.0f;
                ContactID id2 = mp2.id;
                for (int j = 0; j < oldManifold.pointCount; ++j)
                {
                    ManifoldPoint mp1 = oldManifold.points[j];
                    if (mp1.id.isEqual(id2))
                    {
                        mp2.normalImpulse = mp1.normalImpulse;
                        mp2.tangentImpulse = mp1.tangentImpulse;
                        break;
                    }
                }
            }
            if (touching != wasTouching)
            {
                bodyA.setAwake(true);
                bodyB.setAwake(true);
            }
        }
        if (touching)
        {
            flags |= TOUCHING_FLAG;
        }
        else
        {
            flags &= ~TOUCHING_FLAG;
        }
        if (listener == null)
        {
            return;
        }
        if (!wasTouching && touching)
        {
            listener.beginContact(this);
        }
        if (wasTouching && !touching)
        {
            listener.endContact(this);
        }
        if (!sensor && touching)
        {
            listener.preSolve(this, oldManifold);
        }
    }

    /**
     * Friction mixing law. The idea is to allow either fixture to drive the
     * restitution to zero. For example, anything slides on ice.
     */
    public static float mixFriction(float friction1, float friction2)
    {
        return MathUtils.sqrt(friction1 * friction2);
    }

    /**
     * Restitution mixing law. The idea is allowed for anything to bounce off an
     * inelastic surface. For example, a superball bounces on anything.
     */
    public static float mixRestitution(float restitution1, float restitution2)
    {
        return Math.max(restitution1, restitution2);
    }
}
