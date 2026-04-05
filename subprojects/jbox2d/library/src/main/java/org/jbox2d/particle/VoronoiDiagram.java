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
package org.jbox2d.particle;

import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.pooling.normal.MutableStack;

/**
 * A field representing the nearest generator from each point.
 *
 * @repolink https://github.com/google/liquidfun/blob/master/liquidfun/Box2D/Box2D/Particle/b2VoronoiDiagram.h
 */
public class VoronoiDiagram
{
    /**
     * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2VoronoiDiagram.h#L62-L67
     */
    public static class Generator
    {
        final Vec2 center = new Vec2();

        int tag;
    }

    /**
     * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2VoronoiDiagram.h#L69-L82
     */
    public static class VoronoiDiagramTask
    {
        int x, y, i;

        Generator generator;

        public VoronoiDiagramTask()
        {
        }

        public VoronoiDiagramTask(int x, int y, int i, Generator g)
        {
            this.x = x;
            this.y = y;
            this.i = i;
            generator = g;
        }

        public VoronoiDiagramTask set(int x, int y, int i, Generator g)
        {
            this.x = x;
            this.y = y;
            this.i = i;
            generator = g;
            return this;
        }
    }

    public interface VoronoiDiagramCallback
    {
        void callback(int aTag, int bTag, int cTag);
    }

    private final Generator[] generatorBuffer;

    private int generatorCount;

    private int countX, countY;

    // The diagram is an array of "pointers".
    private Generator[] diagram;

    public VoronoiDiagram(int generatorCapacity)
    {
        generatorBuffer = new Generator[generatorCapacity];
        for (int i = 0; i < generatorCapacity; i++)
        {
            generatorBuffer[i] = new Generator();
        }
        generatorCount = 0;
        countX = 0;
        countY = 0;
        diagram = null;
    }

    /**
     * List all nodes that contain at least one necessary generator.
     *
     * @param callback A callback function object called for each node.
     *
     * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2VoronoiDiagram.h#L56-L58
     * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2VoronoiDiagram.cpp#L195-L221
     */
    public void getNodes(VoronoiDiagramCallback callback)
    {
        for (int y = 0; y < countY - 1; y++)
        {
            for (int x = 0; x < countX - 1; x++)
            {
                int i = x + y * countX;
                Generator a = diagram[i];
                Generator b = diagram[i + 1];
                Generator c = diagram[i + countX];
                Generator d = diagram[i + 1 + countX];
                if (b != c)
                {
                    if (a != b && a != c)
                    {
                        callback.callback(a.tag, b.tag, c.tag);
                    }
                    if (d != b && d != c)
                    {
                        callback.callback(b.tag, d.tag, c.tag);
                    }
                }
            }
        }
    }

    /**
     * Add a generator.
     *
     * @param center The position of the generator.
     * @param tag A tag used to identify the generator in callback functions.
     *
     * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2VoronoiDiagram.cpp#L45-L53
     * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2VoronoiDiagram.h#L35-L39
     */
    public void addGenerator(Vec2 center, int tag)
    {
        Generator g = generatorBuffer[generatorCount++];
        g.center.x = center.x;
        g.center.y = center.y;
        g.tag = tag;
    }

    private final Vec2 lower = new Vec2();

    private final Vec2 upper = new Vec2();

    private final MutableStack<VoronoiDiagramTask> taskPool = new MutableStack<>(
            50)
    {
        @Override
        protected VoronoiDiagramTask newInstance()
        {
            return new VoronoiDiagramTask();
        }

        @Override
        protected VoronoiDiagramTask[] newArray(int size)
        {
            return new VoronoiDiagramTask[size];
        }
    };

    private final StackQueue<VoronoiDiagramTask> queue = new StackQueue<>();

