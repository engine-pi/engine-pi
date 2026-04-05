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

import org.jbox2d.common.Mat33;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Rot;
import org.jbox2d.common.Settings;
import org.jbox2d.common.Vec2;
import org.jbox2d.common.Vec3;
import org.jbox2d.dynamics.SolverData;
import org.jbox2d.pooling.WorldPool;

/**
 * A weld joint essentially glues two bodies together. A weld joint may distort
 * somewhat because the island constraint solver is approximate.
 *
 * @repolink https://github.com/erincatto/box2d/blob/main/src/dynamics/b2_weld_joint.cpp
 *
 * @author Daniel Murphy
 */
public class WeldJoint extends Joint
{
    /**
     * The local anchor point relative to body1's origin.
     */
    private final Vec2 localCenterA = new Vec2();

    /**
     * The local anchor point relative to body2's origin.
     */
    private final Vec2 localCenterB = new Vec2();

    /**
     * The body2 angle minus body1 angle in the reference state (radians).
     */
    private final float referenceAngle;

    /**
     * The mass-spring-damper frequency in Hertz. Rotation only. Disable
     * softness with a value of 0.
     */
    private float frequencyHz;

    /**
     * The damping ratio. 0 = no damping, 1 = critical damping.
     */
    private float dampingRatio;

    private float bias;

    // Solver shared
    private final Vec2 localAnchorA;

    private final Vec2 localAnchorB;

    private float gamma;

    private final Vec3 impulse;

    // Solver temp
    private int indexA;

    private int indexB;

    private final Vec2 rA = new Vec2();

    private final Vec2 rB = new Vec2();

    private float invMassA;

    private float invMassB;

    private float invIA;

    private float invIB;

    private final Mat33 mass = new Mat33();

    /**
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_weld_joint.cpp#L41-L60
     */
    protected WeldJoint(WorldPool argWorld, WeldJointDef def)
    {
        super(argWorld, def);
        localAnchorA = new Vec2(def.localAnchorA);
        localAnchorB = new Vec2(def.localAnchorB);
        referenceAngle = def.referenceAngle;
        frequencyHz = def.frequencyHz;
        dampingRatio = def.dampingRatio;
        impulse = new Vec3();
        impulse.setZero();
    }

    public float getReferenceAngle()
    {
        return referenceAngle;
    }

    public Vec2 getLocalAnchorA()
    {
        return localAnchorA;
    }

    public Vec2 getLocalAnchorB()
    {
        return localAnchorB;
    }

    public float getFrequency()
    {
        return frequencyHz;
    }

    public void setFrequency(float frequencyHz)
    {
        this.frequencyHz = frequencyHz;
    }

    public float getDampingRatio()
    {
        return dampingRatio;
    }

    public void setDampingRatio(float dampingRatio)
    {
        this.dampingRatio = dampingRatio;
    }

    /**
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_weld_joint.cpp#L308-L311
     */
    @Override
    public void getAnchorA(Vec2 argOut)
    {
        bodyA.getWorldPointToOut(localAnchorA, argOut);
    }

    /**
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_weld_joint.cpp#L313-L316
     */
    @Override
    public void getAnchorB(Vec2 argOut)
    {
        bodyB.getWorldPointToOut(localAnchorB, argOut);
    }

    /**
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_weld_joint.cpp#L318-L322
     */
    @Override
    public void getReactionForce(float invDt, Vec2 argOut)
    {
        argOut.set(impulse.x, impulse.y);
        argOut.mulLocal(invDt);
    }

    /**
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_weld_joint.cpp#L324-L327
     */
    @Override
    public float getReactionTorque(float invDt)
    {
        return invDt * impulse.z;
    }

