/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha/src/main/java/ea/internal/physics/WorldHandler.java
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
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.callbacks.QueryCallback;
import org.jbox2d.collision.AABB;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import org.jbox2d.dynamics.contacts.ContactEdge;

import pi.Layer;
import pi.actor.Actor;
import pi.annotations.Getter;
import pi.annotations.Internal;
import pi.annotations.Setter;
import pi.event.CollisionEvent;
import pi.event.CollisionListener;
import pi.physics.joints.Joint;
import pi.physics.joints.JointBuilder;

/**
 * Die WorldHandler-Klasse ist die (nicht objektgebundene) Middleware zwischen
 * der JBox2D-Engine und der Engine Pi.
 *
 * <p>
 * Sie ist verantwortlich für:
 * </p>
 *
 * <ul>
 * <li>Den globalen {@link World}-Parameter aus der JBox2D-Engine.</li>
 * <li>Übersetzung zwischen JB2D-Vektoren (SI-Basiseinheiten) und denen der
 * Engine (Zeichengrößen)</li>
 * </ul>
 */
public class WorldHandler implements ContactListener
{
    public static final int CATEGORY_PASSIVE = 1;

    public static final int CATEGORY_STATIC = 2;

    public static final int CATEGORY_KINEMATIC = 4;

    public static final int CATEGORY_DYNAMIC = 8;

    public static final int CATEGORY_PARTICLE = 16;

    public static final double STEP_TIME = 8f / 1000;

    /**
     * Die Ebene, zu dem der {@link WorldHandler} gehört.
     */
    private final Layer layer;

    /**
     * Gibt an, ob die {@link World} gerade pausiert sind.
     */
    private boolean worldPaused = false;

    /**
     * Die {@link World} dieses Handlers. Hierin laufen globale Einstellungen
     * (z.B. Schwerkraft) ein.
     */
    private final World world;

    /**
     * Alle spezifisch angegebenen Actor-Actor Kollisionsüberwachungen.
     */
    private final Map<Body, List<Checkup<? extends Actor>>> specificCollisionListeners = new ConcurrentHashMap<>();

    /**
     * Sämtliche allgemeinen CollisionListener.
     */
    private final Map<Body, List<CollisionListener<Actor>>> generalCollisonListeners = new ConcurrentHashMap<>();

    /**
     * Diese Liste enthält die (noch nicht beendeten) Kontakte, die nicht
     * aufgelöst werden sollen.
     */
    private final Collection<FixturePair> contactsToIgnore = new ArrayList<>();

    private double simulationAccumulator = 0;

    /**
     * Erstellt eine neue standardisierte Physik ohne Schwerkraft.
     *
     * @hidden
     */
    @Internal
    public WorldHandler(Layer layer)
    {
        this.layer = layer;
        world = new World(new Vec2());
        world.setContactListener(this);
    }

    /**
     * Gibt den World-Parameter der Physics aus.
     *
     * @return Der JB2D-World-Parameter der Welt.
     *
     * @hidden
     */
    @Internal
    @Getter
    public World world()
    {
        return world;
    }

    @Setter
    public void worldPaused(boolean worldPaused)
    {
        this.worldPaused = worldPaused;
    }

    public boolean isWorldPaused()
    {
        return worldPaused;
    }

    /**
     * Stellt sicher, dass sich die (JBox2D-)World <b>nicht</b> gerade im
     * <b>World-Step</b> befindet.
     *
     * <p>
     * Dies ist wichtig für die Manipulation von Actors (Manipulation vieler
     * physikalischen Eigenschaften während des World-Steps führt zu
     * Inkonsistenzen).
     * </p>
     *
     * @throws RuntimeException Wenn die World sich gerade im World-Step
     *     befindet. Ist dies nicht der Fall, passiert nichts (und es wird keine
     *     Exception geworfen).
     *
     * @hidden
     */
    @Internal
    public void assertNoWorldStep()
    {
        if (world().isLocked())
        {
            throw new IllegalStateException(
                    "Die Operation kann nicht während des World-Step ausgeführt werden. "
                            + "Ggf. mit Controller.afterWorldStep wrappen.");
        }
    }