    /**
     * Generate the Voronoi diagram. It is rasterized with a given interval in
     * the same range as the necessary generators exist.
     *
     * @param radius The interval of the diagram.
     *
     * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2VoronoiDiagram.cpp#L55-L193
     * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2VoronoiDiagram.h#L41-L45
     */
    public void generate(float radius)
    {
        assert (diagram == null);
        float inverseRadius = 1 / radius;
        lower.x = Float.MAX_VALUE;
        lower.y = Float.MAX_VALUE;
        upper.x = -Float.MAX_VALUE;
        upper.y = -Float.MAX_VALUE;
        for (int k = 0; k < generatorCount; k++)
        {
            Generator g = generatorBuffer[k];
            Vec2.minToOut(lower, g.center, lower);
            Vec2.maxToOut(upper, g.center, upper);
        }
        countX = 1 + (int) (inverseRadius * (upper.x - lower.x));
        countY = 1 + (int) (inverseRadius * (upper.y - lower.y));
        diagram = new Generator[countX * countY];
        queue.reset(new VoronoiDiagramTask[4 * countX * countX]);
        for (int k = 0; k < generatorCount; k++)
        {
            Generator g = generatorBuffer[k];
            g.center.x = inverseRadius * (g.center.x - lower.x);
            g.center.y = inverseRadius * (g.center.y - lower.y);
            int x = MathUtils.max(0,
                MathUtils.min((int) g.center.x, countX - 1));
            int y = MathUtils.max(0,
                MathUtils.min((int) g.center.y, countY - 1));
            queue.push(taskPool.pop().set(x, y, x + y * countX, g));
        }
        while (!queue.empty())
        {
            VoronoiDiagramTask front = queue.pop();
            int x = front.x;
            int y = front.y;
            int i = front.i;
            Generator g = front.generator;
            if (diagram[i] == null)
            {
                diagram[i] = g;
                if (x > 0)
                {
                    queue.push(taskPool.pop().set(x - 1, y, i - 1, g));
                }
                if (y > 0)
                {
                    queue.push(taskPool.pop().set(x, y - 1, i - countX, g));
                }
                if (x < countX - 1)
                {
                    queue.push(taskPool.pop().set(x + 1, y, i + 1, g));
                }
                if (y < countY - 1)
                {
                    queue.push(taskPool.pop().set(x, y + 1, i + countX, g));
                }
            }
            taskPool.push(front);
        }
        int maxIteration = countX + countY;
        for (int iteration = 0; iteration < maxIteration; iteration++)
        {
            for (int y = 0; y < countY; y++)
            {
                for (int x = 0; x < countX - 1; x++)
                {
                    int i = x + y * countX;
                    Generator a = diagram[i];
                    Generator b = diagram[i + 1];
                    if (a != b)
                    {
                        queue.push(taskPool.pop().set(x, y, i, b));
                        queue.push(taskPool.pop().set(x + 1, y, i + 1, a));
                    }
                }
            }
            for (int y = 0; y < countY - 1; y++)
            {
                for (int x = 0; x < countX; x++)
                {
                    int i = x + y * countX;
                    Generator a = diagram[i];
                    Generator b = diagram[i + countX];
                    if (a != b)
                    {
                        queue.push(taskPool.pop().set(x, y, i, b));
                        queue.push(taskPool.pop().set(x, y + 1, i + countX, a));
                    }
                }
            }
            boolean updated = false;
            while (!queue.empty())
            {
                VoronoiDiagramTask front = queue.pop();
                int x = front.x;
                int y = front.y;
                int i = front.i;
                Generator k = front.generator;
                Generator a = diagram[i];
                if (a != k)
                {
                    float ax = a.center.x - x;
                    float ay = a.center.y - y;
                    float bx = k.center.x - x;
                    float by = k.center.y - y;
                    float a2 = ax * ax + ay * ay;
                    float b2 = bx * bx + by * by;
                    if (a2 > b2)
                    {
                        diagram[i] = k;
                        if (x > 0)
                        {
                            queue.push(taskPool.pop().set(x - 1, y, i - 1, k));
                        }
                        if (y > 0)
                        {
                            queue.push(
                                taskPool.pop().set(x, y - 1, i - countX, k));
                        }
                        if (x < countX - 1)
                        {
                            queue.push(taskPool.pop().set(x + 1, y, i + 1, k));
                        }
                        if (y < countY - 1)
                        {
                            queue.push(
                                taskPool.pop().set(x, y + 1, i + countX, k));
                        }
                        updated = true;
                    }
                }
                taskPool.push(front);
            }
            if (!updated)
            {
                break;
            }
        }
    }
}
