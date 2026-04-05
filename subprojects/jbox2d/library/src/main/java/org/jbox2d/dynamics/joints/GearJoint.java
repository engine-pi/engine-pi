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

import org.jbox2d.common.Rot;
import org.jbox2d.common.Settings;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.SolverData;
import org.jbox2d.pooling.WorldPool;

/**
 * A gear joint is used to connect two joints together. Either joint can be a
 * revolute or prismatic joint. You specify a gear ratio to bind the motions
 * together: coordinate1 + ratio * coordinate2 = constant The ratio can be
 * negative or positive. If one joint is a revolute joint and the other joint is
 * a prismatic joint, then the ratio will have units of length or units of
 * 1/length.
 *
 * <p>
 * <img src=
 * "https://github.com/engine-pi/jbox2d/blob/main/misc/images/joints/gear_joint.gif"
 * alt="gear joint">
 * </p>
 *
 * @warning The revolute and prismatic joints must be attached to fixed bodies
 *     (which must be body1 on those joints).
 * @warning You have to manually destroy the gear joint if joint1 or joint2 is
 *     destroyed.
 *
 * @repolink https://github.com/erincatto/box2d/blob/main/src/dynamics/b2_gear_joint.cpp
 *
 * @author Daniel Murphy
 */
public class GearJoint extends Joint
{
    /**
     * The first revolute/prismatic joint attached to the gear joint.
     */
    private final Joint joint1;

    /**
     * The second revolute/prismatic joint attached to the gear joint.
     */
    private final Joint joint2;

    /**
     * Gear ratio.
     *
     * @see GearJoint
     */
    private float ratio;

    private final JointType typeA;

    private final JointType typeB;

    // Body A is connected to body C
    // Body B is connected to body D
    private final Body bodyC;

    private final Body bodyD;

    // Solver shared
    private final Vec2 localAnchorA = new Vec2();

    private final Vec2 localAnchorB = new Vec2();

    private final Vec2 localAnchorC = new Vec2();

    private final Vec2 localAnchorD = new Vec2();

    private final Vec2 localAxisC = new Vec2();

    private final Vec2 localAxisD = new Vec2();

    private final float referenceAngleA;

    private final float referenceAngleB;

    private final float constant;

    private float impulse;

    // Solver temp
    private int indexA, indexB, indexC, indexD;

    private final Vec2 lcA = new Vec2(), lcB = new Vec2(), lcC = new Vec2(),
            lcD = new Vec2();

    private float mA, mB, mC, mD;

    private float iA, iB, iC, iD;

    private final Vec2 JvAC = new Vec2(), JvBD = new Vec2();

    private float JwA, JwB, JwC, JwD;

    private float mass;

    protected GearJoint(WorldPool argWorldPool, GearJointDef def)
    {
        super(argWorldPool, def);
        joint1 = def.joint1;
        joint2 = def.joint2;
        typeA = joint1.getType();
        typeB = joint2.getType();
        assert (typeA == JointType.REVOLUTE || typeA == JointType.PRISMATIC);
        assert (typeB == JointType.REVOLUTE || typeB == JointType.PRISMATIC);
        float coordinateA, coordinateB;
        // TODO_ERIN there might be some problem with the joint edges in Joint.
        bodyC = joint1.getBodyA();
        bodyA = joint1.getBodyB();
        // Get geometry of joint1
        Transform xfA = bodyA.xf;
        float aA = bodyA.sweep.a;
        Transform xfC = bodyC.xf;
        float aC = bodyC.sweep.a;
        if (typeA == JointType.REVOLUTE)
        {
            RevoluteJoint revolute = (RevoluteJoint) def.joint1;
            localAnchorC.set(revolute.localAnchorA);
            localAnchorA.set(revolute.localAnchorB);
            referenceAngleA = revolute.referenceAngle;
            localAxisC.setZero();
            coordinateA = aA - aC - referenceAngleA;
        }
        else
        {
            Vec2 pA = pool.popVec2();
            Vec2 temp = pool.popVec2();
            PrismaticJoint prismatic = (PrismaticJoint) def.joint1;
            localAnchorC.set(prismatic.localAnchorA);
            localAnchorA.set(prismatic.localAnchorB);
            referenceAngleA = prismatic.referenceAngle;
            localAxisC.set(prismatic.localXAxisA);
            Rot.mulToOutUnsafe(xfA.q, localAnchorA, temp);
            temp.addLocal(xfA.p).subLocal(xfC.p);
            Rot.mulTransUnsafe(xfC.q, temp, pA);
            coordinateA = Vec2.dot(pA.subLocal(localAnchorC), localAxisC);
            pool.pushVec2(2);
        }
        bodyD = joint2.getBodyA();
        bodyB = joint2.getBodyB();
        // Get geometry of joint2
        Transform xfB = bodyB.xf;
        float aB = bodyB.sweep.a;
        Transform xfD = bodyD.xf;
        float aD = bodyD.sweep.a;
        if (typeB == JointType.REVOLUTE)
        {
            RevoluteJoint revolute = (RevoluteJoint) def.joint2;
            localAnchorD.set(revolute.localAnchorA);
            localAnchorB.set(revolute.localAnchorB);
            referenceAngleB = revolute.referenceAngle;
            localAxisD.setZero();
            coordinateB = aB - aD - referenceAngleB;
        }
        else
        {
            Vec2 pB = pool.popVec2();
            Vec2 temp = pool.popVec2();
            PrismaticJoint prismatic = (PrismaticJoint) def.joint2;
            localAnchorD.set(prismatic.localAnchorA);
            localAnchorB.set(prismatic.localAnchorB);
            referenceAngleB = prismatic.referenceAngle;
            localAxisD.set(prismatic.localXAxisA);
            Rot.mulToOutUnsafe(xfB.q, localAnchorB, temp);
            temp.addLocal(xfB.p).subLocal(xfD.p);
            Rot.mulTransUnsafe(xfD.q, temp, pB);
            coordinateB = Vec2.dot(pB.subLocal(localAnchorD), localAxisD);
            pool.pushVec2(2);
        }
        ratio = def.ratio;
        constant = coordinateA + ratio * coordinateB;
        impulse = 0.0f;
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
        argOut.set(JvAC).mulLocal(impulse);
        argOut.mulLocal(invDt);
    }

