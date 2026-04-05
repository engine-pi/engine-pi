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
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.testbed.framework.TestbedSettings;
import org.jbox2d.testbed.framework.TestbedTest;

/**
 * @author Daniel Murphy
 *
 * @repolink https://github.com/google/liquidfun/blob/master/liquidfun/Box2D/Testbed/Tests/RayCast.h
 */
public class RayCastTest extends TestbedTest
{
    public static final int maxBodies = 256;

    enum Mode
    {
        closest, any, multiple
    };

    int bodyIndex;

    Body[] bodies;

    Integer[] userData;

    PolygonShape[] polygons;

    CircleShape circle;

    EdgeShape edge;

    float angle;

    Mode mode;

    @Override
    public String getTestName()
    {
        return "Raycast";
    }

    @Override
    public void initTest(boolean deserialized)
    {
        bodies = new Body[maxBodies];
        userData = new Integer[maxBodies];
        polygons = new PolygonShape[4];
        {
            BodyDef bd = new BodyDef();
            Body ground = getWorld().createBody(bd);
            EdgeShape shape = new EdgeShape();
            shape.set(new Vec2(-40.0f, 0.0f), new Vec2(40.0f, 0.0f));
            ground.createFixture(shape, 0.0f);
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
        {
            edge = new EdgeShape();
            edge.set(new Vec2(-1.0f, 0.0f), new Vec2(1.0f, 0.0f));
        }
        bodyIndex = 0;
        angle = 0.0f;
        mode = Mode.closest;
    }

    RayCastClosestCallback ccallback = new RayCastClosestCallback();

    RayCastAnyCallback acallback = new RayCastAnyCallback();

    RayCastMultipleCallback mcallback = new RayCastMultipleCallback();

    // pooling
    Vec2 point1 = new Vec2();

    Vec2 d = new Vec2();

    Vec2 pooledHead = new Vec2();

    Vec2 point2 = new Vec2();

    @Override
    public void step(TestbedSettings settings)
    {
        boolean advanceRay = settings.pause == false || settings.singleStep;
        super.step(settings);
        addTextLine("Press 1-6 to drop stuff, m to change the mode");
        addTextLine("Polygon 1 is filtered");
        addTextLine("Mode = " + mode);
        float L = 11.0f;
        point1.set(0.0f, 10.0f);
        d.set(L * MathUtils.cos(angle), L * MathUtils.sin(angle));
        point2.set(point1);
        point2.addLocal(d);
        if (mode == Mode.closest)
        {
            ccallback.init();
            getWorld().raycast(ccallback, point1, point2);
            if (ccallback.hit)
            {
                getDebugDraw().drawPoint(ccallback.point, 5.0f,
                        new Color3f(0.4f, 0.9f, 0.4f));
                getDebugDraw().drawSegment(point1, ccallback.point,
                        new Color3f(0.8f, 0.8f, 0.8f));
                pooledHead.set(ccallback.normal);
                pooledHead.mulLocal(.5f).addLocal(ccallback.point);
                getDebugDraw().drawSegment(ccallback.point, pooledHead,
                        new Color3f(0.9f, 0.9f, 0.4f));
            }
            else
            {
                getDebugDraw().drawSegment(point1, point2,
                        new Color3f(0.8f, 0.8f, 0.8f));
            }
        }
        else if (mode == Mode.any)
        {
            acallback.init();
            getWorld().raycast(acallback, point1, point2);
            if (acallback.hit)
            {
                getDebugDraw().drawPoint(acallback.point, 5.0f,
                        new Color3f(0.4f, 0.9f, 0.4f));
                getDebugDraw().drawSegment(point1, acallback.point,
                        new Color3f(0.8f, 0.8f, 0.8f));
                pooledHead.set(acallback.normal);
                pooledHead.mulLocal(.5f).addLocal(acallback.point);
                getDebugDraw().drawSegment(acallback.point, pooledHead,
                        new Color3f(0.9f, 0.9f, 0.4f));
            }
            else
            {
                getDebugDraw().drawSegment(point1, point2,
                        new Color3f(0.8f, 0.8f, 0.8f));
            }
        }
        else if (mode == Mode.multiple)
        {
            mcallback.init();
            getWorld().raycast(mcallback, point1, point2);
            getDebugDraw().drawSegment(point1, point2,
                    new Color3f(0.8f, 0.8f, 0.8f));
            for (int i = 0; i < mcallback.count; ++i)
            {
                Vec2 p = mcallback.points[i];
                Vec2 n = mcallback.normals[i];
                getDebugDraw().drawPoint(p, 5.0f,
                        new Color3f(0.4f, 0.9f, 0.4f));
                getDebugDraw().drawSegment(point1, p,
                        new Color3f(0.8f, 0.8f, 0.8f));
                pooledHead.set(n);
                pooledHead.mulLocal(.5f).addLocal(p);
                getDebugDraw().drawSegment(p, pooledHead,
                        new Color3f(0.9f, 0.9f, 0.4f));
            }
        }
        if (advanceRay)
        {
            angle += 0.25f * MathUtils.PI / 180.0f;
        }
    }

    void Create(int index)
    {
        if (bodies[bodyIndex] != null)
        {
            getWorld().destroyBody(bodies[bodyIndex]);
            bodies[bodyIndex] = null;
        }
        BodyDef bd = new BodyDef();
        float x = (float) Math.random() * 20 - 10;
        float y = (float) Math.random() * 20;
        bd.position.set(x, y);
        bd.angle = (float) Math.random() * MathUtils.TWOPI - MathUtils.PI;
        userData[bodyIndex] = index;
        bd.userData = userData[bodyIndex];
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
            bodies[bodyIndex].createFixture(fd);
        }
        else if (index < 5)
        {
            FixtureDef fd = new FixtureDef();
            fd.shape = circle;
            fd.friction = 0.3f;
            bodies[bodyIndex].createFixture(fd);
        }
        else
        {
            FixtureDef fd = new FixtureDef();
            fd.shape = edge;
            fd.friction = 0.3f;
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
        case '6':
            Create(argKeyChar - '1');
            break;

        case 'd':
            DestroyBody();
            break;

        case 'm':
            if (mode == Mode.closest)
            {
                mode = Mode.any;
            }
            else if (mode == Mode.any)
            {
                mode = Mode.multiple;
            }
            else if (mode == Mode.multiple)
            {
                mode = Mode.closest;
            }
            break;
        }
    }
}
// This test demonstrates how to use the world ray-cast feature.
// NOTE: we are intentionally filtering one of the polygons, therefore
// the ray will always miss one type of polygon.

