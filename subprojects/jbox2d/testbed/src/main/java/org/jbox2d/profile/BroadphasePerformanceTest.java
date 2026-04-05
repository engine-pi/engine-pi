package org.jbox2d.profile;

import org.jbox2d.collision.broadphase.DynamicTreeFlatNodes;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.jbox2d.pooling.WorldPool;
import org.jbox2d.pooling.normal.DefaultWorldPool;
import org.jbox2d.profile.worlds.PerformanceTestWorld;
import org.jbox2d.profile.worlds.PistonWorld;

public class BroadphasePerformanceTest extends BasicPerformanceTest
{
    private static final int NUM_TESTS = 2;

    private final PerformanceTestWorld world;

    public BroadphasePerformanceTest(int iterations, PerformanceTestWorld world)
    {
        super(NUM_TESTS, iterations, 1000);
        this.world = world;
        setFormat(ResultFormat.MILLISECONDS);
    }

    public static void main(String[] args)
    {
        BroadphasePerformanceTest benchmark = new BroadphasePerformanceTest(10,
                new PistonWorld());
        benchmark.go();
    }

    public void setupTest(int testNum)
    {
        World w;
        WorldPool pool = new DefaultWorldPool(50, 50);
        if (testNum == 0)
        {
            w = new World(new Vec2(0.0f, -10.0f), pool);
        }
        else
        {
            w = new World(new Vec2(0, -10), pool, new DynamicTreeFlatNodes());
        }
        world.setupWorld(w);
    }

    @Override
    public void step(int testNum)
    {
        world.step();
    }

    @Override
    public String getTestName(int testNum)
    {
        return switch (testNum)
        {
        case 0 -> "Normal";
        case 1 -> "Flat";
        default -> "";
        };
    }
}
