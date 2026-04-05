package org.jbox2d.testbed.tests;

import org.jbox2d.collision.Distance;
import org.jbox2d.collision.TimeOfImpact;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.testbed.framework.TestbedSettings;
import org.jbox2d.testbed.framework.TestbedTest;

/**
 * @author Daniel Murphy
 *
 * @repolink https://github.com/google/liquidfun/blob/master/liquidfun/Box2D/Testbed/Tests/BulletTest.h
 */
public class BulletTest extends TestbedTest
{
    Body body;

    Body bullet;

    float x;

    @Override
    public Vec2 getDefaultCameraPos()
    {
        return new Vec2(0, 6);
    }

    @Override
    public float getDefaultCameraScale()
    {
        return 40;
    }

    @Override
    public void initTest(boolean deserialized)
    {
        {
            BodyDef bd = new BodyDef();
            bd.position.set(0.0f, 0.0f);
            Body body = world.createBody(bd);
            EdgeShape edge = new EdgeShape();
            edge.set(new Vec2(-10.0f, 0.0f), new Vec2(10.0f, 0.0f));
            body.createFixture(edge, 0.0f);
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(0.2f, 1.0f, new Vec2(0.5f, 1.0f), 0.0f);
            body.createFixture(shape, 0.0f);
        }
        {
            BodyDef bd = new BodyDef();
            bd.type = BodyType.DYNAMIC;
            bd.position.set(0.0f, 4.0f);
            PolygonShape box = new PolygonShape();
            box.setAsBox(2.0f, 0.1f);
            body = world.createBody(bd);
            body.createFixture(box, 1.0f);
            box.setAsBox(0.25f, 0.25f);
            // x = RandomFloat(-1.0f, 1.0f);
            x = -0.06530577f;
            bd.position.set(x, 10.0f);
            bd.bullet = true;
            bullet = world.createBody(bd);
            bullet.createFixture(box, 100.0f);
            bullet.setLinearVelocity(new Vec2(0.0f, -50.0f));
        }
    }

    public void launch()
    {
        body.setTransform(new Vec2(0.0f, 4.0f), 0.0f);
        body.setLinearVelocity(new Vec2());
        body.setAngularVelocity(0.0f);
        x = MathUtils.randomFloat(-1.0f, 1.0f);
        bullet.setTransform(new Vec2(x, 10.0f), 0.0f);
        bullet.setLinearVelocity(new Vec2(0.0f, -50.0f));
        bullet.setAngularVelocity(0.0f);
        Distance.GJK_CALLS = 0;
        Distance.GJK_ITERS = 0;
        Distance.GJK_MAX_ITERS = 0;
        TimeOfImpact.toiCalls = 0;
        TimeOfImpact.toiIters = 0;
        TimeOfImpact.toiMaxIters = 0;
        TimeOfImpact.toiRootIters = 0;
        TimeOfImpact.toiMaxRootIters = 0;
    }

    @Override
    public void step(TestbedSettings settings)
    {
        super.step(settings);
        if (Distance.GJK_CALLS > 0)
        {
            addTextLine(String.format(
                    "gjk calls = %d, ave gjk iters = %3.1f, max gjk iters = %d",
                    Distance.GJK_CALLS,
                    Distance.GJK_ITERS * 1.0 / (Distance.GJK_CALLS),
                    Distance.GJK_MAX_ITERS));
        }
        if (TimeOfImpact.toiCalls > 0)
        {
            addTextLine(String.format(
                    "toi calls = %d, ave toi iters = %3.1f, max toi iters = %d",
                    TimeOfImpact.toiCalls,
                    TimeOfImpact.toiIters * 1f / (TimeOfImpact.toiCalls),
                    TimeOfImpact.toiMaxRootIters));
            addTextLine(String.format(
                    "ave toi root iters = %3.1f, max toi root iters = %d",
                    TimeOfImpact.toiRootIters * 1f / (TimeOfImpact.toiCalls),
                    TimeOfImpact.toiMaxRootIters));
        }
        if (getStepCount() % 60 == 0)
        {
            launch();
        }
    }

    @Override
    public String getTestName()
    {
        return "Bullet Test";
    }
}
