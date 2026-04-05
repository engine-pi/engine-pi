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
import org.jbox2d.common.Settings;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.SolverData;
import org.jbox2d.pooling.WorldPool;

/**
 * A mouse joint is used to make a point on a body track a specified world
 * point. This a soft constraint with a maximum force. This allows the
 * constraint to stretch and without applying huge forces. NOTE: this joint is
 * not documented in the manual because it was developed to be used in the
 * testbed. If you want to learn how to use the mouse joint, look at the
 * testbed.
 *
 * @repolink https://github.com/erincatto/box2d/blob/main/src/dynamics/b2_mouse_joint.cpp
 *
 * @author Daniel Murphy
 */
public class MouseJoint extends Joint
{
    private final Vec2 localAnchorB = new Vec2();

    private final Vec2 targetA = new Vec2();

    /**
     * The maximum constraint force that can be exerted to move the candidate
     * body. Usually you will express as some multiple of the weight (multiplier
     * * mass * gravity).
     */
    private float maxForce;

    /**
     * The response speed.
     */
    private float frequencyHz;

    /**
     * The damping ratio. 0 = no damping, 1 = critical damping.
     */
    private float dampingRatio;

    private float beta;

    // Solver shared
    private final Vec2 impulse = new Vec2();

    private float gamma;

    // Solver temp
    private int indexB;

    private final Vec2 rB = new Vec2();

    private final Vec2 localCenterB = new Vec2();

    private float invMassB;

    private float invIB;

    private final Mat22 mass = new Mat22();

    private final Vec2 C = new Vec2();

    protected MouseJoint(WorldPool argWorld, MouseJointDef def)
    {
        super(argWorld, def);
        assert (def.target.isValid());
        assert (def.maxForce >= 0);
        assert (def.frequencyHz >= 0);
        assert (def.dampingRatio >= 0);
        targetA.set(def.target);
        Transform
            .mulTransToOutUnsafe(bodyB.getTransform(), targetA, localAnchorB);
        maxForce = def.maxForce;
        impulse.setZero();
        frequencyHz = def.frequencyHz;
        dampingRatio = def.dampingRatio;
        beta = 0;
        gamma = 0;
    }

    @Override
    public void getAnchorA(Vec2 argOut)
    {
        argOut.set(targetA);
    }

    @Override
    public void getAnchorB(Vec2 argOut)
    {
        bodyB.getWorldPointToOut(localAnchorB, argOut);
    }

    @Override
    public void getReactionForce(float invDt, Vec2 argOut)
    {
        argOut.set(impulse).mulLocal(invDt);
    }

    @Override
    public float getReactionTorque(float invDt)
    {
        return invDt * 0.0f;
    }

    public void setTarget(Vec2 target)
    {
        if (!bodyB.isAwake())
        {
            bodyB.setAwake(true);
        }
        targetA.set(target);
    }

    public Vec2 getTarget()
    {
        return targetA;
    }

    // / set/get the maximum force in Newtons.
    public void setMaxForce(float force)
    {
        maxForce = force;
    }

    public float getMaxForce()
    {
        return maxForce;
    }

    // / set/get the frequency in Hertz.
    public void setFrequency(float hz)
    {
        frequencyHz = hz;
    }

    public float getFrequency()
    {
        return frequencyHz;
    }

    // / set/get the damping ratio (dimensionless).
    public void setDampingRatio(float ratio)
    {
        dampingRatio = ratio;
    }

    public float getDampingRatio()
    {
        return dampingRatio;
    }

