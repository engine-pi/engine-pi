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
package org.jbox2d.dynamics.joints;

import org.jbox2d.dynamics.Body;

/**
 * Joint definitions are used to construct joints.
 *
 * <p>
 * Each joint type has a definition that derives from {@link JointDef}. All
 * joints are connected between two different bodies. One body may static.
 * Joints between static and/or kinematic bodies are allowed, but have no effect
 * and use some processing time.
 * </p>
 *
 * <p>
 * You can specify user data for any joint type and you can provide a flag to
 * prevent the attached bodies from colliding with each other. This is actually
 * the default behavior and you must set the {@link JointDef#collideConnected}
 * Boolean to allow collision between to connected bodies.
 * </p>
 *
 * <p>
 * Many joint definitions require that you provide some geometric data. Often a
 * joint will be defined by anchor points. These are points fixed in the
 * attached bodies. jbox2d requires these points to be specified in local
 * coordinates. This way the joint can be specified even when the current body
 * transforms violate the joint constraint - a common occurrence when a game is
 * saved and reloaded. Additionally, some joint definitions need to know the
 * default relative angle between the bodies. This is necessary to constrain
 * rotation correctly.
 * </p>
 *
 * <p>
 * Initializing the geometric data can be tedious, so many joints have
 * initialization functions that use the current body transforms to remove much
 * of the work. However, these initialization functions should usually only be
 * used for prototyping. Production code should define the geometry directly.
 * This will make joint behavior more robust.
 * </p>
 *
 * <p>
 * The rest of the joint definition data depends on the joint type.
 * </p>
 *
 * https://box2d.org/documentation/md__d_1__git_hub_box2d_docs_dynamics.html#autotoc_md82
 *
 * @author Daniel Murphy
 */
public class JointDef
{
    public JointDef(JointType type)
    {
        this.type = type;
        userData = null;
        bodyA = null;
        bodyB = null;
        collideConnected = false;
    }

    /**
     * The joint type is set automatically for concrete joint types.
     */
    public JointType type;

    /**
     * Use this to attach application specific data to your joints.
     */
    public Object userData;

    /**
     * The first attached body.
     */
    public Body bodyA;

    /**
     * The second attached body.
     */
    public Body bodyB;

    /**
     * Set this flag to true if the attached bodies should collide.
     */
    public boolean collideConnected;
}
