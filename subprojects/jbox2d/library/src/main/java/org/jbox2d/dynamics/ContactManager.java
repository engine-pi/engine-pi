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
package org.jbox2d.dynamics;

import org.jbox2d.callbacks.ContactFilter;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.callbacks.PairCallback;
import org.jbox2d.collision.broadphase.BroadPhase;
import org.jbox2d.dynamics.contacts.Contact;
import org.jbox2d.dynamics.contacts.ContactEdge;

/**
 * Delegate of World.
 *
 * @author Daniel Murphy
 */
public class ContactManager implements PairCallback
{
    public BroadPhase broadPhase;

    public Contact contactList;

    public int contactCount;

    public ContactFilter contactFilter;

    public ContactListener contactListener;

    private final World pool;

    public ContactManager(World argPool, BroadPhase broadPhase)
    {
        contactList = null;
        contactCount = 0;
        contactFilter = new ContactFilter();
        contactListener = null;
        this.broadPhase = broadPhase;
        pool = argPool;
    }

    /**
     * Broad-phase callback.
     */
    public void addPair(Object proxyUserDataA, Object proxyUserDataB)
    {
        FixtureProxy proxyA = (FixtureProxy) proxyUserDataA;
        FixtureProxy proxyB = (FixtureProxy) proxyUserDataB;
        Fixture fixtureA = proxyA.fixture;
        Fixture fixtureB = proxyB.fixture;
        int indexA = proxyA.childIndex;
        int indexB = proxyB.childIndex;
        Body bodyA = fixtureA.getBody();
        Body bodyB = fixtureB.getBody();
        // Are the fixtures on the same body?
        if (bodyA == bodyB)
        {
            return;
        }
        // TODO_ERIN use a hash table to remove a potential bottleneck when both
        // bodies have a lot of contacts.
        // Does a contact already exist?
        ContactEdge edge = bodyB.getContactList();
        while (edge != null)
        {
            if (edge.other == bodyA)
            {
                Fixture fA = edge.contact.getFixtureA();
                Fixture fB = edge.contact.getFixtureB();
                int iA = edge.contact.getChildIndexA();
                int iB = edge.contact.getChildIndexB();
                if (fA == fixtureA && iA == indexA && fB == fixtureB
                        && iB == indexB)
                {
                    // A contact already exists.
                    return;
                }
                if (fA == fixtureB && iA == indexB && fB == fixtureA
                        && iB == indexA)
                {
                    // A contact already exists.
                    return;
                }
            }
            edge = edge.next;
        }
        // Does a joint override collision? is at least one body dynamic?
        if (!bodyB.shouldCollide(bodyA))
        {
            return;
        }
        // Check user filtering.
        if (contactFilter != null
                && !contactFilter.shouldCollide(fixtureA, fixtureB))
        {
            return;
        }
        // Call the factory.
        Contact c = pool.popContact(fixtureA, indexA, fixtureB, indexB);
        if (c == null)
        {
            return;
        }
        // Contact creation may swap fixtures.
        fixtureA = c.getFixtureA();
        fixtureB = c.getFixtureB();
        indexA = c.getChildIndexA();
        indexB = c.getChildIndexB();
        bodyA = fixtureA.getBody();
        bodyB = fixtureB.getBody();
        // Insert into the world.
        c.prev = null;
        c.next = contactList;
        if (contactList != null)
        {
            contactList.prev = c;
        }
        contactList = c;
        // Connect to island graph.
        // Connect to body A
        c.nodeA.contact = c;
        c.nodeA.other = bodyB;
        c.nodeA.prev = null;
        c.nodeA.next = bodyA.contactList;
        if (bodyA.contactList != null)
        {
            bodyA.contactList.prev = c.nodeA;
        }
        bodyA.contactList = c.nodeA;
        // Connect to body B
        c.nodeB.contact = c;
        c.nodeB.other = bodyA;
        c.nodeB.prev = null;
        c.nodeB.next = bodyB.contactList;
        if (bodyB.contactList != null)
        {
            bodyB.contactList.prev = c.nodeB;
        }
        bodyB.contactList = c.nodeB;
        // wake up the bodies
        if (!fixtureA.isSensor() && !fixtureB.isSensor())
        {
            bodyA.setAwake(true);
            bodyB.setAwake(true);
        }
        ++contactCount;
    }

