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

import java.lang.reflect.Array;
import java.util.Arrays;

import org.jbox2d.callbacks.ParticleDestructionListener;
import org.jbox2d.callbacks.ParticleQueryCallback;
import org.jbox2d.callbacks.ParticleRaycastCallback;
import org.jbox2d.callbacks.QueryCallback;
import org.jbox2d.collision.AABB;
import org.jbox2d.collision.RayCastInput;
import org.jbox2d.collision.RayCastOutput;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.BufferUtils;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Rot;
import org.jbox2d.common.Settings;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.TimeStep;
import org.jbox2d.dynamics.World;
import org.jbox2d.particle.VoronoiDiagram.VoronoiDiagramCallback;

public class ParticleSystem
{
    /**
     * All particle types that require creating pairs
     */
    private static final int pairFlags = ParticleType.springParticle;

    /**
     * All particle types that require creating triads
     */
    private static final int triadFlags = ParticleType.elasticParticle;

    /**
     * All particle types that require computing depth
     */
    private static final int noPressureFlags = ParticleType.powderParticle;

    static final int xTruncBits = 12;

    static final int yTruncBits = 12;

    static final int tagBits = 8 * 4 - 1 /* sizeof(int) */;

    static final long yOffset = 1 << (yTruncBits - 1);

    static final int yShift = tagBits - yTruncBits;

    static final int xShift = tagBits - yTruncBits - xTruncBits;

    static final long xScale = 1 << xShift;

    static final long xOffset = xScale * (1 << (xTruncBits - 1));

    static final int xMask = (1 << xTruncBits) - 1;

    static final int yMask = (1 << yTruncBits) - 1;

    static long computeTag(float x, float y)
    {
        return (((long) (y + yOffset)) << yShift)
                + (((long) (xScale * x)) + xOffset);
    }

    static long computeRelativeTag(long tag, int x, int y)
    {
        return tag + ((long) y << yShift) + ((long) x << xShift);
    }

    static int limitCapacity(int capacity, int maxCount)
    {
        return maxCount != 0 && capacity > maxCount ? maxCount : capacity;
    }

    int timestamp;

    int allParticleFlags;

    int allGroupFlags;

    float density;

    float inverseDensity;

    float gravityScale;

    float particleDiameter;

    float inverseDiameter;

    float squaredDiameter;

    int count;

    int internalAllocatedCapacity;

    int maxCount;

    ParticleBufferInt flagsBuffer;

    ParticleBuffer<Vec2> positionBuffer;

    ParticleBuffer<Vec2> velocityBuffer;

    float[] accumulationBuffer; // temporary values

    Vec2[] accumulation2Buffer; // temporary vector values

    float[] depthBuffer; // distance from the surface

    public ParticleBuffer<ParticleColor> colorBuffer;

    ParticleGroup[] groupBuffer;

    ParticleBuffer<Object> userDataBuffer;

    int proxyCount;

    int proxyCapacity;

    Proxy[] proxyBuffer;

    public int contactCount;

    int contactCapacity;

    public ParticleContact[] contactBuffer;

    public int bodyContactCount;

    int bodyContactCapacity;

    public ParticleBodyContact[] bodyContactBuffer;

    int pairCount;

    int pairCapacity;

    Pair[] pairBuffer;

    int triadCount;

    int triadCapacity;

    Triad[] triadBuffer;

    int groupCount;

    ParticleGroup groupList;

    float pressureStrength;

    float dampingStrength;

    float elasticStrength;

    float springStrength;

    float viscousStrength;

    float surfaceTensionStrengthA;

    float surfaceTensionStrengthB;

    float powderStrength;

    float ejectionStrength;

    float colorMixingStrength;

    World world;

    public ParticleSystem(World world)
    {
        this.world = world;
        timestamp = 0;
        allParticleFlags = 0;
        allGroupFlags = 0;
        density = 1;
        inverseDensity = 1;
        gravityScale = 1;
        particleDiameter = 1;
        inverseDiameter = 1;
        squaredDiameter = 1;
        count = 0;
        internalAllocatedCapacity = 0;
        maxCount = 0;
        proxyCount = 0;
        proxyCapacity = 0;
        contactCount = 0;
        contactCapacity = 0;
        bodyContactCount = 0;
        bodyContactCapacity = 0;
        pairCount = 0;
        pairCapacity = 0;
        triadCount = 0;
        triadCapacity = 0;
        groupCount = 0;
        pressureStrength = 0.05f;
        dampingStrength = 1.0f;
        elasticStrength = 0.25f;
        springStrength = 0.25f;
        viscousStrength = 0.25f;
        surfaceTensionStrengthA = 0.1f;
        surfaceTensionStrengthB = 0.2f;
        powderStrength = 0.5f;
        ejectionStrength = 0.5f;
        colorMixingStrength = 0.5f;
        flagsBuffer = new ParticleBufferInt();
        positionBuffer = new ParticleBuffer<>(Vec2.class);
        velocityBuffer = new ParticleBuffer<>(Vec2.class);
        colorBuffer = new ParticleBuffer<>(ParticleColor.class);
        userDataBuffer = new ParticleBuffer<>(Object.class);
    }
    // public void assertNotSamePosition() {
    // for (int i = 0; i < count; i++) {
    // Vec2 vi = positionBuffer.data[i];
    // for (int j = i + 1; j < count; j++) {
    // Vec2 vj = positionBuffer.data[j];
    // assert(vi.x != vj.x || vi.y != vj.y);
    // }
    // }
    // }

    public int createParticle(ParticleDef def)
    {
        if (count >= internalAllocatedCapacity)
        {
            int capacity = getCapacity();
            if (internalAllocatedCapacity < capacity)
            {
                flagsBuffer.data = reallocateBuffer(flagsBuffer,
                    internalAllocatedCapacity,
                    capacity,
                    false);
                positionBuffer.data = reallocateBuffer(positionBuffer,
                    internalAllocatedCapacity,
                    capacity,
                    false);
                velocityBuffer.data = reallocateBuffer(velocityBuffer,
                    internalAllocatedCapacity,
                    capacity,
                    false);
                accumulationBuffer = BufferUtils.reallocateBuffer(
                    accumulationBuffer,
                    0,
                    internalAllocatedCapacity,
                    capacity,
                    false);
                accumulation2Buffer = BufferUtils.reallocateBuffer(Vec2.class,
                    accumulation2Buffer,
                    0,
                    internalAllocatedCapacity,
                    capacity,
                    true);
                depthBuffer = BufferUtils.reallocateBuffer(depthBuffer,
                    0,
                    internalAllocatedCapacity,
                    capacity,
                    true);
                colorBuffer.data = reallocateBuffer(colorBuffer,
                    internalAllocatedCapacity,
                    capacity,
                    true);
                groupBuffer = BufferUtils.reallocateBuffer(ParticleGroup.class,
                    groupBuffer,
                    0,
                    internalAllocatedCapacity,
                    capacity,
                    false);
                userDataBuffer.data = reallocateBuffer(userDataBuffer,
                    internalAllocatedCapacity,
                    capacity,
                    true);
                internalAllocatedCapacity = capacity;
            }
        }
        if (count >= internalAllocatedCapacity)
        {
            return Settings.invalidParticleIndex;
        }
        int index = count++;
        flagsBuffer.data[index] = def.flags;
        positionBuffer.data[index].set(def.position);
        // assertNotSamePosition();
        velocityBuffer.data[index].set(def.velocity);
        groupBuffer[index] = null;
        if (depthBuffer != null)
        {
            depthBuffer[index] = 0;
        }
        if (colorBuffer.data != null || def.color != null)
        {
            colorBuffer.data = requestParticleBuffer(colorBuffer.dataClass,
                colorBuffer.data);
            colorBuffer.data[index].set(def.color);
        }
        if (userDataBuffer.data != null || def.userData != null)
        {
            userDataBuffer.data = requestParticleBuffer(
                userDataBuffer.dataClass,
                userDataBuffer.data);
            userDataBuffer.data[index] = def.userData;
        }
        if (proxyCount >= proxyCapacity)
        {
            int oldCapacity = proxyCapacity;
            int newCapacity = proxyCount != 0 ? 2 * proxyCount
                    : Settings.minParticleBufferCapacity;
            proxyBuffer = BufferUtils.reallocateBuffer(Proxy.class,
                proxyBuffer,
                oldCapacity,
                newCapacity);
            proxyCapacity = newCapacity;
        }
        proxyBuffer[proxyCount++].index = index;
        return index;
    }

    private int getCapacity()
    {
        int capacity = count != 0 ? 2 * count
                : Settings.minParticleBufferCapacity;
        capacity = limitCapacity(capacity, maxCount);
        capacity = limitCapacity(capacity, flagsBuffer.userSuppliedCapacity);
        capacity = limitCapacity(capacity, positionBuffer.userSuppliedCapacity);
        capacity = limitCapacity(capacity, velocityBuffer.userSuppliedCapacity);
        capacity = limitCapacity(capacity, colorBuffer.userSuppliedCapacity);
        capacity = limitCapacity(capacity, userDataBuffer.userSuppliedCapacity);
        return capacity;
    }

    public void destroyParticle(int index, boolean callDestructionListener)
    {
        int flags = ParticleType.zombieParticle;
        if (callDestructionListener)
        {
            flags |= ParticleType.destructionListener;
        }
        flagsBuffer.data[index] |= flags;
    }

    private final AABB temp = new AABB();

    private final DestroyParticlesInShapeCallback dpcallback = new DestroyParticlesInShapeCallback();

    public int destroyParticlesInShape(Shape shape, Transform xf,
            boolean callDestructionListener)
    {
        dpcallback.init(this, shape, xf, callDestructionListener);
        shape.computeAABB(temp, xf, 0);
        world.queryAABB(dpcallback, temp);
        return dpcallback.destroyed;
    }

    public void destroyParticlesInGroup(ParticleGroup group,
            boolean callDestructionListener)
    {
        for (int i = group.firstIndex; i < group.lastIndex; i++)
        {
            destroyParticle(i, callDestructionListener);
        }
    }

    private final AABB temp2 = new AABB();

    private final Vec2 tempVec = new Vec2();

    private final Transform tempTransform = new Transform();

    private final Transform tempTransform2 = new Transform();

    private final CreateParticleGroupCallback createParticleGroupCallback = new CreateParticleGroupCallback();

    private final ParticleDef tempParticleDef = new ParticleDef();

