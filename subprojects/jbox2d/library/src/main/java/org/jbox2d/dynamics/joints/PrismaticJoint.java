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
 * A prismatic joint. This joint provides one degree of freedom: translation
 * along an axis fixed in bodyA. Relative rotation is prevented. You can use a
 * joint limit to restrict the range of motion and a joint motor to drive the
 * motion or to model joint friction.
 *
 * <p>
 * <img src=
 * "https://github.com/engine-pi/jbox2d/blob/main/misc/images/joints/prismatic_joint.svg"
 * alt="prismatic joint">
 * </p>
 *
 * @repolink https://github.com/erincatto/box2d/blob/main/src/dynamics/b2_prismatic_joint.cpp
 *
 * @author Daniel Murphy
 */
public class PrismaticJoint extends Joint
{
    /**
     * The local anchor point relative to body1's origin.
     */
    protected final Vec2 localAnchorA;

    /**
     * The local anchor point relative to body2's origin.
     */
    protected final Vec2 localAnchorB;

    protected final Vec2 localXAxisA;

    protected final Vec2 localYAxisA;

    /**
     * The constrained angle between the bodies: body2_angle - body1_angle.
     */
    protected float referenceAngle;

    /**
     * Enable/disable the joint limit.
     */
    private boolean enableLimit;

    /**
     * The lower translation limit, usually in meters.
     */
    private float lowerTranslation;

    /**
     * The upper translation limit, usually in meters.
     */
    private float upperTranslation;

    /**
     * Enable/disable the joint motor.
     */
    private boolean enableMotor;

    /**
     * The maximum motor torque, usually in N-m.
     */
    private float maxMotorForce;

    /**
     * The desired motor speed in radians per second.
     */
    private float motorSpeed;

    private final Vec3 impulse;

    private float motorImpulse;

    private LimitState limitState;

    // Solver temp
    private int indexA;

    private int indexB;

    private final Vec2 localCenterA = new Vec2();

    private final Vec2 localCenterB = new Vec2();

    private float invMassA;

    private float invMassB;

    private float invIA;

    private float invIB;

    private final Vec2 axis, perp;

    private float s1, s2;

    private float a1, a2;

    private final Mat33 K;

    /**
     * The effective mass for motor/limit translational constraint.
     */
    private float motorMass;

    protected PrismaticJoint(WorldPool argWorld, PrismaticJointDef def)
    {
        super(argWorld, def);
        localAnchorA = new Vec2(def.localAnchorA);
        localAnchorB = new Vec2(def.localAnchorB);
        localXAxisA = new Vec2(def.localAxisA);
        localXAxisA.normalize();
        localYAxisA = new Vec2();
        Vec2.crossToOutUnsafe(1f, localXAxisA, localYAxisA);
        referenceAngle = def.referenceAngle;
        impulse = new Vec3();
        motorMass = 0.0f;
        motorImpulse = 0.0f;
        lowerTranslation = def.lowerTranslation;
        upperTranslation = def.upperTranslation;
        maxMotorForce = def.maxMotorForce;
        motorSpeed = def.motorSpeed;
        enableLimit = def.enableLimit;
        enableMotor = def.enableMotor;
        limitState = LimitState.INACTIVE;
        K = new Mat33();
        axis = new Vec2();
        perp = new Vec2();
    }

    public Vec2 getLocalAnchorA()
    {
        return localAnchorA;
    }

    public Vec2 getLocalAnchorB()
    {
        return localAnchorB;
    }

    /**
     * Get the anchor point on bodyA in world coordinates.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_joint.h#L122-L123
     */
    @Override
    public void getAnchorA(Vec2 argOut)
    {
        bodyA.getWorldPointToOut(localAnchorA, argOut);
    }

    /**
     * Get the anchor point on bodyB in world coordinates.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_joint.h#L125-L126
     */
    @Override
    public void getAnchorB(Vec2 argOut)
    {
        bodyB.getWorldPointToOut(localAnchorB, argOut);
    }

    /**
     * Get the reaction force on bodyB at the joint anchor in Newtons.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_joint.h#L128-L129
     */
    @Override
    public void getReactionForce(float invDt, Vec2 argOut)
    {
        Vec2 temp = pool.popVec2();
        temp.set(axis).mulLocal(motorImpulse + impulse.z);
        argOut.set(perp).mulLocal(impulse.x).addLocal(temp).mulLocal(invDt);
        pool.pushVec2(1);
    }

    @Override
    public float getReactionTorque(float invDt)
    {
        return invDt * impulse.y;
    }