    public void step(double pastTime)
    {
        if (worldPaused)
        {
            return;
        }
        synchronized (this)
        {
            synchronized (world)
            {
                // Wir verwenden konstante Dauer
                // https://gamedev.stackexchange.com/q/86609/38865
                simulationAccumulator += pastTime;
                while (simulationAccumulator >= STEP_TIME)
                {
                    simulationAccumulator -= STEP_TIME;
                    world.step((float) STEP_TIME, 6, 3);
                }
            }
        }
    }

    /**
     * Erstellt einen {@link Body} und mappt ihn intern zum analogen
     * {@link Actor}-Objekt.
     *
     * @param bd Exakte Beschreibung des Bodies.
     * @param actor {@link Actor}-Objekt, das ab sofort zu dem Body gehört.
     *
     * @return Der {@link Body}, der aus der {@link BodyDef} generiert wurde. Er
     *     liegt in der Game-World dieses Handlers.
     */
    public Body createBody(BodyDef bd, Actor actor)
    {
        Body body;
        synchronized (world)
        {
            body = world.createBody(bd);
            body.setUserData(actor);
        }
        return body;
    }

    /**
     * Entfernt alle internen Referenzen auf einen {@link Body Körper} und das
     * zugehörige {@link Actor}-Objekt.
     *
     * @param body Der zu entfernende {@link Body Körper}.
     *
     * @hidden
     */
    @Internal
    public void removeAllInternalReferences(Body body)
    {
        specificCollisionListeners.remove(body);
        generalCollisonListeners.remove(body);
    }

    /**
     * Fügt einen {@link Contact Kontakt} der Blacklist hinzu.
     *
     * <p>
     * {@link Contact Kontakt}e in der Blacklist werden bis zur Trennung nicht
     * aufgelöst. Der Kontakt wird nach endContact wieder entfernt.
     * </p>
     *
     * @hidden
     */
    @Internal
    public void addContactToBlacklist(Contact contact)
    {
        contactsToIgnore
            .add(new FixturePair(contact.fixtureA, contact.fixtureB));
    }

    /* ContactListener interface */

    /**
     * Informationen zu einer Kollision sind in einem {@link Contact}-Objekt
     * enthalten. Es gibt zwei Herangehensweisen, um diese
     * {@link Contact}-Objekte von JBox2D abzurufen:
     *
     * <ul>
     * <li>Über eine Liste: {@link World#getContactList()}</li>
     * <li>Über einen Listener: {@link ContactListener}
     * ({@link World#setContactListener(ContactListener)})</li>
     * </ul>
     *
     * @see <a href=
     *     "https://www.iforce2d.net/b2dtut/collision-anatomy">iforce2d-Tutorial</a>
     */

    /**
     * Broad Phase vs. Narrow Phase
     *
     * <p>
     * Rechenintensive Tests lassen sich durch Begrenzungsvolumen schon einmal
     * minimieren, jedoch muss noch immer jedes Objekt mit jedem getestet
     * werden. Um dieses Problem der vielen potentiellen Kollisionen zu lösen,
     * unterteilt man die Kollisionserkennung in zwei Phasen, die broad Phase
     * (weite/breite Phase) und narrow Phase (engen Phase).
     * </p>
     *
     * https://opus4.kobv.de/opus4-uni-koblenz/frontdoor/deliver/index/docId/907/file/Bachelorarbeit_RigidBody_Physik_Engine_mit_Kollisionserkennung_auf_der_GPU_Daniel_KeAelheim_2015.pdf
     */

    /**
     * Wird aufgerufen, wenn zwei Halterungen ({@link Fixture}) beginnen sich zu
     * berühren.
     *
     * @param contact Der Kontakt zwischen zwei Halterungen ({@link Fixture}. In
     *     der Broad Phase besteht für jedes überlappende AABB ein Kontakt (es
     *     sei denn, es wurde gefiltert). Daher kann es vorkommen, dass ein
     *     Kontakt-Objekt existiert, das keine Kontaktpunkte aufweist.
     */
    @SuppressWarnings("squid:S8491")
    @Override
    public void beginContact(Contact contact)
    {
        processContact(contact, true);
    }