    public ParticleGroup createParticleGroup(ParticleGroupDef groupDef)
    {
        float stride = getParticleStride();
        final Transform identity = tempTransform;
        identity.setIdentity();
        Transform transform = tempTransform2;
        transform.setIdentity();
        int firstIndex = count;
        if (groupDef.shape != null)
        {
            final ParticleDef particleDef = tempParticleDef;
            particleDef.flags = groupDef.flags;
            particleDef.color = groupDef.color;
            particleDef.userData = groupDef.userData;
            Shape shape = groupDef.shape;
            transform.set(groupDef.position, groupDef.angle);
            AABB aabb = temp;
            int childCount = shape.getChildCount();
            for (int childIndex = 0; childIndex < childCount; childIndex++)
            {
                if (childIndex == 0)
                {
                    shape.computeAABB(aabb, identity, childIndex);
                }
                else
                {
                    AABB childAABB = temp2;
                    shape.computeAABB(childAABB, identity, childIndex);
                    aabb.combine(childAABB);
                }
            }
            final float upperBoundY = aabb.upperBound.y;
            final float upperBoundX = aabb.upperBound.x;
            for (float y = MathUtils.floor(aabb.lowerBound.y / stride)
                    * stride; y < upperBoundY; y += stride)
            {
                for (float x = MathUtils.floor(aabb.lowerBound.x / stride)
                        * stride; x < upperBoundX; x += stride)
                {
                    Vec2 p = tempVec;
                    p.x = x;
                    p.y = y;
                    if (shape.testPoint(identity, p))
                    {
                        Transform.mulToOut(transform, p, p);
                        particleDef.position.x = p.x;
                        particleDef.position.y = p.y;
                        p.subLocal(groupDef.position);
                        Vec2.crossToOutUnsafe(groupDef.angularVelocity,
                            p,
                            particleDef.velocity);
                        particleDef.velocity.addLocal(groupDef.linearVelocity);
                        createParticle(particleDef);
                    }
                }
            }
        }
        int lastIndex = count;
        ParticleGroup group = new ParticleGroup();
        group.system = this;
        group.firstIndex = firstIndex;
        group.lastIndex = lastIndex;
        group.groupFlags = groupDef.groupFlags;
        group.strength = groupDef.strength;
        group.userData = groupDef.userData;
        group.transform.set(transform);
        group.destroyAutomatically = groupDef.destroyAutomatically;
        group.prev = null;
        group.next = groupList;
        if (groupList != null)
        {
            groupList.prev = group;
        }
        groupList = group;
        ++groupCount;
        for (int i = firstIndex; i < lastIndex; i++)
        {
            groupBuffer[i] = group;
        }
        updateContacts(true);
        if ((groupDef.flags & pairFlags) != 0)
        {
            for (int k = 0; k < contactCount; k++)
            {
                ParticleContact contact = contactBuffer[k];
                int a = contact.indexA;
                int b = contact.indexB;
                if (a > b)
                {
                    int temp = a;
                    a = b;
                    b = temp;
                }
                if (firstIndex <= a && b < lastIndex)
                {
                    if (pairCount >= pairCapacity)
                    {
                        int oldCapacity = pairCapacity;
                        int newCapacity = pairCount != 0 ? 2 * pairCount
                                : Settings.minParticleBufferCapacity;
                        pairBuffer = BufferUtils.reallocateBuffer(Pair.class,
                            pairBuffer,
                            oldCapacity,
                            newCapacity);
                        pairCapacity = newCapacity;
                    }
                    Pair pair = pairBuffer[pairCount];
                    pair.indexA = a;
                    pair.indexB = b;
                    pair.flags = contact.flags;
                    pair.strength = groupDef.strength;
                    pair.distance = MathUtils.distance(positionBuffer.data[a],
                        positionBuffer.data[b]);
                    pairCount++;
                }
            }
        }
        if ((groupDef.flags & triadFlags) != 0)
        {
            VoronoiDiagram diagram = new VoronoiDiagram(lastIndex - firstIndex);
            for (int i = firstIndex; i < lastIndex; i++)
            {
                diagram.addGenerator(positionBuffer.data[i], i);
            }
            diagram.generate(stride / 2);
            createParticleGroupCallback.system = this;
            createParticleGroupCallback.def = groupDef;
            createParticleGroupCallback.firstIndex = firstIndex;
            diagram.getNodes(createParticleGroupCallback);
        }
        if ((groupDef.groupFlags & ParticleGroupType.solidParticleGroup) != 0)
        {
            computeDepthForGroup(group);
        }
        return group;
    }

    public void joinParticleGroups(ParticleGroup groupA, ParticleGroup groupB)
    {
        assert (groupA != groupB);
        RotateBuffer(groupB.firstIndex, groupB.lastIndex, count);
        assert (groupB.lastIndex == count);
        RotateBuffer(groupA.firstIndex, groupA.lastIndex, groupB.firstIndex);
        assert (groupA.lastIndex == groupB.firstIndex);
        int particleFlags = 0;
        for (int i = groupA.firstIndex; i < groupB.lastIndex; i++)
        {
            particleFlags |= flagsBuffer.data[i];
        }
        updateContacts(true);
        if ((particleFlags & pairFlags) != 0)
        {
            for (int k = 0; k < contactCount; k++)
            {
                final ParticleContact contact = contactBuffer[k];
                int a = contact.indexA;
                int b = contact.indexB;
                if (a > b)
                {
                    int temp = a;
                    a = b;
                    b = temp;
                }
                if (groupA.firstIndex <= a && a < groupA.lastIndex
                        && groupB.firstIndex <= b && b < groupB.lastIndex)
                {
                    if (pairCount >= pairCapacity)
                    {
                        int oldCapacity = pairCapacity;
                        int newCapacity = pairCount != 0 ? 2 * pairCount
                                : Settings.minParticleBufferCapacity;
                        pairBuffer = BufferUtils.reallocateBuffer(Pair.class,
                            pairBuffer,
                            oldCapacity,
                            newCapacity);
                        pairCapacity = newCapacity;
                    }
                    Pair pair = pairBuffer[pairCount];
                    pair.indexA = a;
                    pair.indexB = b;
                    pair.flags = contact.flags;
                    pair.strength = MathUtils.min(groupA.strength,
                        groupB.strength);
                    pair.distance = MathUtils.distance(positionBuffer.data[a],
                        positionBuffer.data[b]);
                    pairCount++;
                }
            }
        }
        if ((particleFlags & triadFlags) != 0)
        {
            VoronoiDiagram diagram = new VoronoiDiagram(
                    groupB.lastIndex - groupA.firstIndex);
            for (int i = groupA.firstIndex; i < groupB.lastIndex; i++)
            {
                if ((flagsBuffer.data[i] & ParticleType.zombieParticle) == 0)
                {
                    diagram.addGenerator(positionBuffer.data[i], i);
                }
            }
            diagram.generate(getParticleStride() / 2);
            JoinParticleGroupsCallback callback = new JoinParticleGroupsCallback();
            callback.system = this;
            callback.groupA = groupA;
            callback.groupB = groupB;
            diagram.getNodes(callback);
        }
        for (int i = groupB.firstIndex; i < groupB.lastIndex; i++)
        {
            groupBuffer[i] = groupA;
        }
        int groupFlags = groupA.groupFlags | groupB.groupFlags;
        groupA.groupFlags = groupFlags;
        groupA.lastIndex = groupB.lastIndex;
        groupB.firstIndex = groupB.lastIndex;
        destroyParticleGroup(groupB);
        if ((groupFlags & ParticleGroupType.solidParticleGroup) != 0)
        {
            computeDepthForGroup(groupA);
        }
    }

    // Only called from solveZombie() or joinParticleGroups().
    void destroyParticleGroup(ParticleGroup group)
    {
        assert (groupCount > 0);
        assert (group != null);
        if (world.getParticleDestructionListener() != null)
        {
            world.getParticleDestructionListener().sayGoodbye(group);
        }
        for (int i = group.firstIndex; i < group.lastIndex; i++)
        {
            groupBuffer[i] = null;
        }
        if (group.prev != null)
        {
            group.prev.next = group.next;
        }
        if (group.next != null)
        {
            group.next.prev = group.prev;
        }
        if (group == groupList)
        {
            groupList = group.next;
        }
        --groupCount;
    }

    public void computeDepthForGroup(ParticleGroup group)
    {
        for (int i = group.firstIndex; i < group.lastIndex; i++)
        {
            accumulationBuffer[i] = 0;
        }
        for (int k = 0; k < contactCount; k++)
        {
            final ParticleContact contact = contactBuffer[k];
            int a = contact.indexA;
            int b = contact.indexB;
            if (a >= group.firstIndex && a < group.lastIndex
                    && b >= group.firstIndex && b < group.lastIndex)
            {
                float w = contact.weight;
                accumulationBuffer[a] += w;
                accumulationBuffer[b] += w;
            }
        }
        depthBuffer = requestParticleBuffer(depthBuffer);
        for (int i = group.firstIndex; i < group.lastIndex; i++)
        {
            float w = accumulationBuffer[i];
            depthBuffer[i] = w < 0.8f ? 0 : Float.MAX_VALUE;
        }
        int iterationCount = group.getParticleCount();
        for (int t = 0; t < iterationCount; t++)
        {
            boolean updated = false;
            for (int k = 0; k < contactCount; k++)
            {
                final ParticleContact contact = contactBuffer[k];
                int a = contact.indexA;
                int b = contact.indexB;
                if (a >= group.firstIndex && a < group.lastIndex
                        && b >= group.firstIndex && b < group.lastIndex)
                {
                    float r = 1 - contact.weight;
                    float ap0 = depthBuffer[a];
                    float bp0 = depthBuffer[b];
                    float ap1 = bp0 + r;
                    float bp1 = ap0 + r;
                    if (ap0 > ap1)
                    {
                        depthBuffer[a] = ap1;
                        updated = true;
                    }
                    if (bp0 > bp1)
                    {
                        depthBuffer[b] = bp1;
                        updated = true;
                    }
                }
            }
            if (!updated)
            {
                break;
            }
        }
        for (int i = group.firstIndex; i < group.lastIndex; i++)
        {
            float p = depthBuffer[i];
            if (p < Float.MAX_VALUE)
            {
                depthBuffer[i] *= particleDiameter;
            }
            else
            {
                depthBuffer[i] = 0;
            }
        }
    }

    public void addContact(int a, int b)
    {
        assert (a != b);
        Vec2 pa = positionBuffer.data[a];
        Vec2 pb = positionBuffer.data[b];
        float dx = pb.x - pa.x;
        float dy = pb.y - pa.y;
        float d2 = dx * dx + dy * dy;
        // assert(d2 != 0);
        if (d2 < squaredDiameter)
        {
            if (contactCount >= contactCapacity)
            {
                int oldCapacity = contactCapacity;
                int newCapacity = contactCount != 0 ? 2 * contactCount
                        : Settings.minParticleBufferCapacity;
                contactBuffer = BufferUtils.reallocateBuffer(
                    ParticleContact.class,
                    contactBuffer,
                    oldCapacity,
                    newCapacity);
                contactCapacity = newCapacity;
            }
            float invD = d2 != 0 ? MathUtils.sqrt(1 / d2) : Float.MAX_VALUE;
            ParticleContact contact = contactBuffer[contactCount];
            contact.indexA = a;
            contact.indexB = b;
            contact.flags = flagsBuffer.data[a] | flagsBuffer.data[b];
            contact.weight = 1 - d2 * invD * inverseDiameter;
            contact.normal.x = invD * dx;
            contact.normal.y = invD * dy;
            contactCount++;
        }
    }