    /**
     * Get the current joint translation, usually in meters.
     */
    public float getJointSpeed()
    {
        Body bA = bodyA;
        Body bB = bodyB;
        Vec2 temp = pool.popVec2();
        Vec2 rA = pool.popVec2();
        Vec2 rB = pool.popVec2();
        Vec2 p1 = pool.popVec2();
        Vec2 p2 = pool.popVec2();
        Vec2 d = pool.popVec2();
        Vec2 axis = pool.popVec2();
        Vec2 temp2 = pool.popVec2();
        Vec2 temp3 = pool.popVec2();
        temp.set(localAnchorA).subLocal(bA.sweep.localCenter);
        Rot.mulToOutUnsafe(bA.xf.q, temp, rA);
        temp.set(localAnchorB).subLocal(bB.sweep.localCenter);
        Rot.mulToOutUnsafe(bB.xf.q, temp, rB);
        p1.set(bA.sweep.c).addLocal(rA);
        p2.set(bB.sweep.c).addLocal(rB);
        d.set(p2).subLocal(p1);
        Rot.mulToOutUnsafe(bA.xf.q, localXAxisA, axis);
        Vec2 vA = bA.linearVelocity;
        Vec2 vB = bB.linearVelocity;
        float wA = bA.angularVelocity;
        float wB = bB.angularVelocity;
        Vec2.crossToOutUnsafe(wA, axis, temp);
        Vec2.crossToOutUnsafe(wB, rB, temp2);
        Vec2.crossToOutUnsafe(wA, rA, temp3);
        temp2.addLocal(vB).subLocal(vA).subLocal(temp3);
        float speed = Vec2.dot(d, temp) + Vec2.dot(axis, temp2);
        pool.pushVec2(9);
        return speed;
    }

    public float getJointTranslation()
    {
        Vec2 pA = pool.popVec2(), pB = pool.popVec2(), axis = pool.popVec2();
        bodyA.getWorldPointToOut(localAnchorA, pA);
        bodyB.getWorldPointToOut(localAnchorB, pB);
        bodyA.getWorldVectorToOutUnsafe(localXAxisA, axis);
        pB.subLocal(pA);
        float translation = Vec2.dot(pB, axis);
        pool.pushVec2(3);
        return translation;
    }

    /**
     * Is the joint limit enabled?
     */
    public boolean isLimitEnabled()
    {
        return enableLimit;
    }

    /**
     * Enable/disable the joint limit.
     */
    public void enableLimit(boolean flag)
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
     * Get the lower joint limit, usually in meters.
     */
    public float getLowerLimit()
    {
        return lowerTranslation;
    }

    /**
     * Get the upper joint limit, usually in meters.
     */
    public float getUpperLimit()
    {
        return upperTranslation;
    }

    /**
     * Set the joint limits, usually in meters.
     */
    public void setLimits(float lower, float upper)
    {
        assert (lower <= upper);
        if (lower != lowerTranslation || upper != upperTranslation)
        {
            bodyA.setAwake(true);
            bodyB.setAwake(true);
            lowerTranslation = lower;
            upperTranslation = upper;
            impulse.z = 0.0f;
        }
    }

    /**
     * Is the joint motor enabled?
     */
    public boolean isMotorEnabled()
    {
        return enableMotor;
    }

    /**
     * Enable/disable the joint motor.
     */
    public void enableMotor(boolean flag)
    {
        bodyA.setAwake(true);
        bodyB.setAwake(true);
        enableMotor = flag;
    }

    /**
     * Set the motor speed, usually in meters per second.
     */
    public void setMotorSpeed(float speed)
    {
        bodyA.setAwake(true);
        bodyB.setAwake(true);
        motorSpeed = speed;
    }

    /**
     * Get the motor speed, usually in meters per second.
     */
    public float getMotorSpeed()
    {
        return motorSpeed;
    }

    /**
     * Set the maximum motor force, usually in N.
     */
    public void setMaxMotorForce(float force)
    {
        bodyA.setAwake(true);
        bodyB.setAwake(true);
        maxMotorForce = force;
    }

    /**
     * Get the current motor force, usually in N.
     */
    public float getMotorForce(float inv_dt)
    {
        return motorImpulse * inv_dt;
    }

    public float getMaxMotorForce()
    {
        return maxMotorForce;
    }

    public float getReferenceAngle()
    {
        return referenceAngle;
    }

