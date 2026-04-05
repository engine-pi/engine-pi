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
package org.jbox2d.collision.shapes;

import org.jbox2d.collision.AABB;
import org.jbox2d.collision.RayCastInput;
import org.jbox2d.collision.RayCastOutput;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Rot;
import org.jbox2d.common.Settings;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;

/**
 * A chain shape is a free form sequence of line segments. The chain has
 * two-sided collision, so you can use inside and outside collision. Therefore,
 * you may use any winding order. Connectivity information is used to create
 * smooth collisions. WARNING: The chain will not collide properly if there are
 * self-intersections.
 *
 * @repolink https://github.com/erincatto/box2d/blob/main/src/collision/b2_chain_shape.cpp
 *
 * @author Daniel Murphy
 */
public class ChainShape extends Shape
{
    public Vec2[] vertices;

    public int count;

    public final Vec2 prevVertex = new Vec2(), nextVertex = new Vec2();

    public boolean hasPrevVertex = false, hasNextVertex = false;

    private final EdgeShape pool0 = new EdgeShape();

    public ChainShape()
    {
        super(ShapeType.CHAIN);
        vertices = null;
        radius = Settings.polygonRadius;
        count = 0;
    }

    public void clear()
    {
        vertices = null;
        count = 0;
    }

    @Override
    public int getChildCount()
    {
        return count - 1;
    }

    /**
     * Get a child edge.
     */
    public void getChildEdge(EdgeShape edge, int index)
    {
        assert (0 <= index && index < count - 1);
        edge.radius = radius;
        final Vec2 v0 = vertices[index];
        final Vec2 v1 = vertices[index + 1];
        edge.vertex1.x = v0.x;
        edge.vertex1.y = v0.y;
        edge.vertex2.x = v1.x;
        edge.vertex2.y = v1.y;
        if (index > 0)
        {
            Vec2 v = vertices[index - 1];
            edge.vertex0.x = v.x;
            edge.vertex0.y = v.y;
            edge.hasVertex0 = true;
        }
        else
        {
            edge.vertex0.x = prevVertex.x;
            edge.vertex0.y = prevVertex.y;
            edge.hasVertex0 = hasPrevVertex;
        }
        if (index < count - 2)
        {
            Vec2 v = vertices[index + 2];
            edge.vertex3.x = v.x;
            edge.vertex3.y = v.y;
            edge.hasVertex3 = true;
        }
        else
        {
            edge.vertex3.x = nextVertex.x;
            edge.vertex3.y = nextVertex.y;
            edge.hasVertex3 = hasNextVertex;
        }
    }

    @Override
    public float computeDistanceToOut(Transform xf, Vec2 p, int childIndex,
            Vec2 normalOut)
    {
        final EdgeShape edge = pool0;
        getChildEdge(edge, childIndex);
        return edge.computeDistanceToOut(xf, p, 0, normalOut);
    }

    @Override
    public boolean testPoint(Transform xf, Vec2 p)
    {
        return false;
    }

    @Override
    public boolean raycast(RayCastOutput output, RayCastInput input,
            Transform xf, int childIndex)
    {
        assert (childIndex < count);
        final EdgeShape edgeShape = pool0;
        int i2 = childIndex + 1;
        if (i2 == count)
        {
            i2 = 0;
        }
        Vec2 v = vertices[childIndex];
        edgeShape.vertex1.x = v.x;
        edgeShape.vertex1.y = v.y;
        Vec2 v1 = vertices[i2];
        edgeShape.vertex2.x = v1.x;
        edgeShape.vertex2.y = v1.y;
        return edgeShape.raycast(output, input, xf, 0);
    }

