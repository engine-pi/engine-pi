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
package org.jbox2d.collision;

import org.jbox2d.collision.Distance.SimplexCache;
import org.jbox2d.collision.Manifold.ManifoldType;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Rot;
import org.jbox2d.common.Settings;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.pooling.WorldPool;

/**
 * Functions used for computing contact points, distance queries, and TOI
 * queries. Collision methods are non-static for pooling speed, retrieve a
 * collision object from the {@code SingletonPool}. Should not be finalructed.
 *
 * @author Daniel Murphy
 */
public class Collision
{
    public static final int NULL_FEATURE = Integer.MAX_VALUE;

    private final WorldPool pool;

    public Collision(WorldPool argPool)
    {
        incidentEdge[0] = new ClipVertex();
        incidentEdge[1] = new ClipVertex();
        clipPoints1[0] = new ClipVertex();
        clipPoints1[1] = new ClipVertex();
        clipPoints2[0] = new ClipVertex();
        clipPoints2[1] = new ClipVertex();
        pool = argPool;
    }

    private final DistanceInput input = new DistanceInput();

    private final SimplexCache cache = new SimplexCache();

    private final DistanceOutput output = new DistanceOutput();

    /**
     * Determine if two generic shapes overlap.
     */
    public final boolean testOverlap(Shape shapeA, int indexA, Shape shapeB,
            int indexB, Transform xfA, Transform xfB)
    {
        input.proxyA.set(shapeA, indexA);
        input.proxyB.set(shapeB, indexB);
        input.transformA.set(xfA);
        input.transformB.set(xfB);
        input.useRadii = true;
        cache.count = 0;
        pool.getDistance().distance(output, cache, input);
        // djm note: anything significant about 10.0f?
        return output.distance < 10.0f * Settings.EPSILON;
    }

    /**
     * Compute the point states given two manifolds. The states pertain to the
     * transition from manifold1 to manifold2. So state1 is either persist or
     * remove while state2 is either add or persist.
     */
    public static void getPointStates(final PointState[] state1,
            final PointState[] state2, final Manifold manifold1,
            final Manifold manifold2)
    {
        for (int i = 0; i < Settings.maxManifoldPoints; i++)
        {
            state1[i] = PointState.NULL_STATE;
            state2[i] = PointState.NULL_STATE;
        }
        // Detect persists and removes.
        for (int i = 0; i < manifold1.pointCount; i++)
        {
            ContactID id = manifold1.points[i].id;
            state1[i] = PointState.REMOVE_STATE;
            for (int j = 0; j < manifold2.pointCount; j++)
            {
                if (manifold2.points[j].id.isEqual(id))
                {
                    state1[i] = PointState.PERSIST_STATE;
                    break;
                }
            }
        }
        // Detect persists and adds
        for (int i = 0; i < manifold2.pointCount; i++)
        {
            ContactID id = manifold2.points[i].id;
            state2[i] = PointState.ADD_STATE;
            for (int j = 0; j < manifold1.pointCount; j++)
            {
                if (manifold1.points[j].id.isEqual(id))
                {
                    state2[i] = PointState.PERSIST_STATE;
                    break;
                }
            }
        }
    }

    /**
     * Clipping for contact manifolds. Sutherland-Hodgman clipping.
     */
    public static int clipSegmentToLine(final ClipVertex[] vOut,
            final ClipVertex[] vIn, final Vec2 normal, float offset,
            int vertexIndexA)
    {
        // Start with no output points
        int numOut = 0;
        final ClipVertex vIn0 = vIn[0];
        final ClipVertex vIn1 = vIn[1];
        final Vec2 vIn0v = vIn0.v;
        final Vec2 vIn1v = vIn1.v;
        // Calculate the distance of end points to the line
        float distance0 = Vec2.dot(normal, vIn0v) - offset;
        float distance1 = Vec2.dot(normal, vIn1v) - offset;
        // If the points are behind the plane
        if (distance0 <= 0.0f)
        {
            vOut[numOut++].set(vIn0);
        }
        if (distance1 <= 0.0f)
        {
            vOut[numOut++].set(vIn1);
        }
        // If the points are on different sides of the plane
        if (distance0 * distance1 < 0.0f)
        {
            // Find intersection point of edge and plane
            float interp = distance0 / (distance0 - distance1);
            ClipVertex vOutNO = vOut[numOut];
            // vOut[numOut].v = vIn[0].v + interp * (vIn[1].v - vIn[0].v);
            vOutNO.v.x = vIn0v.x + interp * (vIn1v.x - vIn0v.x);
            vOutNO.v.y = vIn0v.y + interp * (vIn1v.y - vIn0v.y);
            // VertexA is hitting edgeB.
            vOutNO.id.indexA = (byte) vertexIndexA;
            vOutNO.id.indexB = vIn0.id.indexB;
            vOutNO.id.typeA = (byte) ContactID.Type.VERTEX.ordinal();
            vOutNO.id.typeB = (byte) ContactID.Type.FACE.ordinal();
            ++numOut;
        }
        return numOut;
    }
    // #### COLLISION STUFF (not from collision.h or collision.cpp) ####

    // djm pooling
    private static final Vec2 d = new Vec2();

    /**
     * Compute the collision manifold between two circles.
     */
    public final void collideCircles(Manifold manifold,
            final CircleShape circle1, final Transform xfA,
            final CircleShape circle2, final Transform xfB)
    {
        manifold.pointCount = 0;
        // before inline:
        // Transform.mulToOut(xfA, circle1.p, pA);
        // Transform.mulToOut(xfB, circle2.p, pB);
        // d.set(pB).subLocal(pA);
        // float distSqr = d.x * d.x + d.y * d.y;
        // after inline:
        Vec2 circle1p = circle1.p;
        Vec2 circle2p = circle2.p;
        float pAx = (xfA.q.c * circle1p.x - xfA.q.s * circle1p.y) + xfA.p.x;
        float pAy = (xfA.q.s * circle1p.x + xfA.q.c * circle1p.y) + xfA.p.y;
        float pBx = (xfB.q.c * circle2p.x - xfB.q.s * circle2p.y) + xfB.p.x;
        float pBy = (xfB.q.s * circle2p.x + xfB.q.c * circle2p.y) + xfB.p.y;
        float dx = pBx - pAx;
        float dy = pBy - pAy;
        float distSqr = dx * dx + dy * dy;
        // end inline
        final float radius = circle1.radius + circle2.radius;
        if (distSqr > radius * radius)
        {
            return;
        }
        manifold.type = ManifoldType.CIRCLES;
        manifold.localPoint.set(circle1p);
        manifold.localNormal.setZero();
        manifold.pointCount = 1;
        manifold.points[0].localPoint.set(circle2p);
        manifold.points[0].id.zero();
    }
    // djm pooling, and from above

