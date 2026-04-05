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
 * A distance joint constrains two points on two bodies to remain at a fixed
 * distance from each other. You can view this as a massless, rigid rod.
 *
 * <p>
 * One of the simplest joint is a distance joint which says that the distance
 * between two points on two bodies must be constant. When you specify a
 * distance joint the two bodies should already be in place. Then you specify
 * the two anchor points in world coordinates. The first anchor point is
 * connected to body 1, and the second anchor point is connected to body 2.
 * These points imply the length of the distance constraint.
 * </p>
 *
 * <p>
 * <img src=
 * "https://github.com/engine-pi/jbox2d/blob/main/misc/images/joints/distance_joint.svg"
 * alt="distance joint">
 * </p>
 *
 * <p>
 * Here is an example of a distance joint definition. In this case we decide to
 * allow the bodies to collide.
 * </p>
 *
 * <p>
 * The distance joint can also be made soft, like a spring-damper connection.
 * </p>
 *
 * <p>
 * Softness is achieved by tuning two constants in the definition: stiffness and
 * damping. It can be non-intuitive setting these values directly since they
 * have units in terms on Newtons.
 * </p>
 *
 * <p>
 * Think of the frequency as the frequency of a harmonic oscillator (like a
 * guitar string). The frequency is specified in Hertz. Typically the frequency
 * should be less than a half the frequency of the time step. So if you are
 * using a 60Hz time step, the frequency of the distance joint should be less
 * than 30Hz. The reason is related to the Nyquist frequency.
 * </p>
 *
 * <p>
 * The damping ratio is non-dimensional and is typically between 0 and 1, but
 * can be larger. At 1, the damping is critical (all oscillations should
 * vanish).
 * </p>
 *
 * <p>
 * It is also possible to define a minimum and maximum length for the distance
 * joint.
 * </p>
 *
 * @repolink https://github.com/erincatto/box2d/blob/main/src/dynamics/b2_distance_joint.cpp
 *
 * @author Daniel Murphy
 */
public class DistanceJoint extends Joint
{
    /**
     * The local anchor point relative to bodyA's origin.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_distance_joint.h#L52-L53
     */
    private final Vec2 localAnchorA;

    /**
     * The local anchor point relative to bodyB's origin.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_distance_joint.h#L55-L56
     */
    private final Vec2 localAnchorB;

    /**
     * The equilibrium length between the anchor points.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_distance_joint.h#L58-L59
     */
    private float length;

    /**
     * The mass-spring-damper frequency in Hertz.
     */
    private float frequencyHz;

    /**
     * The damping ratio. 0 = no damping, 1 = critical damping.
     */
    private float dampingRatio;

    private float bias;

    private float gamma;

    private float impulse;

    // Solver temp
    private int indexA;

    private int indexB;

    private final Vec2 u = new Vec2();

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
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_distance_joint.cpp#L44-L74
     */
    protected DistanceJoint(WorldPool argWorld, final DistanceJointDef def)
    {
        super(argWorld, def);
        localAnchorA = def.localAnchorA.clone();
        localAnchorB = def.localAnchorB.clone();
        length = def.length;
        impulse = 0.0f;
        frequencyHz = def.frequencyHz;
        dampingRatio = def.dampingRatio;
        gamma = 0.0f;
        bias = 0.0f;
    }