    public void updateContacts(boolean exceptZombie)
    {
        for (int p = 0; p < proxyCount; p++)
        {
            Proxy proxy = proxyBuffer[p];
            int i = proxy.index;
            Vec2 pos = positionBuffer.data[i];
            proxy.tag = computeTag(inverseDiameter * pos.x,
                inverseDiameter * pos.y);
        }
        Arrays.sort(proxyBuffer, 0, proxyCount);
        contactCount = 0;
        int c_index = 0;
        for (int i = 0; i < proxyCount; i++)
        {
            Proxy a = proxyBuffer[i];
            long rightTag = computeRelativeTag(a.tag, 1, 0);
            for (int j = i + 1; j < proxyCount; j++)
            {
                Proxy b = proxyBuffer[j];
                if (rightTag < b.tag)
                {
                    break;
                }
                addContact(a.index, b.index);
            }
            long bottomLeftTag = computeRelativeTag(a.tag, -1, 1);
            for (; c_index < proxyCount; c_index++)
            {
                Proxy c = proxyBuffer[c_index];
                if (bottomLeftTag <= c.tag)
                {
                    break;
                }
            }
            long bottomRightTag = computeRelativeTag(a.tag, 1, 1);
            for (int b_index = c_index; b_index < proxyCount; b_index++)
            {
                Proxy b = proxyBuffer[b_index];
                if (bottomRightTag < b.tag)
                {
                    break;
                }
                addContact(a.index, b.index);
            }
        }
        if (exceptZombie)
        {
            int j = contactCount;
            for (int i = 0; i < j; i++)
            {
                if ((contactBuffer[i].flags & ParticleType.zombieParticle) != 0)
                {
                    --j;
                    ParticleContact temp = contactBuffer[j];
                    contactBuffer[j] = contactBuffer[i];
                    contactBuffer[i] = temp;
                    --i;
                }
            }
            contactCount = j;
        }
    }

    private final UpdateBodyContactsCallback ubcCallback = new UpdateBodyContactsCallback();

    /**
     * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2ParticleSystem.cpp#L2608-L2708
     */
    public void updateBodyContacts()
    {
        final AABB aabb = temp;
        aabb.lowerBound.x = Float.MAX_VALUE;
        aabb.lowerBound.y = Float.MAX_VALUE;
        aabb.upperBound.x = -Float.MAX_VALUE;
        aabb.upperBound.y = -Float.MAX_VALUE;
        for (int i = 0; i < count; i++)
        {
            Vec2 p = positionBuffer.data[i];
            Vec2.minToOut(aabb.lowerBound, p, aabb.lowerBound);
            Vec2.maxToOut(aabb.upperBound, p, aabb.upperBound);
        }
        aabb.lowerBound.x -= particleDiameter;
        aabb.lowerBound.y -= particleDiameter;
        aabb.upperBound.x += particleDiameter;
        aabb.upperBound.y += particleDiameter;
        bodyContactCount = 0;
        ubcCallback.system = this;
        world.queryAABB(ubcCallback, aabb);
    }

    private final SolveCollisionCallback scCallback = new SolveCollisionCallback();

    /**
     * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2ParticleSystem.cpp#L2752-L2852
     */
    public void solveCollision(TimeStep step)
    {
        final AABB aabb = temp;
        final Vec2 lowerBound = aabb.lowerBound;
        final Vec2 upperBound = aabb.upperBound;
        lowerBound.x = Float.MAX_VALUE;
        lowerBound.y = Float.MAX_VALUE;
        upperBound.x = -Float.MAX_VALUE;
        upperBound.y = -Float.MAX_VALUE;
        for (int i = 0; i < count; i++)
        {
            final Vec2 v = velocityBuffer.data[i];
            final Vec2 p1 = positionBuffer.data[i];
            final float p1x = p1.x;
            final float p1y = p1.y;
            final float p2x = p1x + step.dt * v.x;
            final float p2y = p1y + step.dt * v.y;
            final float bx = Math.min(p1x, p2x);
            final float by = Math.min(p1y, p2y);
            lowerBound.x = Math.min(lowerBound.x, bx);
            lowerBound.y = Math.min(lowerBound.y, by);
            final float b1x = Math.max(p1x, p2x);
            final float b1y = Math.max(p1y, p2y);
            upperBound.x = Math.max(upperBound.x, b1x);
            upperBound.y = Math.max(upperBound.y, b1y);
        }
        scCallback.step = step;
        scCallback.system = this;
        world.queryAABB(scCallback, aabb);
    }

    /**
     * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2ParticleSystem.cpp#L2973-L3095
     */
    public void solve(TimeStep step)
    {
        ++timestamp;
        if (count == 0)
        {
            return;
        }
        allParticleFlags = 0;
        for (int i = 0; i < count; i++)
        {
            allParticleFlags |= flagsBuffer.data[i];
        }
        if ((allParticleFlags & ParticleType.zombieParticle) != 0)
        {
            solveZombie();
        }
        if (count == 0)
        {
            return;
        }
        allGroupFlags = 0;
        for (ParticleGroup group = groupList; group != null; group = group
            .getNext())
        {
            allGroupFlags |= group.groupFlags;
        }
        final float gravityX = step.dt * gravityScale * world.getGravity().x;
        final float gravityY = step.dt * gravityScale * world.getGravity().y;
        float criticalVelocityYSquared = getCriticalVelocitySquared(step);
        for (int i = 0; i < count; i++)
        {
            Vec2 v = velocityBuffer.data[i];
            v.x += gravityX;
            v.y += gravityY;
            float v2 = v.x * v.x + v.y * v.y;
            if (v2 > criticalVelocityYSquared)
            {
                float a = v2 == 0 ? Float.MAX_VALUE
                        : MathUtils.sqrt(criticalVelocityYSquared / v2);
                v.x *= a;
                v.y *= a;
            }
        }
        solveCollision(step);
        if ((allGroupFlags & ParticleGroupType.rigidParticleGroup) != 0)
        {
            solveRigid(step);
        }
        if ((allParticleFlags & ParticleType.wallParticle) != 0)
        {
            solveWall(step);
        }
        for (int i = 0; i < count; i++)
        {
            Vec2 pos = positionBuffer.data[i];
            Vec2 vel = velocityBuffer.data[i];
            pos.x += step.dt * vel.x;
            pos.y += step.dt * vel.y;
        }
        updateBodyContacts();
        updateContacts(false);
        if ((allParticleFlags & ParticleType.viscousParticle) != 0)
        {
            solveViscous(step);
        }
        if ((allParticleFlags & ParticleType.powderParticle) != 0)
        {
            solvePowder(step);
        }
        if ((allParticleFlags & ParticleType.tensileParticle) != 0)
        {
            solveTensile(step);
        }
        if ((allParticleFlags & ParticleType.elasticParticle) != 0)
        {
            solveElastic(step);
        }
        if ((allParticleFlags & ParticleType.springParticle) != 0)
        {
            solveSpring(step);
        }
        if ((allGroupFlags & ParticleGroupType.solidParticleGroup) != 0)
        {
            solveSolid(step);
        }
        if ((allParticleFlags & ParticleType.colorMixingParticle) != 0)
        {
            solveColorMixing(step);
        }
        solvePressure(step);
        solveDamping(step);
    }

    /**
     * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2ParticleSystem.cpp#L3195-L3258
     */
    void solvePressure(TimeStep step)
    {
        // calculates the sum of contact-weights for each particle
        // that means dimensionless density
        for (int i = 0; i < count; i++)
        {
            accumulationBuffer[i] = 0;
        }
        for (int k = 0; k < bodyContactCount; k++)
        {
            ParticleBodyContact contact = bodyContactBuffer[k];
            int a = contact.index;
            float w = contact.weight;
            accumulationBuffer[a] += w;
        }
        for (int k = 0; k < contactCount; k++)
        {
            ParticleContact contact = contactBuffer[k];
            int a = contact.indexA;
            int b = contact.indexB;
            float w = contact.weight;
            accumulationBuffer[a] += w;
            accumulationBuffer[b] += w;
        }
        // ignores powder particles
        if ((allParticleFlags & noPressureFlags) != 0)
        {
            for (int i = 0; i < count; i++)
            {
                if ((flagsBuffer.data[i] & noPressureFlags) != 0)
                {
                    accumulationBuffer[i] = 0;
                }
            }
        }
        // calculates pressure as a linear function of density
        float pressurePerWeight = pressureStrength * getCriticalPressure(step);
        for (int i = 0; i < count; i++)
        {
            float w = accumulationBuffer[i];
            float h = pressurePerWeight * MathUtils.max(0.0f,
                MathUtils.min(w, Settings.maxParticleWeight)
                        - Settings.minParticleWeight);
            accumulationBuffer[i] = h;
        }
        // applies pressure between each particle in contact
        float velocityPerPressure = step.dt / (density * particleDiameter);
        for (int k = 0; k < bodyContactCount; k++)
        {
            ParticleBodyContact contact = bodyContactBuffer[k];
            int a = contact.index;
            Body b = contact.body;
            float w = contact.weight;
            float m = contact.mass;
            Vec2 n = contact.normal;
            Vec2 p = positionBuffer.data[a];
            float h = accumulationBuffer[a] + pressurePerWeight * w;
            final Vec2 f = tempVec;
            final float coef = velocityPerPressure * w * m * h;
            f.x = coef * n.x;
            f.y = coef * n.y;
            final Vec2 velData = velocityBuffer.data[a];
            final float particleInvMass = getParticleInvMass();
            velData.x -= particleInvMass * f.x;
            velData.y -= particleInvMass * f.y;
            b.applyLinearImpulse(f, p, true);
        }
        for (int k = 0; k < contactCount; k++)
        {
            ParticleContact contact = contactBuffer[k];
            int a = contact.indexA;
            int b = contact.indexB;
            float w = contact.weight;
            Vec2 n = contact.normal;
            float h = accumulationBuffer[a] + accumulationBuffer[b];
            final float fx = velocityPerPressure * w * h * n.x;
            final float fy = velocityPerPressure * w * h * n.y;
            final Vec2 velDataA = velocityBuffer.data[a];
            final Vec2 velDataB = velocityBuffer.data[b];
            velDataA.x -= fx;
            velDataA.y -= fy;
            velDataB.x += fx;
            velDataB.y += fy;
        }
    }

