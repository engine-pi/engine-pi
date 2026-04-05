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

import org.jbox2d.callbacks.ContactFilter;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.callbacks.DestructionListener;
import org.jbox2d.callbacks.ParticleDestructionListener;
import org.jbox2d.callbacks.ParticleQueryCallback;
import org.jbox2d.callbacks.ParticleRaycastCallback;
import org.jbox2d.callbacks.QueryCallback;
import org.jbox2d.callbacks.RayCastCallback;
import org.jbox2d.callbacks.TreeCallback;
import org.jbox2d.callbacks.TreeRayCastCallback;
import org.jbox2d.collision.AABB;
import org.jbox2d.collision.RayCastInput;
import org.jbox2d.collision.RayCastOutput;
import org.jbox2d.collision.TimeOfImpact.TOIInput;
import org.jbox2d.collision.TimeOfImpact.TOIOutput;
import org.jbox2d.collision.TimeOfImpact.TOIOutputState;
import org.jbox2d.collision.broadphase.BroadPhase;
import org.jbox2d.collision.broadphase.BroadPhaseStrategy;
import org.jbox2d.collision.broadphase.DefaultBroadPhaseBuffer;
import org.jbox2d.collision.broadphase.DynamicTree;
import org.jbox2d.collision.shapes.ChainShape;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.collision.shapes.ShapeType;
import org.jbox2d.common.Color3f;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Settings;
import org.jbox2d.common.Sweep;
import org.jbox2d.common.Timer;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.contacts.Contact;
import org.jbox2d.dynamics.contacts.ContactEdge;
import org.jbox2d.dynamics.contacts.ContactRegister;
import org.jbox2d.dynamics.joints.Joint;
import org.jbox2d.dynamics.joints.JointDef;
import org.jbox2d.dynamics.joints.JointEdge;
import org.jbox2d.dynamics.joints.PulleyJoint;
import org.jbox2d.particle.ParticleBodyContact;
import org.jbox2d.particle.ParticleColor;
import org.jbox2d.particle.ParticleContact;
import org.jbox2d.particle.ParticleDef;
import org.jbox2d.particle.ParticleGroup;
import org.jbox2d.particle.ParticleGroupDef;
import org.jbox2d.particle.ParticleSystem;
import org.jbox2d.pooling.DynamicStack;
import org.jbox2d.pooling.WorldPool;
import org.jbox2d.pooling.arrays.Vec2Array;
import org.jbox2d.pooling.normal.DefaultWorldPool;

/**
 * The world-class manages all physics entities, dynamic simulation, and
 * asynchronous queries. The world also contains efficient memory management
 * facilities.
 *
 * @author Daniel Murphy
 *
 * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_world.h#L43-L346
 */
public class World
{
    public static final int WORLD_POOL_SIZE = 100;

    public static final int WORLD_POOL_CONTAINER_SIZE = 10;

    public static final int NEW_FIXTURE = 0x0001;

    public static final int LOCKED = 0x0002;

    public static final int CLEAR_FORCES = 0x0004;

    // statistics gathering
    public int activeContacts = 0;

    public int contactPoolCount = 0;

    protected int flags;

    protected ContactManager contactManager;

    private Body bodyList;

    private Joint jointList;

    private int bodyCount;

    private int jointCount;

    private final Vec2 gravity = new Vec2();

    private boolean allowSleep;
    // private Body groundBody;

    private DestructionListener destructionListener;

    private ParticleDestructionListener particleDestructionListener;

    private DebugDraw debugDraw;

    /**
     * The world pool that provides pooling for all objects used in the engine.
     */
    private final WorldPool pool;

    /**
     * This is used to compute the time step ratio to support a variable time
     * step.
     */
    private float invDt0;

    // these are for debugging the solver
    private boolean warmStarting;

    private boolean continuousPhysics;

    private boolean subStepping;

    private boolean stepComplete;

    private final Profile profile;

    private final ParticleSystem particleSystem;

    private final ContactRegister[][] contactStacks = new ContactRegister[ShapeType
        .values().length][ShapeType.values().length];

    /**
     * Construct a world object.
     *
     * @param gravity The world gravity vector.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_world.h#L49-L51
     */
    public World(Vec2 gravity)
    {
        this(gravity, new DefaultWorldPool(WORLD_POOL_SIZE,
                WORLD_POOL_CONTAINER_SIZE));
    }

    /**
     * Construct a world object.
     *
     * @param gravity The world gravity vector.
     * @param pool The world pool that provides pooling for all objects used in
     *     the engine.
     */
    public World(Vec2 gravity, WorldPool pool)
    {
        this(gravity, pool, new DynamicTree());
    }

    /**
     * Construct a world object.
     *
     * @param gravity The world gravity vector.
     * @param pool The world pool that provides pooling for all objects used in
     *     the engine.
     * @param strategy The broad phase strategy.
     *
     */
    public World(Vec2 gravity, WorldPool pool, BroadPhaseStrategy strategy)
    {
        this(gravity, pool, new DefaultBroadPhaseBuffer(strategy));
    }

    /**
     * Construct a world object.
     *
     * @param gravity The world gravity vector.
     */
    public World(Vec2 gravity, WorldPool pool, BroadPhase broadPhase)
    {
        this.pool = pool;
        destructionListener = null;
        debugDraw = null;
        bodyList = null;
        jointList = null;
        bodyCount = 0;
        jointCount = 0;
        warmStarting = true;
        continuousPhysics = true;
        subStepping = false;
        stepComplete = true;
        allowSleep = true;
        this.gravity.set(gravity);
        flags = CLEAR_FORCES;
        invDt0 = 0f;
        contactManager = new ContactManager(this, broadPhase);
        profile = new Profile();
        particleSystem = new ParticleSystem(this);
        initializeRegisters();
    }

    public void setAllowSleep(boolean flag)
    {
        if (flag == allowSleep)
        {
            return;
        }
        allowSleep = flag;
        if (!allowSleep)
        {
            for (Body b = bodyList; b != null; b = b.next)
            {
                b.setAwake(true);
            }
        }
    }

    public void setSubStepping(boolean subStepping)
    {
        this.subStepping = subStepping;
    }

    public boolean isSubStepping()
    {
        return subStepping;
    }

    public boolean isAllowSleep()
    {
        return allowSleep;
    }

    private void addType(DynamicStack<Contact> creator, ShapeType type1,
            ShapeType type2)
    {
        ContactRegister register = new ContactRegister();
        register.creator = creator;
        register.primary = true;
        contactStacks[type1.ordinal()][type2.ordinal()] = register;
        if (type1 != type2)
        {
            ContactRegister register2 = new ContactRegister();
            register2.creator = creator;
            register2.primary = false;
            contactStacks[type2.ordinal()][type1.ordinal()] = register2;
        }
    }

    private void initializeRegisters()
    {
        addType(pool.getCircleContactStack(),
            ShapeType.CIRCLE,
            ShapeType.CIRCLE);
        addType(pool.getPolyCircleContactStack(),
            ShapeType.POLYGON,
            ShapeType.CIRCLE);
        addType(pool.getPolyContactStack(),
            ShapeType.POLYGON,
            ShapeType.POLYGON);
        addType(pool.getEdgeCircleContactStack(),
            ShapeType.EDGE,
            ShapeType.CIRCLE);
        addType(pool.getEdgePolyContactStack(),
            ShapeType.EDGE,
            ShapeType.POLYGON);
        addType(pool.getChainCircleContactStack(),
            ShapeType.CHAIN,
            ShapeType.CIRCLE);
        addType(pool.getChainPolyContactStack(),
            ShapeType.CHAIN,
            ShapeType.POLYGON);
    }