    @Override
    public void initVelocityConstraints(final SolverData data)
    {
        indexB = bodyB.islandIndex;
        localCenterB.set(bodyB.sweep.localCenter);
        invMassB = bodyB.invMass;
        invIB = bodyB.invI;
        Vec2 cB = data.positions[indexB].c;
        float aB = data.positions[indexB].a;
        Vec2 vB = data.velocities[indexB].v;
        float wB = data.velocities[indexB].w;
        final Rot qB = pool.popRot();
        qB.set(aB);
        float mass = bodyB.getMass();
        // Frequency
        float omega = 2.0f * MathUtils.PI * frequencyHz;
        // Damping coefficient
        float d = 2.0f * mass * dampingRatio * omega;
        // Spring stiffness
        float k = mass * (omega * omega);
        // magic formulas
        // gamma has units of inverse mass.
        // beta has units of inverse time.
        float h = data.step.dt;
        assert (d + h * k > Settings.EPSILON);
        gamma = h * (d + h * k);
        if (gamma != 0.0f)
        {
            gamma = 1.0f / gamma;
        }
        beta = h * k * gamma;
        Vec2 temp = pool.popVec2();
        // Compute the effective mass matrix.
        Rot.mulToOutUnsafe(qB,
            temp.set(localAnchorB).subLocal(localCenterB),
            rB);
        // K = [(1/m1 + 1/m2) * eye(2) - skew(r1) * invI1 * skew(r1) - skew(r2)
        // * invI2 * skew(r2)]
        // = [1/m1+1/m2 0 ] + invI1 * [r1.y*r1.y -r1.x*r1.y] + invI2 *
        // [r1.y*r1.y -r1.x*r1.y]
        // [ 0 1/m1+1/m2] [-r1.x*r1.y r1.x*r1.x] [-r1.x*r1.y r1.x*r1.x]
        final Mat22 K = pool.popMat22();
        K.ex.x = invMassB + invIB * rB.y * rB.y + gamma;
        K.ex.y = -invIB * rB.x * rB.y;
        K.ey.x = K.ex.y;
        K.ey.y = invMassB + invIB * rB.x * rB.x + gamma;
        K.invertToOut(this.mass);
        C.set(cB).addLocal(rB).subLocal(targetA);
        C.mulLocal(beta);
        // Cheat with some damping
        wB *= 0.98f;
        if (data.step.warmStarting)
        {
            impulse.mulLocal(data.step.dtRatio);
            vB.x += invMassB * impulse.x;
            vB.y += invMassB * impulse.y;
            wB += invIB * Vec2.cross(rB, impulse);
        }
        else
        {
            impulse.setZero();
        }
//    data.velocities[indexB].v.set(vB);
        data.velocities[indexB].w = wB;
        pool.pushVec2(1);
        pool.pushMat22(1);
        pool.pushRot(1);
    }

    @Override
    public boolean solvePositionConstraints(final SolverData data)
    {
        return true;
    }

    @Override
    public void solveVelocityConstraints(final SolverData data)
    {
        Vec2 vB = data.velocities[indexB].v;
        float wB = data.velocities[indexB].w;
        // Cdot = v + cross(w, r)
        final Vec2 Cdot = pool.popVec2();
        Vec2.crossToOutUnsafe(wB, rB, Cdot);
        Cdot.addLocal(vB);
        final Vec2 impulse = pool.popVec2();
        final Vec2 temp = pool.popVec2();
        temp.set(this.impulse)
            .mulLocal(gamma)
            .addLocal(C)
            .addLocal(Cdot)
            .negateLocal();
        Mat22.mulToOutUnsafe(mass, temp, impulse);
        temp.set(this.impulse);
        this.impulse.addLocal(impulse);
        float maxImpulse = data.step.dt * maxForce;
        if (this.impulse.lengthSquared() > maxImpulse * maxImpulse)
        {
            this.impulse.mulLocal(maxImpulse / this.impulse.length());
        }
        impulse.set(this.impulse).subLocal(temp);
        vB.x += invMassB * impulse.x;
        vB.y += invMassB * impulse.y;
        wB += invIB * Vec2.cross(rB, impulse);
//    data.velocities[indexB].v.set(vB);
        data.velocities[indexB].w = wB;
        pool.pushVec2(3);
    }
}
