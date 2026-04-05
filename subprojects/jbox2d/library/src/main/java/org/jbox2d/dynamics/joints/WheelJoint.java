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
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.SolverData;
import org.jbox2d.pooling.WorldPool;

/**
 * A wheel joint. This joint provides two degrees of freedom: translation along
 * an axis fixed in bodyA and rotation in the plane. You can use a joint limit
 * to restrict the range of motion and a joint motor to drive the rotation or to
 * model rotational friction. This joint is designed for vehicle suspensions.
 *
 * <p>
 * <img src=
 * "https://github.com/engine-pi/jbox2d/blob/main/misc/images/joints/wheel_joint.svg"
 * alt="wheel joint">
 * </p>
 *
 * @repolink https://github.com/erincatto/box2d/blob/main/src/dynamics/b2_wheel_joint.cpp
 *
 * @author Daniel Murphy
 */
public class WheelJoint extends Joint
{

    /**
     * The local anchor point relative to body1's origin.
     */
    private final Vec2 localAnchorA = new Vec2();

    /**
     * The local anchor point relative to body2's origin.
     */
    private final Vec2 localAnchorB = new Vec2();

    /**
     * The local translation axis in body1.
     */
    private final Vec2 localXAxisA = new Vec2();

    /**
     * Enable/disable the joint motor.
     */
    private boolean enableMotor;

    /**
     * The maximum motor torque, usually in N-m.
     */
    private float maxMotorTorque;

    /**
     * The desired motor speed in radians per second.
     */
    private float motorSpeed;

    /**
     * Suspension frequency, zero indicates no suspension
     */
    private float frequencyHz;

    /**
     * Suspension damping ratio, one indicates critical damping
     */
    private float dampingRatio;

    private final Vec2 localCenterA = new Vec2();

    private final Vec2 localCenterB = new Vec2();

    private final Vec2 localYAxisA = new Vec2();

    private float impulse;

    private float motorImpulse;

    private float springImpulse;

    // Solver temp
    private int indexA;

    private int indexB;

    private float invMassA;

    private float invMassB;

    private float invIA;

    private float invIB;

    private final Vec2 ax = new Vec2();

    private final Vec2 ay = new Vec2();

    private float sAx, sBx;

    private float sAy, sBy;

    private float mass;

    private float motorMass;

    private float springMass;

    private float bias;

    private float gamma;

