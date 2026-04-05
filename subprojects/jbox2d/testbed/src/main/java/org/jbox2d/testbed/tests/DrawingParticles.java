package org.jbox2d.testbed.tests;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.particle.ParticleColor;
import org.jbox2d.particle.ParticleGroup;
import org.jbox2d.particle.ParticleGroupDef;
import org.jbox2d.particle.ParticleGroupType;
import org.jbox2d.particle.ParticleType;
import org.jbox2d.testbed.framework.TestbedSettings;
import org.jbox2d.testbed.framework.TestbedTest;

/**
 * @author Daniel Murphy
 *
 * @repolink https://github.com/google/liquidfun/blob/master/liquidfun/Box2D/Testbed/Tests/DrawingParticles.h
 */
public class DrawingParticles extends TestbedTest
{
    ParticleGroup lastGroup;

    boolean drawing;

    int particleFlags;

    int groupFlags;

    ParticleColor color = new ParticleColor();

    @Override
    public void initTest(boolean deserialized)
    {
        {
            {
                PolygonShape shape = new PolygonShape();
                Vec2[] vertices = new Vec2[] { new Vec2(-40, -20),
                        new Vec2(40, -20), new Vec2(40, 0), new Vec2(-40, 0) };
                shape.set(vertices, 4);
                getGroundBody().createFixture(shape, 0.0f);
            }
            {
                PolygonShape shape = new PolygonShape();
                Vec2[] vertices = new Vec2[] { new Vec2(-40, -20),
                        new Vec2(-20, -20), new Vec2(-20, 60),
                        new Vec2(-40, 60) };
                shape.set(vertices, 4);
                getGroundBody().createFixture(shape, 0.0f);
            }
            {
                PolygonShape shape = new PolygonShape();
                Vec2[] vertices = new Vec2[] { new Vec2(20, -20),
                        new Vec2(40, -20), new Vec2(40, 60), new Vec2(20, 60) };
                shape.set(vertices, 4);
                getGroundBody().createFixture(shape, 0.0f);
            }
            {
                PolygonShape shape = new PolygonShape();
                Vec2[] vertices = new Vec2[] { new Vec2(-40, 40),
                        new Vec2(40, 40), new Vec2(40, 60), new Vec2(-40, 60) };
                shape.set(vertices, 4);
                getGroundBody().createFixture(shape, 0.0f);
            }
        }
        world.setParticleRadius(0.5f);
        lastGroup = null;
        drawing = true;
        groupFlags = 0;
    }

    @Override
    public void step(TestbedSettings settings)
    {
        super.step(settings);
        addTextLine("Keys: (L) liquid, (E) elastic, (S) spring");
        addTextLine("(F) rigid, (W) wall, (V) viscous, (T) tensile");
        addTextLine("(O) powder (Z) erase, (X) move");
    }

    public void keyPressed(char keyChar, int keyCode)
    {
        drawing = keyChar != 'x';
        particleFlags = 0;
        groupFlags = 0;
        color.set((byte) 127, (byte) 127, (byte) 127, (byte) 50);
        switch (keyChar)
        {
        case 'e':
            particleFlags = ParticleType.elasticParticle;
            groupFlags = ParticleGroupType.solidParticleGroup;
            break;

        case 'o':
            particleFlags = ParticleType.powderParticle;
            break;

        case 'f':
            groupFlags = ParticleGroupType.rigidParticleGroup
                    | ParticleGroupType.solidParticleGroup;
            break;

        case 's':
            particleFlags = ParticleType.springParticle;
            groupFlags = ParticleGroupType.solidParticleGroup;
            break;

        case 't':
            color.set((byte) 0, (byte) 127, (byte) 0, (byte) 50);
            particleFlags = ParticleType.tensileParticle;
            break;

        case 'v':
            color.set((byte) 0, (byte) 0, (byte) 127, (byte) 50);
            particleFlags = ParticleType.viscousParticle;
            break;

        case 'w':
            particleFlags = ParticleType.wallParticle;
            groupFlags = ParticleGroupType.solidParticleGroup;
            break;

        case 'z':
            particleFlags = ParticleType.zombieParticle;
            break;
        }
    }

    Transform pxf = new Transform();

    CircleShape pshape = new CircleShape();

    ParticleGroupDef ppd = new ParticleGroupDef();

    @Override
    public void mouseDrag(Vec2 p, int button)
    {
        super.mouseDrag(p, button);
        if (drawing)
        {
            pshape.p.set(p);
            pshape.radius = 2.0f;
            pxf.setIdentity();
            world.destroyParticlesInShape(pshape, pxf);
            ppd.shape = pshape;
            ppd.color = color;
            ppd.flags = particleFlags;
            ppd.groupFlags = groupFlags;
            ParticleGroup group = world.createParticleGroup(ppd);
            if (lastGroup != null
                    && group.getGroupFlags() == lastGroup.getGroupFlags())
            {
                world.joinParticleGroups(lastGroup, group);
            }
            else
            {
                lastGroup = group;
            }
            mouseTracing = false;
        }
    }

    @Override
    public void mouseUp(Vec2 p, int button)
    {
        super.mouseUp(p, button);
        lastGroup = null;
    }

    @Override
    public void particleGroupDestroyed(ParticleGroup group)
    {
        super.particleGroupDestroyed(group);
        if (group == lastGroup)
        {
            lastGroup = null;
        }
    }

    @Override
    public String getTestName()
    {
        return "Drawing Particles";
    }
}