    /**
     * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2ParticleSystem.cpp#L3260-L3304
     */
    void solveDamping(TimeStep step)
    {
        // reduces the normal velocity of each contact
        float damping = dampingStrength;
        for (int k = 0; k < bodyContactCount; k++)
        {
            final ParticleBodyContact contact = bodyContactBuffer[k];
            int a = contact.index;
            Body b = contact.body;
            float w = contact.weight;
            float m = contact.mass;
            Vec2 n = contact.normal;
            Vec2 p = positionBuffer.data[a];
            final float tempX = p.x - b.sweep.c.x;
            final float tempY = p.y - b.sweep.c.y;
            final Vec2 velA = velocityBuffer.data[a];
            // getLinearVelocityFromWorldPointToOut, with -= velA
            float vx = -b.angularVelocity * tempY + b.linearVelocity.x - velA.x;
            float vy = b.angularVelocity * tempX + b.linearVelocity.y - velA.y;
            // done
            float vn = vx * n.x + vy * n.y;
            if (vn < 0)
            {
                final Vec2 f = tempVec;
                f.x = damping * w * m * vn * n.x;
                f.y = damping * w * m * vn * n.y;
                final float invMass = getParticleInvMass();
                velA.x += invMass * f.x;
                velA.y += invMass * f.y;
                f.x = -f.x;
                f.y = -f.y;
                b.applyLinearImpulse(f, p, true);
            }
        }
        for (int k = 0; k < contactCount; k++)
        {
            final ParticleContact contact = contactBuffer[k];
            int a = contact.indexA;
            int b = contact.indexB;
            float w = contact.weight;
            Vec2 n = contact.normal;
            final Vec2 velA = velocityBuffer.data[a];
            final Vec2 velB = velocityBuffer.data[b];
            final float vx = velB.x - velA.x;
            final float vy = velB.y - velA.y;
            float vn = vx * n.x + vy * n.y;
            if (vn < 0)
            {
                float fx = damping * w * vn * n.x;
                float fy = damping * w * vn * n.y;
                velA.x += fx;
                velA.y += fy;
                velB.x -= fx;
                velB.y -= fy;
            }
        }
    }

    /**
     * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2ParticleSystem.cpp#L3506-L3515
     */
    public void solveWall(TimeStep step)
    {
        for (int i = 0; i < count; i++)
        {
            if ((flagsBuffer.data[i] & ParticleType.wallParticle) != 0)
            {
                final Vec2 r = velocityBuffer.data[i];
                r.x = 0.0f;
                r.y = 0.0f;
            }
        }
    }

    private final Vec2 tempVec2 = new Vec2();

    private final Rot tempRot = new Rot();

    private final Transform tempXf = new Transform();

    private final Transform tempXf2 = new Transform();

    /**
     * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2ParticleSystem.cpp#L3517-L3541
     */
    void solveRigid(final TimeStep step)
    {
        for (ParticleGroup group = groupList; group != null; group = group
            .getNext())
        {
            if ((group.groupFlags & ParticleGroupType.rigidParticleGroup) != 0)
            {
                group.updateStatistics();
                Vec2 temp = tempVec;
                Vec2 cross = tempVec2;
                Rot rotation = tempRot;
                rotation.set(step.dt * group.angularVelocity);
                Rot.mulToOutUnsafe(rotation, group.center, cross);
                temp.set(group.linearVelocity)
                    .mulLocal(step.dt)
                    .addLocal(group.center)
                    .subLocal(cross);
                tempXf.p.set(temp);
                tempXf.q.set(rotation);
                Transform.mulToOut(tempXf, group.transform, group.transform);
                final Transform velocityTransform = tempXf2;
                velocityTransform.p.x = step.inverseDt * tempXf.p.x;
                velocityTransform.p.y = step.inverseDt * tempXf.p.y;
                velocityTransform.q.s = step.inverseDt * tempXf.q.s;
                velocityTransform.q.c = step.inverseDt * (tempXf.q.c - 1);
                for (int i = group.firstIndex; i < group.lastIndex; i++)
                {
                    Transform.mulToOutUnsafe(velocityTransform,
                        positionBuffer.data[i],
                        velocityBuffer.data[i]);
                }
            }
        }
    }

    /**
     * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2ParticleSystem.cpp#L3543-L3583
     */
    void solveElastic(final TimeStep step)
    {
        float elasticStrength = step.inverseDt * this.elasticStrength;
        for (int k = 0; k < triadCount; k++)
        {
            final Triad triad = triadBuffer[k];
            if ((triad.flags & ParticleType.elasticParticle) != 0)
            {
                int a = triad.indexA;
                int b = triad.indexB;
                int c = triad.indexC;
                final Vec2 oa = triad.pa;
                final Vec2 ob = triad.pb;
                final Vec2 oc = triad.pc;
                final Vec2 pa = positionBuffer.data[a];
                final Vec2 pb = positionBuffer.data[b];
                final Vec2 pc = positionBuffer.data[c];
                final float px = 1f / 3 * (pa.x + pb.x + pc.x);
                final float py = 1f / 3 * (pa.y + pb.y + pc.y);
                float rs = Vec2.cross(oa, pa) + Vec2.cross(ob, pb)
                        + Vec2.cross(oc, pc);
                float rc = Vec2.dot(oa, pa) + Vec2.dot(ob, pb)
                        + Vec2.dot(oc, pc);
                float r2 = rs * rs + rc * rc;
                float invR = r2 == 0 ? Float.MAX_VALUE
                        : MathUtils.sqrt(1f / r2);
                rs *= invR;
                rc *= invR;
                final float strength = elasticStrength * triad.strength;
                final float roax = rc * oa.x - rs * oa.y;
                final float roay = rs * oa.x + rc * oa.y;
                final float robx = rc * ob.x - rs * ob.y;
                final float roby = rs * ob.x + rc * ob.y;
                final float rocx = rc * oc.x - rs * oc.y;
                final float rocy = rs * oc.x + rc * oc.y;
                final Vec2 va = velocityBuffer.data[a];
                final Vec2 vb = velocityBuffer.data[b];
                final Vec2 vc = velocityBuffer.data[c];
                va.x += strength * (roax - (pa.x - px));
                va.y += strength * (roay - (pa.y - py));
                vb.x += strength * (robx - (pb.x - px));
                vb.y += strength * (roby - (pb.y - py));
                vc.x += strength * (rocx - (pc.x - px));
                vc.y += strength * (rocy - (pc.y - py));
            }
        }
    }

    /**
     * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2ParticleSystem.cpp#L3585-L3610
     */
    void solveSpring(final TimeStep step)
    {
        float springStrength = step.inverseDt * this.springStrength;
        for (int k = 0; k < pairCount; k++)
        {
            final Pair pair = pairBuffer[k];
            if ((pair.flags & ParticleType.springParticle) != 0)
            {
                int a = pair.indexA;
                int b = pair.indexB;
                final Vec2 pa = positionBuffer.data[a];
                final Vec2 pb = positionBuffer.data[b];
                final float dx = pb.x - pa.x;
                final float dy = pb.y - pa.y;
                float r0 = pair.distance;
                float r1 = MathUtils.sqrt(dx * dx + dy * dy);
                if (r1 == 0)
                    r1 = Float.MAX_VALUE;
                float strength = springStrength * pair.strength;
                final float fx = strength * (r0 - r1) / r1 * dx;
                final float fy = strength * (r0 - r1) / r1 * dy;
                final Vec2 va = velocityBuffer.data[a];
                final Vec2 vb = velocityBuffer.data[b];
                va.x -= fx;
                va.y -= fy;
                vb.x += fx;
                vb.y += fy;
            }
        }
    }

    /**
     * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2ParticleSystem.cpp#L3612-L3658
     */
    void solveTensile(final TimeStep step)
    {
        accumulation2Buffer = requestParticleBuffer(Vec2.class,
            accumulation2Buffer);
        for (int i = 0; i < count; i++)
        {
            accumulationBuffer[i] = 0;
            accumulation2Buffer[i].setZero();
        }
        for (int k = 0; k < contactCount; k++)
        {
            final ParticleContact contact = contactBuffer[k];
            if ((contact.flags & ParticleType.tensileParticle) != 0)
            {
                int a = contact.indexA;
                int b = contact.indexB;
                float w = contact.weight;
                Vec2 n = contact.normal;
                accumulationBuffer[a] += w;
                accumulationBuffer[b] += w;
                final Vec2 a2A = accumulation2Buffer[a];
                final Vec2 a2B = accumulation2Buffer[b];
                final float inter = (1 - w) * w;
                a2A.x -= inter * n.x;
                a2A.y -= inter * n.y;
                a2B.x += inter * n.x;
                a2B.y += inter * n.y;
            }
        }
        float strengthA = surfaceTensionStrengthA * getCriticalVelocity(step);
        float strengthB = surfaceTensionStrengthB * getCriticalVelocity(step);
        for (int k = 0; k < contactCount; k++)
        {
            final ParticleContact contact = contactBuffer[k];
            if ((contact.flags & ParticleType.tensileParticle) != 0)
            {
                int a = contact.indexA;
                int b = contact.indexB;
                float w = contact.weight;
                Vec2 n = contact.normal;
                final Vec2 a2A = accumulation2Buffer[a];
                final Vec2 a2B = accumulation2Buffer[b];
                float h = accumulationBuffer[a] + accumulationBuffer[b];
                final float sx = a2B.x - a2A.x;
                final float sy = a2B.y - a2A.y;
                float fn = (strengthA * (h - 2)
                        + strengthB * (sx * n.x + sy * n.y)) * w;
                final float fx = fn * n.x;
                final float fy = fn * n.y;
                final Vec2 va = velocityBuffer.data[a];
                final Vec2 vb = velocityBuffer.data[b];
                va.x -= fx;
                va.y -= fy;
                vb.x += fx;
                vb.y += fy;
            }
        }
    }