    /**
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_weld_joint.cpp#L62-L167
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
        // Vec2 cA = data.positions[indexA].c;
        float aA = data.positions[indexA].a;
        Vec2 vA = data.velocities[indexA].v;
        float wA = data.velocities[indexA].w;
        // Vec2 cB = data.positions[indexB].c;
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
        final Mat33 K = pool.popMat33();
        K.ex.x = mA + mB + rA.y * rA.y * iA + rB.y * rB.y * iB;
        K.ey.x = -rA.y * rA.x * iA - rB.y * rB.x * iB;
        K.ez.x = -rA.y * iA - rB.y * iB;
        K.ex.y = K.ey.x;
        K.ey.y = mA + mB + rA.x * rA.x * iA + rB.x * rB.x * iB;
        K.ez.y = rA.x * iA + rB.x * iB;
        K.ex.z = K.ez.x;
        K.ey.z = K.ez.y;
        K.ez.z = iA + iB;
        if (frequencyHz > 0.0f)
        {
            K.getInverse22(mass);
            float invM = iA + iB;
            float m = invM > 0.0f ? 1.0f / invM : 0.0f;
            float C = aB - aA - referenceAngle;
            // Frequency
            float omega = 2.0f * MathUtils.PI * frequencyHz;
            // Damping coefficient
            float d = 2.0f * m * dampingRatio * omega;
            // Spring stiffness
            float k = m * omega * omega;
            // magic formulas
            float h = data.step.dt;
            gamma = h * (d + h * k);
            gamma = gamma != 0.0f ? 1.0f / gamma : 0.0f;
            bias = C * h * k * gamma;
            invM += gamma;
            mass.ez.z = invM != 0.0f ? 1.0f / invM : 0.0f;
        }
        else
        {
            K.getSymInverse33(mass);
            gamma = 0.0f;
            bias = 0.0f;
        }
        if (data.step.warmStarting)
        {
            final Vec2 P = pool.popVec2();
            // Scale impulses to support a variable time step.
            impulse.mulLocal(data.step.dtRatio);
            P.set(impulse.x, impulse.y);
            vA.x -= mA * P.x;
            vA.y -= mA * P.y;
            wA -= iA * (Vec2.cross(rA, P) + impulse.z);
            vB.x += mB * P.x;
            vB.y += mB * P.y;
            wB += iB * (Vec2.cross(rB, P) + impulse.z);
            pool.pushVec2(1);
        }
        else
        {
            impulse.setZero();
        }
        // data.velocities[indexA].v.set(vA);
        data.velocities[indexA].w = wA;
        // data.velocities[indexB].v.set(vB);
        data.velocities[indexB].w = wB;
        pool.pushVec2(1);
        pool.pushRot(2);
        pool.pushMat33(1);
    }

    /**
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_weld_joint.cpp#L169-L225
     */
    @Override
    public void solveVelocityConstraints(final SolverData data)
    {
        Vec2 vA = data.velocities[indexA].v;
        float wA = data.velocities[indexA].w;
        Vec2 vB = data.velocities[indexB].v;
        float wB = data.velocities[indexB].w;
        float mA = invMassA, mB = invMassB;
        float iA = invIA, iB = invIB;
        final Vec2 Cdot1 = pool.popVec2();
        final Vec2 P = pool.popVec2();
        final Vec2 temp = pool.popVec2();
        if (frequencyHz > 0.0f)
        {
            float Cdot2 = wB - wA;
            float impulse2 = -mass.ez.z * (Cdot2 + bias + gamma * impulse.z);
            impulse.z += impulse2;
            wA -= iA * impulse2;
            wB += iB * impulse2;
            Vec2.crossToOutUnsafe(wB, rB, Cdot1);
            Vec2.crossToOutUnsafe(wA, rA, temp);
            Cdot1.addLocal(vB).subLocal(vA).subLocal(temp);
            Mat33.mul22ToOutUnsafe(mass, Cdot1, P);
            P.negateLocal();
            impulse.x += P.x;
            impulse.y += P.y;
            vA.x -= mA * P.x;
            vA.y -= mA * P.y;
            wA -= iA * Vec2.cross(rA, P);
            vB.x += mB * P.x;
            vB.y += mB * P.y;
            wB += iB * Vec2.cross(rB, P);
        }
        else
        {
            Vec2.crossToOutUnsafe(wA, rA, temp);
            Vec2.crossToOutUnsafe(wB, rB, Cdot1);
            Cdot1.addLocal(vB).subLocal(vA).subLocal(temp);
            float Cdot2 = wB - wA;
            final Vec3 Cdot = pool.popVec3();
            Cdot.set(Cdot1.x, Cdot1.y, Cdot2);
            final Vec3 impulse = pool.popVec3();
            Mat33.mulToOutUnsafe(mass, Cdot, impulse);
            impulse.negateLocal();
            this.impulse.addLocal(impulse);
            P.set(impulse.x, impulse.y);
            vA.x -= mA * P.x;
            vA.y -= mA * P.y;
            wA -= iA * (Vec2.cross(rA, P) + impulse.z);
            vB.x += mB * P.x;
            vB.y += mB * P.y;
            wB += iB * (Vec2.cross(rB, P) + impulse.z);
            pool.pushVec3(2);
        }
        // data.velocities[indexA].v.set(vA);
        data.velocities[indexA].w = wA;
        // data.velocities[indexB].v.set(vB);
        data.velocities[indexB].w = wB;
        pool.pushVec2(3);
    }