    /**
     * Compute the collision manifold between a polygon and a circle.
     */
    public final void collidePolygonAndCircle(Manifold manifold,
            final PolygonShape polygon, final Transform xfA,
            final CircleShape circle, final Transform xfB)
    {
        manifold.pointCount = 0;
        // Vec2 v = circle.p;
        // Compute circle position in the frame of the polygon.
        // before inline:
        // Transform.mulToOutUnsafe(xfB, circle.p, c);
        // Transform.mulTransToOut(xfA, c, cLocal);
        // final float cLocalX = cLocal.x;
        // final float cLocalY = cLocal.y;
        // after inline:
        final Vec2 circleP = circle.p;
        final Rot xfBq = xfB.q;
        final Rot xfAq = xfA.q;
        final float cx = (xfBq.c * circleP.x - xfBq.s * circleP.y) + xfB.p.x;
        final float cy = (xfBq.s * circleP.x + xfBq.c * circleP.y) + xfB.p.y;
        final float px = cx - xfA.p.x;
        final float py = cy - xfA.p.y;
        final float cLocalX = (xfAq.c * px + xfAq.s * py);
        final float cLocalY = (-xfAq.s * px + xfAq.c * py);
        // end inline
        // Find the min separating edge.
        int normalIndex = 0;
        float separation = -Float.MAX_VALUE;
        final float radius = polygon.radius + circle.radius;
        final int vertexCount = polygon.count;
        float s;
        final Vec2[] vertices = polygon.vertices;
        final Vec2[] normals = polygon.normals;
        for (int i = 0; i < vertexCount; i++)
        {
            // before inline
            // temp.set(cLocal).subLocal(vertices[i]);
            // float s = Vec2.dot(normals[i], temp);
            // after inline
            final Vec2 vertex = vertices[i];
            final float tempX = cLocalX - vertex.x;
            final float tempY = cLocalY - vertex.y;
            s = normals[i].x * tempX + normals[i].y * tempY;
            if (s > radius)
            {
                // early out
                return;
            }
            if (s > separation)
            {
                separation = s;
                normalIndex = i;
            }
        }
        // Vertices that subtend the incident face.
        final int vertIndex1 = normalIndex;
        final int vertIndex2 = vertIndex1 + 1 < vertexCount ? vertIndex1 + 1
                : 0;
        final Vec2 v1 = vertices[vertIndex1];
        final Vec2 v2 = vertices[vertIndex2];
        // If the center is inside the polygon ...
        if (separation < Settings.EPSILON)
        {
            manifold.pointCount = 1;
            manifold.type = ManifoldType.FACE_A;
            // before inline:
            // manifold.localNormal.set(normals[normalIndex]);
            // manifold.localPoint.set(v1).addLocal(v2).mulLocal(.5f);
            // manifold.points[0].localPoint.set(circle.p);
            // after inline:
            final Vec2 normal = normals[normalIndex];
            manifold.localNormal.x = normal.x;
            manifold.localNormal.y = normal.y;
            manifold.localPoint.x = (v1.x + v2.x) * .5f;
            manifold.localPoint.y = (v1.y + v2.y) * .5f;
            final ManifoldPoint mPoint = manifold.points[0];
            mPoint.localPoint.x = circleP.x;
            mPoint.localPoint.y = circleP.y;
            mPoint.id.zero();
            // end inline
            return;
        }
        // Compute barycentric coordinates
        // before inline:
        // temp.set(cLocal).subLocal(v1);
        // temp2.set(v2).subLocal(v1);
        // float u1 = Vec2.dot(temp, temp2);
        // temp.set(cLocal).subLocal(v2);
        // temp2.set(v1).subLocal(v2);
        // float u2 = Vec2.dot(temp, temp2);
        // after inline:
        final float tempX = cLocalX - v1.x;
        final float tempY = cLocalY - v1.y;
        final float temp2X = v2.x - v1.x;
        final float temp2Y = v2.y - v1.y;
        final float u1 = tempX * temp2X + tempY * temp2Y;
        final float temp3X = cLocalX - v2.x;
        final float temp3Y = cLocalY - v2.y;
        final float temp4X = v1.x - v2.x;
        final float temp4Y = v1.y - v2.y;
        final float u2 = temp3X * temp4X + temp3Y * temp4Y;
        // end inline
        if (u1 <= 0f)
        {
            // inlined
            final float dx = cLocalX - v1.x;
            final float dy = cLocalY - v1.y;
            if (dx * dx + dy * dy > radius * radius)
            {
                return;
            }
            manifold.pointCount = 1;
            manifold.type = ManifoldType.FACE_A;
            // before inline:
            // manifold.localNormal.set(cLocal).subLocal(v1);
            // after inline:
            manifold.localNormal.x = cLocalX - v1.x;
            manifold.localNormal.y = cLocalY - v1.y;
            // end inline
            manifold.localNormal.normalize();
            manifold.localPoint.set(v1);
            manifold.points[0].localPoint.set(circleP);
            manifold.points[0].id.zero();
        }
        else if (u2 <= 0.0f)
        {
            // inlined
            final float dx = cLocalX - v2.x;
            final float dy = cLocalY - v2.y;
            if (dx * dx + dy * dy > radius * radius)
            {
                return;
            }
            manifold.pointCount = 1;
            manifold.type = ManifoldType.FACE_A;
            // before inline:
            // manifold.localNormal.set(cLocal).subLocal(v2);
            // after inline:
            manifold.localNormal.x = cLocalX - v2.x;
            manifold.localNormal.y = cLocalY - v2.y;
            // end inline
            manifold.localNormal.normalize();
            manifold.localPoint.set(v2);
            manifold.points[0].localPoint.set(circleP);
            manifold.points[0].id.zero();
        }
        else
        {
            // Vec2 faceCenter = 0.5f * (v1 + v2);
            // (temp is faceCenter)
            // before inline:
            // temp.set(v1).addLocal(v2).mulLocal(.5f);
            //
            // temp2.set(cLocal).subLocal(temp);
            // separation = Vec2.dot(temp2, normals[vertIndex1]);
            // if (separation > radius) {
            // return;
            // }
            // after inline:
            final float fcx = (v1.x + v2.x) * .5f;
            final float fcy = (v1.y + v2.y) * .5f;
            final float tx = cLocalX - fcx;
            final float ty = cLocalY - fcy;
            final Vec2 normal = normals[vertIndex1];
            separation = tx * normal.x + ty * normal.y;
            if (separation > radius)
            {
                return;
            }
            // end inline
            manifold.pointCount = 1;
            manifold.type = ManifoldType.FACE_A;
            manifold.localNormal.set(normals[vertIndex1]);
            manifold.localPoint.x = fcx; // (faceCenter)
            manifold.localPoint.y = fcy;
            manifold.points[0].localPoint.set(circleP);
            manifold.points[0].id.zero();
        }
    }