    @Override
    public float getReactionTorque(float invDt)
    {
        float L = impulse * JwA;
        return invDt * L;
    }

    public void setRatio(float argRatio)
    {
        ratio = argRatio;
    }

    public float getRatio()
    {
        return ratio;
    }

    @Override
    public void initVelocityConstraints(SolverData data)
    {
        indexA = bodyA.islandIndex;
        indexB = bodyB.islandIndex;
        indexC = bodyC.islandIndex;
        indexD = bodyD.islandIndex;
        lcA.set(bodyA.sweep.localCenter);
        lcB.set(bodyB.sweep.localCenter);
        lcC.set(bodyC.sweep.localCenter);
        lcD.set(bodyD.sweep.localCenter);
        mA = bodyA.invMass;
        mB = bodyB.invMass;
        mC = bodyC.invMass;
        mD = bodyD.invMass;
        iA = bodyA.invI;
        iB = bodyB.invI;
        iC = bodyC.invI;
        iD = bodyD.invI;
        // Vec2 cA = data.positions[indexA].c;
        float aA = data.positions[indexA].a;
        Vec2 vA = data.velocities[indexA].v;
        float wA = data.velocities[indexA].w;
        // Vec2 cB = data.positions[indexB].c;
        float aB = data.positions[indexB].a;
        Vec2 vB = data.velocities[indexB].v;
        float wB = data.velocities[indexB].w;
        // Vec2 cC = data.positions[indexC].c;
        float aC = data.positions[indexC].a;
        Vec2 vC = data.velocities[indexC].v;
        float wC = data.velocities[indexC].w;
        // Vec2 cD = data.positions[indexD].c;
        float aD = data.positions[indexD].a;
        Vec2 vD = data.velocities[indexD].v;
        float wD = data.velocities[indexD].w;
        Rot qA = pool.popRot(), qB = pool.popRot(), qC = pool.popRot(),
                qD = pool.popRot();
        qA.set(aA);
        qB.set(aB);
        qC.set(aC);
        qD.set(aD);
        mass = 0.0f;
        Vec2 temp = pool.popVec2();
        if (typeA == JointType.REVOLUTE)
        {
            JvAC.setZero();
            JwA = 1.0f;
            JwC = 1.0f;
            mass += iA + iC;
        }
        else
        {
            Vec2 rC = pool.popVec2();
            Vec2 rA = pool.popVec2();
            Rot.mulToOutUnsafe(qC, localAxisC, JvAC);
            Rot.mulToOutUnsafe(qC, temp.set(localAnchorC).subLocal(lcC), rC);
            Rot.mulToOutUnsafe(qA, temp.set(localAnchorA).subLocal(lcA), rA);
            JwC = Vec2.cross(rC, JvAC);
            JwA = Vec2.cross(rA, JvAC);
            mass += mC + mA + iC * JwC * JwC + iA * JwA * JwA;
            pool.pushVec2(2);
        }
        if (typeB == JointType.REVOLUTE)
        {
            JvBD.setZero();
            JwB = ratio;
            JwD = ratio;
            mass += ratio * ratio * (iB + iD);
        }
        else
        {
            Vec2 u = pool.popVec2();
            Vec2 rD = pool.popVec2();
            Vec2 rB = pool.popVec2();
            Rot.mulToOutUnsafe(qD, localAxisD, u);
            Rot.mulToOutUnsafe(qD, temp.set(localAnchorD).subLocal(lcD), rD);
            Rot.mulToOutUnsafe(qB, temp.set(localAnchorB).subLocal(lcB), rB);
            JvBD.set(u).mulLocal(ratio);
            JwD = ratio * Vec2.cross(rD, u);
            JwB = ratio * Vec2.cross(rB, u);
            mass += ratio * ratio * (mD + mB) + iD * JwD * JwD + iB * JwB * JwB;
            pool.pushVec2(3);
        }
        // Compute effective mass.
        mass = mass > 0.0f ? 1.0f / mass : 0.0f;
        if (data.step.warmStarting)
        {
            vA.x += (mA * impulse) * JvAC.x;
            vA.y += (mA * impulse) * JvAC.y;
            wA += iA * impulse * JwA;
            vB.x += (mB * impulse) * JvBD.x;
            vB.y += (mB * impulse) * JvBD.y;
            wB += iB * impulse * JwB;
            vC.x -= (mC * impulse) * JvAC.x;
            vC.y -= (mC * impulse) * JvAC.y;
            wC -= iC * impulse * JwC;
            vD.x -= (mD * impulse) * JvBD.x;
            vD.y -= (mD * impulse) * JvBD.y;
            wD -= iD * impulse * JwD;
        }
        else
        {
            impulse = 0.0f;
        }
        pool.pushVec2(1);
        pool.pushRot(4);
        // data.velocities[indexA].v = vA;
        data.velocities[indexA].w = wA;
        // data.velocities[indexB].v = vB;
        data.velocities[indexB].w = wB;
        // data.velocities[indexC].v = vC;
        data.velocities[indexC].w = wC;
        // data.velocities[indexD].v = vD;
        data.velocities[indexD].w = wD;
    }

