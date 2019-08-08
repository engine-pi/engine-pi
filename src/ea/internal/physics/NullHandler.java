package ea.internal.physics;

import ea.Vector;
import ea.actor.BodyType;
import org.jbox2d.collision.AABB;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Default-Handler für Operationen an Actor-Objekten, die an keiner Scene angehängt sind.
 * Führt alle Operationen rein numerisch durch.
 * Gibt Fehler aus, wenn Operationen ausgeführt werden, die nur mit einer Verbindung zu einer
 * Physics World funktionieren können.
 */
public class NullHandler implements PhysicsHandler {

    private final PhysicsData physicsData;
    private final List<Consumer<PhysicsHandler>> mountCallbacks = new ArrayList<>();

    public NullHandler(PhysicsData physicsData) {
        this.physicsData = physicsData;
    }

    @Override
    public void moveBy(Vector v) {
        this.physicsData.setX(this.physicsData.getX() + v.x);
        this.physicsData.setY(this.physicsData.getY() + v.y);
    }

    @Override
    public Vector getCenter() {
        AABB bounds = null;
        AABB shapeBounds = new AABB();
        Transform transform = new Transform();

        for (Shape shape : physicsData.getShapes().get()) {
            transform.set(getPosition().toVec2(), (float) Math.toRadians(getRotation()));
            shape.computeAABB(shapeBounds, transform, 0);

            if (bounds != null) {
                bounds.combine(shapeBounds);
            } else {
                bounds = new AABB();
                bounds.set(shapeBounds);
            }
        }

        return Vector.of(bounds.getCenter());
    }

    /**
     * Ein Objekt ohne Physik enthält keinen Punkt.
     *
     * @param p Ein Point auf der Zeichenebene.
     *
     * @return false
     */
    @Override
    public boolean contains(Vector p) {
        return false;
    }

    @Override
    public Vector getPosition() {
        return new Vector(this.physicsData.getX(), this.physicsData.getY());
    }

    @Override
    public float getRotation() {
        return this.physicsData.getRotation();
    }

    @Override
    public void rotateBy(float degree) {
        this.physicsData.setRotation(this.physicsData.getRotation() + degree);
    }

    @Override
    public void setRotation(float degree) {
        this.physicsData.setRotation(degree);
    }

    @Override
    public void setDensity(float density) {
        if (density <= 0) {
            throw new IllegalArgumentException("Dichte kann nicht kleiner als 0 sein. Eingabe war " + density + ".");
        }
        this.physicsData.setDensity(density);
    }

    @Override
    public float getDensity() {
        return this.physicsData.getDensity();
    }

    @Override
    public void setFriction(float friction) {
        this.physicsData.setFriction(friction);
    }

    @Override
    public float getFriction() {
        return this.physicsData.getFriction();
    }

    @Override
    public void setRestitution(float elasticity) {
        this.physicsData.setRestitution(elasticity);
    }

    @Override
    public float getRestitution() {
        return this.physicsData.getRestitution();
    }

    @Override
    public void setMass(float mass) {
        physicsData.setMass(mass);
    }

    @Override
    public float getMass() {
        Float mass = physicsData.getMass();
        return mass == null ? 0 : mass;
    }

    @Override
    public void applyForce(Vector force) {
        mountCallbacks.add(physicsHandler -> physicsHandler.applyForce(force));
    }

    @Override
    public void applyTorque(float torque) {
        mountCallbacks.add(physicsHandler -> physicsHandler.applyTorque(torque));
    }

    @Override
    public void applyRotationImpulse(float rotationImpulse) {
        mountCallbacks.add(physicsHandler -> physicsHandler.applyRotationImpulse(rotationImpulse));
    }

    @Override
    public void setType(BodyType type) {
        this.physicsData.setType(type);
    }

    @Override
    public BodyType getType() {
        return physicsData.getType();
    }

    @Override
    public void applyForce(Vector force, Vector globalLocation) {
        mountCallbacks.add(physicsHandler -> physicsHandler.applyForce(force, globalLocation));
    }

    @Override
    public void applyImpluse(Vector impulse, Vector globalLocation) {
        mountCallbacks.add(physicsHandler -> physicsHandler.applyImpluse(impulse, globalLocation));
    }

    @Override
    public WorldHandler getWorldHandler() {
        return null;
    }

    @Override
    public Body getBody() {
        return null;
    }

    @Override
    public void resetMovement() {
        physicsData.setVelocity(new Vec2());
        physicsData.setAngularVelocity(0);
    }

    @Override
    public void setVelocity(Vector metersPerSecond) {
        mountCallbacks.add(physicsHandler -> physicsHandler.setVelocity(metersPerSecond));
    }

    @Override
    public Vector getVelocity() {
        return Vector.NULL;
    }

    @Override
    public void setRotationLocked(boolean locked) {
        this.physicsData.setRotationLocked(locked);
    }

    @Override
    public boolean isRotationLocked() {
        return this.physicsData.isRotationLocked();
    }

    @Override
    public boolean isGrounded() {
        return false;
    }

    @Override
    public float getTorque() {
        return 0;
    }

    @Override
    public void setTorque(float value) {
        mountCallbacks.add(physicsHandler -> physicsHandler.setTorque(value));
    }

    @Override
    public void setShapes(Supplier<List<Shape>> shapes) {
        physicsData.setShapes(shapes);
    }

    @Override
    public PhysicsData getPhysicsData() {
        return this.physicsData;
    }

    @Override
    public void applyMountCallbacks(PhysicsHandler otherHandler) {
        for (Consumer<PhysicsHandler> mountCallback : mountCallbacks) {
            mountCallback.accept(otherHandler);
        }

        mountCallbacks.clear();
    }
}
