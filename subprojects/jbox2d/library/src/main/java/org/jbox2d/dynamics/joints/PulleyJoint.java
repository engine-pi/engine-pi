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

import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Rot;
import org.jbox2d.common.Settings;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.SolverData;
import org.jbox2d.pooling.WorldPool;

/**
 * The pulley joint is connected to two bodies and two fixed ground points. The
 * pulley supports a ratio such that:
 * {@code length1 + ratio * length2 <= constant} Yes, the force transmitted is
 * scaled by the ratio. Warning: the pulley joint can get a bit squirrelly by
 * itself. They often work better when combined with prismatic joints. You
 * should also cover the anchor points with static shapes to prevent one side
 * from going to zero length.
 *
 * <p>
 * <img src=
 * "https://github.com/engine-pi/jbox2d/blob/main/misc/images/joints/pulley_joint.gif"
 * alt="pulley joint">
 * </p>
 *
 * @repolink https://github.com/erincatto/box2d/blob/main/src/dynamics/b2_pulley_joint.cpp
 *
 * @author Daniel Murphy
 */
public class PulleyJoint extends Joint
{
    public static final float MIN_PULLEY_LENGTH = 2.0f;

    /**
     * The first ground anchor in the world coordinates. This point never moves.
     */
    private final Vec2 groundAnchorA = new Vec2();

    /**
     * The second ground anchor in the world coordinates. This point never
     * moves.
     */
    private final Vec2 groundAnchorB = new Vec2();

    /**
     * The local anchor point relative to bodyA's origin.
     */
    private final Vec2 localAnchorA = new Vec2();

    /**
     * The local anchor point relative to bodyB's origin.
     */
    private final Vec2 localAnchorB = new Vec2();

    /**
     * The reference length for the segment attached to bodyA.
     */
    private final float lengthA;

    /**
     * The reference length for the segment attached to bodyB.
     */
    private final float lengthB;

    /**
     * The pulley ratio, used to simulate a block-and-tackle.
     */
    private final float ratio;

    private final float constant;

    private float impulse;

    // Solver temp
    private int indexA;

    private int indexB;

    private final Vec2 uA = new Vec2();

    private final Vec2 uB = new Vec2();

    private final Vec2 rA = new Vec2();

    private final Vec2 rB = new Vec2();

    private final Vec2 localCenterA = new Vec2();

    private final Vec2 localCenterB = new Vec2();

    private float invMassA;

    private float invMassB;

    private float invIA;

    private float invIB;

    private float mass;

    /**
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_pulley_joint.cpp#L39-L56
     */
    protected PulleyJoint(WorldPool argWorldPool, PulleyJointDef def)
    {
        super(argWorldPool, def);
        groundAnchorA.set(def.groundAnchorA);
        groundAnchorB.set(def.groundAnchorB);
        localAnchorA.set(def.localAnchorA);
        localAnchorB.set(def.localAnchorB);
        assert (def.ratio != 0.0f);
        ratio = def.ratio;
        lengthA = def.lengthA;
        lengthB = def.lengthB;
        constant = def.lengthA + ratio * def.lengthB;
        impulse = 0.0f;
    }

    public float getLengthA()
    {
        return lengthA;
    }

    public float getLengthB()
    {
        return lengthB;
    }

    public float getCurrentLengthA()
    {
        final Vec2 p = pool.popVec2();
        bodyA.getWorldPointToOut(localAnchorA, p);
        p.subLocal(groundAnchorA);
        float length = p.length();
        pool.pushVec2(1);
        return length;
    }

    public float getCurrentLengthB()
    {
        final Vec2 p = pool.popVec2();
        bodyB.getWorldPointToOut(localAnchorB, p);
        p.subLocal(groundAnchorB);
        float length = p.length();
        pool.pushVec2(1);
        return length;
    }

    public Vec2 getLocalAnchorA()
    {
        return localAnchorA;
    }

    public Vec2 getLocalAnchorB()
    {
        return localAnchorB;
    }

    @Override
    public void getAnchorA(Vec2 argOut)
    {
        bodyA.getWorldPointToOut(localAnchorA, argOut);
    }

    @Override
    public void getAnchorB(Vec2 argOut)
    {
        bodyB.getWorldPointToOut(localAnchorB, argOut);
    }