    @Override
    public void solveVelocityConstraints(SolverData data)
    {
        Vec2 vA = data.velocities[indexA].v;
        float wA = data.velocities[indexA].w;
        Vec2 vB = data.velocities[indexB].v;
        float wB = data.velocities[indexB].w;
        Vec2 vC = data.velocities[indexC].v;
        float wC = data.velocities[indexC].w;
        Vec2 vD = data.velocities[indexD].v;
        float wD = data.velocities[indexD].w;
        Vec2 temp1 = pool.popVec2();
        Vec2 temp2 = pool.popVec2();
        float Cdot = Vec2.dot(JvAC, temp1.set(vA).subLocal(vC))
                + Vec2.dot(JvBD, temp2.set(vB).subLocal(vD));
        Cdot += (JwA * wA - JwC * wC) + (JwB * wB - JwD * wD);
        pool.pushVec2(2);
        float impulse = -mass * Cdot;
        this.impulse += impulse;
        vA.x += (mA * impulse) * JvAC.x;
        vA.y += (mA * impulse) * JvAC.y;
        wA += iA * impulse * JwA;
        vB.x += (mB * impulse) * JvBD.x;
        vB.y += (mB * impulse) * JvBD.y;
        wB += iB * impulse * JwB;
        vC.x -= (mC * impulse) * JvAC.x;
        vC.y -= (mC * impulse) * JvAC.y;
        wC -= iC * impulse * JwC;
        vD.x -= (mD * impulse) * JvBD.x;
        vD.y -= (mD * impulse) * JvBD.y;
        wD -= iD * impulse * JwD;
        // data.velocities[indexA].v = vA;
        data.velocities[indexA].w = wA;
        // data.velocities[indexB].v = vB;
        data.velocities[indexB].w = wB;
        // data.velocities[indexC].v = vC;
        data.velocities[indexC].w = wC;
        // data.velocities[indexD].v = vD;
        data.velocities[indexD].w = wD;
    }

    public Joint getJoint1()
    {
        return joint1;
    }

    public Joint getJoint2()
    {
        return joint2;
    }