    public DestructionListener getDestructionListener()
    {
        return destructionListener;
    }

    public ParticleDestructionListener getParticleDestructionListener()
    {
        return particleDestructionListener;
    }

    public void setParticleDestructionListener(
            ParticleDestructionListener listener)
    {
        particleDestructionListener = listener;
    }

    public Contact popContact(Fixture fixtureA, int indexA, Fixture fixtureB,
            int indexB)
    {
        final ShapeType type1 = fixtureA.getType();
        final ShapeType type2 = fixtureB.getType();
        final ContactRegister reg = contactStacks[type1.ordinal()][type2
            .ordinal()];
        if (reg != null)
        {
            Contact c = reg.creator.pop();
            if (reg.primary)
            {
                c.init(fixtureA, indexA, fixtureB, indexB);
            }
            else
            {
                c.init(fixtureB, indexB, fixtureA, indexA);
            }
            return c;
        }
        else
        {
            return null;
        }
    }

    public void pushContact(Contact contact)
    {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        if (contact.manifold.pointCount > 0 && !fixtureA.isSensor()
                && !fixtureB.isSensor())
        {
            fixtureA.getBody().setAwake(true);
            fixtureB.getBody().setAwake(true);
        }
        ShapeType type1 = fixtureA.getType();
        ShapeType type2 = fixtureB.getType();
        DynamicStack<Contact> creator = contactStacks[type1.ordinal()][type2
            .ordinal()].creator;
        creator.push(contact);
    }

    public WorldPool getPool()
    {
        return pool;
    }

    /**
     * Register a destruction listener. The listener is owned by you and must
     * remain in scope.
     */
    public void setDestructionListener(DestructionListener listener)
    {
        destructionListener = listener;
    }

    /**
     * Register a contact filter to provide specific control over collision.
     * Otherwise, the default filter is used (_defaultFilter). The listener is
     * owned by you and must remain in scope.
     */
    public void setContactFilter(ContactFilter filter)
    {
        contactManager.contactFilter = filter;
    }

    /**
     * Register a contact event listener. The listener is owned by you and must
     * remain in scope.
     */
    public void setContactListener(ContactListener listener)
    {
        contactManager.contactListener = listener;
    }

    /**
     * Register a routine for debug drawing. The debug draw functions are called
     * inside with World.DrawDebugData method. The debug draw object is owned by
     * you and must remain in scope.
     */
    public void setDebugDraw(DebugDraw debugDraw)
    {
        this.debugDraw = debugDraw;
    }

    /**
     * create a rigid body given a definition. No reference to the definition is
     * retained.
     *
     * @warning This function is locked during callbacks.
     */
    public Body createBody(BodyDef def)
    {
        assert (!isLocked());
        if (isLocked())
        {
            return null;
        }
        // TODO djm pooling
        Body b = new Body(def, this);
        // add to the world a doubly linked list
        b.prev = null;
        b.next = bodyList;
        if (bodyList != null)
        {
            bodyList.prev = b;
        }
        bodyList = b;
        ++bodyCount;
        return b;
    }

    /**
     * Destroy a rigid body given a definition. No reference to the definition
     * is retained. This function is locked during callbacks.
     *
     * @warning This automatically deletes all associated shapes and joints.
     * @warning This function is locked during callbacks.
     */
    public void destroyBody(Body body)
    {
        assert (bodyCount > 0);
        assert (!isLocked());
        if (isLocked())
        {
            return;
        }
        // Delete the attached joints.
        JointEdge je = body.jointList;
        while (je != null)
        {
            JointEdge je0 = je;
            je = je.next;
            if (destructionListener != null)
            {
                destructionListener.sayGoodbye(je0.joint);
            }
            destroyJoint(je0.joint);
            body.jointList = je;
        }
        // Delete the attached contacts.
        ContactEdge ce = body.contactList;
        while (ce != null)
        {
            ContactEdge ce0 = ce;
            ce = ce.next;
            contactManager.destroy(ce0.contact);
        }
        body.contactList = null;
        Fixture f = body.fixtureList;
        while (f != null)
        {
            Fixture f0 = f;
            f = f.next;
            if (destructionListener != null)
            {
                destructionListener.sayGoodbye(f0);
            }
            f0.destroyProxies(contactManager.broadPhase);
            f0.destroy();
            // TODO djm recycle fixtures (here or in that destroy method)
            body.fixtureList = f;
            body.fixtureCount -= 1;
        }
        body.fixtureCount = 0;
        // Remove world body list.
        if (body.prev != null)
        {
            body.prev.next = body.next;
        }
        if (body.next != null)
        {
            body.next.prev = body.prev;
        }
        if (body == bodyList)
        {
            bodyList = body.next;
        }
        --bodyCount;
        // TODO djm recycle body
    }

    /**
     * create a joint to constrain bodies together. No reference to the
     * definition is retained. This may cause the connected bodies to cease
     * colliding.
     *
     * @warning This function is locked during callbacks.
     */
    public Joint createJoint(JointDef def)
    {
        assert (!isLocked());
        if (isLocked())
        {
            return null;
        }
        Joint j = Joint.create(this, def);
        // Connect to the world list.
        j.prev = null;
        j.next = jointList;
        if (jointList != null)
        {
            jointList.prev = j;
        }
        jointList = j;
        ++jointCount;
        // Connect to the bodies' doubly linked lists.
        j.edgeA.joint = j;
        j.edgeA.other = j.getBodyB();
        j.edgeA.prev = null;
        j.edgeA.next = j.getBodyA().jointList;
        if (j.getBodyA().jointList != null)
        {
            j.getBodyA().jointList.prev = j.edgeA;
        }
        j.getBodyA().jointList = j.edgeA;
        j.edgeB.joint = j;
        j.edgeB.other = j.getBodyA();
        j.edgeB.prev = null;
        j.edgeB.next = j.getBodyB().jointList;
        if (j.getBodyB().jointList != null)
        {
            j.getBodyB().jointList.prev = j.edgeB;
        }
        j.getBodyB().jointList = j.edgeB;
        Body bodyA = def.bodyA;
        Body bodyB = def.bodyB;
        // If the joint prevents collisions, then flag any contacts for
        // filtering.
        if (!def.collideConnected)
        {
            ContactEdge edge = bodyB.getContactList();
            while (edge != null)
            {
                if (edge.other == bodyA)
                {
                    // Flag the contact for filtering at the next time step
                    // (where either
                    // body is awake).
                    edge.contact.flagForFiltering();
                }
                edge = edge.next;
            }
        }
        // Note: creating a joint doesn't wake the bodies.
        return j;
    }

