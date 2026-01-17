/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha/src/main/java/ea/internal/physics/NullHandler.java
 *
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2011 - 2024 Michael Andonie and contributors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package pi.physics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import de.pirckheimer_gymnasium.jbox2d.collision.AABB;
import de.pirckheimer_gymnasium.jbox2d.common.Transform;
import de.pirckheimer_gymnasium.jbox2d.dynamics.Body;
import pi.actor.Actor;
import pi.annotations.Getter;
import pi.annotations.Setter;
import pi.event.CollisionEvent;
import pi.graphics.geom.Vector;

/**
 * Eine Steuerungsklasse für Operationen an {@link Actor}-Objekten, die an
 * keiner Szene angehängt sind. Die Klasse führt alle Operationen rein numerisch
 * durch und gibt Fehler aus, wenn Operationen ausgeführt werden, die nur mit
 * einer Verbindung zur Physics-Engine funktionieren können.
 */
public class NullHandler implements PhysicsHandler
{
    private final PhysicsData physicsData;

    private final Collection<Consumer<PhysicsHandler>> mountCallbacks = new ArrayList<>();

    public NullHandler(PhysicsData physicsData)
    {
        this.physicsData = physicsData;
    }

    @Override
    public void moveBy(Vector v)
    {
        this.physicsData.x(this.physicsData.x() + v.x());
        this.physicsData.y(this.physicsData.y() + v.y());
    }

    @Override
    @Getter
    public Vector center()
    {
        AABB bounds = null;
        AABB shapeBounds = new AABB();
        Transform transform = new Transform();
        for (FixtureData fixtureData : physicsData.fixtures().get())
        {
            transform.set(anchor().toVec2(),
                (float) Math.toRadians(rotation()));
            fixtureData.getShape().computeAABB(shapeBounds, transform, 0);
            if (bounds != null)
            {
                bounds.combine(shapeBounds);
            }
            else
            {
                bounds = new AABB();
                bounds.set(shapeBounds);
            }
        }
        return Vector.of(bounds.getCenter());
    }

    /**
     * Ein Objekt ohne Physik enthält keinen Punkt.
     *
     * @param p Ein Punkt auf der Zeichenebene.
     *
     * @return false
     */
    @Override
    public boolean contains(Vector p)
    {
        return false;
    }

    @Override
    @Getter
    public Vector anchor()
    {
        return new Vector(this.physicsData.x(), this.physicsData.y());
    }

    @Override
    @Getter
    public double rotation()
    {
        return this.physicsData.rotation();
    }

    @Override
    public void rotateBy(double degree)
    {
        this.physicsData.rotation(this.physicsData.rotation() + degree);
    }

    @Override
    @Setter
    public void rotation(double degree)
    {
        this.physicsData.rotation(degree);
    }

    @Override
    @Setter
    public void density(double density)
    {
        if (density <= 0)
        {
            throw new IllegalArgumentException(
                    "Dichte kann nicht kleiner als 0 sein. Eingabe war "
                            + density + ".");
        }
        this.physicsData.globalDensity(density);
    }

    @Override
    @Getter
    public double density()
    {
        return this.physicsData.globalDensity();
    }

    @Override
    @Getter
    public void gravityScale(double factor)
    {
        this.physicsData.gravityScale(factor);
    }

    @Override
    @Getter
    public double gravityScale()
    {
        return this.physicsData.gravityScale();
    }

    @Override
    @Setter
    public void friction(double friction)
    {
        this.physicsData.globalFriction(friction);
    }

    @Override
    @Getter
    public double friction()
    {
        return this.physicsData.globalFriction();
    }

    @Override
    @Setter
    public void restitution(double restitution)
    {
        this.physicsData.globalRestitution(restitution);
    }

    @Override
    @Getter
    public double restitution()
    {
        return this.physicsData.globalRestitution();
    }

    @Override
    @Setter
    public void linearDamping(double damping)
    {
        this.physicsData.linearDamping(damping);
    }