// This callback finds the closest hit. Polygon 0 is filtered.
class RayCastClosestCallback implements RayCastCallback
{
    boolean hit;

    Vec2 point;

    Vec2 normal;

    public void init()
    {
        hit = false;
    }

    public float reportFixture(Fixture fixture, Vec2 point, Vec2 normal,
            float fraction)
    {
        Body body = fixture.getBody();
        Object userData = body.getUserData();
        if (userData != null)
        {
            int index = (Integer) userData;
            if (index == 0)
            {
                // filter
                return -1f;
            }
        }
        hit = true;
        this.point = point;
        this.normal = normal;
        return fraction;
    }
};

// This callback finds any hit. Polygon 0 is filtered.
class RayCastAnyCallback implements RayCastCallback
{
    public void init()
    {
        hit = false;
    }

    public float reportFixture(Fixture fixture, Vec2 point, Vec2 normal,
            float fraction)
    {
        Body body = fixture.getBody();
        Object userData = body.getUserData();
        if (userData != null)
        {
            int index = (Integer) userData;
            if (index == 0)
            {
                // filter
                return -1f;
            }
        }
        hit = true;
        this.point = point;
        this.normal = normal;
        return 0f;
    }

    boolean hit;

    Vec2 point;

    Vec2 normal;
};

// This ray cast collects multiple hits along the ray. Polygon 0 is filtered.
class RayCastMultipleCallback implements RayCastCallback
{
    public int maxCount = 30;

    Vec2 points[] = new Vec2[maxCount];

    Vec2 normals[] = new Vec2[maxCount];

    int count;

    public void init()
    {
        for (int i = 0; i < maxCount; i++)
        {
            points[i] = new Vec2();
            normals[i] = new Vec2();
        }
        count = 0;
    }

    public float reportFixture(Fixture fixture, Vec2 point, Vec2 normal,
            float fraction)
    {
        Body body = fixture.getBody();
        int index = 0;
        Object userData = body.getUserData();
        if (userData != null)
        {
            index = (Integer) userData;
            if (index == 0)
            {
                // filter
                return -1f;
            }
        }
        assert (count < maxCount);
        points[count].set(point);
        normals[count].set(normal);
        ++count;
        if (count == maxCount)
        {
            return 0f;
        }
        return 1f;
    }
};