    /**
     * destroy a joint. This may cause the connected bodies to begin colliding.
     *
     * @warning This function is locked during callbacks.
     */
    public void destroyJoint(Joint j)
    {
        assert (!isLocked());
        if (isLocked())
        {
            return;
        }
        boolean collideConnected = j.getCollideConnected();
        // Remove from the doubly linked list.
        if (j.prev != null)
        {
            j.prev.next = j.next;
        }
        if (j.next != null)
        {
            j.next.prev = j.prev;
        }
        if (j == jointList)
        {
            jointList = j.next;
        }
        // Disconnect from island graph.
        Body bodyA = j.getBodyA();
        Body bodyB = j.getBodyB();
        // Wake up connected bodies.
        bodyA.setAwake(true);
        bodyB.setAwake(true);
        // Remove from body 1.
        if (j.edgeA.prev != null)
        {
            j.edgeA.prev.next = j.edgeA.next;
        }
        if (j.edgeA.next != null)
        {
            j.edgeA.next.prev = j.edgeA.prev;
        }
        if (j.edgeA == bodyA.jointList)
        {
            bodyA.jointList = j.edgeA.next;
        }
        j.edgeA.prev = null;
        j.edgeA.next = null;
        // Remove from body 2
        if (j.edgeB.prev != null)
        {
            j.edgeB.prev.next = j.edgeB.next;
        }
        if (j.edgeB.next != null)
        {
            j.edgeB.next.prev = j.edgeB.prev;
        }
        if (j.edgeB == bodyB.jointList)
        {
            bodyB.jointList = j.edgeB.next;
        }
        j.edgeB.prev = null;
        j.edgeB.next = null;
        Joint.destroy(j);
        assert (jointCount > 0);
        --jointCount;
        // If the joint prevents collisions, then flag any contacts for
        // filtering.
        if (!collideConnected)
        {
            ContactEdge edge = bodyB.getContactList();
            while (edge != null)
            {
                if (edge.other == bodyA)
                {
                    // Flag the contact for filtering at the next time step
                    // (where either
                    // body is awake).
                    edge.contact.flagForFiltering();
                }
                edge = edge.next;
            }
        }
    }

    // djm pooling
    private final TimeStep step = new TimeStep();

    private final Timer stepTimer = new Timer();

    private final Timer tempTimer = new Timer();

    /**
     * Take a time step. This performs collision detection, integration, and
     * constraint solution.
     *
     * @param timeStep The amount of time to simulate, this should not vary.
     * @param velocityIterations For the velocity constraint solver.
     * @param positionIterations For the position constraint solver.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_world.h#L94-L101
     */
    public void step(float timeStep, int velocityIterations,
            int positionIterations)
    {
        stepTimer.reset();
        tempTimer.reset();
        // log.debug("Starting step");
        // If new fixtures were added, we need to find the new contacts.
        if ((flags & NEW_FIXTURE) == NEW_FIXTURE)
        {
            // log.debug("There's a new fixture, lets look for new contacts");
            contactManager.findNewContacts();
            flags &= ~NEW_FIXTURE;
        }
        flags |= LOCKED;
        step.dt = timeStep;
        step.velocityIterations = velocityIterations;
        step.positionIterations = positionIterations;
        if (timeStep > 0.0f)
        {
            step.inverseDt = 1.0f / timeStep;
        }
        else
        {
            step.inverseDt = 0.0f;
        }
        step.dtRatio = invDt0 * timeStep;
        step.warmStarting = warmStarting;
        profile.stepInit.record(tempTimer.getMilliseconds());
        // Update contacts. This is where some contacts are destroyed.
        tempTimer.reset();
        contactManager.collide();
        profile.collide.record(tempTimer.getMilliseconds());
        // Integrate velocities, solve velocity constraints, and integrate
        // positions.
        if (stepComplete && step.dt > 0.0f)
        {
            tempTimer.reset();
            particleSystem.solve(step); // Particle Simulation
            profile.solveParticleSystem.record(tempTimer.getMilliseconds());
            tempTimer.reset();
            solve(step);
            profile.solve.record(tempTimer.getMilliseconds());
        }
        // Handle TOI events.
        if (continuousPhysics && step.dt > 0.0f)
        {
            tempTimer.reset();
            solveTOI(step);
            profile.solveTOI.record(tempTimer.getMilliseconds());
        }
        if (step.dt > 0.0f)
        {
            invDt0 = step.inverseDt;
        }
        if ((flags & CLEAR_FORCES) == CLEAR_FORCES)
        {
            clearForces();
        }
        flags &= ~LOCKED;
        // log.debug("ending step");
        profile.step.record(stepTimer.getMilliseconds());
    }

    /**
     * Call this after you are done with time steps to clear the forces. You
     * normally call this after each call to Step, unless you are performing
     * sub-steps. By default, forces will be automatically cleared, so you don't
     * need to call this function.
     *
     * @see #setAutoClearForces(boolean)
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_world.h#L103-L110
     */
    public void clearForces()
    {
        for (Body body = bodyList; body != null; body = body.getNext())
        {
            body.force.setZero();
            body.torque = 0.0f;
        }
    }

    private final Color3f color = new Color3f();

    private final Transform xf = new Transform();

    private final Vec2 cA = new Vec2();

    private final Vec2 cB = new Vec2();

    private final Vec2Array avs = new Vec2Array();

    /**
     * Call this to draw shapes and other debug draw data.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_world.h#L112-L113
     */
    public void drawDebugData()
    {
        if (debugDraw == null)
        {
            return;
        }
        int flags = debugDraw.getFlags();
        boolean wireframe = (flags & DebugDraw.wireframeDrawingBit) != 0;
        if ((flags & DebugDraw.shapeBit) != 0)
        {
            for (Body b = bodyList; b != null; b = b.getNext())
            {
                xf.set(b.getTransform());
                for (Fixture f = b.getFixtureList(); f != null; f = f.getNext())
                {
                    if (!b.isActive())
                    {
                        color.set(0.5f, 0.5f, 0.3f);
                        drawShape(f, xf, color, wireframe);
                    }
                    else if (b.getType() == BodyType.STATIC)
                    {
                        color.set(0.5f, 0.9f, 0.3f);
                        drawShape(f, xf, color, wireframe);
                    }
                    else if (b.getType() == BodyType.KINEMATIC)
                    {
                        color.set(0.5f, 0.5f, 0.9f);
                        drawShape(f, xf, color, wireframe);
                    }
                    else if (!b.isAwake())
                    {
                        color.set(0.5f, 0.5f, 0.5f);
                        drawShape(f, xf, color, wireframe);
                    }
                    else
                    {
                        color.set(0.9f, 0.7f, 0.7f);
                        drawShape(f, xf, color, wireframe);
                    }
                }
            }
            drawParticleSystem(particleSystem);
        }
        if ((flags & DebugDraw.jointBit) != 0)
        {
            for (Joint j = jointList; j != null; j = j.getNext())
            {
                drawJoint(j);
            }
        }
        if ((flags & DebugDraw.pairBit) != 0)
        {
            color.set(0.3f, 0.9f, 0.9f);
            for (Contact c = contactManager.contactList; c != null; c = c
                .getNext())
            {
                Fixture fixtureA = c.getFixtureA();
                Fixture fixtureB = c.getFixtureB();
                fixtureA.getAABB(c.getChildIndexA()).getCenterToOut(cA);
                fixtureB.getAABB(c.getChildIndexB()).getCenterToOut(cB);
                debugDraw.drawSegment(cA, cB, color);
            }
        }
        if ((flags & DebugDraw.aabbBit) != 0)
        {
            color.set(0.9f, 0.3f, 0.9f);
            for (Body b = bodyList; b != null; b = b.getNext())
            {
                if (!b.isActive())
                {
                    continue;
                }
                for (Fixture f = b.getFixtureList(); f != null; f = f.getNext())
                {
                    for (int i = 0; i < f.proxyCount; ++i)
                    {
                        FixtureProxy proxy = f.proxies[i];
                        AABB aabb = contactManager.broadPhase
                            .getFatAABB(proxy.proxyId);
                        if (aabb != null)
                        {
                            Vec2[] vs = avs.get(4);
                            vs[0].set(aabb.lowerBound.x, aabb.lowerBound.y);
                            vs[1].set(aabb.upperBound.x, aabb.lowerBound.y);
                            vs[2].set(aabb.upperBound.x, aabb.upperBound.y);
                            vs[3].set(aabb.lowerBound.x, aabb.upperBound.y);
                            debugDraw.drawPolygon(vs, 4, color);
                        }
                    }
                }
            }
        }
        if ((flags & DebugDraw.centerOfMassBit) != 0)
        {
            for (Body b = bodyList; b != null; b = b.getNext())
            {
                xf.set(b.getTransform());
                xf.p.set(b.getWorldCenter());
                debugDraw.drawTransform(xf);
            }
        }
        if ((flags & DebugDraw.dynamicTreeBit) != 0)
        {
            contactManager.broadPhase.drawTree(debugDraw);
        }
        debugDraw.flush();
    }

