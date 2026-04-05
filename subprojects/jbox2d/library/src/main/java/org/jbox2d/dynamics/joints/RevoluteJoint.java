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
import org.jbox2d.common.Mat33;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Rot;
import org.jbox2d.common.Settings;
import org.jbox2d.common.Vec2;
import org.jbox2d.common.Vec3;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.SolverData;
import org.jbox2d.pooling.WorldPool;

/**
 * A revolute joint constrains two bodies to share a common point while they are
 * free to rotate about the point. The relative rotation about the shared point
 * is the joint angle. You can limit the relative rotation with a joint limit
 * that specifies a lower and upper angle. You can use a motor to drive the
 * relative rotation about the shared point. A maximum motor torque is provided
 * so that infinite forces are not generated.
 *
 * <p>
 * <img src=
 * "https://github.com/engine-pi/jbox2d/blob/main/misc/images/joints/revolute_joint.svg"
 * alt="revolute joint">
 * </p>
 *
 * @repolink https://github.com/erincatto/box2d/blob/main/src/dynamics/b2_revolute_joint.cpp
 *
 * @author Daniel Murphy
 */
public class RevoluteJoint extends Joint
{

    /**
     * The local anchor point relative to body1's origin.
     */
    protected final Vec2 localAnchorA = new Vec2();

    /**
     * The local anchor point relative to body2's origin.
     */
    protected final Vec2 localAnchorB = new Vec2();

    /**
     * The body2 angle minus body1 angle in the reference state (radians).
     */
    protected float referenceAngle;

    /**
     * A flag to enable joint limits.
     */
    private boolean enableLimit;

    /**
     * The lower angle for the joint limit (radians).
     */
    private float lowerAngle;

    /**
     * The upper angle for the joint limit (radians).
     */
    private float upperAngle;

    /**
     * A flag to enable the joint motor.
     */
    private boolean enableMotor;

    /**
     * The desired motor speed. Usually in radians per second.
     */
    private float motorSpeed;

    /**
     * The maximum motor torque used to achieve the desired motor speed. Usually
     * in N-m.
     */
    private float maxMotorTorque;

    private float motorImpulse;

    private final Vec3 impulse = new Vec3();

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

    /**
     * effective mass for point-to-point constraint.
     */
    private final Mat33 mass = new Mat33();

    /**
     * effective mass for motor/limit angular constraint.
     */
    private float motorMass;

    private LimitState limitState;

    /**
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_revolute_joint.cpp#L41-L71
     */
    protected RevoluteJoint(WorldPool argWorld, RevoluteJointDef def)
    {
        super(argWorld, def);
        localAnchorA.set(def.localAnchorA);
        localAnchorB.set(def.localAnchorB);
        referenceAngle = def.referenceAngle;
        motorImpulse = 0;
        lowerAngle = def.lowerAngle;
        upperAngle = def.upperAngle;
        maxMotorTorque = def.maxMotorTorque;
        motorSpeed = def.motorSpeed;
        enableLimit = def.enableLimit;
        enableMotor = def.enableMotor;
        limitState = LimitState.INACTIVE;
    }