    // djm pooling, and from above
    private final Vec2 temp = new Vec2();

    private final Transform xf = new Transform();

    private final Vec2 n = new Vec2();

    private final Vec2 v1 = new Vec2();

    /**
     * Find the max separation between poly1 and poly2 using edge normals from
     * poly1.
     */
    public final void findMaxSeparation(EdgeResults results,
            final PolygonShape poly1, final Transform xf1,
            final PolygonShape poly2, final Transform xf2)
    {
        int count1 = poly1.count;
        int count2 = poly2.count;
        Vec2[] n1s = poly1.normals;
        Vec2[] v1s = poly1.vertices;
        Vec2[] v2s = poly2.vertices;
        Transform.mulTransToOutUnsafe(xf2, xf1, xf);
        final Rot xfq = xf.q;
        int bestIndex = 0;
        float maxSeparation = -Float.MAX_VALUE;
        for (int i = 0; i < count1; i++)
        {
            // Get poly1 normal in frame2.
            Rot.mulToOutUnsafe(xfq, n1s[i], n);
            Transform.mulToOutUnsafe(xf, v1s[i], v1);
            // Find the deepest point for normal i.
            float si = Float.MAX_VALUE;
            for (int j = 0; j < count2; ++j)
            {
                Vec2 v2sj = v2s[j];
                float sij = n.x * (v2sj.x - v1.x) + n.y * (v2sj.y - v1.y);
                if (sij < si)
                {
                    si = sij;
                }
            }
            if (si > maxSeparation)
            {
                maxSeparation = si;
                bestIndex = i;
            }
        }
        results.edgeIndex = bestIndex;
        results.separation = maxSeparation;
    }

    public final void findIncidentEdge(final ClipVertex[] c,
            final PolygonShape poly1, final Transform xf1, int edge1,
            final PolygonShape poly2, final Transform xf2)
    {
        int count1 = poly1.count;
        final Vec2[] normals1 = poly1.normals;
        int count2 = poly2.count;
        final Vec2[] vertices2 = poly2.vertices;
        final Vec2[] normals2 = poly2.normals;
        assert (0 <= edge1 && edge1 < count1);
        final ClipVertex c0 = c[0];
        final ClipVertex c1 = c[1];
        final Rot xf1q = xf1.q;
        final Rot xf2q = xf2.q;
        // Get the normal of the reference edge in poly2's frame.
        // Vec2 normal1 = MulT(xf2.R, Mul(xf1.R, normals1[edge1]));
        // before inline:
        // Rot.mulToOutUnsafe(xf1.q, normals1[edge1], normal1); // temporary
        // Rot.mulTrans(xf2.q, normal1, normal1);
        // after inline:
        final Vec2 v = normals1[edge1];
        final float tempX = xf1q.c * v.x - xf1q.s * v.y;
        final float tempY = xf1q.s * v.x + xf1q.c * v.y;
        final float normal1x = xf2q.c * tempX + xf2q.s * tempY;
        final float normal1y = -xf2q.s * tempX + xf2q.c * tempY;
        // end inline
        // Find the incident edge on poly2.
        int index = 0;
        float minDot = Float.MAX_VALUE;
        for (int i = 0; i < count2; ++i)
        {
            Vec2 b = normals2[i];
            float dot = normal1x * b.x + normal1y * b.y;
            if (dot < minDot)
            {
                minDot = dot;
                index = i;
            }
        }
        // Build the clip vertices for the incident edge.
        int i1 = index;
        int i2 = i1 + 1 < count2 ? i1 + 1 : 0;
        // c0.v = Mul(xf2, vertices2[i1]);
        Vec2 v1 = vertices2[i1];
        Vec2 out = c0.v;
        out.x = (xf2q.c * v1.x - xf2q.s * v1.y) + xf2.p.x;
        out.y = (xf2q.s * v1.x + xf2q.c * v1.y) + xf2.p.y;
        c0.id.indexA = (byte) edge1;
        c0.id.indexB = (byte) i1;
        c0.id.typeA = (byte) ContactID.Type.FACE.ordinal();
        c0.id.typeB = (byte) ContactID.Type.VERTEX.ordinal();
        // c1.v = Mul(xf2, vertices2[i2]);
        Vec2 v2 = vertices2[i2];
        Vec2 out1 = c1.v;
        out1.x = (xf2q.c * v2.x - xf2q.s * v2.y) + xf2.p.x;
        out1.y = (xf2q.s * v2.x + xf2q.c * v2.y) + xf2.p.y;
        c1.id.indexA = (byte) edge1;
        c1.id.indexB = (byte) i2;
        c1.id.typeA = (byte) ContactID.Type.FACE.ordinal();
        c1.id.typeB = (byte) ContactID.Type.VERTEX.ordinal();
    }

    private final EdgeResults results1 = new EdgeResults();

    private final EdgeResults results2 = new EdgeResults();

    private final ClipVertex[] incidentEdge = new ClipVertex[2];

    private final Vec2 localTangent = new Vec2();

    private final Vec2 localNormal = new Vec2();

    private final Vec2 planePoint = new Vec2();

    private final Vec2 tangent = new Vec2();

    private final Vec2 v11 = new Vec2();

    private final Vec2 v12 = new Vec2();

    private final ClipVertex[] clipPoints1 = new ClipVertex[2];

    private final ClipVertex[] clipPoints2 = new ClipVertex[2];