    private final WorldQueryWrapper wqwrapper = new WorldQueryWrapper();

    /**
     * Query the world for all fixtures that potentially overlap the provided
     * AABB.
     *
     * @param callback A user implemented callback class.
     * @param aabb The query box. The axis-aligned bounding box.
     */
    public void queryAABB(QueryCallback callback, AABB aabb)
    {
        wqwrapper.broadPhase = contactManager.broadPhase;
        wqwrapper.callback = callback;
        contactManager.broadPhase.query(wqwrapper, aabb);
    }

    /**
     * Query the world for all fixtures and particles that potentially overlap
     * the provided AABB.
     *
     * @param callback A user implemented callback class.
     * @param particleCallback A callback for particles.
     * @param aabb The query box. The axis-aligned bounding box.
     */
    public void queryAABB(QueryCallback callback,
            ParticleQueryCallback particleCallback, AABB aabb)
    {
        wqwrapper.broadPhase = contactManager.broadPhase;
        wqwrapper.callback = callback;
        contactManager.broadPhase.query(wqwrapper, aabb);
        particleSystem.queryAABB(particleCallback, aabb);
    }

    /**
     * Query the world for all particles that potentially overlap the provided
     * AABB.
     *
     * @param particleCallback The callback for particles.
     * @param aabb The query box. The axis-aligned bounding box.
     */
    public void queryAABB(ParticleQueryCallback particleCallback, AABB aabb)
    {
        particleSystem.queryAABB(particleCallback, aabb);
    }

    private final WorldRayCastWrapper wrcwrapper = new WorldRayCastWrapper();

    private final RayCastInput input = new RayCastInput();

    /**
     * Ray-cast the world for all fixtures in the path of the ray. Your callback
     * controls whether you get the closest point, any point, or n-points. The
     * ray-cast ignores shapes that contain the starting point.
     *
     * @param callback A user implemented callback class.
     * @param point1 The ray starting point.
     * @param point2 The ray ending point.
     */
    public void raycast(RayCastCallback callback, Vec2 point1, Vec2 point2)
    {
        wrcwrapper.broadPhase = contactManager.broadPhase;
        wrcwrapper.callback = callback;
        input.maxFraction = 1.0f;
        input.p1.set(point1);
        input.p2.set(point2);
        contactManager.broadPhase.raycast(wrcwrapper, input);
    }

    /**
     * Ray-cast the world for all fixtures and particles in the path of the ray.
     * Your callback controls whether you get the closest point, any point, or
     * n-points. The ray-cast ignores shapes that contain the starting point.
     *
     * @param callback A user implemented callback class.
     * @param particleCallback The particle callback class.
     * @param point1 The ray starting point
     * @param point2 The ray ending point
     */
    public void raycast(RayCastCallback callback,
            ParticleRaycastCallback particleCallback, Vec2 point1, Vec2 point2)
    {
        wrcwrapper.broadPhase = contactManager.broadPhase;
        wrcwrapper.callback = callback;
        input.maxFraction = 1.0f;
        input.p1.set(point1);
        input.p2.set(point2);
        contactManager.broadPhase.raycast(wrcwrapper, input);
        particleSystem.raycast(particleCallback, point1, point2);
    }

    /**
     * Ray-cast the world for all particles in the path of the ray. Your
     * callback controls whether you get the closest point, any point, or
     * n-points.
     *
     * @param particleCallback The particle callback class.
     * @param point1 The ray starting point
     * @param point2 The ray ending point
     */
    public void raycast(ParticleRaycastCallback particleCallback, Vec2 point1,
            Vec2 point2)
    {
        particleSystem.raycast(particleCallback, point1, point2);
    }

    /**
     * Get the world body list. With the returned body, use Body.getNext to get
     * the next body in the world list. A null body indicates the end of the
     * list.
     *
     * @return The head of the world body list.
     */
    public Body getBodyList()
    {
        return bodyList;
    }

    /**
     * Get the world joint list. With the returned joint, use Joint.getNext to
     * get the next joint in the world list. A null joint indicates the end of
     * the list.
     *
     * @return The head of the world joint list.
     */
    public Joint getJointList()
    {
        return jointList;
    }

    /**
     * Get the world contact list. With the returned contact, use
     * Contact.getNext to get the next contact in the world list. A null contact
     * indicates the end of the list.
     *
     * @return The head of the world contact list.
     *
     * @warning contacts are created and destroyed in the middle of a time step.
     *     Use ContactListener to avoid missing contacts.
     */
    public Contact getContactList()
    {
        return contactManager.contactList;
    }

    public boolean isSleepingAllowed()
    {
        return allowSleep;
    }

    public void setSleepingAllowed(boolean sleepingAllowed)
    {
        allowSleep = sleepingAllowed;
    }

    /**
     * Enable/disable warm starting. For testing.
     */
    public void setWarmStarting(boolean flag)
    {
        warmStarting = flag;
    }

    public boolean isWarmStarting()
    {
        return warmStarting;
    }

    /**
     * Enable/disable continuous physics. For testing.
     */
    public void setContinuousPhysics(boolean flag)
    {
        continuousPhysics = flag;
    }

    public boolean isContinuousPhysics()
    {
        return continuousPhysics;
    }

    /**
     * Get the number of broad-phase proxies.
     */
    public int getProxyCount()
    {
        return contactManager.broadPhase.getProxyCount();
    }

    /**
     * Get the number of bodies.
     */
    public int getBodyCount()
    {
        return bodyCount;
    }

    /**
     * Get the number of joints.
     */
    public int getJointCount()
    {
        return jointCount;
    }

    /**
     * Get the number of contacts (each may have 0 or more contact points).
     */
    public int getContactCount()
    {
        return contactManager.contactCount;
    }

    /**
     * Get the height of the dynamic tree.
     */
    public int getTreeHeight()
    {
        return contactManager.broadPhase.getTreeHeight();
    }

    /**
     * Get the balance of the dynamic tree.
     */
    public int getTreeBalance()
    {
        return contactManager.broadPhase.getTreeBalance();
    }

    /**
     * Get the quality of the dynamic tree.
     */
    public float getTreeQuality()
    {
        return contactManager.broadPhase.getTreeQuality();
    }

    /**
     * Change the global gravity vector.
     */
    public void setGravity(Vec2 gravity)
    {
        this.gravity.set(gravity);
    }

    /**
     * Get the global gravity vector.
     */
    public Vec2 getGravity()
    {
        return gravity;
    }

    /**
     * Is the world locked (in the middle of a time step).
     */
    public boolean isLocked()
    {
        return (flags & LOCKED) == LOCKED;
    }

    /**
     * Set flag to control automatic clearing of forces after each time step.
     */
    public void setAutoClearForces(boolean flag)
    {
        if (flag)
        {
            flags |= CLEAR_FORCES;
        }
        else
        {
            flags &= ~CLEAR_FORCES;
        }
    }

    /**
     * Get the flag that controls automatic clearing of forces after each time
     * step.
     */
    public boolean getAutoClearForces()
    {
        return (flags & CLEAR_FORCES) == CLEAR_FORCES;
    }

    /**
     * Get the contact manager for testing purposes
     */
    public ContactManager getContactManager()
    {
        return contactManager;
    }

    public Profile getProfile()
    {
        return profile;
    }

