package org.jbox2d.testbed.tests;

import org.jbox2d.collision.shapes.ChainShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.particle.ParticleGroupDef;
import org.jbox2d.testbed.framework.TestbedTest;

/**
 * @author Daniel Murphy
 *
 * @repolink https://github.com/google/liquidfun/blob/master/liquidfun/Box2D/Testbed/Tests/DamBreak.h
 */
public class DamBreak extends TestbedTest
{
    @Override
    public void initTest(boolean deserialized)
    {
        {
            BodyDef bd = new BodyDef();
            Body ground = world.createBody(bd);
            ChainShape shape = new ChainShape();
            Vec2[] vertices = new Vec2[] { new Vec2(-20, 0), new Vec2(20, 0),
                    new Vec2(20, 40), new Vec2(-20, 40) };
            shape.createLoop(vertices, 4);
            ground.createFixture(shape, 0.0f);
        }
        world.setParticleRadius(0.15f);
        world.setParticleDamping(0.2f);
        {
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(8, 10, new Vec2(-12, 10.1f), 0);
            ParticleGroupDef pd = new ParticleGroupDef();
            pd.shape = shape;
            world.createParticleGroup(pd);
        }
    }

    @Override
    public String getTestName()
    {
        return "Dam Break";
    }
}
