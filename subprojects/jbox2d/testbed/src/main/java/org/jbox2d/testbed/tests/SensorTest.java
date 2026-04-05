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

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.common.Settings;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.contacts.Contact;
import org.jbox2d.testbed.framework.TestbedSettings;
import org.jbox2d.testbed.framework.TestbedTest;

/**
 * @author Daniel Murphy
 *
 * @repolink https://github.com/google/liquidfun/blob/master/liquidfun/Box2D/Testbed/Tests/SensorTest.h
 */
public class SensorTest extends TestbedTest
{
    class BoolWrapper
    {
        boolean tf;
    }

    int count = 7;

    Fixture sensor;

    Body bodies[] = new Body[count];

    BoolWrapper touching[] = new BoolWrapper[count];

    @Override
    public void initTest(boolean deserialized)
    {
        for (int i = 0; i < touching.length; i++)
        {
            touching[i] = new BoolWrapper();
        }
        {
            BodyDef bd = new BodyDef();
            Body ground = getWorld().createBody(bd);
            {
                EdgeShape shape = new EdgeShape();
                shape.set(new Vec2(-40.0f, 0.0f), new Vec2(40.0f, 0.0f));
                ground.createFixture(shape, 0.0f);
            }
            {
                CircleShape shape = new CircleShape();
                shape.radius = 5.0f;
                shape.p.set(0.0f, 10.0f);
                FixtureDef fd = new FixtureDef();
                fd.shape = shape;
                fd.isSensor = true;
                sensor = ground.createFixture(fd);
            }
        }
        {
            CircleShape shape = new CircleShape();
            shape.radius = 1.0f;
            for (int i = 0; i < count; ++i)
            {
                BodyDef bd = new BodyDef();
                bd.type = BodyType.DYNAMIC;
                bd.position.set(-10.0f + 3.0f * i, 20.0f);
                bd.userData = touching[i];
                touching[i].tf = false;
                bodies[i] = getWorld().createBody(bd);
                bodies[i].createFixture(shape, 1.0f);
            }
        }
    }

    // Implement contact listener.
    public void beginContact(Contact contact)
    {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        if (fixtureA == sensor)
        {
            Object userData = fixtureB.getBody().getUserData();
            if (userData != null)
            {
                ((BoolWrapper) userData).tf = true;
            }
        }
        if (fixtureB == sensor)
        {
            Object userData = fixtureA.getBody().getUserData();
            if (userData != null)
            {
                ((BoolWrapper) userData).tf = true;
            }
        }
    }

    // Implement contact listener.
    public void endContact(Contact contact)
    {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        if (fixtureA == sensor)
        {
            Object userData = fixtureB.getBody().getUserData();
            if (userData != null)
            {
                ((BoolWrapper) userData).tf = false;
            }
        }
        if (fixtureB == sensor)
        {
            Object userData = fixtureA.getBody().getUserData();
            if (userData != null)
            {
                ((BoolWrapper) userData).tf = false;
            }
        }
    }

    @Override
    public void step(TestbedSettings settings)
    {
        super.step(settings);
        // Traverse the contact results. Apply a force on shapes
        // that overlap the sensor.
        for (int i = 0; i < count; ++i)
        {
            if (touching[i].tf == false)
            {
                continue;
            }
            Body body = bodies[i];
            Body ground = sensor.getBody();
            CircleShape circle = (CircleShape) sensor.getShape();
            Vec2 center = ground.getWorldPoint(circle.p);
            Vec2 position = body.getPosition();
            Vec2 d = center.sub(position);
            if (d.lengthSquared() < Settings.EPSILON * Settings.EPSILON)
            {
                continue;
            }
            d.normalize();
            Vec2 F = d.mulLocal(100f);
            body.applyForce(F, position);
        }
    }

    @Override
    public String getTestName()
    {
        return "Sensor Test";
    }
}