    /**
     * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2ParticleSystem.cpp#L3660-L3694
     */
    void solveViscous(final TimeStep step)
    {
        float viscousStrength = this.viscousStrength;
        for (int k = 0; k < bodyContactCount; k++)
        {
            final ParticleBodyContact contact = bodyContactBuffer[k];
            int a = contact.index;
            if ((flagsBuffer.data[a] & ParticleType.viscousParticle) != 0)
            {
                Body b = contact.body;
                float w = contact.weight;
                float m = contact.mass;
                Vec2 p = positionBuffer.data[a];
                final Vec2 va = velocityBuffer.data[a];
                final float tempX = p.x - b.sweep.c.x;
                final float tempY = p.y - b.sweep.c.y;
                final float vx = -b.angularVelocity * tempY + b.linearVelocity.x
                        - va.x;
                final float vy = b.angularVelocity * tempX + b.linearVelocity.y
                        - va.y;
                final Vec2 f = tempVec;
                final float pInvMass = getParticleInvMass();
                f.x = viscousStrength * m * w * vx;
                f.y = viscousStrength * m * w * vy;
                va.x += pInvMass * f.x;
                va.y += pInvMass * f.y;
                f.x = -f.x;
                f.y = -f.y;
                b.applyLinearImpulse(f, p, true);
            }
        }
        for (int k = 0; k < contactCount; k++)
        {
            final ParticleContact contact = contactBuffer[k];
            if ((contact.flags & ParticleType.viscousParticle) != 0)
            {
                int a = contact.indexA;
                int b = contact.indexB;
                float w = contact.weight;
                final Vec2 va = velocityBuffer.data[a];
                final Vec2 vb = velocityBuffer.data[b];
                final float vx = vb.x - va.x;
                final float vy = vb.y - va.y;
                final float fx = viscousStrength * w * vx;
                final float fy = viscousStrength * w * vy;
                va.x += fx;
                va.y += fy;
                vb.x -= fx;
                vb.y -= fy;
            }
        }
    }

    /**
     * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2ParticleSystem.cpp#L3719-L3740
     */
    void solvePowder(final TimeStep step)
    {
        float powderStrength = this.powderStrength * getCriticalVelocity(step);
        float minWeight = 1.0f - Settings.particleStride;
        for (int k = 0; k < bodyContactCount; k++)
        {
            final ParticleBodyContact contact = bodyContactBuffer[k];
            int a = contact.index;
            if ((flagsBuffer.data[a] & ParticleType.powderParticle) != 0)
            {
                float w = contact.weight;
                if (w > minWeight)
                {
                    Body b = contact.body;
                    float m = contact.mass;
                    Vec2 p = positionBuffer.data[a];
                    Vec2 n = contact.normal;
                    final Vec2 f = tempVec;
                    final Vec2 va = velocityBuffer.data[a];
                    final float inter = powderStrength * m * (w - minWeight);
                    final float pInvMass = getParticleInvMass();
                    f.x = inter * n.x;
                    f.y = inter * n.y;
                    va.x -= pInvMass * f.x;
                    va.y -= pInvMass * f.y;
                    b.applyLinearImpulse(f, p, true);
                }
            }
        }
        for (int k = 0; k < contactCount; k++)
        {
            final ParticleContact contact = contactBuffer[k];
            if ((contact.flags & ParticleType.powderParticle) != 0)
            {
                float w = contact.weight;
                if (w > minWeight)
                {
                    int a = contact.indexA;
                    int b = contact.indexB;
                    Vec2 n = contact.normal;
                    final Vec2 va = velocityBuffer.data[a];
                    final Vec2 vb = velocityBuffer.data[b];
                    final float inter = powderStrength * (w - minWeight);
                    final float fx = inter * n.x;
                    final float fy = inter * n.y;
                    va.x -= fx;
                    va.y -= fy;
                    vb.x += fx;
                    vb.y += fy;
                }
            }
        }
    }

    /**
     * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2ParticleSystem.cpp#L3742-L3762
     */
    void solveSolid(final TimeStep step)
    {
        // applies extra repulsive force from solid particle groups
        depthBuffer = requestParticleBuffer(depthBuffer);
        float ejectionStrength = step.inverseDt * this.ejectionStrength;
        for (int k = 0; k < contactCount; k++)
        {
            final ParticleContact contact = contactBuffer[k];
            int a = contact.indexA;
            int b = contact.indexB;
            if (groupBuffer[a] != groupBuffer[b])
            {
                float w = contact.weight;
                Vec2 n = contact.normal;
                float h = depthBuffer[a] + depthBuffer[b];
                final Vec2 va = velocityBuffer.data[a];
                final Vec2 vb = velocityBuffer.data[b];
                final float inter = ejectionStrength * h * w;
                final float fx = inter * n.x;
                final float fy = inter * n.y;
                va.x -= fx;
                va.y -= fy;
                vb.x += fx;
                vb.y += fy;
            }
        }
    }

    /**
     * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2ParticleSystem.cpp#L3774-L3796
     */
    void solveColorMixing(final TimeStep step)
    {
        // mixes color between contacting particles
        colorBuffer.data = requestParticleBuffer(ParticleColor.class,
            colorBuffer.data);
        int colorMixing256 = (int) (256 * colorMixingStrength);
        for (int k = 0; k < contactCount; k++)
        {
            final ParticleContact contact = contactBuffer[k];
            int a = contact.indexA;
            int b = contact.indexB;
            if ((flagsBuffer.data[a] & flagsBuffer.data[b]
                    & ParticleType.colorMixingParticle) != 0)
            {
                ParticleColor colorA = colorBuffer.data[a];
                ParticleColor colorB = colorBuffer.data[b];
                int dr = (colorMixing256
                        * ((colorB.r & 0xFF) - (colorA.r & 0xFF))) >> 8;
                int dg = (colorMixing256
                        * ((colorB.g & 0xFF) - (colorA.g & 0xFF))) >> 8;
                int db = (colorMixing256
                        * ((colorB.b & 0xFF) - (colorA.b & 0xFF))) >> 8;
                int da = (colorMixing256
                        * ((colorB.a & 0xFF) - (colorA.a & 0xFF))) >> 8;
                colorA.r += (byte) dr;
                colorA.g += (byte) dg;
                colorA.b += (byte) db;
                colorA.a += (byte) da;
                colorB.r -= (byte) dr;
                colorB.g -= (byte) dg;
                colorB.b -= (byte) db;
                colorB.a -= (byte) da;
            }
        }
    }

    /**
     * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2ParticleSystem.cpp#L3798-L3892
     */
    void solveZombie()
    {
        // removes particles with a zombie flag
        int newCount = 0;
        int[] newIndices = new int[count];
        for (int i = 0; i < count; i++)
        {
            int flags = flagsBuffer.data[i];
            if ((flags & ParticleType.zombieParticle) != 0)
            {
                ParticleDestructionListener destructionListener = world
                    .getParticleDestructionListener();
                if ((flags & ParticleType.destructionListener) != 0
                        && destructionListener != null)
                {
                    destructionListener.sayGoodbye(i);
                }
                newIndices[i] = Settings.invalidParticleIndex;
            }
            else
            {
                newIndices[i] = newCount;
                if (i != newCount)
                {
                    flagsBuffer.data[newCount] = flagsBuffer.data[i];
                    positionBuffer.data[newCount].set(positionBuffer.data[i]);
                    velocityBuffer.data[newCount].set(velocityBuffer.data[i]);
                    groupBuffer[newCount] = groupBuffer[i];
                    if (depthBuffer != null)
                    {
                        depthBuffer[newCount] = depthBuffer[i];
                    }
                    if (colorBuffer.data != null)
                    {
                        colorBuffer.data[newCount].set(colorBuffer.data[i]);
                    }
                    if (userDataBuffer.data != null)
                    {
                        userDataBuffer.data[newCount] = userDataBuffer.data[i];
                    }
                }
                newCount++;
            }
        }
        // update proxies
        for (int k = 0; k < proxyCount; k++)
        {
            Proxy proxy = proxyBuffer[k];
            proxy.index = newIndices[proxy.index];
        }
        // Proxy lastProxy = std.remove_if(
        // proxyBuffer, proxyBuffer + proxyCount,
        // Test.IsProxyInvalid);
        // proxyCount = (int) (lastProxy - proxyBuffer);
        int j = proxyCount;
        for (int i = 0; i < j; i++)
        {
            if (Test.IsProxyInvalid(proxyBuffer[i]))
            {
                --j;
                Proxy temp = proxyBuffer[j];
                proxyBuffer[j] = proxyBuffer[i];
                proxyBuffer[i] = temp;
                --i;
            }
        }
        proxyCount = j;
        // update contacts
        for (int k = 0; k < contactCount; k++)
        {
            ParticleContact contact = contactBuffer[k];
            contact.indexA = newIndices[contact.indexA];
            contact.indexB = newIndices[contact.indexB];
        }
        // ParticleContact lastContact = std.remove_if(
        // contactBuffer, contactBuffer + contactCount,
        // Test.IsContactInvalid);
        // contactCount = (int) (lastContact - contactBuffer);
        j = contactCount;
        for (int i = 0; i < j; i++)
        {
            if (Test.IsContactInvalid(contactBuffer[i]))
            {
                --j;
                ParticleContact temp = contactBuffer[j];
                contactBuffer[j] = contactBuffer[i];
                contactBuffer[i] = temp;
                --i;
            }
        }
        contactCount = j;
        // update particle-body contacts
        for (int k = 0; k < bodyContactCount; k++)
        {
            ParticleBodyContact contact = bodyContactBuffer[k];
            contact.index = newIndices[contact.index];
        }
        // ParticleBodyContact lastBodyContact = std.remove_if(
        // bodyContactBuffer, bodyContactBuffer + bodyContactCount,
        // Test.IsBodyContactInvalid);
        // bodyContactCount = (int) (lastBodyContact - bodyContactBuffer);
        j = bodyContactCount;
        for (int i = 0; i < j; i++)
        {
            if (Test.IsBodyContactInvalid(bodyContactBuffer[i]))
            {
                --j;
                ParticleBodyContact temp = bodyContactBuffer[j];
                bodyContactBuffer[j] = bodyContactBuffer[i];
                bodyContactBuffer[i] = temp;
                --i;
            }
        }
        bodyContactCount = j;
        // update pairs
        for (int k = 0; k < pairCount; k++)
        {
            Pair pair = pairBuffer[k];
            pair.indexA = newIndices[pair.indexA];
            pair.indexB = newIndices[pair.indexB];
        }
        // Pair lastPair = std.remove_if(pairBuffer, pairBuffer +
        // pairCount, Test.IsPairInvalid);
        // pairCount = (int) (lastPair - pairBuffer);
        j = pairCount;
        for (int i = 0; i < j; i++)
        {
            if (Test.IsPairInvalid(pairBuffer[i]))
            {
                --j;
                Pair temp = pairBuffer[j];
                pairBuffer[j] = pairBuffer[i];
                pairBuffer[i] = temp;
                --i;
            }
        }
        pairCount = j;
        // update triads
        for (int k = 0; k < triadCount; k++)
        {
            Triad triad = triadBuffer[k];
            triad.indexA = newIndices[triad.indexA];
            triad.indexB = newIndices[triad.indexB];
            triad.indexC = newIndices[triad.indexC];
        }
        // Triad lastTriad =
        // std.remove_if(triadBuffer, triadBuffer + triadCount,
        // Test.isTriadInvalid);
        // triadCount = (int) (lastTriad - triadBuffer);
        j = triadCount;
        for (int i = 0; i < j; i++)
        {
            if (Test.IsTriadInvalid(triadBuffer[i]))
            {
                --j;
                Triad temp = triadBuffer[j];
                triadBuffer[j] = triadBuffer[i];
                triadBuffer[i] = temp;
                --i;
            }
        }
        triadCount = j;
        // update groups
        for (ParticleGroup group = groupList; group != null; group = group
            .getNext())
        {
            int firstIndex = newCount;
            int lastIndex = 0;
            boolean modified = false;
            for (int i = group.firstIndex; i < group.lastIndex; i++)
            {
                j = newIndices[i];
                if (j >= 0)
                {
                    firstIndex = MathUtils.min(firstIndex, j);
                    lastIndex = MathUtils.max(lastIndex, j + 1);
                }
                else
                {
                    modified = true;
                }
            }
            if (firstIndex < lastIndex)
            {
                group.firstIndex = firstIndex;
                group.lastIndex = lastIndex;
                if (modified)
                {
                    if ((group.groupFlags
                            & ParticleGroupType.rigidParticleGroup) != 0)
                    {
                        group.toBeSplit = true;
                    }
                }
            }
            else
            {
                group.firstIndex = 0;
                group.lastIndex = 0;
                if (group.destroyAutomatically)
                {
                    group.toBeDestroyed = true;
                }
            }
        }
        // update particle count
        count = newCount;
        // world.stackAllocator.Free(newIndices);
        // destroy bodies with no particles
        for (ParticleGroup group = groupList; group != null;)
        {
            ParticleGroup next = group.getNext();
            if (group.toBeDestroyed)
            {
                destroyParticleGroup(group);
            }
            else if (group.toBeSplit)
            {
                // TODO: split the group
            }
            group = next;
        }
    }

