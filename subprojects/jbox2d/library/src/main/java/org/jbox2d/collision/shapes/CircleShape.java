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
 * A circle shape.
 *
 * @repolink https://github.com/erincatto/box2d/blob/main/src/collision/b2_collide_circle.cpp
 *
 * @author Daniel Murphy
 */
public class CircleShape extends Shape
{
    public final Vec2 p;

    public CircleShape()
    {
        super(ShapeType.CIRCLE);
        p = new Vec2();
        radius = 0;
    }

    public final Shape clone()
    {
        CircleShape shape = new CircleShape();
        shape.p.x = p.x;
        shape.p.y = p.y;
        shape.radius = radius;
        return shape;
    }

    public final int getChildCount()
    {
        return 1;
    }

    /**
     * Get the supporting vertex index in the given direction.
     */
    public final int getSupport(final Vec2 d)
    {
        return 0;
    }

    /**
     * Get the supporting vertex in the given direction.
     */
    public final Vec2 getSupportVertex(final Vec2 d)
    {
        return p;
    }

    /**
     * Get the vertex count.
     *
     */
    public final int getVertexCount()
    {
        return 1;
    }

    /**
     * Get a vertex by index.
     */
    public final Vec2 getVertex(final int index)
    {
        assert (index == 0);
        return p;
    }

    @Override
    public final boolean testPoint(final Transform transform, final Vec2 p)
    {
        // Rot.mulToOutUnsafe(transform.q, p, center);
        // center.addLocal(transform.p);
        //
        // final Vec2 d = center.subLocal(p).negateLocal();
        // return Vec2.dot(d, d) <= radius * radius;
        final Rot q = transform.q;
        final Vec2 tp = transform.p;
        float centerx = -(q.c * this.p.x - q.s * this.p.y + tp.x - p.x);
        float centery = -(q.s * this.p.x + q.c * this.p.y + tp.y - p.y);
        return centerx * centerx + centery * centery <= radius * radius;
    }

    @Override
    public float computeDistanceToOut(Transform xf, Vec2 p, int childIndex,
            Vec2 normalOut)
    {
        final Rot xfq = xf.q;
        float centerx = xfq.c * this.p.x - xfq.s * this.p.y + xf.p.x;
        float centery = xfq.s * this.p.x + xfq.c * this.p.y + xf.p.y;
        float dx = p.x - centerx;
        float dy = p.y - centery;
        float d1 = MathUtils.sqrt(dx * dx + dy * dy);
        normalOut.x = dx * 1 / d1;
        normalOut.y = dy * 1 / d1;
        return d1 - radius;
    }

    // Collision Detection in Interactive 3D Environments by Gino van den Bergen
    // From Section 3.1.2
    // x = s + a * r
    // norm(x) = radius
    @Override
    public final boolean raycast(RayCastOutput output, RayCastInput input,
            Transform transform, int childIndex)
    {
        final Vec2 inputP1 = input.p1;
        final Vec2 inputP2 = input.p2;
        final Rot tq = transform.q;
        final Vec2 tp = transform.p;
        // Rot.mulToOutUnsafe(transform.q, p, position);
        // position.addLocal(transform.p);
        final float positionX = tq.c * p.x - tq.s * p.y + tp.x;
        final float positionY = tq.s * p.x + tq.c * p.y + tp.y;
        final float sx = inputP1.x - positionX;
        final float sy = inputP1.y - positionY;
        // final float b = Vec2.dot(s, s) - radius * radius;
        final float b = sx * sx + sy * sy - radius * radius;
        // Solve quadratic equation.
        final float rx = inputP2.x - inputP1.x;
        final float ry = inputP2.y - inputP1.y;
        // final float c = Vec2.dot(s, r);
        // final float rr = Vec2.dot(r, r);
        final float c = sx * rx + sy * ry;
        final float rr = rx * rx + ry * ry;
        final float sigma = c * c - rr * b;
        // Check for negative discriminant and short segment.
        if (sigma < 0.0f || rr < Settings.EPSILON)
        {
            return false;
        }
        // Find the point of intersection of the line with the circle.
        float a = -(c + MathUtils.sqrt(sigma));
        // Is the intersection point on the segment?
        if (0.0f <= a && a <= input.maxFraction * rr)
        {
            a /= rr;
            output.fraction = a;
            output.normal.x = rx * a + sx;
            output.normal.y = ry * a + sy;
            output.normal.normalize();
            return true;
        }
        return false;
    }

    @Override
    public final void computeAABB(final AABB aabb, final Transform transform,
            int childIndex)
    {
        final Rot tq = transform.q;
        final Vec2 tp = transform.p;
        final float px = tq.c * p.x - tq.s * p.y + tp.x;
        final float py = tq.s * p.x + tq.c * p.y + tp.y;
        aabb.lowerBound.x = px - radius;
        aabb.lowerBound.y = py - radius;
        aabb.upperBound.x = px + radius;
        aabb.upperBound.y = py + radius;
    }

    @Override
    public final void computeMass(final MassData massData, final float density)
    {
        massData.mass = density * Settings.PI * radius * radius;
        massData.center.x = p.x;
        massData.center.y = p.y;
        // inertia about the local origin
        // massData.I = massData.mass * (0.5f * radius * radius +
        // Vec2.dot(p, p));
        massData.I = massData.mass
                * (0.5f * radius * radius + (p.x * p.x + p.y * p.y));
    }
}