    /**
     * Wird aufgerufen, wenn zwei Halterungen ({@link Fixture} keinen Kontakt
     * mehr haben.
     *
     * @param contact Der Kontakt zwischen zwei Halterungen ({@link Fixture}. In
     *     der Broad Phase besteht für jedes überlappende AABB ein Kontakt (es
     *     sei denn, es wurde gefiltert). Daher kann es vorkommen, dass ein
     *     Kontakt-Objekt existiert, das keine Kontaktpunkte aufweist.
     */
    @Override
    public void endContact(Contact contact)
    {
        processContact(contact, false);
    }

    /**
     * Wird aufgerufen, nachdem ein Kontakt aktualisiert wurde.
     *
     * <p>
     * Dies ermöglicht es Ihnen, einen Kontakt zu inspizieren, bevor er an den
     * Solver übergeben wird. Bei sorgfältiger Vorgehensweise können Sie das
     * Kontakt-Manifold modifizieren (z. B. den Kontakt deaktivieren). Es wird
     * eine Kopie des alten Manifolds bereitgestellt, damit Sie Änderungen
     * erkennen können.
     * </p>
     *
     * <ul>
     *
     * <li>Hinweis: Diese Methode wird nur für aktive (awake) Körper
     * aufgerufen.</li>
     *
     * <li>Hinweis: Diese Methode wird auch dann aufgerufen, wenn die Anzahl der
     * Kontaktpunkte null beträgt.</li>
     *
     * <li>Hinweis: Diese Methode wird nicht für Sensoren aufgerufen.</li>
     *
     * <li>Hinweis: Wenn Sie die Anzahl der Kontaktpunkte auf null setzen,
     * erhalten Sie keinen EndContact-Callback. Möglicherweise erhalten Sie
     * jedoch im nächsten Schritt einen BeginContact-Callback.</li>
     *
     * <li>Hinweis: Der Parameter `oldManifold` wird gepoolt; es handelt sich
     * daher bei jedem Callback innerhalb desselben Threads um dasselbe
     * Objekt.</li>
     * </ul>
     *
     * @param contact Der Kontakt zwischen zwei Halterungen ({@link Fixture}. In
     *     der Broad Phase besteht für jedes überlappende AABB ein Kontakt (es
     *     sei denn, es wurde gefiltert). Daher kann es vorkommen, dass ein
     *     Kontakt-Objekt existiert, das keine Kontaktpunkte aufweist.
     */
    @Override
    public void preSolve(Contact contact, Manifold manifold)
    {
        for (FixturePair ignoredPair : contactsToIgnore)
        {
            if (ignoredPair.matches(contact.fixtureA, contact.fixtureB))
            {
                contact.setEnabled(false);
            }
        }
    }

