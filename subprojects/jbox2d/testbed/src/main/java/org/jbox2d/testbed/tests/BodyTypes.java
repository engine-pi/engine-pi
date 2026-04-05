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
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.joints.PrismaticJointDef;
import org.jbox2d.dynamics.joints.RevoluteJointDef;
import org.jbox2d.testbed.framework.TestbedSettings;
import org.jbox2d.testbed.framework.TestbedTest;

/**
 * @author Daniel Murphy
 *
 * @repolink https://github.com/google/liquidfun/blob/master/liquidfun/Box2D/Testbed/Tests/BodyTypes.h
 */
public class BodyTypes extends TestbedTest
{
    private final static long ATTACHMENT_TAG = 19;

    private final static long PLATFORM_TAG = 20;

    Body attachment;

    Body platform;

    float speed;

    @Override
    public Long getTag(Body body)
    {
        if (body == attachment)
            return ATTACHMENT_TAG;
        if (body == platform)
            return PLATFORM_TAG;
        return super.getTag(body);
    }

    @Override
    public void processBody(Body body, Long tag)
    {
        if (tag == ATTACHMENT_TAG)
        {
            attachment = body;
        }
        else if (tag == PLATFORM_TAG)
        {
            platform = body;
        }
        else
        {
            super.processBody(body, tag);
        }
    }

    @Override
    public boolean isSaveLoadEnabled()
    {
        return true;
    }

    @Override
    public void initTest(boolean deserialized)
    {
        speed = 3.0f;
        if (deserialized)
        {
            return;
        }
        Body ground = null;
        {
            BodyDef bd = new BodyDef();
            ground = getWorld().createBody(bd);
            EdgeShape shape = new EdgeShape();
            shape.set(new Vec2(-20.0f, 0.0f), new Vec2(20.0f, 0.0f));
            FixtureDef fd = new FixtureDef();
            fd.shape = shape;
            ground.createFixture(fd);
        }
        // Define attachment
        {
            BodyDef bd = new BodyDef();
            bd.type = BodyType.DYNAMIC;
            bd.position.set(0.0f, 3.0f);
            attachment = getWorld().createBody(bd);
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(0.5f, 2.0f);
            attachment.createFixture(shape, 2.0f);
        }
        // Define platform
        {
            BodyDef bd = new BodyDef();
            bd.type = BodyType.DYNAMIC;
            bd.position.set(-4.0f, 5.0f);
            platform = getWorld().createBody(bd);
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(0.5f, 4.0f, new Vec2(4.0f, 0.0f),
                    0.5f * MathUtils.PI);
            FixtureDef fd = new FixtureDef();
            fd.shape = shape;
            fd.friction = 0.6f;
            fd.density = 2.0f;
            platform.createFixture(fd);
            RevoluteJointDef rjd = new RevoluteJointDef();
            rjd.initialize(attachment, platform, new Vec2(0.0f, 5.0f));
            rjd.maxMotorTorque = 50.0f;
            rjd.enableMotor = true;
            getWorld().createJoint(rjd);
            PrismaticJointDef pjd = new PrismaticJointDef();
            pjd.initialize(ground, platform, new Vec2(0.0f, 5.0f),
                    new Vec2(1.0f, 0.0f));
            pjd.maxMotorForce = 1000.0f;
            pjd.enableMotor = true;
            pjd.lowerTranslation = -10.0f;
            pjd.upperTranslation = 10.0f;
            pjd.enableLimit = true;
            getWorld().createJoint(pjd);
        }
        // .create a payload
        {
            BodyDef bd = new BodyDef();
            bd.type = BodyType.DYNAMIC;
            bd.position.set(0.0f, 8.0f);
            Body body = getWorld().createBody(bd);
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(0.75f, 0.75f);
            FixtureDef fd = new FixtureDef();
            fd.shape = shape;
            fd.friction = 0.6f;
            fd.density = 2.0f;
            body.createFixture(fd);
        }
    }

    @Override
    public void step(TestbedSettings settings)
    {
        super.step(settings);
        addTextLine("Keys: (d) dynamic, (s) static, (k) kinematic");
        // Drive the kinematic body.
        if (platform.getType() == BodyType.KINEMATIC)
        {
            Vec2 p = platform.getTransform().p;
            Vec2 v = platform.getLinearVelocity();
            if ((p.x < -10.0f && v.x < 0.0f) || (p.x > 10.0f && v.x > 0.0f))
            {
                v.x = -v.x;
                platform.setLinearVelocity(v);
            }
        }
    }

    @Override
    public void keyPressed(char argKeyChar, int argKeyCode)
    {
        switch (argKeyChar)
        {
        case 'd':
            platform.setType(BodyType.DYNAMIC);
            break;

        case 's':
            platform.setType(BodyType.STATIC);
            break;

        case 'k':
            platform.setType(BodyType.KINEMATIC);
            platform.setLinearVelocity(new Vec2(-speed, 0.0f));
            platform.setAngularVelocity(0.0f);
            break;
        }
    }

    @Override
    public String getTestName()
    {
        return "Body Types";
    }
}
