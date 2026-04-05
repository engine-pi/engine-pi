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
package org.jbox2d.testbed.tests;

import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.joints.DistanceJointDef;
import org.jbox2d.dynamics.joints.Joint;
import org.jbox2d.testbed.framework.TestbedSettings;
import org.jbox2d.testbed.framework.TestbedTest;

/**
 * @author Daniel Murphy
 *
 * @repolink https://github.com/google/liquidfun/blob/master/liquidfun/Box2D/Testbed/Tests/Web.h
 */
public class Web extends TestbedTest
{
    Body bodies[] = new Body[4];

    Joint joints[] = new Joint[8];

    @Override
    public void initTest(boolean argDeserialized)
    {
        Body ground = null;
        {
            BodyDef bd = new BodyDef();
            ground = getWorld().createBody(bd);
            EdgeShape shape = new EdgeShape();
            shape.set(new Vec2(-40.0f, 0.0f), new Vec2(40.0f, 0.0f));
            ground.createFixture(shape, 0.0f);
        }
        {
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(0.5f, 0.5f);
            BodyDef bd = new BodyDef();
            bd.type = BodyType.DYNAMIC;
            bd.position.set(-5.0f, 5.0f);
            bodies[0] = getWorld().createBody(bd);
            bodies[0].createFixture(shape, 5.0f);
            bd.position.set(5.0f, 5.0f);
            bodies[1] = getWorld().createBody(bd);
            bodies[1].createFixture(shape, 5.0f);
            bd.position.set(5.0f, 15.0f);
            bodies[2] = getWorld().createBody(bd);
            bodies[2].createFixture(shape, 5.0f);
            bd.position.set(-5.0f, 15.0f);
            bodies[3] = getWorld().createBody(bd);
            bodies[3].createFixture(shape, 5.0f);
            DistanceJointDef jd = new DistanceJointDef();
            Vec2 p1 = new Vec2();
            Vec2 p2 = new Vec2();
            Vec2 d = new Vec2();
            jd.frequencyHz = 4.0f;
            jd.dampingRatio = 0.5f;
            jd.bodyA = ground;
            jd.bodyB = bodies[0];
            jd.localAnchorA.set(-10.0f, 0.0f);
            jd.localAnchorB.set(-0.5f, -0.5f);
            p1 = jd.bodyA.getWorldPoint(jd.localAnchorA);
            p2 = jd.bodyB.getWorldPoint(jd.localAnchorB);
            d = p2.sub(p1);
            jd.length = d.length();
            joints[0] = getWorld().createJoint(jd);
            jd.bodyA = ground;
            jd.bodyB = bodies[1];
            jd.localAnchorA.set(10.0f, 0.0f);
            jd.localAnchorB.set(0.5f, -0.5f);
            p1 = jd.bodyA.getWorldPoint(jd.localAnchorA);
            p2 = jd.bodyB.getWorldPoint(jd.localAnchorB);
            d = p2.sub(p1);
            jd.length = d.length();
            joints[1] = getWorld().createJoint(jd);
            jd.bodyA = ground;
            jd.bodyB = bodies[2];
            jd.localAnchorA.set(10.0f, 20.0f);
            jd.localAnchorB.set(0.5f, 0.5f);
            p1 = jd.bodyA.getWorldPoint(jd.localAnchorA);
            p2 = jd.bodyB.getWorldPoint(jd.localAnchorB);
            d = p2.sub(p1);
            jd.length = d.length();
            joints[2] = getWorld().createJoint(jd);
            jd.bodyA = ground;
            jd.bodyB = bodies[3];
            jd.localAnchorA.set(-10.0f, 20.0f);
            jd.localAnchorB.set(-0.5f, 0.5f);
            p1 = jd.bodyA.getWorldPoint(jd.localAnchorA);
            p2 = jd.bodyB.getWorldPoint(jd.localAnchorB);
            d = p2.sub(p1);
            jd.length = d.length();
            joints[3] = getWorld().createJoint(jd);
            jd.bodyA = bodies[0];
            jd.bodyB = bodies[1];
            jd.localAnchorA.set(0.5f, 0.0f);
            jd.localAnchorB.set(-0.5f, 0.0f);;
            p1 = jd.bodyA.getWorldPoint(jd.localAnchorA);
            p2 = jd.bodyB.getWorldPoint(jd.localAnchorB);
            d = p2.sub(p1);
            jd.length = d.length();
            joints[4] = getWorld().createJoint(jd);
            jd.bodyA = bodies[1];
            jd.bodyB = bodies[2];
            jd.localAnchorA.set(0.0f, 0.5f);
            jd.localAnchorB.set(0.0f, -0.5f);
            p1 = jd.bodyA.getWorldPoint(jd.localAnchorA);
            p2 = jd.bodyB.getWorldPoint(jd.localAnchorB);
            d = p2.sub(p1);
            jd.length = d.length();
            joints[5] = getWorld().createJoint(jd);
            jd.bodyA = bodies[2];
            jd.bodyB = bodies[3];
            jd.localAnchorA.set(-0.5f, 0.0f);
            jd.localAnchorB.set(0.5f, 0.0f);
            p1 = jd.bodyA.getWorldPoint(jd.localAnchorA);
            p2 = jd.bodyB.getWorldPoint(jd.localAnchorB);
            d = p2.sub(p1);
            jd.length = d.length();
            joints[6] = getWorld().createJoint(jd);
            jd.bodyA = bodies[3];
            jd.bodyB = bodies[0];
            jd.localAnchorA.set(0.0f, -0.5f);
            jd.localAnchorB.set(0.0f, 0.5f);
            p1 = jd.bodyA.getWorldPoint(jd.localAnchorA);
            p2 = jd.bodyB.getWorldPoint(jd.localAnchorB);
            d = p2.sub(p1);
            jd.length = d.length();
            joints[7] = getWorld().createJoint(jd);
        }
    }

    @Override
    public void keyPressed(char key, int argKeyCode)
    {
        switch (key)
        {
        case 'b':
            for (int i = 0; i < 4; ++i)
            {
                if (bodies[i] != null)
                {
                    getWorld().destroyBody(bodies[i]);
                    bodies[i] = null;
                    break;
                }
            }
            break;

        case 'j':
            for (int i = 0; i < 8; ++i)
            {
                if (joints[i] != null)
                {
                    getWorld().destroyJoint(joints[i]);
                    joints[i] = null;
                    break;
                }
            }
            break;
        }
    }

    @Override
    public void step(TestbedSettings settings)
    {
        super.step(settings);
        addTextLine("This demonstrates a soft distance joint.");
        addTextLine("Press: (b) to delete a body, (j) to delete a joint");
    }

    public void jointDestroyed(Joint joint)
    {
        for (int i = 0; i < 8; ++i)
        {
            if (joints[i] == joint)
            {
                joints[i] = null;
                break;
            }
        }
    }

    @Override
    public String getTestName()
    {
        return "Web";
    }
}