    public Vec2 getLocalAxisA()
    {
        return localXAxisA;
    }

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
        final Vec2 d = pool.popVec2();
        final Vec2 temp = pool.popVec2();
        final Vec2 rA = pool.popVec2();
        final Vec2 rB = pool.popVec2();
        qA.set(aA);
        qB.set(aB);
        // Compute the effective masses.
        Rot.mulToOutUnsafe(qA, d.set(localAnchorA).subLocal(localCenterA), rA);
        Rot.mulToOutUnsafe(qB, d.set(localAnchorB).subLocal(localCenterB), rB);
        d.set(cB).subLocal(cA).addLocal(rB).subLocal(rA);
        float mA = invMassA, mB = invMassB;
        float iA = invIA, iB = invIB;
        // Compute motor Jacobian and effective mass.
        {
            Rot.mulToOutUnsafe(qA, localXAxisA, axis);
            temp.set(d).addLocal(rA);
            a1 = Vec2.cross(temp, axis);
            a2 = Vec2.cross(rB, axis);
            motorMass = mA + mB + iA * a1 * a1 + iB * a2 * a2;
            if (motorMass > 0.0f)
            {
                motorMass = 1.0f / motorMass;
            }
        }
        // Prismatic constraint.
        {
            Rot.mulToOutUnsafe(qA, localYAxisA, perp);
            temp.set(d).addLocal(rA);
            s1 = Vec2.cross(temp, perp);
            s2 = Vec2.cross(rB, perp);
            float k11 = mA + mB + iA * s1 * s1 + iB * s2 * s2;
            float k12 = iA * s1 + iB * s2;
            float k13 = iA * s1 * a1 + iB * s2 * a2;
            float k22 = iA + iB;
            if (k22 == 0.0f)
            {
                // For bodies with fixed rotation.
                k22 = 1.0f;
            }
            float k23 = iA * a1 + iB * a2;
            float k33 = mA + mB + iA * a1 * a1 + iB * a2 * a2;
            K.ex.set(k11, k12, k13);
            K.ey.set(k12, k22, k23);
            K.ez.set(k13, k23, k33);
        }
        // Compute motor and limit terms.
        if (enableLimit)
        {
            float jointTranslation = Vec2.dot(axis, d);
            if (MathUtils.abs(upperTranslation - lowerTranslation) < 2.0f
                    * Settings.linearSlop)
            {
                limitState = LimitState.EQUAL;
            }
            else if (jointTranslation <= lowerTranslation)
            {
                if (limitState != LimitState.AT_LOWER)
                {
                    limitState = LimitState.AT_LOWER;
                    impulse.z = 0.0f;
                }
            }
            else if (jointTranslation >= upperTranslation)
            {
                if (limitState != LimitState.AT_UPPER)
                {
                    limitState = LimitState.AT_UPPER;
                    impulse.z = 0.0f;
                }
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
            impulse.z = 0.0f;
        }
        if (!enableMotor)
        {
            motorImpulse = 0.0f;
        }
        if (data.step.warmStarting)
        {
            // Account for variable time step.
            impulse.mulLocal(data.step.dtRatio);
            motorImpulse *= data.step.dtRatio;
            final Vec2 P = pool.popVec2();
            temp.set(axis).mulLocal(motorImpulse + impulse.z);
            P.set(perp).mulLocal(impulse.x).addLocal(temp);
            float LA = impulse.x * s1 + impulse.y
                    + (motorImpulse + impulse.z) * a1;
            float LB = impulse.x * s2 + impulse.y
                    + (motorImpulse + impulse.z) * a2;
            vA.x -= mA * P.x;
            vA.y -= mA * P.y;
            wA -= iA * LA;
            vB.x += mB * P.x;
            vB.y += mB * P.y;
            wB += iB * LB;
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
        pool.pushRot(2);
        pool.pushVec2(4);
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
        final Vec2 temp = pool.popVec2();
        // Solve linear motor constraint.
        if (enableMotor && limitState != LimitState.EQUAL)
        {
            temp.set(vB).subLocal(vA);
            float Cdot = Vec2.dot(axis, temp) + a2 * wB - a1 * wA;
            float impulse = motorMass * (motorSpeed - Cdot);
            float oldImpulse = motorImpulse;
            float maxImpulse = data.step.dt * maxMotorForce;
            motorImpulse = MathUtils
                .clamp(motorImpulse + impulse, -maxImpulse, maxImpulse);
            impulse = motorImpulse - oldImpulse;
            final Vec2 P = pool.popVec2();
            P.set(axis).mulLocal(impulse);
            float LA = impulse * a1;
            float LB = impulse * a2;
            vA.x -= mA * P.x;
            vA.y -= mA * P.y;
            wA -= iA * LA;
            vB.x += mB * P.x;
            vB.y += mB * P.y;
            wB += iB * LB;
            pool.pushVec2(1);
        }
        final Vec2 Cdot1 = pool.popVec2();
        temp.set(vB).subLocal(vA);
        Cdot1.x = Vec2.dot(perp, temp) + s2 * wB - s1 * wA;
        Cdot1.y = wB - wA;
        // System.out.println(Cdot1);
        if (enableLimit && limitState != LimitState.INACTIVE)
        {
            // Solve prismatic and limit constraint in block form.
            float Cdot2;
            temp.set(vB).subLocal(vA);
            Cdot2 = Vec2.dot(axis, temp) + a2 * wB - a1 * wA;
            final Vec3 Cdot = pool.popVec3();
            Cdot.set(Cdot1.x, Cdot1.y, Cdot2);
            final Vec3 f1 = pool.popVec3();
            final Vec3 df = pool.popVec3();
            f1.set(impulse);
            K.solve33ToOut(Cdot.negateLocal(), df);
            // Cdot.negateLocal(); not used anymore
            impulse.addLocal(df);
            if (limitState == LimitState.AT_LOWER)
            {
                impulse.z = MathUtils.max(impulse.z, 0.0f);
            }
            else if (limitState == LimitState.AT_UPPER)
            {
                impulse.z = MathUtils.min(impulse.z, 0.0f);
            }
            // f2(1:2) = invK(1:2,1:2) * (-Cdot(1:2) - K(1:2,3) * (f2(3) -
            // f1(3))) +
            // f1(1:2)
            final Vec2 b = pool.popVec2();
            final Vec2 f2r = pool.popVec2();
            temp.set(K.ez.x, K.ez.y).mulLocal(impulse.z - f1.z);
            b.set(Cdot1).negateLocal().subLocal(temp);
            K.solve22ToOut(b, f2r);
            f2r.addLocal(f1.x, f1.y);
            impulse.x = f2r.x;
            impulse.y = f2r.y;
            df.set(impulse).subLocal(f1);
            final Vec2 P = pool.popVec2();
            temp.set(axis).mulLocal(df.z);
            P.set(perp).mulLocal(df.x).addLocal(temp);
            float LA = df.x * s1 + df.y + df.z * a1;
            float LB = df.x * s2 + df.y + df.z * a2;
            vA.x -= mA * P.x;
            vA.y -= mA * P.y;
            wA -= iA * LA;
            vB.x += mB * P.x;
            vB.y += mB * P.y;
            wB += iB * LB;
            pool.pushVec2(3);
            pool.pushVec3(3);
        }
        else
        {
            // Limit is inactive, just solve the prismatic constraint in block
            // form.
            final Vec2 df = pool.popVec2();
            K.solve22ToOut(Cdot1.negateLocal(), df);
            Cdot1.negateLocal();
            impulse.x += df.x;
            impulse.y += df.y;
            final Vec2 P = pool.popVec2();
            P.set(perp).mulLocal(df.x);
            float LA = df.x * s1 + df.y;
            float LB = df.x * s2 + df.y;
            vA.x -= mA * P.x;
            vA.y -= mA * P.y;
            wA -= iA * LA;
            vB.x += mB * P.x;
            vB.y += mB * P.y;
            wB += iB * LB;
            pool.pushVec2(2);
        }
        // data.velocities[indexA].v.set(vA);
        data.velocities[indexA].w = wA;
        // data.velocities[indexB].v.set(vB);
        data.velocities[indexB].w = wB;
        pool.pushVec2(2);
    }

    @Override
    public boolean solvePositionConstraints(final SolverData data)
    {
        final Rot qA = pool.popRot();
        final Rot qB = pool.popRot();
        final Vec2 rA = pool.popVec2();
        final Vec2 rB = pool.popVec2();
        final Vec2 d = pool.popVec2();
        final Vec2 axis = pool.popVec2();
        final Vec2 perp = pool.popVec2();
        final Vec2 temp = pool.popVec2();
        final Vec2 C1 = pool.popVec2();
        final Vec3 impulse = pool.popVec3();
        Vec2 cA = data.positions[indexA].c;
        float aA = data.positions[indexA].a;
        Vec2 cB = data.positions[indexB].c;
        float aB = data.positions[indexB].a;
        qA.set(aA);
        qB.set(aB);
        float mA = invMassA, mB = invMassB;
        float iA = invIA, iB = invIB;
        // Compute fresh Jacobians
        Rot.mulToOutUnsafe(qA,
            temp.set(localAnchorA).subLocal(localCenterA),
            rA);
        Rot.mulToOutUnsafe(qB,
            temp.set(localAnchorB).subLocal(localCenterB),
            rB);
        d.set(cB).addLocal(rB).subLocal(cA).subLocal(rA);
        Rot.mulToOutUnsafe(qA, localXAxisA, axis);
        float a1 = Vec2.cross(temp.set(d).addLocal(rA), axis);
        float a2 = Vec2.cross(rB, axis);
        Rot.mulToOutUnsafe(qA, localYAxisA, perp);
        float s1 = Vec2.cross(temp.set(d).addLocal(rA), perp);
        float s2 = Vec2.cross(rB, perp);
        C1.x = Vec2.dot(perp, d);
        C1.y = aB - aA - referenceAngle;
        float linearError = MathUtils.abs(C1.x);
        float angularError = MathUtils.abs(C1.y);
        boolean active = false;
        float C2 = 0.0f;
        if (enableLimit)
        {
            float translation = Vec2.dot(axis, d);
            if (MathUtils.abs(upperTranslation - lowerTranslation) < 2.0f
                    * Settings.linearSlop)
            {
                // Prevent large angular corrections
                C2 = MathUtils.clamp(translation,
                    -Settings.maxLinearCorrection,
                    Settings.maxLinearCorrection);
                linearError = MathUtils.max(linearError,
                    MathUtils.abs(translation));
                active = true;
            }
            else if (translation <= lowerTranslation)
            {
                // Prevent large linear corrections and allow some slop.
                C2 = MathUtils.clamp(
                    translation - lowerTranslation + Settings.linearSlop,
                    -Settings.maxLinearCorrection,
                    0.0f);
                linearError = MathUtils.max(linearError,
                    lowerTranslation - translation);
                active = true;
            }
            else if (translation >= upperTranslation)
            {
                // Prevent large linear corrections and allow some slop.
                C2 = MathUtils.clamp(
                    translation - upperTranslation - Settings.linearSlop,
                    0.0f,
                    Settings.maxLinearCorrection);
                linearError = MathUtils.max(linearError,
                    translation - upperTranslation);
                active = true;
            }
        }
        float v = mA + mB + iA * s1 * s1 + iB * s2 * s2;
        if (active)
        {
            float k12 = iA * s1 + iB * s2;
            float k13 = iA * s1 * a1 + iB * s2 * a2;
            float k22 = iA + iB;
            if (k22 == 0.0f)
            {
                // For fixed rotation
                k22 = 1.0f;
            }
            float k23 = iA * a1 + iB * a2;
            float k33 = mA + mB + iA * a1 * a1 + iB * a2 * a2;
            final Mat33 K = pool.popMat33();
            K.ex.set(v, k12, k13);
            K.ey.set(k12, k22, k23);
            K.ez.set(k13, k23, k33);
            final Vec3 C = pool.popVec3();
            C.x = C1.x;
            C.y = C1.y;
            C.z = C2;
            K.solve33ToOut(C.negateLocal(), impulse);
            pool.pushVec3(1);
            pool.pushMat33(1);
        }
        else
        {
            float k12 = iA * s1 + iB * s2;
            float k22 = iA + iB;
            if (k22 == 0.0f)
            {
                k22 = 1.0f;
            }
            final Mat22 K = pool.popMat22();
            K.ex.set(v, k12);
            K.ey.set(k12, k22);
            // temp is impulse1
            K.solveToOut(C1.negateLocal(), temp);
            C1.negateLocal();
            impulse.x = temp.x;
            impulse.y = temp.y;
            impulse.z = 0.0f;
            pool.pushMat22(1);
        }
        float Px = impulse.x * perp.x + impulse.z * axis.x;
        float Py = impulse.x * perp.y + impulse.z * axis.y;
        float LA = impulse.x * s1 + impulse.y + impulse.z * a1;
        float LB = impulse.x * s2 + impulse.y + impulse.z * a2;
        cA.x -= mA * Px;
        cA.y -= mA * Py;
        aA -= iA * LA;
        cB.x += mB * Px;
        cB.y += mB * Py;
        aB += iB * LB;
        // data.positions[indexA].c.set(cA);
        data.positions[indexA].a = aA;
        // data.positions[indexB].c.set(cB);
        data.positions[indexB].a = aB;
        pool.pushVec2(7);
        pool.pushVec3(1);
        pool.pushRot(2);
        return linearError <= Settings.linearSlop
                && angularError <= Settings.angularSlop;
    }
}