    /**
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_revolute_joint.cpp#L73-L165
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
        boolean fixedRotation = (iA + iB == 0.0f);
        mass.ex.x = mA + mB + rA.y * rA.y * iA + rB.y * rB.y * iB;
        mass.ey.x = -rA.y * rA.x * iA - rB.y * rB.x * iB;
        mass.ez.x = -rA.y * iA - rB.y * iB;
        mass.ex.y = mass.ey.x;
        mass.ey.y = mA + mB + rA.x * rA.x * iA + rB.x * rB.x * iB;
        mass.ez.y = rA.x * iA + rB.x * iB;
        mass.ex.z = mass.ez.x;
        mass.ey.z = mass.ez.y;
        mass.ez.z = iA + iB;
        motorMass = iA + iB;
        if (motorMass > 0.0f)
        {
            motorMass = 1.0f / motorMass;
        }
        if (!enableMotor || fixedRotation)
        {
            motorImpulse = 0.0f;
        }
        if (enableLimit && !fixedRotation)
        {
            float jointAngle = aB - aA - referenceAngle;
            if (MathUtils.abs(upperAngle - lowerAngle) < 2.0f
                    * Settings.angularSlop)
            {
                limitState = LimitState.EQUAL;
            }
            else if (jointAngle <= lowerAngle)
            {
                if (limitState != LimitState.AT_LOWER)
                {
                    impulse.z = 0.0f;
                }
                limitState = LimitState.AT_LOWER;
            }
            else if (jointAngle >= upperAngle)
            {
                if (limitState != LimitState.AT_UPPER)
                {
                    impulse.z = 0.0f;
                }
                limitState = LimitState.AT_UPPER;
            }
            else
            {
                limitState = LimitState.INACTIVE;
                impulse.z = 0.0f;
            }
        }
        else
        {
            limitState = LimitState.INACTIVE;
        }
        if (data.step.warmStarting)
        {
            final Vec2 P = pool.popVec2();
            // Scale impulses to support a variable time step.
            impulse.x *= data.step.dtRatio;
            impulse.y *= data.step.dtRatio;
            motorImpulse *= data.step.dtRatio;
            P.x = impulse.x;
            P.y = impulse.y;
            vA.x -= mA * P.x;
            vA.y -= mA * P.y;
            wA -= iA * (Vec2.cross(rA, P) + motorImpulse + impulse.z);
            vB.x += mB * P.x;
            vB.y += mB * P.y;
            wB += iB * (Vec2.cross(rB, P) + motorImpulse + impulse.z);
            pool.pushVec2(1);
        }
        else
        {
            impulse.setZero();
            motorImpulse = 0.0f;
        }
        // data.velocities[indexA].v.set(vA);
        data.velocities[indexA].w = wA;
        // data.velocities[indexB].v.set(vB);
        data.velocities[indexB].w = wB;
        pool.pushVec2(1);
        pool.pushRot(2);
    }

    /**
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_revolute_joint.cpp#L167-L243
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
        boolean fixedRotation = (iA + iB == 0.0f);
        // Solve motor constraint.
        if (enableMotor && limitState != LimitState.EQUAL && !fixedRotation)
        {
            float Cdot = wB - wA - motorSpeed;
            float impulse = -motorMass * Cdot;
            float oldImpulse = motorImpulse;
            float maxImpulse = data.step.dt * maxMotorTorque;
            motorImpulse = MathUtils
                .clamp(motorImpulse + impulse, -maxImpulse, maxImpulse);
            impulse = motorImpulse - oldImpulse;
            wA -= iA * impulse;
            wB += iB * impulse;
        }
        final Vec2 temp = pool.popVec2();
        // Solve limit constraint.
        if (enableLimit && limitState != LimitState.INACTIVE && !fixedRotation)
        {
            final Vec2 Cdot1 = pool.popVec2();
            final Vec3 Cdot = pool.popVec3();
            // Solve point-to-point constraint
            Vec2.crossToOutUnsafe(wA, rA, temp);
            Vec2.crossToOutUnsafe(wB, rB, Cdot1);
            Cdot1.addLocal(vB).subLocal(vA).subLocal(temp);
            float Cdot2 = wB - wA;
            Cdot.set(Cdot1.x, Cdot1.y, Cdot2);
            Vec3 impulse = pool.popVec3();
            mass.solve33ToOut(Cdot, impulse);
            impulse.negateLocal();
            if (limitState == LimitState.EQUAL)
            {
                this.impulse.addLocal(impulse);
            }
            else if (limitState == LimitState.AT_LOWER)
            {
                float newImpulse = this.impulse.z + impulse.z;
                if (newImpulse < 0.0f)
                {
                    final Vec2 rhs = pool.popVec2();
                    rhs.set(mass.ez.x, mass.ez.y)
                        .mulLocal(this.impulse.z)
                        .subLocal(Cdot1);
                    mass.solve22ToOut(rhs, temp);
                    impulse.x = temp.x;
                    impulse.y = temp.y;
                    impulse.z = -this.impulse.z;
                    this.impulse.x += temp.x;
                    this.impulse.y += temp.y;
                    this.impulse.z = 0.0f;
                    pool.pushVec2(1);
                }
                else
                {
                    this.impulse.addLocal(impulse);
                }
            }
            else if (limitState == LimitState.AT_UPPER)
            {
                float newImpulse = this.impulse.z + impulse.z;
                if (newImpulse > 0.0f)
                {
                    final Vec2 rhs = pool.popVec2();
                    rhs.set(mass.ez.x, mass.ez.y)
                        .mulLocal(this.impulse.z)
                        .subLocal(Cdot1);
                    mass.solve22ToOut(rhs, temp);
                    impulse.x = temp.x;
                    impulse.y = temp.y;
                    impulse.z = -this.impulse.z;
                    this.impulse.x += temp.x;
                    this.impulse.y += temp.y;
                    this.impulse.z = 0.0f;
                    pool.pushVec2(1);
                }
                else
                {
                    this.impulse.addLocal(impulse);
                }
            }
            final Vec2 P = pool.popVec2();
            P.set(impulse.x, impulse.y);
            vA.x -= mA * P.x;
            vA.y -= mA * P.y;
            wA -= iA * (Vec2.cross(rA, P) + impulse.z);
            vB.x += mB * P.x;
            vB.y += mB * P.y;
            wB += iB * (Vec2.cross(rB, P) + impulse.z);
            pool.pushVec2(2);
            pool.pushVec3(2);
        }
        else
        {
            // Solve point-to-point constraint
            Vec2 Cdot = pool.popVec2();
            Vec2 impulse = pool.popVec2();
            Vec2.crossToOutUnsafe(wA, rA, temp);
            Vec2.crossToOutUnsafe(wB, rB, Cdot);
            Cdot.addLocal(vB).subLocal(vA).subLocal(temp);
            mass.solve22ToOut(Cdot.negateLocal(), impulse); // just leave
                                                            // negated
            this.impulse.x += impulse.x;
            this.impulse.y += impulse.y;
            vA.x -= mA * impulse.x;
            vA.y -= mA * impulse.y;
            wA -= iA * Vec2.cross(rA, impulse);
            vB.x += mB * impulse.x;
            vB.y += mB * impulse.y;
            wB += iB * Vec2.cross(rB, impulse);
            pool.pushVec2(2);
        }
        // data.velocities[indexA].v.set(vA);
        data.velocities[indexA].w = wA;
        // data.velocities[indexB].v.set(vB);
        data.velocities[indexB].w = wB;
        pool.pushVec2(1);
    }

    /**
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_revolute_joint.cpp#L245-L321
     */
    @Override
    public boolean solvePositionConstraints(final SolverData data)
    {
        final Rot qA = pool.popRot();
        final Rot qB = pool.popRot();
        Vec2 cA = data.positions[indexA].c;
        float aA = data.positions[indexA].a;
        Vec2 cB = data.positions[indexB].c;
        float aB = data.positions[indexB].a;
        qA.set(aA);
        qB.set(aB);
        float angularError = 0.0f;
        float positionError;
        boolean fixedRotation = (invIA + invIB == 0.0f);
        // Solve angular limit constraint.
        if (enableLimit && limitState != LimitState.INACTIVE && !fixedRotation)
        {
            float angle = aB - aA - referenceAngle;
            float limitImpulse = 0.0f;
            if (limitState == LimitState.EQUAL)
            {
                // Prevent large angular corrections
                float C = MathUtils.clamp(angle - lowerAngle,
                    -Settings.maxAngularCorrection,
                    Settings.maxAngularCorrection);
                limitImpulse = -motorMass * C;
                angularError = MathUtils.abs(C);
            }
            else if (limitState == LimitState.AT_LOWER)
            {
                float C = angle - lowerAngle;
                angularError = -C;
                // Prevent large angular corrections and allow some slop.
                C = MathUtils.clamp(C + Settings.angularSlop,
                    -Settings.maxAngularCorrection,
                    0.0f);
                limitImpulse = -motorMass * C;
            }
            else if (limitState == LimitState.AT_UPPER)
            {
                float C = angle - upperAngle;
                angularError = C;
                // Prevent large angular corrections and allow some slop.
                C = MathUtils.clamp(C - Settings.angularSlop,
                    0.0f,
                    Settings.maxAngularCorrection);
                limitImpulse = -motorMass * C;
            }
            aA -= invIA * limitImpulse;
            aB += invIB * limitImpulse;
        }
        // Solve point-to-point constraint.
        {
            qA.set(aA);
            qB.set(aB);
            final Vec2 rA = pool.popVec2();
            final Vec2 rB = pool.popVec2();
            final Vec2 C = pool.popVec2();
            final Vec2 impulse = pool.popVec2();
            Rot.mulToOutUnsafe(qA,
                C.set(localAnchorA).subLocal(localCenterA),
                rA);
            Rot.mulToOutUnsafe(qB,
                C.set(localAnchorB).subLocal(localCenterB),
                rB);
            C.set(cB).addLocal(rB).subLocal(cA).subLocal(rA);
            positionError = C.length();
            float mA = invMassA, mB = invMassB;
            float iA = invIA, iB = invIB;
            final Mat22 K = pool.popMat22();
            K.ex.x = mA + mB + iA * rA.y * rA.y + iB * rB.y * rB.y;
            K.ex.y = -iA * rA.x * rA.y - iB * rB.x * rB.y;
            K.ey.x = K.ex.y;
            K.ey.y = mA + mB + iA * rA.x * rA.x + iB * rB.x * rB.x;
            K.solveToOut(C, impulse);
            impulse.negateLocal();
            cA.x -= mA * impulse.x;
            cA.y -= mA * impulse.y;
            aA -= iA * Vec2.cross(rA, impulse);
            cB.x += mB * impulse.x;
            cB.y += mB * impulse.y;
            aB += iB * Vec2.cross(rB, impulse);
            pool.pushVec2(4);
            pool.pushMat22(1);
        }
        // data.positions[indexA].c.set(cA);
        data.positions[indexA].a = aA;
        // data.positions[indexB].c.set(cB);
        data.positions[indexB].a = aB;
        pool.pushRot(2);
        return positionError <= Settings.linearSlop
                && angularError <= Settings.angularSlop;
    }