    private static class NewIndices
    {
        int start, mid, end;

        final int getIndex(final int i)
        {
            if (i < start)
            {
                return i;
            }
            else if (i < mid)
            {
                return i + end - mid;
            }
            else if (i < end)
            {
                return i + start - mid;
            }
            else
            {
                return i;
            }
        }
    }

    private final NewIndices newIndices = new NewIndices();

    void RotateBuffer(int start, int mid, int end)
    {
        // move the particles assigned to the given group toward the end of
        // the array
        if (start == mid || mid == end)
        {
            return;
        }
        newIndices.start = start;
        newIndices.mid = mid;
        newIndices.end = end;
        BufferUtils.rotate(flagsBuffer.data, start, mid, end);
        BufferUtils.rotate(positionBuffer.data, start, mid, end);
        BufferUtils.rotate(velocityBuffer.data, start, mid, end);
        BufferUtils.rotate(groupBuffer, start, mid, end);
        if (depthBuffer != null)
        {
            BufferUtils.rotate(depthBuffer, start, mid, end);
        }
        if (colorBuffer.data != null)
        {
            BufferUtils.rotate(colorBuffer.data, start, mid, end);
        }
        if (userDataBuffer.data != null)
        {
            BufferUtils.rotate(userDataBuffer.data, start, mid, end);
        }
        // update proxies
        for (int k = 0; k < proxyCount; k++)
        {
            Proxy proxy = proxyBuffer[k];
            proxy.index = newIndices.getIndex(proxy.index);
        }
        // update contacts
        for (int k = 0; k < contactCount; k++)
        {
            ParticleContact contact = contactBuffer[k];
            contact.indexA = newIndices.getIndex(contact.indexA);
            contact.indexB = newIndices.getIndex(contact.indexB);
        }
        // update particle-body contacts
        for (int k = 0; k < bodyContactCount; k++)
        {
            ParticleBodyContact contact = bodyContactBuffer[k];
            contact.index = newIndices.getIndex(contact.index);
        }
        // update pairs
        for (int k = 0; k < pairCount; k++)
        {
            Pair pair = pairBuffer[k];
            pair.indexA = newIndices.getIndex(pair.indexA);
            pair.indexB = newIndices.getIndex(pair.indexB);
        }
        // update triads
        for (int k = 0; k < triadCount; k++)
        {
            Triad triad = triadBuffer[k];
            triad.indexA = newIndices.getIndex(triad.indexA);
            triad.indexB = newIndices.getIndex(triad.indexB);
            triad.indexC = newIndices.getIndex(triad.indexC);
        }
        // update groups
        for (ParticleGroup group = groupList; group != null; group = group
            .getNext())
        {
            group.firstIndex = newIndices.getIndex(group.firstIndex);
            group.lastIndex = newIndices.getIndex(group.lastIndex - 1) + 1;
        }
    }

    public void setParticleRadius(float radius)
    {
        particleDiameter = 2 * radius;
        squaredDiameter = particleDiameter * particleDiameter;
        inverseDiameter = 1 / particleDiameter;
    }

    public void setParticleDensity(float density)
    {
        this.density = density;
        inverseDensity = 1 / this.density;
    }

    public float getParticleDensity()
    {
        return density;
    }

    public void setParticleGravityScale(float gravityScale)
    {
        this.gravityScale = gravityScale;
    }

    public float getParticleGravityScale()
    {
        return gravityScale;
    }

    public void setParticleDamping(float damping)
    {
        dampingStrength = damping;
    }

    public float getParticleDamping()
    {
        return dampingStrength;
    }

    public float getParticleRadius()
    {
        return particleDiameter / 2;
    }

    float getCriticalVelocity(final TimeStep step)
    {
        return particleDiameter * step.inverseDt;
    }

    float getCriticalVelocitySquared(final TimeStep step)
    {
        float velocity = getCriticalVelocity(step);
        return velocity * velocity;
    }

    float getCriticalPressure(final TimeStep step)
    {
        return density * getCriticalVelocitySquared(step);
    }

    float getParticleStride()
    {
        return Settings.particleStride * particleDiameter;
    }

    float getParticleMass()
    {
        float stride = getParticleStride();
        return density * stride * stride;
    }

    float getParticleInvMass()
    {
        return 1.777777f * inverseDensity * inverseDiameter * inverseDiameter;
    }

    public int[] getParticleFlagsBuffer()
    {
        return flagsBuffer.data;
    }

    public Vec2[] getParticlePositionBuffer()
    {
        return positionBuffer.data;
    }

    public Vec2[] getParticleVelocityBuffer()
    {
        return velocityBuffer.data;
    }

    public ParticleColor[] getParticleColorBuffer()
    {
        colorBuffer.data = requestParticleBuffer(ParticleColor.class,
            colorBuffer.data);
        return colorBuffer.data;
    }

    public Object[] getParticleUserDataBuffer()
    {
        userDataBuffer.data = requestParticleBuffer(Object.class,
            userDataBuffer.data);
        return userDataBuffer.data;
    }

    public int getParticleMaxCount()
    {
        return maxCount;
    }

    public void setParticleMaxCount(int count)
    {
        assert (this.count <= count);
        maxCount = count;
    }

    void setParticleBuffer(ParticleBufferInt buffer, int[] newData,
            int newCapacity)
    {
        assert ((newData != null && newCapacity != 0)
                || (newData == null && newCapacity == 0));
        if (buffer.userSuppliedCapacity != 0)
        {
            // world.blockAllocator.Free(buffer.data, sizeof(T) *
            // internalAllocatedCapacity);
        }
        buffer.data = newData;
        buffer.userSuppliedCapacity = newCapacity;
    }

    <T> void setParticleBuffer(ParticleBuffer<T> buffer, T[] newData,
            int newCapacity)
    {
        assert ((newData != null && newCapacity != 0)
                || (newData == null && newCapacity == 0));
        if (buffer.userSuppliedCapacity != 0)
        {
            // world.blockAllocator.Free(buffer.data, sizeof(T) *
            // internalAllocatedCapacity);
        }
        buffer.data = newData;
        buffer.userSuppliedCapacity = newCapacity;
    }

    public void setParticleFlagsBuffer(int[] buffer, int capacity)
    {
        setParticleBuffer(flagsBuffer, buffer, capacity);
    }

    public void setParticlePositionBuffer(Vec2[] buffer, int capacity)
    {
        setParticleBuffer(positionBuffer, buffer, capacity);
    }

    public void setParticleVelocityBuffer(Vec2[] buffer, int capacity)
    {
        setParticleBuffer(velocityBuffer, buffer, capacity);
    }

    public void setParticleColorBuffer(ParticleColor[] buffer, int capacity)
    {
        setParticleBuffer(colorBuffer, buffer, capacity);
    }

    public ParticleGroup[] getParticleGroupBuffer()
    {
        return groupBuffer;
    }

    public int getParticleGroupCount()
    {
        return groupCount;
    }

    public ParticleGroup[] getParticleGroupList()
    {
        return groupBuffer;
    }

    /**
     * Get the number of particles.
     */
    public int getParticleCount()
    {
        return count;
    }

    public void setParticleUserDataBuffer(Object[] buffer, int capacity)
    {
        setParticleBuffer(userDataBuffer, buffer, capacity);
    }

    private static int lowerBound(Proxy[] ray, int length, long tag)
    {
        int left = 0;
        int step, curr;
        while (length > 0)
        {
            step = length / 2;
            curr = left + step;
            if (ray[curr].tag < tag)
            {
                left = curr + 1;
                length -= step + 1;
            }
            else
            {
                length = step;
            }
        }
        return left;
    }

    private static int upperBound(Proxy[] ray, int length, long tag)
    {
        int left = 0;
        int step, curr;
        while (length > 0)
        {
            step = length / 2;
            curr = left + step;
            if (ray[curr].tag <= tag)
            {
                left = curr + 1;
                length -= step + 1;
            }
            else
            {
                length = step;
            }
        }
        return left;
    }

    /**
     * Query the particle system for all particles that potentially overlap the
     * provided AABB.
     *
     * @param callback A user implemented callback class.
     * @param aabb The query box.
     *
     * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2ParticleSystem.h#L668-L673
     */
    public void queryAABB(ParticleQueryCallback callback, final AABB aabb)
    {
        if (proxyCount == 0)
        {
            return;
        }
        final float lowerBoundX = aabb.lowerBound.x;
        final float lowerBoundY = aabb.lowerBound.y;
        final float upperBoundX = aabb.upperBound.x;
        final float upperBoundY = aabb.upperBound.y;
        int firstProxy = lowerBound(proxyBuffer,
            proxyCount,
            computeTag(inverseDiameter * lowerBoundX,
                inverseDiameter * lowerBoundY));
        int lastProxy = upperBound(proxyBuffer,
            proxyCount,
            computeTag(inverseDiameter * upperBoundX,
                inverseDiameter * upperBoundY));
        for (int proxy = firstProxy; proxy < lastProxy; ++proxy)
        {
            int i = proxyBuffer[proxy].index;
            final Vec2 p = positionBuffer.data[i];
            if (lowerBoundX < p.x && p.x < upperBoundX && lowerBoundY < p.y
                    && p.y < upperBoundY)
            {
                if (!callback.reportParticle(i))
                {
                    break;
                }
            }
        }
    }