    /**
     * Compute the collision manifold between two polygons.
     */
    public final void collidePolygons(Manifold manifold,
            final PolygonShape polyA, final Transform xfA,
            final PolygonShape polyB, final Transform xfB)
    {
        // Find edge normal of max separation on A - return if separating axis
        // is found
        // Find edge normal of max separation on B - return if separation axis
        // is found
        // Choose reference edge as min(minA, minB)
        // Find incident edge
        // Clip
        // The normal points from 1 to 2
        manifold.pointCount = 0;
        float totalRadius = polyA.radius + polyB.radius;
        findMaxSeparation(results1, polyA, xfA, polyB, xfB);
        if (results1.separation > totalRadius)
        {
            return;
        }
        findMaxSeparation(results2, polyB, xfB, polyA, xfA);
        if (results2.separation > totalRadius)
        {
            return;
        }
        final PolygonShape poly1; // reference polygon
        final PolygonShape poly2; // incident polygon
        Transform xf1, xf2;
        int edge1; // reference edge
        boolean flip;
        final float tol = 0.1f * Settings.linearSlop;
        if (results2.separation > results1.separation + tol)
        {
            poly1 = polyB;
            poly2 = polyA;
            xf1 = xfB;
            xf2 = xfA;
            edge1 = results2.edgeIndex;
            manifold.type = ManifoldType.FACE_B;
            flip = true;
        }
        else
        {
            poly1 = polyA;
            poly2 = polyB;
            xf1 = xfA;
            xf2 = xfB;
            edge1 = results1.edgeIndex;
            manifold.type = ManifoldType.FACE_A;
            flip = false;
        }
        final Rot xf1q = xf1.q;
        findIncidentEdge(incidentEdge, poly1, xf1, edge1, poly2, xf2);
        int count1 = poly1.count;
        final Vec2[] vertices1 = poly1.vertices;
        final int iv1 = edge1;
        final int iv2 = edge1 + 1 < count1 ? edge1 + 1 : 0;
        v11.set(vertices1[iv1]);
        v12.set(vertices1[iv2]);
        localTangent.x = v12.x - v11.x;
        localTangent.y = v12.y - v11.y;
        localTangent.normalize();
        // Vec2 localNormal = Vec2.cross(dv, 1.0f);
        localNormal.x = localTangent.y;
        localNormal.y = -1f * localTangent.x;
        // Vec2 planePoint = 0.5f * (v11+ v12);
        planePoint.x = (v11.x + v12.x) * .5f;
        planePoint.y = (v11.y + v12.y) * .5f;
        // Rot.mulToOutUnsafe(xf1.q, localTangent, tangent);
        tangent.x = xf1q.c * localTangent.x - xf1q.s * localTangent.y;
        tangent.y = xf1q.s * localTangent.x + xf1q.c * localTangent.y;
        // Vec2.crossToOutUnsafe(tangent, 1f, normal);
        final float normalx = tangent.y;
        final float normaly = -1f * tangent.x;
        Transform.mulToOut(xf1, v11, v11);
        Transform.mulToOut(xf1, v12, v12);
        // v11 = Mul(xf1, v11);
        // v12 = Mul(xf1, v12);
        // Face offset
        // float frontOffset = Vec2.dot(normal, v11);
        float frontOffset = normalx * v11.x + normaly * v11.y;
        // Side offsets, extended by polytope skin thickness.
        // float sideOffset1 = -Vec2.dot(tangent, v11) + totalRadius;
        // float sideOffset2 = Vec2.dot(tangent, v12) + totalRadius;
        float sideOffset1 = -(tangent.x * v11.x + tangent.y * v11.y)
                + totalRadius;
        float sideOffset2 = tangent.x * v12.x + tangent.y * v12.y + totalRadius;
        // Clip incident edge against extruded edge1 side edges.
        // ClipVertex clipPoints1[2];
        // ClipVertex clipPoints2[2];
        int np;
        // Clip to box side 1
        // np = ClipSegmentToLine(clipPoints1, incidentEdge, -sideNormal,
        // sideOffset1);
        tangent.negateLocal();
        np = clipSegmentToLine(clipPoints1,
            incidentEdge,
            tangent,
            sideOffset1,
            iv1);
        tangent.negateLocal();
        if (np < 2)
        {
            return;
        }
        // Clip to negative box side 1
        np = clipSegmentToLine(clipPoints2,
            clipPoints1,
            tangent,
            sideOffset2,
            iv2);
        if (np < 2)
        {
            return;
        }
        // Now clipPoints2 contains the clipped points.
        manifold.localNormal.set(localNormal);
        manifold.localPoint.set(planePoint);
        int pointCount = 0;
        for (int i = 0; i < Settings.maxManifoldPoints; ++i)
        {
            // float separation = Vec2.dot(normal, clipPoints2[i].v) -
            // frontOffset;
            float separation = normalx * clipPoints2[i].v.x
                    + normaly * clipPoints2[i].v.y - frontOffset;
            if (separation <= totalRadius)
            {
                ManifoldPoint cp = manifold.points[pointCount];
                // cp.localPoint = MulT(xf2, clipPoints2[i].v);
                Vec2 out = cp.localPoint;
                final float px = clipPoints2[i].v.x - xf2.p.x;
                final float py = clipPoints2[i].v.y - xf2.p.y;
                out.x = (xf2.q.c * px + xf2.q.s * py);
                out.y = (-xf2.q.s * px + xf2.q.c * py);
                cp.id.set(clipPoints2[i].id);
                if (flip)
                {
                    // Swap features
                    cp.id.flip();
                }
                ++pointCount;
            }
        }
        manifold.pointCount = pointCount;
    }

    private final Vec2 Q = new Vec2();

    private final Vec2 e = new Vec2();

    private final ContactID cf = new ContactID();

    private final Vec2 e1 = new Vec2();

    private final Vec2 P = new Vec2();

