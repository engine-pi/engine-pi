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

import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;

/**
 * A group of particles
 *
 * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2ParticleGroup.h#L172-L287
 */
public class ParticleGroup
{
    ParticleSystem system;

    int firstIndex;

    int lastIndex;

    int groupFlags;

    float strength;

    ParticleGroup prev;

    ParticleGroup next;

    int timestamp;

    float mass;

    float inertia;

    final Vec2 center = new Vec2();

    final Vec2 linearVelocity = new Vec2();

    float angularVelocity;

    final Transform transform = new Transform();

    boolean destroyAutomatically;

    boolean toBeDestroyed;

    boolean toBeSplit;

    /**
     * Use this to store application-specific group data.
     */
    Object userData;

    public ParticleGroup()
    {
        // system = null;
        firstIndex = 0;
        lastIndex = 0;
        groupFlags = 0;
        strength = 1.0f;
        timestamp = -1;
        mass = 0;
        inertia = 0;
        angularVelocity = 0;
        transform.setIdentity();
        destroyAutomatically = true;
        toBeDestroyed = false;
        toBeSplit = false;
    }

    /**
     * Get the next particle group from the list in world.
     *
     * @return The next particle group from the list in world
     */
    public ParticleGroup getNext()
    {
        return next;
    }

    /**
     * Get the number of particles.
     *
     * @return The number of particles.
     *
     * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2ParticleGroup.h#L185-L186
     */
    public int getParticleCount()
    {
        return lastIndex - firstIndex;
    }

    /**
     * Get the offset of this group in the global particle buffer.
     *
     * @return The offset of this group in the global particle buffer.
     *
     * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2ParticleGroup.h#L188-L189
     */
    public int getBufferIndex()
    {
        return firstIndex;
    }

    /**
     * Get the construction flags for the group.
     *
     * @return The construction flags for the group.
     *
     * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2ParticleGroup.h#L197-L198
     */
    public int getGroupFlags()
    {
        return groupFlags;
    }

    /**
     * Set the construction flags for the group.
     *
     * @param flags The construction flags for the group
     *
     * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2ParticleGroup.h#L200-L201
     */
    public void setGroupFlags(int flags)
    {
        groupFlags = flags;
    }

    /**
     * Get the total mass of the group: the sum of all particles in it.
     *
     * @return The total mass of the group: the sum of all particles in it.
     *
     * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2ParticleGroup.h#L203-L204
     */
    public float getMass()
    {
        updateStatistics();
        return mass;
    }

    /**
     * Get the moment of inertia for the group.
     *
     * @return The moment of inertia for the group.
     *
     * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2ParticleGroup.h#L206-L207
     */
    public float getInertia()
    {
        updateStatistics();
        return inertia;
    }

    /**
     * Get the center of gravity for the group.
     *
     * @return The center of gravity for the group.
     *
     * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2ParticleGroup.h#L209-L210
     */
    public Vec2 getCenter()
    {
        updateStatistics();
        return center;
    }

    /**
     * Get the linear velocity of the group.
     *
     * @return The linear velocity of the group.
     *
     * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2ParticleGroup.h#L212-L213
     */
    public Vec2 getLinearVelocity()
    {
        updateStatistics();
        return linearVelocity;
    }

    /**
     * Get the angular velocity of the group.
     *
     * @return The angular velocity of the group.
     *
     * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2ParticleGroup.h#L215-L216
     */
    public float getAngularVelocity()
    {
        updateStatistics();
        return angularVelocity;
    }

    /**
     * Get the position of the group's origin and rotation. Used only with
     * groups of rigid particles.
     *
     * @return The position of the group's origin and rotation.
     *
     * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2ParticleGroup.h#L218-L220
     */
    public Transform getTransform()
    {
        return transform;
    }

    /**
     * Get the position of the particle group as a whole. Used only with groups
     * of rigid particles.
     *
     * @return The position of the particle group as a whole.
     *
     * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2ParticleGroup.h#L222-L224
     */
    public Vec2 getPosition()
    {
        return transform.p;
    }

    /**
     * Get the rotational angle of the particle group as a whole. Used only with
     * groups of rigid particles.
     *
     * @return The rotational angle of the particle group as a whole.
     *
     * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2ParticleGroup.h#L226-L228
     */
    public float getAngle()
    {
        return transform.q.getAngle();
    }

    /**
     * Get the user data pointer that was provided in the group definition.
     *
     * @return The user data pointer that was provided in the group definition.
     *
     * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2ParticleGroup.h#L236-L237
     */
    public Object getUserData()
    {
        return userData;
    }

    /**
     * Set the user data. Use this to store your application specific data.
     *
     * @param data The user data.
     *
     * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2ParticleGroup.h#L239-L240
     */
    public void setUserData(Object data)
    {
        userData = data;
    }

    public void updateStatistics()
    {
        if (timestamp != system.timestamp)
        {
            float m = system.getParticleMass();
            mass = 0;
            center.setZero();
            linearVelocity.setZero();
            for (int i = firstIndex; i < lastIndex; i++)
            {
                mass += m;
                Vec2 pos = system.positionBuffer.data[i];
                center.x += m * pos.x;
                center.y += m * pos.y;
                Vec2 vel = system.velocityBuffer.data[i];
                linearVelocity.x += m * vel.x;
                linearVelocity.y += m * vel.y;
            }
            if (mass > 0)
            {
                center.x *= 1 / mass;
                center.y *= 1 / mass;
                linearVelocity.x *= 1 / mass;
                linearVelocity.y *= 1 / mass;
            }
            inertia = 0;
            angularVelocity = 0;
            for (int i = firstIndex; i < lastIndex; i++)
            {
                Vec2 pos = system.positionBuffer.data[i];
                Vec2 vel = system.velocityBuffer.data[i];
                float px = pos.x - center.x;
                float py = pos.y - center.y;
                float vx = vel.x - linearVelocity.x;
                float vy = vel.y - linearVelocity.y;
                inertia += m * (px * px + py * py);
                angularVelocity += m * (px * vy - py * vx);
            }
            if (inertia > 0)
            {
                angularVelocity *= 1 / inertia;
            }
            timestamp = system.timestamp;
        }
    }
}