    /**
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_distance_joint.cpp#L76-L173
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
        qA.set(aA);
        qB.set(aB);
        // use u as temporary variable
        Rot.mulToOutUnsafe(qA, u.set(localAnchorA).subLocal(localCenterA), rA);
        Rot.mulToOutUnsafe(qB, u.set(localAnchorB).subLocal(localCenterB), rB);
        u.set(cB).addLocal(rB).subLocal(cA).subLocal(rA);
        pool.pushRot(2);
        // Handle singularity.
        float length = u.length();
        if (length > Settings.linearSlop)
        {
            u.x *= 1.0f / length;
            u.y *= 1.0f / length;
        }
        else
        {
            u.set(0.0f, 0.0f);
        }
        float crAu = Vec2.cross(rA, u);
        float crBu = Vec2.cross(rB, u);
        float invMass = invMassA + invIA * crAu * crAu + invMassB
                + invIB * crBu * crBu;
        // Compute the effective mass matrix.
        mass = invMass != 0.0f ? 1.0f / invMass : 0.0f;
        if (frequencyHz > 0.0f)
        {
            float C = length - this.length;
            // Frequency
            float omega = 2.0f * MathUtils.PI * frequencyHz;
            // Damping coefficient
            float d = 2.0f * mass * dampingRatio * omega;
            // Spring stiffness
            float k = mass * omega * omega;
            // magic formulas
            float h = data.step.dt;
            gamma = h * (d + h * k);
            gamma = gamma != 0.0f ? 1.0f / gamma : 0.0f;
            bias = C * h * k * gamma;
            invMass += gamma;
            mass = invMass != 0.0f ? 1.0f / invMass : 0.0f;
        }
        else
        {
            gamma = 0.0f;
            bias = 0.0f;
        }
        if (data.step.warmStarting)
        {
            // Scale the impulse to support a variable time step.
            impulse *= data.step.dtRatio;
            Vec2 P = pool.popVec2();
            P.set(u).mulLocal(impulse);
            vA.x -= invMassA * P.x;
            vA.y -= invMassA * P.y;
            wA -= invIA * Vec2.cross(rA, P);
            vB.x += invMassB * P.x;
            vB.y += invMassB * P.y;
            wB += invIB * Vec2.cross(rB, P);
            pool.pushVec2(1);
        }
        else
        {
            impulse = 0.0f;
        }
        // data.velocities[indexA].v.set(vA);
        data.velocities[indexA].w = wA;
        // data.velocities[indexB].v.set(vB);
        data.velocities[indexB].w = wB;
    }

    /**
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_wheel_joint.cpp#L237-L342
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
        // Cdot = dot(u, v + cross(w, r))
        Vec2.crossToOutUnsafe(wA, rA, vpA);
        vpA.addLocal(vA);
        Vec2.crossToOutUnsafe(wB, rB, vpB);
        vpB.addLocal(vB);
        float Cdot = Vec2.dot(u, vpB.subLocal(vpA));
        float impulse = -mass * (Cdot + bias + gamma * this.impulse);
        this.impulse += impulse;
        float Px = impulse * u.x;
        float Py = impulse * u.y;
        vA.x -= invMassA * Px;
        vA.y -= invMassA * Py;
        wA -= invIA * (rA.x * Py - rA.y * Px);
        vB.x += invMassB * Px;
        vB.y += invMassB * Py;
        wB += invIB * (rB.x * Py - rB.y * Px);
        // data.velocities[indexA].v.set(vA);
        data.velocities[indexA].w = wA;
        // data.velocities[indexB].v.set(vB);
        data.velocities[indexB].w = wB;
        pool.pushVec2(2);
    }

    /**
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_distance_joint.cpp#L268-L314
     */
    @Override
    public boolean solvePositionConstraints(final SolverData data)
    {
        if (frequencyHz > 0.0f)
        {
            return true;
        }
        final Rot qA = pool.popRot();
        final Rot qB = pool.popRot();
        final Vec2 rA = pool.popVec2();
        final Vec2 rB = pool.popVec2();
        final Vec2 u = pool.popVec2();
        Vec2 cA = data.positions[indexA].c;
        float aA = data.positions[indexA].a;
        Vec2 cB = data.positions[indexB].c;
        float aB = data.positions[indexB].a;
        qA.set(aA);
        qB.set(aB);
        Rot.mulToOutUnsafe(qA, u.set(localAnchorA).subLocal(localCenterA), rA);
        Rot.mulToOutUnsafe(qB, u.set(localAnchorB).subLocal(localCenterB), rB);
        u.set(cB).addLocal(rB).subLocal(cA).subLocal(rA);
        float length = u.normalize();
        float C = length - this.length;
        C = MathUtils.clamp(C,
            -Settings.maxLinearCorrection,
            Settings.maxLinearCorrection);
        float impulse = -mass * C;
        float Px = impulse * u.x;
        float Py = impulse * u.y;
        cA.x -= invMassA * Px;
        cA.y -= invMassA * Py;
        aA -= invIA * (rA.x * Py - rA.y * Px);
        cB.x += invMassB * Px;
        cB.y += invMassB * Py;
        aB += invIB * (rB.x * Py - rB.y * Px);
        // data.positions[indexA].c.set(cA);
        data.positions[indexA].a = aA;
        // data.positions[indexB].c.set(cB);
        data.positions[indexB].a = aB;
        pool.pushVec2(3);
        pool.pushRot(2);
        return MathUtils.abs(C) < Settings.linearSlop;
    }

    /**
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_distance_joint.cpp#L316-L319
     */
    @Override
    public void getAnchorA(Vec2 argOut)
    {
        bodyA.getWorldPointToOut(localAnchorA, argOut);
    }

    /**
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_distance_joint.cpp#L321-L324
     */
    @Override
    public void getAnchorB(Vec2 argOut)
    {
        bodyB.getWorldPointToOut(localAnchorB, argOut);
    }

    /**
     * Get the reaction force given the inverse time step. Unit is N.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_distance_joint.cpp#L326-L330
     */
    @Override
    public void getReactionForce(float invDt, Vec2 argOut)
    {
        argOut.x = impulse * u.x * invDt;
        argOut.y = impulse * u.y * invDt;
    }

    /**
     * Get the reaction torque given the inverse time step. Unit is N*m. This is
     * always zero for a distance joint.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_distance_joint.cpp#L332-L336
     */
    @Override
    public float getReactionTorque(float invDt)
    {
        return 0.0f;
    }

    /**
     * Get the rest length.
     *
     * @return The rest length.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_distance_joint.h#L97-L98
     */
    public float getLength()
    {
        return length;
    }

    /**
     * Set the rest length.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/src/dynamics/b2_distance_joint.cpp#L338-L343
     */
    public void setLength(float length)
    {
        this.length = length;
    }

    public void setFrequency(float hz)
    {
        frequencyHz = hz;
    }

    public float getFrequency()
    {
        return frequencyHz;
    }

    public void setDampingRatio(float damp)
    {
        dampingRatio = damp;
    }

    public float getDampingRatio()
    {
        return dampingRatio;
    }

    /**
     * Get the local anchor point relative to bodyA's origin.
     *
     * @return The local anchor point relative to bodyA's origin.
     *
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_distance_joint.h#L91-L92
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
     * @repolink https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_distance_joint.h#L94-L95
     */
    public Vec2 getLocalAnchorB()
    {
        return localAnchorB;
    }

}