    // Compute contact points for edge versus circle.
    // This accounts for edge connectivity.
    public void collideEdgeAndCircle(Manifold manifold, final EdgeShape edgeA,
            final Transform xfA, final CircleShape circleB, final Transform xfB)
    {
        manifold.pointCount = 0;
        // Compute circle in frame of edge
        // Vec2 Q = MulT(xfA, Mul(xfB, circleB.p));
        Transform.mulToOutUnsafe(xfB, circleB.p, temp);
        Transform.mulTransToOutUnsafe(xfA, temp, Q);
        final Vec2 A = edgeA.vertex1;
        final Vec2 B = edgeA.vertex2;
        e.set(B).subLocal(A);
        // Barycentric coordinates
        float u = Vec2.dot(e, temp.set(B).subLocal(Q));
        float v = Vec2.dot(e, temp.set(Q).subLocal(A));
        float radius = edgeA.radius + circleB.radius;
        // ContactFeature cf;
        cf.indexB = 0;
        cf.typeB = (byte) ContactID.Type.VERTEX.ordinal();
        // Region A
        if (v <= 0.0f)
        {
            d.set(Q).subLocal(A);
            float dd = Vec2.dot(d, d);
            if (dd > radius * radius)
            {
                return;
            }
            // Is there an edge connected to A?
            if (edgeA.hasVertex0)
            {
                final Vec2 A1 = edgeA.vertex0;
                e1.set(A).subLocal(A1);
                float u1 = Vec2.dot(e1, temp.set(A).subLocal(Q));
                // Is the circle in Region AB of the previous edge?
                if (u1 > 0.0f)
                {
                    return;
                }
            }
            cf.indexA = 0;
            cf.typeA = (byte) ContactID.Type.VERTEX.ordinal();
            manifold.pointCount = 1;
            manifold.type = Manifold.ManifoldType.CIRCLES;
            manifold.localNormal.setZero();
            manifold.localPoint.set(A);
            // manifold.points[0].id.key = 0;
            manifold.points[0].id.set(cf);
            manifold.points[0].localPoint.set(circleB.p);
            return;
        }
        // Region B
        if (u <= 0.0f)
        {
            d.set(Q).subLocal(B);
            float dd = Vec2.dot(d, d);
            if (dd > radius * radius)
            {
                return;
            }
            // Is there an edge connected to B?
            if (edgeA.hasVertex3)
            {
                final Vec2 B2 = edgeA.vertex3;
                final Vec2 e2 = e1;
                e2.set(B2).subLocal(B);
                float v2 = Vec2.dot(e2, temp.set(Q).subLocal(B));
                // Is the circle in Region AB of the next edge?
                if (v2 > 0.0f)
                {
                    return;
                }
            }
            cf.indexA = 1;
            cf.typeA = (byte) ContactID.Type.VERTEX.ordinal();
            manifold.pointCount = 1;
            manifold.type = Manifold.ManifoldType.CIRCLES;
            manifold.localNormal.setZero();
            manifold.localPoint.set(B);
            // manifold.points[0].id.key = 0;
            manifold.points[0].id.set(cf);
            manifold.points[0].localPoint.set(circleB.p);
            return;
        }
        // Region AB
        float den = Vec2.dot(e, e);
        assert (den > 0.0f);
        // Vec2 P = (1.0f / den) * (u * A + v * B);
        P.set(A).mulLocal(u).addLocal(temp.set(B).mulLocal(v));
        P.mulLocal(1.0f / den);
        d.set(Q).subLocal(P);
        float dd = Vec2.dot(d, d);
        if (dd > radius * radius)
        {
            return;
        }
        n.x = -e.y;
        n.y = e.x;
        if (Vec2.dot(n, temp.set(Q).subLocal(A)) < 0.0f)
        {
            n.set(-n.x, -n.y);
        }
        n.normalize();
        cf.indexA = 0;
        cf.typeA = (byte) ContactID.Type.FACE.ordinal();
        manifold.pointCount = 1;
        manifold.type = Manifold.ManifoldType.FACE_A;
        manifold.localNormal.set(n);
        manifold.localPoint.set(A);
        // manifold.points[0].id.key = 0;
        manifold.points[0].id.set(cf);
        manifold.points[0].localPoint.set(circleB.p);
    }

    private final EPCollider collider = new EPCollider();

    public void collideEdgeAndPolygon(Manifold manifold, final EdgeShape edgeA,
            final Transform xfA, final PolygonShape polygonB,
            final Transform xfB)
    {
        collider.collide(manifold, edgeA, xfA, polygonB, xfB);
    }

    /**
     * Java-specific class for returning edge results
     */
    public static class EdgeResults
    {
        public float separation;

        public int edgeIndex;
    }

    /**
     * Used for computing contact manifolds.
     */
    public static class ClipVertex
    {
        public final Vec2 v;

        public final ContactID id;

        public ClipVertex()
        {
            v = new Vec2();
            id = new ContactID();
        }

        public void set(final ClipVertex cv)
        {
            Vec2 v1 = cv.v;
            v.x = v1.x;
            v.y = v1.y;
            ContactID c = cv.id;
            id.indexA = c.indexA;
            id.indexB = c.indexB;
            id.typeA = c.typeA;
            id.typeB = c.typeB;
        }
    }

    /**
     * This is used for determining the state of contact points.
     *
     * @author Daniel Murphy
     */
    public enum PointState
    {
        /**
         * point does not exist
         */
        NULL_STATE,
        /**
         * point was added in the update
         */
        ADD_STATE,
        /**
         * point persisted across the update
         */
        PERSIST_STATE,
        /**
         * point was removed in the update
         */
        REMOVE_STATE
    }

    /**
     * This structure is used to keep track of the best separating axis.
     */
    static class EPAxis
    {
        enum Type
        {
            UNKNOWN,
            EDGE_A,
            EDGE_B
        }

        Type type;

        int index;

        float separation;
    }

    /**
     * This holds polygon B expressed in frame A.
     */
    static class TempPolygon
    {
        final Vec2[] vertices = new Vec2[Settings.maxPolygonVertices];

        final Vec2[] normals = new Vec2[Settings.maxPolygonVertices];

        int count;

        public TempPolygon()
        {
            for (int i = 0; i < vertices.length; i++)
            {
                vertices[i] = new Vec2();
                normals[i] = new Vec2();
            }
        }
    }

    /**
     * Reference face used for clipping
     */
    static class ReferenceFace
    {
        int i1, i2;

        final Vec2 v1 = new Vec2();

        final Vec2 v2 = new Vec2();

        final Vec2 normal = new Vec2();

        final Vec2 sideNormal1 = new Vec2();

        float sideOffset1;

        final Vec2 sideNormal2 = new Vec2();

        float sideOffset2;
    }