    private final Island island = new Island();

    private Body[] stack = new Body[10]; // TODO djm find a good initial stack
                                         // number;

    private final Timer broadphaseTimer = new Timer();

    private void solve(TimeStep step)
    {
        profile.solveInit.startAccum();
        profile.solveVelocity.startAccum();
        profile.solvePosition.startAccum();
        // update previous transforms
        for (Body b = bodyList; b != null; b = b.next)
        {
            b.xf0.set(b.xf);
        }
        // Size the island for the worst case.
        island.init(bodyCount,
            contactManager.contactCount,
            jointCount,
            contactManager.contactListener);
        // Clear all the island flags.
        for (Body b = bodyList; b != null; b = b.next)
        {
            b.flags &= ~Body.islandFlag;
        }
        for (Contact c = contactManager.contactList; c != null; c = c.next)
        {
            c.flags &= ~Contact.ISLAND_FLAG;
        }
        for (Joint j = jointList; j != null; j = j.next)
        {
            j.islandFlag = false;
        }
        // Build and simulate all awake islands.
        int stackSize = bodyCount;
        if (stack.length < stackSize)
        {
            stack = new Body[stackSize];
        }
        for (Body seed = bodyList; seed != null; seed = seed.next)
        {
            if ((seed.flags & Body.islandFlag) == Body.islandFlag)
            {
                continue;
            }
            if (!seed.isAwake() || !seed.isActive())
            {
                continue;
            }
            // The seed can be dynamic or kinematic.
            if (seed.getType() == BodyType.STATIC)
            {
                continue;
            }
            // Reset island and stack.
            island.clear();
            int stackCount = 0;
            stack[stackCount++] = seed;
            seed.flags |= Body.islandFlag;
            // Perform a depth first search (DFS) on the constraint graph.
            while (stackCount > 0)
            {
                // Grab the next body off the stack and add it to the island.
                Body b = stack[--stackCount];
                assert (b.isActive());
                island.add(b);
                // Make sure the body is awake.
                b.setAwake(true);
                // To keep islands as small as possible, we don't
                // propagate islands across static bodies.
                if (b.getType() == BodyType.STATIC)
                {
                    continue;
                }
                // Search all contacts connected to this body.
                for (ContactEdge ce = b.contactList; ce != null; ce = ce.next)
                {
                    Contact contact = ce.contact;
                    // Has this contact already been added to an island?
                    if ((contact.flags
                            & Contact.ISLAND_FLAG) == Contact.ISLAND_FLAG)
                    {
                        continue;
                    }
                    // Is this contact solid and touching?
                    if (!contact.isEnabled() || !contact.isTouching())
                    {
                        continue;
                    }
                    // Skip sensors.
                    boolean sensorA = contact.fixtureA.isSensor;
                    boolean sensorB = contact.fixtureB.isSensor;
                    if (sensorA || sensorB)
                    {
                        continue;
                    }
                    island.add(contact);
                    contact.flags |= Contact.ISLAND_FLAG;
                    Body other = ce.other;
                    // Was the other body already added to this island?
                    if ((other.flags & Body.islandFlag) == Body.islandFlag)
                    {
                        continue;
                    }
                    assert (stackCount < stackSize);
                    stack[stackCount++] = other;
                    other.flags |= Body.islandFlag;
                }
                // Search all joints connect to this body.
                for (JointEdge je = b.jointList; je != null; je = je.next)
                {
                    if (je.joint.islandFlag)
                    {
                        continue;
                    }
                    Body other = je.other;
                    // Don't simulate joints connected to inactive bodies.
                    if (!other.isActive())
                    {
                        continue;
                    }
                    island.add(je.joint);
                    je.joint.islandFlag = true;
                    if ((other.flags & Body.islandFlag) == Body.islandFlag)
                    {
                        continue;
                    }
                    assert (stackCount < stackSize);
                    stack[stackCount++] = other;
                    other.flags |= Body.islandFlag;
                }
            }
            island.solve(profile, step, gravity, allowSleep);
            // Post solve cleanup.
            for (int i = 0; i < island.bodyCount; ++i)
            {
                // Allow static bodies to participate in other islands.
                Body b = island.bodies[i];
                if (b.getType() == BodyType.STATIC)
                {
                    b.flags &= ~Body.islandFlag;
                }
            }
        }
        profile.solveInit.endAccum();
        profile.solveVelocity.endAccum();
        profile.solvePosition.endAccum();
        broadphaseTimer.reset();
        // Synchronize fixtures, check for out of range bodies.
        for (Body b = bodyList; b != null; b = b.getNext())
        {
            // If a body was not in an island then it did not move.
            if ((b.flags & Body.islandFlag) == 0)
            {
                continue;
            }
            if (b.getType() == BodyType.STATIC)
            {
                continue;
            }
            // Update fixtures (for broad-phase).
            b.synchronizeFixtures();
        }
        // Look for new contacts.
        contactManager.findNewContacts();
        profile.broadphase.record(broadphaseTimer.getMilliseconds());
    }

    private final Island toiIsland = new Island();

    private final TOIInput toiInput = new TOIInput();

    private final TOIOutput toiOutput = new TOIOutput();

    private final TimeStep subStep = new TimeStep();

    private final Body[] tempBodies = new Body[2];

    private final Sweep backup1 = new Sweep();

    private final Sweep backup2 = new Sweep();

