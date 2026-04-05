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
package org.jbox2d.dynamics.joints;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.SolverData;
import org.jbox2d.dynamics.World;
import org.jbox2d.pooling.WorldPool;

/**
 * The base joint class. Joints are used to constrain two bodies together in
 * various fashions. Some joints also feature limits and motors.
 *
 * <p>
 * Joints are used to constrain bodies to the world or to each other. Typical
 * examples in games include ragdolls, teeters, and pulleys. Joints can be
 * combined in many different ways to create interesting motions.
 * </p>
 *
 * <p>
 * Some joints provide limits so you can control the range of motion. Some joint
 * provide motors which can be used to drive the joint at a prescribed speed
 * until a prescribed force/torque is exceeded.
 * </p>
 *
 * <p>
 * Joint motors can be used in many ways. You can use motors to control position
 * by specifying a joint velocity that is proportional to the difference between
 * the actual and desired position. You can also use motors to simulate joint
 * friction: set the joint velocity to zero and provide a small, but significant
 * maximum motor force/torque. Then the motor will attempt to keep the joint
 * from moving until the load becomes too strong.
 * </p>
 *
 * https://box2d.org/documentation/md__d_1__git_hub_box2d_docs_dynamics.html#autotoc_md81
 *
 * @author Daniel Murphy
 */
public abstract class Joint
{
    public static Joint create(World world, JointDef def)
    {
        // Joint joint = null;
        return switch (def.type)
        {
        case MOUSE -> new MouseJoint(world.getPool(), (MouseJointDef) def);
        case DISTANCE ->
            new DistanceJoint(world.getPool(), (DistanceJointDef) def);
        case PRISMATIC ->
            new PrismaticJoint(world.getPool(), (PrismaticJointDef) def);
        case REVOLUTE ->
            new RevoluteJoint(world.getPool(), (RevoluteJointDef) def);
        case WELD -> new WeldJoint(world.getPool(), (WeldJointDef) def);
        case FRICTION ->
            new FrictionJoint(world.getPool(), (FrictionJointDef) def);
        case WHEEL -> new WheelJoint(world.getPool(), (WheelJointDef) def);
        case GEAR -> new GearJoint(world.getPool(), (GearJointDef) def);
        case PULLEY -> new PulleyJoint(world.getPool(), (PulleyJointDef) def);
        case CONSTANT_VOLUME ->
            new ConstantVolumeJoint(world, (ConstantVolumeJointDef) def);
        case ROPE -> new RopeJoint(world.getPool(), (RopeJointDef) def);
        case MOTOR -> new MotorJoint(world.getPool(), (MotorJointDef) def);
        default -> null;
        };
    }

    public static void destroy(Joint joint)
    {
        joint.destructor();
    }

    private final JointType type;

    public Joint prev;

    public Joint next;

    public JointEdge edgeA;

    public JointEdge edgeB;

    protected Body bodyA;

    protected Body bodyB;

    public boolean islandFlag;

    private final boolean collideConnected;

    public Object userData;

    protected WorldPool pool;
    // Cache here per time step to reduce cache misses.
    // final Vec2 localCenterA, localCenterB;
    // float invMassA, invIA;
    // float invMassB, invIB;

    protected Joint(WorldPool worldPool, JointDef def)
    {
        assert (def.bodyA != def.bodyB);
        pool = worldPool;
        type = def.type;
        prev = null;
        next = null;
        bodyA = def.bodyA;
        bodyB = def.bodyB;
        collideConnected = def.collideConnected;
        islandFlag = false;
        userData = def.userData;
        edgeA = new JointEdge();
        edgeA.joint = null;
        edgeA.other = null;
        edgeA.prev = null;
        edgeA.next = null;
        edgeB = new JointEdge();
        edgeB.joint = null;
        edgeB.other = null;
        edgeB.prev = null;
        edgeB.next = null;
        // localCenterA = new Vec2();
        // localCenterB = new Vec2();
    }

    /**
     * Get the type of the concrete joint.
     */
    public JointType getType()
    {
        return type;
    }

    /**
     * Get the first body attached to this joint.
     */
    public final Body getBodyA()
    {
        return bodyA;
    }

    /**
     * Get the second body attached to this joint.
     */
    public final Body getBodyB()
    {
        return bodyB;
    }

    /**
     * Get the anchor point on bodyA in world coordinates.
     */
    public abstract void getAnchorA(Vec2 out);

    /**
     * Get the anchor point on bodyB in world coordinates.
     */
    public abstract void getAnchorB(Vec2 out);

    /**
     * Get the reaction force on body2 at the joint anchor in Newtons.
     */
    public abstract void getReactionForce(float invDt, Vec2 out);

    /**
     * Get the reaction torque on body2 in N*m.
     */
    public abstract float getReactionTorque(float invDt);

    /**
     * Get the next joint the world joint list.
     */
    public Joint getNext()
    {
        return next;
    }

    /**
     * Get the user data pointer.
     */
    public Object getUserData()
    {
        return userData;
    }

    /**
     * Set the user data pointer.
     */
    public void setUserData(Object data)
    {
        userData = data;
    }

    /**
     * Get collide connected. Note: modifying the collide connect flag won't
     * work correctly because the flag is only checked when fixture AABBs begin
     * to overlap.
     */
    public final boolean getCollideConnected()
    {
        return collideConnected;
    }

    /**
     * Short-cut function to determine if either body is inactive.
     *
     */
    public boolean isActive()
    {
        return bodyA.isActive() && bodyB.isActive();
    }

    /**
     * Internal
     */
    public abstract void initVelocityConstraints(SolverData data);

    /**
     * Internal
     */
    public abstract void solveVelocityConstraints(SolverData data);

    /**
     * This returns true if the position errors are within tolerance. Internal.
     */
    public abstract boolean solvePositionConstraints(SolverData data);

    /**
     * Override to handle destruction of joint
     */
    public void destructor()
    {
    }
}