    /**
     * Ray-cast the particle system for all particles in the path of the ray.
     * Your callback controls whether you get the closest point, any point, or
     * n-points. The ray-cast ignores particles that contain the starting point.
     *
     * @param callback A user implemented callback class.
     * @param point1 The ray starting point.
     * @param point2 The ray ending point.
     *
     * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2ParticleSystem.h#L684-L692
     */
    public void raycast(ParticleRaycastCallback callback, final Vec2 point1,
            final Vec2 point2)
    {
        if (proxyCount == 0)
        {
            return;
        }
        int firstProxy = lowerBound(proxyBuffer,
            proxyCount,
            computeTag(inverseDiameter * MathUtils.min(point1.x, point2.x) - 1,
                inverseDiameter * MathUtils.min(point1.y, point2.y) - 1));
        int lastProxy = upperBound(proxyBuffer,
            proxyCount,
            computeTag(inverseDiameter * MathUtils.max(point1.x, point2.x) + 1,
                inverseDiameter * MathUtils.max(point1.y, point2.y) + 1));
        float fraction = 1;
        // solving the following equation:
        // ((1-t)*point1+t*point2-position)^2=diameter^2
        // where t is a potential fraction
        final float vx = point2.x - point1.x;
        final float vy = point2.y - point1.y;
        float v2 = vx * vx + vy * vy;
        if (v2 == 0)
            v2 = Float.MAX_VALUE;
        for (int proxy = firstProxy; proxy < lastProxy; ++proxy)
        {
            int i = proxyBuffer[proxy].index;
            final Vec2 posI = positionBuffer.data[i];
            final float px = point1.x - posI.x;
            final float py = point1.y - posI.y;
            float pv = px * vx + py * vy;
            float p2 = px * px + py * py;
            float determinant = pv * pv - v2 * (p2 - squaredDiameter);
            if (determinant >= 0)
            {
                float sqrtDeterminant = MathUtils.sqrt(determinant);
                // find a solution between 0 and fraction
                float t = (-pv - sqrtDeterminant) / v2;
                if (t > fraction)
                {
                    continue;
                }
                if (t < 0)
                {
                    t = (-pv + sqrtDeterminant) / v2;
                    if (t < 0 || t > fraction)
                    {
                        continue;
                    }
                }
                final Vec2 n = tempVec;
                tempVec.x = px + t * vx;
                tempVec.y = py + t * vy;
                n.normalize();
                final Vec2 point = tempVec2;
                point.x = point1.x + t * vx;
                point.y = point1.y + t * vy;
                float f = callback.reportParticle(i, point, n, t);
                fraction = MathUtils.min(fraction, f);
                if (fraction <= 0)
                {
                    break;
                }
            }
        }
    }

    public float computeParticleCollisionEnergy()
    {
        float sumV2 = 0;
        for (int k = 0; k < contactCount; k++)
        {
            final ParticleContact contact = contactBuffer[k];
            int a = contact.indexA;
            int b = contact.indexB;
            Vec2 n = contact.normal;
            final Vec2 va = velocityBuffer.data[a];
            final Vec2 vb = velocityBuffer.data[b];
            final float vx = vb.x - va.x;
            final float vy = vb.y - va.y;
            float vn = vx * n.x + vy * n.y;
            if (vn < 0)
            {
                sumV2 += vn * vn;
            }
        }
        return 0.5f * getParticleMass() * sumV2;
    }

    // reallocate a buffer
    static <T> T[] reallocateBuffer(ParticleBuffer<T> buffer, int oldCapacity,
            int newCapacity, boolean deferred)
    {
        assert (newCapacity > oldCapacity);
        return BufferUtils.reallocateBuffer(buffer.dataClass,
            buffer.data,
            buffer.userSuppliedCapacity,
            oldCapacity,
            newCapacity,
            deferred);
    }

    static int[] reallocateBuffer(ParticleBufferInt buffer, int oldCapacity,
            int newCapacity, boolean deferred)
    {
        assert (newCapacity > oldCapacity);
        return BufferUtils.reallocateBuffer(buffer.data,
            buffer.userSuppliedCapacity,
            oldCapacity,
            newCapacity,
            deferred);
    }

    @SuppressWarnings("unchecked")
    <T> T[] requestParticleBuffer(Class<T> klass, T[] buffer)
    {
        if (buffer == null)
        {
            buffer = (T[]) Array.newInstance(klass, internalAllocatedCapacity);
            for (int i = 0; i < internalAllocatedCapacity; i++)
            {
                try
                {
                    buffer[i] = klass.getDeclaredConstructor().newInstance();
                }
                catch (Exception e)
                {
                    throw new RuntimeException(e);
                }
            }
        }
        return buffer;
    }

    float[] requestParticleBuffer(float[] buffer)
    {
        if (buffer == null)
        {
            buffer = new float[internalAllocatedCapacity];
        }
        return buffer;
    }

    public static class ParticleBuffer<T>
    {
        public T[] data;

        final Class<T> dataClass;

        int userSuppliedCapacity;

        public ParticleBuffer(Class<T> dataClass)
        {
            this.dataClass = dataClass;
        }
    }

    static class ParticleBufferInt
    {
        int[] data;

        int userSuppliedCapacity;
    }

    /** Used for detecting particle contacts */
    public static class Proxy implements Comparable<Proxy>
    {
        int index;

        long tag;

        @Override
        public int compareTo(Proxy o)
        {
            return (tag - o.tag) < 0 ? -1 : (o.tag == tag ? 0 : 1);
        }

        @Override
        public boolean equals(Object obj)
        {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Proxy other = (Proxy) obj;
            return tag == other.tag;
        }
    }

    /**
     * Connection between two particles.
     *
     * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2ParticleSystem.h#L120-L134
     */
    public static class Pair
    {
        /**
         * Index of the respective particle making a pair.
         *
         * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2ParticleSystem.h#L123-L124
         */
        int indexA;

        /**
         * Index of the respective particle making a pair.
         *
         * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2ParticleSystem.h#L123-L124
         */
        int indexB;

        /**
         * The logical sum of the particle flags. See the b2ParticleFlag enum.
         *
         * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2ParticleSystem.h#L126-L127
         */
        int flags;

        /**
         * The strength of cohesion among the particles.
         *
         * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2ParticleSystem.h#L129-L130
         */
        float strength;

        /**
         * The initial distance of the particles.
         *
         * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2ParticleSystem.h#L132-L133
         */
        float distance;
    }

    /**
     * Connection between three particles.
     */
    public static class Triad
    {
        /**
         * Index of the respective particle making triad.
         *
         * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2ParticleSystem.h#L139-L140
         */
        int indexA;

        /**
         * Index of the respective particle making triad.
         *
         * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2ParticleSystem.h#L139-L140
         */
        int indexB;

        /**
         * Index of the respective particle making triad.
         *
         * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2ParticleSystem.h#L139-L140
         */
        int indexC;

        /**
         * The logical sum of the particle flags.
         *
         * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2ParticleSystem.h#L142-L143
         */
        int flags;

        /**
         * The strength of cohesion among the particles.
         *
         * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2ParticleSystem.h#L145-L146
         */
        float strength;

        final Vec2 pa = new Vec2(), pb = new Vec2(), pc = new Vec2();

        float ka, kb, kc, s;
    }

    /**
     * Callback used with VoronoiDiagram.
     */
    static class CreateParticleGroupCallback implements VoronoiDiagramCallback
    {
        public void callback(int a, int b, int c)
        {
            final Vec2 pa = system.positionBuffer.data[a];
            final Vec2 pb = system.positionBuffer.data[b];
            final Vec2 pc = system.positionBuffer.data[c];
            final float dabx = pa.x - pb.x;
            final float daby = pa.y - pb.y;
            final float dbcx = pb.x - pc.x;
            final float dbcy = pb.y - pc.y;
            final float dcax = pc.x - pa.x;
            final float dcay = pc.y - pa.y;
            float maxDistanceSquared = Settings.maxTriadDistanceSquared
                    * system.squaredDiameter;
            if (dabx * dabx + daby * daby < maxDistanceSquared
                    && dbcx * dbcx + dbcy * dbcy < maxDistanceSquared
                    && dcax * dcax + dcay * dcay < maxDistanceSquared)
            {
                if (system.triadCount >= system.triadCapacity)
                {
                    int oldCapacity = system.triadCapacity;
                    int newCapacity = system.triadCount != 0
                            ? 2 * system.triadCount
                            : Settings.minParticleBufferCapacity;
                    system.triadBuffer = BufferUtils.reallocateBuffer(
                        Triad.class,
                        system.triadBuffer,
                        oldCapacity,
                        newCapacity);
                    system.triadCapacity = newCapacity;
                }
                Triad triad = system.triadBuffer[system.triadCount];
                triad.indexA = a;
                triad.indexB = b;
                triad.indexC = c;
                triad.flags = system.flagsBuffer.data[a]
                        | system.flagsBuffer.data[b]
                        | system.flagsBuffer.data[c];
                triad.strength = def.strength;
                final float midPointx = (float) 1 / 3 * (pa.x + pb.x + pc.x);
                final float midPointy = (float) 1 / 3 * (pa.y + pb.y + pc.y);
                triad.pa.x = pa.x - midPointx;
                triad.pa.y = pa.y - midPointy;
                triad.pb.x = pb.x - midPointx;
                triad.pb.y = pb.y - midPointy;
                triad.pc.x = pc.x - midPointx;
                triad.pc.y = pc.y - midPointy;
                triad.ka = -(dcax * dabx + dcay * daby);
                triad.kb = -(dabx * dbcx + daby * dbcy);
                triad.kc = -(dbcx * dcax + dbcy * dcay);
                triad.s = Vec2.cross(pa, pb) + Vec2.cross(pb, pc)
                        + Vec2.cross(pc, pa);
                system.triadCount++;
            }
        }

        ParticleSystem system;

        ParticleGroupDef def; // pointer

        int firstIndex;
    }

