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

import org.jbox2d.common.Mat22;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Rot;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.SolverData;
import org.jbox2d.pooling.WorldPool;

/**
 * @repolink https://github.com/erincatto/box2d/blob/main/include/box2d/b2_friction_joint.h
 * @repolink https://github.com/erincatto/box2d/blob/main/src/dynamics/b2_friction_joint.cpp
 *
 * @author Daniel Murphy
 */
public class FrictionJoint extends Joint
{
    /**
     * The local anchor point relative to bodyA's origin.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_friction_joint.h#L45-L46
     */
    private final Vec2 localAnchorA;

    /**
     * The local anchor point relative to bodyB's origin.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_friction_joint.h#L48-L49
     */
    private final Vec2 localAnchorB;

    // Solver shared
    private final Vec2 linearImpulse;

    /**
     * The maximum friction force in N.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_friction_joint.h#L51-L52
     */
    private float maxForce;

    /**
     * The maximum friction torque in N-m.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_friction_joint.h#L54-L55
     */
    private float maxTorque;

    private float angularImpulse;

    // Solver temp
    private int indexA;

    private int indexB;

    private final Vec2 rA = new Vec2();

    private final Vec2 rB = new Vec2();

    private final Vec2 localCenterA = new Vec2();

    private final Vec2 localCenterB = new Vec2();

    private float invMassA;

    private float invMassB;

    private float invIA;

    private float invIB;

    private final Mat22 linearMass = new Mat22();

    private float angularMass;

    protected FrictionJoint(WorldPool argWorldPool, FrictionJointDef def)
    {
        super(argWorldPool, def);
        localAnchorA = new Vec2(def.localAnchorA);
        localAnchorB = new Vec2(def.localAnchorB);
        linearImpulse = new Vec2();
        angularImpulse = 0.0f;
        maxForce = def.maxForce;
        maxTorque = def.maxTorque;
    }

    /**
     * Get the local anchor point relative to bodyA's origin.
     *
     * @return The local anchor point relative to bodyA's origin.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_friction_joint.h#L69-L70
     */
    public Vec2 getLocalAnchorA()
    {
        return localAnchorA;
    }

