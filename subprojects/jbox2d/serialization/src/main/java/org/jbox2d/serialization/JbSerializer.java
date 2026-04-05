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

import java.util.Map;

import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.Joint;

/**
 * Serializer for jbox2d, used to serialize any aspect of the physics world
 *
 * @author Daniel Murphy
 *
 */
public interface JbSerializer
{
    /**
     * Sets the object signer for the serializer. This allows the user to
     * specify a 'tag' for each main physics object, which is then referenced
     * later at deserialization for the user.
     */
    void setObjectSigner(ObjectSigner signer);

    /**
     * Sets a listener for unsupported exception instead of stopping the whole
     * serialization process by throwing and exception.
     */
    void setUnsupportedListener(UnsupportedListener listener);

    /**
     * Serializes the world
     *
     * @param world * @throws UnsupportedObjectException if a physics object is
     *     unsupported by this library.
     *
     * @see #setUnsupportedListener(UnsupportedListener)
     */
    SerializationResult serialize(World world)
            throws UnsupportedObjectException;

    /**
     * Serializes a body
     *
     * @param body * @throws UnsupportedObjectException if a physics object is
     *     unsupported by this library.
     *
     * @see #setUnsupportedListener(UnsupportedListener)
     */
    SerializationResult serialize(Body body) throws UnsupportedObjectException;

    /**
     * Serializes a fixture
     *
     * @param fixture * @throws UnsupportedObjectException if a physics object
     *     is unsupported by this library.
     *
     * @see #setUnsupportedListener(UnsupportedListener)
     */
    SerializationResult serialize(Fixture fixture)
            throws UnsupportedObjectException;

    /**
     * Serializes a shape
     *
     * @param shape * @throws UnsupportedObjectException if a physics object is
     *     unsupported by this library.
     *
     * @see #setUnsupportedListener(UnsupportedListener)
     */
    SerializationResult serialize(Shape shape)
            throws UnsupportedObjectException;

    /**
     * Serializes joints. Joints need to reference bodies and sometimes other
     * joints.
     */
    SerializationResult serialize(Joint joint, Map<Body, Integer> bodyIndexMap,
            Map<Joint, Integer> jointIndexMap);

    /**
     * Interface that allows the serializer to look up tags for each object,
     * which can be used later during deserializing by the developer.
     *
     * @author Daniel Murphy
     */
    interface ObjectSigner
    {
        /**
         * @return The tag for the world. Can be null.
         */
        Long getTag(World world);

        /**
         * @return The tag for the body. Can be null.
         */
        Long getTag(Body body);

        /**
         * @return The tag for the shape. Can be null.
         */
        Long getTag(Shape shape);

        /**
         * @return The tag for the fixture. Can be null.
         */
        Long getTag(Fixture fixture);

        /**
         * @return The tag for the joint. Can be null.
         */
        Long getTag(Joint joint);
    }
}