    /**
     * This class collides and edge and a polygon, taking into account edge
     * adjacency.
     */
    static class EPCollider
    {
        enum VertexType
        {
            ISOLATED,
            CONCAVE,
            CONVEX
        }

        final TempPolygon polygonB = new TempPolygon();

        final Transform xf = new Transform();

        final Vec2 centroidB = new Vec2();

        Vec2 v0 = new Vec2();

        Vec2 v1 = new Vec2();

        Vec2 v2 = new Vec2();

        Vec2 v3 = new Vec2();

        final Vec2 normal0 = new Vec2();

        final Vec2 normal1 = new Vec2();

        final Vec2 normal2 = new Vec2();

        final Vec2 normal = new Vec2();

        VertexType type1, type2;

        final Vec2 lowerLimit = new Vec2();

        final Vec2 upperLimit = new Vec2();

        float radius;

        boolean front;

        public EPCollider()
        {
            for (int i = 0; i < 2; i++)
            {
                ie[i] = new ClipVertex();
                clipPoints1[i] = new ClipVertex();
                clipPoints2[i] = new ClipVertex();
            }
        }

        private final Vec2 edge1 = new Vec2();

        private final Vec2 temp = new Vec2();

        private final Vec2 edge0 = new Vec2();

        private final Vec2 edge2 = new Vec2();

        private final ClipVertex[] ie = new ClipVertex[2];

        private final ClipVertex[] clipPoints1 = new ClipVertex[2];

        private final ClipVertex[] clipPoints2 = new ClipVertex[2];

        private final ReferenceFace rf = new ReferenceFace();

        private final EPAxis edgeAxis = new EPAxis();

        private final EPAxis polygonAxis = new EPAxis();