    @Override
    @Getter
    public double linearDamping()
    {
        return physicsData.linearDamping();
    }

    @Override
    @Setter
    public void angularDamping(double damping)
    {
        physicsData.angularDamping(damping);
    }

    @Override
    @Getter
    public double angularDamping()
    {
        return physicsData.angularDamping();
    }

    @Override
    @Getter
    public double mass()
    {
        Double mass = physicsData.mass();
        return mass == null ? 0 : mass;
    }

    @Override
    public void applyForce(Vector force)
    {
        mountCallbacks.add(physicsHandler -> physicsHandler.applyForce(force));
    }

    @Override
    public void applyTorque(double torque)
    {
        mountCallbacks
            .add(physicsHandler -> physicsHandler.applyTorque(torque));
    }

    @Override
    public void applyRotationImpulse(double rotationImpulse)
    {
        mountCallbacks.add(physicsHandler -> physicsHandler
            .applyRotationImpulse(rotationImpulse));
    }

    @Override
    @Setter
    public void bodyType(BodyType type)
    {
        this.physicsData.bodyType(type);
    }

    @Override
    @Getter
    public BodyType bodyType()
    {
        return physicsData.bodyType();
    }

    @Override
    public void applyForce(Vector force, Vector globalLocation)
    {
        mountCallbacks.add(
            physicsHandler -> physicsHandler.applyForce(force, globalLocation));
    }

    @Override
    public void applyImpulse(Vector impulse, Vector globalLocation)
    {
        mountCallbacks.add(physicsHandler -> physicsHandler
            .applyImpulse(impulse, globalLocation));
    }

    @Override
    @Getter
    public WorldHandler worldHandler()
    {
        return null;
    }

    @Override
    @Getter
    public Body body()
    {
        return null;
    }

    @Override
    public void resetMovement()
    {
        physicsData.setVelocity(Vector.NULL);
        physicsData.angularVelocity(0);
    }

    @Override
    @Setter
    public void velocity(Vector metersPerSecond)
    {
        physicsData.setVelocity(metersPerSecond);
    }

    @Override
    @Getter
    public Vector velocity()
    {
        return physicsData.velocity();
    }

    @Override
    @Setter
    public void angularVelocity(double rotationsPerSecond)
    {
        physicsData.angularVelocity(Math.toRadians(rotationsPerSecond * 360));
    }

    @Override
    @Getter
    public double angularVelocity()
    {
        return physicsData.angularVelocity();
    }

    @Override
    @Setter
    public void rotationLocked(boolean locked)
    {
        this.physicsData.rotationLocked(locked);
    }

    @Override
    public boolean isRotationLocked()
    {
        return this.physicsData.isRotationLocked();
    }

    @Override
    public boolean isGrounded()
    {
        return false;
    }

    @Override
    @Setter
    public void fixtures(Supplier<List<FixtureData>> shapes)
    {
        physicsData.fixtures(shapes);
    }

    @Override
    @Getter
    public PhysicsData physicsData()
    {
        return this.physicsData;
    }

    @Override
    public void applyMountCallbacks(PhysicsHandler otherHandler)
    {
        for (Consumer<PhysicsHandler> mountCallback : mountCallbacks)
        {
            mountCallback.accept(otherHandler);
        }
        mountCallbacks.clear();
    }

    @Override
    @Getter
    public List<CollisionEvent<Actor>> collisions()
    {
        return Collections.emptyList();
    }

    /**
     * Legt den Schlafzustand des Körpers fest. Ein schlafender Körper hat sehr
     * geringe CPU-Kosten.
     *
     * <p>
     * Das Versetzen in den Schlafzustand setzt die Geschwindigkeit und den
     * Impuls eines Körpers auf null.
     * </p>
     *
     * @param value Der Schlafzustand des Körpers.
     */
    @Override
    @Setter
    public void awake(boolean value)
    {
        // Mache nichts
    }
}