    @Override
    public void computeAABB(AABB aabb, Transform xf, int childIndex)
    {
        assert (childIndex < count);
        final Vec2 lower = aabb.lowerBound;
        final Vec2 upper = aabb.upperBound;
        int i2 = childIndex + 1;
        if (i2 == count)
        {
            i2 = 0;
        }
        final Vec2 vi1 = vertices[childIndex];
        final Vec2 vi2 = vertices[i2];
        final Rot xfq = xf.q;
        final Vec2 xfp = xf.p;
        float v1x = (xfq.c * vi1.x - xfq.s * vi1.y) + xfp.x;
        float v1y = (xfq.s * vi1.x + xfq.c * vi1.y) + xfp.y;
        float v2x = (xfq.c * vi2.x - xfq.s * vi2.y) + xfp.x;
        float v2y = (xfq.s * vi2.x + xfq.c * vi2.y) + xfp.y;
        lower.x = Math.min(v1x, v2x);
        lower.y = Math.min(v1y, v2y);
        upper.x = Math.max(v1x, v2x);
        upper.y = Math.max(v1y, v2y);
    }

    @Override
    public void computeMass(MassData massData, float density)
    {
        massData.mass = 0.0f;
        massData.center.setZero();
        massData.I = 0.0f;
    }

    @Override
    public Shape clone()
    {
        ChainShape clone = new ChainShape();
        clone.createChain(vertices, count);
        clone.prevVertex.set(prevVertex);
        clone.nextVertex.set(nextVertex);
        clone.hasPrevVertex = hasPrevVertex;
        clone.hasNextVertex = hasNextVertex;
        return clone;
    }

    /**
     * Create a loop. This automatically adjusts connectivity.
     *
     * @param vertices An array of vertices, these are copied.
     * @param count The vertex count.
     */
    public void createLoop(final Vec2[] vertices, int count)
    {
        assert (this.vertices == null && this.count == 0);
        assert (count >= 3);
        this.count = count + 1;
        this.vertices = new Vec2[this.count];
        for (int i = 1; i < count; i++)
        {
            Vec2 v1 = vertices[i - 1];
            Vec2 v2 = vertices[i];
            // If the code crashes here, it means your vertices are too close
            // together.
            if (MathUtils.distanceSquared(v1, v2) < Settings.linearSlop
                    * Settings.linearSlop)
            {
                throw new RuntimeException(
                        "Vertices of chain shape are too close together");
            }
        }
        for (int i = 0; i < count; i++)
        {
            this.vertices[i] = new Vec2(vertices[i]);
        }
        this.vertices[count] = new Vec2(this.vertices[0]);
        prevVertex.set(this.vertices[this.count - 2]);
        nextVertex.set(this.vertices[1]);
        hasPrevVertex = true;
        hasNextVertex = true;
    }

    /**
     * Create a chain with isolated end vertices.
     *
     * @param vertices An array of vertices, these are copied.
     * @param count The vertex count.
     */
    public void createChain(final Vec2[] vertices, int count)
    {
        assert (this.vertices == null && this.count == 0);
        assert (count >= 2);
        this.count = count;
        this.vertices = new Vec2[this.count];
        for (int i = 1; i < this.count; i++)
        {
            Vec2 v1 = vertices[i - 1];
            Vec2 v2 = vertices[i];
            // If the code crashes here, it means your vertices are too close
            // together.
            if (MathUtils.distanceSquared(v1, v2) < Settings.linearSlop
                    * Settings.linearSlop)
            {
                throw new RuntimeException(
                        "Vertices of chain shape are too close together");
            }
        }
        for (int i = 0; i < this.count; i++)
        {
            this.vertices[i] = new Vec2(vertices[i]);
        }
        hasPrevVertex = false;
        hasNextVertex = false;
        prevVertex.setZero();
        nextVertex.setZero();
    }

    /**
     * Establish connectivity to a vertex that precedes the first vertex. Don't
     * call this for loops.
     */
    public void setPrevVertex(final Vec2 prevVertex)
    {
        this.prevVertex.set(prevVertex);
        hasPrevVertex = true;
    }

    /**
     * Establish connectivity to a vertex that follows the last vertex. Don't
     * call this for loops.
     */
    public void setNextVertex(final Vec2 nextVertex)
    {
        this.nextVertex.set(nextVertex);
        hasNextVertex = true;
    }
}