        public void collide(Manifold manifold, final EdgeShape edgeA,
                final Transform xfA, final PolygonShape polygonB,
                final Transform xfB)
        {
            Transform.mulTransToOutUnsafe(xfA, xfB, xf);
            Transform.mulToOutUnsafe(xf, polygonB.centroid, centroidB);
            v0 = edgeA.vertex0;
            v1 = edgeA.vertex1;
            v2 = edgeA.vertex2;
            v3 = edgeA.vertex3;
            boolean hasVertex0 = edgeA.hasVertex0;
            boolean hasVertex3 = edgeA.hasVertex3;
            edge1.set(v2).subLocal(v1);
            edge1.normalize();
            normal1.set(edge1.y, -edge1.x);
            float offset1 = Vec2.dot(normal1, temp.set(centroidB).subLocal(v1));
            float offset0 = 0.0f, offset2 = 0.0f;
            boolean convex1 = false, convex2 = false;
            // Is there a preceding edge?
            if (hasVertex0)
            {
                edge0.set(v1).subLocal(v0);
                edge0.normalize();
                normal0.set(edge0.y, -edge0.x);
                convex1 = Vec2.cross(edge0, edge1) >= 0.0f;
                offset0 = Vec2.dot(normal0, temp.set(centroidB).subLocal(v0));
            }
            // Is there a following edge?
            if (hasVertex3)
            {
                edge2.set(v3).subLocal(v2);
                edge2.normalize();
                normal2.set(edge2.y, -edge2.x);
                convex2 = Vec2.cross(edge1, edge2) > 0.0f;
                offset2 = Vec2.dot(normal2, temp.set(centroidB).subLocal(v2));
            }
            // Determine front or back collision. Determine collision normal
            // limits.
            if (hasVertex0 && hasVertex3)
            {
                if (convex1 && convex2)
                {
                    front = offset0 >= 0.0f || offset1 >= 0.0f
                            || offset2 >= 0.0f;
                    if (front)
                    {
                        normal.x = normal1.x;
                        normal.y = normal1.y;
                        lowerLimit.x = normal0.x;
                        lowerLimit.y = normal0.y;
                        upperLimit.x = normal2.x;
                        upperLimit.y = normal2.y;
                    }
                    else
                    {
                        normal.x = -normal1.x;
                        normal.y = -normal1.y;
                        lowerLimit.x = -normal1.x;
                        lowerLimit.y = -normal1.y;
                        upperLimit.x = -normal1.x;
                        upperLimit.y = -normal1.y;
                    }
                }
                else if (convex1)
                {
                    front = offset0 >= 0.0f
                            || (offset1 >= 0.0f && offset2 >= 0.0f);
                    if (front)
                    {
                        normal.x = normal1.x;
                        normal.y = normal1.y;
                        lowerLimit.x = normal0.x;
                        lowerLimit.y = normal0.y;
                        upperLimit.x = normal1.x;
                        upperLimit.y = normal1.y;
                    }
                    else
                    {
                        normal.x = -normal1.x;
                        normal.y = -normal1.y;
                        lowerLimit.x = -normal2.x;
                        lowerLimit.y = -normal2.y;
                        upperLimit.x = -normal1.x;
                        upperLimit.y = -normal1.y;
                    }
                }
                else if (convex2)
                {
                    front = offset2 >= 0.0f
                            || (offset0 >= 0.0f && offset1 >= 0.0f);
                    if (front)
                    {
                        normal.x = normal1.x;
                        normal.y = normal1.y;
                        lowerLimit.x = normal1.x;
                        lowerLimit.y = normal1.y;
                        upperLimit.x = normal2.x;
                        upperLimit.y = normal2.y;
                    }
                    else
                    {
                        normal.x = -normal1.x;
                        normal.y = -normal1.y;
                        lowerLimit.x = -normal1.x;
                        lowerLimit.y = -normal1.y;
                        upperLimit.x = -normal0.x;
                        upperLimit.y = -normal0.y;
                    }
                }
                else
                {
                    front = offset0 >= 0.0f && offset1 >= 0.0f
                            && offset2 >= 0.0f;
                    if (front)
                    {
                        normal.x = normal1.x;
                        normal.y = normal1.y;
                        lowerLimit.x = normal1.x;
                        lowerLimit.y = normal1.y;
                        upperLimit.x = normal1.x;
                        upperLimit.y = normal1.y;
                    }
                    else
                    {
                        normal.x = -normal1.x;
                        normal.y = -normal1.y;
                        lowerLimit.x = -normal2.x;
                        lowerLimit.y = -normal2.y;
                        upperLimit.x = -normal0.x;
                        upperLimit.y = -normal0.y;
                    }
                }
            }
            else if (hasVertex0)
            {
                if (convex1)
                {
                    front = offset0 >= 0.0f || offset1 >= 0.0f;
                    if (front)
                    {
                        normal.x = normal1.x;
                        normal.y = normal1.y;
                        lowerLimit.x = normal0.x;
                        lowerLimit.y = normal0.y;
                    }
                    else
                    {
                        normal.x = -normal1.x;
                        normal.y = -normal1.y;
                        lowerLimit.x = normal1.x;
                        lowerLimit.y = normal1.y;
                    }
                    upperLimit.x = -normal1.x;
                    upperLimit.y = -normal1.y;
                }
                else
                {
                    front = offset0 >= 0.0f && offset1 >= 0.0f;
                    if (front)
                    {
                        normal.x = normal1.x;
                        normal.y = normal1.y;
                        lowerLimit.x = normal1.x;
                        lowerLimit.y = normal1.y;
                        upperLimit.x = -normal1.x;
                        upperLimit.y = -normal1.y;
                    }
                    else
                    {
                        normal.x = -normal1.x;
                        normal.y = -normal1.y;
                        lowerLimit.x = normal1.x;
                        lowerLimit.y = normal1.y;
                        upperLimit.x = -normal0.x;
                        upperLimit.y = -normal0.y;
                    }
                }
            }
            else if (hasVertex3)
            {
                if (convex2)
                {
                    front = offset1 >= 0.0f || offset2 >= 0.0f;
                    if (front)
                    {
                        normal.x = normal1.x;
                        normal.y = normal1.y;
                        lowerLimit.x = -normal1.x;
                        lowerLimit.y = -normal1.y;
                        upperLimit.x = normal2.x;
                        upperLimit.y = normal2.y;
                    }
                    else
                    {
                        normal.x = -normal1.x;
                        normal.y = -normal1.y;
                        lowerLimit.x = -normal1.x;
                        lowerLimit.y = -normal1.y;
                        upperLimit.x = normal1.x;
                        upperLimit.y = normal1.y;
                    }
                }
                else
                {
                    front = offset1 >= 0.0f && offset2 >= 0.0f;
                    if (front)
                    {
                        normal.x = normal1.x;
                        normal.y = normal1.y;
                        lowerLimit.x = -normal1.x;
                        lowerLimit.y = -normal1.y;
                    }
                    else
                    {
                        normal.x = -normal1.x;
                        normal.y = -normal1.y;
                        lowerLimit.x = -normal2.x;
                        lowerLimit.y = -normal2.y;
                    }
                    upperLimit.x = normal1.x;
                    upperLimit.y = normal1.y;
                }
            }
            else
            {
                front = offset1 >= 0.0f;
                if (front)
                {
                    normal.x = normal1.x;
                    normal.y = normal1.y;
                    lowerLimit.x = -normal1.x;
                    lowerLimit.y = -normal1.y;
                    upperLimit.x = -normal1.x;
                    upperLimit.y = -normal1.y;
                }
                else
                {
                    normal.x = -normal1.x;
                    normal.y = -normal1.y;
                    lowerLimit.x = normal1.x;
                    lowerLimit.y = normal1.y;
                    upperLimit.x = normal1.x;
                    upperLimit.y = normal1.y;
                }
            }
            // Get polygonB in frameA
            this.polygonB.count = polygonB.count;
            for (int i = 0; i < polygonB.count; ++i)
            {
                Transform.mulToOutUnsafe(xf,
                    polygonB.vertices[i],
                    this.polygonB.vertices[i]);
                Rot.mulToOutUnsafe(xf.q,
                    polygonB.normals[i],
                    this.polygonB.normals[i]);
            }
            radius = 2.0f * Settings.polygonRadius;
            manifold.pointCount = 0;
            computeEdgeSeparation(edgeAxis);
            // If no valid normal can be found than this edge should not
            // collide.
            if (edgeAxis.type == EPAxis.Type.UNKNOWN)
            {
                return;
            }
            if (edgeAxis.separation > radius)
            {
                return;
            }
            computePolygonSeparation(polygonAxis);
            if (polygonAxis.type != EPAxis.Type.UNKNOWN
                    && polygonAxis.separation > radius)
            {
                return;
            }
            // Use hysteresis for jitter reduction.
            EPAxis primaryAxis = getEpAxis();
            final ClipVertex ie0 = ie[0];
            final ClipVertex ie1 = ie[1];
            if (primaryAxis.type == EPAxis.Type.EDGE_A)
            {
                manifold.type = Manifold.ManifoldType.FACE_A;
                // Search for the polygon normal that is the most antiparallel
                // to
                // the edge normal.
                int i1 = getI1();
                int i2 = i1 + 1 < this.polygonB.count ? i1 + 1 : 0;
                ie0.v.set(this.polygonB.vertices[i1]);
                ie0.id.indexA = 0;
                ie0.id.indexB = (byte) i1;
                ie0.id.typeA = (byte) ContactID.Type.FACE.ordinal();
                ie0.id.typeB = (byte) ContactID.Type.VERTEX.ordinal();
                ie1.v.set(this.polygonB.vertices[i2]);
                ie1.id.indexA = 0;
                ie1.id.indexB = (byte) i2;
                ie1.id.typeA = (byte) ContactID.Type.FACE.ordinal();
                ie1.id.typeB = (byte) ContactID.Type.VERTEX.ordinal();
                if (front)
                {
                    rf.i1 = 0;
                    rf.i2 = 1;
                    rf.v1.set(v1);
                    rf.v2.set(v2);
                    rf.normal.set(normal1);
                }
                else
                {
                    rf.i1 = 1;
                    rf.i2 = 0;
                    rf.v1.set(v2);
                    rf.v2.set(v1);
                    rf.normal.set(normal1).negateLocal();
                }
            }
            else
            {
                manifold.type = Manifold.ManifoldType.FACE_B;
                ie0.v.set(v1);
                ie0.id.indexA = 0;
                ie0.id.indexB = (byte) primaryAxis.index;
                ie0.id.typeA = (byte) ContactID.Type.VERTEX.ordinal();
                ie0.id.typeB = (byte) ContactID.Type.FACE.ordinal();
                ie1.v.set(v2);
                ie1.id.indexA = 0;
                ie1.id.indexB = (byte) primaryAxis.index;
                ie1.id.typeA = (byte) ContactID.Type.VERTEX.ordinal();
                ie1.id.typeB = (byte) ContactID.Type.FACE.ordinal();
                rf.i1 = primaryAxis.index;
                rf.i2 = rf.i1 + 1 < this.polygonB.count ? rf.i1 + 1 : 0;
                rf.v1.set(this.polygonB.vertices[rf.i1]);
                rf.v2.set(this.polygonB.vertices[rf.i2]);
                rf.normal.set(this.polygonB.normals[rf.i1]);
            }
            rf.sideNormal1.set(rf.normal.y, -rf.normal.x);
            rf.sideNormal2.set(rf.sideNormal1).negateLocal();
            rf.sideOffset1 = Vec2.dot(rf.sideNormal1, rf.v1);
            rf.sideOffset2 = Vec2.dot(rf.sideNormal2, rf.v2);
            // Clip incident edge against extruded edge1 side edges.
            int np;
            // Clip to box side 1
            np = clipSegmentToLine(clipPoints1,
                ie,
                rf.sideNormal1,
                rf.sideOffset1,
                rf.i1);
            if (np < Settings.maxManifoldPoints)
            {
                return;
            }
            // Clip to negative box side 1
            np = clipSegmentToLine(clipPoints2,
                clipPoints1,
                rf.sideNormal2,
                rf.sideOffset2,
                rf.i2);
            if (np < Settings.maxManifoldPoints)
            {
                return;
            }
            // Now clipPoints2 contains the clipped points.
            if (primaryAxis.type == EPAxis.Type.EDGE_A)
            {
                manifold.localNormal.set(rf.normal);
                manifold.localPoint.set(rf.v1);
            }
            else
            {
                manifold.localNormal.set(polygonB.normals[rf.i1]);
                manifold.localPoint.set(polygonB.vertices[rf.i1]);
            }
            int pointCount = 0;
            for (int i = 0; i < Settings.maxManifoldPoints; ++i)
            {
                float separation;
                separation = Vec2.dot(rf.normal,
                    temp.set(clipPoints2[i].v).subLocal(rf.v1));
                if (separation <= radius)
                {
                    ManifoldPoint cp = manifold.points[pointCount];
                    if (primaryAxis.type == EPAxis.Type.EDGE_A)
                    {
                        // cp.localPoint = MulT(xf, clipPoints2[i].v);
                        Transform.mulTransToOutUnsafe(xf,
                            clipPoints2[i].v,
                            cp.localPoint);
                        cp.id.set(clipPoints2[i].id);
                    }
                    else
                    {
                        cp.localPoint.set(clipPoints2[i].v);
                        cp.id.typeA = clipPoints2[i].id.typeB;
                        cp.id.typeB = clipPoints2[i].id.typeA;
                        cp.id.indexA = clipPoints2[i].id.indexB;
                        cp.id.indexB = clipPoints2[i].id.indexA;
                    }
                    ++pointCount;
                }
            }
            manifold.pointCount = pointCount;
        }