    // Callback used with VoronoiDiagram.
    static class JoinParticleGroupsCallback implements VoronoiDiagramCallback
    {
        public void callback(int a, int b, int c)
        {
            // Create a triad if it will contain particles from both groups.
            int countA = ((a < groupB.firstIndex) ? 1 : 0)
                    + ((b < groupB.firstIndex) ? 1 : 0)
                    + ((c < groupB.firstIndex) ? 1 : 0);
            if (countA > 0 && countA < 3)
            {
                int af = system.flagsBuffer.data[a];
                int bf = system.flagsBuffer.data[b];
                int cf = system.flagsBuffer.data[c];
                if ((af & bf & cf & triadFlags) != 0)
                {
                    final Vec2 pa = system.positionBuffer.data[a];
                    final Vec2 pb = system.positionBuffer.data[b];
                    final Vec2 pc = system.positionBuffer.data[c];
                    final float dabx = pa.x - pb.x;
                    final float daby = pa.y - pb.y;
                    final float dbcx = pb.x - pc.x;
                    final float dbcy = pb.y - pc.y;
                    final float dcax = pc.x - pa.x;
                    final float dcay = pc.y - pa.y;
                    float maxDistanceSquared = Settings.maxTriadDistanceSquared
                            * system.squaredDiameter;
                    if (dabx * dabx + daby * daby < maxDistanceSquared
                            && dbcx * dbcx + dbcy * dbcy < maxDistanceSquared
                            && dcax * dcax + dcay * dcay < maxDistanceSquared)
                    {
                        if (system.triadCount >= system.triadCapacity)
                        {
                            int oldCapacity = system.triadCapacity;
                            int newCapacity = system.triadCount != 0
                                    ? 2 * system.triadCount
                                    : Settings.minParticleBufferCapacity;
                            system.triadBuffer = BufferUtils.reallocateBuffer(
                                Triad.class,
                                system.triadBuffer,
                                oldCapacity,
                                newCapacity);
                            system.triadCapacity = newCapacity;
                        }
                        Triad triad = system.triadBuffer[system.triadCount];
                        triad.indexA = a;
                        triad.indexB = b;
                        triad.indexC = c;
                        triad.flags = af | bf | cf;
                        triad.strength = MathUtils.min(groupA.strength,
                            groupB.strength);
                        final float midPointx = (float) 1 / 3
                                * (pa.x + pb.x + pc.x);
                        final float midPointy = (float) 1 / 3
                                * (pa.y + pb.y + pc.y);
                        triad.pa.x = pa.x - midPointx;
                        triad.pa.y = pa.y - midPointy;
                        triad.pb.x = pb.x - midPointx;
                        triad.pb.y = pb.y - midPointy;
                        triad.pc.x = pc.x - midPointx;
                        triad.pc.y = pc.y - midPointy;
                        triad.ka = -(dcax * dabx + dcay * daby);
                        triad.kb = -(dabx * dbcx + daby * dbcy);
                        triad.kc = -(dbcx * dcax + dbcy * dcay);
                        triad.s = Vec2.cross(pa, pb) + Vec2.cross(pb, pc)
                                + Vec2.cross(pc, pa);
                        system.triadCount++;
                    }
                }
            }
        }

        ParticleSystem system;

        ParticleGroup groupA;

        ParticleGroup groupB;
    }

    static class DestroyParticlesInShapeCallback
            implements ParticleQueryCallback
    {
        ParticleSystem system;

        Shape shape;

        Transform xf;

        boolean callDestructionListener;

        int destroyed;

        public DestroyParticlesInShapeCallback()
        {
            // TODO Auto-generated constructor stub
        }

        public void init(ParticleSystem system, Shape shape, Transform xf,
                boolean callDestructionListener)
        {
            this.system = system;
            this.shape = shape;
            this.xf = xf;
            this.destroyed = 0;
            this.callDestructionListener = callDestructionListener;
        }

        @Override
        public boolean reportParticle(int index)
        {
            assert (index >= 0 && index < system.count);
            if (shape.testPoint(xf, system.positionBuffer.data[index]))
            {
                system.destroyParticle(index, callDestructionListener);
                destroyed++;
            }
            return true;
        }
    }

    static class UpdateBodyContactsCallback implements QueryCallback
    {
        ParticleSystem system;

        private final Vec2 tempVec = new Vec2();

        @Override
        public boolean reportFixture(Fixture fixture)
        {
            if (fixture.isSensor())
            {
                return true;
            }
            final Shape shape = fixture.getShape();
            Body b = fixture.getBody();
            Vec2 bp = b.getWorldCenter();
            float bm = b.getMass();
            float bI = b.getInertia() - bm * b.getLocalCenter().lengthSquared();
            float invBm = bm > 0 ? 1 / bm : 0;
            float invBI = bI > 0 ? 1 / bI : 0;
            int childCount = shape.getChildCount();
            for (int childIndex = 0; childIndex < childCount; childIndex++)
            {
                AABB aabb = fixture.getAABB(childIndex);
                final float aabblowerBoundx = aabb.lowerBound.x
                        - system.particleDiameter;
                final float aabblowerBoundy = aabb.lowerBound.y
                        - system.particleDiameter;
                final float aabbupperBoundx = aabb.upperBound.x
                        + system.particleDiameter;
                final float aabbupperBoundy = aabb.upperBound.y
                        + system.particleDiameter;
                int firstProxy = lowerBound(system.proxyBuffer,
                    system.proxyCount,
                    computeTag(system.inverseDiameter * aabblowerBoundx,
                        system.inverseDiameter * aabblowerBoundy));
                int lastProxy = upperBound(system.proxyBuffer,
                    system.proxyCount,
                    computeTag(system.inverseDiameter * aabbupperBoundx,
                        system.inverseDiameter * aabbupperBoundy));
                for (int proxy = firstProxy; proxy != lastProxy; ++proxy)
                {
                    int a = system.proxyBuffer[proxy].index;
                    Vec2 ap = system.positionBuffer.data[a];
                    if (aabblowerBoundx <= ap.x && ap.x <= aabbupperBoundx
                            && aabblowerBoundy <= ap.y
                            && ap.y <= aabbupperBoundy)
                    {
                        float d;
                        final Vec2 n = tempVec;
                        d = fixture.computeDistance(ap, childIndex, n);
                        if (d < system.particleDiameter)
                        {
                            float invAm = (system.flagsBuffer.data[a]
                                    & ParticleType.wallParticle) != 0 ? 0
                                            : system.getParticleInvMass();
                            final float rpx = ap.x - bp.x;
                            final float rpy = ap.y - bp.y;
                            float rpn = rpx * n.y - rpy * n.x;
                            if (system.bodyContactCount >= system.bodyContactCapacity)
                            {
                                int oldCapacity = system.bodyContactCapacity;
                                int newCapacity = system.bodyContactCount != 0
                                        ? 2 * system.bodyContactCount
                                        : Settings.minParticleBufferCapacity;
                                system.bodyContactBuffer = BufferUtils
                                    .reallocateBuffer(ParticleBodyContact.class,
                                        system.bodyContactBuffer,
                                        oldCapacity,
                                        newCapacity);
                                system.bodyContactCapacity = newCapacity;
                            }
                            ParticleBodyContact contact = system.bodyContactBuffer[system.bodyContactCount];
                            contact.index = a;
                            contact.body = b;
                            contact.weight = 1 - d * system.inverseDiameter;
                            contact.normal.x = -n.x;
                            contact.normal.y = -n.y;
                            contact.mass = 1
                                    / (invAm + invBm + invBI * rpn * rpn);
                            system.bodyContactCount++;
                        }
                    }
                }
            }
            return true;
        }
    }

    static class SolveCollisionCallback implements QueryCallback
    {
        ParticleSystem system;

        TimeStep step;

        private final RayCastInput input = new RayCastInput();

        private final RayCastOutput output = new RayCastOutput();

        private final Vec2 tempVec = new Vec2();

        private final Vec2 tempVec2 = new Vec2();

        @Override
        public boolean reportFixture(Fixture fixture)
        {
            if (fixture.isSensor())
            {
                return true;
            }
            final Shape shape = fixture.getShape();
            Body body = fixture.getBody();
            int childCount = shape.getChildCount();
            for (int childIndex = 0; childIndex < childCount; childIndex++)
            {
                AABB aabb = fixture.getAABB(childIndex);
                final float aabblowerBoundx = aabb.lowerBound.x
                        - system.particleDiameter;
                final float aabblowerBoundy = aabb.lowerBound.y
                        - system.particleDiameter;
                final float aabbupperBoundx = aabb.upperBound.x
                        + system.particleDiameter;
                final float aabbupperBoundy = aabb.upperBound.y
                        + system.particleDiameter;
                int firstProxy = lowerBound(system.proxyBuffer,
                    system.proxyCount,
                    computeTag(system.inverseDiameter * aabblowerBoundx,
                        system.inverseDiameter * aabblowerBoundy));
                int lastProxy = upperBound(system.proxyBuffer,
                    system.proxyCount,
                    computeTag(system.inverseDiameter * aabbupperBoundx,
                        system.inverseDiameter * aabbupperBoundy));
                for (int proxy = firstProxy; proxy != lastProxy; ++proxy)
                {
                    int a = system.proxyBuffer[proxy].index;
                    Vec2 ap = system.positionBuffer.data[a];
                    if (aabblowerBoundx <= ap.x && ap.x <= aabbupperBoundx
                            && aabblowerBoundy <= ap.y
                            && ap.y <= aabbupperBoundy)
                    {
                        Vec2 av = system.velocityBuffer.data[a];
                        final Vec2 temp = tempVec;
                        Transform.mulTransToOutUnsafe(body.xf0, ap, temp);
                        Transform.mulToOutUnsafe(body.xf, temp, input.p1);
                        input.p2.x = ap.x + step.dt * av.x;
                        input.p2.y = ap.y + step.dt * av.y;
                        input.maxFraction = 1;
                        if (fixture.raycast(output, input, childIndex))
                        {
                            final Vec2 p = tempVec;
                            p.x = (1 - output.fraction) * input.p1.x
                                    + output.fraction * input.p2.x
                                    + Settings.linearSlop * output.normal.x;
                            p.y = (1 - output.fraction) * input.p1.y
                                    + output.fraction * input.p2.y
                                    + Settings.linearSlop * output.normal.y;
                            final float vx = step.inverseDt * (p.x - ap.x);
                            final float vy = step.inverseDt * (p.y - ap.y);
                            av.x = vx;
                            av.y = vy;
                            final float particleMass = system.getParticleMass();
                            final float ax = particleMass * (av.x - vx);
                            final float ay = particleMass * (av.y - vy);
                            Vec2 b = output.normal;
                            final float fdn = ax * b.x + ay * b.y;
                            final Vec2 f = tempVec2;
                            f.x = fdn * b.x;
                            f.y = fdn * b.y;
                            body.applyLinearImpulse(f, p, true);
                        }
                    }
                }
            }
            return true;
        }
    }

    static class Test
    {
        static boolean IsProxyInvalid(final Proxy proxy)
        {
            return proxy.index < 0;
        }

        static boolean IsContactInvalid(final ParticleContact contact)
        {
            return contact.indexA < 0 || contact.indexB < 0;
        }

        static boolean IsBodyContactInvalid(final ParticleBodyContact contact)
        {
            return contact.index < 0;
        }

        static boolean IsPairInvalid(final Pair pair)
        {
            return pair.indexA < 0 || pair.indexB < 0;
        }

        static boolean IsTriadInvalid(final Triad triad)
        {
            return triad.indexA < 0 || triad.indexB < 0 || triad.indexC < 0;
        }
    }
}