    /**
     * Get the local anchor point relative to bodyB's origin.
     *
     * @return The local anchor point relative to bodyB's origin.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_friction_joint.h#L72-L73
     */
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
        argOut.set(linearImpulse).mulLocal(invDt);
    }

    @Override
    public float getReactionTorque(float invDt)
    {
        return invDt * angularImpulse;
    }

    /**
     * Set the maximum friction force in N.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_friction_joint.h#L75-L76
     */
    public void setMaxForce(float force)
    {
        assert (force >= 0.0f);
        maxForce = force;
    }

    /**
     * Get the maximum friction force in N.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_friction_joint.h#L78-L79
     */
    public float getMaxForce()
    {
        return maxForce;
    }

    /**
     * Set the maximum friction torque in N*m.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_friction_joint.h#L81-L82
     */
    public void setMaxTorque(float torque)
    {
        assert (torque >= 0.0f);
        maxTorque = torque;
    }

    /**
     * Get the maximum friction torque in N*m.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_friction_joint.h#L84-L85
     */
    public float getMaxTorque()
    {
        return maxTorque;
    }

    /**
     * @see org.jbox2d.dynamics.joints.Joint#initVelocityConstraints(org.jbox2d.dynamics.SolverData)
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
        float aA = data.positions[indexA].a;
        Vec2 vA = data.velocities[indexA].v;
        float wA = data.velocities[indexA].w;
        float aB = data.positions[indexB].a;
        Vec2 vB = data.velocities[indexB].v;
        float wB = data.velocities[indexB].w;
        final Vec2 temp = pool.popVec2();
        final Rot qA = pool.popRot();
        final Rot qB = pool.popRot();
        qA.set(aA);
        qB.set(aB);
        // Compute the effective mass matrix.
        Rot.mulToOutUnsafe(qA,
            temp.set(localAnchorA).subLocal(localCenterA),
            rA);
        Rot.mulToOutUnsafe(qB,
            temp.set(localAnchorB).subLocal(localCenterB),
            rB);
        // J = [-I -r1_skew I r2_skew]
        // [ 0 -1 0 1]
        // r_skew = [-ry; rx]
        // Matlab
        // K = [ mA+r1y^2*iA+mB+r2y^2*iB, -r1y*iA*r1x-r2y*iB*r2x,
        // -r1y*iA-r2y*iB]
        // [ -r1y*iA*r1x-r2y*iB*r2x, mA+r1x^2*iA+mB+r2x^2*iB, r1x*iA+r2x*iB]
        // [ -r1y*iA-r2y*iB, r1x*iA+r2x*iB, iA+iB]
        float mA = invMassA, mB = invMassB;
        float iA = invIA, iB = invIB;
        final Mat22 K = pool.popMat22();
        K.ex.x = mA + mB + iA * rA.y * rA.y + iB * rB.y * rB.y;
        K.ex.y = -iA * rA.x * rA.y - iB * rB.x * rB.y;
        K.ey.x = K.ex.y;
        K.ey.y = mA + mB + iA * rA.x * rA.x + iB * rB.x * rB.x;
        K.invertToOut(linearMass);
        angularMass = iA + iB;
        if (angularMass > 0.0f)
        {
            angularMass = 1.0f / angularMass;
        }
        if (data.step.warmStarting)
        {
            // Scale impulses to support a variable time step.
            linearImpulse.mulLocal(data.step.dtRatio);
            angularImpulse *= data.step.dtRatio;
            final Vec2 P = pool.popVec2();
            P.set(linearImpulse);
            temp.set(P).mulLocal(mA);
            vA.subLocal(temp);
            wA -= iA * (Vec2.cross(rA, P) + angularImpulse);
            temp.set(P).mulLocal(mB);
            vB.addLocal(temp);
            wB += iB * (Vec2.cross(rB, P) + angularImpulse);
            pool.pushVec2(1);
        }
        else
        {
            linearImpulse.setZero();
            angularImpulse = 0.0f;
        }
        // data.velocities[indexA].v.set(vA);
        assert data.velocities[indexA].w == wA
                || (data.velocities[indexA].w != wA);
        data.velocities[indexA].w = wA;
        // data.velocities[indexB].v.set(vB);
        data.velocities[indexB].w = wB;
        pool.pushRot(2);
        pool.pushVec2(1);
        pool.pushMat22(1);
    }

    @Override
    public void solveVelocityConstraints(final SolverData data)
    {
        Vec2 vA = data.velocities[indexA].v;
        float wA = data.velocities[indexA].w;
        Vec2 vB = data.velocities[indexB].v;
        float wB = data.velocities[indexB].w;
        float mA = invMassA, mB = invMassB;
        float iA = invIA, iB = invIB;
        float h = data.step.dt;
        // Solve angular friction
        {
            float Cdot = wB - wA;
            float impulse = -angularMass * Cdot;
            float oldImpulse = angularImpulse;
            float maxImpulse = h * maxTorque;
            angularImpulse = MathUtils
                .clamp(angularImpulse + impulse, -maxImpulse, maxImpulse);
            impulse = angularImpulse - oldImpulse;
            wA -= iA * impulse;
            wB += iB * impulse;
        }
        // Solve linear friction
        {
            final Vec2 Cdot = pool.popVec2();
            final Vec2 temp = pool.popVec2();
            Vec2.crossToOutUnsafe(wA, rA, temp);
            Vec2.crossToOutUnsafe(wB, rB, Cdot);
            Cdot.addLocal(vB).subLocal(vA).subLocal(temp);
            final Vec2 impulse = pool.popVec2();
            Mat22.mulToOutUnsafe(linearMass, Cdot, impulse);
            impulse.negateLocal();
            final Vec2 oldImpulse = pool.popVec2();
            oldImpulse.set(linearImpulse);
            linearImpulse.addLocal(impulse);
            float maxImpulse = h * maxForce;
            if (linearImpulse.lengthSquared() > maxImpulse * maxImpulse)
            {
                linearImpulse.normalize();
                linearImpulse.mulLocal(maxImpulse);
            }
            impulse.set(linearImpulse).subLocal(oldImpulse);
            temp.set(impulse).mulLocal(mA);
            vA.subLocal(temp);
            wA -= iA * Vec2.cross(rA, impulse);
            temp.set(impulse).mulLocal(mB);
            vB.addLocal(temp);
            wB += iB * Vec2.cross(rB, impulse);
        }
        // data.velocities[indexA].v.set(vA);
        assert data.velocities[indexA].w == wA
                || (data.velocities[indexA].w != wA);
        data.velocities[indexA].w = wA;
        // data.velocities[indexB].v.set(vB);
        data.velocities[indexB].w = wB;
        pool.pushVec2(4);
    }

    @Override
    public boolean solvePositionConstraints(final SolverData data)
    {
        return true;
    }
}