    @Override
    public void getReactionForce(float invDt, Vec2 argOut)
    {
        argOut.set(uB).mulLocal(impulse).mulLocal(invDt);
    }

    @Override
    public float getReactionTorque(float invDt)
    {
        return 0f;
    }

    public Vec2 getGroundAnchorA()
    {
        return groundAnchorA;
    }

    public Vec2 getGroundAnchorB()
    {
        return groundAnchorB;
    }

    public float getLength1()
    {
        final Vec2 p = pool.popVec2();
        bodyA.getWorldPointToOut(localAnchorA, p);
        p.subLocal(groundAnchorA);
        float len = p.length();
        pool.pushVec2(1);
        return len;
    }

    public float getLength2()
    {
        final Vec2 p = pool.popVec2();
        bodyB.getWorldPointToOut(localAnchorB, p);
        p.subLocal(groundAnchorB);
        float len = p.length();
        pool.pushVec2(1);
        return len;
    }

    public float getRatio()
    {
        return ratio;
    }

    /**
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_pulley_joint.cpp#L77-L165
     */
    @Override
    public void initVelocityConstraints(final SolverData data)
    {
        indexA = bodyA.islandIndex;
        indexB = bodyB.islandIndex;
        localCenterA.set(bodyA.sweep.localCenter);
        localCenterB.set(bodyB.sweep.localCenter);
        invMassA = bodyA.invMass;
        invMassB = bodyB.invMass;
        invIA = bodyA.invI;
        invIB = bodyB.invI;
        Vec2 cA = data.positions[indexA].c;
        float aA = data.positions[indexA].a;
        Vec2 vA = data.velocities[indexA].v;
        float wA = data.velocities[indexA].w;
        Vec2 cB = data.positions[indexB].c;
        float aB = data.positions[indexB].a;
        Vec2 vB = data.velocities[indexB].v;
        float wB = data.velocities[indexB].w;
        final Rot qA = pool.popRot();
        final Rot qB = pool.popRot();
        final Vec2 temp = pool.popVec2();
        qA.set(aA);
        qB.set(aB);
        // Compute the effective masses.
        Rot.mulToOutUnsafe(qA,
            temp.set(localAnchorA).subLocal(localCenterA),
            rA);
        Rot.mulToOutUnsafe(qB,
            temp.set(localAnchorB).subLocal(localCenterB),
            rB);
        uA.set(cA).addLocal(rA).subLocal(groundAnchorA);
        uB.set(cB).addLocal(rB).subLocal(groundAnchorB);
        float lengthA = uA.length();
        float lengthB = uB.length();
        if (lengthA > 10f * Settings.linearSlop)
        {
            uA.mulLocal(1.0f / lengthA);
        }
        else
        {
            uA.setZero();
        }
        if (lengthB > 10f * Settings.linearSlop)
        {
            uB.mulLocal(1.0f / lengthB);
        }
        else
        {
            uB.setZero();
        }
        // Compute effective mass.
        float ruA = Vec2.cross(rA, uA);
        float ruB = Vec2.cross(rB, uB);
        float mA = invMassA + invIA * ruA * ruA;
        float mB = invMassB + invIB * ruB * ruB;
        mass = mA + ratio * ratio * mB;
        if (mass > 0.0f)
        {
            mass = 1.0f / mass;
        }
        if (data.step.warmStarting)
        {
            // Scale impulses to support variable time steps.
            impulse *= data.step.dtRatio;
            // Warm starting.
            final Vec2 PA = pool.popVec2();
            final Vec2 PB = pool.popVec2();
            PA.set(uA).mulLocal(-impulse);
            PB.set(uB).mulLocal(-ratio * impulse);
            vA.x += invMassA * PA.x;
            vA.y += invMassA * PA.y;
            wA += invIA * Vec2.cross(rA, PA);
            vB.x += invMassB * PB.x;
            vB.y += invMassB * PB.y;
            wB += invIB * Vec2.cross(rB, PB);
            pool.pushVec2(2);
        }
        else
        {
            impulse = 0.0f;
        }
        // data.velocities[indexA].v.set(vA);
        data.velocities[indexA].w = wA;
        // data.velocities[indexB].v.set(vB);
        data.velocities[indexB].w = wB;
        pool.pushVec2(1);
        pool.pushRot(2);
    }