    private void solveTOI(final TimeStep step)
    {
        final Island island = toiIsland;
        island.init(2 * Settings.maxTOIContacts,
            Settings.maxTOIContacts,
            0,
            contactManager.contactListener);
        if (stepComplete)
        {
            for (Body b = bodyList; b != null; b = b.next)
            {
                b.flags &= ~Body.islandFlag;
                b.sweep.alpha0 = 0.0f;
            }
            for (Contact c = contactManager.contactList; c != null; c = c.next)
            {
                // Invalidate TOI
                c.flags &= ~(Contact.TOI_FLAG | Contact.ISLAND_FLAG);
                c.toiCount = 0;
                c.toi = 1.0f;
            }
        }
        // Find TOI events and solve them.
        for (;;)
        {
            // Find the first TOI.
            Contact minContact = null;
            float minAlpha = 1.0f;
            for (Contact c = contactManager.contactList; c != null; c = c.next)
            {
                // Is this contact disabled?
                if (!c.isEnabled())
                {
                    continue;
                }
                // Prevent excessive sub-stepping.
                if (c.toiCount > Settings.maxSubSteps)
                {
                    continue;
                }
                float alpha;
                if ((c.flags & Contact.TOI_FLAG) != 0)
                {
                    // This contact has a valid cached TOI.
                    alpha = c.toi;
                }
                else
                {
                    Fixture fA = c.getFixtureA();
                    Fixture fB = c.getFixtureB();
                    // Is there a sensor?
                    if (fA.isSensor() || fB.isSensor())
                    {
                        continue;
                    }
                    Body bA = fA.getBody();
                    Body bB = fB.getBody();
                    BodyType typeA = bA.type;
                    BodyType typeB = bB.type;
                    assert (typeA == BodyType.DYNAMIC
                            || typeB == BodyType.DYNAMIC);
                    boolean activeA = bA.isAwake() && typeA != BodyType.STATIC;
                    boolean activeB = bB.isAwake() && typeB != BodyType.STATIC;
                    // Is at least one body active (awake and dynamic or
                    // kinematic)?
                    if (!activeA && !activeB)
                    {
                        continue;
                    }
                    boolean collideA = bA.isBullet()
                            || typeA != BodyType.DYNAMIC;
                    boolean collideB = bB.isBullet()
                            || typeB != BodyType.DYNAMIC;
                    // Are these two non-bullet dynamic bodies?
                    if (!collideA && !collideB)
                    {
                        continue;
                    }
                    // Compute the TOI for this contact.
                    // Put the sweeps onto the same time interval.
                    float alpha0 = bA.sweep.alpha0;
                    if (bA.sweep.alpha0 < bB.sweep.alpha0)
                    {
                        alpha0 = bB.sweep.alpha0;
                        bA.sweep.advance(alpha0);
                    }
                    else if (bB.sweep.alpha0 < bA.sweep.alpha0)
                    {
                        alpha0 = bA.sweep.alpha0;
                        bB.sweep.advance(alpha0);
                    }
                    assert (alpha0 < 1.0f);
                    int indexA = c.getChildIndexA();
                    int indexB = c.getChildIndexB();
                    // Compute the time of impact in interval [0, minTOI]
                    final TOIInput input = toiInput;
                    input.proxyA.set(fA.getShape(), indexA);
                    input.proxyB.set(fB.getShape(), indexB);
                    input.sweepA.set(bA.sweep);
                    input.sweepB.set(bB.sweep);
                    input.tMax = 1.0f;
                    pool.getTimeOfImpact().timeOfImpact(toiOutput, input);
                    // Beta is the fraction of the remaining portion of the .
                    float beta = toiOutput.t;
                    if (toiOutput.state == TOIOutputState.TOUCHING)
                    {
                        alpha = MathUtils.min(alpha0 + (1.0f - alpha0) * beta,
                            1.0f);
                    }
                    else
                    {
                        alpha = 1.0f;
                    }
                    c.toi = alpha;
                    c.flags |= Contact.TOI_FLAG;
                }
                if (alpha < minAlpha)
                {
                    // This is the minimum TOI found so far.
                    minContact = c;
                    minAlpha = alpha;
                }
            }
            if (minContact == null
                    || 1.0f - 10.0f * Settings.EPSILON < minAlpha)
            {
                // No more TOI events. Done!
                stepComplete = true;
                break;
            }
            // Advance the bodies to the TOI.
            Fixture fA = minContact.getFixtureA();
            Fixture fB = minContact.getFixtureB();
            Body bA = fA.getBody();
            Body bB = fB.getBody();
            backup1.set(bA.sweep);
            backup2.set(bB.sweep);
            bA.advance(minAlpha);
            bB.advance(minAlpha);
            // The TOI contact likely has some new contact points.
            minContact.update(contactManager.contactListener);
            minContact.flags &= ~Contact.TOI_FLAG;
            ++minContact.toiCount;
            // Is the contact solid?
            if (!minContact.isEnabled() || !minContact.isTouching())
            {
                // Restore the sweeps.
                minContact.setEnabled(false);
                bA.sweep.set(backup1);
                bB.sweep.set(backup2);
                bA.synchronizeTransform();
                bB.synchronizeTransform();
                continue;
            }
            bA.setAwake(true);
            bB.setAwake(true);
            // Build the island
            island.clear();
            island.add(bA);
            island.add(bB);
            island.add(minContact);
            bA.flags |= Body.islandFlag;
            bB.flags |= Body.islandFlag;
            minContact.flags |= Contact.ISLAND_FLAG;
            // Get contacts on bodyA and bodyB.
            tempBodies[0] = bA;
            tempBodies[1] = bB;
            for (int i = 0; i < 2; ++i)
            {
                Body body = tempBodies[i];
                if (body.type == BodyType.DYNAMIC)
                {
                    for (ContactEdge ce = body.contactList; ce != null; ce = ce.next)
                    {
                        if (island.bodyCount == island.bodyCapacity)
                        {
                            break;
                        }
                        if (island.contactCount == island.contactCapacity)
                        {
                            break;
                        }
                        Contact contact = ce.contact;
                        // Has this contact already been added to the island?
                        if ((contact.flags & Contact.ISLAND_FLAG) != 0)
                        {
                            continue;
                        }
                        // Only add static, kinematic, or bullet bodies.
                        Body other = ce.other;
                        if (other.type == BodyType.DYNAMIC && !body.isBullet()
                                && !other.isBullet())
                        {
                            continue;
                        }
                        // Skip sensors.
                        boolean sensorA = contact.fixtureA.isSensor;
                        boolean sensorB = contact.fixtureB.isSensor;
                        if (sensorA || sensorB)
                        {
                            continue;
                        }
                        // Tentatively advance the body to the TOI.
                        backup1.set(other.sweep);
                        if ((other.flags & Body.islandFlag) == 0)
                        {
                            other.advance(minAlpha);
                        }
                        // Update the contact points
                        contact.update(contactManager.contactListener);
                        // Was the contact disabled by the user?
                        if (!contact.isEnabled())
                        {
                            other.sweep.set(backup1);
                            other.synchronizeTransform();
                            continue;
                        }
                        // Are there contact points?
                        if (!contact.isTouching())
                        {
                            other.sweep.set(backup1);
                            other.synchronizeTransform();
                            continue;
                        }
                        // Add the contact to the island
                        contact.flags |= Contact.ISLAND_FLAG;
                        island.add(contact);
                        // Has the other body already been added to the island?
                        if ((other.flags & Body.islandFlag) != 0)
                        {
                            continue;
                        }
                        // Add the other body to the island.
                        other.flags |= Body.islandFlag;
                        if (other.type != BodyType.STATIC)
                        {
                            other.setAwake(true);
                        }
                        island.add(other);
                    }
                }
            }
            subStep.dt = (1.0f - minAlpha) * step.dt;
            subStep.inverseDt = 1.0f / subStep.dt;
            subStep.dtRatio = 1.0f;
            subStep.positionIterations = 20;
            subStep.velocityIterations = step.velocityIterations;
            subStep.warmStarting = false;
            island.solveTOI(subStep, bA.islandIndex, bB.islandIndex);
            // Reset island flags and synchronize broad-phase proxies.
            for (int i = 0; i < island.bodyCount; ++i)
            {
                Body body = island.bodies[i];
                body.flags &= ~Body.islandFlag;
                if (body.type != BodyType.DYNAMIC)
                {
                    continue;
                }
                body.synchronizeFixtures();
                // Invalidate all contact TOIs on this displaced body.
                for (ContactEdge ce = body.contactList; ce != null; ce = ce.next)
                {
                    ce.contact.flags &= ~(Contact.TOI_FLAG
                            | Contact.ISLAND_FLAG);
                }
            }
            // Commit fixture proxy movements to the broad-phase so that new
            // contacts are created.
            // Also, some contacts can be destroyed.
            contactManager.findNewContacts();
            if (subStepping)
            {
                stepComplete = false;
                break;
            }
        }
    }

    private void drawJoint(Joint joint)
    {
        Body bodyA = joint.getBodyA();
        Body bodyB = joint.getBodyB();
        Transform xf1 = bodyA.getTransform();
        Transform xf2 = bodyB.getTransform();
        Vec2 x1 = xf1.p;
        Vec2 x2 = xf2.p;
        Vec2 p1 = pool.popVec2();
        Vec2 p2 = pool.popVec2();
        joint.getAnchorA(p1);
        joint.getAnchorB(p2);
        color.set(0.5f, 0.8f, 0.8f);
        switch (joint.getType())
        {
        // TODO djm write after writing joints
        case DISTANCE:
            debugDraw.drawSegment(p1, p2, color);
            break;

        case PULLEY:
        {
            PulleyJoint pulley = (PulleyJoint) joint;
            Vec2 s1 = pulley.getGroundAnchorA();
            Vec2 s2 = pulley.getGroundAnchorB();
            debugDraw.drawSegment(s1, p1, color);
            debugDraw.drawSegment(s2, p2, color);
            debugDraw.drawSegment(s1, s2, color);
        }
            break;

        case CONSTANT_VOLUME:
        case MOUSE:
            // don't draw this
            break;

        default:
            debugDraw.drawSegment(x1, p1, color);
            debugDraw.drawSegment(p1, p2, color);
            debugDraw.drawSegment(x2, p2, color);
        }
        pool.pushVec2(2);
    }

