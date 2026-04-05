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

/**
 * Motor joint definition.
 *
 * @author Daniel Murphy
 */
public class MotorJointDef extends JointDef
{
    /**
     * Position of bodyB minus the position of bodyA, in bodyA's frame, in
     * meters.
     */
    public final Vec2 linearOffset = new Vec2();

    /**
     * The bodyB angle minus bodyA angle in radians.
     */
    public float angularOffset;

    /**
     * The maximum motor force in N.
     */
    public float maxForce;

    /**
     * The maximum motor torque in N-m.
     */
    public float maxTorque;

    /**
     * Position correction factor in the range [0,1].
     */
    public float correctionFactor;

    public MotorJointDef()
    {
        super(JointType.MOTOR);
        angularOffset = 0;
        maxForce = 1;
        maxTorque = 1;
        correctionFactor = 0.3f;
    }

    public void initialize(Body bA, Body bB)
    {
        bodyA = bA;
        bodyB = bB;
        Vec2 xB = bodyB.getPosition();
        bodyA.getLocalPointToOut(xB, linearOffset);
        float angleA = bodyA.getAngle();
        float angleB = bodyB.getAngle();
        angularOffset = angleB - angleA;
    }
}