    /**
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_pulley_joint.cpp#L167-L192
     */
    @Override
    public void solveVelocityConstraints(final SolverData data)
    {
        Vec2 vA = data.velocities[indexA].v;
        float wA = data.velocities[indexA].w;
        Vec2 vB = data.velocities[indexB].v;
        float wB = data.velocities[indexB].w;
        final Vec2 vpA = pool.popVec2();
        final Vec2 vpB = pool.popVec2();
        final Vec2 PA = pool.popVec2();
        final Vec2 PB = pool.popVec2();
        Vec2.crossToOutUnsafe(wA, rA, vpA);
        vpA.addLocal(vA);
        Vec2.crossToOutUnsafe(wB, rB, vpB);
        vpB.addLocal(vB);
        float Cdot = -Vec2.dot(uA, vpA) - ratio * Vec2.dot(uB, vpB);
        float impulse = -mass * Cdot;
        this.impulse += impulse;
        PA.set(uA).mulLocal(-impulse);
        PB.set(uB).mulLocal(-ratio * impulse);
        vA.x += invMassA * PA.x;
        vA.y += invMassA * PA.y;
        wA += invIA * Vec2.cross(rA, PA);
        vB.x += invMassB * PB.x;
        vB.y += invMassB * PB.y;
        wB += invIB * Vec2.cross(rB, PB);
        // data.velocities[indexA].v.set(vA);
        data.velocities[indexA].w = wA;
        // data.velocities[indexB].v.set(vB);
        data.velocities[indexB].w = wB;
        pool.pushVec2(4);
    }

    /**
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_pulley_joint.cpp#L194-L264
     */
    @Override
    public boolean solvePositionConstraints(final SolverData data)
    {
        final Rot qA = pool.popRot();
        final Rot qB = pool.popRot();
        final Vec2 rA = pool.popVec2();
        final Vec2 rB = pool.popVec2();
        final Vec2 uA = pool.popVec2();
        final Vec2 uB = pool.popVec2();
        final Vec2 temp = pool.popVec2();
        final Vec2 PA = pool.popVec2();
        final Vec2 PB = pool.popVec2();
        Vec2 cA = data.positions[indexA].c;
        float aA = data.positions[indexA].a;
        Vec2 cB = data.positions[indexB].c;
        float aB = data.positions[indexB].a;
        qA.set(aA);
        qB.set(aB);
        Rot.mulToOutUnsafe(qA,
            temp.set(localAnchorA).subLocal(localCenterA),
            rA);
        Rot.mulToOutUnsafe(qB,
            temp.set(localAnchorB).subLocal(localCenterB),
            rB);
        uA.set(cA).addLocal(rA).subLocal(groundAnchorA);
        uB.set(cB).addLocal(rB).subLocal(groundAnchorB);
        float lengthA = uA.length();
        float lengthB = uB.length();
        if (lengthA > 10.0f * Settings.linearSlop)
        {
            uA.mulLocal(1.0f / lengthA);
        }
        else
        {
            uA.setZero();
        }
        if (lengthB > 10.0f * Settings.linearSlop)
        {
            uB.mulLocal(1.0f / lengthB);
        }
        else
        {
            uB.setZero();
        }
        // Compute effective mass.
        float ruA = Vec2.cross(rA, uA);
        float ruB = Vec2.cross(rB, uB);
        float mA = invMassA + invIA * ruA * ruA;
        float mB = invMassB + invIB * ruB * ruB;
        float mass = mA + ratio * ratio * mB;
        if (mass > 0.0f)
        {
            mass = 1.0f / mass;
        }
        float C = constant - lengthA - ratio * lengthB;
        float linearError = MathUtils.abs(C);
        float impulse = -mass * C;
        PA.set(uA).mulLocal(-impulse);
        PB.set(uB).mulLocal(-ratio * impulse);
        cA.x += invMassA * PA.x;
        cA.y += invMassA * PA.y;
        aA += invIA * Vec2.cross(rA, PA);
        cB.x += invMassB * PB.x;
        cB.y += invMassB * PB.y;
        aB += invIB * Vec2.cross(rB, PB);
        // data.positions[indexA].c.set(cA);
        data.positions[indexA].a = aA;
        // data.positions[indexB].c.set(cB);
        data.positions[indexB].a = aB;
        pool.pushRot(2);
        pool.pushVec2(7);
        return linearError < Settings.linearSlop;
    }
}