    // NOTE this corresponds to the liquid test, so the debugdraw can draw
    // the liquid particles correctly. They should be the same.
    private static final Integer LIQUID_INT = 1234598372;

    private float averageLinearVel = -1;

    private final Vec2 liquidOffset = new Vec2();

    private final Vec2 circCenterMoved = new Vec2();

    private final Color3f liquidColor = new Color3f(.4f, .4f, 1f);

    private final Vec2 center = new Vec2();

    private final Vec2 axis = new Vec2();

    private final Vec2 v1 = new Vec2();

    private final Vec2 v2 = new Vec2();

    private final Vec2Array tlvertices = new Vec2Array();

    private void drawShape(Fixture fixture, Transform xf, Color3f color,
            boolean wireframe)
    {
        switch (fixture.getType())
        {
        case CIRCLE:
        {
            CircleShape circle = (CircleShape) fixture.getShape();
            // Vec2 center = Mul(xf, circle.p);
            Transform.mulToOutUnsafe(xf, circle.p, center);
            float radius = circle.radius;
            xf.q.getXAxis(axis);
            if (fixture.getUserData() != null
                    && fixture.getUserData().equals(LIQUID_INT))
            {
                Body b = fixture.getBody();
                liquidOffset.set(b.linearVelocity);
                float linVelLength = b.linearVelocity.length();
                if (averageLinearVel == -1)
                {
                    averageLinearVel = linVelLength;
                }
                else
                {
                    averageLinearVel = .98f * averageLinearVel
                            + .02f * linVelLength;
                }
                float liquidLength = .12f;
                liquidOffset.mulLocal(liquidLength / averageLinearVel / 2);
                circCenterMoved.set(center).addLocal(liquidOffset);
                center.subLocal(liquidOffset);
                debugDraw.drawSegment(center, circCenterMoved, liquidColor);
                return;
            }
            if (wireframe)
            {
                debugDraw.drawCircle(center, radius, axis, color);
            }
            else
            {
                debugDraw.drawSolidCircle(center, radius, axis, color);
            }
        }
            break;

        case POLYGON:
        {
            PolygonShape poly = (PolygonShape) fixture.getShape();
            int vertexCount = poly.count;
            assert (vertexCount <= Settings.maxPolygonVertices);
            Vec2[] vertices = tlvertices.get(Settings.maxPolygonVertices);
            for (int i = 0; i < vertexCount; ++i)
            {
                // vertices[i] = Mul(xf, poly.vertices[i]);
                Transform.mulToOutUnsafe(xf, poly.vertices[i], vertices[i]);
            }
            if (wireframe)
            {
                debugDraw.drawPolygon(vertices, vertexCount, color);
            }
            else
            {
                debugDraw.drawSolidPolygon(vertices, vertexCount, color);
            }
        }
            break;

        case EDGE:
        {
            EdgeShape edge = (EdgeShape) fixture.getShape();
            Transform.mulToOutUnsafe(xf, edge.vertex1, v1);
            Transform.mulToOutUnsafe(xf, edge.vertex2, v2);
            debugDraw.drawSegment(v1, v2, color);
        }
            break;

        case CHAIN:
        {
            ChainShape chain = (ChainShape) fixture.getShape();
            int count = chain.count;
            Vec2[] vertices = chain.vertices;
            Transform.mulToOutUnsafe(xf, vertices[0], v1);
            for (int i = 1; i < count; ++i)
            {
                Transform.mulToOutUnsafe(xf, vertices[i], v2);
                debugDraw.drawSegment(v1, v2, color);
                debugDraw.drawCircle(v1, 0.05f, color);
                v1.set(v2);
            }
        }
            break;

        default:
            break;
        }
    }

    private void drawParticleSystem(ParticleSystem system)
    {
        boolean wireframe = (debugDraw.getFlags()
                & DebugDraw.wireframeDrawingBit) != 0;
        int particleCount = system.getParticleCount();
        if (particleCount != 0)
        {
            float particleRadius = system.getParticleRadius();
            Vec2[] positionBuffer = system.getParticlePositionBuffer();
            ParticleColor[] colorBuffer = null;
            if (system.colorBuffer.data != null)
            {
                colorBuffer = system.getParticleColorBuffer();
            }
            if (wireframe)
            {
                debugDraw.drawParticlesWireframe(positionBuffer,
                    particleRadius,
                    colorBuffer,
                    particleCount);
            }
            else
            {
                debugDraw.drawParticles(positionBuffer,
                    particleRadius,
                    colorBuffer,
                    particleCount);
            }
        }
    }

    /**
     * Create a particle whose properties have been defined. No reference to the
     * definition is retained. A simulation step must occur before it's possible
     * to interact with a newly created particle. For example,
     * DestroyParticleInShape() will not destroy a particle until Step() has
     * been called.
     *
     * @warning This function is locked during callbacks.
     *
     * @return The index of the particle.
     */
    public int createParticle(ParticleDef def)
    {
        assert (!isLocked());
        if (isLocked())
        {
            return 0;
        }
        return particleSystem.createParticle(def);
    }

    /**
     * Destroy a particle. The particle is removed after the next step.
     */
    public void destroyParticle(int index)
    {
        destroyParticle(index, false);
    }

    /**
     * Destroy a particle. The particle is removed after the next step.
     *
     * @param index Index of the particle to destroy.
     * @param callDestructionListener Whether to call the destruction listener
     *     just before the particle is destroyed.
     */
    public void destroyParticle(int index, boolean callDestructionListener)
    {
        particleSystem.destroyParticle(index, callDestructionListener);
    }

    /**
     * Destroy particles inside a shape without enabling the destruction
     * callback for destroyed particles. This function is locked during
     * callbacks. For more information see
     * {@code DestroyParticleInShape(Shape&, Transform&,bool)}.
     *
     * @param shape Shape which encloses particles that should be destroyed.
     * @param xf Transform applied to the shape.
     *
     * @warning This function is locked during callbacks.
     *
     * @return Number of particles destroyed.
     */
    public int destroyParticlesInShape(Shape shape, Transform xf)
    {
        return destroyParticlesInShape(shape, xf, false);
    }

    /**
     * Destroy particles inside a shape. This function is locked during
     * callbacks. In addition, this function immediately destroys particles in
     * the shape in contrast to DestroyParticle() which defers the destruction
     * until the next simulation step.
     *
     * @param shape Shape which encloses particles that should be destroyed.
     * @param xf Transform applied to the shape.
     * @param callDestructionListener Whether to call the world
     *     b2DestructionListener for each particle destroyed.
     *
     * @warning This function is locked during callbacks.
     *
     * @return Number of particles destroyed.
     */
    public int destroyParticlesInShape(Shape shape, Transform xf,
            boolean callDestructionListener)
    {
        assert (!isLocked());
        if (isLocked())
        {
            return 0;
        }
        return particleSystem
            .destroyParticlesInShape(shape, xf, callDestructionListener);
    }

    /**
     * Create a particle group whose properties have been defined. No reference
     * to the definition is retained.
     *
     * @warning This function is locked during callbacks.
     */
    public ParticleGroup createParticleGroup(ParticleGroupDef def)
    {
        assert (!isLocked());
        if (isLocked())
        {
            return null;
        }
        return particleSystem.createParticleGroup(def);
    }

