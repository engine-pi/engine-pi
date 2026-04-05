package org.jbox2d.profile;

import org.jbox2d.common.Settings;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.jbox2d.profile.worlds.PerformanceTestWorld;
import org.jbox2d.profile.worlds.PistonWorld;

public class SettingsPerformanceTest extends BasicPerformanceTest
{
    private static final int NUM_TESTS = 14;

    private final PerformanceTestWorld world;

    public SettingsPerformanceTest(int iters, PerformanceTestWorld world)
    {
        super(NUM_TESTS, iters, 300);
        this.world = world;
    }

    public static void main(String[] args)
    {
        SettingsPerformanceTest benchmark = new SettingsPerformanceTest(10,
                new PistonWorld());
        benchmark.go();
    }

    @Override
    public void setupTest(int testNum)
    {
        World w = new World(new Vec2(0, -10));
        world.setupWorld(w);
    }

    @Override
    public void preStep(int testNum)
    {
        Settings.FAST_ABS = testNum == 1;
        Settings.FAST_ATAN2 = testNum == 2;
        Settings.FAST_CEIL = testNum == 3;
        Settings.FAST_FLOOR = testNum == 4;
        Settings.FAST_ROUND = testNum == 5;
        Settings.SINCOS_LUT_ENABLED = testNum == 6;
        if (testNum == 7)
        {
            Settings.FAST_ABS = true;
            Settings.FAST_ATAN2 = true;
            Settings.FAST_CEIL = true;
            Settings.FAST_FLOOR = true;
            Settings.FAST_ROUND = true;
            Settings.SINCOS_LUT_ENABLED = true;
        }
        if (testNum > 7)
        {
            Settings.FAST_ABS = testNum != 8;
            Settings.FAST_ATAN2 = testNum != 9;
            Settings.FAST_CEIL = testNum != 10;
            Settings.FAST_FLOOR = testNum != 11;
            Settings.FAST_ROUND = testNum != 12;
            Settings.SINCOS_LUT_ENABLED = testNum != 13;
        }
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
        case 0 -> "No optimizations";
        case 1 -> "Fast abs";
        case 2 -> "Fast atan2";
        case 3 -> "Fast ceil";
        case 4 -> "Fast floor";
        case 5 -> "Fast round";
        case 6 -> "Sincos lookup table";
        case 7 -> "All optimizations on";
        case 8 -> "no Fast abs";
        case 9 -> "no Fast atan2";
        case 10 -> "no Fast ceil";
        case 11 -> "no Fast floor";
        case 12 -> "no Fast round";
        case 13 -> "no Sincos lookup";
        default -> "";
        };
    }
}
