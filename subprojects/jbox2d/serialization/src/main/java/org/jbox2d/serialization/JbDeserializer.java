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
package org.jbox2d.serialization;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.Joint;

public interface JbDeserializer
{
    /**
     * Sets the object listener, which allows the user to process each physics
     * object with a tag to do any sort of custom logic.
     */
    void setObjectListener(ObjectListener argListener);

    /**
     * Sets a listener for unsupported exceptions instead of stopping the whole
     * deserialization process by throwing and exception.
     */
    void setUnsupportedListener(UnsupportedListener argListener);

    /**
     * Deserializes a world
     *
     * @param input * @throws IOException
     *
     * @throws UnsupportedObjectException if a read physics object is
     *     unsupported by this library
     *
     * @see #setUnsupportedListener(UnsupportedListener)
     */
    World deserializeWorld(InputStream input)
            throws IOException, UnsupportedObjectException;

    /**
     * Deserializes a body
     *
     * @throws UnsupportedObjectException if a read physics object is
     *     unsupported by this library
     *
     * @see #setUnsupportedListener(UnsupportedListener)
     */
    Body deserializeBody(World world, InputStream input)
            throws IOException, UnsupportedObjectException;

    /**
     * Deserializes a fixture
     *
     * @throws UnsupportedObjectException if a read physics object is
     *     unsupported by this library
     *
     * @see #setUnsupportedListener(UnsupportedListener)
     */
    Fixture deserializeFixture(Body body, InputStream input)
            throws IOException, UnsupportedObjectException;

    /**
     * Deserializes a shape
     *
     * @param input * @throws IOException
     *
     * @throws UnsupportedObjectException if a read physics object is
     *     unsupported by this library
     *
     * @see #setUnsupportedListener(UnsupportedListener)
     */
    Shape deserializeShape(InputStream input)
            throws IOException, UnsupportedObjectException;

    /**
     * Deserializes a joint
     *
     * @throws UnsupportedObjectException if a read physics object is
     *     unsupported by this library
     *
     * @see #setUnsupportedListener(UnsupportedListener)
     */
    Joint deserializeJoint(World world, InputStream input,
            Map<Integer, Body> bodyMap, Map<Integer, Joint> jointMap)
            throws IOException, UnsupportedObjectException;

    /**
     * Called for each physics object with a tag defined.
     *
     * @author Daniel Murphy
     *
     */
    interface ObjectListener
    {
        void processWorld(World world, Long tag);

        void processBody(Body body, Long tag);

        void processFixture(Fixture fixture, Long tag);

        void processShape(Shape shape, Long tag);

        void processJoint(Joint joint, Long tag);
    }
}