    public Vec2 getLocalAnchorA()
    {
        return localAnchorA;
    }

    public Vec2 getLocalAnchorB()
    {
        return localAnchorB;
    }

    public float getReferenceAngle()
    {
        return referenceAngle;
    }

    /**
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_revolute_joint.cpp#L323-L326
     */
    @Override
    public void getAnchorA(Vec2 argOut)
    {
        bodyA.getWorldPointToOut(localAnchorA, argOut);
    }

    /**
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_revolute_joint.cpp#L328-L331
     */
    @Override
    public void getAnchorB(Vec2 argOut)
    {
        bodyB.getWorldPointToOut(localAnchorB, argOut);
    }

    /**
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_revolute_joint.cpp#L333-L337
     */
    @Override
    public void getReactionForce(float invDt, Vec2 argOut)
    {
        argOut.set(impulse.x, impulse.y).mulLocal(invDt);
    }

    /**
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_revolute_joint.cpp#L339-L342
     */
    @Override
    public float getReactionTorque(float invDt)
    {
        return invDt * impulse.z;
    }

    /**
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_revolute_joint.cpp#L344-L349
     */
    public float getJointAngle()
    {
        final Body b1 = bodyA;
        final Body b2 = bodyB;
        return b2.sweep.a - b1.sweep.a - referenceAngle;
    }

