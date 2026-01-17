/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha/src/main/java/ea/event/EventListeners.java
 *
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2011 - 2019 Michael Andonie and contributors.
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
package pi.event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.function.Consumer;
import java.util.function.Supplier;

import pi.annotations.API;

/**
 * Verwaltet mehrere Beobachter (Listeners).
 *
 * <p>
 * Die Klasse stellt Methoden bereit, um Beobachter an- ({@link #add(Object)})
 * und abzumelden ({@link #remove(Object)}).
 * </p>
 */
public final class EventListeners<T>
{
    private final Collection<T> listeners = new LinkedHashSet<>();

    private final Collection<T> listenerIterationCopy = new LinkedHashSet<>();

    private final Collection<Runnable> pendingCopyModifications = new ArrayList<>();

    private final Supplier<EventListeners<T>> parentSupplier;

    private boolean iterating = false;

    public EventListeners()
    {
        this(() -> null);
    }

    public EventListeners(Supplier<EventListeners<T>> parentSupplier)
    {
        this.parentSupplier = parentSupplier;
    }

    /**
     * Meldet einen Beobachter an.
     *
     * @param listener Der Beobachter, der angemeldet werden soll.
     */
    @API
    public synchronized void add(T listener)
    {
        listeners.add(listener);
        if (iterating)
        {
            pendingCopyModifications
                .add(() -> listenerIterationCopy.add(listener));
        }
        else
        {
            listenerIterationCopy.add(listener);
        }
        EventListeners<T> parent = parentSupplier.get();
        if (parent != null)
        {
            parent.add(listener);
        }
    }

    /**
     * <b>Meldet</b> einen Beobachter <b>ab</b>.
     *
     * @param listener Der Beobachter, der abgemeldet werden soll.
     */
    @API
    public synchronized void remove(T listener)
    {
        listeners.remove(listener);
        if (iterating)
        {
            pendingCopyModifications
                .add(() -> listenerIterationCopy.remove(listener));
        }
        else
        {
            listenerIterationCopy.remove(listener);
        }
        EventListeners<T> parent = parentSupplier.get();
        if (parent != null)
        {
            parent.remove(listener);
        }
    }

    /**
     * Gibt wahr zurück, wenn der gegebene Beobachter bereits hinzugefügt wurde.
     *
     * @param listener Ein Beobachter.
     *
     * @return wahr, wenn der gegebene Beobachter bereits hinzugefügt wurde,
     *     sonst falsch.
     */
    @API
    public synchronized boolean contains(T listener)
    {
        return listeners.contains(listener);
    }

    @API
    public synchronized void invoke(Consumer<T> invoker)
    {
        if (iterating)
        {
            throw new IllegalStateException(
                    "Recursive invocation of event listeners is unsupported");
        }
        try
        {
            iterating = true;
            for (T listener : listenerIterationCopy)
            {
                invoker.accept(listener);
            }
        }
        finally
        {
            iterating = false;
            for (Runnable pendingModification : pendingCopyModifications)
            {
                pendingModification.run();
            }
            pendingCopyModifications.clear();
        }
    }

    /**
     * Gibt wahr zurück, wenn diese Instanz keine Beobachter enthält.
     *
     * @return wahr, wenn diese Instanz keine Beobachter enthält, sonst falsch.
     */
    @API
    public synchronized boolean isEmpty()
    {
        return listeners.isEmpty();
    }

    /**
     * <b>Löscht</b> alle Beobachter aus der Instanz.
     */
    @API
    public synchronized void clear()
    {
        listeners.clear();
    }

    /**
     * Meldet einen Beobachter automatisch in der entsprechenden Instanz an, die
     * alle Beobachter verwaltet.
     *
     * @param target Der Beobachter, der angemeldet werden soll.
     */
    public static void registerListeners(Object target)
    {
        if (target instanceof KeyStrokeListener)
        {
            ((KeyStrokeListenerRegistration) target)
                .addKeyStrokeListener((KeyStrokeListener) target);
        }
        if (target instanceof MouseClickListener)
        {
            ((MouseClickListenerRegistration) target)
                .addMouseClickListener((MouseClickListener) target);
        }
        if (target instanceof MouseScrollListener)
        {
            ((MouseScrollListenerRegistration) target)
                .addMouseScrollListener((MouseScrollListener) target);
        }
        if (target instanceof FrameUpdateListener)
        {
            ((FrameUpdateListenerRegistration) target)
                .addFrameUpdateListener((FrameUpdateListener) target);
        }
    }
}