    /**
     * Wird aufgerufen, nachdem die Kollisionsreaktion berechnet und angewendet
     * wurde.
     *
     * <p>
     * Dies ist nützlich, um Impulse zu überprüfen. Hinweis: Das
     * Kontakt-Manifold enthält nicht die Impulse zum Zeitpunkt des Aufpralls;
     * diese können beliebig groß ausfallen, wenn der Teilschritt klein ist.
     * Daher wird der Impuls explizit in einer separaten Datenstruktur
     * bereitgestellt. Hinweis: Diese Funktion wird nur für Kontakte aufgerufen,
     * die sich berühren, fest und aktiv sind.
     * </p>
     *
     * <p>
     * Der Impule kann beispielsweise verwendet werden, um festzustellen, ob ein
     * Pfeil beim Aufprall im Ziel stecken bleiben soll.
     * </p>
     *
     * @param contact Der Kontakt zwischen zwei Halterungen ({@link Fixture}. In
     *     der Broad Phase besteht für jedes überlappende AABB ein Kontakt (es
     *     sei denn, es wurde gefiltert). Daher kann es vorkommen, dass ein
     *     Kontakt-Objekt existiert, das keine Kontaktpunkte aufweist.
     *
     * @see <a href=
     *     "https://www.iforce2d.net/b2dtut/collision-anatomy">iforce2d-Tutorial</a>
     */
    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse)
    {
        // Wir ignorieren diese Methode
    }

    /**
     * Verarbeitet einen Kontakt in der Physics-Engine.
     *
     * @param contact JBox2D Contact Objekt, das den Contact beschreibt.
     * @param isBegin true = Begin-Kontakt | false = End-Kontakt
     *
     * @hidden
     */
    @Internal
    @SuppressWarnings("squid:S3776")
    private void processContact(final Contact contact, boolean isBegin)
    {
        final Body b1 = contact.getFixtureA().getBody();
        final Body b2 = contact.getFixtureB().getBody();
        if (b1 == b2)
        {
            // Gleicher Body, don't care
            throw new IllegalStateException("Inter-Body Collision!");
        }

        /* TEIL I : Spezifische Checkups */

        // Sortieren der Bodies.
        Body lower;
        Body higher;
        if (b1.hashCode() == b2.hashCode())
        {
            // Hashes sind gleich (blöde Sache!) -> beide Varianten probieren.
            List<Checkup<? extends Actor>> result1 = specificCollisionListeners
                .get(b1);
            if (result1 != null)
            {
                for (Checkup<? extends Actor> c : result1)
                {
                    c.checkCollision(b2, contact, isBegin);
                }
            }
            List<Checkup<? extends Actor>> result2 = specificCollisionListeners
                .get(b2);
            if (result2 != null)
            {
                for (Checkup<? extends Actor> c : result2)
                {
                    c.checkCollision(b1, contact, isBegin);
                }
            }
        }
        else
        {
            if (b1.hashCode() < b2.hashCode())
            {
                // f1 < f2
                lower = b1;
                higher = b2;
            }
            else
            {
                // f1 > f2
                lower = b2;
                higher = b1;
            }
            List<Checkup<? extends Actor>> result = specificCollisionListeners
                .get(lower);
            if (result != null)
            {
                for (Checkup<? extends Actor> c : result)
                {
                    c.checkCollision(higher, contact, isBegin);
                }
            }
        }

        /*
         * ~~~~~~~~~~~~~~~~~~~~~~~ TEIL II : Allgemeine Checkups
         * ~~~~~~~~~~~~~~~~~~~~~~~
         */

        generalCheckup(b1, b2, contact, isBegin);
        generalCheckup(b2, b1, contact, isBegin);

        if (!isBegin)
        {
            // Contact ist beendet -> Set Enabled and remove from blacklist
            contact.setEnabled(true);
            removeFromBlacklist(contact);
        }
    }

    private void removeFromBlacklist(Contact contact)
    {
        FixturePair fixturePair = null;
        for (FixturePair ignoredPair : contactsToIgnore)
        {
            if (ignoredPair.matches(contact.fixtureA, contact.fixtureB))
            {
                fixturePair = ignoredPair;
                break;
            }
        }
        if (fixturePair != null)
        {
            contactsToIgnore.remove(fixturePair);
        }
    }

    /**
     * @hidden
     */
    @Internal
    private void generalCheckup(Body actor, Body colliding, Contact contact,
            final boolean isBegin)
    {
        List<CollisionListener<Actor>> list = generalCollisonListeners
            .get(actor);
        if (list != null)
        {
            Actor other = (Actor) colliding.getUserData();
            if (other == null)
            {
                return; // Is null on async removals
            }
            CollisionEvent<Actor> collisionEvent = new CollisionEvent<>(contact,
                    other);
            for (CollisionListener<Actor> listener : list)
            {
                if (isBegin)
                {
                    listener.onCollision(collisionEvent);
                }
                else
                {
                    listener.onCollisionEnd(collisionEvent);
                }
            }
        }
    }

    @Getter
    public Layer layer()
    {
        return layer;
    }

    /* ____________ On-Request Collision Checkups ____________ */

    /**
     * Durchsucht die Welt nach Halterungen (Fixtures), die sich möglicherweise
     * mit dem angegebenen achsenparallelen Begrenzungsrahmen (AABB:
     * axis-aligned bounding box AABB) überschneiden.
     *
     * @param aabb Der achsenparallele Begrenzungsrahmen (AABB: axis-aligned
     *     bounding box AABB) durch den nach Überschneidungen mit Halterungen
     *     (Fixtures) gesucht werden soll.
     *
     * @return Ein Feld/Array mit Halterungen (Fixtures), die sich
     *     möglicherweise mit dem achsenparallelen Begrenzungsrahmen (AABB:
     *     axis-aligned bounding box AABB) überschneiden.
     *
     * @hidden
     */
    @Internal
    public Fixture[] queryAABB(AABB aabb)
    {
        ArrayList<Fixture> fixtures = new ArrayList<>();
        world.queryAABB((QueryCallback) fixtures::add, aabb);
        return fixtures.toArray(new Fixture[0]);
    }

    /**
     * Prüft, ob zwei {@link Body}-Objekte aktuell miteinander kollidieren.
     *
     * <p>
     * Die Methode durchsucht die Kontaktliste von {@code a} nach einem Kontakt
     * mit {@code b} und liefert nur dann {@code true}, wenn ein entsprechender
     * Kontakt existiert und dieser tatsächlich als berührend
     * ({@code isTouching()}) markiert ist.
     * </p>
     *
     * @param a Der erste zu prüfende Körper.
     * @param b Der zweite zu prüfende Körper.
     *
     * @return {@code true}, wenn beide Körper sich berühren, sonst
     *     {@code false}.
     *
     * @hidden
     */
    @Internal
    public static boolean isBodyCollision(Body a, Body b)
    {
        if (a == null || b == null)
        {
            return false;
        }
        for (ContactEdge contact = a
            .getContactList(); contact != null; contact = contact.next)
        {
            // Es besteht Kontakt zu einem anderen Körper. Prüfe als
            // Nächstes, ob sie sich tatsächlich berühren.
            if (contact.other == b && contact.contact.isTouching())
            {

                return true;
            }
        }
        return false;
    }

    /**
     * Speichert ein korrespondierendes Body-Objekt
     */
    private static class Checkup<E extends Actor>
    {
        private final CollisionListener<E> listener;

        /**
         * Der zweite Body (erster Body ist Hashmap-Schlüssel)
         */
        private final Body body;

        /**
         * Das {@link Actor}-Objekt, das neben dem Actor angemeldet wurde
         */
        private final E collidingActor;

        /**
         * Erstellt das Checkup-Objekt
         *
         * @param listener Das aufzurufende CollisionListener
         * @param body Der zweite Body für den Checkup
         * @param collidingActor Der zugehörige Collider für diesen Checkup
         */
        private Checkup(CollisionListener<E> listener, Body body,
                E collidingActor)
        {
            this.listener = listener;
            this.body = body;
            this.collidingActor = collidingActor;
        }

        public void checkCollision(Body body, Contact contact, boolean isBegin)
        {
            if (this.body == body)
            {
                CollisionEvent<E> collisionEvent = new CollisionEvent<>(contact,
                        collidingActor);
                if (isBegin)
                {
                    listener.onCollision(collisionEvent);
                }
                else
                {
                    listener.onCollisionEnd(collisionEvent);
                }
            }
        }
    }

    /**
     * Meldet einen allgemeinen Kollisionsbeobachter in der Physics-Welt an.
     *
     * @param listener Das anzumeldende Kollisionsbeobachter
     * @param actor Kollisionsbeobachter wird informiert, falls dieses
     *     {@link Actor}-Objekt mit einem anderen Objekt kollidiert.
     *
     * @hidden
     */
    @Internal
    public static void addGenericCollisionListener(
            CollisionListener<Actor> listener, Actor actor)
    {
        actor.addMountListener(() -> {
            Body body = actor.physicsHandler().body();
            if (body == null)
            {
                throw new IllegalStateException(
                        "Body is missing on an Actor with an existing WorldHandler");
            }
            actor.physicsHandler().worldHandler().generalCollisonListeners
                .computeIfAbsent(body, key -> new CopyOnWriteArrayList<>())
                .add(listener);
        });
    }

    /**
     * Meldet ein spezifisches CollisionListener-Interface in dieser
     * Physics-Welt an.
     *
     * @param listener Das anzumeldende KR Interface
     * @param actor Der Actor (Haupt-{@link Actor}-Objekt)
     * @param collider Der Collider (zweites {@link Actor}-Objekt)
     * @param <E> Der Type des Colliders.
     *
     * @hidden
     */
    @Internal
    public static <E extends Actor> void addSpecificCollisionListener(
            Actor actor, E collider, CollisionListener<E> listener)
    {
        addMountListener(actor, collider, worldHandler -> {
            Body b1 = actor.physicsHandler().body();
            Body b2 = collider.physicsHandler().body();
            if (b1 == null || b2 == null)
            {
                throw new IllegalStateException(
                        "Kollision: Eine Figur ohne physikalischen Body wurde zur Kollisionsüberwachung angemeldet.");
            }
            Body lower;
            Body higher;
            if (b1.hashCode() < b2.hashCode())
            {
                lower = b1;
                higher = b2;
            }
            else
            {
                lower = b2;
                higher = b1;
            }
            Checkup<E> checkup = new Checkup<>(listener, higher, collider);
            worldHandler.specificCollisionListeners
                .computeIfAbsent(lower, key -> new CopyOnWriteArrayList<>())
                .add(checkup);
        });
    }

    /**
     * @hidden
     */
    @Internal
    public static <J extends org.jbox2d.dynamics.joints.Joint, W extends Joint<J>> W createJoint(
            Actor a, Actor b, JointBuilder<J> jointBuilder, W wrapper)
    {
        List<Runnable> releaseCallbacks = addMountListener(a,
            b,
            worldHandler -> wrapper
                .joint(jointBuilder.createJoint(worldHandler.world(),
                    a.physicsHandler().body(),
                    b.physicsHandler().body()), worldHandler));
        releaseCallbacks.forEach(wrapper::addReleaseListener);
        return wrapper;
    }

    /**
     * Fügt Mount-Listener für zwei Figuren (Actors) hinzu und führt den
     * Callback aus, sobald beide im selben {@link WorldHandler} gemountet sind.
     *
     * @param a Die erste Figur (Actor)
     * @param b Die zweiter Figur (Actor)
     * @param runnable Callback, der mit dem gemeinsamen {@link WorldHandler}
     *     aufgerufen wird
     *
     * @return Liste mit Cleanup-Runnables zum Entfernen der registrierten
     *     Listener
     *
     * @hidden
     */
    @Internal
    public static List<Runnable> addMountListener(Actor a, Actor b,
            Consumer<WorldHandler> runnable)
    {
        List<Runnable> releases = new ArrayList<>();
        AtomicBoolean skipListener = new AtomicBoolean(true);
        Runnable listenerA = () -> {
            WorldHandler worldHandler = a.physicsHandler().worldHandler();
            if (!skipListener.get() && b.isMounted()
                    && b.physicsHandler().worldHandler() == worldHandler)
            {
                runnable.accept(worldHandler);
            }
        };
        Runnable listenerB = () -> {
            WorldHandler worldHandler = b.physicsHandler().worldHandler();
            if (!skipListener.get() && a.isMounted()
                    && a.physicsHandler().worldHandler() == worldHandler)
            {
                runnable.accept(worldHandler);
            }
        };
        a.addMountListener(listenerA);
        b.addMountListener(listenerB);
        skipListener.set(false);
        releases.add(() -> a.removeMountListener(listenerA));
        releases.add(() -> b.removeMountListener(listenerB));
        if (a.isMounted() && b.isMounted())
        {
            runnable.accept(a.physicsHandler().worldHandler());
        }
        return releases;
    }

    /**
     * Ein ungeordnetes Tupel aus zwei Halterungen (Fixtures).
     *
     * <p>
     * Die Reihenfolge der Halterungen (Fixtures) ist für Vergleiche
     * unerheblich.
     * </p>
     */
    private static class FixturePair
    {
        /**
         * Die erste Halterung (Fixture).
         */
        private final Fixture fixtureA;

        /**
         * Die zweite Halterung (Fixture).
         */
        private final Fixture fixtureB;

        /**
         * Erstellt ein neues Fixture-Tupel.
         *
         * @param fixtureA Die erste Halterung (Fixture).
         * @param fixtureB Die zweite Halterung (Fixture).
         */
        public FixturePair(Fixture fixtureA, Fixture fixtureB)
        {
            this.fixtureA = fixtureA;
            this.fixtureB = fixtureB;
        }

        /**
         * Prüft dieses Fixture-Tupel auf Referenzgleichheit mit einem weiteren.
         *
         * @param otherA Fixture A
         * @param otherB Fixture B
         *
         * @return {@code true}, wenn beide Fixtures referenzgleich sind (in
         *     beliebiger Reihenfolge), sonst {@code false}
         */
        public boolean matches(Fixture otherA, Fixture otherB)
        {
            return (fixtureA == otherA && fixtureB == otherB)
                    || (fixtureA == otherB && fixtureB == otherA);
        }
    }
}