    /**
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_revolute_joint.cpp#L351-L356
     */
    public float getJointSpeed()
    {
        final Body b1 = bodyA;
        final Body b2 = bodyB;
        return b2.angularVelocity - b1.angularVelocity;
    }

    /**
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_revolute_joint.cpp#L358-L361
     */
    public boolean isMotorEnabled()
    {
        return enableMotor;
    }

    /**
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_revolute_joint.cpp#L363-L371
     */
    public void enableMotor(boolean flag)
    {
        bodyA.setAwake(true);
        bodyB.setAwake(true);
        enableMotor = flag;
    }

    /**
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_revolute_joint.cpp#L373-L376
     */
    public float getMotorTorque(float inv_dt)
    {
        return motorImpulse * inv_dt;
    }

    /**
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_revolute_joint.cpp#L378-L386
     */
    public void setMotorSpeed(final float speed)
    {
        bodyA.setAwake(true);
        bodyB.setAwake(true);
        motorSpeed = speed;
    }

    /**
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_revolute_joint.cpp#L388-L396
     */
    public void setMaxMotorTorque(final float torque)
    {
        bodyA.setAwake(true);
        bodyB.setAwake(true);
        maxMotorTorque = torque;
    }

    public float getMotorSpeed()
    {
        return motorSpeed;
    }

    public float getMaxMotorTorque()
    {
        return maxMotorTorque;
    }

    /**
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_revolute_joint.cpp#L398-L401
     */
    public boolean isLimitEnabled()
    {
        return enableLimit;
    }

    /**
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_revolute_joint.cpp#L403-L413
     */
    public void enableLimit(final boolean flag)
    {
        if (flag != enableLimit)
        {
            bodyA.setAwake(true);
            bodyB.setAwake(true);
            enableLimit = flag;
            impulse.z = 0.0f;
        }
    }

    /**
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_revolute_joint.cpp#L415-L418
     */
    public float getLowerLimit()
    {
        return lowerAngle;
    }

    /**
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_revolute_joint.cpp#L420-L423
     */
    public float getUpperLimit()
    {
        return upperAngle;
    }

    /**
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_revolute_joint.cpp#L425-L438
     */
    public void setLimits(final float lower, final float upper)
    {
        assert (lower <= upper);
        if (lower != lowerAngle || upper != upperAngle)
        {
            bodyA.setAwake(true);
            bodyB.setAwake(true);
            impulse.z = 0.0f;
            lowerAngle = lower;
            upperAngle = upper;
        }
    }
}