    /**
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_wheel_joint.cpp#L44-L87
     */
    protected WheelJoint(WorldPool argPool, WheelJointDef def)
    {
        super(argPool, def);
        localAnchorA.set(def.localAnchorA);
        localAnchorB.set(def.localAnchorB);
        localXAxisA.set(def.localAxisA);
        Vec2.crossToOutUnsafe(1.0f, localXAxisA, localYAxisA);
        motorMass = 0.0f;
        motorImpulse = 0.0f;
        maxMotorTorque = def.maxMotorTorque;
        motorSpeed = def.motorSpeed;
        enableMotor = def.enableMotor;
        frequencyHz = def.frequencyHz;
        dampingRatio = def.dampingRatio;
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
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_wheel_joint.cpp#L446-L449
     */
    @Override
    public void getAnchorA(Vec2 argOut)
    {
        bodyA.getWorldPointToOut(localAnchorA, argOut);
    }

    /**
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_wheel_joint.cpp#L451-L454
     */
    @Override
    public void getAnchorB(Vec2 argOut)
    {
        bodyB.getWorldPointToOut(localAnchorB, argOut);
    }

    /**
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_wheel_joint.cpp#L456-L459
     */
    @Override
    public void getReactionForce(float invDt, Vec2 argOut)
    {
        final Vec2 temp = pool.popVec2();
        temp.set(ay).mulLocal(impulse);
        argOut.set(ax).mulLocal(springImpulse).addLocal(temp).mulLocal(invDt);
        pool.pushVec2(1);
    }

    /**
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_wheel_joint.cpp#L461-L464
     */
    @Override
    public float getReactionTorque(float invDt)
    {
        return invDt * motorImpulse;
    }

    /**
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_wheel_joint.cpp#L466-L478
     */
    public float getJointTranslation()
    {
        Body b1 = bodyA;
        Body b2 = bodyB;
        Vec2 p1 = pool.popVec2();
        Vec2 p2 = pool.popVec2();
        Vec2 axis = pool.popVec2();
        b1.getWorldPointToOut(localAnchorA, p1);
        b2.getWorldPointToOut(localAnchorA, p2);
        p2.subLocal(p1);
        b1.getWorldVectorToOut(localXAxisA, axis);
        float translation = Vec2.dot(p2, axis);
        pool.pushVec2(3);
        return translation;
    }

    /**
     * For serialization
     */
    public Vec2 getLocalAxisA()
    {
        return localXAxisA;
    }

    public float getJointSpeed()
    {
        return bodyA.angularVelocity - bodyB.angularVelocity;
    }

    /**
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_wheel_joint.cpp#L556-L559
     */
    public boolean isMotorEnabled()
    {
        return enableMotor;
    }

    /**
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_wheel_joint.cpp#L561-L569
     */
    public void enableMotor(boolean flag)
    {
        bodyA.setAwake(true);
        bodyB.setAwake(true);
        enableMotor = flag;
    }

    /**
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_wheel_joint.cpp#L571-L579
     */
    public void setMotorSpeed(float speed)
    {
        bodyA.setAwake(true);
        bodyB.setAwake(true);
        motorSpeed = speed;
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
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_wheel_joint.cpp#L581-L589
     */
    public void setMaxMotorTorque(float torque)
    {
        bodyA.setAwake(true);
        bodyB.setAwake(true);
        maxMotorTorque = torque;
    }

    public float getMotorTorque(float inv_dt)
    {
        return motorImpulse * inv_dt;
    }

    public void setSpringFrequencyHz(float hz)
    {
        frequencyHz = hz;
    }

    public float getSpringFrequencyHz()
    {
        return frequencyHz;
    }

    public void setSpringDampingRatio(float ratio)
    {
        dampingRatio = ratio;
    }

    public float getSpringDampingRatio()
    {
        return dampingRatio;
    }

    // pooling
    private final Vec2 rA = new Vec2();

    private final Vec2 rB = new Vec2();

    private final Vec2 d = new Vec2();

    /**
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_wheel_joint.cpp#L89-L235
     */
    @Override
    public void initVelocityConstraints(SolverData data)
    {
        indexA = bodyA.islandIndex;
        indexB = bodyB.islandIndex;
        localCenterA.set(bodyA.sweep.localCenter);
        localCenterB.set(bodyB.sweep.localCenter);
        invMassA = bodyA.invMass;
        invMassB = bodyB.invMass;
        invIA = bodyA.invI;
        invIB = bodyB.invI;
        float mA = invMassA, mB = invMassB;
        float iA = invIA, iB = invIB;
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
        d.set(cB).addLocal(rB).subLocal(cA).subLocal(rA);
        // Point to line constraint
        {
            Rot.mulToOut(qA, localYAxisA, ay);
            sAy = Vec2.cross(temp.set(d).addLocal(rA), ay);
            sBy = Vec2.cross(rB, ay);
            mass = mA + mB + iA * sAy * sAy + iB * sBy * sBy;
            if (mass > 0.0f)
            {
                mass = 1.0f / mass;
            }
        }
        // Spring constraint
        springMass = 0.0f;
        bias = 0.0f;
        gamma = 0.0f;
        if (frequencyHz > 0.0f)
        {
            Rot.mulToOut(qA, localXAxisA, ax);
            sAx = Vec2.cross(temp.set(d).addLocal(rA), ax);
            sBx = Vec2.cross(rB, ax);
            float invMass = mA + mB + iA * sAx * sAx + iB * sBx * sBx;
            if (invMass > 0.0f)
            {
                springMass = 1.0f / invMass;
                float C = Vec2.dot(d, ax);
                // Frequency
                float omega = 2.0f * MathUtils.PI * frequencyHz;
                // Damping coefficient
                float d = 2.0f * springMass * dampingRatio * omega;
                // Spring stiffness
                float k = springMass * omega * omega;
                // magic formulas
                float h = data.step.dt;
                gamma = h * (d + h * k);
                if (gamma > 0.0f)
                {
                    gamma = 1.0f / gamma;
                }
                bias = C * h * k * gamma;
                springMass = invMass + gamma;
                if (springMass > 0.0f)
                {
                    springMass = 1.0f / springMass;
                }
            }
        }
        else
        {
            springImpulse = 0.0f;
        }
        // Rotational motor
        if (enableMotor)
        {
            motorMass = iA + iB;
            if (motorMass > 0.0f)
            {
                motorMass = 1.0f / motorMass;
            }
        }
        else
        {
            motorMass = 0.0f;
            motorImpulse = 0.0f;
        }
        if (data.step.warmStarting)
        {
            final Vec2 P = pool.popVec2();
            // Account for variable time step.
            impulse *= data.step.dtRatio;
            springImpulse *= data.step.dtRatio;
            motorImpulse *= data.step.dtRatio;
            P.x = impulse * ay.x + springImpulse * ax.x;
            P.y = impulse * ay.y + springImpulse * ax.y;
            float LA = impulse * sAy + springImpulse * sAx + motorImpulse;
            float LB = impulse * sBy + springImpulse * sBx + motorImpulse;
            vA.x -= invMassA * P.x;
            vA.y -= invMassA * P.y;
            wA -= invIA * LA;
            vB.x += invMassB * P.x;
            vB.y += invMassB * P.y;
            wB += invIB * LB;
            pool.pushVec2(1);
        }
        else
        {
            impulse = 0.0f;
            springImpulse = 0.0f;
            motorImpulse = 0.0f;
        }
        pool.pushRot(2);
        pool.pushVec2(1);
        // data.velocities[indexA].v = vA;
        data.velocities[indexA].w = wA;
        // data.velocities[indexB].v = vB;
        data.velocities[indexB].w = wB;
    }

    /**
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_wheel_joint.cpp#L237-L342
     */
    @Override
    public void solveVelocityConstraints(SolverData data)
    {
        float mA = invMassA, mB = invMassB;
        float iA = invIA, iB = invIB;
        Vec2 vA = data.velocities[indexA].v;
        float wA = data.velocities[indexA].w;
        Vec2 vB = data.velocities[indexB].v;
        float wB = data.velocities[indexB].w;
        final Vec2 temp = pool.popVec2();
        final Vec2 P = pool.popVec2();
        // Solve spring constraint
        {
            float Cdot = Vec2.dot(ax, temp.set(vB).subLocal(vA)) + sBx * wB
                    - sAx * wA;
            float impulse = -springMass * (Cdot + bias + gamma * springImpulse);
            springImpulse += impulse;
            P.x = impulse * ax.x;
            P.y = impulse * ax.y;
            float LA = impulse * sAx;
            float LB = impulse * sBx;
            vA.x -= mA * P.x;
            vA.y -= mA * P.y;
            wA -= iA * LA;
            vB.x += mB * P.x;
            vB.y += mB * P.y;
            wB += iB * LB;
        }
        // Solve rotational motor constraint
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
        // Solve point to line constraint
        {
            float Cdot = Vec2.dot(ay, temp.set(vB).subLocal(vA)) + sBy * wB
                    - sAy * wA;
            float impulse = -mass * Cdot;
            this.impulse += impulse;
            P.x = impulse * ay.x;
            P.y = impulse * ay.y;
            float LA = impulse * sAy;
            float LB = impulse * sBy;
            vA.x -= mA * P.x;
            vA.y -= mA * P.y;
            wA -= iA * LA;
            vB.x += mB * P.x;
            vB.y += mB * P.y;
            wB += iB * LB;
        }
        pool.pushVec2(2);
        // data.velocities[indexA].v = vA;
        data.velocities[indexA].w = wA;
        // data.velocities[indexB].v = vB;
        data.velocities[indexB].w = wB;
    }

    /**
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_wheel_joint.cpp#L344-L444
     */
    @Override
    public boolean solvePositionConstraints(SolverData data)
    {
        Vec2 cA = data.positions[indexA].c;
        float aA = data.positions[indexA].a;
        Vec2 cB = data.positions[indexB].c;
        float aB = data.positions[indexB].a;
        final Rot qA = pool.popRot();
        final Rot qB = pool.popRot();
        final Vec2 temp = pool.popVec2();
        qA.set(aA);
        qB.set(aB);
        Rot.mulToOut(qA, temp.set(localAnchorA).subLocal(localCenterA), rA);
        Rot.mulToOut(qB, temp.set(localAnchorB).subLocal(localCenterB), rB);
        d.set(cB).subLocal(cA).addLocal(rB).subLocal(rA);
        Vec2 ay = pool.popVec2();
        Rot.mulToOut(qA, localYAxisA, ay);
        float sAy = Vec2.cross(temp.set(d).addLocal(rA), ay);
        float sBy = Vec2.cross(rB, ay);
        float C = Vec2.dot(d, ay);
        float k = invMassA + invMassB + invIA * this.sAy * this.sAy
                + invIB * this.sBy * this.sBy;
        float impulse;
        if (k != 0.0f)
        {
            impulse = -C / k;
        }
        else
        {
            impulse = 0.0f;
        }
        final Vec2 P = pool.popVec2();
        P.x = impulse * ay.x;
        P.y = impulse * ay.y;
        float LA = impulse * sAy;
        float LB = impulse * sBy;
        cA.x -= invMassA * P.x;
        cA.y -= invMassA * P.y;
        aA -= invIA * LA;
        cB.x += invMassB * P.x;
        cB.y += invMassB * P.y;
        aB += invIB * LB;
        pool.pushVec2(3);
        pool.pushRot(2);
        // data.positions[indexA].c = cA;
        data.positions[indexA].a = aA;
        // data.positions[indexB].c = cB;
        data.positions[indexB].a = aB;
        return MathUtils.abs(C) <= Settings.linearSlop;
    }
}
