/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha/src/main/java/ea/collision/CollisionEvent.java
 *
 * Engine Omega ist eine anfängerorientierte 2D-Gaming Engine.
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
package rocks.friedrich.engine_omega.event;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.jbox2d.collision.WorldManifold;
import org.jbox2d.dynamics.contacts.Contact;

import rocks.friedrich.engine_omega.Vector;
import rocks.friedrich.engine_omega.actor.Actor;
import rocks.friedrich.engine_omega.annotations.API;
import rocks.friedrich.engine_omega.annotations.Internal;

/**
 * Ein Objekt der Klasse {@link CollisionEvent} repräsentiert eine <b>Kollision
 * zwischen zwei {@link Actor}-Objekten</b>. Nur {@link Actor}-Objekte, mit
 * denen ein CollisionListener verknüpft sind, generieren
 * {@link CollisionEvent}s.
 *
 * <p>
 * Das {@link CollisionEvent} wird verwendet als
 *
 * <ul>
 *
 * <li><b>Angabe des Kollisionspartners</b>: In der Engine ist eines der beiden
 * {@link Actor}-Objekte des Aufpralls implizit bestimmt dadurch, dass der
 * {@link CollisionListener} an dem entsprechenden {@link Actor}-Objekt
 * <b>angemeldet</b> werden musste. Das hiermit kollidierende Objekt ist im
 * Event angegeben.</li>
 *
 * <li><b>Ausführliche Informationsquelle</b>: Hierüber sind Informationen zur
 * Kollision erhältlich, z.B. über die Härte des Aufpralls.</li>
 *
 * <li><b>Kontrolle der Kollisionsauflösung</b>: Der Nutzer kann entscheiden, ob
 * die Kollision aufgelöst werden soll oder ignoriert werden soll. Hiermit
 * lassen sich zum Beispiel einseitige Sperren/Wände umsetzen.</li>
 *
 * </ul>
 *
 * @param <E> Typ des anderen Objekts bei Kollisionen.
 * @see CollisionListener
 * @see <a href="http://www.iforce2d.net/b2dtut/collision-anatomy" target=
 *      "_top">http://www.iforce2d.net/b2dtut/collision-anatomy</a>
 */
public class CollisionEvent<E extends Actor>
{
    private static final ThreadLocal<WorldManifold> worldManifold = ThreadLocal
            .withInitial(WorldManifold::new);

    /**
     * Der JBox2D-Contact. Zur Manipulation der Kollision und zur Abfrage.
     */
    private final Contact contact;

    /**
     * Das kollidierende {@link Actor}-Objekt.
     */
    private final E colliding;

    /**
     * Konstruktor. Erstellt ein Collision-Event.
     *
     * @param contact   Das JBox2D-Contact-Objekt zur direkten Manipulation der
     *                  Kollisionsauflösung (und zur Abfrage von Informationen).
     * @param colliding Das kollidierende {@link Actor}-Objekt. Das zweite
     *                  Objekt der Kollision ist implizit durch die Anmeldung am
     *                  entsprechenden Actor gegeben.
     */
    @Internal
    public CollisionEvent(Contact contact, E colliding)
    {
        this.contact = contact;
        this.colliding = colliding;
    }

    /**
     * Gibt das {@link Actor}-Objekt aus, dass mit dem {@link Actor} kollidiert,
     * an dem der Listener angemeldet wurde.
     *
     * @return Das kollidierende {@link Actor}-Objekt. Das zweite Objekt der
     *         Kollision ist implizit durch die Anmeldung am entsprechenden
     *         {@link Actor} gegeben.
     */
    @API
    public E getColliding()
    {
        return colliding;
    }

    /**
     * Wenn diese Methode aufgerufen wird, wird diese Kollision <b>nicht von der
     * Physics-Engine</b> aufgelöst, sondern ignoriert.
     *
     * <p>
     * Dies lässt sich Nutzen zum Beispiel für:
     * <ul>
     *
     * <li>Feste Plattformen, durch die man von unten „durchspringen“ kann, um
     * so von unten auf sie drauf zu springen.</li>
     *
     * <li>Einbahn-Sperren, die nur auf einer Seite durchlässig sind.</li>
     *
     * <li>Gegner, die nicht miteinander kollidieren sollen, sondern nur mit dem
     * Spielcharakter.</li>
     * </ul>
     */
    @API
    public void ignoreCollision()
    {
        contact.setEnabled(false);
        colliding.getPhysicsHandler().getWorldHandler()
                .addContactToBlacklist(contact);
    }
    /*
     * @API public double getTangentSpeed() { return contact.getTangentSpeed();
     * // TODO Check how this works, currently returns always 0.0 }
     */

    @API
    public Vector getTangentNormal()
    {
        WorldManifold worldManifold = CollisionEvent.worldManifold.get();
        contact.getWorldManifold(worldManifold);
        Vector normal = Vector.of(worldManifold.normal);
        if (contact.m_fixtureA.getBody().getUserData() == colliding)
        {
            normal = normal.negate();
        }
        return normal;
    }

    @API
    public List<Vector> getPoints()
    {
        WorldManifold worldManifold = CollisionEvent.worldManifold.get();
        contact.getWorldManifold(worldManifold);
        int pointCount = contact.getManifold().pointCount;
        if (pointCount == 0)
        {
            return Collections.emptyList();
        }
        else if (pointCount == 1)
        {
            return Collections
                    .singletonList(Vector.of(worldManifold.points[0]));
        }
        else if (pointCount == 2)
        {
            return Arrays.asList(Vector.of(worldManifold.points[0]),
                    Vector.of(worldManifold.points[1]));
        }
        else
        {
            throw new IllegalStateException(
                    "Invalid contact point count: " + pointCount);
        }
    }

    public boolean isIgnored()
    {
        return !contact.isEnabled();
    }
}