    /**
     * Join two particle groups.
     *
     * @param groupA The first group. Expands to encompass the second group.
     * @param groupB The second group. It is destroyed.
     *
     * @warning This function is locked during callbacks.
     */
    public void joinParticleGroups(ParticleGroup groupA, ParticleGroup groupB)
    {
        assert (!isLocked());
        if (isLocked())
        {
            return;
        }
        particleSystem.joinParticleGroups(groupA, groupB);
    }

    /**
     * Destroy particles in a group. This function is locked during callbacks.
     *
     * @param group The particle group to destroy.
     * @param callDestructionListener Whether to call the world
     *     b2DestructionListener for each particle is destroyed.
     *
     * @warning This function is locked during callbacks.
     */
    public void destroyParticlesInGroup(ParticleGroup group,
            boolean callDestructionListener)
    {
        assert (!isLocked());
        if (isLocked())
        {
            return;
        }
        particleSystem.destroyParticlesInGroup(group, callDestructionListener);
    }

    /**
     * Destroy particles in a group without enabling the destruction callback
     * for destroyed particles. This function is locked during callbacks.
     *
     * @param group The particle group to destroy.
     *
     * @warning This function is locked during callbacks.
     */
    public void destroyParticlesInGroup(ParticleGroup group)
    {
        destroyParticlesInGroup(group, false);
    }

    /**
     * Get the world particle group list. With the returned group, use
     * ParticleGroup::GetNext to get the next group in the world list. A NULL
     * group indicates the end of the list.
     *
     * @return The head of the world particle group list.
     */
    public ParticleGroup[] getParticleGroupList()
    {
        return particleSystem.getParticleGroupList();
    }

    /**
     * Get the number of particle groups.
     */
    public int getParticleGroupCount()
    {
        return particleSystem.getParticleGroupCount();
    }

    /**
     * Get the number of particles.
     */
    public int getParticleCount()
    {
        return particleSystem.getParticleCount();
    }

    /**
     * Get the maximum number of particles.
     */
    public int getParticleMaxCount()
    {
        return particleSystem.getParticleMaxCount();
    }

    /**
     * Set the maximum number of particles.
     */
    public void setParticleMaxCount(int count)
    {
        particleSystem.setParticleMaxCount(count);
    }

    /**
     * Change the particle density.
     */
    public void setParticleDensity(float density)
    {
        particleSystem.setParticleDensity(density);
    }

    /**
     * Get the particle density.
     */
    public float getParticleDensity()
    {
        return particleSystem.getParticleDensity();
    }

    /**
     * Change the particle gravity scale. Adjusts the effect of the global
     * gravity vector on particles. Default value is 1.0f.
     */
    public void setParticleGravityScale(float gravityScale)
    {
        particleSystem.setParticleGravityScale(gravityScale);
    }

    /**
     * Get the particle gravity scale.
     */
    public float getParticleGravityScale()
    {
        return particleSystem.getParticleGravityScale();
    }

    /**
     * Damping is used to reduce the velocity of particles. The damping
     * parameter can be larger than 1.0f but the damping effect becomes
     * sensitive to the time step when the damping parameter is large.
     */
    public void setParticleDamping(float damping)
    {
        particleSystem.setParticleDamping(damping);
    }

    /**
     * Get damping for particles
     */
    public float getParticleDamping()
    {
        return particleSystem.getParticleDamping();
    }

    /**
     * Change the particle radius. You should set this only once, on world
     * start. If you change the radius during execution, existing particles may
     * explode, shrink, or behave unexpectedly.
     */
    public void setParticleRadius(float radius)
    {
        particleSystem.setParticleRadius(radius);
    }

    /**
     * Get the particle radius.
     */
    public float getParticleRadius()
    {
        return particleSystem.getParticleRadius();
    }

    /**
     * Get the particle data.
     *
     * @return The pointer to the head of the particle data.
     */
    public int[] getParticleFlagsBuffer()
    {
        return particleSystem.getParticleFlagsBuffer();
    }

    public Vec2[] getParticlePositionBuffer()
    {
        return particleSystem.getParticlePositionBuffer();
    }

    public Vec2[] getParticleVelocityBuffer()
    {
        return particleSystem.getParticleVelocityBuffer();
    }

    public ParticleColor[] getParticleColorBuffer()
    {
        return particleSystem.getParticleColorBuffer();
    }

    public ParticleGroup[] getParticleGroupBuffer()
    {
        return particleSystem.getParticleGroupBuffer();
    }

    public Object[] getParticleUserDataBuffer()
    {
        return particleSystem.getParticleUserDataBuffer();
    }

    /**
     * Set a buffer for particle data.
     *
     * @param buffer Is a pointer to a block of memory.
     * @param capacity Is the number of values in the block.
     */
    public void setParticleFlagsBuffer(int[] buffer, int capacity)
    {
        particleSystem.setParticleFlagsBuffer(buffer, capacity);
    }

    public void setParticlePositionBuffer(Vec2[] buffer, int capacity)
    {
        particleSystem.setParticlePositionBuffer(buffer, capacity);
    }

    public void setParticleVelocityBuffer(Vec2[] buffer, int capacity)
    {
        particleSystem.setParticleVelocityBuffer(buffer, capacity);
    }

    public void setParticleColorBuffer(ParticleColor[] buffer, int capacity)
    {
        particleSystem.setParticleColorBuffer(buffer, capacity);
    }

    public void setParticleUserDataBuffer(Object[] buffer, int capacity)
    {
        particleSystem.setParticleUserDataBuffer(buffer, capacity);
    }

    /**
     * Get contacts between particles
     */
    public ParticleContact[] getParticleContacts()
    {
        return particleSystem.contactBuffer;
    }

    public int getParticleContactCount()
    {
        return particleSystem.contactCount;
    }

    /**
     * Get contacts between particles and bodies
     */
    public ParticleBodyContact[] getParticleBodyContacts()
    {
        return particleSystem.bodyContactBuffer;
    }

    public int getParticleBodyContactCount()
    {
        return particleSystem.bodyContactCount;
    }

    /**
     * Compute the kinetic energy that can be lost by damping force
     */
    public float computeParticleCollisionEnergy()
    {
        return particleSystem.computeParticleCollisionEnergy();
    }
}

class WorldQueryWrapper implements TreeCallback
{
    public boolean treeCallback(int nodeId)
    {
        FixtureProxy proxy = (FixtureProxy) broadPhase.getUserData(nodeId);
        return callback.reportFixture(proxy.fixture);
    }

    BroadPhase broadPhase;

    QueryCallback callback;
}

class WorldRayCastWrapper implements TreeRayCastCallback
{
    // djm pooling
    private final RayCastOutput output = new RayCastOutput();

    private final Vec2 temp = new Vec2();

    private final Vec2 point = new Vec2();

    public float raycastCallback(RayCastInput input, int nodeId)
    {
        Object userData = broadPhase.getUserData(nodeId);
        FixtureProxy proxy = (FixtureProxy) userData;
        Fixture fixture = proxy.fixture;
        int index = proxy.childIndex;
        boolean hit = fixture.raycast(output, input, index);
        if (hit)
        {
            float fraction = output.fraction;
            // Vec2 point = (1.0f - fraction) * input.p1 + fraction * input.p2;
            temp.set(input.p2).mulLocal(fraction);
            point.set(input.p1).mulLocal(1 - fraction).addLocal(temp);
            return callback
                .reportFixture(fixture, point, output.normal, fraction);
        }
        return input.maxFraction;
    }

    BroadPhase broadPhase;

    RayCastCallback callback;
}
