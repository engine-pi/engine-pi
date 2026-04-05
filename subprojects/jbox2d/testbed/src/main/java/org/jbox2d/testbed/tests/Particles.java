package org.jbox2d.testbed.tests;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.particle.ParticleGroupDef;
import org.jbox2d.particle.ParticleType;
import org.jbox2d.testbed.framework.TestbedTest;

/**
 * @author Daniel Murphy
 *
 * @repolink https://github.com/google/liquidfun/blob/master/liquidfun/Box2D/Testbed/Tests/Particles.h
 */
public class Particles extends TestbedTest
{
    @Override
    public String getTestName()
    {
        return "Particles";
    }

    @Override
    public void initTest(boolean deserialized)
    {
        {
            {
                PolygonShape shape = new PolygonShape();
                Vec2[] vertices = new Vec2[] { new Vec2(-40, -10),
                        new Vec2(40, -10), new Vec2(40, 0), new Vec2(-40, 0) };
                shape.set(vertices, 4);
                getGroundBody().createFixture(shape, 0.0f);
            }
            {
                PolygonShape shape = new PolygonShape();
                Vec2[] vertices = { new Vec2(-40, -1), new Vec2(-20, -1),
                        new Vec2(-20, 20), new Vec2(-40, 30) };
                shape.set(vertices, 4);
                getGroundBody().createFixture(shape, 0.0f);
            }
            {
                PolygonShape shape = new PolygonShape();
                Vec2[] vertices = { new Vec2(20, -1), new Vec2(40, -1),
                        new Vec2(40, 30), new Vec2(20, 20) };
                shape.set(vertices, 4);
                getGroundBody().createFixture(shape, 0.0f);
            }
        }
        world.setParticleRadius(0.35f);
        world.setParticleDamping(0.2f);
        {
            CircleShape shape = new CircleShape();
            shape.p.set(0, 30);
            shape.radius = 20;
            ParticleGroupDef pd = new ParticleGroupDef();
            pd.flags = ParticleType.waterParticle;
            pd.shape = shape;
            world.createParticleGroup(pd);
        }
        {
            BodyDef bd = new BodyDef();
            bd.type = BodyType.DYNAMIC;
            Body body = world.createBody(bd);
            CircleShape shape = new CircleShape();
            shape.p.set(0, 80);
            shape.radius = 5;
            body.createFixture(shape, 0.5f);
        }
    }
}
