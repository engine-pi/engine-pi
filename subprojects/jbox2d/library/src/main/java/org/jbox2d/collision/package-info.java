/**
 * The Collision module contains shapes and functions that operate on them. The
 * module also contains a dynamic tree and broad-phase to acceleration collision
 * processing of large systems.
 *
 * <p>
 * The collision module is designed to be usable outside of the dynamic system.
 * For example, you can use the dynamic tree for other aspects of your game
 * besides physics.
 * </p>
 *
 * <p>
 * However, the main purpose of jbox2d is to provide a rigid body physics
 * engine, so the using the collision module by itself may feel limited for some
 * applications. Likewise, I will not make a strong effort to document it or
 * polish the APIs.
 * </p>
 *
 * https://box2d.org/documentation/md__d_1__git_hub_box2d_docs_collision.html
 */
package org.jbox2d.collision;
