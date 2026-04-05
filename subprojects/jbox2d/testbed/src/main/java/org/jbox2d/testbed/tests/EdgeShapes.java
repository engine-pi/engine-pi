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

import org.jbox2d.callbacks.RayCastCallback;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Color3f;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.testbed.framework.TestbedSettings;
import org.jbox2d.testbed.framework.TestbedTest;

/**
 * @author Daniel Murphy
 *
 * @repolink https://github.com/google/liquidfun/blob/master/liquidfun/Box2D/Testbed/Tests/EdgeShapes.h
 */
public class EdgeShapes extends TestbedTest
{
    int maxBodies = 256;

    int bodyIndex;

    Body bodies[] = new Body[maxBodies];

    PolygonShape polygons[] = new PolygonShape[4];

    CircleShape circle;

    float angle;

    @Override
    public void initTest(boolean argDeserialized)
    {
        for (int i = 0; i < bodies.length; i++)
        {
            bodies[i] = null;
        }
        // Ground body
        {
            BodyDef bd = new BodyDef();
            Body ground = getWorld().createBody(bd);
            float x1 = -20.0f;
            float y1 = 2.0f * MathUtils.cos(x1 / 10.0f * MathUtils.PI);
            for (int i = 0; i < 80; ++i)
            {
                float x2 = x1 + 0.5f;
                float y2 = 2.0f * MathUtils.cos(x2 / 10.0f * MathUtils.PI);
                EdgeShape shape = new EdgeShape();
                shape.set(new Vec2(x1, y1), new Vec2(x2, y2));
                ground.createFixture(shape, 0.0f);
                x1 = x2;
                y1 = y2;
            }
        }
        {
            Vec2 vertices[] = new Vec2[3];
            vertices[0] = new Vec2(-0.5f, 0.0f);
            vertices[1] = new Vec2(0.5f, 0.0f);
            vertices[2] = new Vec2(0.0f, 1.5f);
            polygons[0] = new PolygonShape();
            polygons[0].set(vertices, 3);
        }
        {
            Vec2 vertices[] = new Vec2[3];
            vertices[0] = new Vec2(-0.1f, 0.0f);
            vertices[1] = new Vec2(0.1f, 0.0f);
            vertices[2] = new Vec2(0.0f, 1.5f);
            polygons[1] = new PolygonShape();
            polygons[1].set(vertices, 3);
        }
        {
            float w = 1.0f;
            float b = w / (2.0f + MathUtils.sqrt(2.0f));
            float s = MathUtils.sqrt(2.0f) * b;
            Vec2 vertices[] = new Vec2[8];
            vertices[0] = new Vec2(0.5f * s, 0.0f);
            vertices[1] = new Vec2(0.5f * w, b);
            vertices[2] = new Vec2(0.5f * w, b + s);
            vertices[3] = new Vec2(0.5f * s, w);
            vertices[4] = new Vec2(-0.5f * s, w);
            vertices[5] = new Vec2(-0.5f * w, b + s);
            vertices[6] = new Vec2(-0.5f * w, b);
            vertices[7] = new Vec2(-0.5f * s, 0.0f);
            polygons[2] = new PolygonShape();
            polygons[2].set(vertices, 8);
        }
        {
            polygons[3] = new PolygonShape();
            polygons[3].setAsBox(0.5f, 0.5f);
        }
        {
            circle = new CircleShape();
            circle.radius = 0.5f;
        }
        bodyIndex = 0;
        angle = 0.0f;
    }

    void Create(int index)
    {
        if (bodies[bodyIndex] != null)
        {
            getWorld().destroyBody(bodies[bodyIndex]);
            bodies[bodyIndex] = null;
        }
        BodyDef bd = new BodyDef();
        float x = MathUtils.randomFloat(-10.0f, 10.0f);
        float y = MathUtils.randomFloat(10.0f, 20.0f);
        bd.position.set(x, y);
        bd.angle = MathUtils.randomFloat(-MathUtils.PI, MathUtils.PI);
        bd.type = BodyType.DYNAMIC;
        if (index == 4)
        {
            bd.angularDamping = 0.02f;
        }
        bodies[bodyIndex] = getWorld().createBody(bd);
        if (index < 4)
        {
            FixtureDef fd = new FixtureDef();
            fd.shape = polygons[index];
            fd.friction = 0.3f;
            fd.density = 20.0f;
            bodies[bodyIndex].createFixture(fd);
        }
        else
        {
            FixtureDef fd = new FixtureDef();
            fd.shape = circle;
            fd.friction = 0.3f;
            fd.density = 20.0f;
            bodies[bodyIndex].createFixture(fd);
        }
        bodyIndex = (bodyIndex + 1) % maxBodies;
    }

    void DestroyBody()
    {
        for (int i = 0; i < maxBodies; ++i)
        {
            if (bodies[i] != null)
            {
                getWorld().destroyBody(bodies[i]);
                bodies[i] = null;
                return;
            }
        }
    }

    @Override
    public void keyPressed(char argKeyChar, int argKeyCode)
    {
        switch (argKeyChar)
        {
        case '1':
        case '2':
        case '3':
        case '4':
        case '5':
            Create(argKeyChar - '1');
            break;

        case 'd':
            DestroyBody();
            break;
        }
    }

    EdgeShapesCallback callback = new EdgeShapesCallback();

    @Override
    public void step(TestbedSettings settings)
    {
        boolean advanceRay = settings.pause == false || settings.singleStep;
        super.step(settings);
        addTextLine("Press 1-5 to drop stuff");
        float L = 25.0f;
        Vec2 point1 = new Vec2(0.0f, 10.0f);
        Vec2 d = new Vec2(L * MathUtils.cos(angle),
                -L * MathUtils.abs(MathUtils.sin(angle)));
        Vec2 point2 = point1.add(d);
        callback.fixture = null;
        getWorld().raycast(callback, point1, point2);
        if (callback.fixture != null)
        {
            getDebugDraw().drawPoint(callback.point, 5.0f,
                    new Color3f(0.4f, 0.9f, 0.4f));
            getDebugDraw().drawSegment(point1, callback.point,
                    new Color3f(0.8f, 0.8f, 0.8f));
            Vec2 head = callback.normal.mul(.5f).addLocal(callback.point);
            getDebugDraw().drawSegment(callback.point, head,
                    new Color3f(0.9f, 0.9f, 0.4f));
        }
        else
        {
            getDebugDraw().drawSegment(point1, point2,
                    new Color3f(0.8f, 0.8f, 0.8f));
        }
        if (advanceRay)
        {
            angle += 0.25f * MathUtils.PI / 180.0f;
        }
    }

    @Override
    public String getTestName()
    {
        return "Edge Shapes";
    }
}

class EdgeShapesCallback implements RayCastCallback
{
    EdgeShapesCallback()
    {
        fixture = null;
    }

    public float reportFixture(Fixture fixture, final Vec2 point,
            final Vec2 normal, float fraction)
    {
        this.fixture = fixture;
        this.point = point;
        this.normal = normal;
        return fraction;
    }

    Fixture fixture;

    Vec2 point;

    Vec2 normal;
};