    /**
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_weld_joint.cpp#L227-L306
     */
    @Override
    public boolean solvePositionConstraints(final SolverData data)
    {
        Vec2 cA = data.positions[indexA].c;
        float aA = data.positions[indexA].a;
        Vec2 cB = data.positions[indexB].c;
        float aB = data.positions[indexB].a;
        final Rot qA = pool.popRot();
        final Rot qB = pool.popRot();
        final Vec2 temp = pool.popVec2();
        final Vec2 rA = pool.popVec2();
        final Vec2 rB = pool.popVec2();
        qA.set(aA);
        qB.set(aB);
        float mA = invMassA, mB = invMassB;
        float iA = invIA, iB = invIB;
        Rot.mulToOutUnsafe(qA,
            temp.set(localAnchorA).subLocal(localCenterA),
            rA);
        Rot.mulToOutUnsafe(qB,
            temp.set(localAnchorB).subLocal(localCenterB),
            rB);
        float positionError, angularError;
        final Mat33 K = pool.popMat33();
        final Vec2 C1 = pool.popVec2();
        final Vec2 P = pool.popVec2();
        K.ex.x = mA + mB + rA.y * rA.y * iA + rB.y * rB.y * iB;
        K.ey.x = -rA.y * rA.x * iA - rB.y * rB.x * iB;
        K.ez.x = -rA.y * iA - rB.y * iB;
        K.ex.y = K.ey.x;
        K.ey.y = mA + mB + rA.x * rA.x * iA + rB.x * rB.x * iB;
        K.ez.y = rA.x * iA + rB.x * iB;
        K.ex.z = K.ez.x;
        K.ey.z = K.ez.y;
        K.ez.z = iA + iB;
        if (frequencyHz > 0.0f)
        {
            C1.set(cB).addLocal(rB).subLocal(cA).subLocal(rA);
            positionError = C1.length();
            angularError = 0.0f;
            K.solve22ToOut(C1, P);
            P.negateLocal();
            cA.x -= mA * P.x;
            cA.y -= mA * P.y;
            aA -= iA * Vec2.cross(rA, P);
            cB.x += mB * P.x;
            cB.y += mB * P.y;
            aB += iB * Vec2.cross(rB, P);
        }
        else
        {
            C1.set(cB).addLocal(rB).subLocal(cA).subLocal(rA);
            float C2 = aB - aA - referenceAngle;
            positionError = C1.length();
            angularError = MathUtils.abs(C2);
            final Vec3 C = pool.popVec3();
            final Vec3 impulse = pool.popVec3();
            C.set(C1.x, C1.y, C2);
            K.solve33ToOut(C, impulse);
            impulse.negateLocal();
            P.set(impulse.x, impulse.y);
            cA.x -= mA * P.x;
            cA.y -= mA * P.y;
            aA -= iA * (Vec2.cross(rA, P) + impulse.z);
            cB.x += mB * P.x;
            cB.y += mB * P.y;
            aB += iB * (Vec2.cross(rB, P) + impulse.z);
            pool.pushVec3(2);
        }
        // data.positions[indexA].c.set(cA);
        data.positions[indexA].a = aA;
        // data.positions[indexB].c.set(cB);
        data.positions[indexB].a = aB;
        pool.pushVec2(5);
        pool.pushRot(2);
        pool.pushMat33(1);
        return positionError <= Settings.linearSlop
                && angularError <= Settings.angularSlop;
    }
}