    public void findNewContacts()
    {
        broadPhase.updatePairs(this);
    }

    public void destroy(Contact c)
    {
        Fixture fixtureA = c.getFixtureA();
        Fixture fixtureB = c.getFixtureB();
        Body bodyA = fixtureA.getBody();
        Body bodyB = fixtureB.getBody();
        if (contactListener != null && c.isTouching())
        {
            contactListener.endContact(c);
        }
        // Remove from the world.
        if (c.prev != null)
        {
            c.prev.next = c.next;
        }
        if (c.next != null)
        {
            c.next.prev = c.prev;
        }
        if (c == contactList)
        {
            contactList = c.next;
        }
        // Remove from body 1
        if (c.nodeA.prev != null)
        {
            c.nodeA.prev.next = c.nodeA.next;
        }
        if (c.nodeA.next != null)
        {
            c.nodeA.next.prev = c.nodeA.prev;
        }
        if (c.nodeA == bodyA.contactList)
        {
            bodyA.contactList = c.nodeA.next;
        }
        // Remove from body 2
        if (c.nodeB.prev != null)
        {
            c.nodeB.prev.next = c.nodeB.next;
        }
        if (c.nodeB.next != null)
        {
            c.nodeB.next.prev = c.nodeB.prev;
        }
        if (c.nodeB == bodyB.contactList)
        {
            bodyB.contactList = c.nodeB.next;
        }
        // Call the factory.
        pool.pushContact(c);
        --contactCount;
    }

    /**
     * This is the top level collision call for the time step. Here all the
     * narrow phase collision is processed for the world contact list.
     */
    public void collide()
    {
        // Update awake contacts.
        Contact c = contactList;
        while (c != null)
        {
            Fixture fixtureA = c.getFixtureA();
            Fixture fixtureB = c.getFixtureB();
            int indexA = c.getChildIndexA();
            int indexB = c.getChildIndexB();
            Body bodyA = fixtureA.getBody();
            Body bodyB = fixtureB.getBody();
            // is this contact flagged for filtering?
            if ((c.flags & Contact.FILTER_FLAG) == Contact.FILTER_FLAG)
            {
                // Should these bodies collide?
                if (!bodyB.shouldCollide(bodyA))
                {
                    Contact cNuke = c;
                    c = cNuke.getNext();
                    destroy(cNuke);
                    continue;
                }
                // Check user filtering.
                if (contactFilter != null
                        && !contactFilter.shouldCollide(fixtureA, fixtureB))
                {
                    Contact cNuke = c;
                    c = cNuke.getNext();
                    destroy(cNuke);
                    continue;
                }
                // Clear the filtering flag.
                c.flags &= ~Contact.FILTER_FLAG;
            }
            boolean activeA = bodyA.isAwake() && bodyA.type != BodyType.STATIC;
            boolean activeB = bodyB.isAwake() && bodyB.type != BodyType.STATIC;
            // At least one body must be awake, and it must be dynamic or
            // kinematic.
            if (!activeA && !activeB)
            {
                c = c.getNext();
                continue;
            }
            int proxyIdA = fixtureA.proxies[indexA].proxyId;
            int proxyIdB = fixtureB.proxies[indexB].proxyId;
            boolean overlap = broadPhase.testOverlap(proxyIdA, proxyIdB);
            // Here we destroy contacts that cease to overlap in the
            // broad-phase.
            if (!overlap)
            {
                Contact cNuke = c;
                c = cNuke.getNext();
                destroy(cNuke);
                continue;
            }
            // The contact persists.
            c.update(contactListener);
            c = c.getNext();
        }
    }
}