    @Override
    public boolean solvePositionConstraints(SolverData data)
    {
        Vec2 cA = data.positions[indexA].c;
        float aA = data.positions[indexA].a;
        Vec2 cB = data.positions[indexB].c;
        float aB = data.positions[indexB].a;
        Vec2 cC = data.positions[indexC].c;
        float aC = data.positions[indexC].a;
        Vec2 cD = data.positions[indexD].c;
        float aD = data.positions[indexD].a;
        Rot qA = pool.popRot(), qB = pool.popRot(), qC = pool.popRot(),
                qD = pool.popRot();
        qA.set(aA);
        qB.set(aB);
        qC.set(aC);
        qD.set(aD);
        float linearError = 0.0f;
        float coordinateA, coordinateB;
        Vec2 temp = pool.popVec2();
        Vec2 JvAC = pool.popVec2();
        Vec2 JvBD = pool.popVec2();
        float JwA, JwB, JwC, JwD;
        float mass = 0.0f;
        if (typeA == JointType.REVOLUTE)
        {
            JvAC.setZero();
            JwA = 1.0f;
            JwC = 1.0f;
            mass += iA + iC;
            coordinateA = aA - aC - referenceAngleA;
        }
        else
        {
            Vec2 rC = pool.popVec2();
            Vec2 rA = pool.popVec2();
            Vec2 pC = pool.popVec2();
            Vec2 pA = pool.popVec2();
            Rot.mulToOutUnsafe(qC, localAxisC, JvAC);
            Rot.mulToOutUnsafe(qC, temp.set(localAnchorC).subLocal(lcC), rC);
            Rot.mulToOutUnsafe(qA, temp.set(localAnchorA).subLocal(lcA), rA);
            JwC = Vec2.cross(rC, JvAC);
            JwA = Vec2.cross(rA, JvAC);
            mass += mC + mA + iC * JwC * JwC + iA * JwA * JwA;
            pC.set(localAnchorC).subLocal(lcC);
            Rot.mulTransUnsafe(qC, temp.set(rA).addLocal(cA).subLocal(cC), pA);
            coordinateA = Vec2.dot(pA.subLocal(pC), localAxisC);
            pool.pushVec2(4);
        }
        if (typeB == JointType.REVOLUTE)
        {
            JvBD.setZero();
            JwB = ratio;
            JwD = ratio;
            mass += ratio * ratio * (iB + iD);
            coordinateB = aB - aD - referenceAngleB;
        }
        else
        {
            Vec2 u = pool.popVec2();
            Vec2 rD = pool.popVec2();
            Vec2 rB = pool.popVec2();
            Vec2 pD = pool.popVec2();
            Vec2 pB = pool.popVec2();
            Rot.mulToOutUnsafe(qD, localAxisD, u);
            Rot.mulToOutUnsafe(qD, temp.set(localAnchorD).subLocal(lcD), rD);
            Rot.mulToOutUnsafe(qB, temp.set(localAnchorB).subLocal(lcB), rB);
            JvBD.set(u).mulLocal(ratio);
            JwD = Vec2.cross(rD, u);
            JwB = Vec2.cross(rB, u);
            mass += ratio * ratio * (mD + mB) + iD * JwD * JwD + iB * JwB * JwB;
            pD.set(localAnchorD).subLocal(lcD);
            Rot.mulTransUnsafe(qD, temp.set(rB).addLocal(cB).subLocal(cD), pB);
            coordinateB = Vec2.dot(pB.subLocal(pD), localAxisD);
            pool.pushVec2(5);
        }
        float C = (coordinateA + ratio * coordinateB) - constant;
        float impulse = 0.0f;
        if (mass > 0.0f)
        {
            impulse = -C / mass;
        }
        pool.pushVec2(3);
        pool.pushRot(4);
        cA.x += (mA * impulse) * JvAC.x;
        cA.y += (mA * impulse) * JvAC.y;
        aA += iA * impulse * JwA;
        cB.x += (mB * impulse) * JvBD.x;
        cB.y += (mB * impulse) * JvBD.y;
        aB += iB * impulse * JwB;
        cC.x -= (mC * impulse) * JvAC.x;
        cC.y -= (mC * impulse) * JvAC.y;
        aC -= iC * impulse * JwC;
        cD.x -= (mD * impulse) * JvBD.x;
        cD.y -= (mD * impulse) * JvBD.y;
        aD -= iD * impulse * JwD;
        // data.positions[indexA].c = cA;
        data.positions[indexA].a = aA;
        // data.positions[indexB].c = cB;
        data.positions[indexB].a = aB;
        // data.positions[indexC].c = cC;
        data.positions[indexC].a = aC;
        // data.positions[indexD].c = cD;
        data.positions[indexD].a = aD;
        // TODO_ERIN not implemented
        return linearError < Settings.linearSlop;
    }
}