        private int getI1()
        {
            int bestIndex = 0;
            float bestValue = Vec2.dot(normal, this.polygonB.normals[0]);
            for (int i = 1; i < this.polygonB.count; ++i)
            {
                float value = Vec2.dot(normal, this.polygonB.normals[i]);
                if (value < bestValue)
                {
                    bestValue = value;
                    bestIndex = i;
                }
            }
            return bestIndex;
        }

        private EPAxis getEpAxis()
        {
            final float relativeTol = 0.98f;
            final float absoluteTol = 0.001f;
            EPAxis primaryAxis;
            if (polygonAxis.type == EPAxis.Type.UNKNOWN)
            {
                primaryAxis = edgeAxis;
            }
            else if (polygonAxis.separation > relativeTol * edgeAxis.separation
                    + absoluteTol)
            {
                primaryAxis = polygonAxis;
            }
            else
            {
                primaryAxis = edgeAxis;
            }
            return primaryAxis;
        }

        public void computeEdgeSeparation(EPAxis axis)
        {
            axis.type = EPAxis.Type.EDGE_A;
            axis.index = front ? 0 : 1;
            axis.separation = Float.MAX_VALUE;
            float nx = normal.x;
            float ny = normal.y;
            for (int i = 0; i < polygonB.count; ++i)
            {
                Vec2 v = polygonB.vertices[i];
                float tempX = v.x - v1.x;
                float tempY = v.y - v1.y;
                float s = nx * tempX + ny * tempY;
                if (s < axis.separation)
                {
                    axis.separation = s;
                }
            }
        }

        private final Vec2 perp = new Vec2();

        private final Vec2 n = new Vec2();

        public void computePolygonSeparation(EPAxis axis)
        {
            axis.type = EPAxis.Type.UNKNOWN;
            axis.index = -1;
            axis.separation = -Float.MAX_VALUE;
            perp.x = -normal.y;
            perp.y = normal.x;
            for (int i = 0; i < polygonB.count; ++i)
            {
                Vec2 normalB = polygonB.normals[i];
                Vec2 vB = polygonB.vertices[i];
                n.x = -normalB.x;
                n.y = -normalB.y;
                // float s1 = Vec2.dot(n, temp.set(vB).subLocal(v1));
                // float s2 = Vec2.dot(n, temp.set(vB).subLocal(v2));
                float tempX = vB.x - v1.x;
                float tempY = vB.y - v1.y;
                float s1 = n.x * tempX + n.y * tempY;
                tempX = vB.x - v2.x;
                tempY = vB.y - v2.y;
                float s2 = n.x * tempX + n.y * tempY;
                float s = MathUtils.min(s1, s2);
                if (s > radius)
                {
                    // No collision
                    axis.type = EPAxis.Type.EDGE_B;
                    axis.index = i;
                    axis.separation = s;
                    return;
                }
                // Adjacency
                if (n.x * perp.x + n.y * perp.y >= 0.0f)
                {
                    if (Vec2.dot(temp.set(n).subLocal(upperLimit),
                        normal) < -Settings.angularSlop)
                    {
                        continue;
                    }
                }
                else
                {
                    if (Vec2.dot(temp.set(n).subLocal(lowerLimit),
                        normal) < -Settings.angularSlop)
                    {
                        continue;
                    }
                }
                if (s > axis.separation)
                {
                    axis.type = EPAxis.Type.EDGE_B;
                    axis.index = i;
                    axis.separation = s;
                }
            }
        }
    }
}
